package fabric.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class ConsoleFormatter extends Formatter {

  private static String lineSeparator = System.getProperty("line.separator");
  private static long start = -1;
  private static int tagWidth = 13;

  @Override
  public String format(LogRecord record) {
    if (start < 0) start = record.getMillis();

    StringBuilder result = new StringBuilder();
    String level = record.getLevel().toString();
    String time = Long.toString(record.getMillis() - start);

    result.append("[");
    result.append(level);
    result.append(":");
    for (int i = tagWidth - level.length() - time.length(); i > 0; i--)
      result.append(" ");
    result.append(time);
    result.append("] ");
    result.append(formatMessage(record));

    result.append(lineSeparator);
    if (record.getThrown() != null) {
      try {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
          record.getThrown().printStackTrace(pw);
        }
        result.append(sw.toString());
      } catch (Exception ex) {
      }
    }

    return result.toString();
  }
}
