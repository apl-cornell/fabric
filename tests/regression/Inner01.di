package regression;

public class Inner01 {
  class foo {
  }
  void m(final int x) {
    foo f = new foo();
    I i = new I() {
      int y = x;
    };
  }
}

interface I {}
