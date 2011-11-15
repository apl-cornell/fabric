package jif.translate;

import java.util.*;

import jif.ast.JifClassDecl;
import jif.types.*;
import jif.types.label.Label;
import jif.types.principal.Principal;
import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;

public class ClassDeclToJavaExt_c extends ToJavaExt_c {
    /*
     * Some static final constants and methods for producing names of
     * generated methods and fields.
     */
    protected static final String INSTANCEOF_METHOD_NAME = "jif$Instanceof";
    protected static final String INITIALIZATIONS_METHOD_NAME = "jif$init";
    protected static final String castMethodName(ClassType ct) {
        return "jif$cast$"+ct.fullName().replace('.','_');
    }
    protected static final String interfaceClassImplName(String jifInterfaceName) {
        return jifInterfaceName + "_JIF_IMPL";
    }
    protected static final String constructorTranslatedName(ClassType ct) {
        return (ct.fullName() + ".").replace('.', '$');
    }
    
    protected static final String DEFAULT_CONSTRUCTOR_INVOKER_METHOD_NAME = "jif$invokeDefConstructor";
    
    /*
     * Code for translating ClassDecls 
     */
    private boolean hasDefaultConstructor = false;
    private List defaultConstructorExceptions = null;
    
    public NodeVisitor toJavaEnter(JifToJavaRewriter rw) throws SemanticException {
        // Bypass params and authority.
        JifClassDecl n = (JifClassDecl) node();

        rw.enteringClass(n.type());
        
        ClassType ct = n.type();
        for (Iterator iter = ct.constructors().iterator(); iter.hasNext(); ) {
            ConstructorInstance ci = (ConstructorInstance)iter.next();
            if (ci.formalTypes().isEmpty()) {
                hasDefaultConstructor = true;
                defaultConstructorExceptions = ci.throwTypes();
                break;
            }
        }

        return rw.bypass(n.params()).bypass(n.authority());
    }

    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        JifClassDecl n = (JifClassDecl) node();
        JifPolyType jpt = (JifPolyType)n.type();

        ClassBody cb = n.body();
        if (!jpt.flags().isInterface()) {
            if (rw.jif_ts().isJifClass(jpt)) {
                // add constructor
                cb = cb.addMember(produceConstructor(jpt, rw));
                if (hasDefaultConstructor) {
                    cb = cb.addMember(produceDefaultConstructorInvoker(jpt, rw, defaultConstructorExceptions));
                }
                // add initializer method (which is called from every 
                // translated constructer.                
                cb = addInitializer(cb, rw);
                
                // add any static initializers             
                cb = addStaticInitializers(cb, rw);
            }
            if (rw.jif_ts().isParamsRuntimeRep(jpt)) {
                if (!jpt.params().isEmpty()) {
                    // add instanceof and cast static methods to the class
                    cb = cb.addMember(produceInstanceOfMethod(jpt, rw, false));
                    cb = cb.addMember(produceCastMethod(jpt, rw));
                    
                    // add fields for params
                    if (rw.jif_ts().isJifClass(jpt)) {
                        for (Iterator iter = jpt.params().iterator(); iter.hasNext(); ) {
                            ParamInstance pi = (ParamInstance)iter.next();
                            String paramFieldName = ParamToJavaExpr_c.paramFieldName(pi);
                            TypeNode tn = typeNodeForParam(pi, rw);
                            cb = cb.addMember(rw.qq().parseMember("private final %T %s;", tn, paramFieldName));
                        }
                    }
                }
                
                // add getter methods for any params declared in interfaces
                cb = addInterfaceParamGetters(cb, jpt, jpt, rw);
            }            
        }
        else {
            // it's an interface
            if (rw.jif_ts().isParamsRuntimeRep(jpt)) {
                ClassBody implBody = rw.java_nf().ClassBody(Position.compilerGenerated(), new ArrayList(2));
                implBody = implBody.addMember(produceInstanceOfMethod(jpt, rw, true));
                implBody = implBody.addMember(produceCastMethod(jpt, rw));
                
                ClassDecl implDecl = rw.java_nf().ClassDecl(Position.compilerGenerated(),
                                                            n.flags().clearInterface().Abstract(),
                                                            interfaceClassImplName(n.name()),
                                                            null,
                                                            Collections.EMPTY_LIST,
                                                            implBody);
                rw.addAdditionalClassDecl(implDecl);
                
                // add getters for params to the interface
                for (Iterator iter = jpt.params().iterator(); iter.hasNext(); ) {
                    ParamInstance pi = (ParamInstance)iter.next();
                    String paramFieldNameGetter = ParamToJavaExpr_c.paramFieldNameGetter(pi);
                    TypeNode tn = typeNodeForParam(pi, rw);
                    cb = cb.addMember(rw.qq().parseMember("%T %s();", tn,paramFieldNameGetter));
                }
                
            }
        }
        

