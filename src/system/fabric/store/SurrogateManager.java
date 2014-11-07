package fabric.store;

/**
 * A surrogate manager encapsulates the strategy for building and maintaining
 * surrogate objects for inter-store references.
 *
 * @author mdgeorge
 */
public interface SurrogateManager {
  /**
   * <p>
   * Modify req so that all references are local. It should do so by creating or
   * reusing surrogate objects for all of the non-local references. Any new
   * surrogate objects should be added to the req.creates, while any read
   * surrogate objects should be added to req.reads.
   * </p>
   * <p>
   * TODO:
   * <ul>
   * <li>Add any required fields for authorization and surrogate policies</li>
   * <li>Need access to an object database to allocate new surrogate onums</li>
   * </ul>
   * </p>
   *
   * @param req
   *          the collections of objects created, modified, and read by this
   *          transaction. These collections may be modified if surrogate
   *          objects are created or modified.
   */
  public void createSurrogates(PrepareRequest req);
}
