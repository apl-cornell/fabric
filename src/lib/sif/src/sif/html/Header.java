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


public final class Header extends BlockContainer {
    private final int level;

    public Header(Label L, Label E, int level, Label cL, Label cE, Node contents) {
        super(L, E, "h"+level, cL, cE, contents);
        if (level < 1 || level > 5) {
            throw new IllegalArgumentException();
        }
        this.level = level;
    }
    
    /**
     * @param i
     * @param header
     */
    public Header(Label L, Label E, int i, String header) {
        this(L, E, i, L, E, new Text(L, E, header));
    }

    public static boolean jif$Instanceof(Label l, Label e, Object o) {
	return (o instanceof Header) && Node.jif$Instanceof(l, e, o);
    }

    public static Header jif$cast$sif_html_Header(Label l, Label e, Object o) {
        if (o == null) return null; 
	if (jif$Instanceof(l, e, o))
	    return (Header)o;
	throw new ClassCastException();
    }
}
