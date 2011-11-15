package jif.types.label;

import java.util.Collection;



/** The join of several labels. 
 */
public interface JoinLabel extends Label
{
    //Label flatten();
    Collection joinComponents();
}
