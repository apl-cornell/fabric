package fabric.io;

public class fileByteArray {
  public byte[] contents;
  public fileByteArray(int length) {
    contents = new byte[length];
  }
  
  public fileByteArray(byte[] wrapped) {
    this.contents = wrapped;
  }
  
  public int length() {
    return contents.length;
  }
  
  public void set(int i, byte b) {
    contents[i] = b;
  }
  
  public byte get(int i) {
    return contents[i];
  }
}