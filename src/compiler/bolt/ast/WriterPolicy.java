package bolt.ast;

/**
 * An integrity policy consisting of an owner principal and a writer principal.
 */
public interface WriterPolicy extends IntegPolicy {
  Principal owner();

  WriterPolicy owner(Principal owner);

  Principal writer();

  WriterPolicy writer(Principal writer);
}
