package cms.www.xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;

import javax.ejb.FinderException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cms.www.util.DateTimeUtil;
import cms.www.util.Profiler;
import cms.www.util.Util;

import edu.cornell.csuglab.cms.author.Principal;
import edu.cornell.csuglab.cms.base.*;
import edu.cornell.csuglab.cms.util.category.CategoryBeanDataProvider;
import edu.cornell.csuglab.cms.util.category.CategoryDataProvider;
import edu.cornell.csuglab.cms.util.category.Coord;
import edu.cornell.csuglab.cms.util.category.CtgUtil;

/**
 * Builds an XML subtree with category general and content information
 * 
 * Created 4 / 6 / 05
 * @author Evan
 */
public class CategoryXMLBuilder extends XMLBuilder
{
	/**
	 * Generate an XML subtree with all known info about the given Category, including its contents
	 * @param p The Principal to generate the page for
	 * @param xml The Document to place this element on
	 * @param cat The category from which to generate the branch
	 * @param ctntAction TODO what is this?
	 * @return A category element, with general properties set and subtrees for contents
	 * @throws FinderException
	 */
	public static Element buildFullSubtree(Principal p, Document xml, CategoryLocal cat) throws FinderException
	{
		Profiler.enterMethod("CategoryXMLBuilder.buildFullSubtree", "CategoryID: " + cat.getCategoryID());
		Element xCategory = buildGeneralSubtree(p, xml, cat);
		CategoryDataProvider provider = new CategoryBeanDataProvider(cat);
		//list columns
		xCategory.appendChild(buildNonremovedColumnListsSubtree(xml, provider));
		xCategory.appendChild(buildRemovedColumnListSubtree(xml, provider));
		//list rows
		Element xVisibleRows = xml.createElement(TAG_VISIBLEROWS),
			xHiddenRows = xml.createElement(TAG_HIDDENROWS);
		buildRowListSubtrees(xml, provider, xVisibleRows, xHiddenRows);
		xCategory.appendChild(xVisibleRows);
		xCategory.appendChild(xHiddenRows);
		Profiler.exitMethod("CategoryXMLBuilder.buildFullSubtree", "CategoryID: " + cat.getCategoryID());
		return xCategory;
	}
	
	/**
	 * Generate an XML subtree with info about the given Category's template, but not its contents
	 * @param p The Principal to generate the page for
	 * @param xml The Document to place this element on
	 * @param cat The category from which to generate the branch
	 * @return A category element, with general properties set
	 * @throws FinderException
	 */
	public static Element buildGeneralSubtree(Principal p, Document xml, CategoryLocal cat) throws FinderException
	{
		Element xCategory= xml.createElement(TAG_CATEGORY);
		xCategory.setAttribute(A_ID, Long.toString(cat.getCategoryID()));
		xCategory.setAttribute(A_NAME, cat.getCategoryName());
		xCategory.setAttribute(A_HIDDEN, cat.getHidden() ? "true" : "false");
		xCategory.setAttribute(A_SORTBYID, Long.toString(cat.getSortByColId()));
		xCategory.setAttribute(A_NUMSHOW, Long.toString(cat.getNumShowContents()));
		xCategory.setAttribute(A_AUTHORZN, "" + cat.getAuthorzn());
		if(cat.getIsAnnouncements()) xCategory.setAttribute(A_ISANNOUNCEMENTS, ""); //does it get special treatment?
		if(cat.getAscending()) xCategory.setAttribute(A_ORDER,"1");
		else xCategory.setAttribute(A_ORDER, "0");
		return xCategory;
	}
	
	/**
	 * Generate an XML subtree with minimal identifying information on the given category
	 * @param xml The Document to place this element on
	 * @param cat The category from which to take information
	 * @return A category element with some general attributes set
	 */
	public static Element buildShortSubtree(Document xml, CategoryData data)
	{
		Element xCategory = xml.createElement(TAG_CATEGORY);
		xCategory.setAttribute(A_ID, Long.toString(data.getCategoryID()));
		xCategory.setAttribute(A_NAME, data.getCategoryName());
		xCategory.setAttribute(A_HIDDEN, data.getHidden() ? "true" : "false");
		xCategory.setAttribute(A_POSITION, Integer.toString(data.getPositn()));
		xCategory.setAttribute(A_NUMSHOW, Long.toString(data.getNumShowContents()));
		return xCategory;
	}
	
