package regression;

public class LRValue02 {
  LRValue02 t;

  void m(LRValue02 arg) {
    this.m(t);
    t.m(this);
  }
}

