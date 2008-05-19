/*
 * Created on Jul 19, 2004
 */
package cms.www;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.Date;
import java.text.ParseException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
// import org.apache.crimson.jaxp.DocumentBuilderFactoryImpl;
import javax.xml.parsers.DocumentBuilderFactory; // java 5 replacement for
                                                  // DocumentBuilderFactoryImpl

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cms.www.util.CSVFileFormatsUtil;
import cms.www.util.CSVParseException;
import cms.www.util.DateTimeUtil;
import cms.www.util.DownloadFile;
import cms.www.util.Emailer;
import cms.www.util.FileUtil;
import cms.www.util.Profiler;
import cms.www.util.StringUtil;
import cms.www.util.Util;
import cms.www.xml.XMLBuilder;
import cms.www.xml.XMLUtil;

import com.Ostermiller.util.CSVParse;
import com.Ostermiller.util.CSVPrinter;
import com.Ostermiller.util.ExcelCSVParser;
import com.Ostermiller.util.ExcelCSVPrinter;

import cms.auth.Principal;
import cms.model.*;

/**
 * TransactionHandler is a wrapper class for TransactionBean. For each
 * transaction the system supports, the servlet passes the transaction
 * parameters to the methods in this class, the validity of the arguments is
 * verified, and meaningful error messages are produced to be displayed to the
 * end user on any sort of transaction failure.
 * 
 * @author Jon
 */
public class TransactionHandler {
  private static CMSRoot database = null;

  private static TransactionsLocal transactions = null;
  private DocumentBuilder db = null;
  private Properties env;

