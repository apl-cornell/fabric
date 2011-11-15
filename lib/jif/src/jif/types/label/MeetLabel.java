package jif.types.label;

import java.util.Set;



/** The meet of several labels. 
 */
public interface MeetLabel extends Label
{
    //Label flatten();
    Set meetComponents();
}
