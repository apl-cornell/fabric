package fabnfs.util;

public class StringArray {

  public String[] contents;
  
  public StringArray(int length) {
    contents = new String[length];
  }
  
  public StringArray(String[] args) {
    this.contents = args;
  }

  
  public void set(int i, String str) {
    contents[i] = str;
  }
  
  public int length() {
    return contents.length;
  }
}