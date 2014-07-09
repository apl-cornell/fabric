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
package sif.servlet;

import javax.servlet.ServletException;

import fabric.lang.security.Label;

/**
 * @author andru
 *
 * An Action represents some servlet behavior to be triggered
 * by a user request. An action expects to receive some
 * parameters from the request.
 * 
 * Servlets create subclasses of action to provide
 * request handling.
 * 
 * XXX Actions need some notion of timing out so we can throw them
 * away eventually.
 */
abstract public class Action {
    private Name name;
    private String ext_name;
    private Servlet servlet;
    //public final Date sunset;     // when this action expires
    
    // need a default constructor for the Jif subclasses to use.
    protected Action() {  }
    private boolean fieldsInit = false;
    protected final void initFields(Servlet s) {
        if (fieldsInit) {
            throw new RuntimeException("Fields already init");
        }
        fieldsInit = true;
        name = s.generateName();
        ext_name = name.toHex();
        servlet = s;
    }
    protected final void initFields(String n, Servlet s) {
        if (fieldsInit) {
            throw new RuntimeException("Fields already init");
        }
        fieldsInit = true;
        name = null;
        ext_name = n;
        servlet = s;        
    }
    
    public Action(Servlet s) {
        initFields(s);
    }
    public Action(String n, Servlet s) {
        initFields(n, s);
    }
    
    abstract public void invoke(Label lbl, Request req) throws ServletException;
    
    public String getName() {
        if (name == null) return ext_name;
        return name.toHex();
    }

    String getExtName() {
        return ext_name;
    }
    
    public Servlet getServlet() {
        return servlet;
    }
}
