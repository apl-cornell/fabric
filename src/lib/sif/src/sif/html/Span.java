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

import fabric.lang.security.Label;

/**
 * @author andru
 *
 * A span is a way of attaching a CSS class to a
 * bunch of text. This is useful for applying formatting.
 * The assumption is that all formatting will be done
 * through style sheets rather than explicit <b>, <i>, etc.
 */
public class Span extends Container {
    public Span(Label L, Label E, String class_, Label cL, Label cE, Node n) {
        super(L, E, "span", class_, cL, cE, n);
    }
    /** A useful version of the constructor that
     *  automatically constructs a text node.
     * @param class_
     * @param text
     */
    public Span(Label L, Label E, String class_, String text) {
        this(L, E, class_, L, E, new Text(L, E, text));
    }

    public static boolean jif$Instanceof(Label l, Label e, Object o) {
	return (o instanceof Span) && Node.jif$Instanceof(l, e, o);
    }

    public static Span jif$cast$sif_html_Span(Label l, Label e, Object o) {
        if (o == null) return null; 
	if (jif$Instanceof(l, e, o))
	    return (Span)o;
	throw new ClassCastException();
    }
}
