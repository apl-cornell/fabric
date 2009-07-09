package fabric.common;

import java.io.PrintStream;
import java.util.StringTokenizer;

import fabric.common.exceptions.UsageError;

public abstract class Options {
  /**
   * Whether to turn off SSL encryption for debugging purposes.
   */
  public static boolean DEBUG_NO_SSL = false;
  
  protected Options() {
    setDefaultValues();
  }

  protected abstract void setDefaultValues();

  /**
   * Parses a command.
   * 
   * @return the next index to process. i.e., if calling this method processes
   *         two commands, then the return value should be index+2.
   */
  protected abstract int parseCommand(String args[], int index)
      throws UsageError;

  public void parseCommandLine(String args[]) throws UsageError {
    for (int i = 0; i < args.length;) {
      try {
        int ni = parseCommand(args, i);
        if (ni == i) throw new UsageError("Illegal option: " + args[i]);
        i = ni;
      } catch (ArrayIndexOutOfBoundsException e) {
        throw new UsageError("Missing argument");
      }
    }
  }

  /**
   * The maximum width of a line when printing usage information. Used by
   * <code>usageForFlag</code>.
   */
  protected static final int USAGE_SCREEN_WIDTH = 76;

  /**
   * The number of spaces from the left that the description for flags will be
   * displayed. Used by <code>usageForFlag</code>.
   */
  protected static final int USAGE_FLAG_WIDTH = 27;

  /**
   * Output a flag and a description of its usage in a nice format.
   * 
   * @param out
   *          output PrintStream
   * @param flag
   *          the name of the flag.
   * @param description
   *          description of the flag.
   */
  protected static void usageForFlag(PrintStream out, String flag, String desc) {
    out.print("  ");
    out.print(flag);

    // cur is where the cursor is on the screen.
    int cur = flag.length() + 2;

    if (cur < USAGE_FLAG_WIDTH) {
      printSpaces(out, USAGE_FLAG_WIDTH - cur);
    } else {
      // The flag is long. Get a new line before printing the description.
      out.println();
      printSpaces(out, USAGE_FLAG_WIDTH);
    }

    cur = USAGE_FLAG_WIDTH;

    // Break up the description.
    StringTokenizer st = new StringTokenizer(desc);
    while (st.hasMoreTokens()) {
      String s = st.nextToken();
      if (cur + s.length() > USAGE_SCREEN_WIDTH) {
        out.println();
        printSpaces(out, USAGE_FLAG_WIDTH);
        cur = USAGE_FLAG_WIDTH;
      }

      out.print(s);
      cur += s.length();
      if (st.hasMoreTokens()) {
        if (cur + 1 > USAGE_SCREEN_WIDTH) {
          out.println();
          printSpaces(out, USAGE_FLAG_WIDTH);
          cur = USAGE_FLAG_WIDTH;
        } else {
          out.print(" ");
          cur++;
        }
      }
    }

    out.println();
  }

  /**
   * Output a flag and a description of its usage in a nice format.
   * 
   * @param out
   *          output PrintStream
   * @param flag
   *          the name of the flag.
   * @param description
   *          description of the flag.
   * @param defVal
   *          default value
   */
  protected static void usageForFlag(PrintStream out, String flag, String desc,
      String defVal) {
    usageForFlag(out, flag, desc + " (default: " + defVal + ")");
  }

  /**
   * Output a flag and a description of its usage in a nice format.
   * 
   * @param out
   *          output PrintStream
   * @param flag
   *          the name of the flag.
   * @param description
   *          description of the flag.
   * @param defVal
   *          default value
   */
  protected static void usageForFlag(PrintStream out, String flag, String desc,
      int defVal) {
    usageForFlag(out, flag, desc, new Integer(defVal).toString());
  }

  private static void printSpaces(PrintStream out, int n) {
    while (n-- > 0)
      out.print(' ');
  }
}
