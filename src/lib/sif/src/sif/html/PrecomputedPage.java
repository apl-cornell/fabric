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

import java.io.*;
import java.util.*;

import sif.servlet.*;
import sif.servlet.HTMLWriter.LevelStack;

import fabric.lang.security.Label;
import fabric.lang.security.LabelUtil;

public class PrecomputedPage {	    
    final Label L;

    /**
     * A list of char[].
     */
    private List<char[]> precomputedOutput;
    
    /**
     * A list describing how the spaces between the char[]s of
     * precomputedOutput should fit.
     */
    private List<Object> args;
    
    /**
     * The ActionNodePairs that are added by the page.
     */
    private Set<ActionNodePair> addedActions;

    private Map<ActionNodePair, Label> actionLevels;

    private CharArrayWriter caw;
    
    public PrecomputedPage(Label L, Label E, Node body) {
        super();
        this.L = L;
        
        // pre-render the HTML for the page, using our own special HTMLWriter
        precomputedOutput = new ArrayList<char[]>();
        args = new ArrayList<Object>();
        
        // precompute the output of the page body
        caw = new CharArrayWriter(1024);
        HTMLPrecomputerWriter w = new HTMLPrecomputerWriter(this, caw);     
        body.write(w, null); 
        
        
        try {
            w.close();
        }
        catch (IOException e) {
            e.printStackTrace(Servlet.DEBUG);
        }
        // add last block
        addOutput();
        
        // size of precomputedOutput is now one more than size of args
        
        addedActions = w.addedActions;
        actionLevels = w.actionLevels;
    }
    
    private void addOutput() {
        caw.flush();
        char[] output = caw.toCharArray();
        caw.reset();
        precomputedOutput.add(output);
    }

    public void addHolder(Holder holder) {
        addOutput();
        args.add(holder);        
    }
    public void addPlaceholder(HTMLWriter p) {
        addOutput();
        args.add(new HolderNode(p.currentLevelStack()));        
    }

    public static boolean jif$Instanceof(Label l, Label e, Object o) {
        if (o instanceof PrecomputedPage) {
            PrecomputedPage that = (PrecomputedPage)o;
            return LabelUtil._Impl.equivalentTo(that.L, l);
        }
        return false;
    }

    public static PrecomputedPage jif$cast$sif_html_Placeholder(Label l, Label e, Object o) {
        if (o == null) return (PrecomputedPage)null;
	if (jif$Instanceof(l, e, o))
	    return (PrecomputedPage)o;
	throw new ClassCastException();
    }
        
    static final int HOLDER_SERVLET_URL = 0;
    static final int HOLDER_ACTION_NAME = 1;
    static final int HOLDER_ACTION_URL = 2;
    static final int HOLDER_HYPERLINK_REQ = 3;
    
    private static class HolderNode {
        public final LevelStack stack;

        public HolderNode(LevelStack stack) {
            this.stack = stack;
        }
        
    }