	/**
	 * Generate an XML subtree with info on accepted content types
	 * (note this does not depend on the specific category)
	 * @param xml The Document to place this element on
	 * @return A category element with a child containing a list of legal datatypes
	 */
	public static Element buildDatatypesSubtree(Document xml)
	{
		Element xCategory = xml.createElement(TAG_CATEGORY);
		Element xType = xml.createElement(TAG_DATATYPE);
		Element xItem = xml.createElement(TAG_ITEM);
		xItem.setAttribute(A_TYPE, CtgUtil.CTNT_DATE);
		xType.appendChild(xItem);
		xItem = xml.createElement(TAG_ITEM);
		xItem.setAttribute(A_TYPE, CtgUtil.CTNT_FILE);
		xType.appendChild(xItem);
		xItem = xml.createElement(TAG_ITEM);
		xItem.setAttribute(A_TYPE, CtgUtil.CTNT_TEXT);
		xType.appendChild(xItem);
		xItem = xml.createElement(TAG_ITEM);
		xItem.setAttribute(A_TYPE, CtgUtil.CTNT_URL);
		xType.appendChild(xItem);
		xCategory.appendChild(xType);
		xItem = xml.createElement(TAG_ITEM);
		xItem.setAttribute(A_TYPE, CtgUtil.CTNT_NUMBER);
		xType.appendChild(xItem);
		xCategory.appendChild(xType);
		return xCategory;
	}
	
	/**
	 * Build subtrees with lists of visible and hidden columns, respectively, in this category;
	 * do NOT include removed columns
	 * @param xml The document on which to build the subtrees
	 * @param ctgProvider The category from which to take information
	 * @return A column-list element whose children represent lists of columns
	 */
	public static Element buildNonremovedColumnListsSubtree(Document xml, CategoryDataProvider ctgProvider) throws FinderException
	{
		Element xColumnList = xml.createElement(TAG_COLUMNS);
		//the list of columns we get here will be ordered by position within the category
		Collection c = ctgProvider.getNonremovedColumnsCollection(false);
		Iterator i = c.iterator();
		while(i.hasNext()) {
			CategoryColData col = (CategoryColData)i.next();
			Element xColumn = xml.createElement(TAG_COLUMN);
			xColumn.setAttribute(A_ID, Long.toString(col.getColID()));
			xColumn.setAttribute(A_NAME, col.getColName());
			xColumn.setAttribute(A_POSITION, Long.toString(col.getPosition()));
			xColumn.setAttribute(A_TYPE, col.getColType());
			xColumn.setAttribute(A_HIDDEN, Boolean.toString(col.getHidden()));
			xColumn.setAttribute(A_REMOVED, Boolean.toString(col.getRemoved()));
			xColumnList.appendChild(xColumn);
		}
		return xColumnList;
	}
	
	/**
	 * Build a subtree with a list of all removed columns in this category
	 * @param xml The document on which to build the subtrees
	 * @param ctgProvider The category from which to take information
	 * @return A column-list element whose children represent columns
	 */
	public static Element buildRemovedColumnListSubtree(Document xml, CategoryDataProvider ctgProvider) throws FinderException {
		Element xColumnList = xml.createElement(TAG_REMOVEDCOLUMNS);
		//the list of columns we get here will be ordered by position within the category
		Collection c = ctgProvider.getRemovedColumnsCollection();
		Iterator i = c.iterator();
		while(i.hasNext()) {
			CategoryColData col = (CategoryColData)i.next();
			Element xColumn = xml.createElement(TAG_COLUMN);
			xColumn.setAttribute(A_ID, Long.toString(col.getColID()));
			xColumn.setAttribute(A_NAME, col.getColName());
			xColumn.setAttribute(A_POSITION, Long.toString(col.getPosition()));
			xColumn.setAttribute(A_TYPE, col.getColType());
			xColumn.setAttribute(A_HIDDEN, Boolean.toString(col.getHidden()));
			xColumn.setAttribute(A_REMOVED, Boolean.toString(col.getRemoved()));
			xColumnList.appendChild(xColumn);
		}
		return xColumnList;
	}
	
	/**
	 * Used by makeRowColContentsMap() to sort column IDs by column position
	 */
	private static class ColIDComparator implements Comparator
	{
		private ArrayList colList; // a list of CategoryColumnDatas for a given category
		
		/**
		 * 
		 * @param colList Column IDs in a given category by order of appearance
		 */
		public ColIDComparator(ArrayList colList)
		{
			this.colList = colList;
		}
		
