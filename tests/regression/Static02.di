package regression;

class Static02 {
  static void m() {
    m();
    Static02.m();
  }
}

class Static02b extends Static02 {
  static void m() {}
}

