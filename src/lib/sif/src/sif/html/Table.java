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
package sif.html;

import sif.servlet.HTMLWriter;
import fabric.lang.security.Label;

/** An HTML table. */
public final class Table extends Tag {
    private final Node head, body;
    private final int cell_spacing; // no way to do this in a style file!
    private final boolean have_cell_spacing;
/*
    public Table(String{L} class_,
            label hL, label hE, label bL, label bE,
            Node[hL,hE]{L} h, Node[bL,bE]{L} b, int{L} cellSpacing)
    where {*hL;*bL} <= L, {E; h} <= {*hE}, {E; b} <= {*bE} {
        super("table", class_);
        cell_spacing = cellSpacing;
        have_cell_spacing = true;
    }
 * 
 */
    public Table(Label L, Label E, Label hL, Label hE, Label bL, Label bE, Node h, Node b) {
        this(L, E, null, hL, hE, bL, bE, h, b);
    }
    public Table(Label L, Label E, Label hL, Label hE, Node h, Label bL, Label bE, Node b) {
        this(L, E, null, hL, hE, bL, bE, h, b);
    }
    public Table(Label L, Label E, String class_, Label hL, Label hE, Label bL, Label bE, Node h, Node b) {
        super(L, E, "table", class_);
        head = h;
        body = b;
        cell_spacing = 0;
        have_cell_spacing = false;
    }
    public Table(Label L, Label E, String class_, Label hL, Label hE, Node h, Label bL, Label bE, Node b) {
        super(L, E, "table", class_);
        head = h;
        body = b;
        cell_spacing = 0;
        have_cell_spacing = false;
    }
    public Table(Label L, Label E, Label bL, Label bE, Node b) {
        this(L, E, null, bL, bE, bL, bE, null, b);
    }
    public Table(Label L, Label E, String class_, Node h, Node b, int cellSpacing) {
        super(L, E, "table", class_);
        head = h;
        body = b;
        cell_spacing = cellSpacing;
        have_cell_spacing = true;
    }
    public Table(Label L, Label E, Node h, Node b) {
        this(L, E, null, L, E, L, E, h, b);
    }
    public Table(Label L, Label E, String class_, Node h, Node b) {
        super(L, E, "table", class_);
        head = h;
        body = b;
        cell_spacing = 0;
        have_cell_spacing = false;
    }
    public Table(Label L, Label E, Node b) {
        this(L, E, null, L, E, L, E, null, b);
    }


    void writeOptions(HTMLWriter p) {
        if (have_cell_spacing) {
            p.print(" cellspacing=");
            p.printq(cell_spacing);
        }
    }

    void writeRest(HTMLWriter p) {
        if (head != null) {
            p.begin(2);
            p.print("<thead>");
            p.unifiedBreak(1);
            head.write(p, this);
            p.end();
            p.unifiedBreak(1);
            p.print("</thead>");
            p.unifiedBreak(1);
        }
        p.begin(2);
        p.print("<tbody>");
        p.unifiedBreak(1);
        if (body != null) body.write(p, this);        
        p.end();
        if (body != null) p.unifiedBreak(1);
        p.print("</tbody>");
    }

    public static boolean jif$Instanceof(Label l, Label e, Object o) {
        return (o instanceof Table) && Node.jif$Instanceof(l, e, o);
    }

    public static Table jif$cast$sif_html_Table(Label l, Label e, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(l, e, o))
            return (Table)o;
        throw new ClassCastException();
    }
}
