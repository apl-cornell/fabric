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
package sif.html;

import sif.servlet.HTMLWriter;
import sif.servlet.Servlet;
import fabric.lang.security.Label;

/**
 * A Page is an entire HTML page.
 * @author andru
 */
public final class Page extends Node {
    private final Head head;
    private final Body body;
    
    
    public Page(Label L, Label E, Label cL, Label cE, Head h, Body b) {
        super(L, E);
        head = h;
        body = b;
    }
    
    void writeImpl(HTMLWriter p) {
        if (Servlet.debug(2)) {
            Servlet.DEBUG.println("*** starting to write Page");           
        }
        p.begin(2);
        p.print("<html>"); 
        p.breakLine();
        if (head != null) head.write(p, this);
        p.breakLine();
        if (body != null) body.write(p, this);
        p.end();
        p.breakLine();
        p.print("</html>");     
        //p.breakLine();
        if (Servlet.debug(2)) {
            Servlet.DEBUG.println("*** finished writing Page");           
        }
    }
    public static boolean jif$Instanceof(Label l, Label e, Object o) {
        return (o instanceof Page) && Node.jif$Instanceof(l, e, o);
    }
    
    public static Page jif$cast$sif_html_Page(Label l, Label e, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(l, e, o))
            return (Page)o;
        throw new ClassCastException();
    }
    
}
