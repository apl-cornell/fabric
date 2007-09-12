package regression;

public interface Interface01 {
  void moo();
}

class C implements Interface01 {
  Interface01 f;
  public void moo() {
    f.moo();
  }
}

