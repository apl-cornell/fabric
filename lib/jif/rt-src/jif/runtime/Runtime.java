package jif.runtime;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import jif.lang.*;

/**
 * The runtime interface between Jif programs and the underlying system.
 */
public class Runtime {
    /**
     * The principal under whose authority the JVM is running.
     */
    private Principal dynp;

    private Runtime(Principal p) {
        this.dynp = p;
    }

    /**
     * Gets a <code>Runtime</code> object parameterized with the
     * principal <code>p</code>.
     */
    public static Runtime getRuntime(Principal p) throws SecurityException {
        //check if the current user can act for p
        Principal user = user(p);
        if (!PrincipalUtil.actsFor(user, p)) {
            throw new SecurityException("The current user does not act for "
                    + p.name() + ".");
        }
        return new Runtime(p);
    }

    /** Get the current user  */
    public static Principal user(Principal parameter) {
        String username = currentUser();
        return NativePrincipal.getInstance(username);
    }
    /** Get a native user  */
    public static Principal getUser(Principal parameter, String username) {
        return NativePrincipal.getInstance(username);
    }

    private Label defaultOutputLabel() {
        ConfPolicy cp = LabelUtil.singleton().readerPolicy(dynp, new LinkedList());
        return LabelUtil.singleton().toLabel(cp);
    }
    private Label defaultInputLabel() {
        IntegPolicy ip = LabelUtil.singleton().writerPolicy(dynp, new LinkedList());
        return LabelUtil.singleton().toLabel(ip);
    }

    /**
     * Opens a file output stream to write a file with the specific <code>name</code>.
     *
     * @param name     the file name
     * @param append   if true, then bytes will be written to the end of the file
     *                 rather than the beginning
     * @param L        the label parameter of the resulting <code>FileOutputStream</code>
     *
     * @exception  FileNotFoundException
     *      if the file exists but is a directory rather than a regular file,
     *      does not exist but cannot be created, or cannot be opened for any
     *      other reason.
     *
     * @exception  SecurityException
     *      if <code>l</code> is unable to relabel to the Jif label derived from
     *      the ACL of the file.
     */
    public FileOutputStream openFileWrite(String name, boolean append, Label L)
            throws IOException, SecurityException {
        File f = new File(name);
        boolean existed = f.exists();

        if (existed) {
            Label acLabel = FileSystem.labelOf(name);
            if (!LabelUtil.singleton().relabelsTo(L, acLabel)) {
                throw new SecurityException("The file " + name
                        + "doesn't have sufficient access restrictions.");
            }
        }

        FileOutputStream fos = new FileOutputStream(name, append);

        if (!existed) {
            fos.flush();
//            FileSystem.setPolicy(name, (PrivacyPolicy)L.policy());
        }
        return fos;
    }

    /** Opens a file input stream for reading from the file with the specific
     *  <code>name</code>.
     *
     *  @param name     the file name
     *  @param L        the the label parameter of the resulting <code>FileInputStream</code>
     *
     *  @exception  SecurityException
     *      if <code>l</code> is less restrictive than the Jif label derived from
     *      the ACL of the file.
     */
    public FileInputStream openFileRead(String name, Label L)
            throws FileNotFoundException, SecurityException {
        Label acLabel = FileSystem.labelOf(name);
        
        if (LabelUtil.singleton().relabelsTo(acLabel, L)) return new FileInputStream(name);
        
        throw new SecurityException("The file has label " + LabelUtil.singleton().stringValue(acLabel) + 
                                    ", which is more restrictive than " +
                                    L.toString());
    }

    /**
     * Gets the standard error output.
     * The output channel is parameterized by <code>l</code>.
     */
    public PrintStream stderr(Label l) {
        if (LabelUtil.singleton().relabelsTo(l, defaultOutputLabel())) return System.err;

        throw new SecurityException("The standard error output is not "
                + "sufficiently secure.");
    }

    /**
     * Gets the standard output.
     * This output channel is parameterized by <code>l</code>.
     */
    public PrintStream stdout(Label l) {
        if (LabelUtil.singleton().relabelsTo(l, defaultOutputLabel())) return System.out;
        throw new SecurityException("The standard output is not "
                + "sufficiently secure.");
    }

    /**
     * Gets the standard input.
     * This input channel is parameterized by <code>l</code>.
     */
    public InputStream stdin(Label l) {
        if (LabelUtil.singleton().relabelsTo(defaultInputLabel(), l)) return System.in;
        
        throw new SecurityException("The standard output is not "
                + "sufficiently secure.");
    }

    /**
     * Get the standard output parameterized by the default label, which
     * has only one reader: the principal of this <code>Runtime</code> object.
     */
    public PrintStream out() {
        return System.out;
    }

    /**
     * Get the standard input parameterized by the default label, which
     * has only one reader: the principal of this <code>Runtime</code> object.
     */
    public InputStream in() {
        return System.in;
    }

    /**
     * Get the standard error output parameterized by the default label, which
     * has only one reader: the principal of this <code>Runtime</code> object.
     */
    public PrintStream err() {
        return System.err;
    }

    public static String currentUser() {
        if (_nativeOK) return currentUserImpl();
        return null;
    }
    private static native String currentUserImpl();

    public static int currentYear(Principal dummy) {
        return new GregorianCalendar().get(Calendar.YEAR);
    }
    public static int currentMonth(Principal dummy) {
        return new GregorianCalendar().get(Calendar.MONTH) - Calendar.JANUARY + 1;
    }
    public static int currentDayOfMonth(Principal dummy) {
        return new GregorianCalendar().get(Calendar.DAY_OF_MONTH);
    }
    public static int currentHour(Principal dummy) {
        return new GregorianCalendar().get(Calendar.HOUR_OF_DAY);
    }
    public static int currentMinute(Principal dummy) {
        return new GregorianCalendar().get(Calendar.MINUTE);
    }
    public static void sleep(Principal dummy, int s) {
        try {
            // add some noise...
            double noise = 0.15; 
            double multiplier = 1 + ((2 * Math.random() - 1) * noise); // = 1 plus or minus noise
            long ms = (long)((long)s * 1000 * multiplier);
            if (!Thread.interrupted()) {
                Thread.sleep(ms);
            }
        }
        catch (InterruptedException e) {
            // ignore the interrupted exception
        }
    }
    
    
    public static Object[] arrayDeepClone(Object[] a) {
        if (a == null) return null;
        Object[] c = (Object[])a.clone();
        for (int i = 0; i < a.length; i++) {
            Object o = a[i];
            if (o != null && o.getClass().isArray()) {
                if (o.getClass().getComponentType().isPrimitive()) {
                    // o is of type e.g., int[]. Need to clone it.
                    int length = Array.getLength(o);
                    o = Array.newInstance(o.getClass().getComponentType(), length);
                    System.arraycopy(a[i], 0, o, 0, length);
                }
                else {
                    // o i of type C[]
                    o = arrayDeepClone((Object[])o);
                }
            }
            c[i] = o;
        }
        return c;
    }
       
    private static boolean _nativeOK = true;
    static {
        try {
            System.loadLibrary("jifrt");
        }
        catch (UnsatisfiedLinkError ule) {
            // fail, but continue
            _nativeOK = false;
            // System.err.println(ule.getLocalizedMessage());
        }
    }   
}