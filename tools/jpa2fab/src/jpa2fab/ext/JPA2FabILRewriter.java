package jpa2fab.ext;

import java.util.HashSet;
import java.util.Set;

import polyglot.ast.Expr;
import polyglot.ast.FieldDecl;
import polyglot.ast.TypeNode;
import polyglot.ext.jl5.translate.JL5ToJLRewriter;
import polyglot.ext.jl5.types.JL5ClassType;
import polyglot.ext.jl5.types.JL5ParsedClassType;
import polyglot.ext.jl5.types.inference.LubType;
import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.Job;
import polyglot.types.ClassType;
import polyglot.types.Flags;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import polyglot.util.StringUtil;
import fabil.types.FabILTypeSystem;

public class JPA2FabILRewriter extends JL5ToJLRewriter {
  protected boolean inConstructor;
  protected boolean inEntity;
  protected String defaultStore;

  public JPA2FabILRewriter(Job job, ExtensionInfo from_ext,
      ExtensionInfo to_ext, String defaultStore) {
    super(job, from_ext, to_ext);
    this.inConstructor = false;
    this.defaultStore = defaultStore;
  }

  @Override
  public TypeNode typeToJava(Type t, Position pos) {
    if (isCollection(t)) {
      t = jl5ts.erasureType(t);
      if (t instanceof LubType) t = ((LubType) t).calculateLub();

      String fabName = fabricUtilNameFor(t.toClass());
      System.err.println("Converting " + t.toClass().fullName() + " to "
          + fabName);
      return nf.TypeNodeFromQualifiedName(pos, fabName);
    } else return super.typeToJava(t, pos);
  }

  public String fabricUtilNameFor(ClassType ct) {
    String cls = ct.name();
    while (ct.outer() != null) {
      cls = ct.outer().name() + "." + cls;
      ct = ct.outer();
    }
    return "fabric.util." + cls;
  }

  public boolean inConstructor() {
    return inConstructor;
  }

  public void inConstructor(boolean b) {
    inConstructor = b;
  }

  public void inEntity(boolean b) {
    inEntity = b;
  }

  public boolean inEntity() {
    return inEntity;
  }

  public Type Entity() throws SemanticException {
    return from_ts().typeForName("javax.persistence.Entity");
  }

  public Type MappedSuperclass() throws SemanticException {
    return from_ts().typeForName("javax.persistence.MappedSuperclass");
  }

  public boolean isEntity(ClassType ct) throws SemanticException {
    JL5ClassType jl5ct = (JL5ClassType) ct;
    return jl5ct.annotations().hasAnnotationType(Entity());
  }

  public void checkInheritence(JL5ParsedClassType type)
      throws SemanticException {
    if (from_ts().Object().equals(type.superType())) return;
    JL5ClassType sup = (JL5ClassType) type.superType();
    if (isPersistent(type) != isPersistent(sup))
      throw new SemanticException(
          "Entities and MappedSuperclasses classes must extend Entities or MappedSuperclasses:"
              + type.fullName()
              + "(isPersistent: "
              + isPersistent(type)
              + ")\n"
              + sup.fullName()
              + "(isPersistent: "
              + isPersistent(sup)
              + ")" + sup.annotations());
  }

  public boolean isMappedSuperclass(ClassType ct) throws SemanticException {
    JL5ClassType jl5ct = (JL5ClassType) ct;
    return jl5ct.annotations().hasAnnotationType(MappedSuperclass());
  }

  public boolean isPersistent(ClassType ct) throws SemanticException {
    return isEntity(ct) || isMappedSuperclass(ct);
  }

  private static Set<String> convert = new HashSet<String>();
  static {
    convert.add("AbstractCollection");
    convert.add("AbstractList");
    convert.add("AbstractMap");
    convert.add("AbstractSequentialList");
    convert.add("AbstractSet");
    convert.add("ArrayList");
    convert.add("Arrays");
    convert.add("Collection");
    convert.add("Collections");
    convert.add("Comparator");
    convert.add("ConcurrentModificationException");
    convert.add("Enumeration");
    convert.add("HashMap");
    convert.add("HashSet");
    convert.add("Iterable");
    convert.add("Iterator");
    convert.add("LinkedHashMap");
    convert.add("LinkedHashSet");
    convert.add("LinkedList");
    convert.add("List");
    convert.add("ListIterator");
    convert.add("Map");
    convert.add("NoSuchElementException");
    convert.add("Random");
    convert.add("RandomAccess");
    convert.add("Set");
    convert.add("SortedMap");
    convert.add("SortedSet");
    convert.add("TreeMap");
    convert.add("TreeSet");

    //
    convert.add("Entry");

  }

  public boolean isCollection(Type t) {
    if (!t.isClass()) return false;
    ClassType ct = t.toClass();
    return ct.package_() != null
        && ct.package_().fullName().equals("java.util")
        && convert.contains(ct.name());
  }

  public boolean isCollection(String className) {
    return className.startsWith("java.util")
        && convert.contains(StringUtil.getShortNameComponent(className));
  }

  public Expr createLocationExpr() {
    return to_nf().Local(Position.compilerGenerated(),
        to_nf().Id(Position.compilerGenerated(), defaultStore));
  }

  public FieldDecl createStoreFieldDecl() {
    Position pos = Position.compilerGenerated();
    FabILTypeSystem ts = (FabILTypeSystem) to_ts();
    TypeNode storeType = to_nf().CanonicalTypeNode(pos, ts.Store());
    Expr init =
        qq().parseExpr("Worker.getWorker().getStore(%E)",
            to_nf().StringLit(pos, defaultStore));
    FieldDecl fd =
        to_nf().FieldDecl(pos, Flags.PUBLIC.Static().Final(), storeType,
            to_nf().Id(pos, "store"), init);
    return fd;
  }
}
