package foo;

public class Fail {
	public Fail() {
		D d = null;
		System.out.println("Instantiating D");
		d = new D(); 
		System.out.println(d);
	}

}
