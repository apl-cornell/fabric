/**
 * Copyright (C) 2010 Fabric project group, Cornell University
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
package sif.servlet;

import java.io.PrintWriter;
import java.io.Writer;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.Stack;

/**
 * SimpleCodeWriter is a simple, fast, bulletproof implementation of the
 * CodeWriter interface. However, it does not try very hard to use vertical
 * space optimally or to stay within the horizontal margins. If aesthetically
 * appealing output is desired, use OptimalCodeWriter.
 */
public class SimpleCodeWriter extends CodeWriter {
    PrintWriter output;
    int width;
    int rmargin;
    int lmargin;
    boolean breakAll;
    Stack<State> lmargins;
    int pos;

    public SimpleCodeWriter(OutputStream o, int width_) {
        this(new PrintWriter(new OutputStreamWriter(o)), width_);
    }

    class State {
	int lmargin;
	boolean breakAll;

	State(int m, boolean b) { lmargin = m; b = breakAll; }
    }

    public SimpleCodeWriter(PrintWriter o, int width_) {
        output = o;
        width = width_;
	rmargin = width;
	adjustRmargin();
	breakAll = false;
	pos = 0;
	lmargins = new Stack<State>();
    }

    public SimpleCodeWriter(Writer o, int width_) {
        this(new PrintWriter(o), width_);
    }
    
    private void adjustRmargin() {
	rmargin -= 8;
	if (rmargin < width/2) rmargin = width/2;
    }
        
    public void write(String s) {
       if (s.length() > 0)
	    write(s, s.length());
    }

    public void write(String s, int length) {
	output.print(s);
	pos += length;
    }

    public void begin(int n) {
	lmargins.push(new State(lmargin, breakAll));
	lmargin = pos + n;
    }
        
    public void end() {
	State s = lmargins.pop();
	lmargin = s.lmargin;
	breakAll = s.breakAll;
    }

    public void allowBreak(int n, int level, String alt, int altlen) {
	if (pos > width) adjustRmargin();
	if (breakAll || pos > rmargin) {
	    newline(n);
	    breakAll = true;
	} else {
	    output.print(alt);
	    pos += altlen;
	}
    }
    public void unifiedBreak(int n, int level, String alt, int altlen) {
	allowBreak(n, level, alt, altlen);
    }

    private void spaces(int n) {
	for (int i = 0; i < n; i++) {
	    output.print(' ');
	}
    }
    public void newline() {
	if (pos != lmargin) {
	    output.println();
	    pos = lmargin;
	    spaces(lmargin);
	}
    }
    public void newline(int n) {
	newline();
	spaces(n);
	pos += n;
    }

    public boolean flush() throws IOException {
	output.flush();
	pos = 0;
	return true;
    }

    public boolean flush(boolean format) throws IOException {
	return flush();
    }

    public void close() throws IOException {
	flush();
	output.close();
    }

    /**
     * toString is not really supported by this implementation.
     */
    public String toString() {
	return "<SimpleCodeWriter>";
    }
}

