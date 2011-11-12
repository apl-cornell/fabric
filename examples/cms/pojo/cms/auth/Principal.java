package cms.auth;


public interface Principal {
  public boolean delegatesTo(Principal delagatee);
}
