package fabnfs.util;


public interface RandomAccessFile {

  public void seek(long offset);

  public void write(byte[] b, int off, int len);

  public void close();
}
