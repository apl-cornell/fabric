package jif.types.label;

import jif.types.ParamInstance;


/** The label derived from a label paramter. 
 */
public interface ParamLabel extends Label
{
    ParamInstance paramInstance();
}
