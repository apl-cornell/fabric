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
 * Preformatted text, like <pre>example</example>
 *
 * @author andru
 *
 **/
public final class Pre extends Container {
    public Pre(Label L, Label E, Label cL, Label cE, Node n) {
        super(L, E, "pre", cL, cE, n);
    }
    public Pre(Label L, Label E, String text) {
        super(L, E, "pre", L, E, new Text(L, E, text));
    }
    void writeImpl(HTMLWriter p) {
        p.print("<" + getTag() + ">");
        p.noindent(true);
        if (contents != null) contents.write(p, this);
        p.noindent(false);
        //p.breakLine();
        p.print("</" + getTag() + ">");
        //p.breakLine();
    }

    public static boolean jif$Instanceof(Label l, Label e, Object o) {
	return (o instanceof Pre) && Node.jif$Instanceof(l, e, o);
    }

    public static Pre jif$cast$sif_html_Pre(Label l, Label e, Object o) {
        if (o == null) return null; 
	if (jif$Instanceof(l, e, o))
	    return (Pre)o;
	throw new ClassCastException();
    }
}
