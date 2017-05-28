package old.scl;

public class FabricClassWrapper implements ClassWrapper{
	final InternalClassLoader icl;
	final String className;
	final byte[] classData;
	
	public FabricClassWrapper() {
		this("TestClass", null);
	}
	
	public FabricClassWrapper(String className, byte[] c) {
		this.className = className;
		classData = c;
		
		// will try to load this using the
		icl = new InternalClassLoader();
	}

	public Class loadClass() throws ClassNotFoundException {
		System.out.println("Attempting to invoke FabricClassWrapper's internal loader");
		return icl.loadClass("scl.TestClass");
	}
}