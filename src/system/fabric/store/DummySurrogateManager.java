package fabric.store;

/**
 * This is a dummy surrogate policy. It leaves all inter-store references
 * intact.
 */
public class DummySurrogateManager implements SurrogateManager {

  public DummySurrogateManager(final TransactionManager tm) {
  }

  @Override
  public void createSurrogates(PrepareRequest req) {
    // Do nothing.
  }
}
