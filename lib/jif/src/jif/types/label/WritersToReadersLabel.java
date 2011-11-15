package jif.types.label;

import jif.types.hierarchy.LabelEnv;



/** 
 * Label that converts the writers of a label into readers. The label
 * WritersToReaders(L) has the property that
 * readers(WritersToReaders(L)) is a superset of writers(L).
 */
public interface WritersToReadersLabel extends Label
{
    /**
     * The nested label.
     */
    Label label();
    
    /**
     * Apply this operator to the nested label.
     */
    Label transform(LabelEnv env);
}
