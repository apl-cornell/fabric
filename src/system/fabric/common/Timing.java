/**
 * Copyright (C) 2010-2013 Fabric project group, Cornell University
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

import java.io.PrintStream;
import java.util.Stack;

/** A utility class for recording the timing of various categories. */
public enum Timing {

  APP, // application code
  BEGIN, // local transaction begin
  COMMIT, // local transaction commit processing
  TXLOG, // logging reads, writes, creates, etc
  SUBTX, // merging of logs and other subtx management
  FETCH, // fetching objects
  STORE; // other communication with the store

  /** The time attributed to this category so far */
  private long time;
  /** The number of additions used to generate time */
  private int count;
  /** Whether or not to record time for this category */
  public boolean enabled = false;

  private static Stack<Timing> scope;
  private static long stamp;
  private static boolean debug = true;

  static {
    reset();
  }

  private static void attribute() {
    long now = System.currentTimeMillis();
    Timing t = scope.peek();
    t.time += now - stamp;
    t.count++;
    stamp = now;
  }

  // public interface //////////////////////////////////////////////////////////

  /** Begin recording time into the given category (exclusively) */
  public void begin() {
    if (enabled) {
      attribute();
      scope.push(this);
    }
  }

  /**
   * Stop recording time into the given category, and begin recording to the
   * former category.
   */
  public void end() {
    if (enabled) {
      attribute();
      if (scope.pop() != this && debug) throw new AssertionError();
    }
  }

  /** Print a summary of the time spent in each category to standard output. */
  public static void printStats() {
    printStats(System.out);
  }

  /**
   * Print a summary of the time spent in each category to the provided
   * OutputStream
   */
  public static void printStats(PrintStream out) {
    attribute();

    long total = 0;
    int count = 0;
    for (Timing t : Timing.values()) {
      total += t.time;
      count += t.count;
    }
    for (Timing t : Timing.values()) {
      if (t.enabled)
        out.format("%6s:  %6d ms\n", t.name().toLowerCase(), t.time);
    }
    out.format(" Total: %7d ms (%-8d measurements)\n", total, count);
  }

  /** Reset all of the statistics, and begin timing in cat */
  public static void reset() {

    for (Timing t : values()) {
      t.time = 0;
      t.count = 0;
    }

    scope = new Stack<Timing>();
    scope.push(APP);
    APP.count = 1;
    stamp = System.currentTimeMillis();
  }

  // unit test ///////////////////////////////////////////////////////////////

  public static void main(String[] args) {
    long begin = System.currentTimeMillis();
    int n = 500000;
    APP.enabled = true;
    for (int i = 0; i < n; i++) {
      APP.begin();
      APP.end();
    }
    long end = System.currentTimeMillis();
    System.out.println(((double) end - begin) / 2 / n);
  }
}
