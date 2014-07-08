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

import sif.servlet.Action;
import sif.servlet.HTMLWriter;
import fabric.lang.security.Label;
import fabric.lang.security.Principal;
import fabric.lang.security.PrincipalUtil;

/**
 *
 * A Hyperlink Request -- an "a href=....?p=v" tag.
 */
public final class HyperlinkRequest extends Hyperlink {
    final jif.util.Map inputs;
    public HyperlinkRequest(Principal servletP, Label L, Label E, Action a, jif.util.Map inputs, Label cL, Label cE, Node n) {
        super(servletP, L, E, a, cL, cE, n);
        this.inputs = inputs;
    }
    void makeURLString(HTMLWriter p) {
        p.addAction(this.action, this);
        p.printHyperlinkRequestURL(this.action, this); 
    }
    
    public jif.util.Map getInputs() {
        return inputs;
    }
    public static boolean jif$Instanceof(Principal P, Label l, Label e, Object o) {
        if ((o instanceof HyperlinkRequest) && Node.jif$Instanceof(l, e, o)) {
            HyperlinkRequest that = (HyperlinkRequest)o;
            return PrincipalUtil._Impl.equivalentTo(that.servletP, P);
        }
        return false;
    }
    
    public static HyperlinkRequest jif$cast$sif_html_HyperlinkRequest(Principal P, Label l, Label e, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(P, l, e, o))
            return (HyperlinkRequest)o;
        throw new ClassCastException();
    }    
}
