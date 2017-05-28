package cms.model;

import java.util.Date;
import fabric.util.ArrayList;
import fabric.util.Collection;

/**
 * A LogSearchParams describes the parameters entered by the user
 * for a log search. Permission checking for log functionality is
 * assumed to be done by whoever is creating a LogSearchParams.
 * Not all fields need be non-null; null fields represent a lack of
 * preference in a certain area.
 */
public class LogSearchParams
{
	private String actingNetID;
	private String receivingNetID;			//a SINGLE NetID
	private String simulatedNetID;
	private Long receivingGroupID;
	private String actingIPAddress;
	private Date startTime;				//date/time range is INCLUSIVE
	private Date endTime;
	private long logTypes;						//an |ing of codes in LogBean
	private Collection logNames;
	private Long courseID;
	private Long assignmentID;
	
	public LogSearchParams()
	{
		this.actingNetID = null;
		this.receivingNetID = null;
		this.actingIPAddress = null;
		this.simulatedNetID = null;
		this.receivingGroupID = null;
		this.logTypes = 0;
		this.logNames = new ArrayList();
		this.startTime = null;
		this.endTime = null;
		this.courseID = null;
		this.assignmentID = null;
	}
	
	//null should be given for an argument when the search doesn't involve that parameter
	public LogSearchParams(long logTypes, Collection logNames, String actingNetID, String ip, String receivingNetID,
									String simulatedNetID, Long receivingGroupID, Long courseID, Long assignID, Date start, Date end)
	{
		this.actingNetID = actingNetID;
		this.logNames = new ArrayList(logNames);
		this.receivingNetID = receivingNetID;
		this.simulatedNetID = simulatedNetID;
		this.actingIPAddress = ip;
		this.receivingGroupID = receivingGroupID;
		this.logTypes = logTypes;
		this.startTime = start;
		this.endTime = end;
		this.courseID = courseID;
		this.assignmentID = assignID;
	}
	
	public String getActingNetID()
	{
		return actingNetID;
	}
	
	public void setActingNetID(String netid)
	{
	    if (netid != null && !netid.trim().equals("")) {
	        this.actingNetID = netid.trim();
	    }
	}
	
	public String getReceivingNetID()
	{
		return receivingNetID;
	}
	
	public void setReceivingNetID(String netid)
	{
	    if (netid != null && !netid.trim().equals("")) {
	        this.receivingNetID = netid;	
	    }
	}
	
	public Long getReceivingGroupID()
	{
		return receivingGroupID;
	}
	
    /**
     * @return Returns the simulatedNetID.
     */
    public String getSimulatedNetID() {
        return simulatedNetID;
    }
    
    /**
     * @param simulatedNetID The simulatedNetID to set.
     */
    public void setSimulatedNetID(String simulatedNetID) {
	    if (simulatedNetID != null && !simulatedNetID.trim().equals("")) {
	        this.simulatedNetID = simulatedNetID;
	    }
	}
    
	public void setReceivingGroupID(Long group)
	{
		this.receivingGroupID = group;
	}
	
	public String getActingIPAddress()
	{
		return actingIPAddress;
	}
	
	public void setActingIPAddress(String ip)
	{
	    if (ip != null && !ip.trim().equals("")) {
	        this.actingIPAddress = ip;
	    }
	}
	
	public Date getStartTime()
	{
		return startTime;
	}
	
	public void setStartTime(Date start)
	{
		this.startTime = start;
	}
	
	public Date getEndTime()
	{
		return endTime;
	}
	
	public void setEndTime(Date end)
	{
		this.endTime = end;
	}
	
	public long getLogTypes()
	{
		return logTypes;
	}
	
	public void setLogTypes(long types)
	{
		this.logTypes = types;
	}
	
	public void addLogType(long type) {
	    this.logTypes = this.logTypes | type;
	}
	
	public Collection getLogNames() {
	    return logNames;
	}
	
	public void addLogName(String logName) {
	    logNames.add(logName);
	}
	
	public Long getCourseID()
	{
		return courseID;
	}
	
	public void setCourseID(Long course)
	{
		this.courseID = course;
	}
	
	public Long getAssignmentID()
	{
		return assignmentID;
	}
	
	public void setAssignmentID(Long asgn)
	{
		this.assignmentID = asgn;
	}
}