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
import fabric.lang.security.Principal;
import fabric.lang.security.Label;
import fabric.lang.security.PrincipalUtil;


/**
 * @author andru
 *
 * An Input node is one that accepts user input. It
 * must occur within a form. There are several kinds
 * of Input nodes. Every input is associated with a
 * servlet. Input nodes should result in HTML decorations
 * that describe confidentiality requirements.
 * XXX Currently this last is not implemented.
 */
public abstract class InputNode extends Node {
    public final Input input;
    private final Principal servletP;
    
    InputNode(Principal servletP, Label L, Label E, Input i) {
        super(L, E);
        this.servletP = servletP;
        input = i;
    }
    InputNode(Principal servletP, Label L, Label E, Servlet s, Label inputLabel) {
        this(servletP, L, E, new Input(servletP, s, inputLabel));
    }
    
    public static boolean jif$Instanceof(Principal P, Label l, Label e, Object o) {
        if ((o instanceof InputNode) && Node.jif$Instanceof(l, e, o)) {
            InputNode that = (InputNode)o;
            return PrincipalUtil._Impl.equivalentTo(that.servletP, P);
        }
        return false;
    }

    public static InputNode jif$cast$sif_html_InputNode(Principal P, Label l, Label e, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(P, l, e, o))
            return (InputNode)o;
        throw new ClassCastException();
    }
    
}
