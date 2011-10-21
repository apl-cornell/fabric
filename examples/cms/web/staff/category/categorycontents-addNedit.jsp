<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*,edu.cornell.csuglab.cms.util.category.*"%>
<% Document displayData= (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
   Element root= (Element)displayData.getChildNodes().item(0);
   Element course= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE);
   Element category= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_CATEGORY);
   Element status= (Element)root.getElementsByTagName(XMLBuilder.TAG_STATUS).item(0);
   String ctgID= category.getAttribute(XMLBuilder.A_ID); %>
<jsp:include page="../../header.jsp" />
<link href="calstyle.css" rel="stylesheet" type="text/css">
<script src="CalendarPopup.js" type="text/javascript"></script> <%-- required by formattedtextboxes.js --%>
<script src="datetime.js" type="text/javascript"></script> <%-- required by formattedtextboxes.js --%>
<script src="formattedtextboxes.js" type="text/javascript"></script>
<jsp:include page="categoryscript.js.jsp" />
<jsp:include page="../../header-page.jsp" />
<div id="course_wrapper_withnav">
	<table id="course_wrapper_table" summary="course wrapper" cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<jsp:include page="../navbar.jsp"/>
			<td id="course_page_container" >
				<form action="?<%= AccessController.P_ACTION   %>=<%= AccessController.ACT_SETADDNEDITCONTENTS   %>&amp;
				               <%= AccessController.P_COURSEID %>=<%= course.getAttribute(XMLBuilder.A_COURSEID) %>&amp;
				               <%= AccessController.P_CATID    %>=<%= category.getAttribute(XMLBuilder.A_ID)     %>"
				      onSubmit="return validateAllFormattedTextboxes();"
				      method="post" enctype="multipart/form-data">
					<div id="course_page">
						<jsp:include page="../../problem-report.jsp"/>
						<span class="assignment_title"><%= ctgID.equals("0") ? "New Content" : category.getAttribute(XMLBuilder.A_NAME) %></span>
						<div class="assignment_left">
							<% /* start code for add & edit category contents */
							String sortByColID = category.getAttribute(XMLBuilder.A_SORTBYID);
							int numOfCal = 0;
							String SEP = CategoryXMLUtil.REQ_ID_SEP; /* separator character for elements of a request parameter */
							/* size of displayed text boxes */
							String rowSize = "5", colSize = "25";
							if (category.hasAttribute(XMLBuilder.A_ISANNOUNCEMENTS)) {
								/* announcements get nice big boxes */
								rowSize = "3";
								colSize = "30";
							}%>
							<h2>Existing Data:</h2>
							<div id="contents" class="showhide"><%
							Element xColumnList = XMLUtil.getFirstChildByTagName(category, XMLBuilder.TAG_COLUMNS);
							NodeList columnList = xColumnList.getChildNodes();
							if (columnList.getLength() == 0) { %>
								No existing columns<%
							} else { %>
								<table id="ctnts" class="assignment_table" cellpadding="2" cellspacing="0" border="0" width="100%">
									<tr> <%
										long[] colIDArr = new long[columnList.getLength()];
										for (int column=0; column < columnList.getLength(); column ++) {
											Element ctgCol = (Element)columnList.item(column);
											colIDArr[column] = Long.parseLong(ctgCol.getAttribute(XMLBuilder.A_ID));%>
											<th id="<%= colIDArr[column] %>"
											    title="<%= ctgCol.getAttribute(XMLBuilder.A_TYPE) %>"
											    a_colId="<%= ctgCol.getAttribute(XMLBuilder.A_ID) %>"
											    a_coltype="<%= ctgCol.getAttribute(XMLBuilder.A_TYPE) %>"
											    nowrap> <%
												if (ctgCol.getAttribute(XMLBuilder.A_HIDDEN).equals("true")) {%>
													<font color="#8888aa"> <%= ctgCol.getAttribute(XMLBuilder.A_NAME) + " [HIDDEN]" %> </font> <%
												} else { %>
													<font> <%= ctgCol.getAttribute(XMLBuilder.A_NAME) %> </font> <%
												} %>
											</th> <%
										} %>
										<th>Remove</th>
									</tr> <%
									NodeList rowList = CategoryXMLUtil.getVisibleRowList(category);
									for (int row=0; row<rowList.getLength(); row++) { %>
										<tr> <%
											Element ctgRow = (Element)rowList.item(row);
											long ctgRowID = Long.parseLong(ctgRow.getAttribute(XMLBuilder.A_ID)); %>
											<script type="text/javascript">
												existingRowIDs[existingRowIDs.length] = <%= ctgRowID %>;
											</script> <%
											String rowIDString = "" + ctgRowID;
											NodeList contentList = CategoryXMLUtil.getContentList(ctgRow);
											for (int column=0; column<contentList.getLength(); column++)/*Each Column Of A Row*/ {
												Element content = (Element)contentList.item(column);
												long ctntID = Long.parseLong(content.getAttribute(XMLBuilder.A_ID));
												long colID = colIDArr[column];
												String ctntIDString = CategoryXMLUtil.createContentIDsString(ctntID, ctgRowID, colID);
												String datatype = content.getAttribute(XMLBuilder.A_TYPE);
												String fileType = "curfiles";
												String sectionLabel = "Existing files";
												if (datatype.equalsIgnoreCase(CtgUtil.CTNT_FILE)) { %>
													int length = ctntID == -1 ? 0 : fileList.getLength();
													<td id="<%= "cell" + ctntIDString %>" align="left" numfiles="<%= length %>"> <%
														if (ctntID != -1) /* if the content actually exists, check for existing files */ {
															NodeList fileList = XMLUtil.getFirstChildByTagName(content, XMLBuilder.TAG_VISIBLEFILES).getChildNodes();%>
															if (fileList.getLength() > 0) /* display table of visible files (don't display hidden) */ {
																<div class="replace">
																	<span id="<%= fileType + SEP + ctntIDString + "head" %>">
																		<a href="#"
																		   onClick="show('<%= fileType + SEP + ctntIDString %>',
																		                 '<%= sectionLabel %> &raquo;',
																		                 '&laquo; <%= sectionLabel %>'); return false;">
																			<%= sectionLabel %> &raquo;</a>
																	</span>
																	<table class="replace" id="<%= fileType + SEP + ctntIDString %>"
																	       cellpadding="0" cellspacing="0" style="display:none">
																		<tr>
																			<th>Shown As</th>
																			<th>Filename</th>
																			<th>Remove</th>
																		</tr> <%
																		/* display table of visible (nonhidden) files */
																		for (int count=0; count<fileList.getLength(); count++) {
																			Element file = (Element)fileList.item(count);
																			long fileID = Long.parseLong(file.getAttribute(XMLBuilder.A_ID));
																			String fileIDString = CategoryXMLUtil.createFileIDsString(fileID, ctntID, ctgRowID, colID, count);%>
																			<tr class="<%= count % 2 == 0 ? "row_even" : "row_odd" %>">
																				<td align="left">
																					<input type="text" size="15"
																					       name="<%= AccessController.P_CONTENT_FILELABEL + SEP + fileIDString %>"
																					       value="<%= file.getAttribute(XMLBuilder.A_LINKNAME) %>">
																				</td>
																				<td align="left">
																					<%= file.getAttribute(XMLBuilder.A_FILENAME).equals("")
																					        ? "(no file)"
																					        : file.getAttribute(XMLBuilder.A_FILENAME) %>
																				</td>
																				<td class="remove">
																					<input type="checkbox"
																					       name="<%= AccessController.P_REMOVEFILE + SEP + fileIDString %>">
																				</td>
																			</tr> <%
																		}
																		/* don't bother displaying hidden files; it's easy enough to re-upload a file instead of restoring */ %>
																	</table>
																</div> <%
															}
														} else /* empty content; hasn't been created in the db yet */ {} %>
														<div class="replace">
															<a href="#" onClick="moreFiles('<%= "cell" + ctntIDString %>', '<%= ctntIDString %>', false); return false;"> (Add New File) </a>
														</div>
													</td> <%
												} else if (datatype.equalsIgnoreCase(CtgUtil.CTNT_DATE)) { %>
													<td align="center" id="<%= "cell" + ctntIDString %>">
														<div class="cal" id="<%="dateselect"+numOfCal%>"></div>
														<div class="dateblock">
															<script type="text/javascript">
																calArray[numOfDates] = new CalendarPopup();
																numOfDates++;
															</script>
															<input type="text" size="20" id="<%= "datebox"+numOfCal %>" name="<%= AccessController.P_CONTENT_DATE + SEP + ctntIDString %>" value="<%= content.getAttribute(XMLBuilder.A_DATA) %>">
															<script type="text/javascript">
																registerDateFormatTextbox('<%= "datebox"+numOfCal %>');
															</script>
															<a href="#" id="<%= "datelink"+numOfCal %>" name="<%= "datelink"+numOfCal %>" 
																	onClick="<%= "calArray["+numOfCal+"].select(getElementById('datebox"+numOfCal+"'), 'datelink"+numOfCal+"', 'MMM d, yyyy'); return false;" %>">
																<img class="calicon" src="../images/cal.gif" alt="Select" width="16px" height="16px">
															</a>
														</div>
													</td> <%
													numOfCal++;	
												} else if (datatype.equalsIgnoreCase(CtgUtil.CTNT_TEXT)) { %>
													<td align="center" id="<%= "cell" + ctntIDString %>">
														<textarea rows="<%= rowSize %>"
														          cols="<%= colSize %>"
														          physical
														          name="<%= AccessController.P_CONTENT_TEXT + SEP + ctntIDString %>">
															<%=content.getAttribute(XMLBuilder.A_DATA)%>
														</textarea>
													</td> <%
												} else if (datatype.equalsIgnoreCase(CtgUtil.CTNT_NUMBER)) { %>
													<td align="center" id="<%= "cell" + ctntIDString %>">
														<input type="text"
														       size="6"
														       id="editnumber_<%= ctntIDString %>"
														       name="<%= AccessController.P_CONTENT_NUMBER + SEP + ctntIDString %>"
														       value="<%=content.getAttribute(XMLBuilder.A_DATA)%>">
														<script language="javascript">
															registerIntegerFormatTextbox('editnumber_<%= ctntIDString %>');
														</script>
													</td> <%
												} else if (datatype.equalsIgnoreCase(CtgUtil.CTNT_URL)) { %>
													<td align="center" id="<%= "cell" + ctntIDString %>">
														<table border="0">
															<tr class="no_borders">
																<td align="right">
																	URL:
																</td>
																<td align="left">
																	<input type="text"
																	       size="20"
																	       name="<%= AccessController.P_CONTENT_URLADDRESS + SEP + ctntIDString %>"
																	       value="<%=content.getAttribute(XMLBuilder.A_URL)%>">
																</td>
															</tr>
															<tr>
																<td border="0" align="right">
																	Displayed link name:
																</td>
																<td border="0" align="left">
																	<input type="text"
																	       size="20"
																	       name="<%= AccessController.P_CONTENT_URLLABEL + SEP + ctntIDString %>"
																	       value="<%=content.getAttribute(XMLBuilder.A_LINKNAME)%>">
																</td>
															</tr>
														</table>
													</td> <%
												}
											}/*End Each Column Of A Row*/ %>
											<td align="center">
												<input type="checkbox"
												       name="<%= AccessController.P_REMOVEROW + SEP + rowIDString %>">
											</td>
										</tr> <%
									} /*End Each Row */ %>
								</table><%
							} %>
						</div>

						<script type="text/javascript">
							/*
							make sure new row IDs don't overlap with existing ones in this category
							(so CategoryCtntsOption can put them all into the same hashtable)
							*/
							var maxExistingRowID = 0;
							for (var i = 0; i < existingRowIDs.length; i++)
								if (existingRowIDs[i] > maxExistingRowID)
									maxExistingRowID = existingRowIDs[i];
							var nextNewRowID = maxExistingRowID + 1;
						</script>

						<div id="newcontents" class="showhide">
							<br>
							<h2>Add Data:</h2>
							<div id="addsub">
								<a href="#" onClick="addSubRow(); return false;">(New Row)</a>
							</div>
							<script type="text/javascript">
								var subtable = document.createElement('table');
								subtable.id = 'subtable';
								subtable.className = 'assignment_table';
								subtable.cellPadding = 0;
								subtable.cellSpacing = 0;
								var subtbody = document.createElement('tbody');
								/*
								 * Clone the header row of the existing-contents table to be the header row for the new-contents
								 * table. Note getElementById() is probably still safe to use; in any reasonable implementation
								 * it ought to find the *first* such element, which would be the one in the existing-contents table.
								 * --Evan, 12 / 4 / 05
								 */
								var headerRow = getElementById('ctnts').getElementsByTagName('tr')[0];
								subtbody.appendChild(headerRow.cloneNode(true)); //cloneNode(true) gives a deep copy of a DOM subtree
								subtable.appendChild(subtbody);
								var sib = getElementById('addsub');
								sib.parentNode.insertBefore(subtable, sib);
							</script>
						</div>

						<% /* display hidden rows, to be restored */
						if (columnList.getLength() > 0)
						{
							long[] colIDArr = new long[columnList.getLength()];
							NodeList rowListHidden = CategoryXMLUtil.getHiddenRowList(category);
							if (rowListHidden.getLength() > 0)
							{%>
								<div class="replace">
									<span id="removedcontentshead">
										<a href="#"
										   onClick="show('removedcontents',
										                 'Removed contents &raquo;',
										                 '&laquo; Removed contents');
										            return false;">Removed contents &raquo;</a>
									</span>
									<table class="replacebody" id="removedcontents" cellpadding="0" cellspacing="0">
										<tr> <%
											for (int column=0; column < columnList.getLength(); column ++) {
												Element ctgCol = (Element)columnList.item(column);
												colIDArr[column] = Long.parseLong(ctgCol.getAttribute(XMLBuilder.A_ID));%>
												<th id="<%= colIDArr[column] %>" title="<%= ctgCol.getAttribute(XMLBuilder.A_TYPE) %>"> <%
													if (ctgCol.getAttribute(XMLBuilder.A_HIDDEN).equals("true")) { %>
														<font color="#8888aa"> <%= ctgCol.getAttribute(XMLBuilder.A_NAME) + " [HIDDEN]" %> </font> <%
													} else { %>
														<font> <%= ctgCol.getAttribute(XMLBuilder.A_NAME) %> </font> <%
													} %>
												</th> <%
											} %>
											<th>Restore</th>
										</tr> <%
										for (int row=0; row<rowListHidden.getLength(); row++) {
											/* display hidden rows */
											Element ctgRow = (Element)rowListHidden.item(row);
											long ctgRowID = Long.parseLong(ctgRow.getAttribute(XMLBuilder.A_ID));
											String rowIDString = "" + ctgRowID;
											NodeList contentList = CategoryXMLUtil.getContentList(ctgRow); %>
											<tr class="<%= (row % 2 == 0) ? "row_even" : "row_odd" %>"> <%
												for (int column=0; column<contentList.getLength(); column++) {
													/*Each Column Of A Row*/
													Element content = (Element)contentList.item(column);
													String datatype = content.getAttribute(XMLBuilder.A_TYPE); %>
													<td align="center"> <%
														if (datatype.equalsIgnoreCase(CtgUtil.CTNT_URL)) { %>
															<%= CategoryXMLUtil.printURL(content) %> <%
														} else if (datatype.equalsIgnoreCase(CtgUtil.CTNT_FILE)) { %>
															<%= CategoryXMLUtil.printFile(content) %> <%
														} else if (datatype.equalsIgnoreCase(CtgUtil.CTNT_TEXT)) { %>
															<%= CategoryXMLUtil.printText(content) %> <%
														} else if (datatype.equalsIgnoreCase(CtgUtil.CTNT_DATE)) { %>
															<%= CategoryXMLUtil.printDate(content) %> <%
														} else if (datatype.equalsIgnoreCase(CtgUtil.CTNT_NUMBER)) { %>
															<%= CategoryXMLUtil.printNumber(content) %> <%
														} %>
													</td> <%
												} %>
												<td>
													<input type="checkbox"
													       name="<%= AccessController.P_RESTOREROW + SEP + rowIDString %>">
												</td>
											</tr> <%
										} /* end hidden rows */ %>
									</table>
								</div> <%
							}
						}
						/* end code to add & edit category contents */ %>
					</div>
						<div class="assignment_left">
							<input type="submit" value="Submit">
						</div>
					</div>
				</form>
			</td>
			<td id="course_menu_container"><div id="course_menu_top">&nbsp;</div></td>
		</tr>
<jsp:include page="../../footer.jsp"/>

<!--
vim: ts=2 sw=2 ai list
-->
