package jif.types;

import java.util.LinkedHashMap;
import java.util.Map;

import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.util.Position;

/** A Jif label with names for the debuging use. 
 */
public class NamedLabel
{
    protected Map nameToLabels;
    protected Map nameToDescrip;
    protected String totalName;

    protected Label label;
    protected Position pos;
    
    public NamedLabel() {
        this(null);
    }
    
    public NamedLabel(String name, Label l) {
        this(name, null, l);
    }
    
    public NamedLabel(String name, String descrip, Label l) {
        this(l.position());
        nameToLabels.put(name, l);
        if (descrip != null) {
            nameToDescrip.put(name, descrip);
        }
        
        label = l;
        if (pos == null) pos = l.position();
        this.totalName = name;
    }

    public NamedLabel(Position pos) {
        this.nameToLabels = new LinkedHashMap();
        this.nameToDescrip = new LinkedHashMap();
	this.label = null;
	this.pos = pos;
    }
    
    public Position position() {
	return pos;
    }
    
    public NamedLabel join(LabelChecker lc, String name, Label l) {
        return join(lc, name, null, l);
        
    }
    public NamedLabel join(LabelChecker lc, String name, String descrip, Label l) {
        nameToLabels.put(name, l);
        if (descrip != null) {
            nameToDescrip.put(name, descrip);
        }
        
        if (label==null) {
            label = l;
            if (pos == null) pos = l.position();
            this.totalName = name;
        }
        else {
            label = lc.upperBound(label, l);
            this.totalName += " join " + name;
        }
        return this;
    }
    public NamedLabel meet(LabelChecker lc, String name, Label l) {
        return meet(lc, name, null, l);
        
    }
    public NamedLabel meet(LabelChecker lc, String name, String descrip, Label l) {
        nameToLabels.put(name, l);
        if (descrip != null) {
            nameToDescrip.put(name, descrip);
        }
        
        if (label==null) {
            label = l;
            if (pos == null) pos = l.position();
            this.totalName = name;
        }
        else {
            label = lc.lowerBound(label, l);
            this.totalName += " meet " + name;
        }
        return this;
    }
    
    public Label label() {
	return label;
    }
    
    public String toString() {
        return totalName;
    }
    
    public Label label(String name) {
	return (Label) nameToLabels.get(name);
    }
    
    public String name() {
    	return totalName;
    }
}
