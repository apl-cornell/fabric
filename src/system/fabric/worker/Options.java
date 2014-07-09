/**
 * Copyright (C) 2010-2014 Fabric project group, Cornell University
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
package fabric.worker;

import java.io.PrintStream;
import java.util.Set;

import fabric.common.Options.Flag.Kind;
import fabric.common.Resources;
import fabric.common.Timing;
import fabric.common.exceptions.UsageError;

public class Options extends fabric.common.Options {
  /**
   * The worker shell command to run.
   */
  public String[] cmd;

  /**
   * This worker's name.
   */
  public String name;

  /**
   * Whether the worker should stay running after executing the commands
   * specified on the command line.
   */
  protected boolean keepOpen;

  /**
   * Whether to have an interactive shell. A non-interactive shell this is
   * useful when the worker is started with a disconnected stdin.
   */
  protected boolean interactiveShell;

  private Options() {
  }

  public Options(String[] args) throws UsageError {
    super(args);
  }

  static void printUsage(PrintStream out, boolean showSecretMenu) {
    new Options().usage(out, showSecretMenu);
  }

  @Override
  protected void populateFlags(Set<Flag> flags) {
    flags.add(new Flag("--name", null, "this worker's name", "$HOSTNAME") {
      @Override
      public int handle(String[] args, int index) {
        Options.this.name = args[index];
        return index + 1;
      }
    });

    flags.add(new Flag("--keep-open", null,
        "keep the worker running after executing CMD") {
      @Override
      public int handle(String[] args, int index) {
        Options.this.keepOpen = true;
        return index;
      }
    });

    flags.add(new Flag("--no-shell", null, "disable the worker shell. This is "
        + "useful when the worker is started with a disconnected stdin.") {
      @Override
      public int handle(String[] args, int index) {
        Options.this.interactiveShell = false;
        return index;
      }
    });

    flags.add(new Flag(Kind.DEBUG, "--time", "<category>",
        "enable timing of category") {
      @Override
      public int handle(String[] args, int index) throws UsageError {
        if (index >= args.length) {
          System.err.println(timeUsage());
          throw new UsageError("Invalid timing category");
        }

        for (Timing t : Timing.values()) {
          if (t.name().equalsIgnoreCase(args[index])) {
            t.enabled = true;
            return index + 1;
          }
        }
        if (args[index].equalsIgnoreCase("all")) {
          for (Timing t : Timing.values())
            t.enabled = true;
          return index + 1;
        }

        System.err.println(timeUsage());
        throw new UsageError("Invalid timing category");
      }
    });
  }

  @Override
  public void setDefaultValues() {
    this.name = System.getenv("HOSTNAME");
    this.cmd = null;
    this.keepOpen = false;
    this.interactiveShell = true;
    // Default codeCache is set in validateOptions because it depends on name.
  }

  @Override
  public void validateOptions() throws UsageError {
    if (null == this.name) throw new UsageError("No worker name specified");

    // Default codeCache is set here because it depends on name.
    if (null == this.codeCache) {
      this.codeCache = Resources.relpathRewrite("var", "cache", name);
    }
  }

  @Override
  public void usageHeader(PrintStream out) {
    out.println("Usage: fab [options] [cmd...]");
    out.println("where");
    out.println("  [cmd...] is a command for the worker shell to execute");
    out.println("and [options] includes:");
  }

  private String timeUsage() {
    StringBuffer message =
        new StringBuffer("possible categories for --time:\n");
    message.append("all");
    for (Timing t : Timing.values()) {
      message.append(", ");
      message.append(t.name().toLowerCase());
    }
    return message.toString();
  }

  @Override
  protected int defaultHandler(String[] args, int index) {
    this.cmd = new String[args.length - index];
    for (int idx = index; idx < args.length; idx++)
      this.cmd[idx - index] = args[idx];

    return args.length;
  }
}
