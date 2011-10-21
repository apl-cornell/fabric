<%@ page language="java" import="org.w3c.dom.*, cms.model.*, cms.www.*, cms.www.xml.*" %>
<%
/**********************************************************
* display a single category on the main course page
**********************************************************/

   Document displayData  = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
   Element  root         = (Element)  displayData.getChildNodes().item(0); 
   Element  course       = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE); 
   String   courseID     = (course != null) ? course.getAttribute(XMLBuilder.A_COURSEID):"0"; 
   NodeList categoryList = root.getElementsByTagName(XMLBuilder.TAG_CATEGORY);

   String   URL = request.getServletPath();
   User p= (User) session.getAttribute(AccessController.A_PRINCIPAL);

   boolean isAdmin = false, isCategory = false;
   if (!p.isInStaffAsBlankMode()) {
     isAdmin= course.hasAttribute(XMLBuilder.A_ISADMIN);
     isCategory = course.hasAttribute(XMLBuilder.A_ISCATEGORY);
   }

   if (categoryList == null || categoryList.getLength() == 0) {
      %> <br> <%
   }
   else {
      for (int i = 0; i < categoryList.getLength(); i++) {
         Element cat = (Element)categoryList.item(i);
         int num2Show = Integer.parseInt(cat.getAttribute(XMLBuilder.A_NUMSHOW));

         if (cat.getAttribute(XMLBuilder.A_HIDDEN).equals("false")) {
            String categoryID = cat.getAttribute(XMLBuilder.A_ID);
            String display = num2Show < Category.SHOWALL ? "": "style = \"display:none\" ";
            NodeList columnList = CategoryXMLUtil.getVisibleColumnList(cat);
            NodeList rowList = CategoryXMLUtil.getVisibleRowList(cat);
            boolean showExpand = num2Show < rowList.getLength();%>

<div class="assignment_left">
  <h2>
    <%= cat.getAttribute(XMLBuilder.A_NAME) %>
    <span id="category<%=i%>head">
      <a id="category<%=i%>showhide" href="#" onclick="hide<%= showExpand ? "Category" : "" %>('category<%= i %>', '(hide)', '(show)'); return false;" class = "hide">(hide)</a>
       <%   if (showExpand)
            {%>
          <a id="category<%=i%>expand" href="#" onclick="expandCategory('category<%= i %>',false, <%= Math.min(num2Show, rowList.getLength()) %>); return false;" class="hide" <%=display%>>(show all)</a>
      <%    } %>
    </span>
  </h2>
  <div id="<%="category"+i%>" class="showhide">
<%          if (columnList.getLength() == 0)
            {%>
  <span id="<%="category"+i%>" class="showhide">
    No columns<br><br>
  </span>
<%          }
            else
            {%>
    <table class="category_table" id="category<%=i%>table" cellpadding="2" cellspacing="0" border="0">
      <tr>
<%             for (int column=0; column < columnList.getLength(); column ++)
               {
                  Element ctgCol = (Element)columnList.item(column);%>
       <th><%=ctgCol.getAttribute(XMLBuilder.A_NAME)%></th>
<%             }
               if (isAdmin || isCategory) /* header for column of row-remove buttons */
               {%>
      <th>&nbsp;</th>
<%             }%>      
     </tr>
<%             for (int row = 0; row < rowList.getLength(); row++)
               {%>
     <tr>
<%                Element ctgRow = (Element)rowList.item(row);
                  String ctgRowID = ctgRow.getAttribute(XMLBuilder.A_ID);
                  NodeList contentList = CategoryXMLUtil.getContentList(ctgRow);
                  for (int column=0; column<contentList.getLength(); column++)
                  {
                     Element content = (Element)contentList.item(column);
                     String data = "";
                     String datatype = content.getAttribute(XMLBuilder.A_TYPE);%>
         <td align="center">
<%                   if (datatype.equalsIgnoreCase(CategoryColumn.FILE))
                     {%>
                  <%= CategoryXMLUtil.printFile(content) %>
<%                   }
                     else if (datatype.equalsIgnoreCase(CategoryColumn.DATE))
                     {%>
                  <%= CategoryXMLUtil.printDate(content) %>
<%                   }
                     else if (datatype.equalsIgnoreCase(CategoryColumn.TEXT))
                     {%>
                  <%= CategoryXMLUtil.printText(content) %>
<%                   }
                     else if (datatype.equalsIgnoreCase(CategoryColumn.LINK))
                     {%>
                  <%= CategoryXMLUtil.printURL(content) %>
<%                   }
                     else if (datatype.equalsIgnoreCase(CategoryColumn.NUMBER))
                     {%>
                  <%= CategoryXMLUtil.printNumber(content) %>
<%                   }%>
         </td>
<%                } /* End for each column within a row */
                  if (isAdmin || isCategory)
                  {%>            
      <td align="center">
         <script type="text/javascript">
            rollOver("?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_REMOVEROW %>&amp;<%= AccessController.P_ID %>=<%= ctgRowID %>&amp;<%=AccessController.P_COURSEID%>=<%=courseID%>","remove","class=\"hide\"");
         </script>
      </td>
<%                }%>
     </tr>
<%             } /* end for all rows within a category */ %>
    </table>
<%          }
            if (isAdmin || isCategory)
            {%>
    <p>
    <a href="?<%= AccessController.P_ACTION %>=<%=AccessController.ACT_EDITCTG%>&amp;<%= AccessController.P_CATID %>=<%= categoryID %>">Edit Layout</a>&nbsp;
    <a href="?<%= AccessController.P_ACTION %>=<%=AccessController.ACT_ADDNEDITCONTENTS%>&amp;<%= AccessController.P_CATID %>=<%= categoryID %>">Add/Edit Data</a>&nbsp;
<%          }%>
    <br>
  </div>
<%          if (num2Show < Category.SHOWALL)
            {%>
  <!-- show only top items in the above category table -->
  <script type="text/javascript">
      collapseCategory('<%= "category" + i %>',false, <%= Math.min(num2Show, rowList.getLength()) %>);
  </script>
<%          }
         } /* end if category is visible */ %>
</div>
<%    } /* end for all categories, visible or hidden */
   }%>
