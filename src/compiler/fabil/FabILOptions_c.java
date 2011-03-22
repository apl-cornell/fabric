package fabil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import polyglot.frontend.ExtensionInfo;
import polyglot.main.UsageError;
import polyglot.main.Main.TerminationException;
import polyglot.util.InternalCompilerError;

/**
 * This is the same as the JL options, except by default, we always generate
 * fully qualified class names. This is here because the logic for qualifying
 * class names seems a bit wonky.
 */
public class FabILOptions_c extends polyglot.main.Options implements FabILOptions {
  /**
   * Whether we're running in signature mode.
   */
  public boolean signatureMode;
  
  /**
   * Whether to dump class dependencies for each class.
   */
  public boolean dumpDependencies;

  /**
   * The classpath for the FabIL signatures of Java objects.
   */
  public String sigcp;

  /**
   * Additional classpath entries for FabIL signatures.
   */
  public List<String> addSigcp;
  
  /** Whether to perform optimizations. */
  public int optLevel;

  /**
   * Whether to create a Java skeleton for each class.
   */
  public boolean createJavaSkel;
 
  /**
   * Name of worker for compiling source from Fabric.
   */  
  protected String workerName;

  /**
   * Whether to run a Fabric worker so that Fabric-hosted source code can be
   * compiled.
   */  
  protected boolean runWorker;
  
  /**
   * The name of the store for writing generated codebase and classes.
   */  
  protected String destinationStore;
  
  /**
   * Codebase path.
   */
  protected List<URI> codebasePath;

  public FabILOptions_c(ExtensionInfo extension) {
    super(extension);
    this.sigcp = null;
    this.addSigcp = new ArrayList<String>();
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.main.Options#setDefaultValues()
   */
  @Override
  public void setDefaultValues() {
    super.setDefaultValues();
    this.fully_qualified_names = true;
    this.signatureMode = false;
    this.dumpDependencies = false;
    this.optLevel = 0;
    this.createJavaSkel = false;
    try {
      this.workerName = java.net.InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      this.workerName = "localhost";
    }
    this.codebasePath = new LinkedList<URI>();
    this.runWorker = false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.main.Options#parseCommand(java.lang.String[], int,
   *      java.util.Set)
   */
  @SuppressWarnings("rawtypes")
  @Override
  public int parseCommand(String[] args, int index, Set source) throws UsageError,
      TerminationException {
    if (args[index].equals("-sig")) {
      index++;
      signatureMode = true;
    } else if (args[index].equals("-dumpdeps")) {
      index++;
      dumpDependencies = true;
    } else if (args[index].equals("-sigcp")) {
      index++;
      this.sigcp = args[index++];
    } else if (args[index].equals("-addsigcp")) {
      index++;
      this.addSigcp.add(args[index++]);
    } else if (args[index].startsWith("-O")) {
      if (args[index].length() == 2) {
        this.optLevel = Integer.MAX_VALUE;
      } else {
        try {
          this.optLevel = Integer.parseInt(args[index].substring(2));
        } catch (NumberFormatException e) {}
      }
      index++;
    }
    else if (args[index].equals("-useworker")) {
      index++;
      this.runWorker = true;
      return index;
    }
    else if (args[index].equals("-worker")) {
      index++;
      this.runWorker = true;
      this.workerName = args[index++];
      return index;
    }
    else if (args[index].equals("-deststore")) {
      index++;
      this.runWorker = true;
      this.destinationStore = args[index++];
      return index;
    }
    else if (args[index].equals("-addCodebase")) {
      index++;
      this.runWorker = true;
      String cb = args[index++];
      URI uri = URI.create(cb);
      if(uri.isAbsolute())
        this.codebasePath.add(uri);
      else {
        try {
          BufferedReader lr = new BufferedReader(new FileReader(cb));
          this.codebasePath.add(URI.create(lr.readLine()));
        } catch (FileNotFoundException e) {
          throw new InternalCompilerError(e);
        } catch (IOException e) {
          throw new InternalCompilerError(e);
        }
      }
      return index;

    } else if (args[index].equals("-bootstrap-skel")) {
      index++;
      createJavaSkel = true;
      serialize_type_info = false;      

    } else {
      return super.parseCommand(args, index, source);
    }

    return index;
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.main.Options#usage(java.io.PrintStream)
   */
  @Override
  public void usage(PrintStream out) {
    super.usage(out);
    usageForFlag(out, "-sig", "compile sources to signatures");
    usageForFlag(out, "-sigcp <path>",
        "path for FabIL signatures (e.g. for fabric.lang.Object)");
    usageForFlag(out, "-addsigcp <path>",
        "additional path for FabIL signatures; prefixed to sigcp");
    usageForFlag(out, "-dumpdeps", "output dependencies for each class");
    usageForFlag(out, "-bootstrap-skel", "generate java bootstrap skeletons for each class");
    usageForFlag(out, "-O", "turn optimizations on");
  }

  /* (non-Javadoc)
   * @see fabil.FabILOptions#constructSignatureClasspath()
   */
  public String constructSignatureClasspath() {
    // Use the signature classpath if it exists for compiling Fabric classes.
    String scp = "";
    for (String item : addSigcp)
      scp += File.pathSeparator + item;

    if (sigcp != null) scp += File.pathSeparator + sigcp;

    return scp;
  }

  /* (non-Javadoc)
   * @see fabil.FabILOptions#constructFabILClasspath()
   */
  public String constructFabILClasspath() {
    return constructSignatureClasspath() + File.pathSeparator
        + constructFullClasspath();
  }

  /* (non-Javadoc)
   * @see fabil.FabILOptions#constructPostCompilerClasspath()
   */
  @Override
  public String constructPostCompilerClasspath() {
    return super.constructPostCompilerClasspath() + File.pathSeparator
        + constructFullClasspath();
  }

  /* (non-Javadoc)
   * @see fabil.FabILOptions#optLevel()
   */
  public int optLevel() {
    return optLevel;
  }
  /*
   * (non-Javadoc)
   * @see fabil.FabILOptions#dumpDependencies()
   */
  public boolean dumpDependencies() {
    return dumpDependencies;
  }
  /*
   * (non-Javadoc)
   * @see fabil.FabILOptions#createJavaSkel()
   */
  public boolean createJavaSkel() {
    return createJavaSkel;
  }

  /* (non-Javadoc)
   * @see fabil.FabILOptions#signatureMode()
   */
  public boolean signatureMode() {
    return signatureMode;
  }

  public static FabILOptions global() {
    return (FabILOptions) global;
  }

  public String workerName() {
    return workerName;
  }

  public Collection<URI> codebasePath() {
    return codebasePath;
  }

  public String destinationStore() {
    return destinationStore;
  }

  public boolean runWorker() {
    return runWorker;
  }
}
