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
import fabric.lang.security.Principal;

/**
 * @author andru
 *
 * A Hidden is a way to pass data directly to an input
 * without the client having to provide it. */
public final class Hidden extends InputNode {
    private final String data;
    public Hidden(Principal servletP, Label L, Label E, Input i, String data_) {
        super(servletP, L, E, i);
        data = data_;
    }
    public Hidden(Principal servletP, Label L, Label E, String name, String data_) {
        super(servletP, L, E, name);
        data = data_;
    }

    void writeImpl(HTMLWriter p) {
        p.print("<input type=\"hidden\" name=");
        p.printq(getName());
        p.print(" value=");
        p.printq(data);
        p.print("/>");
    }
    
    public static boolean jif$Instanceof(Principal P, Label l, Label e, Object o) {
        return ((o instanceof Hidden) && InputNode.jif$Instanceof(P, l, e, o));
    }

    public static Hidden jif$cast$sif_html_Hidden(Principal P, Label l, Label e, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(P, l, e, o))
            return (Hidden)o;
        throw new ClassCastException();
    }
    
}
