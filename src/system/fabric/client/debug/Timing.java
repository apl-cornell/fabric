package fabric.client.debug;

import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Deque;

/** A utility class for recording the timing of various categories. */
public enum Timing {

  INIT,          // everything before the client app is run
  APP,           // application code
  BEGIN, COMMIT, // local transaction management
  TXLOG,         // logging reads, writes, creates, etc
  SUBTX,         // merging of logs and other subtx management
  FETCH,         // fetching objects
  CORE;          // other communication with the core
  
  /** The time attributed to this category so far */
  private long time  = 0;
  /** The number of additions used to generate time */
  private int  count = 0;
  
  private static Deque<Timing> scope = new ArrayDeque<Timing>();
  private static long          stamp = System.currentTimeMillis();
  private static boolean       debug = true;
  
  static {
    scope.push(INIT);
  }

  private void attribute() {
    long now  = System.currentTimeMillis();
    long diff = now - stamp; 
    Timing t = scope.peek();
    t.time += diff;
    t.count++;
    stamp = now;
  }
  
  // public interface //////////////////////////////////////////////////////////
    
  /** Begin recording time into the given category (exclusively) */
  public void begin() {
    attribute();
    scope.push(this);
  }
  
  /** Stop recording time into the given category, and begin recording to the
   *  former category.
   */
  public void end() {
    attribute();
    if (scope.pop() != this && debug)
      throw new AssertionError();
  }
  
  /** Print a summary of the time spent in each category to standard output. */
  public static void printStats() {
    printStats(System.out);
  }
  
  /** Print a summary of the time spent in each category to the provided OutputStream */
  public static void printStats(PrintStream out) {
    long total = 0;
    for (Timing t : Timing.values()) {
      total += t.time;
    }
    out.println("Category  Time (ms)  Stdev    Percent of total\n");
    for (Timing t : Timing.values()) {
      out.format("%6s:   %7dms  +/-%4d  %d%%\n",
                        t.name(), t.time, (int) Math.sqrt(t.count), t.time * 100 / total);
    }
    out.format("\n Total:  %8dms\n", total);
  }
  
  /** Reset all of the statistics. */
  public static void reset() {
    for (Timing t : values()) {
      t.time  = 0;
      t.count = 0;
    }
    
    scope.clear();
    scope.push(INIT);
    stamp = System.currentTimeMillis();
  }
  
  // unit test ///////////////////////////////////////////////////////////////
  
  public static void main(String[] args) throws InterruptedException {
    Object o = new Object();
    synchronized(o) {
      o.wait(100);

      Timing.COMMIT.begin();
      o.wait(50);

      Timing.CORE.begin();
      o.wait(150);

      Timing.CORE.end();
      o.wait(35);

      Timing.SUBTX.begin();
      o.wait(90);

      Timing.SUBTX.end();
      o.wait(120);

      Timing.COMMIT.end();
      
      Timing.printStats();
    }
  }
}
