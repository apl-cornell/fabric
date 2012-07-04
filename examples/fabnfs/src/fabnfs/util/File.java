package fabnfs.util;

public interface File {
  static final int EOF = -1;
  
  public boolean exists();

  public boolean isFile();

  public boolean isDirectory();

//TODO apply access control here
  public boolean canRead();

//TODO apply access control here
  public boolean canWrite();

  public long lastModified();

  public boolean delete();

  public String[] list();

  public boolean mkdir();

  public boolean mkfile();

  public boolean renameTo(File dest);

  public boolean reset();

  public long length();

}
