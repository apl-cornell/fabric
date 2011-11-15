package jif.ast;

import java.util.*;

/** This class represents a join of several label nodes. It's also a
 *  label node by itself. 
 */
public interface MeetLabelNode extends LabelNode {
    /** Gets the list of join components, which are either label nodes or policy nodes. */
    List components();
    
    /** Returns a copy of this object with <code>components</code> updated.
     */    
    MeetLabelNode components(List components);
}
