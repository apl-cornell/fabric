package codebases.frontend;

import java.io.*;
import java.util.Date;

import polyglot.frontend.FileSource;

public class UTF8FileSource extends FileSource {
    public UTF8FileSource(File f, boolean userSpecified) throws IOException {
	super(f, userSpecified);
    }
    
    public UTF8FileSource(String path, String name, Date lastModified, boolean userSpecified) throws IOException {
        super(path, name, lastModified, userSpecified);
    }
    
    @Override
    protected Reader createReader(InputStream str) {
      try {
	return new polyglot.lex.EscapedUnicodeReader(
	    new InputStreamReader(str, "UTF-8"));
      } catch (UnsupportedEncodingException e) {
	System.err.println("Bad Java implementation: UTF-8 encoding must be supported");
	return null;
      }
    }
}
