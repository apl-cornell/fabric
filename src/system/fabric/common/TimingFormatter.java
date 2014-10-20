package fabric.common;

import java.text.MessageFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import fabric.worker.Worker;

/**
 * A log formatter for outputting timing information as comma separated values.
 * The output is of the format
 *
 * <pre>
 * [clock time in ms], [worker name], thread [thread ID], [message]
 * </pre>
 *
 * @author mdgeorge
 */
public final class TimingFormatter extends Formatter {

  @Override
  public String format(LogRecord record) {
    StringBuilder result = new StringBuilder();

    result.append(record.getMillis());
    result.append(", ");
    result.append(Worker.isInitialized() ? Worker.getWorker().config.name
        : "(uninitialized)");
    result.append(", thread ");
    result.append(record.getThreadID());
    result.append(", ");
    result.append(MessageFormat.format(record.getMessage(),
        record.getParameters()));
    result.append('\n');

    return result.toString();
  }
}
