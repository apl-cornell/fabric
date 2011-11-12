package sif.servlet;

import sif.html.Node;

public class ActionNodePair {
    public final Action a;
    public final Node n;
    public ActionNodePair(Action a, Node n) {
        this.a = a;
        this.n = n;
    }
    public boolean equals(Object o) {
        if (o instanceof ActionNodePair) {
            ActionNodePair that = (ActionNodePair)o;
            return that.a == a && that.n == n;
        }
        return false;
    }
    public int hashCode() {
        return (a == null?0:a.hashCode()) ^ (n == null?0:n.hashCode()); 
    }
}