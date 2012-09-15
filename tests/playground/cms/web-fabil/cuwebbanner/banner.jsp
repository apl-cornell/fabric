
<style type="text/css">
@import url("cuwebbanner/styles/screen.css");
</style>

<!--
	This document provides the basis of a semantically structured web page 
	authored in XHTML 1.0 Transitional using established Cornell University
	naming conventions.
-->

<!-- The following div contains the Cornell University logo and search link -->
<div id="cu-identity">
	<div id="cu-logo">
		<a href="http://www.cornell.edu/"><img src="images/cu_logo_unstyled.gif" alt="Cornell University" width="263" height="76" border="0" /></a>
	</div>

	<!-- 
		The search-form div contains a form that allows the user to search 
		either pages or people within cornell.edu directly from the banner.
	-->
	<div id="search-form">
		<form action="http://www.cornell.edu/search/" method="get" enctype="application/x-www-form-urlencoded">
			<div id="search-input">
				<label for="search-form-query">SEARCH CORNELL:</label>
				<input type="text" id="search-form-query" name="q" value="" size="20" />
				<input type="submit" id="search-form-submit" name="submit" value="go" />
			</div>

			<div id="search-filters">
					<input type="radio" id="search-filters1" name="tab" value="" checked="checked" />
					<label for="search-filters1">Pages</label>
				
					<input type="radio" id="search-filters2" name="tab" value="people" />
					<label for="search-filters2">People</label>
					
					<a href="http://www.cornell.edu/search/">more options</a>
			</div>	
		</form>
	</div>
</div>

<!-- Page contents go here -->
