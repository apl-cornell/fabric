package fabric.lang;

public class FabricClassLoader extends java.lang.ClassLoader {
  public native Codebase getCodebase();

  public static native fabric.lang.FabricClassLoader getClassLoader(
      fabric.lang.Codebase codebase);

  @Override
  public native java.lang.Class<?> findClass(java.lang.String name)
      throws java.lang.ClassNotFoundException;
}
