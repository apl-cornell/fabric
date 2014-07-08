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

import sif.servlet.HTMLWriter;
import fabric.lang.security.Label;
import fabric.lang.security.LabelUtil;

/** A Node represents part of an HTML document.
 *  It can be written out to an HTMLWriter in a
 *  somewhat pretty-printed style.
 **/
public abstract class Node extends java.lang.Object {        
    final Label L;
    final Label E;
    
    Node(Label L, Label E) {
        this.L = L;
        this.E = E;
    }
    
    /** Write out HTML for this node to p. */
    public final void write(HTMLWriter p, Node parent) {
        p.enterNode(parent, this);
        this.writeImpl(p);
        p.leaveNode(parent, this);
    }

    /** Write out HTML for this node to p. */
    abstract void writeImpl(HTMLWriter p);
    
    public final Label getL() { return L; }
    public final Label getE() { return E; }
    
    public static boolean jif$Instanceof(Label l, Label e, Object o) {
        if (o instanceof Node) {
            Node that = (Node)o;
            return LabelUtil._Impl.equivalentTo(that.L, l) && LabelUtil._Impl.equivalentTo(that.E, e);
        }
        return false;
    }
    
    public static Node jif$cast$sif_html_Node(Label l, Label e, Object o) {
        if (o == null) return (Node)null;
        if (jif$Instanceof(l, e, o))
            return (Node)o;
        throw new ClassCastException();
    }
    public String toString() {
        return this.getClass().getName();
    }
}