        rw.leavingClass();
        return rw.java_nf().ClassDecl(n.position(), n.flags(), n.name(),
                                      n.superClass(), n.interfaces(), cb);
    }

    /**
     * Create a method for initializations, and add it to cb.
     */
    protected ClassBody addInitializer(ClassBody cb, JifToJavaRewriter rw) {
        List inits = new ArrayList(rw.getInitializations());
        rw.getInitializations().clear();
        return cb.addMember(rw.qq().parseMember(
                                              "private void %s() { %LS }", 
                                              INITIALIZATIONS_METHOD_NAME,
                                              inits));
    }

    /**
     * Create methods for static initializations, and add it to cb.
     * 
     */
    protected ClassBody addStaticInitializers(ClassBody cb, JifToJavaRewriter rw) {
        if (rw.getStaticInitializations().isEmpty()) {
            return cb;
        }
        List inits = new ArrayList(rw.getStaticInitializations());
        rw.getStaticInitializations().clear();   

        Block b;
        if (inits.size() == 1) {
            b = (Block)inits.get(0);
        }
        else {
            b = rw.java_nf().Block(Position.compilerGenerated(), inits);
        }
        return cb.addMember(rw.java_nf().Initializer(Position.compilerGenerated(),
                                                     Flags.STATIC,
                                                     b));
    }

    /**
     * Go through the interfaces, and add any required fields and getters for the fields.
     * @param cb
     * @param jpt
     * @param rw
     * @throws SemanticException
     */
    protected ClassBody addInterfaceParamGetters(ClassBody cb, JifPolyType baseClass, JifPolyType jpt, JifToJavaRewriter rw) throws SemanticException {
        // go through the interfaces of cb
        if (!rw.jif_ts().isParamsRuntimeRep(jpt)) {
            // don't bother adding interface methods for classes that don't represent the runtime params (i.e., Jif sig classes)
            return cb;
        }
        for (Iterator iter = jpt.interfaces().iterator(); iter.hasNext(); ) {
            Type interf = (Type)iter.next();
            if (rw.jif_ts().isParamsRuntimeRep(interf) &&
                    !rw.jif_ts().isSubtype(baseClass.superType(), interf)) {                
                // the interface is not implemented in a super class,
                // so add fields and params for runtime representation of params
                JifPolyType interfPT = null;
                if (interf instanceof JifSubstType) {
                    JifSubstType interfST = (JifSubstType)interf;
                    JifSubst subst = (JifSubst)interfST.subst();
                    interfPT = (JifPolyType)interfST.base();
                    for (Iterator iter2 = interfPT.params().iterator(); iter2.hasNext(); ) {
                        ParamInstance pi = (ParamInstance)iter2.next();
                        String paramFieldName = ParamToJavaExpr_c.paramFieldName(pi);
                        String paramFieldNameGetter = ParamToJavaExpr_c.paramFieldNameGetter(pi);
                        TypeNode tn = typeNodeForParam(pi, rw);
                        Expr lblExpr = rw.paramToJava(subst.get(pi));
                        if (rw.jif_ts().isJifClass(jpt)) {
                            // it's a real Jif class, so add a real implementation
                            cb = cb.addMember(rw.qq().parseMember("private %T %s;", tn, paramFieldName));
                            cb = cb.addMember(rw.qq().parseMember(
                                                                  "public final %T %s() { "
                                                                  + " if (this.%s==null) this.%s = %E; "
                                                                  + "return this.%s; }",
                                                                  tn, paramFieldNameGetter, paramFieldName,
                                                                  paramFieldName, lblExpr, paramFieldName));
                        }
                        else {
                            // it's just a signature file, add the method sig but nothing else.
                            cb = cb.addMember(rw.qq().parseMember("public final native %T %s();", tn, paramFieldNameGetter));
                        }
                    }
                }
                else if (interf instanceof JifPolyType) {
                    interfPT = (JifPolyType)interf;
                }

                if (interfPT != null) {
                    // recurse on the supertype of interfaces.
                    cb = addInterfaceParamGetters(cb, baseClass, interfPT, rw);
                }

            }
        }
        return cb;

    }
    protected ClassMember produceInstanceOfMethod(JifPolyType jpt, JifToJavaRewriter rw, boolean useGetters) throws SemanticException {
        Context A = rw.context();
        rw = (JifToJavaRewriter)rw.context(A.pushStatic());
        JifTypeSystem jifts = rw.jif_ts();
        List formals = produceParamFormals(jpt, rw, true);

        String name = jpt.name();
        
        if (!jifts.isJifClass(jpt)) {
            // just produce a header
            return rw.qq().parseMember("static public native boolean %s(%LF);", INSTANCEOF_METHOD_NAME, formals);            
        }
        
        StringBuffer sb = new StringBuffer();
        sb.append("static public boolean %s(%LF) {");
        if (jpt.params().isEmpty()) {
            sb.append("return (o instanceof %s);");            
        }
        else {
            sb.append("if (o instanceof %s) { ");
            sb.append("%s c = (%s)o; ");
    
            // now test each of the params
            boolean moreThanOneParam = (jpt.params().size() > 1);
            sb.append(moreThanOneParam?"boolean ok = true;":"");
            for (Iterator iter = jpt.params().iterator(); iter.hasNext(); ) {
                ParamInstance pi = (ParamInstance)iter.next();
                String paramFieldName = ParamToJavaExpr_c.paramFieldName(pi);
                String paramArgName = ParamToJavaExpr_c.paramArgName(pi);
                String comparison = "equivalentTo";
                if (pi.isCovariantLabel()) {
                    comparison = "relabelsTo";
                }
    
                sb.append(moreThanOneParam?"ok = ok && ":"return ");
    
                String paramExpr = paramFieldName;
                if (useGetters) {
                    paramExpr = ParamToJavaExpr_c.paramFieldNameGetter(pi) + "()";
                }
                if (pi.isPrincipal()) {  
                    // e.g., PrincipalUtil.equivTo(c.expr, paramArgName)
                    sb.append(jifts.PrincipalUtilClassName() + "."+comparison+
                                         "(c."+paramExpr+","+paramArgName+");");
                }
                else {
                    // e.g., LabelUtil.equivTo(paramArgName)
                    sb.append(rw.runtimeLabelUtil() + "."+comparison+ 
                              "(c."+paramExpr+","+paramArgName+");");
                }
            }
            if (moreThanOneParam) sb.append("return ok;");
            sb.append("}");
            sb.append("return false;");
        }


        sb.append("}");
        return rw.qq().parseMember(sb.toString(), INSTANCEOF_METHOD_NAME, formals, name, name, name);
    }

    private static TypeNode typeNodeForParam(ParamInstance pi, JifToJavaRewriter rw) throws SemanticException {
        Type paramType = pi.isPrincipal() ? rw.jif_ts().Principal() : rw.jif_ts().Label();
        return rw.typeToJava(paramType, Position.compilerGenerated());
    }
    protected ClassMember produceCastMethod(JifPolyType jpt, JifToJavaRewriter rw) throws SemanticException {
        Context A = rw.context();
        rw = (JifToJavaRewriter)rw.context(A.pushStatic());

        TypeNode tn = rw.typeToJava(jpt, Position.compilerGenerated());;
        List formals = produceParamFormals(jpt, rw, true);
        if (!rw.jif_ts().isJifClass(jpt)) {
            // just produce a header
            return rw.qq().parseMember("static public native %T %s(%LF);", tn, castMethodName(jpt), formals);
            
        }

        StringBuffer sb = new StringBuffer();
        sb.append("static public %T %s(%LF) {");
        sb.append("if (o == null) return null;");
        sb.append("if (%s(%LE)) return (%T)o;");
        sb.append("throw new ClassCastException();");
        sb.append("}");

        List args = produceParamArgs(jpt, rw);
        return rw.qq().parseMember(sb.toString(), tn, castMethodName(jpt), formals, INSTANCEOF_METHOD_NAME, args, tn);
    }

    static protected List produceParamFormals(JifPolyType jpt, JifToJavaRewriter rw, boolean addObjectFormal) throws SemanticException {
        List formals = new ArrayList(jpt.params().size() + 1);
        Position pos = Position.compilerGenerated();
        for (Iterator iter = jpt.params().iterator(); iter.hasNext(); ) {
            ParamInstance pi = (ParamInstance)iter.next();
            String paramArgName = ParamToJavaExpr_c.paramArgName(pi);
            TypeNode tn = typeNodeForParam(pi, rw);
            Formal f = rw.java_nf().Formal(pos, Flags.FINAL, tn, paramArgName);
            formals.add(f);
        }

        if (addObjectFormal) {
            // add the object argument too.
            TypeNode tn = rw.qq().parseType("java.lang.Object");
            formals.add(rw.java_nf().Formal(pos, Flags.FINAL, tn, "o"));
        }
        return formals;
    }

    protected List produceParamArgs(JifPolyType jpt, JifToJavaRewriter rw) {
        List args = new ArrayList(jpt.params().size() + 1);
        for (Iterator iter = jpt.params().iterator(); iter.hasNext(); ) {
            ParamInstance pi = (ParamInstance)iter.next();
            String paramArgName = ParamToJavaExpr_c.paramArgName(pi);
            args.add(rw.qq().parseExpr(paramArgName));
        }

        // add the object argument too.
        args.add(rw.qq().parseExpr("o"));
        return args;
    }

    protected ClassMember produceConstructor(JifPolyType jpt, JifToJavaRewriter rw) throws SemanticException {
        // add arguments for params.
        List formals = produceParamFormals(jpt, rw, false);

        List inits = new ArrayList();

        // add super call.
        List superArgs = new ArrayList();
        Type superType = jpt.superType();
        if (superType instanceof JifSubstType && rw.jif_ts().isParamsRuntimeRep(((JifSubstType)superType).base())) {
            JifSubstType superjst = (JifSubstType)jpt.superType();
            JifPolyType superjpt = (JifPolyType)superjst.base();

            JifContext A = (JifContext)rw.context();
            JifToJavaRewriter rwCons = (JifToJavaRewriter)rw.context(A.pushConstructorCall());
            for (Iterator iter = superjpt.params().iterator(); iter.hasNext(); ) {
                ParamInstance pi = (ParamInstance)iter.next();
                Param param = ((JifSubst)superjst.subst()).get(pi);
                if (pi.isLabel()) {
                    superArgs.add(((Label)param).toJava(rwCons));
                }
                else {
                    superArgs.add(((Principal)param).toJava(rwCons));
                }
            }
        }
        inits.add(rw.qq().parseStmt("super(%LE);", (Object)superArgs));

        // create initializers for the fields from the arguments
        for (Iterator iter = jpt.params().iterator(); iter.hasNext(); ) {
            ParamInstance pi = (ParamInstance)iter.next();
            String paramFieldName = ParamToJavaExpr_c.paramFieldName(pi);
            String paramArgName = ParamToJavaExpr_c.paramArgName(pi);
            inits.add(rw.qq().parseStmt("this." + paramFieldName + " = " + paramArgName + ";"));
        }

        inits.addAll(additionalConstructorCode(rw));

        return rw.java_nf().ConstructorDecl(Position.compilerGenerated(),
                                            Flags.PUBLIC,
                                            jpt.name(),
                                            formals,
                                            Collections.EMPTY_LIST,
                                            rw.java_nf().Block(Position.compilerGenerated(),
                                                               inits));
    }

    protected List additionalConstructorCode(JifToJavaRewriter rw) {
        return Collections.EMPTY_LIST;
    }
    /**
     * Produce a method (with a standard name) that will invoke the default
     * constructor of the class. This method assumes that such a default 
     * constructor exists.
     */
    protected ClassMember produceDefaultConstructorInvoker(ClassType ct, 
                    JifToJavaRewriter rw, List throwTypes) throws SemanticException {
        // add arguments for params.
        if (throwTypes == null || throwTypes.isEmpty()) {
            return rw.qq().parseMember("public void " + 
                                       DEFAULT_CONSTRUCTOR_INVOKER_METHOD_NAME + "() {" +
                                       "this." + constructorTranslatedName(ct)+ "();" + 
                                            "}");
        }
        List typeNodes = new ArrayList(throwTypes.size());
        for (Iterator iter = throwTypes.iterator(); iter.hasNext(); ) {
            Type t = (Type)iter.next();
            TypeNode tn = rw.java_nf().AmbTypeNode(Position.compilerGenerated(), t.toClass().name());
            typeNodes.add(tn);
        }
        return rw.qq().parseMember("public void " + 
                                   DEFAULT_CONSTRUCTOR_INVOKER_METHOD_NAME + 
                                   "() throws %LT {" +
                                   "this." + constructorTranslatedName(ct)+ "();" + 
                                        "}", (Object)typeNodes);
        
    }    
}