  public TransactionHandler() {
    try {
      if (transactions == null) {
        transactions = getHome().create();
      }
      if (database == null) {
        database = transactions.getRoot();
      }
      db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Accepts an invitation to a user. Has the side effect of rejecting any other
   * invitations for the same assignment, as well as removing the user from any
   * other groups they are currently active in.
   * 
   * @param netid
   *          The NetID of the user
   * @param groupid
   *          The GroupID of the group to accept into
   * @return TransactionResult
   */
  public TransactionResult acceptInvitation(Principal p, Group group) {
    TransactionResult result = new TransactionResult();
    try {
      String netid = p.getNetID();
      GroupLocal group = null;
      try {
        group = database.groupHome().findByGroupID(groupid);
      } catch (Exception e) {
      }
      AssignmentLocal assignment = null;
      try {
        assignment = database.assignmentHome().findByGroupID(groupid);
      } catch (Exception e) {
      }
      StudentLocal student = null;
      try {
        student =
            assignment == null ? null : database.studentHome()
                .findByPrimaryKey(
                    new StudentPK(assignment.getCourseID(), netid));
      } catch (Exception e) {
      }
      GroupMemberLocal member = null, current = null;
      try {
        member =
            database.groupMemberHome().findByPrimaryKey(
                new GroupMemberPK(groupid, netid));
      } catch (Exception e) {
      }
      try {
        current =
            database.groupMemberHome().findActiveByNetIDAssignmentID(netid,
                group.getAssignmentID());
      } catch (Exception e) {
      }
      if (group == null) {
        result.addError("Invalid group entered, does not exist");
        return result;
      }
      if (assignment == null || assignment.getHidden()) {
        result.addError("No corresponding assignment exists");
        return result;
      } else if (!assignment.getStatus().equals(AssignmentBean.OPEN)) {
        result
            .addError("Group management is not currently available for this assignment");
        return result;
      }
      if (courseIsFrozen(assignment.getCourseID())) {
        result.addError("Course is frozen, no changes may be made to it");
        return result;
      }
      if (student == null || !student.getStatus().equals(StudentBean.ENROLLED)) {
        result
            .addError("Must be an enrolled student in this course to accept invitations");
        return result;
      }
      if (member == null || !member.getStatus().equals(GroupMemberBean.INVITED)) {
        if (member != null && member.getStatus().equals(GroupMemberBean.ACTIVE)) {
          result.addError("Already an active member of this group");
        } else {
          result.addError("No invitation to join this group exists");
        }
      }
      Collection grades =
          (current == null ? null : database.gradeHome()
              .findMostRecentByNetAssignmentID(current.getNetID(),
                  group.getAssignmentID()));
      if (grades != null && grades.size() > 0) {
        result.addError("Cannot change groups for this assignment");
      }
      if (!result.hasErrors()) {
        int numMembers =
            database.groupMemberHome().findActiveByGroupID(groupid).size();
        int maxSize = assignment.getGroupSizeMax();
        if (numMembers >= maxSize) {
          result.addError("Cannot join group, it is already full");
        }
        if (!result.hasErrors()) {
          if (transactions.acceptInvitation(p, groupid)) {
            result.setValue("Successfully joined group");
          } else {
            result.addError("Database failed to update invite acceptance");
          }
        }
      }

    } catch (Exception e) {
      result.addError("Database failed to update invite acceptance");
      e.printStackTrace();
    }
    return result;
  }

  /**
   * Add a current user's NetID to the list of those given CMS admin access
   * 
   * @return TransactionResult
   */
  public TransactionResult addCMSAdmin(Principal p, User admin) {
    TransactionResult result = new TransactionResult();
    boolean success = false;
    try {
      success = transactions.addCMSAdmin(p, netID.toLowerCase());
    } catch (Exception e) {
      result.addError("Database failed to add CMS admin");
      e.printStackTrace();
    }
    return result;
  }

  /**
   * Create a course under the current semester
   * 
   * @param courseCode
   *          The department and number, e.g. COM S 211
   * @param courseName
   *          The course title, e.g. Intro to Java
   * @return TransactionResult
   */
  public TransactionResult addCourse(Principal p, String courseCode,
      String courseName) {
    TransactionResult result = new TransactionResult();
    boolean success = false;
    try {
      success = transactions.createCourse(p, courseCode, courseName);
    } catch (Exception e) {
      result.addError("Database failed to create course");
      e.printStackTrace();
    }
    return result;
  }

  /**
   * Adds a sidewide notice to the database
   * 
   * @param text
   *          The text of the notice
   * @param author
   *          The author of the notice
   * @param exp
   *          The expiration time of the notice (may be null)
   * @param hidden
   *          Whether or not the annoucement is viewable by all
   * @return TransactionResult
   */
  public TransactionResult addNotice(Principal p, String text, User author,
      Date exp, boolean hidden) {
    TransactionResult result = new TransactionResult();
    boolean success = false;
    try {
      success = transactions.addSiteNotice(p, text, author, exp, hidden);
    } catch (Exception e) {
      result.addError("Database failed to create notice");
      e.printStackTrace();
    }
    return result;
  }

  /**
   * Edits a sitewide notice
   * 
   * @param p
   *          The principal of the user
   * @param id
   *          The id of the notice
   * @param text
   *          The text of the notice
   * @param exp
   *          The expiration time of the notice (may be null)
   * @param hidden
   *          Whether the notice should be hidden
   * @param deleted
   *          Whether the notice should be marked as deleteds
   * @return TransactionResult
   * @ejb.interface-method view-type="local"
   * @ejb.transaction type="Required"
   */
  public TransactionResult editNotice(Principal p, SiteNotice id, String text,
      Date exp, boolean hidden, boolean deleted) {
    TransactionResult result = new TransactionResult();
    boolean success = false;
    try {
      success = transactions.editSiteNotice(p, id, text, exp, hidden, deleted);
    } catch (Exception e) {
      result.addError("Database failed to edit notice");
      e.printStackTrace();
    }
    return result;
  }

  /**
   * download (TO cms server) files for a given category (auxiliary to
   * addCtgContents(), editCtgContents())
   * 
   * @param item
   * @param categoryID
   * @param cellCount
   *          The index of this file within its (row, col) cell
   * @param fileCount
   *          The number of files currently stored in this category
   * @param rowID
   * @param colID
   * @return A CtgFileInfo representing the file, which is now stored on disk on
   *         the server
   * @throws FileUploadException
   *           on any number of yucky conditions; IOException; ...?
   */
  private CtgFileInfo downloadCategoryFile(FileItem item, Category category,
      int cellCount, int fileCount, CategoryRow row, CategoryColumn col) throws FileUploadException {
    fileCount += 1; // the folder with ID fileCount will already have been
                    // created; let's use the next ID
    String fullFileName = FileUtil.trimFilePath(item.getName()); // title +
                                                                  // extension
    /*
     * TODO category ID is independent of course, so we don't really need course
     * ID here; but note that removing it will require changing the CMS
     * filesystem structure. For now use course ID 0 for everything
     */
    Course course = 0;
    java.io.File file =
        new File(FileUtil.getCategoryFileSystemPath(courseID, categoryID,
            rowID, colID, fileCount, fullFileName)), parent =
        file.getParentFile();
    if (parent.exists())
      throw new FileUploadException("Failed to create a unique file path");
    if (!parent.mkdirs())
      throw new FileUploadException("Failed to create new local directories");
    if (!file.createNewFile())
      throw new FileUploadException(
          "Failed to create a new file in local directory");
    item.write(file); // actually copy the file data to a specified place in the
                      // filesystem
    return new CtgFileInfo(categoryID, fullFileName, cellCount, file
        .getParent());
  }

  /**
   * Put together a CategoryCtntsOption structure describing all changes to make
   * to the given category, and pass it to TransactionsBean for actual DB
   * changes. Space efficiency in the database: When a cell is created it isn't
   * entered into the database because there's no data in it yet. It is editable
   * from the content-edit page; when non-empty data is entered there for the
   * first time, a database entry is created. We don't remove that entry if the
   * data is subsequently deleted, because this seems very unlikely to happen in
   * practice.
   * 
   * @return TransactionResult
   */
  public TransactionResult addNEditCtgContents(Principal p, Category ctgID,
      HttpServletRequest request) {
    Profiler.enterMethod("TransactionHandler.addNEditCtgContents",
        "CategoryID: " + ctgID);
    TransactionResult result = new TransactionResult();
    try {
      CategoryLocal cat =
          database.categoryHome().findByPrimaryKey(new CategoryPK(ctgID));
      if (courseIsFrozen(cat.getCourseID()))
        result.addError("Course is frozen; no changes may be made to it");
      else if (cat == null)
        result.addError("Couldn't find content in database");
      else {
        CategoryCtntsOption contents = new CategoryCtntsOption(ctgID);
        DiskFileUpload upload = new DiskFileUpload();
        List params =
            upload.parseRequest(request, 1024, AccessController.maxFileSize,
                FileUtil.TEMP_DIR);
        /*
         * Create a map from the row ID used by the JSP to an ArrayList with all
         * FileItems representing content adds that need to be be put into that
         * row, so we can create each row as a unit instead of haphazardly
         */
        Iterator i = params.iterator();
        HashMap rowMap = new HashMap(); // map rowID from JSP to ArrayList of
                                        // FileItems
        ArrayList editItems = new ArrayList(); // the FileItems that won't be
                                                // processed through rowMap
        while (i.hasNext()) {
          FileItem item = (FileItem) i.next();
          // list the category rows we'll need to create new db entries for
          if (item.getFieldName().startsWith(
              AccessController.P_PREFIX_NEW_CONTENT)) {
            // '_' separates info bits in the request param
            String[] msgParts = item.getFieldName().split("_");
            long rowID = Long.parseLong(msgParts[1]);
            if (!rowMap.containsKey(new Long(rowID))) // need to create a record
                                                      // for this row
              rowMap.put(new Long(rowID), new ArrayList());
            ((ArrayList) rowMap.get(new Long(rowID))).add(item);
          } else editItems.add(item);
        }
        /*
         * **************************************************************************
         * format of add request param: <TYPE>_<ROW>_<COL>[_<FILE_INDEX>]
         * **************************************************************************
         * format of edit request param: <TYPE>_<CONTENT>[_<FILE_INDEX>] to
         * change data or download a file OR <TYPE>_<ROW>_<COL>[_<FILE_INDEX>]
         * to change data that isn't in the db yet because its cell is currently
         * empty OR <TYPE>_<ROW> or <TYPE>_<FILEID> to hide/unhide a row or
         * file
         * **************************************************************************
         * <COL> is the column ID in the database; <ROW> is a unique row ID just
         * for the HTML form <CONTENT> is the content ID in the database <ROW>
         * is the row ID in the database (not generated by the JSP, as when
         * adding content) <FILE_INDEX> is the index of a file within its
         * content cell
         */
        long curCtgFileCount = cat.getFileCount(); // will be updated at each
                                                    // new file download
        // go through our map and add the data for each row in turn to the
        // contents info structure
        Set rowIDs = rowMap.keySet();
        i = rowIDs.iterator();
        System.out.println("NOW ADDING");
        while (i.hasNext()) {
          Long jspRowID = (Long) i.next();
          long rowID = contents.createNewRow(jspRowID.longValue());
          ArrayList itemList = (ArrayList) rowMap.get(jspRowID);
          for (int j = 0; j < itemList.size(); j++) {
            FileItem item = (FileItem) itemList.get(j);
            if (item.isFormField())
              System.out.println("addNEditCtgCtnts(): file item: "
                  + item.getFieldName() + "=" + item.getString());
            else System.out.println("addNEditCtgCtnts(): file item: "
                + item.getFieldName() + "=" + item.getName());
            String[] msgParts = item.getFieldName().split("_");
            /*
             * the start strings we check for here ("text", "url", etc) need to
             * match those used by categoryscript.js in web/staff/category
             */
            if (msgParts[0].equals(AccessController.P_NEW_CONTENT_TEXT)) // add
                                                                          // arbitrary
                                                                          // text/html
            {
              long colID = Long.parseLong(msgParts[2]);
              // don't add a db entry if there's no information yet
              if (item.getString().length() > 0)
                contents.addNewCtntText(colID, rowID, item.getString());
            } else if (msgParts[0].equals(AccessController.P_NEW_CONTENT_DATE)) // add
                                                                                // a
                                                                                // date
            {
              long colID = Long.parseLong(msgParts[2]);
              String dateStr = item.getString();
              Date date =
                  dateStr.equals("") ? null : DateTimeUtil.parseDate(dateStr,
                      DateTimeUtil.DATE);
              // don't add a db entry if there's no information yet
              if (date != null) contents.addNewCtntDate(colID, rowID, date);
            } else if (msgParts[0]
                .equals(AccessController.P_NEW_CONTENT_NUMBER)) // add an index
                                                                // for the
                                                                // content row
            {
              try {
                Long number = new Long(item.getString());
                long colID = Long.parseLong(msgParts[2]);
                contents.addNewCtntNumber(colID, rowID, number);
              } catch (NumberFormatException x) // shouldn't happen; we check
                                                // string format on the JSP
              {
                System.out
                    .println("shouldn't happen: NumFmtXcp in XHdlr::addNEditCtnts()!");
              }
            } else if (msgParts[0].equals(AccessController.P_NEW_CONTENT_FILE)) // add
                                                                                // an
                                                                                // actual
                                                                                // file
            {
              long colID = Long.parseLong(msgParts[2]), fileNum =
                  Long.parseLong(msgParts[3]); // index of file within content
              CtgFileInfo fileInfo;
              // check for whether a file was uploaded by whether its name
              // exists
              if (item.getName() != null && item.getName().length() > 0)
                fileInfo =
                    downloadCategoryFile(item, ctgID, fileNum,
                        ++curCtgFileCount, rowID, colID);
              else fileInfo = new CtgFileInfo(ctgID, null, fileNum, null); // no
                                                                            // actual
                                                                            // file
              contents.addNewCtntFile(colID, rowID, fileNum, fileInfo);
            } else if (msgParts[0]
                .equals(AccessController.P_NEW_CONTENT_FILELABEL)) // add file
                                                                    // to
                                                                    // content
                                                                    // in row
                                                                    // about to
                                                                    // be
                                                                    // created
            {
              long colID = Long.parseLong(msgParts[2]), fileNum =
                  Long.parseLong(msgParts[3]); // index of file within content
              // don't make sure string is non-empty; that will be done later
              contents.addNewCtntFileLabel(colID, rowID, fileNum, item
                  .getString());
            } else if (msgParts[0]
                .equals(AccessController.P_NEW_CONTENT_URLADDRESS)) // add an
                                                                    // address
                                                                    // link
                                                                    // points to
            {
              long colID = Long.parseLong(msgParts[2]);
              // don't add a db entry if there's no information yet
              if (item.getString().length() > 0)
                contents.addNewCtntURL(colID, rowID, item.getString());
            } else if (msgParts[0]
                .equals(AccessController.P_NEW_CONTENT_URLLABEL)) // add a
                                                                  // displayed
                                                                  // name for
                                                                  // link
            {
              long colID = Long.parseLong(msgParts[2]);
              // don't add a db entry if there's no information yet
              if (item.getString().length() > 0)
                contents.addNewCtntURLLabel(colID, rowID, item.getString());
            }
          }
        }
        System.out.println("NOW EDITING");
        // process items that work on existing content and so whose row IDs
        // refer to existing db entries
        for (int j = 0; j < editItems.size(); j++) {
          FileItem item = (FileItem) editItems.get(j);
          if (item.isFormField())
            System.out.println("addNEditCtgCtnts(): file item: "
                + item.getFieldName() + "=" + item.getString());
          else System.out.println("addNEditCtgCtnts(): file item: "
              + item.getFieldName() + "=" + item.getName());
          String[] msgParts = item.getFieldName().split("_");
          if (item.isFormField()) {
            /*
             * the start strings we check for here ("text", "url", etc) need to
             * match those used by categoryscript.js in web/staff/category
             */
            if (msgParts[0].equals(AccessController.P_CONTENT_TEXT)) // edit
                                                                      // existing
                                                                      // text/html
            {
              if (msgParts.length == 2) // this content exists in the db
              {
                long contentID = Long.parseLong(msgParts[1]);
                contents.changeCtntText(contentID, item.getString());
              } else if (msgParts.length == 3) // this content doesn't yet
                                                // exist in the db
              {
                long rowID = Long.parseLong(msgParts[1]), colID =
                    Long.parseLong(msgParts[2]);
                // don't add a db entry if there's no information yet
                if (item.getString().length() > 0)
                  contents.addNewCtntText(colID, rowID, item.getString());
              }
            } else if (msgParts[0].equals(AccessController.P_CONTENT_DATE)) // edit
                                                                            // an
                                                                            // existing
                                                                            // date
            {
              String dateStr = item.getString();
              Date date =
                  dateStr.equals("") ? null : DateTimeUtil.parseDate(dateStr,
                      DateTimeUtil.DATE);
              if (msgParts.length == 2) // this content exists in the db
              {
                long contentID = Long.parseLong(msgParts[1]);
                contents.changeCtntDate(contentID, date);
              } else if (msgParts.length == 3) // this content doesn't yet
                                                // exist in the db
              {
                long rowID = Long.parseLong(msgParts[1]), colID =
                    Long.parseLong(msgParts[2]);
                // don't add a db entry if there's no information yet
                if (date != null) contents.addNewCtntDate(colID, rowID, date);
              }
            } else if (msgParts[0].equals(AccessController.P_CONTENT_NUMBER)) // edit
                                                                              // an
                                                                              // index
                                                                              // for
                                                                              // the
                                                                              // content
                                                                              // row
            {
              Long number;
              try {
                number = new Long(item.getString());
              } catch (NumberFormatException x) // shouldn't happen; we check
                                                // string format on the JSP
              {
                number = null;
              }
              if (msgParts.length == 2) // this content exists in the db
              {
                long contentID = Long.parseLong(msgParts[1]);
                // don't add a db entry if there's no information yet
                if (number != null)
                  contents.changeCtntNumber(contentID, number);
              } else if (msgParts.length == 3) // this content doesn't yet
                                                // exist in the db
              {
                long rowID = Long.parseLong(msgParts[1]), colID =
                    Long.parseLong(msgParts[2]);
                // don't add a db entry if there's no information yet
                if (number != null)
                  contents.addNewCtntNumber(colID, rowID, number);
              }
            } else if (msgParts[0].equals(AccessController.P_CONTENT_FILELABEL)) // edit
                                                                                  // or
                                                                                  // add
                                                                                  // a
                                                                                  // displayed
                                                                                  // name
                                                                                  // for
                                                                                  // a
                                                                                  // file
            {
              if (msgParts.length == 2) // edit label of existing file
              {
                long fileID = Long.parseLong(msgParts[1]);
                contents.changeCtntFileLabel(fileID, item.getString());
              } else if (msgParts.length == 3) // add file to existing content
              {
                long contentID = Long.parseLong(msgParts[1]);
                int fileNum = Integer.parseInt(msgParts[2]); // index of file
                                                              // within content
                // don't make sure string is non-empty; that will be done later
                contents.addCtntFileLabel(contentID, fileNum, item.getString());
              } else if (msgParts.length == 4) // add file to empty content in
                                                // existing row
              {
                long rowID = Long.parseLong(msgParts[1]), colID =
                    Long.parseLong(msgParts[2]), fileNum =
                    Long.parseLong(msgParts[3]); // index of file within
                                                  // content
                // don't make sure string is non-empty; that will be done later
                contents.addNewCtntFileLabel(colID, rowID, fileNum, item
                    .getString());
              }
            } else if (msgParts[0]
                .equals(AccessController.P_CONTENT_URLADDRESS)) // edit the
                                                                // address link
                                                                // points to
            {
              if (msgParts.length == 2) // this content exists in the db
              {
                long contentID = Long.parseLong(msgParts[1]);
                contents.changeCtntURL(contentID, item.getString());
              } else if (msgParts.length == 3) // this content doesn't yet
                                                // exist in the db
              {
                long rowID = Long.parseLong(msgParts[1]), colID =
                    Long.parseLong(msgParts[2]);
                // don't add a db entry if there's no information yet
                if (item.getString().length() > 0)
                  contents.addNewCtntURL(colID, rowID, item.getString());
              }
            } else if (msgParts[0].equals(AccessController.P_CONTENT_URLLABEL)) // edit
                                                                                // a
                                                                                // displayed
                                                                                // name
                                                                                // for
                                                                                // link
            {
              if (msgParts.length == 2) // this content exists in the db
              {
                long contentID = Long.parseLong(msgParts[1]);
                contents.changeCtntURLLabel(contentID, item.getString());
              } else if (msgParts.length == 3) // this content doesn't yet
                                                // exist in the db
              {
                long rowID = Long.parseLong(msgParts[1]), colID =
                    Long.parseLong(msgParts[2]);
                // don't add a db entry if there's no information yet
                if (item.getString().length() > 0)
                  contents.addNewCtntURLLabel(colID, rowID, item.getString());
              }
            } else // not a content-edit change; check for hide/unhide
            {
              if (msgParts[0].equals(AccessController.P_REMOVEROW)) {
                long rowID = Long.parseLong(msgParts[1]);
                contents.removeRow(rowID);
              } else if (msgParts[0].equals(AccessController.P_RESTOREROW)) {
                long rowID = Long.parseLong(msgParts[1]);
                contents.restoreRow(rowID);
              } else if (msgParts[0].equals(AccessController.P_REMOVEFILE)) {
                /*
                 * note: can only remove an existing file here; removal of files
                 * not yet created is done by Javascript on the client side
                 */
                long fileID = Long.parseLong(msgParts[1]);
                contents.removeFile(fileID);
              }
            }
          } else // a file upload (a new file in an existing cell or an empty
                  // cell in an existing row)
          {
            if (msgParts[0].equals(AccessController.P_CONTENT_FILE)) {
              if (msgParts.length == 3) // add a new file to an existing cell
              {
                long contentID = Long.parseLong(msgParts[1]);
                CategoryContentsLocal content =
                    database.categoryContentsHome().findByPrimaryKey(
                        new CategoryContentsPK(contentID));
                long fileNum = Long.parseLong(msgParts[2]); // index of file
                                                            // within content
                CtgFileInfo fileInfo;
                // check whether a file was uploaded
                if (item.getName() != null && item.getName().length() > 0) // make
                                                                            // sure
                                                                            // the
                                                                            // filename
                                                                            // exists
                  fileInfo =
                      downloadCategoryFile(item, ctgID, fileNum,
                          ++curCtgFileCount, content.getRowID(), content
                              .getColumnID());
                else fileInfo = new CtgFileInfo(ctgID, null, fileNum, null); // no
                                                                              // actual
                                                                              // file
                contents.addCtntFile(contentID, fileNum, fileInfo);
              } else if (msgParts.length == 4) // add a file to an empty cell
                                                // in an existing row
              {
                long rowID = Long.parseLong(msgParts[1]), colID =
                    Long.parseLong(msgParts[2]), fileNum =
                    Long.parseLong(msgParts[3]); // index of file within
                                                  // content
                CtgFileInfo fileInfo;
                // check whether a file was uploaded
                if (item.getName() != null && item.getName().length() > 0) // make
                                                                            // sure
                                                                            // the
                                                                            // filename
                                                                            // exists
                  fileInfo =
                      downloadCategoryFile(item, ctgID, fileNum,
                          ++curCtgFileCount, rowID, colID);
                else fileInfo = new CtgFileInfo(ctgID, null, fileNum, null); // no
                                                                              // actual
                                                                              // file
                contents.addNewCtntFile(colID, rowID, fileNum, fileInfo);
              }
            }
          }
        }
        contents.removeEmptyFiles(); // make sure we don't add any files where
                                      // all the input was empty
        if (!transactions.addNEditCtgContents(p, contents, curCtgFileCount))
          result.addError("Unexpected error while trying to add/edit data");
      }
    } catch (FileUploadException e) {
      result.addError(FileUtil.checkFileException(e));
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
      result.addError("Error: Date contents must be of the form MMMM DD, YYYY");
    } catch (Exception e) {
      e.printStackTrace();
      result.addError("Unexpected error while trying to add/edit data");
    }
    Profiler.exitMethod("TransactionHandler.addNEditCtgContents",
        "CategoryID: " + ctgID);
    return result;
  }

  /**
   * Sets grades and comments for a course. Also accepts submitted files and
   * regrade requests entered by staff members on behalf of a group.
   * 
   * @param p
   * @param isAssign
   *          True if this transaction request comes from the single Assignment
   *          Grade page as opposed to the setting grades for a student in all
   *          assignments page.
   * @param ID
   *          If isAssign is true, an assignment ID; else a course ID
   * @param request
   * @return TransactionResult containing with success set false and with
   *         appended errors if the transaction failed. If it completed
   *         successfully and isAssign is true, then success is true, and the
   *         value object of the TransactionResult is set to a length 2 object
   *         array containing the success message, followed by a List of
   *         GroupIDs (for reloading the page). If the transaction was
   *         successful and isAssign is false, the TransactionResult value is
   *         set to just the success message.
   */
  public TransactionResult addGradesComments(Principal p, boolean isAssign,
      Object data, HttpServletRequest request) {
    Profiler.enterMethod("TransactionHandler.addGradesComments", "");
    TransactionResult result = new TransactionResult();
    try {
      DiskFileUpload upload = new DiskFileUpload();
      List info =
          upload.parseRequest(request, 1024, AccessController.maxFileSize,
              FileUtil.TEMP_DIR);
      Iterator i = info.iterator();
      GradeCommentInfo data = new GradeCommentInfo();
      Map assignIDs = isAssign ? null : database.getAssignmentIDMap(data);
      AssignmentLocal assignment =
          isAssign ? database.assignmentHome().findByAssignmentID(data) : null;
      if (isAssign ? courseIsFrozen(assignment.getCourseID())
          : courseIsFrozen(ID)) {
        result.addError("Course is frozen, cannot make changes");
        return result;
      }
      Map subProbNames =
          isAssign ? database.getSubProblemNameMap(ID) : database
              .getSubProblemNameMapByCourse(ID);
      ArrayList groupIDs = new ArrayList();
      while (i.hasNext()) {
        FileItem item = (FileItem) i.next();
        String field = item.getFieldName();
        if (field.startsWith(AccessController.P_GRADE)) {
          String[] vals = field.split("_");
          long subProbID = Long.parseLong(vals[2]);
          Group group = Long.parseLong(vals[3]);
          try {
            String scoreStr = item.getString().trim();
            if (!scoreStr.equals("")) {
              float score = Float.parseFloat(scoreStr);
              data.addScore(vals[1], subProbID, new Float(score), groupID);
            } else data.addScore(vals[1], subProbID, null, groupID);
          } catch (NumberFormatException e) {
            result.addError("Grade for '" + vals[1] + "' on problem '"
                + ((String) subProbNames.get(new Long(subProbID)))
                + "' is not a valid floating point number.");
          }
        } else if (field.startsWith(AccessController.P_OLDGRADE)) {
          String[] vals = field.split("_");
          long subProbID = Long.parseLong(vals[2]);
          Group group = Long.parseLong(vals[3]);
          try {
            String scoreStr = item.getString().trim();
            if (!scoreStr.equals("")) {
              float score = StringUtil.parseFloat(scoreStr);
              data.addOldScore(vals[1], subProbID, score, groupID);
            }
          } catch (NumberFormatException e) {
            /*
             * This shouldn't happen as P_OLDGRADE represents the score from the
             * database as of the time the user loaded the web page. We should
             * certainly know about it if it happens to though.
             */
            e.printStackTrace();
            result
                .addError("An unexpected error occurred and grades could not be committed.  Contact CMS staff.");
          }
        } else if (field.startsWith(AccessController.P_COMMENTTEXT)) {
          Group group =
              Long.parseLong(field.split(AccessController.P_COMMENTTEXT)[1]);
          data.addCommentText(groupID, item.getString());
        } else if (field.startsWith(AccessController.P_COMMENTFILE)) {
          String fileName = FileUtil.trimFilePath(item.getName());
          if (fileName.equals("")) {
            continue;
          }
          Group group =
              Long.parseLong(field.split(AccessController.P_COMMENTFILE)[1]);
          int fileCounter = transactions.getGroupFileCounter(groupID);
          java.io.File path, file;
          Course course = isAssign ? assignment.getCourseID() : ID;
          Assignment assign =
              isAssign ? ID : ((Long) assignIDs.get(new Long(groupID)))
                  .longValue();
          file =
              new java.io.File(FileUtil.getCommentFileSystemPath(courseID,
                  assignID, groupID, fileCounter, fileName));
          path = file.getParentFile();
          if (path.exists() || !path.mkdirs()) {
            result.addError("Could not get unique path for new comment file.");
            continue;
          }
          if (!file.createNewFile()) {
            result
                .addError("Could not create a new file location on the local file system.");
            continue;
          }
          item.write(file);
          data.addCommentFile(groupID, new CommentFileData(0, 0, fileName, path
              .getAbsolutePath()));
        } else if (field.startsWith(AccessController.P_SUBMITTEDFILE)) {
          String[] vals = field.split("_");
          Group group = Long.parseLong(vals[1]);
          long submissionID = Long.parseLong(vals[2]);
          String fileName = FileUtil.trimFilePath(item.getName());
          String[] splitName = FileUtil.splitFileNameType(fileName);
          if (fileName.equals("")) {
            continue;
          }
          int fileCounter = transactions.getGroupFileCounter(groupID);
          java.io.File path, file;
          Course course = isAssign ? assignment.getCourseID() : ID;
          Assignment assign =
              isAssign ? ID : ((Long) assignIDs.get(new Long(groupID)))
                  .longValue();
          file =
              new java.io.File(FileUtil.getSubmittedFileSystemPath(courseID,
                  assignID, groupID, fileCounter, submissionID, splitName[1]));
          path = file.getParentFile();
          if (path.exists() || !path.mkdirs()) {
            result
                .addError("Could not get unique path for new submitted file.");
            continue;
          }
          if (!file.createNewFile()) {
            result
                .addError("Could not create a new file location on the local file system.");
            continue;
          }
          String MD5 = FileUtil.calcMD5(item);
          item.write(file);
          data.addSubmittedFile(assignID, fileName, new SubmittedFileData(0,
              groupID, groupID, p.getNetID(), submissionID, null, splitName[1],
              (int) item.getSize(), MD5, false, /* pathname */path
                  .getAbsolutePath()));
        } else if (field.startsWith(AccessController.P_REGRADERESPONSE)) {
          String[] vals = field.split("_");
          long requestID = Long.parseLong(vals[1]);
          Group group = Long.parseLong(vals[2]);
          data.addRegradeResponse(groupID, requestID);
        } else if (field.startsWith(AccessController.P_REGRADESUB)) {
          String[] vals = field.split("_");
          long subProbID = Long.parseLong(vals[1]);
          Group group = Long.parseLong(vals[2]);
          data.addNewRegradeSubProb(groupID, subProbID);
        } else if (field.startsWith(AccessController.P_REGRADEWHOLE)) {
          Group group = Long.parseLong((field.split("_"))[1]);
          data.addNewRegradeSubProb(groupID, 0);
        } else if (field.startsWith(AccessController.P_REGRADEREQUEST)) {
          Group group = Long.parseLong((field.split("_"))[1]);
          data.addNewRegrade(groupID, item.getString());
        } else if (field.startsWith(AccessController.P_REGRADENETID)) {
          Group group = Long.parseLong((field.split("_"))[1]);
          data.addNewRegradeNetID(groupID, item.getString());
        } else if (field.startsWith(AccessController.P_GROUPID)) {
          groupIDs.add(new Long(Long.parseLong(item.getString())));
        } else if (field.startsWith(AccessController.P_REMOVECOMMENT)) {
          long commentID =
              Long.parseLong(field.substring(AccessController.P_REMOVECOMMENT
                  .length()));
          data.addRemovedComment(commentID);
        }
      }
      if (isAssign) result.setValue(new Object[] { null, groupIDs });
      /*
       * If this is an Assignment-level transaction and the assignment is set to
       * assigned graders only, we must check that the grader is not setting
       * anything he's not allowed to. (This shouldn't be possible, since the
       * form elements the grader doesn't have permission for should be disabled
       * or missing, but just in case)
       */
      if (isAssign && !p.isAdminPrivByCourseID(assignment.getCourseID())) {
        AssignmentLocal assign =
            database.assignmentHome().findByAssignmentID(ID);
        if (assign.getAssignedGraders()) {
          boolean permission =
              database.assignedToGroups(ID, p.getNetID(), groupIDs).size() == groupIDs
                  .size();
          HashSet canGrades = new HashSet();
          Iterator assignTos =
              database.groupAssignedToHome().findByNetIDAssignmentID(
                  p.getNetID(), ID).iterator();
          while (assignTos.hasNext()) {
            GroupAssignedToLocal a = (GroupAssignedToLocal) assignTos.next();
            canGrades.add(a.getGroupID() + "_" + a.getSubProblemID());
          }
          for (int j = 0; j < data.getScores().size(); j++) {
            Object[] grade = (Object[]) data.getScores().get(j);
            Long groupid = (Long) grade[3];
            Long subprobid = (Long) grade[1];
            permission =
                permission && canGrades.contains(groupid + "_" + subprobid);
          }
          if (!permission) {
            result
                .addError("Permission violation: You are not allowed to set these grades");
          }
        }
      }
      if (!result.hasErrors()) {
        if (isAssign) {
          result = transactions.addGradesComments(p, ID, data);
          // If commit went through successfully and updates were made, update
          // statistics
          if (result.getSuccess()
              && ((Boolean) result.getValue()).booleanValue()) {
            try {
              transactions.computeAssignmentStats(p, ID, (LogData) null);
              transactions.computeTotalScores(p, assignment.getCourseID(),
                  (LogData) null);
            } catch (Exception e) {
              e.printStackTrace();
              result
                  .addError("Grades committed, but failed to compute updated statistics");
            }
          }
        } else {
          result = transactions.addAllAssignsGrades(p, ID, data);
          if (result.getSuccess()) {
            try {
              Iterator assigns = (Iterator) result.getValue();
              while (assigns.hasNext()) {
                Long assignID = (Long) assigns.next();
                transactions.computeAssignmentStats(p, assignID.longValue(),
                    (LogData) null);
              }
              transactions.computeTotalScores(p, ID, (LogData) null);
            } catch (Exception e) {
              e.printStackTrace();
              result
                  .addError("Grades committed, but failed to compute updated statistics");
            }
          }
        }
        String msg =
            result.getSuccess() ? "Grades/comments updated successfully"
                : "Database did not update grades and comments";
        if (isAssign) {
          Object[] pack = new Object[2];
          pack[0] = msg;
          pack[1] = groupIDs;
          result.setValue(pack);
        } else {
          result.setValue(msg);
        }
      } else {
        result.addError("Database did not update grades and comments");
      }
    } catch (FileUploadException e) {
      result.addError(FileUtil.checkFileException(e));
    } catch (Exception e) {
      result.addError("Database did not update grades and comments");
      e.printStackTrace();
    }
    Profiler.exitMethod("TransactionHandler.addGradesComments", "");
    return result;
  }

  /**
   * Submits a regrade request for a student in specified group
   * 
   * @param groupID -
   *          ID of the group
   * @param assignID -
   *          ID of assignment regrade if for
   * @param netID -
   *          ID of student submitting the regrade
   * @param request
   * @return TransactionResult
   */
  public TransactionResult addRegradeRequest(Principal p, Group group,
      HttpServletRequest request) {
    TransactionResult result = new TransactionResult();
    boolean success;
    try {
      GroupLocal group = database.groupHome().findByGroupID(groupID);
      AssignmentLocal assignment =
          database.assignmentHome().findByAssignmentID(group.getAssignmentID());
      if (courseIsFrozen(assignment.getCourseID())) {
        result.addError("Course is frozen; no changes may be made to it");
        return result;
      }
      Collection subProblemIDs = new ArrayList();
      String requestText = null;
      for (Enumeration e = request.getParameterNames(); e.hasMoreElements();) {
        String parameter = (String) e.nextElement();
        if (parameter.startsWith(AccessController.P_REGRADESUB)) {
          String subProbID = parameter.split(AccessController.P_REGRADESUB)[1];
          subProblemIDs.add(new Long(Long.parseLong(subProbID)));
        } else if (parameter.equals(AccessController.P_REGRADEREQUEST)) {
          requestText = request.getParameter(parameter);
        }
      }
      if (request.getParameter(AccessController.P_REGRADEWHOLE) != null) { // assignment
                                                                            // doesn't
                                                                            // have
                                                                            // subProblems
        subProblemIDs.add(new Long(0));
        if (!transactions.addRegradeRequest(p, groupID, subProblemIDs,
            requestText)) {
          result.addError("Unexpected error; regrade could not be committed");
        }
      } else { // assignment has subProblems
        if (subProblemIDs.size() == 0) {
          result
              .addError("Please check the problems you would like to submit request for");
        } else {
          if (!transactions.addRegradeRequest(p, groupID, subProblemIDs,
              requestText)) {
            result.addError("Unexpected error; regrade could not be committed");
          }
        }
      }
    } catch (Exception e) {
      result.addError("Unexpected error; failed to submit regrade request");
      e.printStackTrace();
    }
    return result;
  }

  /**
   * @return TransactionResult
   */
  public TransactionResult addStudentsToCourse(Principal pr, Course course,
      HttpServletRequest request) {
    TransactionResult result = new TransactionResult();
    try {
      if (courseIsFrozen(courseID)) {
        result.addError("Course is frozen; no changes may be made to it");
      } else {
        Vector netids = new Vector();
        ExcelCSVParser p;
        boolean isFile = false;
        boolean isList = false;
        boolean emailOn = false;
        FileItem file = null;
        String list = null;
        DiskFileUpload upload = new DiskFileUpload();
        List info =
            upload.parseRequest(request, 1024, AccessController.maxFileSize,
                FileUtil.TEMP_DIR);
        Iterator i = info.iterator();
        while (i.hasNext()) {
          FileItem item = (FileItem) i.next();
          String name = item.getFieldName();
          if (AccessController.P_ADDSTUDENTSFILE.equals(name)) {
            isFile = true;
          } else if (AccessController.P_ADDSTUDENTSLIST.equals(name)) {
            isList = true;
          } else if (AccessController.P_STUDENTSFILE.equals(name)) {
            file = item;
          } else if (AccessController.P_STUDENTSLIST.equals(name)) {
            list = item.getString();
          } else if (AccessController.P_EMAILADDED.equals(name)) {
            emailOn = true;
          } else {
            result.addError("Invalid form entries");
            return result;
          }
        }
        if (isList && isFile) {
          result.addError("Invalid form entries");
          return result;
        }
        if (isList) {
          netids.addAll(StringUtil.parseNetIDList(list));
        } else // isFile
        {
          InputStream stream = file.getInputStream();
          p = new ExcelCSVParser(stream);
          String[][] a = p.getAllValues();
          for (int j = 0; j != a.length; j++) {
            for (int k = 0; k != a[j].length; k++) {
              if (!"".equals(a[j][k])) {
                netids.add(a[j][k].trim().toLowerCase());
              }
            }
          }
        }
        result =
            transactions.addStudentsToCourse(pr, netids, courseID, emailOn);
      }
    } catch (FileUploadException e) {
      result.addError(FileUtil.checkFileException(e));
    } catch (Exception e) {
      e.printStackTrace();
      result.addError("Unexpected error; could not add students");
    }
    return result;
  }

  public TransactionResult assignGrader(Principal p, Assignment assign,
      SubProblem subProblem, User grader, Map requestMap) {
    TransactionResult result = new TransactionResult();
    try {
      CourseLocal course =
          database.courseHome().findByAssignmentID(assignmentID);
      if (courseIsFrozen(course.getCourseID())) {
        result.addError("Course is frozen; no changes may be made to it");
        return result;
      }
      Map probids = database.getSubProblemIDMap(assignmentID);
      Long probid;
      if (subproblemName.equalsIgnoreCase(GroupAssignedToBean.ALLPARTS)) {
        // -1 signifies that this grader should be assigned to all SubProblems
        probid = new Long(-1);
      } else if (subproblemName == null) {
        // 0 signifies that there are no sub problems for this assignment
        probid = new Long(0);
      } else {
        probid = (Long) probids.get(subproblemName);
        if (probid == null)
          throw new FinderException("Subproblem ID not in map");
      }
      long subProblemID = probid.longValue();
      Collection groups = new LinkedList();
      Iterator i = requestMap.keySet().iterator();
      while (i.hasNext()) {
        String key = (String) i.next();
        if (key.startsWith(AccessController.P_GRADEGROUP)) {
          groups.add(new Long(Long.parseLong(key
              .split(AccessController.P_GRADEGROUP)[1])));
        }
      }
      if (groups.size() > 0) {
        boolean success =
            transactions.assignGrader(p, assignmentID, subProblemID, grader
                .toLowerCase(), groups);
        if (!success) {
          result.addError("Database failed to update TA grading assignment");
        } else {
          result.setValue("Grader assignments successfully updated");
        }
      } else {
        result.setValue("No groups selected");
      }
    } catch (Exception e) {
      e.printStackTrace();
      result.addError("Database failed to update TA grading assignment");
    }
    return result;
  }

  /**
   * Returns true only if the given principal has authorization to download the
   * given file
   * 
   * @param p
   *          The Principal to authorize
   * @param ID
   *          The ID of the file to check
   * @param type
   *          The type of the file to check
   * @return True if the given Principal has access to download the given file
   */
  public boolean authorizeDownload(Principal p, long id, int type) {
    try {
      long ID = id;
      AssignmentLocal a = null;
      GroupLocal g = null;
      CourseLocal c = null;
      switch (type) {
      case XMLBuilder.T_SOLFILE:
        SolutionFileLocal sf = null;
        try {
          sf =
              database.solutionFileHome().findByPrimaryKey(
                  new SolutionFilePK(ID));
        } catch (Exception e) {
        }
        if (sf == null) return false;
        try {
          a =
              database.assignmentHome()
                  .findByAssignmentID(sf.getAssignmentID());
        } catch (Exception e) {
        }
        if (a == null) return false;
        try {
          c =
              database.courseHome().findByPrimaryKey(
                  new CoursePK(a.getCourseID()));
        } catch (Exception e) {
        }
        if (c == null) return false;
        if (p.isStaffInCourseByCourseID(a.getCourseID())) {
          return true;
        } else if (p.isStudentInCourseByCourseID(a.getCourseID())) {
          if (a.getShowSolution()) {
            return a.getStatus().equals(AssignmentBean.CLOSED)
                || a.getStatus().equals(AssignmentBean.GRADED);
          }
          Collection grades =
              database.gradeHome().findMostRecentByNetAssignmentID(
                  p.getNetID(), a.getAssignmentID());
          return a.getStatus().equals(AssignmentBean.GRADED)
              && grades.size() > 0;
        } else if (c.getSolutionCCAccess() && p.isAuthenticated()) {
          return a.getStatus().equals(AssignmentBean.GRADED);
        } else if (c.getSolutionGuestAccess() && p.isGuest()) {
          return a.getStatus().equals(AssignmentBean.GRADED);
        } else {
          return false;
        }
      case XMLBuilder.T_CATFILE:
        CategoryFileLocal cf = null;
        try {
          cf =
              database.categoryFileHome().findByPrimaryKey(
                  new CategoryFilePK(ID));
        } catch (Exception e) {
        }
        if (cf == null) return false;
        if (cf.getFileName() == null || cf.getFileName().length() == 0) // there's
                                                                        // a
                                                                        // label
                                                                        // but
                                                                        // no
                                                                        // actual
                                                                        // file
          return false;
        CategoryContentsLocal ct = null;
        try {
          ct =
              database.categoryContentsHome().findByPrimaryKey(
                  new CategoryContentsPK(cf.getContentID()));
        } catch (Exception e) {
        }
        if (ct == null) return false;
        CategoryRowLocal cr = null;
        try {
          cr =
              database.categoryRowHome().findByPrimaryKey(
                  new CategoryRowPK(ct.getRowID()));
        } catch (Exception e) {
        }
        if (cr == null) return false;
        CategoryLocal cg = null;
        try {
          cg =
              database.categoryHome().findByPrimaryKey(
                  new CategoryPK(cr.getCategoryID()));
        } catch (Exception e) {
        }
        if (cg == null) return false;
        switch (cg.getAuthorzn()) {
        case Principal.AUTHOR_GUEST:
          return true;
        case Principal.AUTHOR_CORNELL_COMMUNITY:
          return p.isAuthenticated();
        case Principal.AUTHOR_STUDENT:
          return p.isStudentInCourseByCourseID(cg.getCourseID())
              || p.isStaffInCourseByCourseID(cg.getCourseID());
        default:
          return p.isStaffInCourseByCourseID(cg.getCourseID());
        }
      case XMLBuilder.T_COMMENTFILE:
        CommentFileLocal cmf = null;
        try {
          cmf =
              database.commentFileHome()
                  .findByPrimaryKey(new CommentFilePK(ID));
        } catch (Exception e) {
        }
        if (cmf == null) return false;
        CommentLocal cm = null;
        try {
          cm =
              database.commentHome().findByPrimaryKey(
                  new CommentPK(cmf.getCommentID()));
        } catch (Exception e) {
        }
        if (cm == null) return false;
        try {
          g = database.groupHome().findByGroupID(cm.getGroupID());
        } catch (Exception e) {
        }
        if (g == null) return false;
        try {
          a = database.assignmentHome().findByAssignmentID(g.getAssignmentID());
        } catch (Exception e) {
        }
        if (a == null) return false;
        if (p.isAdminPrivByCourseID(a.getCourseID())) {
          return true;
        } else if (p.isGradesPrivByCourseID(a.getCourseID())) {
          if (a.getAssignedGraders()) {
            Iterator ats =
                database.groupAssignedToHome().findByGroupID(g.getGroupID())
                    .iterator();
            boolean isAssigned = false;
            while (ats.hasNext()) {
              GroupAssignedToLocal gt = (GroupAssignedToLocal) ats.next();
              isAssigned =
                  isAssigned || gt.getNetID().equalsIgnoreCase(p.getNetID());
            }
            return isAssigned;
          } else {
            return true;
          }
        } else if (p.isStudentInCourseByCourseID(a.getCourseID())) {
          GroupMemberLocal gm = null;
          try {
            gm =
                database.groupMemberHome().findByPrimaryKey(
                    new GroupMemberPK(g.getGroupID(), p.getNetID()));
          } catch (Exception e) {
          }
          return gm != null && gm.getStatus().equals(GroupMemberBean.ACTIVE);
        } else {
          return false;
        }
      case XMLBuilder.T_FILEFILE:
        AssignmentFileLocal af = null;
        try {
          af =
              database.assignmentFileHome().findByPrimaryKey(
                  new AssignmentFilePK(ID));
        } catch (Exception e) {
        }
        if (af == null) return false;
        ID = af.getAssignmentItemID();
      case XMLBuilder.T_ITEMFILE:
        AssignmentItemLocal ai = null;
        try {
          ai =
              database.assignmentItemHome().findByPrimaryKey(
                  new AssignmentItemPK(ID));
        } catch (Exception e) {
        }
        if (ai == null) return false;
        try {
          a =
              database.assignmentHome()
                  .findByAssignmentID(ai.getAssignmentID());
        } catch (Exception e) {
        }
        if (a == null) return false;
        try {
          c =
              database.courseHome().findByPrimaryKey(
                  new CoursePK(a.getCourseID()));
        } catch (Exception e) {
        }
        if (c == null) return false;
        if (p.isStaffInCourseByCourseID(a.getCourseID())) {
          return true;
        } else if (p.isStudentInCourseByCourseID(a.getCourseID())) {
          return !(a.getHidden() || a.getStatus().equals(AssignmentBean.HIDDEN));
        } else if (c.getAssignCCAccess() && p.isAuthenticated()) {
          return !(a.getHidden() || a.getStatus().equals(AssignmentBean.HIDDEN));
        } else if (c.getAssignGuestAccess() && p.isGuest()) {
          return !(a.getHidden() || a.getStatus().equals(AssignmentBean.HIDDEN));
        } else {
          return false;
        }
      case XMLBuilder.T_GROUPFILE:
        SubmittedFileLocal sbf = null;
        try {
          sbf =
              database.submittedFileHome().findByPrimaryKey(
                  new SubmittedFilePK(ID));
        } catch (Exception e) {
        }
        if (sbf == null) return false;
        try {
          g = database.groupHome().findByGroupID(sbf.getGroupID());
        } catch (Exception e) {
        }
        if (g == null) return false;
        try {
          a = database.assignmentHome().findByAssignmentID(g.getAssignmentID());
        } catch (Exception e) {
        }
        if (a == null) return false;
        if (p.isAdminPrivByCourseID(a.getCourseID())) {
          return true;
        } else if (p.isGradesPrivByCourseID(a.getCourseID())) {
          if (a.getAssignedGraders()) {
            Vector gid = new Vector();
            gid.add(new Long(g.getGroupID()));
            return database.isAssignedTo(p.getNetID(), gid);
          } else {
            return true;
          }
        } else {
          return false;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Returns true iff the given Principal has permission to download group
   * submissions from the given groups
   * 
   * @param p
   * @param groupIDs
   * @return
   */
  public boolean authorizeGroupFiles(Principal p, Collection groupIDs) {
    try {
      AssignmentLocal assign = null;
      Long assignID = database.isValidGroupCollection(groupIDs);
      if (assignID == null) return false;
      try {
        assign =
            database.assignmentHome().findByAssignmentID(assignID.longValue());
      } catch (Exception e) {
      }
      if (assign == null) return false;
      if (p.isAdminPrivByCourseID(assign.getCourseID())) {
        return true;
      } else if (p.isGradesPrivByCourseID(assign.getCourseID())) {
        if (assign.getAssignedGraders()) {
          return database.isAssignedTo(p.getNetID(), groupIDs);
        } else {
          return true;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Cancels an invitation for a user to join a group
   * 
   * @param p
   *          The Principal of the user canceling this invitation (must be a
   *          member of the group in question)
   * @param canceled
   *          The NetID of the user to uninvite
   * @param groupid
   *          The GroupID of the group
   * @return TransactionResult
   */
  public TransactionResult cancelInvitation(Principal p, User canceled, Group group) {
    TransactionResult result = new TransactionResult();
    try {
      String canceler = p.getNetID();
      canceled = canceled.toLowerCase();
      GroupLocal group = null;
      AssignmentLocal assign = null;
      try {
        group = database.groupHome().findByGroupID(groupid);
      } catch (Exception e) {
      }
      try {
        assign = database.assignmentHome().findByGroupID(groupid);
      } catch (Exception e) {
      }
      if (group == null) {
        result.addError("Invalid group entered, does not exist");
        return result;
      }
      if (assign == null || assign.getHidden()) {
        result.addError("No corresponding assignment exists");
        return result;
      } else if (!assign.getStatus().equals(AssignmentBean.OPEN)) {
        result
            .addError("Group management is not currently available for this assignment");
        return result;
      }
      if (courseIsFrozen(assign.getCourseID())) {
        result.addError("Course is frozen; no changes may be made to it");
        return result;
      }
      GroupMemberLocal memCanceller = null, memCancelled = null;
      try {
        memCanceller =
            database.groupMemberHome().findByPrimaryKey(
                new GroupMemberPK(groupid, canceler));
      } catch (Exception e) {
      }
      try {
        memCancelled =
            database.groupMemberHome().findByPrimaryKey(
                new GroupMemberPK(groupid, canceled));
      } catch (Exception e) {
      }
      if (memCanceller == null
          || !memCanceller.getStatus().equals(GroupMemberBean.ACTIVE)) {
        result
            .addError("Must be an active group member to cancel an invitation");
      }
      if (memCancelled == null
          || !memCancelled.getStatus().equals(GroupMemberBean.INVITED)) {
        if (memCancelled != null
            && memCancelled.getStatus().equals(GroupMemberBean.ACTIVE)) {
          result.addError(canceled + " is already an active group member");
        } else {
          result
              .addError(canceled + " has not been invited to join this group");
        }
      }
      if (!result.hasErrors()) {
        if (transactions.cancelInvitation(p, canceled, groupid)) {
          result.setValue("Successfully canceled invitation");
        } else {
          result.addError("Database failed to cancel invitation");
        }
      }
    } catch (Exception e) {
      result.addError("Database failed to cancel invitation");
      e.printStackTrace();
    }
    return result;
  }

  // This is used whenever a student or staff member adds or removes a group
  // from a slot
  public TransactionResult changeGroupSlot(Principal p, Group group,
      Assignment assign, HttpServletRequest req, boolean canAssign,
      boolean canReplace) {
    TransactionResult result = new TransactionResult();
    try {
      Long slotID = null;
      boolean addGroup = false;
      // look for slot id in servlet request
      if (canAssign) {
        String slotParam = req.getParameter(AccessController.P_TIMESLOTID);
        if (slotParam != null) {
          slotID = new Long(Long.parseLong(slotParam));
          addGroup = true;
        }
      }
      // if not found, search postdata
      if ((!addGroup) && canAssign) {
        DiskFileUpload u = new DiskFileUpload();
        List info =
            u.parseRequest(req, 1024, AccessController.maxFileSize,
                FileUtil.TEMP_DIR);
        Iterator i = info.iterator();
        while (i.hasNext()) {
          FileItem item = (FileItem) i.next();
          String field = item.getFieldName();
          if (item.isFormField() && field.equals(AccessController.P_TIMESLOTID)) {
            slotID = new Long(Long.parseLong(item.getString()));
            addGroup = true;
          }
        }
      }
      AssignmentLocal assign = null;
      TimeSlotLocal slot = null;
      GroupLocal group = null;
      try {
        group = database.groupHome().findByGroupID(groupid);
        assign = database.assignmentHome().findByAssignmentID(assignid);
        if (addGroup)
          slot = database.timeSlotHome().findByTimeSlotID(slotID.longValue());
      } catch (Exception e) {
      }
      // handle potential errors
      if (group == null) {
        result.addError("Invalid group entered: does not exist");
        return result;
      }
      if (addGroup && (slot == null || slot.getHidden())) {
        result
            .addError("Invalid time slot entered: does not exist or is hidden");
        return result;
      }
      if (assign == null || assign.getHidden() || (!assign.getScheduled())) {
        result
            .addError("Invalid assignment entered: does not exist or does not use schedule");
        return result;
      }
      if (courseIsFrozen(assign.getCourseID())) {
        result.addError("Course is frozen; no changes may be made to it");
        return result;
      }
      // check for a full time slot
      if (addGroup) {
        int limit = 0;
        if (assign.getGroupLimit() != null)
          limit = assign.getGroupLimit().intValue();
        int population = slot.getPopulation();
        if (population >= limit) {
          result.addError("Requested time slot is full");
          return result;
        }
      }
      // check for an already assigned group
      if ((!canReplace) && addGroup) {
        Long currentTSid = group.getTimeSlotID();
        if (currentTSid != null && !(currentTSid.longValue() == 0)) {
          result
              .addError("Unauthorized reassignment of an already assigned group");
          return result;
        }
      }
      // check for locked schedule on this assignment (only important if change
      // is being requested by a student)
      if (!p.isStaffInCourseByCourseID(assign.getCourseID())
          && assign.getTimeslotLockTime() != null
          && new Date().after(assign.getTimeslotLockTime())) {
        result
            .addError("Assignment schedule is currently locked; students may not make changes");
        return result;
      }
      if (!result.hasErrors()) {

        long slotNum = 0;
        if (addGroup && slotID != null) slotNum = slotID.longValue();

        if (transactions.changeGroupSlot(p, groupid, assignid, slotNum,
            addGroup)) {
          if (addGroup)
            result.setValue("Successfully added group to time slot");
          else result.setValue("Successfully removed group from time slot");
        } else {
          result.addError("Database failed to make time slot change");
        }
      }
    } catch (Exception e) {
      result.addError("Database failed to make time slot change");
      e.printStackTrace();
    }
    return result;
  }

  // This is used whenever a student or staff member adds or removes a group
  // from a slot
  public TransactionResult changeGroupSlotByID(Principal p, Group group,
      Assignment assign, TimeSlot slot, boolean canAssign, boolean canReplace) {
    TransactionResult result = new TransactionResult();
    try {
      boolean addGroup = true;
      AssignmentLocal assign = null;
      TimeSlotLocal slot = null;
      GroupLocal group = null;
      try {
        group = database.groupHome().findByGroupID(groupid);
        assign = database.assignmentHome().findByAssignmentID(assignid);
        if (addGroup) slot = database.timeSlotHome().findByTimeSlotID(slotID);
      } catch (Exception e) {
      }
      // handle potential errors
      if (group == null) {
        result.addError("Invalid group entered: does not exist");
        return result;
      }
      if (addGroup && (slot == null || slot.getHidden())) {
        result
            .addError("Invalid time slot entered: does not exist or is hidden");
        return result;
      }
      if (assign == null || assign.getHidden() || (!assign.getScheduled())) {
        result
            .addError("Invalid assignment entered: does not exist or does not use schedule");
        return result;
      }
      if (courseIsFrozen(assign.getCourseID())) {
        result.addError("Course is frozen; no changes may be made to it");
        return result;
      }
      // check for a full time slot
      if (addGroup) {
        int limit = 0;
        if (assign.getGroupLimit() != null)
          limit = assign.getGroupLimit().intValue();
        int population = slot.getPopulation();
        if (population >= limit) {
          result.addError("Requested time slot is full");
          return result;
        }
      }
      // check for an already assigned group
      if ((!canReplace) && addGroup) {
        Long currentTSid = group.getTimeSlotID();
        if (currentTSid != null && !(currentTSid.longValue() == 0)) {
          result
              .addError("Unauthorized reassignment of an already assigned group");
          return result;
        }
      }
      // check for locked schedule on this assignment (only important if change
      // is being requested by a student)
      if (!p.isStaffInCourseByCourseID(assign.getCourseID())
          && assign.getTimeslotLockTime() != null
          && new Date().after(assign.getTimeslotLockTime())) {
        result
            .addError("Assignment schedule is currently locked; students may not make changes");
        return result;
      }
      if (!result.hasErrors()) {

        long slotNum = 0;
        if (addGroup) slotNum = slotID;

        if (transactions.changeGroupSlot(p, groupid, assignid, slotNum,
            addGroup)) {
          if (addGroup)
            result.setValue("Successfully added group to time slot");
          else result.setValue("Successfully removed group from time slot");
        } else {
          result.addError("Database failed to make time slot change");
        }
      }
    } catch (Exception e) {
      result.addError("Database failed to make time slot change");
      e.printStackTrace();
    }
    return result;
  }

  public TransactionResult commitFinalGradesFile(Principal p, Course course, List table) {
    TransactionResult result = new TransactionResult();
    try {
      if (courseIsFrozen(courseID)) {
        result.addError("Course is frozen; no changes can be made to it");
      } else {
        result = transactions.commitFinalGradesFile(p, courseID, table);
      }
    } catch (Exception e) {
      result
          .addError("Unexpected error while trying to commit final grades file");
      e.printStackTrace();
    }
    return result;
  }

  /**
   * @param assignmentID
   * @param grader
   * @param table
   * @return TransactionResult
   */
  public TransactionResult commitGradesFile(Principal p, Assignment assign, List table) {
    TransactionResult result = new TransactionResult();
    boolean success = false;
    try {
      AssignmentLocal assignment =
          database.assignmentHome().findByAssignmentID(assignmentID);
      if (courseIsFrozen(assignment.getCourseID())) {
        result.addError("Course is frozen; no changes may be made to it");
        return result;
      }
      Map subProbIDMap = database.getSubProblemIDMap(assignmentID);
      Map groupIDs = database.getGroupIDMap(assignmentID);
      boolean checkCanGrade =
          !p.isAssignPrivByAssignmentID(assignmentID)
              && assignment.getAssignedGraders();
      if (checkCanGrade) {
        String[] header = (String[]) table.get(0);
        long[] subProbIDs = new long[header.length - 1];
        String grader = p.getNetID();

        int[] colsFound = CSVFileFormatsUtil.parseColumnNamesFlexibly(header);
        int netIDIndex =
            CSVFileFormatsUtil.getFlexibleColumnNum(colsFound,
                CSVFileFormatsUtil.NETID);

        for (int i = 0; i < header.length; i++) {
          if (i != netIDIndex) {
            String[] data = (String[]) table.get(i);
            subProbIDs[i - 1] =
                ((Long) subProbIDMap.get(header[i])).longValue();
          }
        }

        HashSet canGrades = new HashSet();
        Iterator assignTos =
            database.groupAssignedToHome().findByNetIDAssignmentID(
                p.getNetID(), assignmentID).iterator();
        while (assignTos.hasNext()) {
          GroupAssignedToLocal a = (GroupAssignedToLocal) assignTos.next();
          canGrades.add(a.getGroupID() + "_" + a.getSubProblemID());
        }
        for (int i = 1; i < table.size(); i++) {
          String[] data = (String[]) table.get(i);
          String netid = (String) data[netIDIndex];
          Group group = ((Long) groupIDs.get(netid)).longValue();
          for (int j = 0; j < data.length; j++) {
            if (j != netIDIndex) {
              try {
                float grade = StringUtil.parseFloat(data[j]);
                long subProbID = subProbIDs[j - 1];
                if (!canGrades.contains(groupID + "_" + subProbID)) {
                  result.addError("No permission to grade " + netid
                      + " in column '" + (j + 1) + "'");
                }
              } catch (NumberFormatException x) {
                if (!data[j].equals("")) // empty string is ok
                  result.addError("Badly formatted grade for " + netid
                      + " in column '" + (j + 1) + "'");
              }
            }
          }
        }
      }
      if (!result.hasErrors()) {
        success = transactions.commitGradesFile(p, assignmentID, table);
      }
    } catch (Exception e) {
      success = false;
      e.printStackTrace();
    }
    if (!success) {
      result.addError("Database did not accept uploaded grades file");
    }
    return result;
  }

  /**
   * Commit whatever info was parsed from a CSV input file
   * 
   * @param p
   * @param table
   *          A List of String[]s, one per student mentioned in the uploaded
   *          file; the first element holds the header strings, and all the data
   *          have been verified
   * @param courseID
   *          The ID of the particular course involved if any, else null
   * @param isClasslist
   *          Whether we'll need to read but ignore a CUID column (usually NetID
   *          is used instead to identify records; the classlist format is a
   *          special case)
   * @return TransactionResult
   * @throws FinderException,
   *           RemoteException
   */
  public TransactionResult commitStudentInfo(Principal p, List table,
      Course course, boolean isClasslist) throws FinderException,
      RemoteException {
    TransactionResult result = new TransactionResult();
    boolean success = false;
    CourseLocal course = null;
    if (courseID != null) {
      course =
          database.courseHome().findByPrimaryKey(
              new CoursePK(courseID.longValue()));
      if (courseIsFrozen(course.getCourseID())) {
        result.addError("Course is frozen; no changes may be made to it");
        return result;
      }
    }
    try {
      success = transactions.commitStudentInfo(p, table, courseID, isClasslist);
    } catch (Exception e) {
      success = false;
      e.printStackTrace();
    }
    if (!success)
      result.addError("Database did not accept uploaded file");
    else result.setValue("Student information successfully committed");
    return result;
  }

  /**
   * To be called from every function in TransactionHandler that changes a
   * course, before trying to make changes
   * 
   * @return Whether the course being dealt with is available for changes
   */
  private boolean courseIsFrozen(Course course) throws RemoteException,
      FinderException {
    CourseLocal course =
        database.courseHome().findByPrimaryKey(new CoursePK(courseID));
    if (course == null)
      throw new FinderException("Course " + courseID + " not found in database");
    return course.getFreezeCourse();
  }

  /**
   * Staff-level method for putting a group of students into a group together in
   * the given assignment. Requires that each student in the collection of
   * NetIDs is currently in a group by him/herself.
   * 
   * @param p
   * @param netids
   *          A List of Strings in correct NetID format
   * @param assignmentID
   * @return
   */
  public TransactionResult createGroup(Principal p, List netids,
      Assignment assign) {
    TransactionResult result = new TransactionResult();
    try {
      if (netids.size() < 2) // list has correct format but no, or not enough,
                              // netIDs
      {
        result.addError("You must specify at least two NetIDs");
        return result;
      }
      AssignmentLocal assignment =
          database.assignmentHome().findByAssignmentID(assignmentID);
      Collection nonStudents =
          database.getNonStudentNetIDs(netids, ((assignment == null) ? 0
              : assignment.getCourseID()));
      Collection nonSoloMembers =
          database.getNonSoloGroupMembers(netids, assignmentID);
      Collection gradedMembers =
          database.getGradedStudents(netids, assignmentID);
      if (assignment == null) {
        result.addError("Assignment does not exist in the database");
        return result;
      }
      if (courseIsFrozen(assignment.getCourseID())) {
        result.addError("Course is frozen; no changes may be made to it");
        return result;
      }
      if (nonStudents.size() > 0) {
        String error = Util.listUniqueElements(nonStudents);
        error +=
            (nonStudents.size() > 1 ? " are not students in this course or have been dropped"
                : " is not a student in this course or has been dropped");
        result.addError(error);
      }
      if (nonSoloMembers.size() > 0) {
        String error = Util.listUniqueElements(nonSoloMembers);
        error +=
            (nonSoloMembers.size() > 1 ? " are already in groups for this assignment"
                : " is already in a group for this assignment");
        result.addError(error);
      }
      if (gradedMembers.size() > 0) {
        String error = Util.listUniqueElements(gradedMembers);
        error +=
            (gradedMembers.size() > 1 ? " have already received grades for this assignment. Their groups may not be altered."
                : " has already received a grade for this assignment. His/her group may not be altered.");
        result.addError(error);
      }
      if (!result.hasErrors()) {
        boolean success = transactions.createGroup(p, netids, assignmentID);
        if (!success) {
          result.addError("Database failed to group students");
        } else {
          result.setValue("Group was successfully created");
        }
      }
    } catch (Exception e) {
      result.addError("Database failed to group students");
    }
    return result;
  }

  /**
   * Merge all indicated students/groups into one group for the given assignment
   * 
   * @param p
   * @param asgnID
   * @param groupIDs
   *          A List of Longs holding the group IDs to consider
   * @return TransactionResult
   */
  public TransactionResult groupSelectedStudents(Principal p, Assignment assign,
      List groups) {
    TransactionResult result = new TransactionResult();
    try {
      if (groupIDs.isEmpty()) {
        result.setValue("No groups selected");
        return result;
      } else if (groupIDs.size() == 1) {
        result.setValue("Only one group selected");
        return result;
      }
      AssignmentLocal assignment =
          database.assignmentHome().findByAssignmentID(asgnID);
      if (assignment == null) {
        result.addError("Assignment does not exist in the database");
        return result;
      }
      if (courseIsFrozen(assignment.getCourseID())) {
        result.addError("Course is frozen; no changes may be made to it");
        return result;
      }
      Collection gradedGroups = database.getGradedGroups(groupIDs, asgnID);
      if (gradedGroups.size() > 0) {
        String error = "Group", groupStr = "";
        Iterator i = gradedGroups.iterator();
        while (i.hasNext()) {
          Long groupid = (Long) i.next();
          Collection members =
              database.groupMemberHome().findActiveByGroupID(
                  groupid.longValue());
          Collection netids = new ArrayList();
          Iterator i2 = members.iterator();
          while (i2.hasNext())
            netids.add(((GroupMemberLocal) i2.next()).getNetID());
          groupStr += " (" + Util.listElements(netids) + ")";
        }
        error +=
            (gradedGroups.size() > 1 ? "s"
                + groupStr
                + " have already received grades for this assignment and may not be altered"
                : groupStr
                    + " has already received a grade for this assignment and may not be altered");
        result.addError(error);
      }
      if (!result.hasErrors()) {
        boolean success = transactions.mergeGroups(p, groupIDs, asgnID);
        if (!success) {
          result.addError("Database failed to merge groups");
        } else {
          result.setValue("Groups were successfully merged");
        }
      }
    } catch (Exception e) {
      result.addError("Database failed to merge groups");
      e.printStackTrace();
    }
    return result;
  }

  /**
   * Break up all indicated groups and put their members into individual groups
   * for the given assignment; ignore all selected single students (this last is
   * done in XactionsBean)
   * 
   * @param p
   * @param asgnID
   * @param groupIDs
   *          A List of Longs holding the group IDs to consider
   * @return TransactionResult
   */
  public TransactionResult ungroupSelectedStudents(Principal p, Assignment assign, List groups) {
    TransactionResult result = new TransactionResult();
    try {
      if (groupIDs.isEmpty()) {
        result.setValue("No students selected");
        return result;
      }
      AssignmentLocal assignment =
          database.assignmentHome().findByAssignmentID(asgnID);
      if (assignment == null) {
        result.addError("Assignment does not exist in the database");
        return result;
      }
      if (courseIsFrozen(assignment.getCourseID())) {
        result.addError("Course is frozen; no changes may be made to it");
        return result;
      }
      Collection gradedGroups = database.getGradedGroups(groupIDs, asgnID);
      if (gradedGroups.size() > 0) {
        String error = "Group", groupStr = "";
        Iterator i = gradedGroups.iterator();
        while (i.hasNext()) {
          Long groupid = (Long) i.next();
          Collection members =
              database.groupMemberHome().findActiveByGroupID(
                  groupid.longValue());
          Collection netids = new ArrayList();
          Iterator i2 = members.iterator();
          while (i2.hasNext())
            netids.add(((GroupMemberLocal) i2.next()).getNetID());
          groupStr += " (" + Util.listElements(netids) + ")";
        }
        error +=
            (gradedGroups.size() > 1 ? "s"
                + groupStr
                + " have already received grades for this assignment and may not be altered"
                : groupStr
                    + " has already received a grade for this assignment and may not be altered");
        result.addError(error);
      }
      if (!result.hasErrors()) {
        boolean success = transactions.disbandGroups(p, groupIDs, asgnID);
        if (!success) {
          result.addError("Database failed to disband group(s)");
        } else {
          result.setValue("Groups were successfully disbanded");
        }
      }
    } catch (Exception e) {
      result.addError("Database failed to disband group(s)");
      e.printStackTrace();
    }
    return result;
  }

  /**
   * Add a new semester by name
   * 
   * @param semesterName
   *          The selected name string (will be checked for legality)
   * @return TransactionResult
   */
  public TransactionResult createSemester(Principal p, String semesterName) {
    TransactionResult result = new TransactionResult();
    boolean success = true;
    if (!Util.isLegalSemesterName(semesterName)) {
      result.addError("'" + semesterName + "' is not a legal semester name");
      return result;
    }
    try {
      success = transactions.createSemester(p, semesterName);
    } catch (Exception e) {
      success = false;
      result.addError("Database failed to create semester");
      e.printStackTrace();
    }
    return result;
  }

  /**
   * Declines an invitation to a user to join a group.
   * 
   * @param netid
   *          The NetID of the user whose invitation is being declined
   * @param groupid
   *          The GroupID of the group
   * @return TransactionResult
   */
  public TransactionResult declineInvitation(Principal p, Group group) {
    TransactionResult result = new TransactionResult();
    try {
      String netid = p.getNetID();
      GroupLocal group = null;
      AssignmentLocal assign = null;
      try {
        group = database.groupHome().findByGroupID(groupid);
      } catch (Exception e) {
      }
      try {
        assign = database.assignmentHome().findByGroupID(groupid);
      } catch (Exception e) {
      }
      if (group == null) {
        result.addError("Invalid group entered: does not exist");
        return result;
      }
      if (assign == null || assign.getHidden()) {
        result.addError("No corresponding assignment exists");
        return result;
      } else if (!assign.getStatus().equals(AssignmentBean.OPEN)) {
        result
            .addError("Group management is not currently available for this assignment");
        return result;
      }
      if (courseIsFrozen(assign.getCourseID())) {
        result.addError("Course is frozen; no changes may be made to it");
        return result;
      }
      GroupMemberLocal member = null;
      try {
        member =
            database.groupMemberHome().findByPrimaryKey(
                new GroupMemberPK(groupid, netid));
      } catch (Exception e) {
      }
      if (member == null || !member.getStatus().equals(GroupMemberBean.INVITED)) {
        if (member != null && member.getStatus().equals(GroupMemberBean.ACTIVE)) {
          result.addError("Already an active member of this group");
        } else {
          result.addError("No invitation to join this group exists");
        }
      }
      if (transactions.declineInvitation(p, groupid)) {
        result.setValue("Successfully declined invitation");
      } else {
        result.addError("Database failed to update declined invitation");
      }
    } catch (Exception e) {
      result.addError("Database failed to update declined invitation");
      e.printStackTrace();
    }
    return result;
  }

  public TransactionResult disbandGroup(Principal p, Group group) {
    TransactionResult result = new TransactionResult();
    try {
      GroupLocal group = database.groupHome().findByGroupID(groupID);
      if (group == null) {
        result.addError("Group does not exist in database.");
        // FIXME check for group members already graded
      } else {
        AssignmentLocal assignment =
            database.assignmentHome().findByGroupID(groupID);
        if (courseIsFrozen(assignment.getCourseID())) {
          result.addError("Course is frozen; no changes may be made to it");
          return result;
        }
        boolean success = transactions.disbandGroup(p, groupID);
        if (!success)
          result.addError("Database failed to ungroup selected groups");
      }
    } catch (Exception e) {
      result.addError("Database failed to ungroup selected groups");
    }
    return result;
  }

  public TransactionResult dropStudent(Principal p, Course course,
      Collection netIDs) {
    TransactionResult result = new TransactionResult();
    try {
      if (courseIsFrozen(courseID)) {
        result.addError("Course is frozen; no changes may be made to it");
      } else {
        Collection nonExist = database.getNonStudentNetIDs(netIDs, courseID);
        if (nonExist.size() > 0) {
          result.addError(Util.listElements(nonExist)
              + (nonExist.size() == 1 ? " is not an enrolled student"
                  : " are not enrolled students") + " in the course");
        } else {
          if (transactions.dropStudents(p, netIDs, courseID)) {
            result.setValue("Student" + (netIDs.size() > 1 ? "s" : "")
                + " successfully dropped");
          } else {
            result.addError("Unexpected error while trying to drop student");
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      result.addError("Unexpected error while trying to drop student");
    }
    return result;
  }

  /**
   * Edits an already existing announcement
   * 
   * @param announceID
   *          ID of the annoucement to edit
   * @param announce
   *          Text of the revised announcement
   * @param poster
   *          Person who posted the original announcement
   * @return TransactionResult
   */
  public TransactionResult editAnnouncement(Principal p, Announcement announce,
      String newText, boolean remove) {
    TransactionResult result = new TransactionResult();
    try {
      AnnouncementLocal annt =
          database.announcementHome().findByPrimaryKey(
              new AnnouncementPK(announceID));
      if (annt == null)
        result.addError("Couldn't find announcement in database");
      else {
        if (courseIsFrozen(annt.getCourseID()))
          result.addError("Course is frozen; no changes may be made to it");
        else {
          if (!transactions.editAnnouncement(p, announceID, announce, remove))
            result
                .addError("Could not edit announcement due to database error");
        }
      }
    } catch (Exception e) {
      result.addError("Unexpected error while trying to edit announcement");
      e.printStackTrace();
    }
    return result;
  }

  /**
   * Set a semester's properties. This function doesn't use a custom data object
   * because hopefully hiddenness will always be the only settable semester
   * property.
   * 
   * @param semesterID
   * @param hidden
   * @return TransactionResult
   */
  public TransactionResult editSemester(Principal p, Semester semester, boolean hidden) {
    TransactionResult result = new TransactionResult();
    boolean success = true;
    try {
      SemesterLocal semester =
          database.semesterHome().findByPrimaryKey(new SemesterPK(semesterID));
      success =
          transactions.editSemester(p, new SemesterData(semesterID, semester
              .getSemesterName(), hidden));
    } catch (Exception e) {
      success = false;
      result.addError("Database failed to edit semester");
      e.printStackTrace();
    }
    return result;
  }

  public TransactionResult exportSurveyResult(OutputStream out,
      Collection surveyResultData) {
    TransactionResult result = new TransactionResult();
    ExcelCSVPrinter printer = new ExcelCSVPrinter(out);
    Iterator i = surveyResultData.iterator();
    while (i.hasNext()) {
      String[] line = (String[]) i.next();
      try {
        printer.writeln(line);
      } catch (IOException ex) {
        result.addError("Error writing a row");
        return result;
      }
    }
    return result;
  }

  /**
   * Write a full template for student data, including all columns the
   * flexible-upload feature can accept. The user should remove the columns that
   * aren't wanted. If there isn't a course ID, only write cmsadmin columns; if
   * there is one, write all columns except CUID, which only cmsadmin should
   * touch.
   * 
   * @param courseID
   *          A Long giving the course ID for a course-specific download, or
   *          null for a cmsadmin download
   * @param out
   *          The stream to which to write the resulting CSV file
   * @return TransactionResult
   */
  public TransactionResult exportStudentInfoTemplate(Course course, OutputStream out) {
    TransactionResult result = new TransactionResult();
    CSVPrinter printer = new CSVPrinter(out);
    List headers = new ArrayList(); // will hold objects of type
                                    // CSVFileFormatsUtil.ColumnInfo
    if (courseID == null) // admin info only
    {
      for (int i = 0; i < CSVFileFormatsUtil.ALLOWED_COLUMNS.length; i++)
        if (CSVFileFormatsUtil.ALLOWED_COLUMNS[i].isForAdmin())
          headers.add(CSVFileFormatsUtil.ALLOWED_COLUMNS[i]);
    } else // course info only
    {
      for (int i = 0; i < CSVFileFormatsUtil.ALLOWED_COLUMNS.length; i++)
        if (CSVFileFormatsUtil.ALLOWED_COLUMNS[i].isForStaff())
          headers.add(CSVFileFormatsUtil.ALLOWED_COLUMNS[i]);
    }
    String[] headerLine = new String[headers.size()];
    for (int i = 0; i < headerLine.length; i++)
      headerLine[i] =
          ((CSVFileFormatsUtil.ColumnInfo) headers.get(i)).getName();
    try {
      printer.writeln(headerLine);
    } catch (IOException x) {
      result.addError("Couldn't print to template file");
      return result;
    }
    // write one line of commas with empty values between
    String[] commaLine = new String[headerLine.length];
    // CSVPrinter will wrap the first (empty) token with quotes; can't think of
    // a way to avoid that--Evan
    for (int i = 0; i < commaLine.length; i++)
      commaLine[i] = "";
    try {
      printer.writeln(commaLine);
    } catch (IOException x) {
      result.addError("Couldn't print to template file");
      return result;
    }
    return result;
  }

  /**
   * @return The InitalContext
   * @throws NamingException
   *           If naming exception is encountered
   */
  private InitialContext getContext() throws NamingException {
    Hashtable props = new Hashtable();
    props.put(InitialContext.INITIAL_CONTEXT_FACTORY,
        "org.jnp.interfaces.NamingContextFactory");
    props.put(InitialContext.PROVIDER_URL, "jnp://localhost:1099");
    InitialContext ic = new InitialContext(props);
    return ic;
  }

  /**
   * @return The Session Bean home interface
   * @throws NamingException
   *           If the RootHome cannot be found
   */
  private TransactionsLocalHome getHome() throws NamingException {
    Object result = getContext().lookup(TransactionsLocalHome.JNDI_NAME);
    return ((TransactionsLocalHome) PortableRemoteObject.narrow(result,
        TransactionsLocalHome.class));
  }

  /**
   * Returns a DownloadFile object representing a file on the CMS system
   * 
   * @param id
   *          ID of the file; relative to specified type
   * @param type
   *          Specifies type of file to search database for; see T_* fields in
   *          this class for valid types.
   * @return representation of the file on the CMS system
   * @throws RemoteException
   *           Database Error
   * @throws IllegalArgumentException
   *           Undefined type given
   */
  public DownloadFile getJavaFile(long id, int type) throws FinderException,
      RemoteException, IllegalArgumentException {
    switch (type) {
    case XMLBuilder.T_SOLFILE:
      SolutionFileLocal sf =
          database.solutionFileHome().findByPrimaryKey(new SolutionFilePK(id));
      return new DownloadFile(sf.getPath(), sf.getFileName());
    case XMLBuilder.T_ITEMFILE:
      AssignmentItemLocal ai =
          database.assignmentItemHome().findByPrimaryKey(
              new AssignmentItemPK(id));
      AssignmentFileLocal af = ai.getAssignmentFile();
      return new DownloadFile(af.getPath(), af.getFileName(), af.getItemName());
    case XMLBuilder.T_FILEFILE:
      af =
          database.assignmentFileHome().findByPrimaryKey(
              new AssignmentFilePK(id));
      return new DownloadFile(af.getPath(), af.getFileName(), af.getItemName());
    case XMLBuilder.T_GROUPFILE:
      SubmittedFileLocal mf =
          database.submittedFileHome()
              .findByPrimaryKey(new SubmittedFilePK(id));
      RequiredSubmissionLocal sub =
          database.requiredSubmissionHome().findByPrimaryKey(
              new RequiredSubmissionPK(mf.getSubmissionID()));
      String fileName = mf.appendFileType(sub.getSubmissionName());
      return new DownloadFile(mf.getPath(), mf.appendFileType(String.valueOf(mf
          .getSubmissionID())), fileName);
    case XMLBuilder.T_CATFILE:
      CategoryFileLocal ctgFile =
          database.categoryFileHome().findByPrimaryKey(new CategoryFilePK(id));
      return new DownloadFile(ctgFile.getPath(), ctgFile.getFileName());
    case XMLBuilder.T_COMMENTFILE:
      CommentFileLocal commentFile =
          database.commentFileHome().findByPrimaryKey(new CommentFilePK(id));
      return new DownloadFile(commentFile.getPath(), commentFile.getFileName());
    default:
      throw new IllegalArgumentException("Invalid file type");
    }
  }

  /**
   * Parse a servlet request and return a collection of FileItems which
   * represent the files being uploaded by the user.
   * 
   * @param request
   *          The servlet request with uploaded files
   * @return Returns a Collection of FileItems
   */
  private Collection getUploadedFiles(String netid, HttpServletRequest request,
      boolean isLate) throws FileUploadException {
    Profiler.enterMethod("TransactionHandler.getUploadedFiles", "");
    DiskFileUpload upload = new DiskFileUpload();
    Collection result = new ArrayList();
    java.io.File mkDir = new java.io.File(FileUtil.TEMP_DIR);
    if (!mkDir.exists()) mkDir.mkdirs();
    long assignmentid =
        Long.parseLong(request.getParameter(AccessController.P_ASSIGNID));
    try {
      AssignmentLocal assignment =
          database.assignmentHome().findByAssignmentID(assignmentid);
      GroupLocal group =
          database.groupHome().findByNetIDAssignmentID(netid, assignmentid);
      List files =
          upload.parseRequest(request, 1024, AccessController.maxFileSize,
              FileUtil.TEMP_DIR);
      Iterator i = files.iterator();
      while (i.hasNext()) {
        FileItem file = (FileItem) i.next();
        long submissionID =
            Long.parseLong((file.getFieldName().split("file_"))[1]);
        if (!file.getName().equals("")) {
          String fullFileName = FileUtil.trimFilePath(file.getName()), givenFileName =
              null, givenFileType = null;
          String[] split = FileUtil.splitFileNameType(fullFileName);
          givenFileName = split[0];
          givenFileType = split[1];
          RequiredSubmissionLocal submission =
              database.requiredSubmissionHome().findByPrimaryKey(
                  new RequiredSubmissionPK(submissionID));
          RequiredFileTypeData match = submission.matchFileType(givenFileType);
          if (match == null)
            throw new FileUploadException("match fail:" + fullFileName);
          if (file.getSize() / 1024 > submission.getMaxSize())
            throw new FileUploadException("size violation:" + fullFileName);
          int fileCount = transactions.getGroupFileCounter(group.getGroupID());
          java.io.File movedFile, path;
          movedFile =
              new java.io.File(FileUtil.getSubmittedFileSystemPath(assignment
                  .getCourseID(), submission.getAssignmentID(), group
                  .getGroupID(), fileCount, match.getSubmissionID(), match
                  .getFileType()));
          path = movedFile.getParentFile();
          if (path.exists())
            throw new FileUploadException(
                "Failed to create a unique file path for uploaded file.");
          if (!path.mkdirs())
            throw new FileUploadException(
                "Failed to create new directory on the file system");
          if (!movedFile.createNewFile()) {
            throw new FileUploadException(
                "System failed to accept submitted file");
          }
          String md5 = FileUtil.calcMD5(file);
          file.write(movedFile);
          result.add(new SubmissionInfo(submission.getRequiredSubmissionData(),
              match.getFileType(), fileCount, md5, (int) file.getSize(),
              fullFileName, isLate));
        }
      }
    } catch (FileUploadException e) {
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FileUploadException("Error getting uploaded files");
    }
    Profiler.exitMethod("TransactionHandler.getUploadedFiles", "");
    return result;
  }

  /**
   * Create an invitation for a user to join a group.
   * 
   * @param p
   *          The Principal of the user inviting another user to join their
   *          group
   * @param invited
   *          The NetID of the user being invited to join
   * @param groupid
   *          The GroupID of the group
   * @return An error string that's empty ("", NOT null) if no error
   */
  public TransactionResult inviteUser(Principal p, User invited, Group group) {
    TransactionResult result = new TransactionResult();
    try {
      String inviter = p.getNetID();
      invited = invited == null ? "" : invited.toLowerCase();
      GroupLocal group = null;
      AssignmentLocal assign = null;
      try {
        group = database.groupHome().findByGroupID(groupid);
        assign = database.assignmentHome().findByGroupID(groupid);
      } catch (Exception e) {
        if (group == null) {
          result.addError("Group does not exist in the database");
        } else {
          result.addError("No assignment corresponding to the given group");
        }
        return result;
      }
      if (assign.getHidden()) {
        result.addError("No assignment corresponding to the given group");
        return result;
      }
      if (!assign.getStatus().equals(AssignmentBean.OPEN)) {
        result
            .addError("Group management is not currently available for this assignment");
        return result;
      }
      if (courseIsFrozen(assign.getCourseID())) {
        result.addError("Course is frozen; no changes may be made to it");
        return result;
      }
      int numMembers =
          database.groupMemberHome().findActiveByGroupID(groupid).size();
      if (assign.getAssignedGroups()) {
        result
            .addError("Students are not allowed to create their own groups for this assignment");
      }
      if (numMembers >= assign.getGroupSizeMax()) {
        result.addError("Cannot invite students; group is already full");
      }
      GroupMemberLocal memInviter = null, memInvited = null;
      try {
        memInviter =
            database.groupMemberHome().findByPrimaryKey(
                new GroupMemberPK(groupid, inviter));
      } catch (Exception e) {
      }
      try {
        memInvited =
            database.groupMemberHome().findByPrimaryKey(
                new GroupMemberPK(groupid, invited));
      } catch (Exception e) {
      }
      StudentLocal studentInviter = null, studentInvited = null;
      try {
        studentInviter =
            database.studentHome().findByPrimaryKey(
                new StudentPK(assign.getCourseID(), inviter));
      } catch (Exception e) {
      }
      try {
        studentInvited =
            database.studentHome().findByPrimaryKey(
                new StudentPK(assign.getCourseID(), invited));
      } catch (Exception e) {
      }
      if (memInviter == null
          || !memInviter.getStatus().equals(GroupMemberBean.ACTIVE)) {
        result.addError("Must be an active member in a group to invite");
      }
      if (studentInviter == null
          || !studentInviter.getStatus().equals(StudentBean.ENROLLED)) {
        result
            .addError("Must be an enrolled student in this course to create group invitations");
      }
      if (studentInvited == null
          || !studentInvited.getStatus().equals(StudentBean.ENROLLED)) {
        result
            .addError("Invited NetID is not an enrolled student in this course");
      }
      if (!result.hasErrors()) {
        if (memInvited != null) {
          if (memInvited.getStatus().equalsIgnoreCase(GroupMemberBean.INVITED)) {
            result
                .addError("This student has already been invited to join this group");
          } else if (memInvited.getStatus().equalsIgnoreCase(
              GroupMemberBean.ACTIVE)) {
            result.addError("This student is already a member of this group");
          } else {
            if (transactions.inviteUser(p, invited, groupid)) {
              result.setValue("Invited " + invited + " to join the group");
            } else {
              result.addError("Database failed to invite student");
            }
          }
        } else {
          if (transactions.inviteUser(p, invited, groupid)) {
            result.setValue("Invited " + invited + " to join the group");
          } else {
            result.addError("Database failed to invite student");
          }
        }
      }
    } catch (Exception e) {
      result.addError("Database failed to invite student");
      e.printStackTrace();
    }
    return result;
  }

  /**
   * Remove a user from a group. Has the side effect of creating a new, empty
   * group in the same assignment and adding the user to it.
   * 
   * @param netid
   *          The NetID of the user to remove
   * @param groupid
   *          The GroupID of the group
   * @return An error string that's empty ("", NOT null) if no error
   */
  public TransactionResult leaveGroup(Principal p, Group group) {
    TransactionResult result = new TransactionResult();
    try {
      String netid = p.getNetID();
      GroupLocal group = null;
      AssignmentLocal assign = null;
      try {
        group = database.groupHome().findByGroupID(groupid);
      } catch (Exception e) {
      }
      try {
        assign = database.assignmentHome().findByGroupID(groupid);
      } catch (Exception e) {
      }
      if (group == null) {
        result.addError("Invalid group entered: does not exist");
        return result;
      }
      if (assign == null || assign.getHidden()) {
        result.addError("No corresponding assignment exists");
        return result;
      } else if (!assign.getStatus().equals(AssignmentBean.OPEN)) {
        result
            .addError("Group management is not currently available for this assignment");
        return result;
      }
      if (courseIsFrozen(assign.getCourseID())) {
        result.addError("Course is frozen; no changes may be made to it");
        return result;
      }
      GroupMemberLocal member = null;
      try {
        member =
            database.groupMemberHome().findByPrimaryKey(
                new GroupMemberPK(groupid, netid));
      } catch (Exception e) {
      }
      if (member == null || !member.getStatus().equals(GroupMemberBean.ACTIVE)) {
        result.addError("Not an active member of this group");
      }
      Collection grades =
          database.gradeHome().findMostRecentByNetAssignmentID(netid,
              assign.getAssignmentID());
      if (grades != null && grades.size() > 0) {
        result
            .addError("Cannot leave this group because grades have been entered");
      }
      int numMembers =
          database.groupMemberHome().findActiveByGroupID(groupid).size();
      if (numMembers < 2) {
        result.addError("Cannot leave a solo group");
      }
      if (!result.hasErrors()) {
        if (transactions.leaveGroup(p, groupid)) {
          result.setValue("Successfully left group");
        } else {
          result.addError("Database failed to remove group member");
        }
      }
    } catch (Exception e) {
      result.addError("Database failed to remove group member");
      e.printStackTrace();
    }
    return result;
  }

  /**
   * Search through request looking for a file with the given parameter name; if
   * it isn't found, return null
   * 
   * @param request
   * @param paramName
   * @param result
   *          The object to which to append an error if the file isn't found
   * @return FileItem
   */
  private FileItem retrieveUploadedFile(HttpServletRequest request,
      String paramName, TransactionResult result) {
    DiskFileUpload upload = new DiskFileUpload();
    FileItem file = null;
    try {
      Iterator items =
          upload.parseRequest(request, 1024, AccessController.maxFileSize,
              FileUtil.TEMP_DIR).iterator();
      while (items.hasNext()) {
        FileItem item = (FileItem) items.next();
        if (item.getFieldName().equals(paramName)) {
          file = item;
          break;
        }
      }
    } catch (FileUploadException x) {
      result
          .addError("Unexpected error receiving uploaded file; please try again");
    }
    if (file == null || file.getName().equals(""))
      result.addError("Error receiving uploaded file; please try again");
    return file;
  }

  /**
   * Read the first line of a CSV file and make sure it fits the given file
   * format
   * 
   * @param parser
   * @param format
   * @param result
   *          The object to which to append errors if bad things happen
   * @return A String[] containing the values on the header line, or an
   *         undefined value if result contains errors
   */
  private String[] readCSVHeaderLine(CSVParse parser, String[] format,
      TransactionResult result) {
    String[] line = null;
    int expectedLineLength = CSVFileFormatsUtil.getNumColumns(format);
    try {
      line = parser.getLine();
      if (line == null) {
        result.addError("Uploaded file is empty");
      }
    } catch (IOException x) {
      result.addError("Error parsing header line of uploaded file");
    }
    // make sure the header line looks vaguely right
    if (line.length != expectedLineLength) {
      result.addError("Header line of uploaded file doesn't have "
          + expectedLineLength + " columns.\n" + "Columns should be "
          + CSVFileFormatsUtil.getColumnListing(format) + ".");
    }
    for (int i = 0; i < expectedLineLength; i++) {
      line[i] = line[i].trim();
      if (!line[i].equalsIgnoreCase(format[i]))
        result.addError("Column " + (i + 1)
            + " of header line should resemble '" + format[i] + "'");
    }
    return line;
  }

  /**
   * Flexibly parse the included CSV file and return the data found in it, which
   * should provide at least one predefined kind of information for a group of
   * students. There must be a NetID column unless we're adhering to a specific
   * file format (the second argument). Check to make sure that if a NetID and
   * CUID are provided for a student and they're already in the database, the
   * values in the file check with those in the db. Check that all students
   * listed in the file exist in the database. If this is an upload for a
   * course, check that all students listed in the file are enrolled in the
   * course. (In the case of a class-specific upload with a required format
   * without NetID, this means checking that all included CUIDs belong to
   * students enrolled in the class.) Check that no columns not
   * permission-appropriate for the upload type (course, system) are included
   * (just ignore them if they are). Don't require that all students in the
   * course be listed in an upload; we want to allow adding info for just a few
   * students as it becomes available.
   * 
   * @param request
   * @param requiredFormat
   *          If not null, the format (CSVFileFormatsUtil.<SOMETHING>_FORMAT)
   *          the file header must match
   * @return A TransactionResult. If the upload is successful, the TR's data
   *         object will be a List of String[]s with one entry for each student
   *         for whom data was read; the elements in each array will be the
   *         columns read in (null in each case if no data). The first list
   *         entry will contain the data in the header row of the file.
   */
  public TransactionResult parseCSVInfo(HttpServletRequest request,
      String[] requiredFormat) {
    TransactionResult result = new TransactionResult();
    List values = new ArrayList();
    try {
      Course course = 0;
      // if there is a course ID, this is a staff upload
      try {
        courseID =
            Long.parseLong(request.getParameter(AccessController.P_COURSEID));
      } catch (NumberFormatException x) {
      } // no course; cmsadmin upload
      FileItem file =
          retrieveUploadedFile(request, AccessController.P_UPLOADEDCSV, result);
      if (result.hasErrors()) return result;
      // read the file
      BufferedReader listlines =
          new BufferedReader(new InputStreamReader(file.getInputStream()));
      ExcelCSVParser csvParser = new ExcelCSVParser(listlines);
      // try to recognize header line
      String[] line = csvParser.getLine();
      int[] infoFound = CSVFileFormatsUtil.parseColumnNamesFlexibly(line);
      values.add(line);
      for (int i = 0; i < infoFound.length; i++)
        if (infoFound[i] == -1) // the ith column name wasn't recognized
          result.addError("Unknown column header '" + line[i] + "'");
      if (result.hasErrors()) return result;
      // if a required format is specified, make sure it matches
      if (requiredFormat != null
          && !CSVFileFormatsUtil.headerLineMatchesFormat(infoFound,
              requiredFormat)) {
        result.addError("Header line does not match required format");
        return result;
      }
      // if no format is specified, require that NetID appear
      int netidCol =
          CSVFileFormatsUtil.getFlexibleColumnNum(infoFound,
              CSVFileFormatsUtil.NETID);
      if (requiredFormat == null && netidCol == -1) {
        result.addError("NetID column must appear in uploaded file");
        return result;
      }
      // parse all further lines using assumed format
      int lineNum = 2, expectedLineLength = line.length;
      try {
        line = csvParser.getLine();
      } catch (IOException x) {
        result.addError("Error parsing CSV on line " + lineNum);
        return result;
      }
      while (line != null) { // null return value means end of file
        try {
          if (line.length != expectedLineLength) {
            result.addError("Line " + lineNum + " doesn't have "
                + expectedLineLength + " columns");
            return result;
          }
          // if no format requirement, always require NetID
          if (requiredFormat == null) {
            if (!line[netidCol].matches(CSVFileFormatsUtil.netidRegexp)) {
              result.addError("NetID on line " + lineNum + " does not parse");
            }
            if (result.hasErrors()) return result;
            // require *existing* NetID
            try {
              UserLocal user =
                  database.userHome().findByPrimaryKey(
                      new UserPK(line[netidCol]));
            } catch (FinderException x) {
              result.addError("User '" + line[netidCol] + "' does not exist",
                  lineNum);
            }
            if (result.hasErrors()) return result;
          }
          // check data formatting
          String[] checkedLine = new String[expectedLineLength];
          for (int i = 0; i < expectedLineLength; i++) {
            line[i] = line[i].trim();
            if (infoFound[i] != -1) {
              CSVFileFormatsUtil.ColumnInfo colInfo =
                  CSVFileFormatsUtil.ALLOWED_COLUMNS[infoFound[i]];
              if (colInfo.getDatatype() == CSVFileFormatsUtil.ColumnInfo.TYPE_STRING) {
                if (colInfo.getRegexp() != null
                    && !line[i].matches(colInfo.getRegexp())) {
                  result.addError("Couldn't parse formatted string in column "
                      + (i + 1) + " on line " + lineNum);
                } else checkedLine[i] = line[i];
              } else if (colInfo.getDatatype() == CSVFileFormatsUtil.ColumnInfo.TYPE_INT) {
                try {
                  Integer datum = Integer.valueOf(line[i]);
                  checkedLine[i] = datum.toString();
                } catch (NumberFormatException x) {
                  result.addError("Couldn't parse integer in column " + (i + 1)
                      + " on line " + lineNum);
                }
              } else if (colInfo.getDatatype() == CSVFileFormatsUtil.ColumnInfo.TYPE_FLOAT) {
                try {
                  Float datum = Float.valueOf(line[i]);
                  checkedLine[i] = datum.toString();
                } catch (NumberFormatException x) {
                  result.addError("Couldn't parse real in column " + (i + 1)
                      + " on line " + lineNum);
                }
              }
            }
          }
          if (result.hasErrors()) return result;
          values.add(checkedLine);
          line = csvParser.getLine();
        } catch (IOException x) {
          result.addError("Error parsing line " + lineNum);
          return result;
        }
        lineNum++;
      }
      result.setValue(values);
      /* data integrity & security checks */
      int cuidCol =
          CSVFileFormatsUtil.getFlexibleColumnNum(infoFound,
              CSVFileFormatsUtil.CUID);
      if (netidCol != -1) {
        // if this is a course-specific upload, make sure all users included are
        // students in the course
        if (courseID != 0)
          for (int i = 1; i < values.size(); i++) {
            String netid = ((String[]) values.get(i))[netidCol];
            try {
              StudentLocal student =
                  database.studentHome().findByPrimaryKey(
                      new StudentPK(courseID, netid));
            } catch (FinderException x) {
              result.addError("User '" + netid
                  + "' is not a student in this course");
            }
          }
        if (result.hasErrors()) return result;
        // CUID-related checks
        if (cuidCol != -1) {
          HashMap netid2cuid = new HashMap(); // for all students in course
          Collection courseStudents =
              database.userHome().findByCourseID(courseID);
          Iterator it = courseStudents.iterator();
          while (it.hasNext()) {
            UserLocal student = (UserLocal) it.next();
            netid2cuid.put(student.getNetID(), student.getCUID());
          }
          // if this is a course-specific upload and cuid is included, all
          // students should have cuids in the db
          if (courseID != 0) {
            for (int i = 1; i < values.size(); i++) {
              line = (String[]) values.get(i);
              if (!netid2cuid.containsKey(line[netidCol])
                  || netid2cuid.get(line[netidCol]) == null
                  || netid2cuid.get(line[netidCol]).equals(""))
                result.addError("Student on line " + (i + 1)
                    + " has no CUID in CMS");
            }
          }
          if (result.hasErrors()) return result;
          // if netid & cuid both appear, make sure they match if we already
          // have both in the db
          for (int i = 1; i < values.size(); i++) { // run through data rows
            line = (String[]) values.get(i);
            String netid = line[netidCol], cuid = line[cuidCol];
            if (netid2cuid.get(netid) != null) {
              if (!cuid.equals("") && !netid2cuid.get(netid).equals(cuid))
                result.addError("NetID and CUID on line " + (i + 1)
                    + " are not a match");
            }
          }
        }
        if (result.hasErrors()) return result;
      } else { // no netid (there's a required format and it doesn't contain
                // netid)
        // CUID-related checks
        if (courseID != 0) { // class-specific upload
          // make sure each CUID corresponds to a student in the class
          HashSet cuids = new HashSet(); // for all students in course
          Collection courseStudents =
              database.userHome().findByCourseID(courseID);
          Iterator it = courseStudents.iterator();
          while (it.hasNext()) {
            UserLocal student = (UserLocal) it.next();
            cuids.add(student.getCUID());
          }
          for (int i = 1; i < values.size(); i++) { // run through data rows
            line = (String[]) values.get(i);
            if (!cuids.contains(line[cuidCol]))
              result.addError("CUID on line " + (i + 1)
                  + " does not belong to a student in this class");
          }
          if (result.hasErrors()) return result;
        }
      }
      /*
       * silently remove data from the columns that aren't appropriate for the
       * principal's permission level
       */
      boolean[] colsOK = new boolean[infoFound.length];
      int numColsOK = 0;
      for (int i = 0; i < infoFound.length; i++) {
        if (courseID == 0) // cmsadmin upload
          colsOK[i] =
              CSVFileFormatsUtil.ALLOWED_COLUMNS[infoFound[i]].isForAdmin();
        else colsOK[i] =
            CSVFileFormatsUtil.ALLOWED_COLUMNS[infoFound[i]].isForStaff();
        numColsOK += (colsOK[i] ? 1 : 0);
      }
      // special case: for a course-specific upload, leave in the CUID (we'll
      // need to remove it later)
      if (courseID != 0 && cuidCol != -1 && !colsOK[cuidCol]) {
        numColsOK++;
        colsOK[cuidCol] = true;
      }
      for (int i = 1; i < values.size(); i++) {
        line = new String[numColsOK];
        int k = 0;
        for (int j = 0; j < infoFound.length; j++)
          if (colsOK[j]) line[k++] = ((String[]) values.get(i))[j];
        values.set(i, line);
      }
      // remove unwanted column headers
      line = (String[]) values.get(0);
      String[] newLine = new String[numColsOK];
      int k = 0;
      for (int j = 0; j < infoFound.length; j++)
        if (colsOK[j]) newLine[k++] = line[j];
      values.set(0, newLine);
    } catch (Exception x) {
      x.printStackTrace();
      result.addError("Unexpected error while trying to parse uploaded file");
    }
    return result;
  }

  /**
   * @param courseID
   * @param request
   * @return A TransactionResult. If the upload is successful, the TR's data
   *         object will be a List of String[]s with one entry for each student
   *         for whom data was read; the elements in each array will be the
   *         columns read in (null in each case if no data). The first list
   *         entry will contain the data in the header row of the file.
   */
  public TransactionResult parseFinalGradesFile(Course course, HttpServletRequest request) {
    TransactionResult result = new TransactionResult();
    Collection lines = new ArrayList();
    boolean hasSubProbs = false;
    HashSet netids = new HashSet();
    final String[] format = CSVFileFormatsUtil.FINALGRADES_TEMPLATE_FORMAT;
    List values = new ArrayList();
    try {
      if (courseIsFrozen(courseID)) {
        result.addError("Course is frozen; no changes may be made to it");
        return result;
      }
      Iterator students =
          database.studentHome().findByCourseIDSortByLastName(courseID)
              .iterator();
      while (students.hasNext()) {
        StudentLocal s = (StudentLocal) students.next();
        netids.add(s.getNetID());
      }
      FileItem file =
          retrieveUploadedFile(request, AccessController.P_GRADESFILE, result);
      if (result.hasErrors()) return result;
      // read the file
      BufferedReader listlines =
          new BufferedReader(new InputStreamReader(file.getInputStream()));
      ExcelCSVParser csvParser = new ExcelCSVParser(listlines);
      int netidCol =
          CSVFileFormatsUtil.getColumnNumber(format, CSVFileFormatsUtil.NETID);
      int gradeCol =
          CSVFileFormatsUtil.getColumnNumber(format,
              CSVFileFormatsUtil.FINAL_GRADE);
      String[] line = readCSVHeaderLine(csvParser, format, result);
      if (result.hasErrors()) return result;
      String[] fileValues = new String[] { "NetID", "Final Grade" };
      values.add(fileValues); // regardless of the file format, this is all the
                              // info we need
      int lineSize = fileValues.length;

      int lineNum = 2;
      try {
        line = csvParser.getLine();
      } catch (IOException x) {
        result.addError("Error parsing line " + lineNum + " of uploaded file");
        return result;
      }
      while (line != null) // null return value means end of file
      {
        try {
          // make sure that there is a grade entered
          String[] checkedLine = new String[lineSize];
          String netID = "", grade = "";
          if (netidCol < line.length) {
            netID = line[netidCol].toLowerCase().trim();
          }

          if (gradeCol < line.length) {
            grade = line[gradeCol].trim();
          }

          if (!netids.contains(netID)) {
            result.addError(netID
                + " is not an enrolled student in this course", lineNum);
          }
          checkedLine[0] = netID;
          if (grade.equals("")) {
            checkedLine[1] = "";
          } else {
            String checkedGrade = Util.validGrade(grade);
            if (checkedGrade == null) {
              result.addError("'" + grade + "' is not a valid final grade",
                  lineNum);
              checkedLine[1] = grade;
            } else {
              checkedLine[1] = checkedGrade;
            }
          }
          values.add(checkedLine);
          line = csvParser.getLine();
        } catch (IOException x) {
          result
              .addError("Error parsing line " + lineNum + " of uploaded file");
          return result;
        }
        lineNum++;
      }
      result.setValue(values);
    } catch (Exception e) {
      e.printStackTrace();
      result
          .addError("An unexpected error occurred while trying to parse the final grades file");
    }
    return result;
  }

  /**
   * Return a TransactionResult holding a list with the grades that should be
   * entered into the system upon confirmation if no errors occur. If there is
   * no value uploaded, return a successful TransactionResult with a null value.
   * For any other error, the TransactionResult is not successful, the value is
   * null, and the result contains any errors that occurred.
   * 
   * @param request
   * @return A TransactionResult. If the upload is successful, the TR's data
   *         object will be a List of String[]s with one entry for each student
   *         for whom data was read; the elements in each array will be the
   *         columns read in (null in each case if no data). The first list
   *         entry will contain the data in the header row of the file.
   */
  public TransactionResult parseGradesFile(Assignment assign, HttpServletRequest request) {
    Profiler.enterMethod("TransactionHandler.parseGradesFile", "AssignmentID: "
        + assignmentID);
    TransactionResult result = new TransactionResult();
    boolean hasSubProbs = false;
    List values = new ArrayList();
    Iterator i = null;
    try {
      AssignmentLocal assignment =
          database.assignmentHome().findByAssignmentID(assignmentID);
      if (courseIsFrozen(assignment.getCourseID())) {
        result.addError("Course is frozen; no changes may be made to it");
        return result;
      }
      Collection subProbs =
          database.subProblemHome().findByAssignmentID(assignmentID);
      Iterator students =
          database.studentHome().findByCourseIDSortByLastName(
              assignment.getCourseID()).iterator();
      /*
       * Create these hash sets for easily telling later whether or not a value
       * we parsed is valid
       */
      Vector subProbNames = new Vector();
      HashSet netids = new HashSet();
      if (subProbs.size() == 0) {
        hasSubProbs = false;
      } else {
        hasSubProbs = true;
        i = subProbs.iterator();
        while (i.hasNext()) {
          SubProblemLocal s = (SubProblemLocal) i.next();
          subProbNames.add(s.getSubProblemName());
        }
      }
      while (students.hasNext()) {
        StudentLocal s = (StudentLocal) students.next();
        netids.add(s.getNetID());
      }
      // Find the uploaded file stream
      FileItem file =
          retrieveUploadedFile(request, AccessController.P_GRADESFILE, result);
      if (result.hasErrors()) return result;
      // read the file
      BufferedReader listlines =
          new BufferedReader(new InputStreamReader(file.getInputStream()));
      ExcelCSVParser csvParser = new ExcelCSVParser(listlines);
      String[] line = csvParser.getLine();
      if (line == null) return result;
      int maxWidth = line.length;
      int[] colsFound = null, subproblemColsFound = null;
      try {
        colsFound = CSVFileFormatsUtil.parseColumnNamesFlexibly(line);
        subproblemColsFound =
            CSVFileFormatsUtil.parseSubProblemColumnNamesFlexibly(line,
                subProbNames);
      } catch (CSVParseException e) {
        result.addError(e.getMessage());
      }
      if (colsFound != null) {
        if (hasSubProbs) {
          // Make sure we have netID at least
          if (CSVFileFormatsUtil.getFlexibleColumnNum(colsFound,
              CSVFileFormatsUtil.NETID) == -1) {
            result.addError(
                "The file is misformatted: it must contain a net id", 0);
          }
        } else {
          // make sure the header line looks vaguely right
          final String[] format = CSVFileFormatsUtil.GRADES_FORMAT_NOSUBPROBS;

          for (int j = 0; j < format.length; j++) {
            if (CSVFileFormatsUtil.getFlexibleColumnNum(colsFound, format[j]) == -1) {
              result
                  .addError("Header line of uploaded file doesn't have the correct columns.\n"
                      + "Columns should include "
                      + CSVFileFormatsUtil.getColumnListing(format) + ".");
            }
          }
        }
      }
      values.add(line);
      int lineno = 2;

      int netIDIndex =
          CSVFileFormatsUtil.getFlexibleColumnNum(colsFound,
              CSVFileFormatsUtil.NETID);
      int gradeIndex =
          CSVFileFormatsUtil.getFlexibleColumnNum(colsFound,
              CSVFileFormatsUtil.GRADE);
      if (colsFound == null) {
        while ((line = csvParser.getLine()) != null) {
          values.add(line);
        }
      } else {
        while ((line = csvParser.getLine()) != null) {
          String checkedLine[] = new String[line.length];

          for (int j = 0; j < line.length; j++)
            line[j] = line[j].trim();
          // Check that the NetID that begins this line is a student in the
          // course
          if (!netids.contains(line[netIDIndex])) {
            result.addError("'" + line[netIDIndex]
                + "' is not an enrolled student in this course", lineno);
          }
          checkedLine[netIDIndex] = line[netIDIndex];

          /*
           * For each element after the first on a line, check that it properly
           * parses as a floating point number, and if it does, replace the
           * entry in checkedLine with a Float instead of a String
           */
          for (int j = 0; j < line.length; j++) {
            if (j != netIDIndex) {
              try {
                if (line[j].equals(""))
                  checkedLine[j] = "";
                else checkedLine[j] =
                    new Float(StringUtil.parseFloat(line[j])).toString();
              } catch (NumberFormatException e) {
                result.addError("'" + line[j] + "' is not a valid score",
                    lineno);
                checkedLine[j] = line[j];
              }
            }
          }
          values.add(checkedLine);
          lineno++;
        }
      }
      result.setValue(values);
    } catch (Exception e) {
      e.printStackTrace();
      result
          .addError("An unexpected exception occurred while trying to parse the file");
    }
    Profiler.exitMethod("TransactionHandler.parseGradesFile", "AssignmentID: "
        + assignmentID);
    return result;
  }

  /**
   * Posts a new announcement
   * 
   * @param courseID
   *          The course in which to post the announcement
   * @param announce
   *          Text of the announcement
   * @param poster
   *          User who posted this announcement
   * @return TransactionResult
   */
  public TransactionResult postAnnouncement(Principal p, Course course,
      String announce) {
    TransactionResult result = new TransactionResult();
    try {
      if (courseIsFrozen(courseID))
        result.addError("Course is frozen; no changes may be made to it");
      else {
        if (!transactions.postAnnouncement(p, courseID, announce))
          result.addError("Could not post announcement due to database error");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  public TransactionResult reenrollStudent(Principal p, Course course,
      User user, boolean emailOn) {
    TransactionResult result = new TransactionResult();
    try {
      if (courseIsFrozen(courseID)) {
        result.addError("Course is frozen; no changes may be made to it");
      } else {
        StudentLocal s = null;
        try {
          s =
              database.studentHome().findByPrimaryKey(
                  new StudentPK(courseID, netID));
        } catch (Exception e) {
        }
        if (s == null) {
          result.addError(netID
              + " has not been previously enrolled in this class");
        } else {
          Vector n = new Vector();
          n.add(netID);
          result = transactions.addStudentsToCourse(p, n, courseID, emailOn);
          if (!result.hasErrors()) {
            result.setValue(netID + " has been successfully reenrolled");
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      result.addError("Unexpected error while trying to reenroll student");
    }
    return result;
  }

  public TransactionResult removeAssignment(Principal p, Assignment assign) {
    TransactionResult result = new TransactionResult();
    try {
      AssignmentLocal assignment =
          database.assignmentHome().findByAssignmentID(assignmentID);
      if (courseIsFrozen(assignment.getCourseID())) {
        result.addError("Course is frozen; no changes may be made to it");
      } else {
        result = transactions.removeAssignment(p, assignmentID);
      }
    } catch (Exception e) {
      result.addError("Unexpected error while trying to remove assignment");
      e.printStackTrace();
    }
    return result;
  }

  /**
   * @return 0 on error, the course ID of the category on success
   */
  private long removeCategory(Principal p, Category category) {
    Course course = 0;
    try {
      CategoryLocal cat =
          database.categoryHome().findByPrimaryKey(new CategoryPK(categoryID));
      if (cat == null)
        return 0;
      else if (courseIsFrozen(cat.getCourseID())) {
        return 0;
      } else if (transactions.removeCategory(p, categoryID)) {
        return cat.getCourseID();
      } else {
        return 0;
      }
    } catch (Exception e) {
      System.out.println("Database failed to find content with id "
          + categoryID);
    }
    return courseID;
  }

  /**
   * Remove a NetID from the list of those given CMS admin access
   * 
   * @return TransactionResult
   */
  public TransactionResult removeCMSAdmin(Principal p, User toRemove) {
    TransactionResult result = new TransactionResult();
    boolean success = false;
    try {
      CMSAdminLocal admin =
          database.cmsAdminHome().findByPrimaryKey(new CMSAdminPK(netID));
      if (admin == null)
        result.addError("Couldn't find CMS admin in database");
      else success = transactions.removeCMSAdmin(p, netID);
    } catch (Exception e) {
      result.addError("Database failed to remove CMS admin");
      e.printStackTrace();
    }
    return result;
  }

  /**
   * @return TransactionResult
   */
  public TransactionResult removeCtgRow(Principal p, CategoryRow row) {
    TransactionResult result = new TransactionResult();
    try {
      CategoryRowLocal row =
          database.categoryRowHome().findByPrimaryKey(new CategoryRowPK(rowID));
      if (row == null)
        result.addError("Couldn't find content row with id: " + rowID);
      else {
        CategoryLocal cat =
            database.categoryHome().findByPrimaryKey(
                new CategoryPK(row.getCategoryID()));
        if (cat == null)
          result.addError("Couldn't find content with id "
              + row.getCategoryID());
        else if (courseIsFrozen(cat.getCourseID()))
          result.addError("Course is frozen; no changes may be made to it");
        else if (transactions.removeCtgRow(p, rowID)) {
          return result;
        }
      }
    } catch (Exception e) {
      result.addError("Database failed to find row with id " + rowID);
    }
    return result;
  }

  public TransactionResult removeExtension(Principal p, Course course, Group group) {
    TransactionResult result = new TransactionResult();
    try {
      GroupLocal group = database.groupHome().findByGroupID(groupID);
      AssignmentLocal assign =
          database.assignmentHome().findByAssignmentID(group.getAssignmentID());
      /*
       * Authentication happens by CourseID, must check that the GroupID is
       * actually a group within the given course.
       */
      if (courseID != assign.getCourseID()) {
        result.addError("Illegal request: group is not in this course");
      }
      if (courseIsFrozen(assign.getCourseID())) {
        result.addError("Course is frozen; no changes may be made to it");
      }
      if (!result.hasErrors()) {
        result = transactions.removeExtension(p, groupID);
      }
    } catch (Exception e) {
      result.addError("Unexpected error; could not remove extension");
      e.printStackTrace();
    }
    return result;
  }

  public TransactionResult restoreAnnouncement(Principal p, Announcement announcement) {
    TransactionResult result = new TransactionResult();
    try {
      AnnouncementLocal announce = null;
      try {
        announce =
            database.announcementHome().findByPrimaryKey(
                new AnnouncementPK(announceID));
      } catch (Exception e) {
      }
      if (announce == null) {
        result.addError("Announcement does not exist in database");
      } else if (courseIsFrozen(announce.getAnnouncementID())) {
        result.addError("Course is frozen; no changes may be made to it");
      }
      if (!result.hasErrors()) {
        result = transactions.restoreAnnouncement(p, announceID);
      }
    } catch (Exception e) {
      result.addError("Unexpected error; could not restore announcement");
      e.printStackTrace();
    }
    return result;
  }

  public TransactionResult restoreAssignment(Principal p, Assignment assign) {
    TransactionResult result = new TransactionResult();
    try {
      AssignmentLocal assignment =
          database.assignmentHome().findByAssignmentID(assignmentID);
      if (courseIsFrozen(assignment.getCourseID())) {
        result.addError("Course is frozen; no changes may be made to it");
      } else {
        result = transactions.restoreAssignment(p, assignmentID);
      }
    } catch (Exception e) {
      result.addError("Unexpected error while trying to restore assignment");
      e.printStackTrace();
    }
    return result;
  }

  public TransactionResult sendEmail(Principal p, Course course, HttpServletRequest request) {
    TransactionResult result = new TransactionResult();
    try {
      Emailer email = new Emailer();
      DiskFileUpload upload = new DiskFileUpload();
      Iterator i = upload.parseRequest(request).iterator();
      String subject = null, body = null, recipient = null, netid = null;
      while (i.hasNext()) {
        FileItem item = (FileItem) i.next();
        if (item.getFieldName().equals(AccessController.P_EMAIL_SUBJECT)) {
          subject = item.getString();
        } else if (item.getFieldName().equals(AccessController.P_EMAIL_BODY)) {
          body = item.getString();
        } else if (item.getFieldName().equals(
            AccessController.P_EMAIL_RECIPIENTS)) {
          recipient = item.getString();
        } else if (item.getFieldName().equals(AccessController.P_EMAIL_NETIDS)) {
          netid = item.getString();
          email.addTo(netid + "@cornell.edu");
        }
      }
      if (recipient.equals("all") || recipient.equals("students")) {
        Iterator students =
            database.studentHome().findByCourseIDSortByLastName(courseID)
                .iterator();
        while (students.hasNext()) {
          StudentLocal student = (StudentLocal) students.next();
          email.addTo(student.getNetID() + "@cornell.edu");
        }
      }
      // The decision was made that staff should always receive the email
      // if (recipient.equals("all") || recipient.equals("staff")) {
      Iterator staffs =
          database.staffHome().findByCourseID(courseID).iterator();
      while (staffs.hasNext()) {
        StaffLocal staff = (StaffLocal) staffs.next();
        email.addTo(staff.getNetID() + "@cornell.edu");
      }
      // }
      String fullname = p.getFirstName();
      fullname +=
          fullname.length() > 0 ? " " + p.getLastName() : p.getLastName();
      email.setFrom("\"" + fullname + "\"" + "<" + p.getNetID()
          + "@cornell.edu" + ">");
      CourseLocal course =
          database.courseHome().findByPrimaryKey(new CoursePK(courseID));
      email.setSubject("[" + course.getDisplayedCode() + "] " + subject);
      email.setMessage(body);
      email.setRecipient(recipient);
      result = transactions.sendEmail(p, courseID, email);
    } catch (Exception e) {
      e.printStackTrace();
      result.addError("Unexpected error; could not send e-mail");
    }
    return result;
  }

  /**
   * Set the properties of an existing assignment or create a new assignment
   * 
   * @param p
   * @param assignID
   *          ID of the assignment to set, or 0 to create a new one
   * @param request
   *          The HTTP request from which to take parameters and values
   * @return TransactionResult
   */
  public TransactionResult setAssignmentProps(Principal p, Course course,
      Assignment assign, HttpServletRequest request) {
    Profiler.enterMethod("TransactionHandler.setAssignmentProps",
        "AssignmentID: " + assignID);
    TransactionResult result = new TransactionResult();
    String name = "", nameshort = "", status = "", description = "";
    String duedate = null, duetime = null, dueampm = null, latedate = null, latetime =
        null, lateampm = null, regradedate = null, regradetime = null, regradeampm =
        null, tslockdate = null, tslocktime = null, tslockampm = null;
    Date due = null, late = null, regradedeadline = null;
    boolean latesubmissions = false, assignedgroups = false, assignedgraders =
        false, studentregrades = false, showstats = false, showsolution = false;
    int graceperiod = 0, groupmin = 0, groupmax = 0, groupoption = 0, regradeoption =
        0, numOfAssignedFiles = 0, type = AssignmentBean.ASSIGNMENT;
    float score = 0, weight = 0;
    int order = 1;
    char letter = 'a';
    SubProblemOptions choices = null;
    boolean useSchedule = false; // flag indicating whether scheduling is in
                                  // use
    boolean emptyProbName = false, emptyItemName = false, emptySubmissionName =
        false; // flag indicating empty names, so only one such error is shown
    boolean emptyQuestName = false; // flag indicating an empty problem name was
                                    // submitted
    String TSTimeStr = null; // string indicating the duration of a timeslot in
                              // h:mm:ss format
    long TSDuration = 0; // duration of a timeslot in seconds
    int TSMaxGroups = 0; // maximum number of groups in a timeslot
    Date TSLockedTime = null; // deadline for students to change slots
    boolean proceed = true; // success flag (not an assignment property)
    long importID = 0;
    DiskFileUpload upload = new DiskFileUpload();
    AssignmentOptions options = new AssignmentOptions();
    HashSet filenames = new HashSet(), probnames = new HashSet(), subnames =
        new HashSet(), questnames = new HashSet();
    HashMap probScores = new HashMap(); // for ensuring totalscore = sum of
                                        // problem scores
    List info = null;
    try {
      info =
          upload.parseRequest(request, 1024, AccessController.maxFileSize,
              FileUtil.TEMP_DIR);
      if (courseIsFrozen(courseID)) {
        result.addError("Course is frozen; no changes may be made to it");
        result.setValue(info);
        return result;
      }
      Iterator i = info.iterator();
      while (i.hasNext()) {
        FileItem item = (FileItem) i.next();
        String field = item.getFieldName();
        if (item.isFormField()) {
          if (field.equals(AccessController.P_NAME)) {
            name = item.getString();
          } else if (field.equals(AccessController.P_NAMESHORT)) {
            nameshort = item.getString();
          } else if (field.equals(AccessController.P_ASSIGNMENTTYPE)) {
            type = Integer.parseInt(item.getString());
            options.setAssignmentType(type);
          } else if (field.equals(AccessController.P_DUEDATE)) {
            duedate = item.getString();
          } else if (field.equals(AccessController.P_DUETIME)) {
            duetime = item.getString();
          } else if (field.equals(AccessController.P_DUEAMPM)) {
            dueampm = item.getString();
          } else if (field.equals(AccessController.P_GRACEPERIOD)) {
            try {
              graceperiod = Integer.parseInt(item.getString());
            } catch (NumberFormatException nfe) {
              result.addError("Grace period must be an integer");
              proceed = false;
            }
          } else if (field.equals(AccessController.P_LATEALLOWED)) {
            latesubmissions = item.getString().equals(AccessController.ONE);
          } else if (field.equals(AccessController.P_LATEDATE)) {
            latedate = item.getString();
          } else if (field.equals(AccessController.P_LATETIME)) {
            latetime = item.getString();
          } else if (field.equals(AccessController.P_LATEAMPM)) {
            lateampm = item.getString();
          } else if (field.equals(AccessController.P_USESCHEDULE)) {
            try {
              useSchedule = item.getString().equals("on"); // changed to
                                                            // support "on"
                                                            // checkbox value,
                                                            // EPW 02-24-06
            } catch (Exception e) {
              useSchedule = true;
            }
          } else if (field.equals(AccessController.P_TSDURATIONSTR)) {
            TSTimeStr = item.getString();
          } else if (field.equals(AccessController.P_TSMAXGROUPS)) {
            // Try-catch block added in case empty string sent, EPW 02-24-06
            try {
              TSMaxGroups = Integer.parseInt(item.getString());
            } catch (NumberFormatException nfe) {
              TSMaxGroups = 0;
            }
          } else if (field.equals(AccessController.P_SCHEDULE_LOCKDATE)) {
            tslockdate = item.getString();
          } else if (field.equals(AccessController.P_SCHEDULE_LOCKTIME)) {
            tslocktime = item.getString();
          } else if (field.equals(AccessController.P_SCHEDULE_LOCKAMPM)) {
            tslockampm = item.getString();
          } else if (field.equals(AccessController.P_STATUS)) {
            status = item.getString();
          } else if (field.equals(AccessController.P_DESCRIPTION)) {
            description = item.getString();
          } else if (field.equals(AccessController.P_GROUPS)) {
            groupoption = Integer.parseInt(item.getString());
          } else if (field.equals(AccessController.P_GROUPSMIN)) {
            try {
              groupmin = Integer.parseInt(item.getString());
            } catch (NumberFormatException nfe) {
              groupmin = -1;
            }
          } else if (field.equals(AccessController.P_GROUPSMAX)) {
            try {
              groupmax = Integer.parseInt(item.getString());
            } catch (NumberFormatException nfe) {
              groupmax = -1;
            }
          } else if (field.equals(AccessController.P_GROUPSBYTA)) {
            assignedgraders = item.getString().equals(AccessController.ONE);
          } else if (field.equals(AccessController.P_REGRADES)) {
            regradeoption = Integer.parseInt(item.getString());
          } else if (field.equals(AccessController.P_REGRADEDATE)) {
            regradedate = item.getString();
          } else if (field.equals(AccessController.P_REGRADETIME)) {
            regradetime = item.getString();
          } else if (field.equals(AccessController.P_REGRADEAMPM)) {
            regradeampm = item.getString();
          } else if (field.equals(AccessController.P_TOTALSCORE)) {
            try {
              score = StringUtil.parseFloat(item.getString());
              if (score <= 0) throw new NumberFormatException();
            } catch (NumberFormatException nfe) {
              result.addError("Max score must be a positive number");
              proceed = false;
            }
          } else if (field.equals(AccessController.P_WEIGHT)) {
            try {
              weight = StringUtil.parseFloat(item.getString());
              if (weight < 0) throw new NumberFormatException();
            } catch (NumberFormatException nfe) {
              result.addError("Weight must be a positive number");
              proceed = false;
            }
          } else if (field.equals(AccessController.P_SHOWSTATS)) {
            showstats = true;
          } else if (field.equals(AccessController.P_SHOWSOLUTION)) {
            showsolution = true;
          } else if (field.equals(AccessController.P_GROUPSFROM)) {
            importID = Long.parseLong(item.getString());
          } else if (field.startsWith(AccessController.P_REQFILENAME)) {
            long id =
                Long
                    .parseLong((field.split(AccessController.P_REQFILENAME))[1]);
            if (subnames.contains(item.getString())) {
              result.addError("A required submission with name '"
                  + item.getString() + "' already exists");
              proceed = false;
            } else {
              if (item.getString().equals("")) {
                emptySubmissionName = true;
              } else {
                subnames.add(item.getString());
                options.addRequiredFileName(item.getString(), id);
                numOfAssignedFiles++;
              }
            }
          } else if (field.startsWith(AccessController.P_REQFILETYPE)) {
            long id =
                Long
                    .parseLong((field.split(AccessController.P_REQFILETYPE))[1]);
            options.addRequiredFileType(item.getString(), id);
          } else if (field.startsWith(AccessController.P_REQSIZE)) {
            long id =
                Long.parseLong((field.split(AccessController.P_REQSIZE))[1]);
            try {
              int size = Integer.parseInt(item.getString());
              if (size <= 0) throw new NumberFormatException();
              options.addRequiredFileMaxSize(size, id);
            } catch (NumberFormatException e) {
              result.addError("Max submission size must be a positive integer");
              proceed = false;
            }
          } else if (field.startsWith(AccessController.P_NEWREQFILENAME)) {
            int id =
                Integer.parseInt((field
                    .split(AccessController.P_NEWREQFILENAME))[1]);
            if (subnames.contains(item.getString())) {
              result.addError("A required submission with name '"
                  + item.getString() + "' already exists");
              proceed = false;
            } else {
              if (item.getString().equals("")) {
                emptySubmissionName = true;
              } else {
                subnames.add(item.getString());
                options.addNewRequiredFileName(item.getString(), id);
                numOfAssignedFiles++;
              }
            }
          } else if (field.startsWith(AccessController.P_NEWREQFILETYPE)) {
            int id =
                Integer.parseInt((field
                    .split(AccessController.P_NEWREQFILETYPE))[1]);
            options.addNewRequiredFileType(item.getString(), id);
          } else if (field.startsWith(AccessController.P_NEWREQSIZE)) {
            int id =
                Integer
                    .parseInt((field.split(AccessController.P_NEWREQSIZE))[1]);
            try {
              int size = Integer.parseInt(item.getString());
              if (size <= 0) throw new NumberFormatException();
              options.addNewRequiredMaxSize(size, id);
            } catch (NumberFormatException e) {
              result.addError("Max submission size must be a positive integer");
              proceed = false;
            }

          } else if (field.startsWith(AccessController.P_NEWITEMNAME)) {
            int id =
                Integer
                    .parseInt((field.split(AccessController.P_NEWITEMNAME))[1]);
            if (filenames.contains(item.getString())) {
              result.addError("An assignment file with name '"
                  + item.getString() + "' already exists");
              proceed = false;
            } else {
              if (item.getString().equals("")) {
                emptyItemName = true;
              } else {
                filenames.add(item.getString());
                options.addNewAssignmentItemName(item.getString(), id);
              }
            }
          } else if (field.startsWith(AccessController.P_ITEMNAME)) {
            long id =
                Long.parseLong((field.split(AccessController.P_ITEMNAME))[1]);
            if (filenames.contains(item.getString())) {
              result.addError("An assignment file with name '"
                  + item.getString() + "' already exists");
              proceed = false;
            } else {
              if (item.getString().equals("")) {
                emptyItemName = true;
              } else {
                filenames.add(item.getString());
                options.addAssignmentItemName(item.getString(), id);
              }
            }
          } else if (field.startsWith(AccessController.P_REMOVEITEM)) {
            long id =
                Long.parseLong((field.split(AccessController.P_REMOVEITEM))[1]);
            options.removeAssignmentItem(id);
          } else if (field.startsWith(AccessController.P_RESTOREREQ)) {
            long id =
                Long.parseLong((field.split(AccessController.P_RESTOREREQ))[1]);
            options.restoreSubmission(id);
            numOfAssignedFiles++;
          } else if (field.startsWith(AccessController.P_RESTOREITEM)) {
            /*
             * XXX This next line breaks eclipse's Content Assist for me for
             * some unfathomable reason. When it's commented out, everything is
             * fine. Any type of new variable declaration has the same effect as
             * long as it's in this exact position in the code -Jon
             */
            long id =
                Long
                    .parseLong((field.split(AccessController.P_RESTOREITEM))[1]);
            options.restoreAssignmentItem(id);
          } else if (field.startsWith(AccessController.P_RESTOREITEMFILE)) {
            String itemfile =
                field.split(AccessController.P_RESTOREITEMFILE)[1];
            String[] itemfiles = itemfile.split("_");
            long itemID = Long.parseLong(itemfiles[0]);
            long fileID = Long.parseLong(itemfiles[1]);
            try {
              options.restoreAssignmentFile(itemID, fileID);
            } catch (FileUploadException e) {
              result.addError(e.getMessage());
              proceed = false;
            }
          } else if (field.startsWith(AccessController.P_REMOVEREQ)) {
            options.removeSubmission(Long.parseLong(field
                .split(AccessController.P_REMOVEREQ)[1]));
            numOfAssignedFiles--;
          } else if (field.equals(AccessController.P_REMOVESOL)) {
            options.removeCurrentSolutionFile();
          } else if (field.startsWith(AccessController.P_RESTORESOL)) {
            long solID =
                Long.parseLong(field.split(AccessController.P_RESTORESOL)[1]);
            try {
              options.restoreSolutionFile(solID);
            } catch (FileUploadException e) {
              result.addError(e.getMessage());
              proceed = false;
            }
          } else if (field.startsWith(AccessController.P_SUBPROBNAME)) {
            long subID =
                Long.parseLong(field.split(AccessController.P_SUBPROBNAME)[1]);
            /*
             * if (probnames.contains(item.getString())) { // if there exists
             * another subporblem with the same name result.addError("A
             * subproblem with name '" + item.getString() + "' already exists");
             * proceed= false; } else
             */if (item.getString().equals("")) {
              emptyProbName = true;
            } else {
              // Assign subproblem orders in the order that they appear in the
              // form
              options.addSubProblemOrder(order, subID);
              options.addSubProblemName(item.getString(), subID);
              probnames.add(item.getString());
              order++;

              // reset the choice lettering
              letter = 'a';
              choices = new SubProblemOptions();
              options.addSubProblemChoices(choices, subID);
            }
          } else if (field.startsWith(AccessController.P_SUBPROBSCORE)) {
            long subID =
                Long.parseLong(field.split(AccessController.P_SUBPROBSCORE)[1]);
            float maxscore = 0.0f;
            try {
              maxscore = StringUtil.parseFloat(item.getString());
              if (maxscore < 0.0f) throw new NumberFormatException();
            } catch (NumberFormatException e) {
              result.addError("Problem scores must be positive numbers");
              proceed = false;
            }
            Long key = new Long(subID);
            if (!probScores.containsKey(key)) {
              probScores.put(key, new Float(maxscore));
            }
            options.addSubProblemScore(maxscore, subID);
          } else if (field.startsWith(AccessController.P_REMOVECHOICE)) {
            long choiceID =
                Long.parseLong(field.split(AccessController.P_REMOVECHOICE)[1]);
            choices.removeChoice(choiceID);
          } else if (field.startsWith(AccessController.P_NEWSUBPROBNAME)) {
            int ID =
                Integer
                    .parseInt(field.split(AccessController.P_NEWSUBPROBNAME)[1]);
            if (probnames.contains(item.getString())) {
              result.addError("A subproblem with name '" + item.getString()
                  + "' already exists");
              proceed = false;
            } else if (item.getString().equals("")) {
              emptyProbName = true;
            } else {
              // Assign subproblem orders in the order that they appear in the
              // form
              options.addNewSubProblemName(item.getString(), ID);
              options.addNewSubProblemOrder(order, ID);
              probnames.add(item.getString());
              order++;

              // reset the choice lettering
              letter = 'a';
              choices = new SubProblemOptions();
              options.addNewSubProblemChoices(choices, ID);
            }
          } else if (field.startsWith(AccessController.P_NEWSUBPROBSCORE)) {
            int ID =
                Integer.parseInt(field
                    .split(AccessController.P_NEWSUBPROBSCORE)[1]);
            float maxscore = 0;
            try {
              maxscore = StringUtil.parseFloat(item.getString());
              if (maxscore < 0.0f) throw new NumberFormatException();
              probScores.put(new Long(-ID), new Float(maxscore));
            } catch (NumberFormatException e) {
              result.addError("Problem scores must be positive numbers");
              proceed = false;
            }
            options.addNewSubProblemScore(maxscore, ID);
          } else if (field.startsWith(AccessController.P_RESTORESUBPROB)) {
            long subID =
                Long
                    .parseLong(field.split(AccessController.P_RESTORESUBPROB)[1]);
            options.restoreSubProblem(subID);
          } else if (field.startsWith(AccessController.P_REMOVESUBPROB)) {
            long subID =
                Long
                    .parseLong(field.split(AccessController.P_REMOVESUBPROB)[1]);
            probScores.put(new Long(subID), new Float(0));
            options.removeSubProblem(subID);
          }
          // Surveys
          else if (field.startsWith(AccessController.P_CORRECTCHOICE)) {
            int ID =
                Integer
                    .parseInt(field.split(AccessController.P_CORRECTCHOICE)[1]);
            int correctChoice = -1;
            try {
              correctChoice = Integer.parseInt(item.getString());
              if (correctChoice < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
              result.addError("Correct choices must be positive numbers");
              proceed = false;
            }
            options.addSubProblemAnswer(correctChoice, ID);
          } else if (field.startsWith(AccessController.P_SUBPROBTYPE)) {
            long questID =
                Long.parseLong(field.split(AccessController.P_SUBPROBTYPE)[1]);
            int questtype = -1;
            try {
              questtype = Integer.parseInt(item.getString());
              if (questtype < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
              result.addError("Question types must be positive numbers");
              proceed = false;
            }
            options.addSubProblemType(questtype, questID);
          } else if (field.startsWith(AccessController.P_CHOICE)) {
            String[] tokens = field.split("_");
            long questID = Long.parseLong(tokens[1]);
            long choiceID = Long.parseLong(tokens[2]);

            choices.addChoiceText(item.getString(), choiceID);
            choices.addChoiceLetter(Character.toString(letter), choiceID);
            letter++;
          } else if (field.startsWith(AccessController.P_NEWCORRECTCHOICE)) {
            int ID =
                Integer.parseInt(field
                    .split(AccessController.P_NEWCORRECTCHOICE)[1]);
            int correctChoice = -1;
            try {
              correctChoice = Integer.parseInt(item.getString());
              if (correctChoice < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
              result.addError("Correct choices must be positive numbers");
              proceed = false;
            }
            options.addNewSubProblemAnswer(correctChoice, ID);
          } else if (field.startsWith(AccessController.P_NEWSUBPROBTYPE)) {
            int ID =
                Integer
                    .parseInt(field.split(AccessController.P_NEWSUBPROBTYPE)[1]);
            int questtype = -1;
            try {
              questtype = Integer.parseInt(item.getString());
              if (questtype < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
              result.addError("Question types must be positive numbers");
              proceed = false;
            }
            options.addNewSubProblemType(questtype, ID);
          } else if (field.startsWith(AccessController.P_NEWSUBPROBORDER)) {
            int ID =
                Integer.parseInt(field
                    .split(AccessController.P_NEWSUBPROBORDER)[1]);
            int questorder = -1;
            try {
              questorder = Integer.parseInt(item.getString());
              if (questorder < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
              result.addError("Question orders must be positive numbers");
              proceed = false;
            }
          } else if (field.startsWith(AccessController.P_NEWCHOICE)) {

            String[] tokens = field.split("_");

            int questID = Integer.parseInt(tokens[1]);
            int choiceID = Integer.parseInt(tokens[2]);
            choices.addNewChoiceText(item.getString(), choiceID);
            choices.addNewChoiceLetter(Character.toString(letter), choiceID);
            letter++;
          } else {
            System.out.println("Not parsed: " + item.getString());
          }
        } else if (!item.getName().equals("")) { // file to be downloaded
          if (field.equals(AccessController.P_SOLFILE)) {
            options.addSolutionFile(item);

          } else if (field.startsWith(AccessController.P_NEWITEMFILE)) {
            int id =
                Integer.parseInt(item.getFieldName().split(
                    AccessController.P_NEWITEMFILE)[1]);
            options.addNewAssignmentFile(item, id);
          } else if (field.startsWith(AccessController.P_ITEMFILE)) {
            long id =
                Long.parseLong(item.getFieldName().split(
                    AccessController.P_ITEMFILE)[1]);
            options.addReplacementAssignmentFile(item, id);
          } else {
            System.out.println("Not parsed (file): " + field);
          }
        }
      }
      if (name == null || name.equals("")) {
        result.addError("Name must be non-empty");
      }
      if (nameshort == null || nameshort.equals("")) {
        result.addError("Name short must be non-empty");
      }
      if (duedate == null || duetime == null || dueampm == null)
        result.addError("Due date is not in the proper format");
      else {
        try {
          due = DateTimeUtil.parseDate(duedate, duetime, dueampm);
        } catch (ParseException pe) {
          result.addError("Due date is not in the proper format");
          proceed = false;
        } catch (IllegalArgumentException iae) {
          result.addError("Due date " + iae.getMessage());
          proceed = false;
        }
      }
      if (emptyProbName) {
        result.addError("Problem names cannot be empty");
        proceed = false;
      }
      if (emptyItemName) {
        result.addError("Assignment file names must not be empty");
      }
      if (emptySubmissionName) {
        result.addError("Required submission names must not be empty");
      }
      if (latesubmissions) {
        if (latedate == null || latetime == null || lateampm == null)
          result
              .addError("Late submission deadline is not in the proper format");
        else {
          try {
            late = DateTimeUtil.parseDate(latedate, latetime, lateampm);
          } catch (ParseException pe) {
            result
                .addError("Late submission deadline is not in the proper format");
            proceed = false;
          } catch (IllegalArgumentException iae) {
            result.addError("Late submission deadline " + iae.getMessage());
            proceed = false;
          }
        }
      }
      if (useSchedule) {
        if (TSTimeStr == null)
          result.addError("Timeslot duration is not in the proper format");
        else {
          try {
            TSDuration = Long.parseLong(TSTimeStr);
          } catch (Exception e) {
            result.addError("Timeslot duration is not in the proper format");
          }
        }
        if (tslockdate.equals("") || tslocktime.equals("")) {
          TSLockedTime = null; // empty input means no deadline to set schedule
        } else {
          try {
            TSLockedTime =
                DateTimeUtil.parseDate(tslockdate, tslocktime, tslockampm);
          } catch (ParseException pe) {
            result
                .addError("Schedule change deadline is not in the proper format");
            proceed = false;
          } catch (IllegalArgumentException iae) {
            result.addError("Schedule change deadline " + iae.getMessage());
            proceed = false;
          }
        }
      }
      if (groupoption == 0) {
        groupmin = 1;
        groupmax = 1;
      } else if (groupoption == 2) {
        groupmin = 0;
        groupmax = 0;
        assignedgroups = true;
      } else if (groupoption == 1) {
        if (groupmin < 1) {
          result.addError("Minimum group size must be a positive integer");
          proceed = false;
        }
        if (groupmax < 1) {
          result.addError("Maximum group size must be a positive integer");
          proceed = false;
        }
        if (!(groupmin <= groupmax)) {
          result.addError("Please specify a valid group size range");
          proceed = false;
        }
      }
      if (regradeoption == 0) {
        studentregrades = false;
      } else if (regradeoption == 1) {
        studentregrades = true;
        if (regradedate == null || regradetime == null || regradeampm == null)
          result
              .addError("Regrade submission deadline is not in the proper format");
        else {
          try {
            regradedeadline =
                DateTimeUtil.parseDate(regradedate, regradetime, regradeampm);
          } catch (ParseException pe) {
            result
                .addError("Regrade submission deadline is not in the proper format");
            proceed = false;
          } catch (IllegalArgumentException iae) {
            result.addError("Regrade submission deadline " + iae.getMessage());
            proceed = false;
          }
        }
      }
      Iterator pScores = probScores.values().iterator();
      float total = 0.0f;
      while (pScores.hasNext()) {
        Float val = (Float) pScores.next();
        total += val.floatValue();
      }
      // don't check this for quizzes for now
      if (probScores.size() > 0 && total != score
          && type != AssignmentBean.QUIZ) {
        result.addError("Problem scores sum (" + total
            + ") does not equal the Total Score (" + score + ")");
      }
      CourseLocal course =
          database.courseHome().findByPrimaryKey(new CoursePK(courseID));
      long itemID = -1;
      int ID = -1;
      Iterator itemIDs = options.getReplacedAssignmentItemIDs().iterator();
      Iterator IDs = options.getNewAssignmentItemIDs().iterator();
      while (itemIDs.hasNext() || IDs.hasNext()
          || options.hasUncheckedSolutionFile()) {
        try {
          boolean oldFile = itemIDs.hasNext(); // itemID > 0;
          boolean solFile = !oldFile && !IDs.hasNext(); // ID < 0;
          FileItem data;
          String fileName = null;
          String[] givenName;
          if (solFile) {
            data = options.getSolutionFile();
            givenName =
                FileUtil.splitFileNameType(FileUtil
                    .trimFilePath(data.getName()));
            fileName = givenName[0];
          } else if (oldFile) {
            itemID = ((Long) itemIDs.next()).longValue();
            data = options.getFileItemByID(itemID);
            fileName = options.getItemNameByID(itemID);
            givenName =
                data == null ? null : FileUtil.splitFileNameType(FileUtil
                    .trimFilePath(data.getName()));
          } else {
            ID = ((Integer) IDs.next()).intValue();
            data = options.getNewFileItemByID(ID);
            fileName = options.getNewItemNameByID(ID);
            givenName =
                data == null ? null : FileUtil.splitFileNameType(FileUtil
                    .trimFilePath(data.getName()));
          }
          if (data == null && (fileName == null || fileName.equals(""))) {
            continue;
          } else if (fileName == null || fileName.equals("")) {
            throw new FileUploadException(
                "Must provide a name for assignment file " + givenName[0]
                    + (givenName[1].equals("") ? "" : "." + givenName[1]));
          } else if (data == null) {
            throw new FileUploadException(
                "No file provided for assignment file " + fileName);
          }
          String givenFileName =
              fileName + (givenName[1].equals("") ? "" : ("." + givenName[1]));
          /*
           * Some browsers will return an entire path name with the file name,
           * so we trim that here
           */
          givenFileName = FileUtil.trimFilePath(givenFileName);
          long fileCounter;
          java.io.File path, file;
          fileCounter = transactions.getCourseFileCounter(course.getCourseID());
          if (solFile)
            file =
                new java.io.File(
                    FileUtil
                        .getSolutionFileSystemPath(
                            course.getSemesterID(),
                            course.getCourseID(),
                            -1 /*
                                 * TODO use asgn ID (doesn't matter until we
                                 * change the filesystem format)
                                 */,
                            fileCounter, givenFileName));
          else file =
              new java.io.File(
                  FileUtil
                      .getAssignmentFileSystemPath(
                          course.getSemesterID(),
                          course.getCourseID(),
                          -1 /*
                               * TODO use asgn ID (doesn't matter until we
                               * change the filesystem format)
                               */,
                          fileCounter, givenFileName));
          path = file.getParentFile();
          if (path.exists())
            throw new FileUploadException(
                "Failed to find a unique path on the file system");
          if (!path.mkdirs())
            throw new FileUploadException(
                "Failed to create new directory in the file system");
          if (!file.createNewFile())
            throw new FileUploadException(
                "Failed to create new file in file system");
          data.write(file);
          if (solFile) {
            options.addFinalSolutionFile(new SolutionFileData(0, 0,
                givenFileName, false, path.getAbsolutePath()));
          } else if (oldFile) {
            options.setReplacementFinalFileByID(itemID, new AssignmentFileData(
                0, itemID, givenFileName, null, false, path.getAbsolutePath()));
          } else {
            options.setNewFinalFileByID(ID, new AssignmentFileData(0, 0,
                givenFileName, null, false, path.getAbsolutePath()));
          }
        } catch (FileUploadException e) {
          result.addError(e.getMessage());
          proceed = false;
        }
      }
      if (importID != 0) {
        options.setGroupMigration(importID);
        try {
          AssignmentLocal importAssign =
              database.assignmentHome().findByAssignmentID(importID);
          if (importAssign.getCourseID() != courseID) {
            result
                .addError("Cannot import groups: assignments are not in the same course");
            proceed = false;
          }
        } catch (FinderException e) {
          result
              .addError("Assignment used for importing groups does not exist");
          proceed = false;
        }
      }
      if (proceed && !result.hasErrors()) {
        AssignmentData data = new AssignmentData();
        data.setAssignmentID(assignID);
        data.setCourseID(courseID);
        data.setName(name);
        data.setNameShort(nameshort);
        data.setDescription(description);
        data.setDueDate(due);
        data.setGracePeriod(graceperiod);
        data.setAllowLate(latesubmissions);
        data.setLateDeadline(late);
        data.setStatus(status);
        data.setGroupSizeMax(groupmax);
        data.setGroupSizeMin(groupmin);
        data.setAssignedGroups(assignedgroups);
        data.setAssignedGraders(assignedgraders);
        data.setStudentRegrades(studentregrades);
        data.setRegradeDeadline(regradedeadline);

        // if score was not set, compute maxScore by adding all subproblem
        // scores
        if (score < 0.01)
          data.setMaxScore(options.getMaxScore());
        else data.setMaxScore(score);

        data.setWeight(weight);
        data.setAssignedGroups(assignedgroups);
        data.setShowStats(showstats);
        data.setShowSolution(showsolution);
        data.setNumOfAssignedFiles(numOfAssignedFiles);
        data.setScheduled(useSchedule);
        data.setDuration(new Long(TSDuration));
        data.setGroupLimit(new Integer(TSMaxGroups));
        data.setTimeslotLockTime(TSLockedTime);
        data.setType(type);
        if ((assignID == 0)) { // new assignment
          result = transactions.createNewAssignment(p, data, options);
        } else {
          result = transactions.setAssignmentProps(p, data, options);
        }
      }
    } catch (FileUploadException e) {
      result.addError(FileUtil.checkFileException(e));
    } catch (Exception e) {
      e.printStackTrace();
      result.addError("Unexpected error while trying to "
          + (assignID == 0 ? "create" : "edit") + " this assignment");
      result.setException(e);
    }
    Profiler.exitMethod("TransactionHandler.setAssignmentProps",
        "AssignmentID: " + assignID);
    result.setValue(info);
    return result;
  }

  /**
   * Set properties for an existing category or create a category for the given
   * course
   * 
   * @param p
   * @param categoryID
   *          0 to create a new category; else the ID of the category to edit
   * @param courseID
   * @param request
   * @return TransactionResult
   */
  public TransactionResult createNEditCategory(Principal p, Category category,
      Course course, HttpServletRequest request) {
    TransactionResult result = new TransactionResult();
    String ctgName = "";
    CategoryTemplate ctgTempl = new CategoryTemplate();
    ctgTempl.setCourseID(courseID);
    CategoriesOption option = new CategoriesOption();
    DiskFileUpload upload = new DiskFileUpload();
    long colId;
    boolean newCategory = false, success = true;
    try {
      List info =
          upload.parseRequest(request, 1024, AccessController.maxFileSize,
              FileUtil.TEMP_DIR);
      if (courseIsFrozen(courseID)) {
        result.addError("Course is frozen; no changes may be made to it");
        return result;
      }
      Iterator i = info.iterator();
      while (i.hasNext()) {
        FileItem item = (FileItem) i.next();
        String field = item.getFieldName();
        System.out.println("createNEditCtg: item: " + item.getFieldName() + "="
            + item.getString());
        if (item.isFormField()) {
          if (field.equals(AccessController.P_NEWCTGNAME)) {
            ctgName = item.getString();
            ctgTempl.setCategoryName(ctgName);
            newCategory = true;
          } else if (field.startsWith(AccessController.P_CTGNAME)) {
            String id = (field.split(AccessController.P_CTGNAME))[1];
            ctgTempl.setCategoryID(Long.parseLong(id));
            ctgName = item.getString();
            ctgTempl.setCategoryName(ctgName);
          } else if (field.equals(AccessController.P_ORDER)) {
            if (item.getString().equals(CategoryTemplate.ASCENDING))
              ctgTempl.setAscending(true);
            else ctgTempl.setAscending(false);
          } else if (field.equals(AccessController.P_COLSORTBY)) {
            String id = item.getString();
            if (id.startsWith(AccessController.P_PREFIX_NEW_CONTENT)) {
              id = (id.split(AccessController.P_PREFIX_NEW_CONTENT)[1]);
              colId = Long.parseLong(id);
              ctgTempl.addNewSortByColID(colId);
            } else {
              colId = Long.parseLong(id);
              ctgTempl.addOldSortByColID(colId);
            }
          } else if (field.equals(AccessController.P_NUMSHOWITEMS)) {
            try {
              if (item.getString().equals(""))
                ctgTempl.setNumShowItems(CategoryTemplate.SHOWALL);
              else ctgTempl.setNumShowItems(Long.parseLong(item.getString()));
            } catch (NumberFormatException e) {
              e.printStackTrace();
              result.addError("Max items to show must be a positive number");
            }
          } else if (field.equals(AccessController.P_AUTHORZN)) {
            int authorzn = Integer.parseInt(item.getString());
            ctgTempl.setAuthorzn(authorzn);
          } else if (field.startsWith(AccessController.P_NEWCOLNAME)) {
            String id = (field.split(AccessController.P_NEWCOLNAME))[1];
            colId = Long.parseLong(id);
            ctgTempl.addNewColumnName(item.getString(), colId);
          } else if (field.startsWith(AccessController.P_NEWCOLTYPE)) {
            String id = (field.split(AccessController.P_NEWCOLTYPE))[1];
            colId = Long.parseLong(id);
            ctgTempl.addNewColumnType(item.getString(), colId);
          } else if (field.startsWith(AccessController.P_NEWCOLHIDDEN)) {
            String id = (field.split(AccessController.P_NEWCOLHIDDEN))[1];
            colId = Long.parseLong(id);
            // if we're seeing this field at all, its value is equivalent to
            // true
            ctgTempl.addNewColumnHidden(true, colId);
          } else if (field.startsWith(AccessController.P_NEWCOLPOSITION)) {
            String id = (field.split(AccessController.P_NEWCOLPOSITION))[1];
            colId = Long.parseLong(id);
            ctgTempl.addNewColumnPosition(Long.parseLong(item.getString()),
                colId);
          } else if (field.startsWith(AccessController.P_COLNAME)) {
            String id = (field.split(AccessController.P_COLNAME))[1];
            colId = Long.parseLong(id);
            ctgTempl.addOldColumnName(item.getString(), colId);
          } else if (field.startsWith(AccessController.P_COLPOSITION)) {
            String id = (field.split(AccessController.P_COLPOSITION)[1]);
            colId = Long.parseLong(id);
            ctgTempl.addOldColumnPosition(Long.parseLong(item.getString()),
                colId);
          } else if (field.startsWith(AccessController.P_COLHIDDEN)) {
            String id = (field.split(AccessController.P_COLHIDDEN)[1]);
            colId = Long.parseLong(id);
            ctgTempl.addOldColumnHidden(Boolean.valueOf(item.getString())
                .booleanValue(), colId);
          } else if (field.startsWith(AccessController.P_REMOVECOL)) {
            String id = (field.split(AccessController.P_REMOVECOL)[1]);
            colId = Long.parseLong(id);
            ctgTempl.updateRemoved(colId, true);
          } else if (field.startsWith(AccessController.P_RESTORECOL)) {
            String id = (field.split(AccessController.P_RESTORECOL)[1]);
            colId = Long.parseLong(id);
            ctgTempl.updateRemoved(colId, false);
          } else if (field.startsWith(AccessController.P_CTGPOSITION)) {
            String id = (field.split(AccessController.P_CTGPOSITION))[1];
            long ctgID = Long.parseLong(id);
            int ctgPosition = Integer.parseInt(item.getString());
            option.setCtgPositn(ctgID, ctgPosition);
          } else if (field.startsWith(AccessController.P_NEWCTGPOSITION)) {
            int ctgPosition = Integer.parseInt(item.getString());
            ctgTempl.setPositn(ctgPosition);
          } else if (field.startsWith(AccessController.P_REMOVECTG)) {
            String id = (field.split(AccessController.P_REMOVECTG))[1];
            long ctgID = Long.parseLong(id);
            option.setCtgRemove(ctgID);
          } else if (field.startsWith(AccessController.P_RESTORECTG)) {
            String id = (field.split(AccessController.P_RESTORECTG))[1];
            long ctgID = Long.parseLong(id);
            option.setCtgRestore(ctgID);
          }
        }
      }
      if (newCategory)
        success = transactions.createCategory(p, ctgTempl, option);
      else transactions.updateCategory(p, ctgTempl);
      if (!success)
        result
            .addError("Unexpected error while trying to create/edit content properties");
    } catch (FileUploadException e) {
      result.addError(FileUtil.checkFileException(e));
    } catch (Exception e) {
      e.printStackTrace();
      result
          .addError("Unexpected error while trying to create/edit content properties");
    }
    return result;
  }

  /**
   * Set course properties and course staff member permissions; add and remove
   * staff members
   * 
   * @param courseID
   *          The ID of the course to work on
   * @param map
   *          A map of various AccessController-defined property strings to
   *          String values. Current staff NetIDs not present in the map are
   *          removed from the course; current and new staff have permissions
   *          set here.
   * @return TransactionResult
   */
  public TransactionResult setCourseProps(Principal p, Course course, Map map) {
    TransactionResult result = new TransactionResult();
    try {
      boolean isFreeze =
          ((String[]) map.get(AccessController.P_FREEZECOURSE)) != null;
      if (isFreeze && courseIsFrozen(courseID))
        result.addError("Course is frozen; no changes may be made to it");
      else {
        CourseLocal course =
            database.courseHome().findByPrimaryKey(new CoursePK(courseID));
        boolean isAdmin, hasAdmin = false;
        // put request data into a nicer form
        Vector staff2remove = new Vector(); // netid
        Hashtable staffPerms = new Hashtable(); // netid ->
                                                // CourseAdminPermissions
        // remove current staff members and set remaining staff member
        // permissions
        Iterator iter = course.getStaff().iterator();
        while (iter.hasNext()) {
          String netID = ((StaffData) iter.next()).getNetID();
          String[] mapRemIDs =
              (String[]) map.get(netID + AccessController.P_REMOVE);
          if (mapRemIDs != null) { // the staff should be removed
            if (netID.equalsIgnoreCase(p.getNetID())) {
              result.addError("Cannot remove yourself as a staff member");
              // return result;
            }
            staff2remove.add(netID);
          } else { // set staff permissions
            isAdmin = map.containsKey(netID + AccessController.P_ISADMIN);
            hasAdmin = hasAdmin || isAdmin;
            staffPerms.put(netID, new CourseAdminPermissions(isAdmin, map
                .containsKey(netID + AccessController.P_ISASSIGN), map
                .containsKey(netID + AccessController.P_ISGROUPS), map
                .containsKey(netID + AccessController.P_ISGRADES), map
                .containsKey(netID + AccessController.P_ISCATEGORY)));
          }
        }
        // add new staff
        int i = 0;
        String[] newnetids =
            (String[]) map.get(AccessController.P_NEWNETID + i);
        while (newnetids != null) {
          isAdmin = map.containsKey(AccessController.P_NEWADMIN + i);
          hasAdmin = hasAdmin || isAdmin;
          staffPerms.put(newnetids[0].toLowerCase().trim(),
              new CourseAdminPermissions(isAdmin, map
                  .containsKey(AccessController.P_NEWASSIGN + i), map
                  .containsKey(AccessController.P_NEWGROUPS + i), map
                  .containsKey(AccessController.P_NEWGRADES + i), map
                  .containsKey(AccessController.P_NEWCATEGORY + i)));
          ++i;
          newnetids = (String[]) map.get(AccessController.P_NEWNETID + i);
        }
        if (!hasAdmin) {
          result
              .addError("Must have at least one staff member with admin privilege");
        }
        if (result.hasErrors()) {
          return result;
        }
        // set course general properties
        CourseProperties generalProperties =
            new CourseProperties(
                ((String[]) map.get(AccessController.P_NAME))[0],
                ((String[]) map.get(AccessController.P_CODE))[0],
                ((String[]) map.get(AccessController.P_DISPLAYEDCODE))[0],
                /*
                 * note course description is now edited from main course page,
                 * so editing it is a separate action; see
                 * TransactionHandler::editCourseDescription()
                 * ((String[])map.get(AccessController.P_DESCRIPTION))[0],
                 */
                course.getDescription(),
                ((String[]) map.get(AccessController.P_FREEZECOURSE)) != null,
                ((String[]) map.get(AccessController.P_FINALGRADES)) != null,
                ((String[]) map.get(AccessController.P_SHOWTOTALSCORES)) != null,
                ((String[]) map.get(AccessController.P_SHOWASSIGNWEIGHTS)) != null,
                ((String[]) map.get(AccessController.P_SHOWGRADERID)) != null,
                ((String[]) map.get(AccessController.P_HASSECTION)) != null,
                ((String[]) map.get(AccessController.P_COURSEGUESTACCESS)) != null,
                ((String[]) map.get(AccessController.P_ASSIGNGUESTACCESS)) != null,
                ((String[]) map.get(AccessController.P_ANNOUNCEGUESTACCESS)) != null,
                ((String[]) map.get(AccessController.P_SOLUTIONGUESTACCESS)) != null,
                ((String[]) map.get(AccessController.P_COURSECCACCESS)) != null,
                ((String[]) map.get(AccessController.P_ASSIGNCCACCESS)) != null,
                ((String[]) map.get(AccessController.P_ANNOUNCECCACCESS)) != null,
                ((String[]) map.get(AccessController.P_SOLUTIONCCACCESS)) != null);
        result =
            transactions.setAllCourseProperties(p, courseID, staff2remove,
                staffPerms, generalProperties);
      }
    } catch (Exception e) {
      result.addError("Error while trying to set course properties");
      e.printStackTrace();
    }
    return result;
  }

  public TransactionResult editCourseDescription(Principal p, Course course,
      String newDescription) {
    TransactionResult result = new TransactionResult();
    try {
      if (courseIsFrozen(courseID))
        result.addError("Course is frozen; no changes may be made to it");
      else {
        result.appendErrors(transactions.editCourseDescription(p, courseID,
            newDescription));
      }
    } catch (Exception x) {
      result
          .addError("Unexpected error while trying to set course description");
      x.printStackTrace();
    }
    return result;
  }

  /**
   * Set the active semester for future operations
   * 
   * @return TransactionResult
   */
  public TransactionResult setCurrentSemester(Principal p, Semester sem) {
    TransactionResult result = new TransactionResult();
    boolean success = true;
    try {
      success = transactions.setCurrentSemester(p, semesterID);
    } catch (Exception e) {
      success = false;
      result.addError("Database failed to set current semester");
      e.printStackTrace();
    }
    return result;
  }

  public TransactionResult setExtension(Principal p, Group group, HttpServletRequest request) {
    TransactionResult result = new TransactionResult();
    try {
      GroupLocal group = database.groupHome().findByGroupID(groupID);
      AssignmentLocal assign =
          database.assignmentHome().findByAssignmentID(group.getAssignmentID());
      if (courseIsFrozen(assign.getCourseID())) {
        result.addError("Course is frozen; no changes may be made to it");
      }
      if (!result.hasErrors()) {
        String duedate = null, duetime = null, dueampm = null;
        Date extension = null;
        duedate = request.getParameter(AccessController.P_DUEDATE + groupID);
        duetime = request.getParameter(AccessController.P_DUETIME + groupID);
        dueampm = request.getParameter(AccessController.P_DUEAMPM + groupID);
        if (duedate == null || duetime == null || dueampm == null)
          result.addError("Extension date is not in the proper format");
        else {
          try {
            extension = DateTimeUtil.parseDate(duedate, duetime, dueampm);
          } catch (ParseException pe) {
            result.addError("Extension date is not in the proper format");
          } catch (IllegalArgumentException iae) {
            result.addError("Extension date is not in the proper format");
          }
          result = transactions.setExtension(p, groupID, extension);
        }
      }
    } catch (Exception e) {
      result.addError("Unexpected error; could not grant extension");
      e.printStackTrace();
    }
    return result;
  }

  /**
   * Remove one or more timeslots from the course schedule
   * 
   * @return TransactionResult
   */
  public TransactionResult deleteTimeSlots(Principal p, Assignment assign,
      HttpServletRequest request) {
    TransactionResult result = new TransactionResult();
    try {
      AssignmentLocal assignment =
          database.assignmentHome()
              .findByPrimaryKey(new AssignmentPK(assignID));
      Course course = assignment.getCourseID();
      if (courseIsFrozen(courseID)) {
        result.addError("Course is frozen; no changes may be made to it");
        return result;
      }
      ArrayList toDelete = new ArrayList();
      DiskFileUpload u = new DiskFileUpload();
      Iterator i = u.parseRequest(request).iterator();
      // iterate through request to find all fields of form deletetimeslot_##
      while (i.hasNext()) {
        FileItem item = (FileItem) i.next();
        String field = item.getFieldName();
        if (field.startsWith(AccessController.P_DELETETIMESLOT)) {
          long id =
              Long
                  .parseLong((field.split(AccessController.P_DELETETIMESLOT))[1]);
          TimeSlotLocal ts = database.timeSlotHome().findByTimeSlotID(id);
          if (ts.getHidden()) {
            result.addError("Timeslot has already been deleted");
          } else {
            if (ts.getStaff().equals(p.getNetID())
                || p.isAdminPrivByCourseID(database.assignmentHome()
                    .findByAssignmentID(assignID).getCourseID()))
              toDelete.add(ts);
            else {
              result
                  .addError("User lacks permission to remove selected timeslot");
            }
          }
        }
      }
      boolean success = transactions.removeTimeSlots(p, assignID, toDelete);
      if (!success) {
        result.addError("Database failed to delete selected time slots");
      }
    } catch (FileUploadException e) {
      result.addError(FileUtil.checkFileException(e));
    } catch (Exception e) {
      e.printStackTrace();
      result.addError("Unexpected error while trying to remvoe timeslot(s)");
    }
    return result;
  }

  /**
   * Create a block of timeslots
   * 
   * @return TransactionResult
   */
  public TransactionResult createTimeSlots(Principal p, Assignment assign,
      HttpServletRequest request) {
    TransactionResult result = new TransactionResult();
    try {
      AssignmentLocal assign =
          database.assignmentHome().findByAssignmentID(assignID);
      Course course = assign.getCourseID();
      if (courseIsFrozen(courseID)) {
        result.addError("Course is frozen; no changes may be made to it");
        return result;
      }
      if (!assign.getScheduled()) {
        // scheduling not enabled for this assignment
        result.addError("Scheduling not enabled for this assignment");
        return result;
      }
      String name = null;
      String staff = null;
      String location = null;
      int multiplicity = 0;
      Date startTime = null;
      // raw info about start time; to be converted to timestamp
      String sDate = null;
      String sTime = null;
      String sAMPM = null;
      DiskFileUpload u = new DiskFileUpload();
      Iterator i = u.parseRequest(request).iterator();
      while (i.hasNext()) {
        FileItem item = (FileItem) i.next();
        String field = item.getFieldName();
        if (field.equals(AccessController.P_NEWTSNAME))
          name = item.getString();
        else if (field.equals(AccessController.P_NEWTSLOCATION))
          location = item.getString();
        else if (field.equals(AccessController.P_NEWTSSTAFF))
          staff = item.getString();
        else if (field.equals(AccessController.P_NEWTSMULTIPLICITY))
          multiplicity = Integer.parseInt(item.getString());
        else if (field.equals(AccessController.P_NEWTSSTARTDATE))
          sDate = item.getString();
        else if (field.equals(AccessController.P_NEWTSSTARTTIME))
          sTime = item.getString();
        else if (field.equals(AccessController.P_NEWTSSTARTAMPM))
          sAMPM = item.getString();
      }

      // parse the start time information into a timestamp
      if (sDate == null || sTime == null || sAMPM == null) {
        result.addError("Time slot start time is not in the proper format");
        return result;
      } else {
        try {
          startTime = DateTimeUtil.parseDate(sDate, sTime, sAMPM);
        } catch (ParseException pe) {
          result.addError("Timeslot start time is not in the proper format");
          return result;
        } catch (IllegalArgumentException iae) {
          result.addError("Timeslot start time " + iae.getMessage());
          return result;
        }
      }
      // store the information in a TimeSlotData structure containing the
      // timestamp of the
      // first timeslot in the block
      TimeSlotData tsd =
          new TimeSlotData((long) 0, assignID, courseID, name, location, staff,
              startTime, false, 0);
      // perform transaction
      boolean success = transactions.createTimeSlots(p, tsd, multiplicity);
      if (!success) {
        result.addError("Database failed to add to schedule");
      }

    } catch (FileUploadException e) {
      result.addError(FileUtil.checkFileException(e));
    } catch (Exception e) {
      e.printStackTrace();
      result.addError("Unexpected error while trying to add to schedule");
    }
    return result;
  }

  public TransactionResult setFinalGrades(Principal p, Course course,
      HttpServletRequest request) {
    TransactionResult result = new TransactionResult();
    boolean success = true;
    DiskFileUpload upload = new DiskFileUpload();
    Iterator list = null;
    try {
      if (courseIsFrozen(courseID)) {
        result.addError("Course is frozen; no changes may be made to it");
        return result;
      }
      list = upload.parseRequest(request).iterator();
    } catch (Exception e) {
      success = false;
      e.printStackTrace();
    }
    Collection grades = new Vector();
    while (success && list.hasNext()) {
      FileItem item = (FileItem) list.next();
      if (item.getFieldName().startsWith(AccessController.P_FINALGRADE)) {
        String netid =
            item.getFieldName().split(AccessController.P_FINALGRADE)[1];
        String grade = item.getString();
        grades.add(new String[] { netid, grade });
      }
    }
    try {
      success = transactions.setFinalGrades(p, courseID, grades);
    } catch (Exception e) {
      e.printStackTrace();
      success = false;
    }
    if (!success) {
      result.addError("Database failed to accept updated final grades.");
    }
    return result;
  }

  public TransactionResult setStaffPrefs(Principal p, Course course,
      HttpServletRequest request) {
    TransactionResult result = new TransactionResult();
    try {
      if (courseIsFrozen(courseID)) {
        result.addError("Course is frozen; no changes may be made to it");
      } else {
        StaffData data = new StaffData();
        data.setEmailDueDate(false);
        data.setEmailFinalGrade(false);
        data.setEmailNewAssign(false);
        data.setEmailAssignedTo(false);
        data.setEmailRequest(false);
        DiskFileUpload upload = new DiskFileUpload();
        Iterator i = upload.parseRequest(request).iterator();
        while (i.hasNext()) {
          FileItem item = (FileItem) i.next();
          String field = item.getFieldName();
          if (field.equals(AccessController.P_PREF_DATECHANGE)) {
            data.setEmailDueDate(true);
          } else if (field.equals(AccessController.P_PREF_NEWASSIGN)) {
            data.setEmailNewAssign(true);
          } else if (field.equals(AccessController.P_PREF_FINALGRADES)) {
            data.setEmailFinalGrade(true);
          } else if (field.equals(AccessController.P_PREF_ASSIGNEDTO)) {
            data.setEmailAssignedTo(true);
          } else if (field.equals(AccessController.P_PREF_REGRADEREQUEST)) {
            data.setEmailRequest(true);
          }
        }
        result = transactions.setStaffPrefs(p, courseID, data);
      }
    } catch (Exception e) {
      e.printStackTrace();
      result
          .addError("Unexpected error while trying to set course preferences");
    }
    return result;
  }

  public TransactionResult setStudentPrefs(Principal p, Course course,
      HttpServletRequest request) {
    TransactionResult result = new TransactionResult();
    try {
      if (courseIsFrozen(courseID)) {
        result.addError("Course is frozen; no changes may be made to it");
      } else {
        StudentData data = buildStudentData(request);
        result = transactions.setStudentPrefs(p, courseID, data);
      }
    } catch (Exception e) {
      e.printStackTrace();
      result
          .addError("Unexpected error while trying to set course preferences");
    }
    return result;
  }

  public TransactionResult setAllStudentPrefs(Principal p,
      HttpServletRequest request) {
    TransactionResult result = new TransactionResult();
    try {
      StudentData data = buildStudentData(request);
      result = transactions.setAllStudentPrefs(p, data);
    } catch (Exception e) {
      e.printStackTrace();
      result
          .addError("Unexpected error while trying to set course preferences");
    }
    return result;
  }

  private Student buildStudentData(HttpServletRequest request)
      throws FileUploadException {
    StudentData data = new StudentData();
    data.setEmailDueDate(request
        .getParameter(AccessController.P_PREF_DATECHANGE) != null);
    data
        .setEmailFile(request.getParameter(AccessController.P_PREF_FILESUBMIT) != null);
    data.setEmailFinalGrade(request
        .getParameter(AccessController.P_PREF_FINALGRADES) != null);
    data
        .setEmailGroup(request.getParameter(AccessController.P_PREF_INVITATION) != null);
    data.setEmailNewAssignment(request
        .getParameter(AccessController.P_PREF_NEWASSIGN) != null);
    data.setEmailNewGrade(request
        .getParameter(AccessController.P_PREF_GRADERELEASE) != null);
    data.setEmailRegrade(request
        .getParameter(AccessController.P_PREF_GRADECHANGE) != null);
    data.setEmailTimeSlot(request
        .getParameter(AccessController.P_PREF_TIMESLOTCHANGE) != null);
    return data;
  }

  /**
   * Attempts to accept submitted files as sent through the given
   * HttpServletRequest from the user. Returns a string describing the error if
   * the transaction failed and was rolled back. Returns a null string on
   * success.
   * 
   * @param netid
   * @param request
   * @return TransactionResult
   */
  public TransactionResult submitFiles(Principal p, HttpServletRequest request) {
    Profiler.enterMethod("TransactionHandler.submitFiles", "");
    TransactionResult result = new TransactionResult();
    try {
      long assignmentid =
          Long.parseLong(request.getParameter(AccessController.P_ASSIGNID));
      AssignmentData assignment =
          database.assignmentHome().findByAssignmentID(assignmentid)
              .getAssignmentData();
      if (courseIsFrozen(assignment.getCourseID()))
        result.addError("Course is frozen; no changes may be made to it");
      else {
        GroupData group =
            database.groupHome().findByNetIDAssignmentID(p.getNetID(),
                assignmentid).getGroupData();
        // Get current time while adjusting for grace period
        long graceperiod = assignment.getGracePeriod() * 60000;
        Date now = new Date(System.currentTimeMillis() - graceperiod);
        if (now.after(assignment.getDueDate())
            && (!assignment.getAllowLate() || now.after(assignment
                .getLateDeadline()))
            && (group.getExtension() == null || now.after(group.getExtension()))) {
          result
              .addError("Submissions for this assignment are no longer being accepted");
        } else {
          boolean isLate =
              (group.getExtension() == null)
                  && assignment.getDueDate().before(now);
          Collection files = getUploadedFiles(p.getNetID(), request, isLate);
          result.addError(transactions.submitFiles(p, assignmentid, files));
        }
      }
    } catch (FileUploadException e) {
      if (e.getMessage().equalsIgnoreCase(FileUtil.SIZE_VIOLATION))
        result
            .addError("A submitted file violates the CMS maximum file size of "
                + AccessController.maxFileSize + " bytes");
      else if (e.getMessage().startsWith("match fail")) {
        String err = e.getMessage();
        String fileName = err.substring(err.indexOf(":") + 1);
        result.addError("File '" + fileName
            + "' failed to match an accepted type");
      } else if (e.getMessage().startsWith("size violation")) {
        String err = e.getMessage();
        String fileName = err.substring(err.indexOf(":") + 1);
        result.addError("File '" + fileName
            + "' violated the maximum size limitation");
      } else {
        result.addError(e.getLocalizedMessage());
      }
    } catch (Exception e) {
      result.addError("Error accessing database; files could not be submitted");
    }
    Profiler.exitMethod("TransactionHandler.submitFiles", "");
    return result;
  }

  /**
   * Attempts to accept a survey HttpServletRequest from the user. Returns a
   * string describing the error if the transaction failed and was rolled back.
   * Returns a null string on success.
   * 
   * @param netid
   * @param request
   * @return TransactionResult
   */
  public TransactionResult submitSurvey(Principal p, HttpServletRequest request) {
    Profiler.enterMethod("TransactionHandler.submitFiles", "");
    TransactionResult result = new TransactionResult();
    List info = null;
    List answers = null;
    DiskFileUpload upload = new DiskFileUpload();
    try {
      long assignmentid =
          Long.parseLong(request.getParameter(AccessController.P_ASSIGNID));
      AssignmentData assignment =
          database.assignmentHome().findByAssignmentID(assignmentid)
              .getAssignmentData();
      if (courseIsFrozen(assignment.getCourseID()))
        result.addError("Course is frozen; no changes may be made to it");
      else {
        GroupData group =
            database.groupHome().findByNetIDAssignmentID(p.getNetID(),
                assignmentid).getGroupData();
        // Get current time while adjusting for grace period
        long graceperiod = assignment.getGracePeriod() * 60000;
        Date now = new Date(System.currentTimeMillis() - graceperiod);
        if (now.after(assignment.getDueDate())
            && (!assignment.getAllowLate() || now.after(assignment
                .getLateDeadline()))
            && (group.getExtension() == null || now.after(group.getExtension()))) {
          result
              .addError("Submissions for this assignment are no longer being accepted");
        } else {
          info =
              upload.parseRequest(request, 1024, AccessController.maxFileSize,
                  FileUtil.TEMP_DIR);
          Iterator i = info.iterator();
          answers = new Vector();
          while (i.hasNext()) {
            FileItem item = (FileItem) i.next();
            String field = item.getFieldName();
            if (item.isFormField()) {
              if (field.startsWith(AccessController.P_SUBPROBNAME)) {
                long subID =
                    Long
                        .parseLong(field.split(AccessController.P_SUBPROBNAME)[1]);
                AnswerData answerData =
                    new AnswerData(-1, -1, subID, item.getString());
                answers.add(answerData);
              }
            }
          }
          result.addError(transactions.submitSurvey(p, assignmentid, answers));
        }
      }
    } catch (FileUploadException e) {
      if (e.getMessage().equalsIgnoreCase(FileUtil.SIZE_VIOLATION))
        result
            .addError("A submitted file violates the CMS maximum file size of "
                + AccessController.maxFileSize + " bytes");
      else if (e.getMessage().startsWith("match fail")) {
        String err = e.getMessage();
        String fileName = err.substring(err.indexOf(":") + 1);
        result.addError("File '" + fileName
            + "' failed to match an accepted type");
      } else if (e.getMessage().startsWith("size violation")) {
        String err = e.getMessage();
        String fileName = err.substring(err.indexOf(":") + 1);
        result.addError("File '" + fileName
            + "' violated the maximum size limitation");
      } else {
        result.addError(e.getLocalizedMessage());
      }
    } catch (Exception e) {
      result.addError("Error accessing database; files could not be submitted");
    }
    Profiler.exitMethod("TransactionHandler.submitFiles", "");
    return result;
  }

  /**
   * Output a CSV file with assignment names, max scores and weights for the
   * given course
   * 
   * @param p
   * @param courseID
   * @param out
   * @return TransactionResult
   */
  public TransactionResult exportGradingRubric(Principal p, Course course,
      OutputStream out) {
    TransactionResult result = new TransactionResult();
    try {
      CSVPrinter printer = new CSVPrinter(out);
      Collection asgns = database.assignmentHome().findByCourseID(courseID);
      List data = new ArrayList(); // a String[3] in each slot; temporary asgn
                                    // info storage
      Iterator i = asgns.iterator();
      while (i.hasNext()) {
        AssignmentLocal asgn = (AssignmentLocal) i.next();
        String[] info = new String[3];
        info[0] = asgn.getName();
        info[1] = String.valueOf(asgn.getMaxScore());
        info[2] = String.valueOf(asgn.getWeight());
        data.add(info);
      }
      String[] line = new String[1 + asgns.size()];
      // format data in columns instead of rows for easy Excel-ing
      line[0] = "Assignment Name";
      for (int j = 0; j < asgns.size(); j++)
        line[j + 1] = ((String[]) data.get(j))[0];
      printer.println(line);
      line[0] = "Max Score";
      for (int j = 0; j < asgns.size(); j++)
        line[j + 1] = ((String[]) data.get(j))[1];
      printer.println(line);
      line[0] = "Weight";
      for (int j = 0; j < asgns.size(); j++)
        line[j + 1] = ((String[]) data.get(j))[2];
      printer.println(line);
    } catch (Exception x) {
      x.printStackTrace();
      result.addError(x.getMessage());
    }
    return result;
  }

  /**
   * Output a CSV file containing all students in the class, with any grades
   * they may have for the given assignment, to the OutputStream. If the given
   * assignment has the property that staff may only grade groups they've been
   * explicitly assigned to, and p is not admin, only output grades for groups p
   * is assigned to. (Note other groups are output as well, but their grades
   * aren't.)
   * 
   * @param p
   * @param assignmentID
   * @param s
   * @return TransactionResult
   */
  public TransactionResult exportSingleAssignmentGradesTable(Principal p,
      Assignment assign, OutputStream s) {
    TransactionResult result = new TransactionResult();
    try {
      AssignmentLocal assignment =
          database.assignmentHome().findByAssignmentID(assignmentID);
      CourseLocal course =
          database.courseHome().findByAssignmentID(assignmentID);
      Collection subProbs =
          database.subProblemHome().findByAssignmentID(assignmentID);
      Collection students = null, grades = null;
      if (assignment.getAssignedGraders()
          && !p.isAdminPrivByCourseID(course.getCourseID())) {
        students =
            database.studentHome().findByAssignmentIDAssignedTo(assignmentID,
                p.getNetID());
        grades =
            database.gradeHome().findMostRecentByAssignmentIDGrader(
                assignmentID, p.getNetID());
      } else {
        // both students and grades are sorted by NetID; grades only has entries
        // where the grade exists
        students =
            database.studentHome().findByCourseIDSortByNetID(
                assignment.getCourseID());
        grades =
            database.gradeHome().findMostRecentByAssignmentID(assignmentID);
      }
      CSVPrinter out = new CSVPrinter(s);
      int numSubprobs = subProbs.size();
      String[] firstRow;
      long[] subProbLocs = null;
      Iterator i = subProbs.iterator();
      Iterator j = grades.iterator();
      int count = 0;
      if (numSubprobs == 0) {
        firstRow = new String[2];
        firstRow[0] = "NetID";
        firstRow[1] = "Grade";
      } else {
        firstRow = new String[2 + numSubprobs];
        subProbLocs = new long[1 + numSubprobs];
        firstRow[0] = "NetID";
        while (i.hasNext()) {
          SubProblemLocal subProb = (SubProblemLocal) i.next();
          firstRow[1 + (count++)] = subProb.getSubProblemName();
          subProbLocs[count] = subProb.getSubProblemID();
        }
        firstRow[firstRow.length - 1] = "Total";
      }
      out.println(firstRow);
      i = students.iterator();
      GradeLocal grade = null;
      // No subproblems
      if (subProbLocs == null) {
        String[] thisRow = new String[2];
        if (j.hasNext()) {
          grade = (GradeLocal) j.next();
        }
        while (i.hasNext()) {
          StudentLocal student = (StudentLocal) i.next();
          thisRow[0] = student.getNetID();
          if (grade != null && grade.getNetID().equals(thisRow[0])) {
            thisRow[1] = String.valueOf(grade.getGrade());
            if (j.hasNext()) grade = (GradeLocal) j.next();
          } else // either we're not yet at the netid of this grade, or we're
                  // at a netid past all existing grades or there are no grades
          thisRow[1] = "";
          out.println(thisRow);
        }
      } else // Subproblems
      {
        while (i.hasNext()) {
          StudentLocal student = (StudentLocal) i.next();
          String[] thisRow = new String[firstRow.length];
          thisRow[0] = student.getNetID();
          count = 1;
          Float totalScore = null;
          boolean nextRow = false;
          while (!nextRow && j.hasNext()) {
            if (grade == null) grade = (GradeLocal) j.next();
            if (grade.getNetID().equals(thisRow[0])) {
              if (grade.getSubProblemID() == 0) {
                totalScore = grade.getGrade();
                grade = null;
              } else if (grade.getSubProblemID() == subProbLocs[count]) {
                thisRow[count++] = String.valueOf(grade.getGrade());
                grade = null;
              } else {
                thisRow[count++] = "";
              }
            } else if (thisRow[0].compareTo(grade.getNetID()) < 0) {
              for (int k = count; k < thisRow.length; k++) {
                if (k == thisRow.length - 1 && totalScore != null) {
                  thisRow[k] = totalScore.toString();
                } else {
                  thisRow[k] = "";
                }
              }
              nextRow = true;
            } else {
              grade = null;
            }
          }
          if (totalScore != null) {
            for (int k = count; k < thisRow.length; k++) {
              if (k == thisRow.length - 1) {
                thisRow[k] = totalScore.toString();
              } else {
                thisRow[k] = "";
              }
            }
          }
          out.println(thisRow);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      result.addError(e.getMessage());
    }
    return result;
  }

  /**
   * Output a CSV file containing NetID and final grade columns for all students
   * in the given course to the OutputStream
   * 
   * @param courseID
   * @param s
   * @return
   */
  public TransactionResult exportFinalGradesTemplate(Course course,
      OutputStream s) {
    TransactionResult result = new TransactionResult();
    try {
      Collection students =
          database.studentHome().findByCourseIDSortByNetID(courseID);
      CSVPrinter out = new CSVPrinter(s);
      String[] line = null;
      // header line
      line =
          new String[] { CSVFileFormatsUtil.NETID,
              CSVFileFormatsUtil.FINAL_GRADE };
      out.println(line);
      // data lines
      Iterator i = students.iterator();
      while (i.hasNext()) {
        StudentLocal student = (StudentLocal) i.next();
        String grade = student.getFinalGrade();
        line = new String[] { student.getNetID(), grade == null ? "" : grade };
        out.println(line);
      }
    } catch (Exception x) {
      result.addError(x.getMessage());
    }
    return result;
  }

  /**
   * Output a CSV file containing all students in the class with all grades for
   * all assignments for the course, as well as their current total grades, to
   * the OutputStream
   * 
   * @param p
   * @param assignmentID
   * @param s
   * @return TransactionResult
   */
  public TransactionResult exportGradesTable(Principal p, Course course,
      OutputStream s) {
    TransactionResult result = new TransactionResult();
    try {
      Document doc = XMLBuilder.buildStudentsPage(p, courseID, false, false);
      // Element root = doc.createElement("root");
      // doc.appendChild(root);
      // ViewStudentsXMLBuilder.buildStudentsPage(courseID, doc);
      CSVPrinter out = new CSVPrinter(s);
      Element root = (Element) doc.getFirstChild();
      Element xCourse =
          XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE);
      NodeList assigns =
          (xCourse.getElementsByTagName(XMLBuilder.TAG_ASSIGNMENTS).item(0))
              .getChildNodes();
      int aCount = assigns.getLength();
      long[] assignIDs = new long[aCount];
      String[] firstRow = new String[5 + aCount];
      firstRow[0] = "Last Name";
      firstRow[1] = "First Name";
      firstRow[2] = "NetID";
      for (int i = 0; i < aCount; i++) {
        Element assign = (Element) assigns.item(i);
        firstRow[3 + i] = assign.getAttribute(XMLBuilder.A_NAMESHORT);
        assignIDs[i] =
            Long.valueOf(assign.getAttribute(XMLBuilder.A_ASSIGNID))
                .longValue();
      }
      firstRow[3 + aCount] = "Total Score";
      firstRow[4 + aCount] = "Final Grade";
      out.println(firstRow);
      NodeList students =
          (root.getElementsByTagName(XMLBuilder.TAG_STUDENTS).item(0))
              .getChildNodes();
      for (int j = 0; j < students.getLength(); j++) {
        String[] thisRow = new String[firstRow.length];
        Element student = (Element) students.item(j);
        if (!student.getAttribute(XMLBuilder.A_ENROLLED).equals(
            StudentBean.ENROLLED)) {
          continue;
        }
        thisRow[0] = student.getAttribute(XMLBuilder.A_LASTNAME);
        thisRow[1] = student.getAttribute(XMLBuilder.A_FIRSTNAME);
        thisRow[2] = student.getNodeName();
        for (int k = 0; k < aCount; k++) {
          Element grade =
              (Element) student.getElementsByTagName("id" + assignIDs[k]).item(
                  0);
          if (!grade.hasAttribute(XMLBuilder.A_SCORE))
            thisRow[3 + k] = "";
          else thisRow[3 + k] = grade.getAttribute(XMLBuilder.A_SCORE);
        }
        thisRow[3 + aCount] = student.getAttribute(XMLBuilder.A_TOTALSCORE);
        thisRow[4 + aCount] = student.getAttribute(XMLBuilder.A_FINALGRADE);
        out.println(thisRow);
      }
    } catch (Exception e) {
      result.addError(e.getMessage());
    }
    return result;
  }

  /**
   * Output a CSV file containing all students in the class with basic
   * information for each student including CUIDs and final grades to the
   * OutputStream
   * 
   * @param p
   * @param assignmentID
   * @param s
   * @return TransactionResult
   */
  public TransactionResult exportStudentInfoFinalGrades(Course course,
      OutputStream s) {
    TransactionResult result = new TransactionResult();
    try {
      // get lists of Users and Students, both sorted by NetID
      Iterator users = database.userHome().findByCourseID(courseID).iterator();
      Iterator students =
          database.studentHome().findByCourseIDSortByNetID(courseID).iterator();
      CSVPrinter out = new CSVPrinter(s);
      HashMap netIDs = new HashMap();
      Vector output = new Vector();
      final int numCols =
          CSVFileFormatsUtil
              .getNumColumns(CSVFileFormatsUtil.FINALGRADES_FORMAT), lastnameCol =
          CSVFileFormatsUtil.getColumnNumber(
              CSVFileFormatsUtil.FINALGRADES_FORMAT,
              CSVFileFormatsUtil.LASTNAME), firstnameCol =
          CSVFileFormatsUtil.getColumnNumber(
              CSVFileFormatsUtil.FINALGRADES_FORMAT,
              CSVFileFormatsUtil.FIRSTNAME), netidCol =
          CSVFileFormatsUtil.getColumnNumber(
              CSVFileFormatsUtil.FINALGRADES_FORMAT, CSVFileFormatsUtil.NETID), cuidCol =
          CSVFileFormatsUtil.getColumnNumber(
              CSVFileFormatsUtil.FINALGRADES_FORMAT, CSVFileFormatsUtil.CUID), collegeCol =
          CSVFileFormatsUtil
              .getColumnNumber(CSVFileFormatsUtil.FINALGRADES_FORMAT,
                  CSVFileFormatsUtil.COLLEGE), deptCol =
          CSVFileFormatsUtil.getColumnNumber(
              CSVFileFormatsUtil.FINALGRADES_FORMAT,
              CSVFileFormatsUtil.DEPARTMENT), courseNumCol =
          CSVFileFormatsUtil.getColumnNumber(
              CSVFileFormatsUtil.FINALGRADES_FORMAT,
              CSVFileFormatsUtil.COURSE_NUM), courseCodeCol =
          CSVFileFormatsUtil.getColumnNumber(
              CSVFileFormatsUtil.FINALGRADES_FORMAT,
              CSVFileFormatsUtil.COURSE_CODE), lecCol =
          CSVFileFormatsUtil
              .getColumnNumber(CSVFileFormatsUtil.FINALGRADES_FORMAT,
                  CSVFileFormatsUtil.LECTURE), secCol =
          CSVFileFormatsUtil
              .getColumnNumber(CSVFileFormatsUtil.FINALGRADES_FORMAT,
                  CSVFileFormatsUtil.SECTION), labCol =
          CSVFileFormatsUtil.getColumnNumber(
              CSVFileFormatsUtil.FINALGRADES_FORMAT, CSVFileFormatsUtil.LAB), creditsCol =
          CSVFileFormatsUtil
              .getColumnNumber(CSVFileFormatsUtil.FINALGRADES_FORMAT,
                  CSVFileFormatsUtil.CREDITS), gradeOptCol =
          CSVFileFormatsUtil.getColumnNumber(
              CSVFileFormatsUtil.FINALGRADES_FORMAT,
              CSVFileFormatsUtil.GRADE_OPTION), finalGradeCol =
          CSVFileFormatsUtil.getColumnNumber(
              CSVFileFormatsUtil.FINALGRADES_FORMAT,
              CSVFileFormatsUtil.FINAL_GRADE);
      CourseLocal course =
          database.courseHome().findByPrimaryKey(new CoursePK(courseID));
      String[] firstRow = CSVFileFormatsUtil.FINALGRADES_FORMAT;
      output.add(firstRow); // file header row
      // add the data that comes from the user table
      while (users.hasNext()) {
        UserLocal user = (UserLocal) users.next();
        String[] mydata = new String[numCols];
        output.add(mydata);
        netIDs.put(user.getNetID(), mydata);
        if (courseCodeCol != -1) mydata[courseCodeCol] = course.getCode();
        if (lastnameCol != -1) mydata[lastnameCol] = user.getLastName();
        if (firstnameCol != -1) mydata[firstnameCol] = user.getFirstName();
        if (netidCol != -1) mydata[netidCol] = user.getNetID();
        if (cuidCol != -1)
          mydata[cuidCol] = user.getCUID() == null ? "" : user.getCUID();
        if (collegeCol != -1) mydata[collegeCol] = user.getCollege();
      }
      // add the data that comes from the student table
      while (students.hasNext()) {
        StudentLocal student = (StudentLocal) students.next();
        String[] mydata = (String[]) netIDs.get(student.getNetID());
        if (mydata != null) {
          if (deptCol != -1)
            mydata[deptCol] =
                student.getDepartment() == null ? "" : student.getDepartment();
          if (courseNumCol != -1)
            mydata[courseNumCol] =
                student.getCourseNum() == null ? "" : student.getCourseNum();
          if (lecCol != -1)
            mydata[lecCol] =
                student.getLecture() == null ? "" : student.getLecture();
          if (labCol != -1)
            mydata[labCol] = student.getLab() == null ? "" : student.getLab();
          if (secCol != -1)
            mydata[secCol] =
                student.getSection() == null ? "" : student.getSection();
          if (creditsCol != -1)
            mydata[creditsCol] =
                student.getCredits() == null ? "" : student.getCredits();
          if (gradeOptCol != -1)
            mydata[gradeOptCol] =
                student.getGradeOption() == null ? "" : student
                    .getGradeOption();
          if (finalGradeCol != -1)
            mydata[finalGradeCol] =
                student.getFinalGrade() == null ? "" : student.getFinalGrade();
        }
      }
      for (int i = 0; i < output.size(); i++) {
        String[] thisRow = (String[]) output.get(i);
        out.println(thisRow);
      }
    } catch (Exception e) {
      e.printStackTrace();
      result.addError("Unexpected error while trying to export final grades");
    }
    return result;
  }

  /**
   * Output the most recently submitted files for the given group to the given
   * ZipOutputStream
   * 
   * @param p
   * @param assignmentID
   * @param s
   * @return TransactionResult
   */
  public TransactionResult uploadGroupSubmission(Group group,
      ZipOutputStream out, Map submissionNames) {
    TransactionResult result = new TransactionResult();
    try {
      GroupLocal group = database.groupHome().findByGroupID(groupID);
      Collection members =
          database.groupMemberHome().findActiveByGroupID(groupID);
      String folder = "Submissions/";
      if (members.size() > 1) {
        folder += "group_of_";
      }
      Iterator i = members.iterator();
      Assignment assign = 0;
      while (i.hasNext()) {
        GroupMemberLocal member = (GroupMemberLocal) i.next();
        folder += member.getNetID() + (i.hasNext() ? "_" : "");
      }
      folder += "/";
      Iterator files =
          database.submittedFileHome().findByGroupID(groupID).iterator();
      if (!files.hasNext()) {
        // Add empty folder if there are no submitted files
        out.putNextEntry(new ZipEntry(folder));
        out.closeEntry();
      }
      byte[] buff = new byte[1024];
      while (files.hasNext()) {
        SubmittedFileLocal file = (SubmittedFileLocal) files.next();
        String filePath = file.getPath();
        /*
         * next line handles missing DB_SLASH at end of path in database, caused
         * by update on 2/28/06 -- Evan
         */
        if (!filePath.endsWith("" + FileUtil.SYS_SLASH))
          filePath += FileUtil.SYS_SLASH;
        filePath += file.appendFileType(String.valueOf(file.getSubmissionID()));
        FileInputStream in = new FileInputStream(filePath);
        String fileName =
            (String) submissionNames.get(new Long(file.getSubmissionID()));
        if (fileName == null)
          throw new RemoteException("Submission name map was incomplete.");
        fileName = file.appendFileType(fileName);
        out.putNextEntry(new ZipEntry(folder + fileName));
        int len, sum = 0;
        while ((len = in.read(buff)) > 0) {
          sum += len;
          out.write(buff, 0, len);
        }
        out.closeEntry();
        in.close();
      }
      // out.close();
    } catch (Exception e) {
      result.addError(e.getMessage());
    }
    return result;
  }

  /**
   * Output a zip file containing all the most recently submitted files for all
   * groups given in the groupIDs Collection to the OutputStream
   * 
   * @param groupIDs
   * @param s
   * @return TransactionResult
   */
  public TransactionResult uploadGroupSubmissions(Collection groupIDs,
      OutputStream s) {
    TransactionResult result = new TransactionResult();
    Profiler.enterMethod("TransactionHandler.uploadGroupSubmissions",
        "Uploading files for " + groupIDs.size() + " groups");
    try {
      ZipOutputStream out = new ZipOutputStream(s);
      Iterator groups = groupIDs.iterator();
      Map submissionNames = null;
      while (groups.hasNext()) {
        Long groupID = (Long) groups.next();
        if (submissionNames == null) {
          GroupLocal group =
              database.groupHome().findByGroupID(groupID.longValue());
          submissionNames =
              database.getSubmissionNameMap(group.getAssignmentID());
        }
        uploadGroupSubmission(groupID.longValue(), out, submissionNames);
      }
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
      result.addError(e.getMessage());
    }
    Profiler.exitMethod("TransactionHandler.uploadGroupSubmissions",
        "Uploading files for " + groupIDs.size() + " groups");
    return result;
  }
}
