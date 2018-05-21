package fabric.worker.metrics;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

import fabric.common.FastSerializable;
import fabric.common.Logging;
import fabric.common.util.LongKeyMap;
import fabric.common.util.OidKeyHashMap;
import fabric.metrics.Metric;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * Utility class for an inlineable representation of a mapping from Metrics to a
 * set of "weakly consistent" stats that were grabbed for the metrics at some
 * point. This is useful for allowing weakly consistent/old statistics for
 * metrics when creating treaties.
 */
public class StatsMap implements FastSerializable, Serializable {

  @Override
  public String toString() {
    return this.table.toString();
  }

  private static class Entry implements FastSerializable, Serializable {
    public final double value;
    public final long samples;
    public final long lastUpdate;
    public final double updateInterval;
    public final double velocity;
    public final double noise;

    /**
     * @param value
     * @param samples
     * @param lastUpdate
     * @param updateInterval
     * @param velocity
     * @param noise
     */
    public Entry(double value, long samples, long lastUpdate,
        double updateInterval, double velocity, double noise) {
      this.value = value;
      this.samples = samples;
      this.lastUpdate = lastUpdate;
      this.updateInterval = updateInterval;
      this.velocity = velocity;
      this.noise = noise;
    }

    public Entry(DataInput in) throws IOException {
      this.value = in.readDouble();
      this.samples = in.readLong();
      this.lastUpdate = in.readLong();
      this.updateInterval = in.readDouble();
      this.velocity = in.readDouble();
      this.noise = in.readDouble();
    }

    @Override
    public void write(DataOutput out) throws IOException {
      out.writeDouble(this.value);
      out.writeLong(this.samples);
      out.writeLong(this.lastUpdate);
      out.writeDouble(this.updateInterval);
      out.writeDouble(this.velocity);
      out.writeDouble(this.noise);
    }

    @Override
    public String toString() {
      return "StatsEntry(" + this.value + "," + this.samples + ","
          + this.lastUpdate + "," + this.updateInterval + "," + this.velocity
          + "," + this.noise + ")";
    }
  }

  private OidKeyHashMap<Entry> table;

  private StatsMap() {
    this.table = new OidKeyHashMap<>();
  }

  private StatsMap(StatsMap other) {
    this.table = new OidKeyHashMap<>(other.table);
  }

  protected StatsMap(DataInput in) throws IOException {
    this.table = new OidKeyHashMap<>();
    int size = in.readInt();
    for (int i = 0; i < size; i++) {
      Store s = Worker.getWorker().getStore(in.readUTF());
      int size2 = in.readInt();
      for (int j = 0; j < size2; j++) {
        long onum = in.readLong();
        this.table.put(s, onum, new Entry(in));
      }
    }
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeInt(this.table.storeSet().size());
    for (Store s : this.table.storeSet()) {
      out.writeUTF(s.name());
      out.writeInt(this.table.get(s).size());
      for (LongKeyMap.Entry<Entry> e : this.table.get(s).entrySet()) {
        out.writeLong(e.getKey());
        e.getValue().write(out);
      }
    }
  }

  /* Serializable definitions, need to special case fabric references. */

  private void writeObject(ObjectOutputStream out) throws IOException {
    write(out);
  }

  private void readObject(ObjectInputStream in)
      throws IOException, ClassNotFoundException {
    this.table = new OidKeyHashMap<>();
    int size = in.readInt();
    for (int i = 0; i < size; i++) {
      Store s = Worker.getWorker().getStore(in.readUTF());
      int size2 = in.readInt();
      for (int j = 0; j < size2; j++) {
        long onum = in.readLong();
        this.table.put(s, onum, new Entry(in));
      }
    }
  }

  private void readObjectNoData() throws ObjectStreamException {
    this.table = new OidKeyHashMap<>();
  }

  private static final StatsMap EMPTY = new StatsMap();

  /**
   * Factory constructor for a blank map because signatures for FabIL don't
   * appear to support constructors.
   */
  public static StatsMap emptyStats() {
    return EMPTY;
  }

  /**
   * @param m a {@link Metric}
   * @return true iff there are stats in this map for m.
   */
  public boolean containsKey(Metric m) {
    return table.containsKey(m);
  }

  /**
   * @param m a {@link Metric}
   * @return the cached value of m, or a default value of 0.
   */
  public double getValue(Metric m) {
    if (table.containsKey(m)) return table.get(m).value;
    return 0;
  }

  /**
   * @param m a {@link Metric}
   * @return the cached sample count of m, or a default value of 0.
   */
  public long getSamples(Metric m) {
    if (table.containsKey(m)) return table.get(m).samples;
    return 0;
  }

  /**
   * @param m a {@link Metric}
   * @return the cached last update time of m, or a default value of now.
   */
  public long getLastUpdate(Metric m) {
    if (table.containsKey(m)) return table.get(m).lastUpdate;
    return System.currentTimeMillis();
  }

  /**
   * @param m a {@link Metric}
   * @return the cached update interval of m, or a default value of max long.
   */
  public double getUpdateInterval(Metric m) {
    if (table.containsKey(m)) return table.get(m).updateInterval;
    return Long.MAX_VALUE;
  }

  /**
   * @param m a {@link Metric}
   * @return the cached velocity of m, or a default value of 0.
   */
  public double getVelocity(Metric m) {
    if (table.containsKey(m)) return table.get(m).velocity;
    return 0;
  }

  /**
   * @param m a {@link Metric}
   * @return the cached noise of m, or a default value of 0.
   */
  public double getNoise(Metric m) {
    if (table.containsKey(m)) return table.get(m).noise;
    return 0;
  }

  /**
   * Put an entry for the given metric with the given stats.
   * @param m the new key
   * @param value the value of m
   * @param samples the sample count of m
   * @param lastUpdate the last update time of m
   * @param updateInterval the update interval of m
   * @param velocity the velocity of m
   * @param noise the noise of m
   * @return the updated map.
   */
  public StatsMap put(Metric m, double value, long samples, long lastUpdate,
      double updateInterval, double velocity, double noise) {
    StatsMap updated = new StatsMap(this);
    updated.table.put(m,
        new Entry(value, samples, lastUpdate, updateInterval, velocity, noise));
    Logging.METRICS_LOGGER.finest("Updated weak stats to " + updated);
    return updated;
  }

  /**
   * Merge entries in another map missing from this map.
   * @param other the other map to be merged in.
   * @return the updated map.
   */
  public StatsMap merge(StatsMap other) {
    StatsMap updated = new StatsMap(this);
    updated.table.putAll(other.table);
    Logging.METRICS_LOGGER.finest("Updated weak stats to " + updated);
    return updated;
  }
}
