package jif.translate;

import java.util.*;

import jif.ast.Jif;
import jif.ast.JifNodeFactory;
import jif.ast.JifUtil;
import jif.types.JifTypeSystem;
import jif.types.Param;
import jif.types.label.Label;
import jif.types.principal.Principal;
import polyglot.ast.*;
import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.Job;
import polyglot.frontend.Source;
import polyglot.qq.QQ;
import polyglot.types.*;
import polyglot.types.Package;
import polyglot.util.*;
import polyglot.visit.ContextVisitor;
import polyglot.visit.NodeVisitor;

/** Visitor which performs rewriting on the AST. */
public class JifToJavaRewriter extends ContextVisitor
{
    protected ExtensionInfo java_ext;
    private JifTypeSystem jif_ts;
    private JifNodeFactory jif_nf;
    private Job job;
    private QQ qq;
    
    protected Collection additionalClassDecls;
    protected Collection newSourceFiles;
    
    protected List initializations;
    protected List staticInitializations;

    public JifToJavaRewriter(Job job,
                             JifTypeSystem jif_ts,
                             JifNodeFactory jif_nf,
                             ExtensionInfo java_ext) {
        //super(jif_ts);
        super(job, jif_ts, java_ext.nodeFactory());
        this.job = job;
        this.jif_ts = jif_ts;
        this.jif_nf = jif_nf;
        this.java_ext = java_ext;
        this.qq = new QQ(java_ext);
        this.additionalClassDecls = new LinkedList();
        this.newSourceFiles = new LinkedList();
        this.initializations = new ArrayList();
        this.staticInitializations = new ArrayList();
    }

    public void finish(Node ast) {
        if (ast instanceof SourceCollection) {
            SourceCollection c = (SourceCollection) ast;
            for (Iterator iter = c.sources().iterator(); iter.hasNext(); ) {
                SourceFile sf = (SourceFile)iter.next();
                java_ext.scheduler().addJob(sf.source(), sf);
                
            }
        }
        else {
            java_ext.scheduler().addJob(job.source(), ast);
        }

        
        // now add any additional source files, which should all be public.
        for (Iterator iter = newSourceFiles.iterator(); iter.hasNext(); ) {
            SourceFile sf = (SourceFile)iter.next();
            java_ext.scheduler().addJob(sf.source(), sf);
        }
        newSourceFiles.clear();
    }

    public JifTypeSystem jif_ts() {
        return jif_ts;
    }

    public JifNodeFactory jif_nf() {
        return jif_nf;
    }

    public TypeSystem java_ts() {
        return java_ext.typeSystem();
    }

    public NodeFactory java_nf() {
        return java_ext.nodeFactory();
    }

    public ErrorQueue errorQueue() {
        return job.compiler().errorQueue();
    }

    public NodeVisitor enterCall(Node n) {
        try {
            Jif ext = JifUtil.jifExt(n);
            return ext.toJava().toJavaEnter(this);
        }
        catch (SemanticException e) {
            Position position = e.position();

            if (position == null) {
                position = n.position();
            }

            errorQueue().enqueue(ErrorInfo.SEMANTIC_ERROR,
                                 e.getMessage(), position);

            return this;
        }
    }

    public Node leaveCall(Node old, Node n, NodeVisitor v) {
        try {
            Jif ext = JifUtil.jifExt(n);
            Node m = ext.toJava().toJava(this);
            if (m.del() instanceof Jif)
                throw new InternalCompilerError(m + " is still a Jif node.");
            return m;
        }
        catch (SemanticException e) {
            Position position = e.position();

            if (position == null) {
                position = n.position();
            }

            errorQueue().enqueue(ErrorInfo.SEMANTIC_ERROR,
                                 e.getMessage(), position);

            return n;
        }
    }

    public Expr paramToJava(Param param) throws SemanticException {
        if (param instanceof Label) {
            return labelToJava((Label)param);
        }
        if (param instanceof Principal) {
            return principalToJava((Principal)param);
        }
        throw new InternalCompilerError("Unexpected param " + param);
    }

    public Expr labelToJava(Label label) throws SemanticException {
        return label.toJava(this);
    }

    public Expr principalToJava(Principal principal) throws SemanticException {
        return principal.toJava(this);
    }

