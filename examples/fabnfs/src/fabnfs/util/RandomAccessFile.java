package fabnfs.util;


public interface RandomAccessFile {

  public void seek(long offset);

  public void write(FileByteArray b, int off, int len);

  public void close();
}
