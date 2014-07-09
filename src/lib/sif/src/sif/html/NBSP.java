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

import fabric.lang.security.Label;

/**
 * A non-breaking space.
 */
public final class NBSP extends SpecialChar {
    public NBSP(Label L, Label E) {
        super(L, E, "nbsp");
    }
    public static boolean jif$Instanceof(Label l, Label e, Object o) {
        return (o instanceof NBSP) && Node.jif$Instanceof(l, e, o);
    }
    
    public static NBSP jif$cast$sif_html_NBSP(Label l, Label e, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(l, e, o))
            return (NBSP)o;
        throw new ClassCastException();
    }        
}