		/**
		 * obj1 and obj2 are Longs representing column IDs; tell whether obj1 comes before obj2 in 
		 * whatever category we were initialized with
		 */
		public int compare(Object obj1, Object obj2)
		{
			long id1 = ((Long)obj1).longValue(), id2 = ((Long)obj2).longValue();
			CategoryColData col1 = null, col2 = null;
			for(int i = 0; i < colList.size(); i++)
				if(((CategoryColData)colList.get(i)).getColID() == id1) col1 = (CategoryColData)colList.get(i);
				else if(((CategoryColData)colList.get(i)).getColID() == id2) col2 = (CategoryColData)colList.get(i);
			return (int)(col1.getPosition() - col2.getPosition());
		}
	}
	
	/**
	 * Make a list of CategoryContentsDatas easier to iterate through by listing them by row and column;
	 * also build sorted lists of the row and column IDs that appear, since these are arbitrary
	 * @param ctg The category all of the data comes from
	 * @param contentsMap A Map to be created from content ID to each input ContentsData
	 * @param filesMap A Map to be created from content ID to ArrayLists of FileDatas
	 * @param rows An initialized list to be filled with all row IDs found among the contents, sorted increasing
	 * @param cols An initialized list to be filled with all column IDs found among the contents, sorted by position in the category
	 * @throws FinderException
	 */
	private static void makeRowColContentsMap(CategoryDataProvider ctgProvider, HashMap contentsMap, HashMap filesMap, ArrayList rows, ArrayList cols) throws FinderException
	{
		//also build a map from content ID to Coord(row, col), so we can map row/col to file data
		HashMap contentIDMap = new HashMap();
		Collection contents = ctgProvider.getContentsCollection(false, false), 
			files = ctgProvider.getFilesCollection(),
			colDatas = ctgProvider.getNonremovedColumnsCollection(false);
		/*
		 * build list of column IDs in the same order in which buildColumnListSubtrees() would return them,
		 * which is the order in which we get them from the provider
		 */
		Iterator i = colDatas.iterator();
		while(i.hasNext())
		{
			CategoryColData col = (CategoryColData)i.next();
			cols.add(new Long(col.getColID()));
		}
		//build lists of contents and row IDs
		i = contents.iterator();
		while(i.hasNext())
		{
			CategoryContentsData data = (CategoryContentsData)i.next();
			contentsMap.put(new Coord(data.getRowID(), data.getColumnID()), data);
			contentIDMap.put(new Long(data.getContentID()), new Coord(data.getRowID(), data.getColumnID()));
			if(!rows.contains(new Long(data.getRowID()))) rows.add(new Long(data.getRowID()));
		}
		i = files.iterator();
		/*
		 * assume there are no row/col combos in the file list that weren't mentioned in the contents list
		 * (ie we can stop checking the row & column lists)
		 */
		while(i.hasNext())
		{
			CategoryFileData data = (CategoryFileData)i.next();
			if(filesMap.containsKey(new Long(data.getContentID())))
				((ArrayList)filesMap.get(new Long(data.getContentID()))).add(data);
			else
			{
				ArrayList dataList = new ArrayList();
				dataList.add(data);
				filesMap.put(new Long(data.getContentID()), dataList);
			}
		}
		//get an indexable list of active columns belonging to this category
		ArrayList columnList = new ArrayList();
		columnList.addAll(ctgProvider.getNonremovedColumnsCollection(false));
		
	}
	
	/**
	 * List hidden and visible rows in a category, along with all contents (those in visible and hidden columns)
	 * @param xml The Document to place this element on
	 * @param ctg The category from which to generate the branch
	 * @param xVisibleRows The element under which to list visible rows
	 * @param xHiddenRows The element under which to list hidden rows
	 * @throws FinderException
	 */
	public static void buildRowListSubtrees(Document xml, CategoryDataProvider ctgProvider, Element xVisibleRows, Element xHiddenRows) throws FinderException
	{
		/*
		 * map each (row, col) pair to a Content and a list of Files, 
		 * and list the row and col IDs we find as Longs for easy looping
		 * (lists of row and col IDs are sorted when we get them)
		 */
		ArrayList rowIDs = new ArrayList(), colIDs = new ArrayList();
		HashMap rowColContentsMap = new HashMap(), rowColFilesMap = new HashMap();
		makeRowColContentsMap(ctgProvider, rowColContentsMap, rowColFilesMap, rowIDs, colIDs);
		//loop through row and column IDs and tack on contents one at a time
		for(int i = 0; i < rowIDs.size(); i++)
		{
			long rowID = ((Long)rowIDs.get(i)).longValue();
			Element xRow = xml.createElement(TAG_CTGROW);
			xRow.setAttribute(A_ID, "" + rowID);
			//append children, one per content in the row
			for(int j = 0; j < colIDs.size(); j++)
			{
				long colID = ((Long)colIDs.get(j)).longValue();
				CategoryContentsData currentContent = (CategoryContentsData)rowColContentsMap.get(new Coord(rowID, colID));
				if(currentContent == null) //this cell doesn't exist in the database, meaning it's empty
					//assign the data an ID of -1, meaning there's no data and a jsp should be careful when submitting
					//(note that the column type will need to be filled in when data is put into this cell)
					currentContent = new CategoryContentsData(-1, colID, ctgProvider.findColumnByID(colID).getColType(), rowID, null, null, null, null);
				//if the content isn't in the filemap's list, the returned ArrayList will be null; we can handle that
				xRow.appendChild(buildContentCellSubtree(xml, currentContent, (ArrayList)rowColFilesMap.get(new Long(currentContent.getContentID()))));
			}
			//add the row to one of our two lists of rows to be sorted
		    CategoryRowLocal currentRow = database.categoryRowHome().findByPrimaryKey(new CategoryRowPK(rowID));
		    if(currentRow.getHidden()) xHiddenRows.appendChild(xRow);
		    else xVisibleRows.appendChild(xRow);
		}
	}
	
