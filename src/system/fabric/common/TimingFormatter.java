package fabric.common;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import fabric.worker.Worker;

/**
 * A log formatter for outputting timing information as comma separated values. 
 * The output is of the format
 * 
 * <pre>[worker name and thread ID], [clock time in ms], [message]</pre>
 * 
 * @author mdgeorge
 *
 */
public final class TimingFormatter extends Formatter {

  @Override
  public String format(LogRecord record) {
    StringBuilder result = new StringBuilder();
    
    result.append(record.getMillis());
    result.append(", ");
    result.append(Worker.getWorker().config.name);
    result.append(" thread ");
    result.append(record.getThreadID());
    result.append(", ");
    result.append(String.format(record.getMessage(), record.getParameters()));
    result.append('\n');
    
    return result.toString();
  }
}
