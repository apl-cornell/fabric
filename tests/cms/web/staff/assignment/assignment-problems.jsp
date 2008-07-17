<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*" %>
<% Document displayData= (Document)session.getAttribute(AccessController.A_DISPLAYDATA); 
   Element root= (Element)displayData.getChildNodes().item(0); 
   Element assignment= (Element) XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_ASSIGNMENT);
   NodeList subproblems= assignment.getElementsByTagName(XMLBuilder.TAG_SUBPROBLEM);
   NodeList hiddensubprobs= assignment.getElementsByTagName(XMLBuilder.TAG_HIDDENSUBPROB);
   NodeList newProbs = assignment.getElementsByTagName(XMLBuilder.TAG_NEWPROB); %>
<h2>
  Problems
  <span id="problemshead">
    <a href="#" onClick="hide('problems', '(hide)', '(show)'); return false;" class="hide">(hide)</a>
  </span>
</h2>
<div id="problems" class="showhide">
  <table id="probtable" class="assignment_table" cellpadding="0" cellspacing="0">
    <tr>
      <th>Problem Name</th>
      <th>Total Score</th>
      <th style="width: 10%">Remove</th>
    </tr>
<% for (int i= 0; i != subproblems.getLength(); i++) {
     Element subprob= (Element) subproblems.item(i);
     String subID= subprob.getAttribute(XMLBuilder.A_ID); %>
    <tr>
      <td style="text-align: center">
        <input size="30" name="<%= AccessController.P_SUBPROBNAME + subID %>" value="<%= subprob.getAttribute(XMLBuilder.A_NAME) %>">
      </td>
      <td style="text-align: center">
        <input size="3" name="<%= AccessController.P_SUBPROBSCORE + subID %>" value="<%= subprob.getAttribute(XMLBuilder.A_TOTALSCORE) %>">
      </td>
      <td style="text-align: center">
        <input type="checkbox" name="<%= AccessController.P_REMOVESUBPROB + subID %>">
      </td>
    </tr>
<% } %>
<% for (int i=0; i < newProbs.getLength(); i++) {
	Element newProb = (Element) newProbs.item(i); %>
	<tr id="sub<%= i %>">
		<td style="text-align: center">
			<input type="text" size="30" value="<%= newProb.getAttribute(XMLBuilder.A_SUBPROBNAME) %>" name="<%= AccessController.P_NEWSUBPROBNAME + i %>">
		</td>
		<td style="text-align: center">
			<input type="text" size="3" value="<%= newProb.getAttribute(XMLBuilder.A_SCORE) %>" name="<%= AccessController.P_NEWSUBPROBSCORE + i %>">
		</td>
		<td style="text-align: center; white-space: nowrap">
			<a href="#" onClick="remove('<%= "sub" + i %>'); return false;">(Remove row)</a>
		</td>
	</tr>
<% } %>
<script type="text/javascript">
	probindex = <%= newProbs.getLength() %>;
</script>
  </table>
  <div id="addprob">
    <div class="joke" style="float: right">Problem scores must sum to assignment Total Score</div>
    <a href="#" onClick="addProbRow(); return false;" class="button">(New Row)</a>
  </div>
<% if (hiddensubprobs.getLength() != 0) { %>
  <div class="replace">
    <span id="removedprobshead">
      <a href="#" onClick="show('removedprobs', 'Removed problems &raquo;', '&laquo; Removed problems'); return false;">Removed problems &raquo;</a>
    </span>
    <table class="replacebody" id="removedprobs" cellpadding="0" cellspacing="0">
      <tr>
        <th>Problem Name</th>
        <th>Total Score</th>
        <th>Restore</th>
      </tr>
  <% for (int i= 0; i != hiddensubprobs.getLength(); i++) { 
     Element prob= (Element) hiddensubprobs.item(i);
     String probID = prob.getAttribute(XMLBuilder.A_ID); %>
      <tr class="<%= i % 2 == 0 ? "row_even" : "row_odd" %>">
        <td><input type="hidden" name="<%= AccessController.P_HIDDENPROBNAME + probID %>" value="<%= prob.getAttribute(XMLBuilder.A_NAME) %>">
        	<%= prob.getAttribute(XMLBuilder.A_NAME) %></td>
        <td><input type="hidden" name="<%= AccessController.P_HIDDENPROBSCORE + probID %>" value="<%= prob.getAttribute(XMLBuilder.A_TOTALSCORE) %>">
        	<%= prob.getAttribute(XMLBuilder.A_TOTALSCORE) %></td>
        <td>
          <input type="checkbox" name="<%= AccessController.P_RESTORESUBPROB + probID %>"<%= prob.hasAttribute(XMLBuilder.A_RESTORED) ? " checked" : "" %>>
        </td>      
      </tr>
  <% } %>
    </table>
  </div>
<% } %>
</div>