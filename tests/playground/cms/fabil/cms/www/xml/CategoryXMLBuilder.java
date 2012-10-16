package cms.www.xml;

import fabric.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cms.www.util.DateTimeUtil;
import cms.www.util.Profiler;

import cms.auth.Principal;
import cms.model.*;

/**
 * Builds an XML subtree with category general and content information Created 4 /
 * 6 / 05
 * 
 * @author Evan
 */
public class CategoryXMLBuilder {
  
  public CategoryXMLBuilder(XMLBuilder builder) {
    // TODO Auto-generated constructor stub
  }

  /**
   * Generate an XML subtree with all known info about the given Category,
   * including its contents
   * 
   * @param p
   *          The Principal to generate the page for
   * @param xml
   *          The Document to place this element on
   * @param cat
   *          The category from which to generate the branch
   * @param ctntAction
   *          TODO what is this?
   * @return A category element, with general properties set and subtrees for
   *         contents
   * @throws FinderException
   */
  public Element buildFullSubtree(User p, Document xml, Category cat) {
    Profiler.enterMethod("CategoryXMLBuilder.buildFullSubtree", "CategoryID: " + cat.toString());
    Element xCategory = buildGeneralSubtree(p, xml, cat);
    // list columns
    xCategory.appendChild(buildNonremovedColumnListsSubtree(xml, cat));
    xCategory.appendChild(buildRemovedColumnListSubtree(xml, cat));
    // list rows
    Element xVisibleRows = xml.createElement(XMLBuilder.TAG_VISIBLEROWS), xHiddenRows =
        xml.createElement(XMLBuilder.TAG_HIDDENROWS);
    buildRowListSubtrees(xml, cat, xVisibleRows, xHiddenRows);
    xCategory.appendChild(xVisibleRows);
    xCategory.appendChild(xHiddenRows);
    Profiler.exitMethod("CategoryXMLBuilder.buildFullSubtree", "CategoryID: " + cat.toString());
    return xCategory;
  }

  /**
   * Generate an XML subtree with info about the given Category's template, but
   * not its contents
   * 
   * @param p
   *          The Principal to generate the page for
   * @param xml
   *          The Document to place this element on
   * @param cat
   *          The category from which to generate the branch
   * @return A category element, with general properties set
   * @throws FinderException
   */
  public Element buildGeneralSubtree(User p, Document xml, Category cat) {
    Element xCategory = xml.createElement(XMLBuilder.TAG_CATEGORY);
    xCategory.setAttribute(XMLBuilder.A_ID,       cat.toString());
    xCategory.setAttribute(XMLBuilder.A_NAME,     cat.getCategoryName());
    xCategory.setAttribute(XMLBuilder.A_HIDDEN,   cat.getHidden() ? "true" : "false");
    xCategory.setAttribute(XMLBuilder.A_SORTBYID, cat.getSortBy().toString());
    xCategory.setAttribute(XMLBuilder.A_NUMSHOW,  Integer.toString(cat.getNumToShow()));
    xCategory.setAttribute(XMLBuilder.A_AUTHORZN, Integer.toString(cat.getAuthLevel()));
    if (cat.getIsAnnouncements())
      xCategory.setAttribute(XMLBuilder.A_ISANNOUNCEMENTS, ""); // does it get special
                                                      // treatment?
    if (cat.getAscending())
      xCategory.setAttribute(XMLBuilder.A_ORDER, "1");
    else xCategory.setAttribute(XMLBuilder.A_ORDER, "0");
    return xCategory;
  }

  /**
   * Generate an XML subtree with minimal identifying information on the given
   * category
   * 
   * @param xml
   *          The Document to place this element on
   * @param cat
   *          The category from which to take information
   * @return A category element with some general attributes set
   */
  public Element buildShortSubtree(Document xml, Category data) {
    Element xCategory = xml.createElement(XMLBuilder.TAG_CATEGORY);
    xCategory.setAttribute(XMLBuilder.A_ID,       data.toString());
    xCategory.setAttribute(XMLBuilder.A_NAME,     data.getCategoryName());
    xCategory.setAttribute(XMLBuilder.A_HIDDEN,   data.getHidden() ? "true" : "false");
    xCategory.setAttribute(XMLBuilder.A_POSITION, Integer.toString(data.getPosition()));
    xCategory.setAttribute(XMLBuilder.A_NUMSHOW,  Integer.toString(data.getNumToShow()));
    return xCategory;
  }

