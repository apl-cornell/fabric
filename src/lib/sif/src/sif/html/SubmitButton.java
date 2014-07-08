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
import sif.servlet.Servlet;
import fabric.lang.security.Label;
import fabric.lang.security.Principal;

/**
 * @author andru
 *
 * A button that submits a form.
 */
public final class SubmitButton extends InputNode {
    private final String name;

    public SubmitButton(Principal servletP, Label L, Label E, Input input, String name_) {
        super(servletP, L, E, input);
        name = name_;
    }
    public SubmitButton(Principal servletP, Label L, Label E, Servlet s, Label inputLabel, String name_) {
        super(servletP, L, E, new Input(servletP, s, inputLabel));
        name = name_;
    }
    /* (non-Javadoc)
     * @see servlet.Node#write(servlet.HTMLWriter)
     */
    void writeImpl(HTMLWriter p) {
        p.print("<input ");
        p.begin();
        p.print("type=submit class=submit value=");
        p.printq(name);
        p.print(" name=");
        p.printq(input.getName());
        p.end();
        p.print(" />");
    }

    public static boolean jif$Instanceof(Principal P, Label l, Label e, Object o) {
        return ((o instanceof SubmitButton) && InputNode.jif$Instanceof(P, l, e, o));
    }

    public static SubmitButton jif$cast$sif_html_SubmitButton(Principal P, Label l, Label e, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(P, l, e, o))
            return (SubmitButton)o;
        throw new ClassCastException();
    }

}
