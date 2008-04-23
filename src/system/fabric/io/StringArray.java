package fabric.io;

public class StringArray {
  public StringArray(int length) {
    contents = new String[length];
  }
  public String[] contents;
  
  public void set(int i, String str) {
    contents[i] = str;
  }
  
  public int length() {
    return contents.length;
  }
}