    public TypeNode typeToJava(Type t, Position pos) throws SemanticException {
        NodeFactory nf = this.java_nf();
        TypeSystem ts = this.java_ts();
        JifTypeSystem jifts = this.jif_ts();
        
        if (t.isNull()) return canonical(nf, ts.Null(), pos);
        if (t.isVoid()) return canonical(nf, ts.Void(), pos);
        if (t.isBoolean()) return canonical(nf, ts.Boolean(), pos);
        if (t.isByte()) return canonical(nf, ts.Byte(), pos);
        if (t.isChar()) return canonical(nf, ts.Char(), pos);
        if (t.isShort()) return canonical(nf, ts.Short(), pos);
        if (t.isInt()) return canonical(nf, ts.Int(), pos);
        if (t.isLong()) return canonical(nf, ts.Long(), pos);
        if (t.isFloat()) return canonical(nf, ts.Float(), pos);
        if (t.isDouble()) return canonical(nf, ts.Double(), pos);
        
        if (jifts.isLabel(t)) {
        	return nf.TypeNodeFromQualifiedName(pos, jifts.LabelClassName());
        }
        
        if (jifts.isPrincipal(t)) {
        	return nf.TypeNodeFromQualifiedName(pos, jifts.PrincipalClassName());
        }
        
        if (t.isArray()) {
            return nf.ArrayTypeNode(pos,
                                    typeToJava(t.toArray().base(), pos));
        }
        
        if (t.isClass()) {
         	return nf.TypeNodeFromQualifiedName(pos, t.toClass().fullName());
        }
        
        throw new InternalCompilerError("Cannot translate type " + t + ".");
    }
    
    protected TypeNode canonical(NodeFactory nf, Type t, Position pos) {
        return nf.CanonicalTypeNode(pos, t);
    }

    public QQ qq() {
        return qq;
    }

    private ClassType currentClass;
    private boolean inConstructor;

    public ClassType currentClass() {
        return this.currentClass;
    }

    public void enteringClass(ClassType t) {
        this.currentClass = t;
    }
    public void leavingClass() {
        this.currentClass = null;
    }
    public void addInitializer(Block s) {
        this.initializations.add(s);        
    }
    public void addInitializer(FieldInstance fi, Expr init) throws SemanticException {
        Stmt s = qq().parseStmt(fi.name() + " = %E;", init);
        this.initializations.add(s);
    }

    public List getInitializations() {
        return this.initializations;
    }

    public void addStaticInitializer(Block s) {
        this.staticInitializations.add(s);                
    }
    public List getStaticInitializations() {
        return this.staticInitializations;
    }

    public void addAdditionalClassDecl(ClassDecl cd) {
        this.additionalClassDecls.add(cd);
    }

    /**
     * Take any additional class declarations that can fit into the source file,
     * i.e., non-public class decls.
     */
    public Node leavingSourceFile(SourceFile n) {
        List l = new ArrayList(n.decls().size() + additionalClassDecls.size());
        l.addAll(n.decls());
        for (Iterator iter = this.additionalClassDecls.iterator(); iter.hasNext(); ) {
            ClassDecl cd = (ClassDecl)iter.next();
            if (cd.flags().isPublic()) {
                // cd is public, we will put it in it's own source file.
                SourceFile sf = java_nf().SourceFile(Position.compilerGenerated(), 
                                                     n.package_(), 
                                                     Collections.EMPTY_LIST,
                                                     Collections.singletonList(cd));
                
                String newName = cd.name() + "." + job.extensionInfo().defaultFileExtension();
                String newPath = n.source().path().substring(0, n.source().path().length() - n.source().name().length()) + newName;
                
                Source s = new Source(newName,
                                      newPath,
                                      n.source().lastModified());
                sf = sf.source(s);
                this.newSourceFiles.add(sf);
            }
            else {
                // cd is not public; it's ok to put the class decl in the source file.
                l.add(cd);
            }
        }    
        
        this.additionalClassDecls.clear();
        return n.decls(l);
    }

    public boolean inConstructor() {
        return this.inConstructor;
    }

    public void inConstructor(boolean flag) {
        this.inConstructor = flag;
    }

    /**
     * The full class path of the runtime label utility.
     */
    public String runtimeLabelUtil() {
        return jif_ts().LabelUtilClassName() + ".singleton()";
    }


}
