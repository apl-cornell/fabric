/**
 * Copyright (C) 2010-2013 Fabric project group, Cornell University
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
 * A Paragraph is an HTML paragraph.
 */
public final class Paragraph extends BlockContainer {
    public Paragraph(Label L, Label E, Label cL, Label cE, Node n) {
        super(L, E, "p", cL, cE, n);
    }
    public Paragraph(Label L, Label E, Node n) {
        super(L, E, "p", L, E, n);
    }
    public Paragraph(Label L, Label E, String text) {
        super(L, E, "p", L, E, new Text(L, E, text));
    }
    boolean isBigContainer() {
        return true;
    }

    public static boolean jif$Instanceof(Label l, Label e, Object o) {
	return (o instanceof Paragraph) && Node.jif$Instanceof(l, e, o);
    }

    public static Paragraph jif$cast$sif_html_Paragraph(Label l, Label e, Object o) {
        if (o == null) return null; 
	if (jif$Instanceof(l, e, o))
	    return (Paragraph)o;
	throw new ClassCastException();
    }
}
