package sif.html;

import fabric.lang.security.Label;

/**
 * @author andru
 *
 * */
public final class TRow extends BlockContainer {

    public TRow(Label L, Label E, Label cL, Label cE, Node cells) {
        super(L, E, "tr", cL, cE, cells);
    }

    public TRow(Label L, Label E, Node cells) {
        super(L, E, "tr", L, E, cells);
    }

    boolean isBigContainer() {
        return true;
    }

    public static TRow create(Label L, Label E, Label cL, Label cE, NodeList cells) {
        NodeList newcells = NodeList.EMPTY(cL, cE);
        for (int i = 0; i < cells.children.length; i++) {
            Node child = cells.children[i];
            if (child != null) {
                newcells = newcells.append(child.getL(), child.getE(), new TCell(child.getL(), child.getE(), child.getL(), child.getE(), child));
            }
        }
        return new TRow(L, E, cL, cE, newcells);
    }
    public static TRow create(Label L, Label E, NodeList cells) {
        NodeList newcells = NodeList.EMPTY(L, E);
        for (int i = 0; i < cells.children.length; i++) {
            Node child = cells.children[i];
            if (child != null) {
                newcells = newcells.append(child.getL(), child.getE(), new TCell(child.getL(), child.getE(), child.getL(), child.getE(), child));
            }
        }
        return new TRow(L, E, newcells);
    }

    public static boolean jif$Instanceof(Label l, Label e, Object o) {
        return (o instanceof TRow) && Node.jif$Instanceof(l, e, o);
    }

    public static TRow jif$cast$sif_html_TRow(Label l, Label e, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(l, e, o))
            return (TRow)o;
        throw new ClassCastException();
    }
}
