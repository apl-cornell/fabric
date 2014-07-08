/**
 * Copyright (C) 2010-2012 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
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
        PrintWriter pw = new PrintWriter(sw);
        record.getThrown().printStackTrace(pw);
        pw.close();
        result.append(sw.toString());
      } catch (Exception ex) {
      }
    }

    return result.toString();
  }
}
