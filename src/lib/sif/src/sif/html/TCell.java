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
package sif.html;

import sif.servlet.HTMLWriter;
import fabric.lang.security.Label;

/**
 * A table cell.
 */
public final class TCell extends BlockContainer {
    private final boolean header;
    private final int colspan;
    public TCell(Label L, Label E, Label cL,Label  cE, Node n) {
        this(L, E, null, cL, cE, n, 1, false);
    }
    public TCell(Label L, Label E, String class_, Label cL, Label cE, Node n) {
        this(L, E, class_, cL, cE, n, 1, false);
    }
    public TCell(Label L, Label E, String class_, Label cL, Label cE, Node n, int colspan_, boolean header_) {
        super(L, E, header_ ? "th" : "td", class_, cL, cE, n);
        colspan = colspan_;
        header = header_;
    }
    public TCell(Label L, Label E, Node n) {
        this(L, E, null, L, E, n, 1, false);
    }
    public TCell(Label L, Label E, String class_, Node n) {
        this(L, E, class_, L, E, n, 1, false);
    }
    public TCell(Label L, Label E, String class_, Node n, int colspan_, boolean header_) {
        super(L, E, header_ ? "th" : "td", class_, L, E, n);
        colspan = colspan_;
        header = header_;
    }
    final boolean isHeader() {
        return header;
    }
    
    void writeOptions(HTMLWriter p) {
        if (colspan != 1) {
            //p.unifiedBreak(p.getLevel());
            p.print(" colspan=");
            p.print(colspan);
        }
    }

    public static boolean jif$Instanceof(Label l, Label e, Object o) {
	return (o instanceof TCell) && Node.jif$Instanceof(l, e, o);
    }

    public static TCell jif$cast$sif_html_TCell(Label l, Label e, Object o) {
        if (o == null) return null; 
	if (jif$Instanceof(l, e, o))
	    return (TCell)o;
	throw new ClassCastException();
    }
}
