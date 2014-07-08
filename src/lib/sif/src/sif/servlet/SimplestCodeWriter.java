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

/**
 * SimpleCodeWriter is a simple, fast, bulletproof implementation of the
 * CodeWriter interface. However, it does not try very hard to use vertical
 * space optimally or to stay within the horizontal margins. If aesthetically
 * appealing output is desired, use OptimalCodeWriter.
 */
public class SimplestCodeWriter extends CodeWriter {
    PrintWriter output;
    final int width;
    final int rmargin;
    int pos;
    
    public SimplestCodeWriter(OutputStream o, int width_) {
        this(new PrintWriter(new OutputStreamWriter(o)), width_);
    }
    
    
    public SimplestCodeWriter(PrintWriter o, int width_) {
        output = o;
        width = width_;
        int rm = width;
        rm -= 8;
        if (rm < width/2) rm = width/2;
        
        rmargin = rm;
        pos = 0;
    }
    
    public SimplestCodeWriter(Writer o, int width_) {
        this(new PrintWriter(o), width_);
    }
    
    public void write(String s) {
        int len = s.length();
        if (len > 0)
            write(s, len);
    }
    
    public void write(String s, int length) {
        output.print(s);
        pos += length;
    }
    
    public void begin(int n) {
    }
    
    public void end() {
    }
    
    public void allowBreak(int n, int level, String alt, int altlen) {
        if (pos > rmargin) {
            newline(n);
        } else {
            output.print(alt);
            pos += altlen;
        }
    }
    public void unifiedBreak(int n, int level, String alt, int altlen) {
        allowBreak(n, level, alt, altlen);
    }
    
    public void newline() {
        if (pos > 0) {
            output.println();
            pos = 0;
        }
    }
    public void newline(int n) {
        newline();
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
    
    
    public String toString() {
        return "SimplestCodeWriter";
    }
}

