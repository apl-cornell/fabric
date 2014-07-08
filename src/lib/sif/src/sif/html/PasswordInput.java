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

import sif.servlet.Servlet;
import fabric.lang.security.Label;
import fabric.lang.security.Principal;

/** An input that allows typing in a password. */
public final class PasswordInput extends TextInput {
    public PasswordInput(Principal servletP, Label L, Label E, Servlet s, Label inputLabel, int size, String initial) {
        super(servletP, L, E, s, inputLabel, size, initial);
    }
    public PasswordInput(Principal servletP, Label L, Label E, Input input, int size, String initial) {
        super(servletP, L, E, input, size, initial);
    }
    String typeName() {return "password";}

    public static boolean jif$Instanceof(Principal P, Label l, Label e, Object o) {
        return ((o instanceof PasswordInput) && InputNode.jif$Instanceof(P, l, e, o));
    }

    public static PasswordInput jif$cast$sif_html_PasswordInput(Principal P, Label l, Label e, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(P, l, e, o))
            return (PasswordInput)o;
        throw new ClassCastException();
    }
    
}
