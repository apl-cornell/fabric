CORNELL UNIVERSITY WEB BANNER
Overview and Instructions
For more information, 
see http://www.cornell.edu/identity/banners/
**************************************************

==================================================
Overview
==================================================

This banner been created to enable Cornell University units to build websites that conform to the Cornell University identity guidelines. The step-by-step instructions below and extensive inline documentation (HTML and CSS comments) are written so that webmasters with a basic understanding of HTML and CSS can quickly and easily use the banner in their websites.

Coding Standards and Tested Browsers

The banner is coded using valid, semantic XHTML 1.0 Transitional for structure and valid CSS for presentation. The banner has been tested for consistency in the following browsers:

    * Internet Explorer 5.0, 5.5, and 6 for Windows
    * Firefox 1
    * Netscape 7.1 (also covers Mozilla browsers)
    * Safari for Mac OS X
    * Internet Explorer 5 for Mac OS X and Mac OS 9
    * AOL 9 for Windows
    * AOL for Mac OS X

==================================================
Instructions
==================================================

These instructions assume a basic knowledge of HTML and CSS. If you are familiar with web standards and CSS-driven layout, you will probably find the banner intuitive to use. Otherwise, please be patient, and resist the urge to use layout tables, spacer graphics, or font tags. If you need help implementing this banner, please contact Lisa Cameron-Norfleet (hck1@cornell.edu).

--------------------------------------------------
Step 1: Choose a Banner Configuration
--------------------------------------------------

A number of factors will determine which banner is appropriate for your project. If your group has a unit signature, consider using a unit signature banner (75 pixel for two-line signatures, 88 pixel for three-line signatures).

--------------------------------------------------
Step 2: Download the Banner
--------------------------------------------------

Once you have determined which banner size and color to use, download and extract the banner for your platform. You may need to install Stuffit Expander (Macintosh) or WinZip (PC) to extract the archives.

--------------------------------------------------
Step 3: Get Familiar
--------------------------------------------------

Open the HTML and CSS files in a text editor and read through the comments in the code (CSS files are found in the styles folder). It will be helpful to start with the HTML file, especially if you are unfamiliar with using CSS for layout.

If you are using a visual editor such as Dreamweaver or Frontpage, it is important that you work with this banner in code view. If you are uncomfortable working in code view, please be aware that these editors render CSS differently from web browsers. Although the banner will look odd in the preview window of the editor, there is nothing wrong with it.

--------------------------------------------------
Step 4: Copy Files
--------------------------------------------------

Copy favicon.ico and the images and styles directories to the root of your web server. 

--------------------------------------------------
Step 5: Add the Banner Code to your Pages
--------------------------------------------------

If you have an existing website, add the HTML from banner.html to the top of each of your pages. If you do not have an existing website, rename banner.html to index.html and begin adding content.

This is a good time to go through the banner HTML and replace placeholders with actual values. For example, replace "Unit Name Goes Here" and the contents of the <title> tag with your unit's name.

-------------------------
Unit Signatures
-------------------------

If you are using a unit signature banner, you will need to replace images/layout/unit_signature.gif with your own unit signature. After renaming your unit signature file to unit_signature.gif and placing it in images/layout/, open styles/main.css, find #unit-signature-links a, and set the width value to:

    * 68 pixels less than the total width of your two-line unit signature image. For example, if your unit signature is 268 pixels wide, set the width value to 200px.
    
    * 80 pixels less than the total width of your three-line unit signature image. For example, if your unit signature is 380 pixels wide, set the width value to 300px.

You must also replace images/unit_signature_unstyled.gif with your own two-color unit signature. This is the image shown to browsers that cannot render the templates' stylesheet (e.g. Netscape Navigator 4). Save your two-color unit signature image as images/unit_signature_unstyled.gif.

-------------------------
Banner Options
-------------------------

If you are using a unit signature banner, make sure you choose a search interface that is appropriate to your needs and capabilities. If you choose to use the search form with the option to search either your unit website or all of cornell.edu, you must modify the form to work with your site's search engine.

Although every available search interface option is offered as a separate download, every unit signature banner includes the alternate search interface configurations in the form of HTML and CSS comments. If you change your mind about the search interface you should use, follow the instructions in banner.html and styles/main.css to enable the appropriate configuration.

The banner begins with a link that allows users with special accessibility needs to skip reptitive navigation elements and proceed directly to the main contents of the page. This link is hidden from general users with visual browsers that can render the banner's layout (although the link is hidden, it can still be accessed using the tab key). However, if you wish to display the "skip to content" link to all users, follow the instructions in styles/main.css (look for #skipnav near the end of the file).

The 75 and 88 pixel banners feature an image of Ezra Cornell in the background. To disable or change this image, follow the instructions in styles/main.css (look for #cu-identity near the top of the file). There are eight available background images.

