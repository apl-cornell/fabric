package regression;

public class Array01 {
  void m() {
    int[] i;
    Array01[] a;
    i = new int[3];
    i[0] = 0;
    i[1] = i[2];
    a = new Array01[3];
    a[0] = null;
    a[1] = a[2];
  }
}

