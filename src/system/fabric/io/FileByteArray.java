package fabric.io;

public class FileByteArray {
  public byte[] contents;
  public FileByteArray(int length) {
    contents = new byte[length];
  }
  
  public FileByteArray(byte[] wrapped) {
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