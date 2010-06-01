package foo;

public class A {
	public A() {
		B b = null;
		System.out.println("Instantiating B");
		b = new B(); 
		System.out.println(b);
	}

}
