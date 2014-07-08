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
import fabric.lang.security.Label;

/**
 * 
 */
public final class Class extends Node {
    private final String class_;
    private final Node node;
    
    public Class(Label L, Label E, String c, Label cL, Label cE, Node n) {
        super(L, E);
        class_ = c;
        node = n;
    }
    /* (non-Javadoc)
     * @see servlet.Node#write(servlet.HTMLWriter)
     */
    void writeImpl(HTMLWriter p) {
        p.setClass(class_);
        if (node != null) node.write(p, this);
        p.setClass(null);		
    }
    public static boolean jif$Instanceof(Label l, Label e, Object o) {
        return (o instanceof Class) && Node.jif$Instanceof(l, e, o);
    }
    
    public static Class jif$cast$sif_html_Class(Label l, Label e, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(l, e, o))
            return (Class)o;
        throw new ClassCastException();
    }
    
}
