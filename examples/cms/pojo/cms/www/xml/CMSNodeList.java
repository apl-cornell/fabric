package cms.www.xml;

import java.util.*;
import org.w3c.dom.*;

/**
 * For use in XMLUtil's child-finder methods
 */
public class CMSNodeList extends Vector implements NodeList
{
	public CMSNodeList()
	{
		super();
	}
	
	public int getLength()
	{
		return size();
	}
	
	public void addList(NodeList list)
	{
		for (int i = 0; i < list.getLength(); i++) add(list.item(i));
	}
	
	public Node item(int i)
	{	
	    try {
	        return (Node)get(i);
	    } catch (ArrayIndexOutOfBoundsException e) {
	        return null;
	    }
	}
}
