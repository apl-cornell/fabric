package fabnfs.util;

public interface FileInputStream {

  public long skip(long n);

  public int read(byte[] barray);

  public int read();

  public void close();

}
