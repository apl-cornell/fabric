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
import fabric.lang.security.Label;

/**
 * @author andru
 *
 * A <br> line-break element.
 */
public final class Br extends Node {
    public Br(Label L, Label E) {
        super(L, E);
    }

    void writeImpl(HTMLWriter p) {
        //p.allowBreak();
        p.print("<br/>");
        //p.allowBreak(0, 0, "");
    }

    public static boolean jif$Instanceof(Label l, Label e, Object o) {
        return (o instanceof Br) && Node.jif$Instanceof(l, e, o);
    }
    
    public static Br jif$cast$sif_html_Br(Label l, Label e, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(l, e, o))
            return (Br)o;
        throw new ClassCastException();
    }
    
}
