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

import java.io.*;
import java.util.*;

import sif.html.*;

import fabric.lang.security.*;

/**
 * Just prints stuff directly.
 * @author schong
 */
public class StandardHTMLWriter extends HTMLWriter  {
    private final Request request;
    
    /**
     * Map from ActionNodePairs to Strings
     */
    private final Map<ActionNodePair, String> actionNames = new HashMap<ActionNodePair, String>();
        
    /** The set of input names used so far. Each input must have
     * a unique name, ensuring that the program doesn't receive
     * data on one input that was intended for another input with
     * a different label. 
     * */
    private Set<String> inputNames = new HashSet<String>();

    public StandardHTMLWriter(Request req, PrintWriter p, int lineWidth, String colorCoding) {
        super(p, lineWidth, colorCoding);
        this.request = req;
    }
           
    public StandardHTMLWriter(Request req, PrintWriter rw, String colorCoding) {
        super(rw, colorCoding);
        this.request = req;
    }


    public Request getRequest() { return this.request; }
        
    protected void addInput(Input in) {
        String n = in.getName();
        if (inputNames.contains(n))
            throw new IllegalArgumentException("Duplicate input name: " + n);
        inputNames.add(n);
    }

    public void addAction(Action action, Node n) {
        Label level = n.getE();
        if (Servlet.debug(1)) {
            Servlet.DEBUG.println("*** HTMLWriter: adding action " + action.getExtName() + " at level " + level);
        }
        String name = request.servlet.addAction(action, level, request);
        actionNames.put(new ActionNodePair(action, n), name);
    }


    public void printServletURL() {
        print(escape_URI(getRequest().servletURL()));
    }


    public void printActionName(Action action, Node n) {
        String name = lookupActionName(action, n);
        print(name);
        
    }


    public void printActionURL(Action action, Node n) {
        String name = lookupActionName(action, n);
        print(escape_URI(getRequest().servletURL()+"?action="+name));        
    }


    public void printHyperlinkRequestURL(Action action, HyperlinkRequest n) {
        String name = lookupActionName(action, n);
        print(escape_URI(getRequest().servlet.createRequestURL(name, n.getInputs(), getRequest())));        
    }


    private String lookupActionName(Action action, Node n) {
        return actionNames.get(new ActionNodePair(action, n));
    }
}
 
