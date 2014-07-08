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
 * A Image -- an "img src=" tag.
 */
public class Img extends Tag {
    private final String url;
    
    public Img(Label L, Label E, String url_) {
        super(L, E, "img", null);
        this.url = url_;
    }
    public Img(Label L, Label E, String url_, String c) {
        super(L, E, "img", c);
        this.url = url_;
    }


    void writeOptions(HTMLWriter p) {
        p.print(" src=");
        p.print("\"");
        p.print(HTMLWriter.escape_URI(url));
        p.print("\"");
    }
    
    public static boolean jif$Instanceof(Label l, Label e, Object o) {
        return ((o instanceof Img) && Node.jif$Instanceof(l, e, o));
    }
    
    public static Img jif$cast$sif_html_Hyperlink(Label l, Label e, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(l, e, o))
            return (Img)o;
        throw new ClassCastException();
    }    
}
