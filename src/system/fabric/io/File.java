package fabric.io;

import fabric.client.Core;

public interface File {
  
  public boolean exists();

  public boolean isFile();

  public boolean isDirectory();

//TODO apply access control here
  public boolean canRead();

//TODO apply access control here
  public boolean canWrite();

//TODO get data from Transaction manager
  public long lastModified();

  public boolean delete();

  public StringArray list();

  public boolean mkdir();

  public boolean mkfile();

//TODO not implemented
  public boolean renameTo(File dest);

  public boolean reset();
  
  public long length();

}