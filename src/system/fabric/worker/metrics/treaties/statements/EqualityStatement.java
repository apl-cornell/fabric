package fabric.worker.metrics.treaties.statements;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.metrics.Metric;

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
}
