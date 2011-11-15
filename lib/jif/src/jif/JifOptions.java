package jif;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import polyglot.main.Options;
import polyglot.main.Report;
import polyglot.main.UsageError;

/**
 * This object encapsulates various polyglot options.
 */
public class JifOptions extends Options {
    /*
     * Fields for storing values for options.
     */
    /**
     * Should the checking for the robustness condition be disabled?
     */
    public boolean nonRobustness;

    
     /**
      * Use a single Solver to infer labels globally, or solve on a class
      * by class basis.
      */
     public boolean solveGlobally;

     /**
      * Provide more detailed explanation of solver error messages?
      */
     public boolean explainErrors;

     /**
      * The classpath for the Jif signatures of java.lang objects.
      */
     public String sigcp = null;

     /**
      * Additional classpath entries for Jif signatures.
      */
     public List addSigcp = new ArrayList();

    /**
     * Constructor
     */
    public JifOptions(ExtensionInfo extension) {
        super(extension);
        setDefaultValues();
    }

    /**
     * Set default values for options
     */
    public void setDefaultValues() {
        super.setDefaultValues();
        solveGlobally = false;
        explainErrors = false;
        nonRobustness = false;
    }

    /**
     * Parse a command
     * @return the next index to process. That is, if calling this method
     *         processes two commands, then the return value should be index+2
     */
    protected int parseCommand(String args[], int index, Set source) throws UsageError {
        if (args[index].equals("-globalsolve")) {
            index++;
            System.err.println("Will use a single solver to infer labels");
            solveGlobally = true;
        }
        else if (args[index].equals("-explain") || args[index].equals("-e")) {
            index++;
            explainErrors = true;
        }
        else if (args[index].equals("-nonrobust")) {
            index++;
            nonRobustness = true;
        }
        else if (args[index].equals("-robust")) {
            index++;
            nonRobustness = false;
        }
        else if (args[index].equals("-sigcp")) {
            index++;
            this.sigcp = args[index++];
        }
        else if (args[index].equals("-addsigcp")) {
            index++;
            this.addSigcp.add(args[index++]);
        }
        else if (args[index].equals("-debug")) {
            index++;
            int level=0;
            try {
                level = Integer.parseInt(args[index]);
            } 
            catch (NumberFormatException e) {
            }
            Report.addTopic("debug", level);
            index++;
        }
        else {
            int i = super.parseCommand(args, index, source);
            return i;
        }
        return index;
    }

    /**
     * Print usage information
     */
    public void usage(PrintStream out) {
        super.usage(out);
        usageForFlag(out, "-e -explain", "provide more detailed " +
                                         "explanations of failed label checking.");
        usageForFlag(out, "-robust", "enable checking of robustness conditions for downgrading (use -nonrobust to disable).");
        usageForFlag(out, "-debug <n>", "set debug level to n. Prints more information about labels.");
        usageForFlag(out, "-stop_constraint <n>", "halt when the nth constraint is added");
        usageForFlag(out, "-globalsolve", "infer label variables globally (default: per class)");
        usageForFlag(out, "-sigcp <path>", "path for Jif signatures (e.g. for java.lang.Object)");
        usageForFlag(out, "-addsigcp <path>", "additional path for Jif signatures; prepended to sigcp");
    }

    public String constructSignatureClasspath() {        
        // use the signature classpath if it exists for compiling Jif classes
        String scp = "";
        for (Iterator iter = addSigcp.iterator(); iter.hasNext(); ) {
            scp += ((String)iter.next());
            if (iter.hasNext()) {
                scp += File.pathSeparator;            
            }
        }
        if (sigcp != null) {
            scp += File.pathSeparator + sigcp;
        }
        return scp;
    }

    public String constructJifClasspath() {
        return constructSignatureClasspath() +  
                File.pathSeparator + constructFullClasspath();
    }
    
    public String constructOutputExtClasspath() {
        return constructFullClasspath();
    }

    public String constructPostCompilerClasspath() {
        String cp = super.constructPostCompilerClasspath() + File.pathSeparator
                + constructFullClasspath();
        return cp;
    }

}
