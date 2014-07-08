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
package fabric.worker.shell;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StreamTokenizer;
import java.util.List;

import jline.ConsoleReader;
import jline.ConsoleReaderInputStream;
import jline.Terminal;
import fabric.worker.Worker;

/**
 * An interactive console-based source of worker-shell commands.
 */
public class InteractiveCommandSource extends StreamCommandSource {

  final PrintStream out;

  static {
    Terminal.setupTerminal();
  }

  public InteractiveCommandSource(Worker worker) throws IOException {
    this(worker, new ConsoleReader(System.in,
        new OutputStreamWriter(System.out)));
  }

  public InteractiveCommandSource(Worker worker, ConsoleReader cr) {
    this(worker, cr, System.out);
  }

  public InteractiveCommandSource(Worker worker, ConsoleReader cr,
      PrintStream out) {
    super(new ConsoleReaderInputStream(cr), false);
    this.out = out;
    cr.setDefaultPrompt("\n" + worker.config.name + "> ");
  }

  @Override
  public List<String> getNextCommand(List<String> buf) {
    List<String> result = super.getNextCommand(buf);
    if (result == null) out.println("exit");
    return result;
  }

  @Override
  protected void handleSyntaxError() throws IOException {
    // Read to end of line.
    int token = StreamTokenizer.TT_WORD;
    while (token != StreamTokenizer.TT_EOF && token != StreamTokenizer.TT_EOL) {
      token = in.nextToken();
    }
  }

  @Override
  public boolean reportError(String message) {
    err.println("Error: " + message);
    return exitOnError;
  }
}
