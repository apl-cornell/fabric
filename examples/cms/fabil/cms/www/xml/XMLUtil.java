package cms.www.xml;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author Evan
 */
public class XMLUtil
{
	/**
	 * Return the children of the given node that have the given tag 
	 * @return A CMSNodeList of Elements. CMSNodeList extends Vector (making it writable)
	 * and implements NodeList (to make it interchangeable with Node.getChildNodes() ).
	 */
	public static CMSNodeList getChildrenByTagName(Element element, String tag)
	{
		NodeList children = element.getChildNodes();
		CMSNodeList result = new CMSNodeList();
		for (int i = 0; i < children.getLength(); i++)
			if (((Element)children.item(i)).getTagName().equals(tag))
				result.add(children.item(i));
		return result;
	}
	
	/**
	 * 
	 * @param element
	 * @param tag
	 * @return The first child Element if there is one; else null
	 */
	public static Element getFirstChildByTagName(Element element, String tag)
	{
		NodeList n= getChildrenByTagName(element, tag);
		if (n.getLength() == 0)
		    return null;
		else 
		    return (Element)n.item(0);
	}
	
	/**
	 * Return a list of children of the given node that have the given value for the given attribute
	 * @param element The Element whose children to check
	 * @param attribute
	 * @param value
	 * @return A CMSNodeList of Elements. CMSNodeList extends Vector (making it writable)
	 * and implements NodeList (to make it interchangeable with Node.getChildNodes() ).
	 */
	public static CMSNodeList getChildrenByAttributeValue(Element element, String attribute, String value)
	{
		NodeList children = element.getChildNodes();
		CMSNodeList result = new CMSNodeList();
		for (int i = 0; i < children.getLength(); i++)
			if (((Element)children.item(i)).getAttribute(attribute).equals(value))
				result.add(children.item(i));
		return result;
	}
	
	public static CMSNodeList getChildrenByTagNameAndAttributeValue(Element element, String tag, String attribute, String value)
	{
		NodeList children = element.getChildNodes();
		CMSNodeList result = new CMSNodeList();
		for (int i = 0; i < children.getLength(); i++)
			if (((Element)children.item(i)).getTagName().equals(tag)
				&& ((Element)children.item(i)).getAttribute(attribute).equals(value))
				result.add(children.item(i));
		return result;
	}
	
	public static CMSNodeList getChildrenByTagNameAndAttributeValueR(Element element, String tag, String attribute, String value)
	{
		NodeList children = element.getElementsByTagName(tag);
		CMSNodeList result = new CMSNodeList();
		for (int i = 0; i < children.getLength(); i++)
			if (((Element)children.item(i)).getAttribute(attribute).equals(value))
				result.add(children.item(i));
		return result;
	}
}
