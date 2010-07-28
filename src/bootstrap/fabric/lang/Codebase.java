package fabric.lang;

public interface Codebase extends fabric.lang.Object {

  public fabric.util.Map get$classes();

  public fabric.util.Map set$classes(fabric.util.Map val);

  public fabric.lang.FClass resolveClassName(java.lang.String name);

  public java.lang.Class<?> toJavaClass(java.lang.String name);

  public fabric.lang.FabricClassLoader getClassLoader();

  public fabric.util.Map getClasses();

  public fabric.lang.FClass getClass(java.lang.String s);

}
