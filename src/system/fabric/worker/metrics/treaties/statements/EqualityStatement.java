package fabric.worker.metrics.treaties.statements;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.metrics.Metric;
import fabric.worker.Store;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.MetricTreaty;
import fabric.worker.metrics.treaties.enforcement.EnforcementPolicy;

/**
 * {@link TreatyStatement} for equality bounds.
 */
public class EqualityStatement extends TreatyStatement {
  private final double value;

  public EqualityStatement(double value) {
    super(TreatyStatement.Kind.EQUALITY);
    this.value = value;
  }

  public EqualityStatement(DataInput in) throws IOException {
    this(in.readDouble());
  }

  @Override
  public void writeStatementData(DataOutput out) throws IOException {
    out.writeDouble(this.value);
  }

  @Override
  public long directExpiry(Metric m, StatsMap weakStats) {
    return trueExpiry(m, weakStats);
  }

  @Override
  public long trueExpiry(Metric m, StatsMap weakStats) {
    if (m.value() < value) {
      return 0;
    } else {
      return Long.MAX_VALUE;
    }
  }

  @Override
  public boolean implies(TreatyStatement stmt) {
    if (stmt instanceof EqualityStatement) {
      return value == ((EqualityStatement) stmt).value;
    }
    return false;
  }

  @Override
  public boolean equals(Object obj) {
    return obj == this || (obj instanceof EqualityStatement
        && value == ((EqualityStatement) obj).value);
  }

  @Override
  public int hashCode() {
    return Double.hashCode(value);
  }

  @Override
  public String toString() {
    return "== " + value;
  }

  @Override
  public EnforcementPolicy getNewPolicy(Metric m, StatsMap weakStats) {
    return m.equalityPolicy(value, weakStats, m.$getStore());
  }

  @Override
  public MetricTreaty getProxy(Metric m, Store s) {
    return m.getProxy(s).getEqualityTreaty(value);
  }
}
