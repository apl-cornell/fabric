/*
 * Created on Apr 19, 2005
 */
package cms.www.xml;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import edu.cornell.csuglab.cms.www.AccessController;
/**
 * @author yc263
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CategoryXMLUtil
{
	//separator character for IDs in an HTTP request string used to add or edit contents
	public static final String REQ_ID_SEP = "_";
	
	/**
	 * Take in a set of IDs for a given content and put out a String that can be
	 * parsed to unambiguously identify the content
	 * (to be used in request parameters when adding/editing category contents)
	 * @param contentID
	 * @param rowID
	 * @param colID
	 * @return A String giving all the useful IDs for this content
	 * (some subset of the input IDs, with delimiters)
	 */
	public static String createContentIDsString(long contentID, long rowID, long colID)
	{
		if(contentID != -1) return "" + contentID;
		return "" + rowID + REQ_ID_SEP + colID;
	}
	
	/**
	 * Take in a set of IDs for a given file and put out a String that can be parsed to
	 * unambiguously identify the file
	 * (to be used in request parameters when adding/editing category contents)
	 * @param fileID
	 * @param contentID
	 * @param rowID
	 * @param colID
	 * @param fileIndex The index of the file within its content
	 * @return A String giving all the useful IDs for this file
	 * (some subset of the input IDs, with delimiters)
	 */
	public static String createFileIDsString(long fileID, long contentID, long rowID, long colID, int fileIndex)
	{
		//if we have a file ID, use it; if we have a content ID, use it; else can only use row & column
		if(fileID != -1) return "" + fileID;
		return createContentIDsString(contentID, rowID, colID) + REQ_ID_SEP + fileIndex;
	}
	
	/**
	 * 
	 * @param parent
	 * @param tag
	 * @return The list of children of the first child of parent with the given tag, if there
	 * is such a child; else an empty NodeList
	 */
	public static NodeList getListByTagName(Element parent, String tag){
		NodeList children = parent.getChildNodes();
		for(int i=0; i<children.getLength(); i++){
			if(((Element)children.item(i)).getTagName().equals(tag))
				return children.item(i).getChildNodes();
		}
		return new CMSNodeList();
	}
	
	public static NodeList getVisibleColumnList(Element category){
		Element columnList = XMLUtil.getFirstChildByTagName(category, XMLBuilder.TAG_COLUMNS);
		return XMLUtil.getChildrenByTagNameAndAttributeValue(columnList, XMLBuilder.TAG_COLUMN, XMLBuilder.A_HIDDEN, "false");
	}
	
	public static NodeList getVisibleRowList(Element category){
		return getListByTagName(category, XMLBuilder.TAG_VISIBLEROWS);
	}
	
	public static NodeList getHiddenColumnList(Element category){
		Element columnList = XMLUtil.getFirstChildByTagName(category, XMLBuilder.TAG_COLUMNS);
		return XMLUtil.getChildrenByTagNameAndAttributeValue(columnList, XMLBuilder.TAG_COLUMN, XMLBuilder.A_HIDDEN, "true");
	}
	
	public static NodeList getHiddenRowList(Element category){
		return getListByTagName(category, XMLBuilder.TAG_HIDDENROWS);
	}
	
	public static NodeList getRemovedColumnList(Element category)
	{
		Element columnList = XMLUtil.getFirstChildByTagName(category, XMLBuilder.TAG_REMOVEDCOLUMNS);
		return XMLUtil.getChildrenByTagName(columnList, XMLBuilder.TAG_COLUMN);
	}
	
	/**
	 * Return visible and hidden columns in one list
	 * @param category
	 * @return
	 */
	public static NodeList getColumnList(Element category)
	{
		Element columnList = XMLUtil.getFirstChildByTagName(category, XMLBuilder.TAG_COLUMNS);
		return XMLUtil.getChildrenByTagName(columnList, XMLBuilder.TAG_COLUMN);
	}
	
	public static NodeList getContentList(Element row){
		return row.getChildNodes();
	}
	
	/* print various types of content data, formatted for Web display */
	
	public static String printBlank(){
		return  "&nbsp;";
	}
	
	public static String printURL(Element content)
	{
		if(Long.parseLong(content.getAttribute(XMLBuilder.A_ID)) == -1) //id -1 means no actual content in db
			return printBlank();
		String url = content.getAttribute(XMLBuilder.A_URL),
			linkName = content.getAttribute(XMLBuilder.A_LINKNAME);
		if(!linkName.equals(""))
		{
			if(!url.equals(""))
				return "<a target=\"blank\" href=\"" + url + "\">" + linkName + "</a>";
			else
				return linkName;
		}
		else
		{
			if(!url.equals(""))
				return "<a target=\"_blank\" href=\"" + url + "\">(link)</a>";					
			else
				return "&nbsp;";
		}
	}
	
	public static String printText(Element content)
	{
		if(Long.parseLong(content.getAttribute(XMLBuilder.A_ID)) == -1 //id -1 means no actual content in db
			|| content.getAttribute(XMLBuilder.A_DATA).length() == 0) //IE has problems displaying no text at all
			return printBlank();
		return content.getAttribute(XMLBuilder.A_DATA);
	}
	
	public static String printNumber(Element content)
	{
		if(Long.parseLong(content.getAttribute(XMLBuilder.A_ID)) == -1) //id -1 means no actual content in db
			return printBlank();
		return content.getAttribute(XMLBuilder.A_DATA);
	}
	
	public static String printDate(Element content)
	{
		if(Long.parseLong(content.getAttribute(XMLBuilder.A_ID)) == -1) //id -1 means no actual content in db
			return printBlank();
		//the date strings in the XML tree are preformatted, ready to print
		return  content.getAttribute(XMLBuilder.A_DATA);
	}
	
	public static String printFile(Element content)
	{
		if(Long.parseLong(content.getAttribute(XMLBuilder.A_ID)) == -1) //id -1 means no actual content in db
			return printBlank();
		String result = "";
		NodeList fileList = getListByTagName(content, XMLBuilder.TAG_VISIBLEFILES);
		if(fileList.getLength() == 0) result +="&nbsp;";
		else
		{
			result += "<ul class=\"ctg_file_list\">";
			for(int count=0; count<fileList.getLength(); count++)
			{
				result += "<li>";
				Element file = (Element)fileList.item(count);
				String filename = file.getAttribute(XMLBuilder.A_FILENAME),
					linkName = file.getAttribute(XMLBuilder.A_LINKNAME);
				if(!filename.equals(""))
				{
					result += "<a href=\"?" + AccessController.P_ACTION + "=" + AccessController.ACT_DOWNLOAD
							+"&amp;" + AccessController.P_ID + "=" + file.getAttribute(XMLBuilder.A_ID)
							+"&amp;" + AccessController.P_DOWNLOADTYPE + "=" + XMLBuilder.T_CATFILE + "\">";
					if(!linkName.equals(""))
						result += linkName + "</a><br>";
					else
						result += filename + "</a><br>";
				}
				else
				{
					if(!linkName.equals(""))
						result += linkName;
					else
						result += "&nbsp;";
				}
				result += "</li>";
			}
			result += "</ul>";
		}
		return result;
	}
}
