package cms.model;

public interface FileEntry {
  public boolean  isFileAuthorized(User user);
  public FileData getFile();
}
