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

import sif.servlet.Servlet;
import fabric.lang.security.Principal;
import fabric.lang.security.PrincipalUtil;

/**
 * To create radio buttions, a Radio is needed.
 */
public final class Radio {
    private final String name;
    private final Principal servletP;
    public Radio(Principal servletP, Servlet s) {
        this.servletP = servletP;
        name = s.generateNonce();
    }

    public static boolean jif$Instanceof(Principal P, Object o) {
        if (o instanceof Radio) {
            Radio that = (Radio)o;
            return PrincipalUtil._Impl.equivalentTo(that.servletP, P);
        }
        return false;
    }
    
    public static Radio jif$cast$sif_html_Radio(Principal P, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(P, o))
            return (Radio)o;
        throw new ClassCastException();
    }
    
}