    /**
     * Output the page to the writer.
     */
    public void write(Request req, PrintWriter w, NodeList precomputedPageArgs, Head precomputedPageHead) {
        // add all the added actions, and store the names generated.
        Map<ActionNodePair, String> actionNames = new HashMap<ActionNodePair, String>();
        for (Iterator<ActionNodePair> iter = addedActions.iterator(); iter.hasNext(); ) {
            ActionNodePair anp = iter.next();
            String name = req.servlet.addAction(anp.a, actionLevels.get(anp), req);
            actionNames.put(anp, name);            
        }
        

        w.println("<html>");
        if (precomputedPageHead != null) {
            HTMLWriter hw = new StandardHTMLWriter(req, w, null);
            precomputedPageHead.write(hw, null);
            try {
                hw.flush();
            }
            catch (IOException e) {
                e.printStackTrace(Servlet.DEBUG);
            }
        }
        w.println("<body>");
        
        Iterator<char[]> text = precomputedOutput.iterator();
        Iterator<Object> holders = args.iterator();        
        int index = 0;
        
        while (text.hasNext() && holders.hasNext()) {
            // print some text
            w.print(text.next());
            
            // look at the holders and figure out what to do
            Object o = holders.next();
            if (o instanceof HolderNode) {
                // output a node from arg.
                HolderNode hn = (HolderNode)o;

                HTMLWriter hw = new StandardHTMLWriter(req, w, null);
                hw.setLevelStack(hn.stack);
                Node n = null;
                try {
                    n = precomputedPageArgs.children[index++];
                    if (n != null) {
                        n.write(hw, null);
                    }
                    hw.flush();
                }
                catch (ArrayIndexOutOfBoundsException ignore) { }
                catch (NullPointerException ignore) { }
                catch (IOException e) {
                    e.printStackTrace(Servlet.DEBUG);
                }
            }
            else {
                Holder h = (Holder)o;
                switch (h.kind) {
                case HOLDER_ACTION_NAME:
                    w.print(actionNames.get(h.p));
                    break;
                case HOLDER_ACTION_URL:
                    w.print(HTMLWriter.escape_URI(req.servletURL()+"?action="+actionNames.get(h.p)));        
                    break;
                case HOLDER_SERVLET_URL:
                    w.print(HTMLWriter.escape_URI(req.servletURL()));
                    break;
                case HOLDER_HYPERLINK_REQ:
                    w.print(HTMLWriter.escape_URI(req.servlet.createRequestURL(actionNames.get(h.p), ((HyperlinkRequest)h.p.n).getInputs(), req)));        
                    break;
                }
            }
        }
        w.print(text.next());
        w.println("</body>");
        w.println("</html>");

        
    }

}

class HTMLPrecomputerWriter extends HTMLWriter {
    Set<ActionNodePair> addedActions = new HashSet<ActionNodePair>();
    Map<ActionNodePair, Label> actionLevels = new HashMap<ActionNodePair, Label>();
    Set<Input> addedInputs = new HashSet<Input>();
    private PrecomputedPage precomputer;
    public HTMLPrecomputerWriter(PrecomputedPage precomputer, Writer out) {
        super(new PrintWriter(out), null);
        this.precomputer = precomputer;
    }    
    
    public void addAction(Action action, Node n) {
        if (Servlet.debug(1)) {
            Servlet.DEBUG.println("*** HTMLWriter: adding action " + action.getName() + " at level " + n.getE());
        }
        ActionNodePair anp = new ActionNodePair(action, n);
        addedActions.add(anp);
        actionLevels.put(anp, n.getE());
    }
    
    protected void addInput(Input n) {
        addedInputs.add(n); // XXX do I do anything with these? 
    }
    
    public void printServletURL() {
        try {
            cw.flush();
        }
        catch (IOException e) {
            e.printStackTrace(Servlet.DEBUG);
        }
        precomputer.addHolder(new Holder(PrecomputedPage.HOLDER_SERVLET_URL, null));
    }
    
    public void printActionName(Action action, Node n) {
        try {
            cw.flush();
        }
        catch (IOException e) {
            e.printStackTrace(Servlet.DEBUG);
        }
        precomputer.addHolder(new Holder(PrecomputedPage.HOLDER_ACTION_NAME, new ActionNodePair(action, n)));
    }
    
    public void printActionURL(Action action, Node n) {
        try {
            cw.flush();
        }
        catch (IOException e) {
            e.printStackTrace(Servlet.DEBUG);
        }
        precomputer.addHolder(new Holder(PrecomputedPage.HOLDER_ACTION_URL, new ActionNodePair(action, n)));
    }
    
    public void printHyperlinkRequestURL(Action action, HyperlinkRequest n) {
        try {
            cw.flush();
        }
        catch (IOException e) {
            e.printStackTrace(Servlet.DEBUG);
        }
        precomputer.addHolder(new Holder(PrecomputedPage.HOLDER_HYPERLINK_REQ, new ActionNodePair(action, n)));
    }
    
    public void addPlaceholderNode(HTMLWriter p) {
        try {
            cw.flush();
        }
        catch (IOException e) {
            e.printStackTrace(Servlet.DEBUG);
        }
        precomputer.addPlaceholder(p);            
    }
}

class Holder {
    public int kind;
    public final ActionNodePair p;
    Holder(int kind, ActionNodePair p) {
        this.p = p;
        this.kind = kind;
    }
}
