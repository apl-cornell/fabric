package bolt.ast;

/**
 * A confidentiality policy consisting of an owner principal and a reader
 * principal.
 */
public interface ReaderPolicy extends ConfPolicy {
  Principal owner();

  ReaderPolicy owner(Principal owner);

  Principal reader();

  ReaderPolicy reader(Principal reader);
}
