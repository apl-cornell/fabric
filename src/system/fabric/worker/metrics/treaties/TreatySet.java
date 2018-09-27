package fabric.worker.metrics.treaties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.FastSerializable;
import fabric.metrics.util.TreatiesBox;
import fabric.worker.Store;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.treaties.statements.TreatyStatement;

/**
 * Base class for a set of treaties (time limited logical statements) on a
 * Fabric object.
 */
public abstract class TreatySet
    implements FastSerializable, Iterable<MetricTreaty> {

  protected enum Kind {
    METRIC, DUMMY
  }

  private final Kind kind;

  protected TreatySet(Kind kind) {
    this.kind = kind;
  }

  /** @return the number of treaties. */
  public abstract int size();

  /** @return a value to use for an empty vector */
  public static TreatySet emptySet(fabric.lang.Object owner) {
    if (owner instanceof TreatiesBox)
      return new MetricTreatySet((TreatiesBox) owner);
    return DummyTreatySet.singleton;
  }

  /** Deserializer */
  public static TreatySet read(DataInput in) throws IOException {
    switch (Kind.values()[in.readByte()]) {
    case METRIC:
      return new MetricTreatySet(in);
    case DUMMY:
      return DummyTreatySet.singleton;
    default:
      throw new InternalError("This should never happen");
    }
  }

  @Override
  public final void write(DataOutput out) throws IOException {
    out.writeByte(kind.ordinal());
    writeData(out);
  }

  protected abstract void writeData(DataOutput out) throws IOException;

  /**
   * Check if this TreatySet is an effective extension (or equals) the given TreatySet.
   *
   * @param from
   *    the starting {@link TreatySet}
   * @return true if from and to are equal or to only differs with from by
   * extending the still live treaties.
   */
  public abstract boolean isExtensionOf(TreatySet from);

  /**
   * Check if this is a *strict* extension of the given TreatySet.
   */
  public abstract boolean isStrictExtensionOf(TreatySet t);

  /**
   * Add a treaty, overwriting any pre-existing treaties with an equivalent
   * assertion.
   */
  public abstract void add(MetricTreaty treaty);

  /**
   * Remove a treaty.  (This should only be called when deactivating a treaty.)
   */
  public abstract void remove(MetricTreaty treaty);

  /**
   * Create a new treaty with the given statement.
   *
   * @return updated set ("this" if there's no change) paired with the treaty.
   */
  public abstract MetricTreaty create(TreatyStatement stmt);

  /**
   * @return the treaty for the given id, if it has been "garbage collected",
   * returns null.
   */
  public abstract MetricTreaty get(long id);

  /**
   * @return treaty for the statement, if it already exists
   */
  public abstract MetricTreaty get(TreatyStatement stmt);

  /**
   * Get the complete set of observers for the treaties.
   */
  public abstract ImmutableObserverSet getObservers();

  /**
   * Run a prefetch for all observers of the treaties in this set.
   */
  public abstract void prefetch(Store triggeringStore);

  /**
   * Keep flattening updates until the backing maps match the history's maps.
   */
  public abstract void flattenUpdates();
}
