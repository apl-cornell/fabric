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

import fabric.lang.security.Label;

/**
 * @author andru
 *
 * A <div> node.
 */
public final class Div extends BlockContainer {

    public Div(Label L, Label E, String class_, Label cL, Label cE, Node n) {
        super(L, E, "div", class_, cL, cE, n);
    }
    public Div(Label L, Label E, String class_, Node n) {
        super(L, E, "div", class_, L, E, n);
    }
    boolean isBigContainer() {
        return true;
    }
    

    public static boolean jif$Instanceof(Label l, Label e, Object o) {
	return (o instanceof Div) && Node.jif$Instanceof(l, e, o);
    }

    public static Div jif$cast$sif_html_Div(Label l, Label e, Object o) {
        if (o == null) return null; 
	if (jif$Instanceof(l, e, o))
	    return (Div)o;
	throw new ClassCastException();
    }
}
