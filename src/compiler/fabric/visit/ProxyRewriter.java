package fabric.visit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import polyglot.ast.Block;
import polyglot.ast.CodeDecl;
import polyglot.ast.ConstructorCall;
import polyglot.ast.Local;
import polyglot.ast.Node;
import polyglot.ast.Receiver;
import polyglot.ast.Stmt;
import polyglot.qq.QQ;
import polyglot.types.ClassType;
import polyglot.types.Flags;
import polyglot.types.LocalInstance;
import polyglot.visit.NodeVisitor;
import fabric.ExtensionInfo;
import fabric.ast.FabricNodeFactory;
import fabric.extension.FabricExt;
import fabric.types.FabricTypeSystem;
import fabric.visit.ReadWriteChecker.State;

/**
 * Rewrites Fabric classes into classes that implement
 * <code>fabric.lang.Object</code>.
 */
public class ProxyRewriter extends NodeVisitor {
  
  protected QQ qq;
  protected FabricNodeFactory nf;
  protected FabricTypeSystem ts;
  
  private Map<LocalInstance, String> shadows;

  public ProxyRewriter(ExtensionInfo extInfo) {
    this.qq = new QQ(extInfo);
    this.nf = extInfo.nodeFactory();
    this.ts = extInfo.typeSystem();
  }

  @Override
  public NodeVisitor enter(Node parent, Node n) {
    if (n instanceof CodeDecl) {
      shadows = new HashMap<LocalInstance, String>();
    }
    
    return super.enter(parent, n);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.visit.NodeVisitor#override(polyglot.ast.Node)
   */
  @Override
  public Node override(Node n) {
    return ext(n).rewriteProxiesOverride(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.visit.NodeVisitor#leave(polyglot.ast.Node, polyglot.ast.Node,
   *      polyglot.visit.NodeVisitor)
   */
  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    n = ext(n).rewriteProxies(this);
    
    if (n instanceof CodeDecl) {
      n = produceShadowDecls((CodeDecl) n);
    }
    
    return n;
  }

  private FabricExt ext(Node n) {
    return (FabricExt) n.ext();
  }

  public QQ qq() {
    return qq;
  }

  public FabricNodeFactory nodeFactory() {
    return nf;
  }

  public FabricTypeSystem typeSystem() {
    return ts;
  }
  
  public static Flags toPublic(Flags flags) {
    return flags.clearPrivate().clearProtected().Public();
  }
  
  public static Flags toPrivate(Flags flags) {
    return flags.clearPublic().clearProtected().Private();
  }
  
  public static Flags toFinal(Flags flags) {
    return flags.clearAbstract().clearInterface().Final();
  }
  
  public static Flags toInterface(Flags flags) {
    return flags.clearAbstract().clearFinal().clearPrivate().Interface();
  }
  
  /** Returns a shadow name for local variable l. */
  public String getShadow(LocalInstance l) {
    if (l == null) {
      throw new NullPointerException();
    }
    
    String s = shadows.get(l);
    
    if (s == null) {
      s = l.name() + "$impl$" + shadows.size();
      shadows.put(l, s);
    }
    
    return s;
  }
  
  public Receiver replaceTarget(Receiver target, State accessState) {
    ClassType ct = (ClassType) target.type();
    Receiver shadow = target;
    
    if (target instanceof Local) {
      Local l = (Local) target;
      shadow = qq.parseExpr(getShadow(l.localInstance()));
    }
    
    if (!accessState.resident()) {
      target = qq.parseExpr("(%E = (" + ct.fullName() + ".$Impl) %E.fetch())", 
          shadow, target);
    } else {
      target = shadow;
    }
    
    return target;
  }
  
  @SuppressWarnings("unchecked")
  private CodeDecl produceShadowDecls(CodeDecl n) {
    if (shadows == null || shadows.size() == 0) {
      return n;
    }
    
    Block b = n.body();
    List<Stmt> l = new ArrayList<Stmt>(b.statements());
    
    int i = 0;
    for (; i < l.size(); i++) {
      if (!(l.get(i) instanceof ConstructorCall)) {
        break;
      }
    }
    
    l.addAll(i, shadowDecls());
    b = b.statements(l);
    n = (CodeDecl) n.body(b);
    return n;
  }

  private List<Stmt> shadowDecls() {
    List<Stmt> l = new ArrayList<Stmt>(shadows.size());
    
    for (Map.Entry<LocalInstance, String> e : shadows.entrySet()) {
      LocalInstance li = e.getKey();
      String name = e.getValue();
      ClassType ct = (ClassType) li.type();
      
      l.add(qq.parseStmt(ct.fullName() + ".$Impl " + name + ";"));
    }
    
    return l;
  }
  
}
