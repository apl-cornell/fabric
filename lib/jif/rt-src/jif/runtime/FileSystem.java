package jif.runtime;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import jif.lang.Label;
import jif.lang.LabelUtil;

/** This class represents the file system, through which you can query
 *  and set the security labels of files.
 */
public class FileSystem
{
    /** Get the security label of <code>file</code>. */
    public static Label labelOf(String file) throws FileNotFoundException {
        File f = new File(file);
        if (!f.exists()) throw new FileNotFoundException("File " + file + " not found");
        String[] readers = readers(file);
//        String[] writers = writers(file);
        String owner = owner(file);
        
        List readerList = new LinkedList();
        for (int i = 0; readers != null && i < readers.length; i++) {
            readerList.add(NativePrincipal.getInstance(readers[i]));
        }
//        List writerList = new LinkedList();            
//        for (int i = 0; i < writers.length; i++) {
//            writerList.add(new NativePrincipal(writers[i]));
//        }
        jif.lang.Principal op = NativePrincipal.getInstance(owner);
        return LabelUtil.singleton().readerPolicyLabel(op, readerList);
    }
    
    /** Set the access(read) policy of <code>file</code>.  */
    /*
     public static void setPolicy(String file, ReaderPolicy p) 
    throws IOException
    {
        if (!(p.owner() instanceof NativePrincipal)) {
            throw new IOException("File owner is not a NativePrincipal");
        }
        NativePrincipal owner = (NativePrincipal)p.owner();
        String[] readers = new String[p.readers().size()];
        int i = 0;
        for (Iterator iter = p.readers().iterator(); iter.hasNext(); )
            readers[i++] = ((jif.lang.Principal) iter.next()).name();
        
        String os = System.getProperty("os.name");
        //in unix systems, files can belong to only one group. so reader
        //set has to be adjusted. 
        if (os.equals("Linux") || os.equals("SunOS") || os.equals("Mac OS X")) {
            Set groups = groups(owner);
            ReaderPolicy p1 = null;
            for (Iterator iter = groups.iterator(); iter.hasNext(); ) {
                jif.lang.Principal reader = (jif.lang.Principal) iter.next();
                List readerSet = new LinkedList();
                readerSet.add(reader);
                p1 = new ReaderPolicy(owner, readerSet);
                if (p.relabelsTo(p1) && p1.relabelsTo(p)) {
                    readers = new String[1];
                    readers[0] = reader.name();
                    setPolicy(file, owner.name(), readers);
                    return;
                }
            }
            String msg = "no group corresponds to the reader set: {";
            for (i = 0; i < readers.length; i++) {
                msg += readers[i];
                if (i < readers.length - 1 ) msg += ", ";
                else msg += "}.";
            }
            
            throw new IOException(msg);
        }
        
        setPolicy(file, owner.name(), readers);
    }
    
    /** Returns the set of groups in which <code>p</code> belongs.
     */
    public static Set groups(NativePrincipal p) {
        Set grps = new LinkedHashSet();
        Set supers = new LinkedHashSet(p.superiors());
        
        while (!supers.isEmpty()) {
            jif.lang.Principal one = (jif.lang.Principal) supers.iterator().next();
            if (one instanceof NativePrincipal) {
                supers.addAll(((NativePrincipal)one).superiors());
                grps.add(one);
            }
            supers.remove(one);
        }
        
        return grps;
    }
    
    private static native void setPolicy(String file, String owner,
            String[] readers);							      
    
    private static native String[] readers(String file);
//    private static native String[] writers(String file);
    
    private static native String owner(String file);
    
    static {
        try {
            System.loadLibrary("jifrt");
        }
        catch (UnsatisfiedLinkError ule) {
            // fail, but continue
            // System.err.println(ule.getLocalizedMessage());
        }
    }
}