  /**
   * Generate an XML subtree with info on accepted content types (note this does
   * not depend on the specific category)
   * 
   * @param xml
   *          The Document to place this element on
   * @return A category element with a child containing a list of legal
   *         datatypes
   */
  public Element buildDatatypesSubtree(Document xml) {
    Element xCategory = xml.createElement(XMLBuilder.TAG_CATEGORY);
    Element xType = xml.createElement(XMLBuilder.TAG_DATATYPE);
    Element xItem = xml.createElement(XMLBuilder.TAG_ITEM);
    xItem.setAttribute(XMLBuilder.A_TYPE, CategoryColumn.DATE);
    xType.appendChild(xItem);
    xItem = xml.createElement(XMLBuilder.TAG_ITEM);
    xItem.setAttribute(XMLBuilder.A_TYPE, CategoryColumn.FILE);
    xType.appendChild(xItem);
    xItem = xml.createElement(XMLBuilder.TAG_ITEM);
    xItem.setAttribute(XMLBuilder.A_TYPE, CategoryColumn.TEXT);
    xType.appendChild(xItem);
    xItem = xml.createElement(XMLBuilder.TAG_ITEM);
    xItem.setAttribute(XMLBuilder.A_TYPE, CategoryColumn.LINK);
    xType.appendChild(xItem);
    xCategory.appendChild(xType);
    xItem = xml.createElement(XMLBuilder.TAG_ITEM);
    xItem.setAttribute(XMLBuilder.A_TYPE, CategoryColumn.NUMBER);
    xType.appendChild(xItem);
    xCategory.appendChild(xType);
    return xCategory;
  }

  /**
   * Build subtrees with lists of visible and hidden columns, respectively, in
   * this category; do NOT include removed columns
   * 
   * @param xml
   *          The document on which to build the subtrees
   * @param category
   *          The category from which to take information
   * @return A column-list element whose children represent lists of columns
   */
  public Element buildNonremovedColumnListsSubtree(Document xml, Category category) {
    Element xColumnList = xml.createElement(XMLBuilder.TAG_COLUMNS);
    // the list of columns we get here will be ordered by position within the
    // category
    Iterator i = category.getColumns().iterator();
    while (i.hasNext()) {
      CategoryColumn col = (CategoryColumn) i.next();

      if (col.getHidden())
        continue;
      
      Element xColumn = xml.createElement(XMLBuilder.TAG_COLUMN);
      xColumn.setAttribute(XMLBuilder.A_ID,       col.toString());
      xColumn.setAttribute(XMLBuilder.A_NAME,     col.getName());
      xColumn.setAttribute(XMLBuilder.A_POSITION, Integer.toString(col.getPosition()));
      xColumn.setAttribute(XMLBuilder.A_TYPE,     col.getType());
      xColumn.setAttribute(XMLBuilder.A_HIDDEN,   Boolean.toString(col.getHidden()));
      xColumn.setAttribute(XMLBuilder.A_REMOVED,  Boolean.toString(col.getRemoved()));
      xColumnList.appendChild(xColumn);
    }
    return xColumnList;
  }

  /**
   * Build a subtree with a list of all removed columns in this category
   * 
   * @param xml
   *          The document on which to build the subtrees
   * @param category
   *          The category from which to take information
   * @return A column-list element whose children represent columns
   */
  public Element buildRemovedColumnListSubtree(Document xml, Category category) {
    Element xColumnList = xml.createElement(XMLBuilder.TAG_REMOVEDCOLUMNS);
    // the list of columns we get here will be ordered by position within the
    // category
    Iterator i = category.getRemovedColumns().iterator();
    while (i.hasNext()) {
      CategoryColumn col = (CategoryColumn) i.next();
      Element xColumn = xml.createElement(XMLBuilder.TAG_COLUMN);
      xColumn.setAttribute(XMLBuilder.A_ID,       col.toString());
      xColumn.setAttribute(XMLBuilder.A_NAME,     col.getName());
      xColumn.setAttribute(XMLBuilder.A_POSITION, Integer.toString(col.getPosition()));
      xColumn.setAttribute(XMLBuilder.A_TYPE,     col.getType());
      xColumn.setAttribute(XMLBuilder.A_HIDDEN,   Boolean.toString(col.getHidden()));
      xColumn.setAttribute(XMLBuilder.A_REMOVED,  Boolean.toString(col.getRemoved()));
      xColumnList.appendChild(xColumn);
    }
    return xColumnList;
  }