	/**
	 * Build an XML subtree representing the given content, including any files associated with it
	 * @param xml The document on which to build the subtree
	 * @param content The current content object
	 * @param fileList An ArrayList of CategoryFileDatas representing all files in this content (may be null)
	 * @return A TAG_CONTENT element with properties set and children representing files if necessary
	 * @throws FinderException
	 */
	private static Element buildContentCellSubtree(Document xml, CategoryContentsData content, ArrayList fileList) throws FinderException
	{
		Element xContent = xml.createElement(TAG_CONTENT);
		String ctntType = content.getColumnType();
		xContent.setAttribute(A_TYPE, ctntType);
		xContent.setAttribute(A_ID, Long.toString(content.getContentID()));
		if(ctntType.equals(A_DATE))
		{
			if(content.getDate() == null) xContent.setAttribute(A_DATA, "");
			else xContent.setAttribute(A_DATA, DateTimeUtil.DATE.format(content.getDate()));
		}
		else if(ctntType.equals(A_TEXT))
		{
			xContent.setAttribute(A_DATA, content.getText());
		}
		else if(ctntType.equals(A_NUMBER))
		{
			if(content.getNumber() == null) xContent.setAttribute(A_DATA, "");
			else xContent.setAttribute(A_DATA, content.getNumber().toString());
		}
		else if(ctntType.equals(A_URL))
		{
			xContent.setAttribute(A_URL, content.getText());
			xContent.setAttribute(A_LINKNAME, (content.getLinkName() == null) ? "" : content.getLinkName());
		}
		else if(ctntType.equals(A_FILE))
		{
			addContentFileLists(xml, xContent, fileList);
		}
		else throw new IllegalArgumentException("Unknown content datatype '" + ctntType 
				+ "' in CategoryXMLBuilder::buildContentCellSubtree() at col " + content.getColumnID() 
				+ ", row " + content.getRowID());
		return xContent;
	}
	
	/**
	 * Build lists of visible and hidden files onto an element representing a single content
	 * @param xml The document on which to build the subtrees
	 * @param xContent The parent element for both subtrees
	 * @param fileList A NON-NULL list of CategoryFileDatas representing all files belonging to the given content
	 * @throws FinderException
	 */
	private static void addContentFileLists(Document xml, Element xContent, ArrayList fileList) throws FinderException
	{
		Element xVisibleFiles = xml.createElement(TAG_VISIBLEFILES),
			xHiddenFiles = xml.createElement(TAG_HIDDENFILES);
		//if the list is null, we want the visfiles and hiddenfiles tags but no lists under them
		if(fileList != null)
			for(int i = 0; i < fileList.size(); i++)
			{
				CategoryFileData data = (CategoryFileData)fileList.get(i);
				Element xFile = xml.createElement(TAG_CTGFILE);
				xFile.setAttribute(A_FILENAME, (data.getFileName() == null) ? "" : data.getFileName());
				xFile.setAttribute(A_LINKNAME, (data.getLinkName() == null) ? "" : data.getLinkName());
				xFile.setAttribute(A_ID, Long.toString(data.getCategoryFileID()));
				if(data.getHidden()) xHiddenFiles.appendChild(xFile);
				else xVisibleFiles.appendChild(xFile);
			}
		xContent.appendChild(xVisibleFiles);
		xContent.appendChild(xHiddenFiles);
	}
}
