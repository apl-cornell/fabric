package fabric.worker.metrics.treaties.statements;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.metrics.Metric;
import fabric.worker.metrics.StatsMap;
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
  public long directExpiry(Metric m) {
    return trueExpiry(m);
  }

  @Override
  public long trueExpiry(Metric m) {
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
    if (!(obj instanceof EqualityStatement)) return false;
    EqualityStatement other = (EqualityStatement) obj;
    return value == other.value;
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
}