  /**
   * List hidden and visible rows in a category, along with all contents (those
   * in visible and hidden columns)
   * 
   * @param xml
   *          The Document to place this element on
   * @param ctg
   *          The category from which to generate the branch
   * @param xVisibleRows
   *          The element under which to list visible rows
   * @param xHiddenRows
   *          The element under which to list hidden rows
   * @throws FinderException
   */
  public void buildRowListSubtrees(Document xml, Category category,
                                   Element xVisibleRows, Element xHiddenRows) {
    // loop through row and column IDs and tack on contents one at a time
    Iterator rows = category.getRows().iterator();
    while (rows.hasNext()) {
      CategoryRow row = (CategoryRow) rows.next();
      
      Element xRow = xml.createElement(XMLBuilder.TAG_CTGROW);
      xRow.setAttribute(XMLBuilder.A_ID, row.toString());
      
      // append children, one per content in the row
      Iterator cols = category.getColumns().iterator();
      while (cols.hasNext()) {
        CategoryColumn column = (CategoryColumn) cols.next();
        CategoryContents currentContent = row.getContents(column);
        Element xContent = null;
        if (currentContent != null)
          xContent = buildContentCellSubtree(xml, currentContent);
        else {
          xContent = xml.createElement(XMLBuilder.TAG_CONTENT);
          xContent.setAttribute(XMLBuilder.A_TYPE, column.getType());
          xContent.setAttribute(XMLBuilder.A_ID,   "-1");
          xContent.setAttribute(XMLBuilder.A_DATA, "");
        }
        xRow.appendChild(xContent);
      }
      // add the row to one of our two lists of rows to be sorted
      if (row.getHidden())
        xHiddenRows.appendChild(xRow);
      else
        xVisibleRows.appendChild(xRow);
    }
  }

  /**
   * Build an XML subtree representing the given content, including any files
   * associated with it
   * 
   * @param xml
   *          The document on which to build the subtree
   * @param content
   *          The current content object
   * @return A XMLBuilder.TAG_CONTENT element with properties set and children representing
   *         files if necessary
   * @throws FinderException
   */
  private Element buildContentCellSubtree(final Document xml, CategoryContents content) {
    final Element xContent = xml.createElement(XMLBuilder.TAG_CONTENT);
    xContent.setAttribute(XMLBuilder.A_TYPE, content.getType());
    xContent.setAttribute(XMLBuilder.A_ID,   content.toString());
    
    content.accept(new CategoryContents.Visitor() {
      public void visitDateContents(CategoryContentsDate date) {
        xContent.setAttribute(XMLBuilder.A_DATA,
                              DateTimeUtil.DATE.format(date.getDate()));
      }
      
      public void visitStringContents(CategoryContentsString text) {
        xContent.setAttribute(XMLBuilder.A_DATA, text.getText());
      }
      
      public void visitNumberContents(CategoryContentsNumber num) {
        xContent.setAttribute(XMLBuilder.A_DATA, Integer.toString(num.getValue()));
      }
      
      public void visitLinkContents(CategoryContentsLink link) {
        xContent.setAttribute(XMLBuilder.A_URL, link.getAddress());
        xContent.setAttribute(XMLBuilder.A_LINKNAME,
                              (link.getTheLabel() == null) ? "" : link.getTheLabel());
      }
      
      public void visitFileContents(CategoryContentsFileList file) {
        addContentFileLists(xml, xContent, file);
      }
    });
    
    return xContent;
  }

  /**
   * Build lists of visible and hidden files onto an element representing a
   * single content
   * 
   * @param xml
   *          The document on which to build the subtrees
   * @param xContent
   *          The parent element for both subtrees
   * @param fileList
   *          A NON-NULL list of CategoryFileDatas representing all files
   *          belonging to the given content
   * @throws FinderException
   */
  private void addContentFileLists(Document xml, Element xContent, CategoryContentsFileList file) {
    Element xVisibleFiles = xml.createElement(XMLBuilder.TAG_VISIBLEFILES),
            xHiddenFiles  = xml.createElement(XMLBuilder.TAG_HIDDENFILES);
    
    Iterator files = file.getFiles().iterator();
    while (files.hasNext()) {
      CategoryContentsFileEntry data = (CategoryContentsFileEntry) files.next();
      Element xFile = xml.createElement(XMLBuilder.TAG_CTGFILE);
      xFile.setAttribute(XMLBuilder.A_FILENAME, data.getFile().getName());
      xFile.setAttribute(XMLBuilder.A_LINKNAME, data.getLinkName());
      xFile.setAttribute(XMLBuilder.A_ID,       data.toString());
      if (data.getHidden())
        xHiddenFiles.appendChild(xFile);
      else
        xVisibleFiles.appendChild(xFile);
    }
    xContent.appendChild(xVisibleFiles);
    xContent.appendChild(xHiddenFiles);
  }
}
