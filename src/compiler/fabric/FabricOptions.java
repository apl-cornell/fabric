package fabric;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import jif.JifOptions;
import polyglot.main.UsageError;
import polyglot.main.Main.TerminationException;
import polyglot.util.InternalCompilerError;
import fabil.FabILOptions;
import fabil.FabILOptions_c;
import fabric.common.exceptions.InternalError;
import fabric.worker.Worker;

public class FabricOptions extends JifOptions implements FabILOptions {

  public FabricOptions(ExtensionInfo extension) {
    super(extension);
    this.delegate = new FabILOptions_c(extension);
  }

  /**
   * Whether to fully compile classes or just verify and publish.
   */
  public boolean publishOnly;

  /**
   * Name of file to write URL of new codebase to.
   */
  protected String codebaseFilename;

  /**
   * The classpath for the Fabric signatures of FabIL and Java classes.
   */
  public List<URI> sigcp;

  private static URI file = URI.create("file:///");

  @Override
  public void setDefaultValues() {
    super.setDefaultValues();

    // Override default in Jif: do not trust providers.
    this.trustedProviders = false;

    this.fully_qualified_names = true;
    this.fatalExceptions = true;
    this.publishOnly = false;

    this.sigcp = new ArrayList<URI>();
  }

  /* FabIL Options (forwarded to delegate ) ********************************** */

  protected FabILOptions_c delegate;

  /**
   * A map from names to codebase URIs. Used to resolve explicit codebases in
   * source.
   */
  protected Map<String, URI> codebase_aliases;

  /**
   * Whether to publish source to Fabric.
   */  
  protected boolean publish;

  /**
   * Whether to run a Fabric worker.
   */  
  protected boolean needWorker;
  
  @Override
  public boolean dumpDependencies() {
    return delegate.dumpDependencies;
  }

  @Override
  public int optLevel() {
    return delegate.optLevel();
  }

  @Override
  public boolean signatureMode() {
    return delegate.signatureMode();
  }

  /* Parsing ***************************************************************** */
  @Override
  public void usage(PrintStream out) {
    super.usage(out);
    usageForFlag(out, "-filsigcp <path>",
        "path for FabIL signatures (e.g. for fabric.lang.Object)");
    usageForFlag(out, "-addfilsigcp <path>",
        "additional path for FabIL signatures; prefixed to sigcp");
    usageForFlag(out, "-worker <worker>",
        "compile as a specific worker");
    usageForFlag(out, "-deststore <store>",
        "publish source to a Fabric store (all source on the commandline and" +
        " loaded through the sourcepath will be published)");
    usageForFlag(out, "-codebase-alias <name>=<URI>", "associate a codebase with an alias in source files");
    usageForFlag(out, "-publish",
        "Publish source to Fabric.");
    usageForFlag(out, "-publish-only",
        "Verify and publish source, do not compile to bytecode.");
    usageForFlag(out, "-codebase-output-file <filename>",
        "Write Fabric reference of published codebase to file.");
    usageForFlag(out, "-generate-native-skeletons",
        "generate FabIL and Java bootstrap skeletons for each class");
    usageForFlag(out, "-O", "turn optimizations on");

    out.println("Most <path> arguments accept local directory paths as well as");
    out.println("Fabric references to codebases objects in the following form:");
    usageForFlag(out, "<path>", "\"<fab://store/codebase_onum>:/path/to/local/dir/:...\"");
  }
  
  @SuppressWarnings("rawtypes")
  @Override
  protected int parseCommand(String[] args, int index, Set source)
      throws UsageError, TerminationException {
    // parse new options from fabric
    if (args[index].equals("-filsigcp")) {
      index++;
      delegate.sigcp.clear();
      delegate.addSigcp(args[index++]);
      return index;
    } else if (args[index].equals("-addfilsigcp")) {
      index++;
      delegate.addSigcp(args[index++]);
      return index;
    } else if (args[index].equals("-publish")) {
      index++;
      publish = true;
    } else if (args[index].equals("-publish-only")) {
      index++;
      publish = true;
      post_compiler = null;
      publishOnly = true;
    } else if (args[index].equals("-codebase-output-file")) {
      index++;
      this.codebaseFilename = args[index++];
      return index;
    } else if (args[index].equals("-sigcp")) {
      index++;
      sigcp.clear();
      processPathString(sigcp,args[index++]);
    } else if (args[index].equals("-addsigcp")) {
      index++;
      processPathString(sigcp,args[index++]);
    }
 
    // parse fabil options before jif's, otherwise some options
    //  will get gobbled by jif's superclass, BUT-- don't call 
    //  FabILOption_c superclass.
    int i = delegate.parseCommand(args, index, source,false);
    if (i != index) {
      index = i;
      return index;
    }

    // parse jif options
    return super.parseCommand(args, index, source);
  }

  public boolean publishOnly() {
    return publishOnly;
  }

  public String codebaseFilename() {
    return codebaseFilename;
  }

  @Override
  public List<URI> signaturepath() {
    return sigcp;
  }
  
  public List<URI> fabILSignaturePath() {
    return delegate.sigcp;
  }

  @Override
  public Map<String, URI> codebaseAliases() {
    return delegate.codebaseAliases();
  }

  /// Options processed by FabIL delegate
  @Override
  public boolean createSkeleton() {
    return delegate.createSkeleton;
  }

  @Override
  public String destinationStore() {
    return delegate.destinationStore();
  }

  public String workerName() {
    return delegate.workerName();
  }
  
  @Override
  public File outputDirectory() {
    return output_directory;
  }

  @Override
  public List<URI> classpath() {
    return delegate.classpath();
  }

  @Override
  public List<URI> sourcepath() {
    return delegate.sourcepath();
  }
  
  @Override
  public List<URI> bootclasspath() {
    return delegate.bootclasspath();
  }
  
  @Override
  public boolean platformMode() {
    return delegate.platformMode();
  }
  @Override
  public boolean needWorker() {
    return delegate.needWorker() || needWorker;
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
        int end = path.indexOf('>',idx);
        if(end < 0)
          throw new InternalCompilerError("Invalid path");
        URI u = URI.create(path.substring(idx + 1, end));
        uris.add(u);

        if(u.getScheme().equals("fab"))
          this.needWorker = true;
        idx = end + 1;
        
      } else if (path.charAt(idx) == File.pathSeparatorChar) {
        idx++;
      } else {
        int end = path.indexOf(File.pathSeparatorChar,idx);

        String dir="";
        if(end < 0) {
          dir = path.substring(idx);
          idx = path.length();
        }
        else {
          dir = path.substring(idx, end);
          idx = end;
        }
        if(!"".equals(dir)) {
          URI u = URI.create(dir);
          
          if (u.isAbsolute())
            uris.add(u);
          else {
            File f = new File(dir);
            uris.add(file.resolve(f.getAbsolutePath()));
          }
        }
      }
    }
  }

  /**
   * Should source be published to Fabric? Always false in signature or
   * platform modes.
   */
  public boolean publish() {
    //Never publish in signature or platform mode
    return publish & !signatureMode() && !platformMode();
  }
}