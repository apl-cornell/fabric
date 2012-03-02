package fabric.translate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fabil.ast.FabILNodeFactory;
import fabil.types.FabILTypeSystem;
import fabric.types.FabricClassType;
import fabric.types.FabricParsedClassType;
import fabric.types.FabricParsedClassType_c;
import fabric.types.FabricTypeSystem;
import fabric.visit.FabricToFabilRewriter;

import polyglot.ast.ClassBody;
import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.Expr;
import polyglot.ast.Formal;
import polyglot.ast.Node;
import polyglot.ast.Stmt;
import polyglot.ast.TypeNode;
import polyglot.types.ClassType;
import polyglot.types.Context;
import polyglot.types.Flags;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import jif.translate.ClassDeclToJavaExt_c;
import jif.translate.JifToJavaRewriter;
import jif.translate.ParamToJavaExpr_c;
import jif.types.JifPolyType;
import jif.types.JifTypeSystem;
import jif.types.ParamInstance;
import jif.types.label.ConfPolicy;
import jif.types.label.Label;

public class ClassDeclToFabilExt_c extends ClassDeclToJavaExt_c {
  public static final String jifConstructorTranslatedName(ClassType ct) {
    return ClassDeclToJavaExt_c.constructorTranslatedName(ct);
  }


  @SuppressWarnings("unchecked")
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {

    ClassDecl cd = (ClassDecl) super.toJava(rw);

    cd = cd.body(addLabelInitializer(cd.body(), rw));

    FabILNodeFactory nf = (FabILNodeFactory) rw.nodeFactory();
    FabILTypeSystem ts = (FabILTypeSystem) rw.java_ts();

    TypeNode worker =
      nf.CanonicalTypeNode(Position.compilerGenerated(), ts.Worker());

    List<ClassMember> members =
      new ArrayList<ClassMember>(cd.body().members().size() + 1);
    members.add(nf.FieldDecl(
        Position.compilerGenerated(),
        Flags.FINAL.Static(),
        worker,
        nf.Id(Position.compilerGenerated(), "worker$"),
        nf.Call(Position.compilerGenerated(), worker,
            nf.Id(Position.compilerGenerated(), "getWorker"))));
    members.addAll(cd.body().members());

    return cd.body(cd.body().members(members));
  }

  @Override
  protected ClassMember produceInstanceOfMethod(JifPolyType jpt, JifToJavaRewriter rw, boolean useGetters) throws SemanticException {
    Context A = rw.context();
    rw = (JifToJavaRewriter)rw.context(A.pushStatic());
    FabricToFabilRewriter frw = (FabricToFabilRewriter) rw;
    JifTypeSystem jifts = rw.jif_ts();
    List formals = produceFormals(jpt, rw, true);

    String name = jpt.name();

    boolean sigMode = ((FabricToFabilRewriter) rw).inSignatureMode();

    if (!jifts.isJifClass(jpt) || sigMode) {
      // just produce a header
      return rw.qq().parseMember("static public native boolean %s(%LF);", INSTANCEOF_METHOD_NAME, formals);            
    }

    StringBuffer sb = new StringBuffer();
    sb.append("static public boolean %s(%LF) {");
    // Add code that checks that the access label of jpt flows to o.store
    // proceed normally if it does, otherwise throw an InternalError
    if (!(jpt instanceof FabricParsedClassType_c)) 
      throw new InternalCompilerError("Trying to produce an instanceof method for a non-fabric class");

    FabricParsedClassType_c fpct = (FabricParsedClassType_c) jpt;
    ConfPolicy cp = fpct.accessPolicy();
    Label accessLabel = jifts.pairLabel(cp.position(), cp, jifts.topIntegPolicy(cp.position()));
    Expr accessLabelExpr = rw.labelToJava(accessLabel);
    Expr storeLabelExpr = rw.qq().parseExpr(rw.runtimeLabelUtil() + 
        ".readerPolicyLabel(" + frw.runtimePrincipalUtil() +
        ".topPrincipal()"+", o.$getStore().getPrincipal())");
    sb.append("if (!" + rw.runtimeLabelUtil() + ".relabelsTo(%E, %E)) " +
    "throw new InternalError(\"Illegal Access to \" + o.$getStore());");

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
    return rw.qq().parseMember(sb.toString(), INSTANCEOF_METHOD_NAME, 
        formals, accessLabelExpr, storeLabelExpr, name, name, name);
  }


  @Override
  @SuppressWarnings("unchecked")
  protected List produceFormals(JifPolyType jpt, JifToJavaRewriter rw, boolean addObjectFormal) throws SemanticException {
    List formals = super.produceFormals(jpt, rw, false);

    if (addObjectFormal) {
      // add the object argument too.
      TypeNode tn = rw.qq().parseType("fabric.lang.Object");
      formals.add(rw.java_nf().Formal(Position.compilerGenerated(), Flags.FINAL, tn, "o"));
    }

    // add access policy formal

    Formal al = rw.nodeFactory().Formal(Position.compilerGenerated(),
        Flags.FINAL, 
        rw.typeToJava(rw.jif_ts().Label(), Position.compilerGenerated()), 
    "jif$accessPolicy");

    formals.add(al);

    return formals;
  }

  @Override
  @SuppressWarnings("unchecked")
  protected List produceParamArgs(JifPolyType jpt, JifToJavaRewriter rw) {
    List args = super.produceParamArgs(jpt, rw);

    // add access policy arg
    args.add(rw.qq().parseExpr("jif$accessPolicy"));

    return args;
  }



  @Override
  protected ClassBody addInitializer(ClassBody cb, JifToJavaRewriter rw) {
    List<Stmt> inits = new ArrayList<Stmt>(rw.getInitializations());
    rw.getInitializations().clear();
    return cb.addMember(rw.qq().parseMember("private void %s() { %LS }",
        INITIALIZATIONS_METHOD_NAME, inits));
  }

  /**
   * Create a method for initializing update and access labels
   * 
   * @throws SemanticException
   *           if the field or access label cannot be translated
   */
  protected ClassBody addLabelInitializer(ClassBody cb, JifToJavaRewriter rw)
  throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) rw.jif_ts();
    boolean sigMode = ((FabricToFabilRewriter) rw).inSignatureMode();

    //FIXME: why is rw.currentClass() null?
    ClassDecl n = (ClassDecl) node();
    FabricClassType ct = (FabricClassType)n.type();

    if (!sigMode && ts.isFabricClass(ct)) {

      // translate the labels to FabIL
      Label updateLabel = ct.updateLabel();
      ConfPolicy accessPolicy = ct.accessPolicy();

      if (updateLabel == null || accessPolicy == null) {
        throw new InternalCompilerError("Null update label or access policy");
      }

      // locate labels at the same store as the object
      rw = ((FabricToFabilRewriter) rw).pushLocation(rw.qq().parseExpr("this.$getStore()"));

      Expr updateLabelExpr = rw.labelToJava(updateLabel);

      Label accessLabel = ts.pairLabel(accessPolicy.position(), accessPolicy, 
          ts.topIntegPolicy(accessPolicy.position()));

      Expr accessLabelExpr = rw.labelToJava(accessLabel);

      return cb.addMember(rw.qq().parseMember(
          "public Object %s() { " + "this.$updateLabel = %E;  "
          + "this.$accessPolicy = %E.confPolicy();" + "return this;" + "}",
          FabricToFabilRewriter.LABEL_INITIALIZER_METHOD_NAME, updateLabelExpr, accessLabelExpr));
    }

    return cb;
  }
}
