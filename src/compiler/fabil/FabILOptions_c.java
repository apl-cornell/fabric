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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import polyglot.frontend.ExtensionInfo;
import polyglot.main.Main.TerminationException;
import polyglot.main.UsageError;
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
  public List<URI> sigcp;
  
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
   * Class path.  May include Fabric references to codebases.  
   * NB: This field hides the corresponding field in polyglot.main.Options 
   */
  protected List<URI> classpath;
  
  /**
   * Source path.  May include Fabric references to codebases. 
   * NB: This field hides the corresponding field in polyglot.main.Options 
   */
  protected List<URI> source_path;
    
  /**
   * Codebase names.
   */
  protected Map<String, URI> codebase_aliases;
  
  public FabILOptions_c(ExtensionInfo extension) {
    super(extension);
  }

  private static URI file = URI.create("file:///");
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
    this.sigcp = new ArrayList<URI>();
    this.classpath = new ArrayList<URI>();
    this.source_path = new ArrayList<URI>();
    this.codebase_aliases = new LinkedHashMap<String, URI>();

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
    } 
    else if (args[index].equals("-dumpdeps")) {
      index++;
      dumpDependencies = true;
    } 
    else if (args[index].equals("-sigcp")) {
      index++;
      sigcp.clear();
      addSigcp(args[index++]);
    } 
    else if (args[index].equals("-addsigcp")) {
      index++;
      addSigcp(args[index++]);
    } 
    else if (args[index].startsWith("-O")) {
      if (args[index].length() == 2) {
        this.optLevel = Integer.MAX_VALUE;
      } else {
        try {
          this.optLevel = Integer.parseInt(args[index].substring(2));
        } catch (NumberFormatException e) {}
      }
      index++;
    } else if (args[index].equals("-classpath") || args[index].equals("-cp")) {
      index++;
      classpath = processPathString(args[index++]);
    } else if (args[index].equals("-sourcepath")) {
      index++;
      source_path = processPathString(args[index++]);
    }
    if (args[index].equals("-worker")) {
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
    else if (args[index].equals("-codebase-alias") || args[index].equals("-cb-alias")) {
      index++;
      this.runWorker = true;
      String arg = args[index++];
      if(arg.startsWith("@")) {
        try {
          BufferedReader lr = new BufferedReader(new FileReader(arg.substring(1)));
          addCodebaseAlias(lr.readLine());
        } catch (FileNotFoundException e) {
          throw new InternalCompilerError(e);
        } catch (IOException e) {
          throw new InternalCompilerError(e);
        }
      }
      else {
        addCodebaseAlias(arg);
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

  public void addSigcp(String arg) {
    processPathString(sigcp, arg);
  }

  protected void addCodebaseAlias(String arg) throws UsageError {
    String[] alias = arg.split("=");
    if(alias.length != 2)
      throw new UsageError("Invalid codebase alias:" + arg);
    URI uri = URI.create(alias[1]);
    if(!uri.isAbsolute())
      throw new UsageError("Invalid codebase reference in alias:" + arg);
    codebase_aliases.put(alias[0], uri);
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
    usageForFlag(out, "-deststore <store>", "the destination store for published classes");
    usageForFlag(out, "-codebase-alias <name>=<URI>", "associate a codebase with an alias in source files");
    usageForFlag(out, "-bootstrap-skel", "generate java bootstrap skeletons for each class");
    usageForFlag(out, "-O", "turn optimizations on");    
    
    out.println("Most <path> arguments accept local directory paths as well as");
    out.println("Fabric references to codebases objects in the following form:");
    usageForFlag(out, "<path>", "\"<fab://store/codebase_onum>:/path/to/local/dir/:...\"");
  }
  
  @Override
  public List<URI> signaturepath() {
    return sigcp;
  }

  @Override
  public List<URI> classpath() { 
    return classpath;
  }
  
  @Override
  public List<URI> sourcepath() {
    return source_path;
  }
  
  @Override
  public Map<String,URI> codebaseAliases() {
    return codebase_aliases;
  }
  
  /* (non-Javadoc)
   * @see fabil.FabILOptions#optLevel()
   */
  @Override
  public int optLevel() {
    return optLevel;
  }
  /*
   * (non-Javadoc)
   * @see fabil.FabILOptions#dumpDependencies()
   */
  @Override
  public boolean dumpDependencies() {
    return dumpDependencies;
  }
  /*
   * (non-Javadoc)
   * @see fabil.FabILOptions#createJavaSkel()
   */
  @Override
  public boolean createJavaSkel() {
    return createJavaSkel;
  }

  /* (non-Javadoc)
   * @see fabil.FabILOptions#signatureMode()
   */
  @Override
  public boolean signatureMode() {
    return signatureMode;
  }

  public static FabILOptions global() {
    return (FabILOptions) global;
  }

  public String workerName() {
    return workerName;
  }

  @Override
  public String destinationStore() {
    return destinationStore;
  }

  @Override
  public File outputDirectory() {
    return output_directory;
  }
  
  private List<URI> processPathString(String path) {
    List<URI> uris = new ArrayList<URI>();
    processPathString(uris, path);
    return uris;
  }
  /**
   * Process a path string of the form <URI>:/localdir/:... into URIs and add to a list
   * @param uris the list to add the URIs to
   * @param path the path-style string of URIs and directories, with URIs delimited by '<' and '>'
   */
  private void processPathString(List<URI> uris, String path) {
    if (path.startsWith("@")) {
      try {
        BufferedReader lr =
            new BufferedReader(new FileReader(path.substring(1)));
        path = lr.readLine();
      } catch (FileNotFoundException e) {
        throw new InternalCompilerError(e);
      } catch (IOException e) {
        throw new InternalCompilerError(e);
      }
    }
    int idx = 0;
    while (idx < path.length()) {
      if (path.charAt(idx) == '<') {
        int end = path.indexOf('>');
        if(end < idx)
          throw new InternalCompilerError("Invalid path");
        URI u = URI.create(path.substring(idx + 1, end));
        uris.add(u);
        if(u.getScheme().equals("fab"))
          this.runWorker = true;
        idx = end + 1;
      } else if (path.charAt(idx) == File.separatorChar) {
        idx++;
      } else {
        int end = path.indexOf(':');
        if(end < idx)
          end = path.length();
        URI u = URI.create(path.substring(idx, end));
        if (u.isAbsolute())
          uris.add(u);
        else uris.add(file.resolve(u));
        idx = end + 1;
      }
    }
  }

}
