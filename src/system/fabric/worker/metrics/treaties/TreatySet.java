package fabric.worker.metrics.treaties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.FastSerializable;

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
    // TODO
    return null;
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
  public void write(DataOutput out) throws IOException {
    out.writeByte(kind.ordinal());
    writeData(out);
  }

  protected abstract void writeData(DataOutput out) throws IOException;

  /**
   * Check if the second TreatySet is an effective extension of all treaties in
   * the first TreatySet.
   *
   * @param from
   *    the starting {@link TreatySet}
   * @param to
   *    the ending {@link TreatySet}
   * @return true if from and to are equal or to only differs with from by
   * extending the still live treaties.
   */
  public static boolean checkExtension(TreatySet from, TreatySet to) {
    return from.equals(to) || checkExtensionStrict(from, to);
  }

  /**
   * Check if the second TreatySet is a strict extension of all treaties in
   * the first TreatySet.
   *
   * @param from
   *    the starting {@link TreatySet}
   * @param to
   *    the ending {@link TreatySet}
   * @return true if from and to are not equal and to only differs with from by
   * extending the still live treaties.
   */
  public static boolean checkExtensionStrict(TreatySet from, TreatySet to) {
    // TODO
    return false;
  }
}
