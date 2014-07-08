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
import fabric.lang.security.Principal;
import fabric.lang.security.PrincipalUtil;
import sif.servlet.Servlet;

/**
 * @author andru
 *
 */
public final class Input {
    private final String name;
    public final Label inputLbl;
//    public final Label thisLabel;
    final Servlet servlet;
    private final Principal servletP;
    
    /** Construct an input with an automatically generated name. */
    public Input(Principal servletP, Servlet s, Label inputLabel) {
        this.inputLbl = inputLabel;
//        this.thisLabel = thisLabel;
        this.servlet = s;
        this.servletP = servletP;
    	this.name = "i" + s.generateNonce();
    }
    
    /** 
     * Construct an input with a given name. No two inputs in the same servlet
     * may have the same name. Throws an error if they do.
     * @param s the servlet
     * @param n the name of the input
     */
    public Input(Principal servletP, String n, Servlet s, Label inputLabel) {
        this.inputLbl = inputLabel;
//        this.thisLabel = thisLabel;
        this.servlet = s;
        this.servletP = servletP;
        this.name = n;
        if (s == null) throw new Error("Illegal servlet passed in");
        s.addNamedInput(this);
    }
    
    /** @return The name of the input. */
    public String getName() {
        return name;
    }
    
    public int hashCode() {
        return name.hashCode();
    }
    public static boolean jif$Instanceof(fabric.lang.security.Principal jif$servletP, Object o) {
        if (o instanceof Input) {
            Input that = (Input)o;
            return PrincipalUtil._Impl.equivalentTo(that.servletP, jif$servletP);
        }
        return false;
    }
          
    public static Input jif$cast$sif_html_Input(fabric.lang.security.Principal jif$servletP, Object o) {
        if (o == null) return (Input)null;
        if (jif$Instanceof(jif$servletP, o))
            return (Input)o;
        throw new ClassCastException();
        
    }
    
}
