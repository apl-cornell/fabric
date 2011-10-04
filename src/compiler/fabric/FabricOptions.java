package fabric;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
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
   * The classpath for the FabIL signatures of Java objects.
   */
  public List<URI> sigcp;

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

  /* FabIL Options (forwarded to delegate ) ***********************************/
  
  protected FabILOptions_c delegate;

  protected Map<String, URI> codebase_aliases;
  
  public boolean dumpDependencies() {
    return delegate.dumpDependencies;
  }

  public int optLevel() {
    return delegate.optLevel();
  }

  public boolean signatureMode() {
    return delegate.signatureMode();
  }

  /* Parsing ******************************************************************/
  @Override
  public void usage(PrintStream out) {
    super.usage(out);
    usageForFlag(out, "-filsigcp <path>",
        "path for FabIL signatures (e.g. for fabric.lang.Object)");
    usageForFlag(out, "-addfilsigcp <path>",
        "additional path for FabIL signatures; prefixed to sigcp");
    usageForFlag(out, "-bootstrap-skel", "generate FabIL and Java bootstrap skeletons for each class");
    usageForFlag(out, "-publish-only", "Verify and publish source, do not compile to bytecode.");
    usageForFlag(out, "-codebase-output-file <filename>", "Write Fabric reference of published codebase to file.");
    usageForFlag(out, "-O", "turn optimizations on");
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
    }
    else if (args[index].equals("-addfilsigcp")) {
      index++;
      delegate.addSigcp(args[index++]);
      return index;
    } 
    else if (args[index].equals("-publish-only")) {
      index++;
      post_compiler = null;
      publishOnly = true;
    }
    else if (args[index].equals("-codebase-output-file")) {
      index++;
      this.codebaseFilename = args[index++];
      return index;
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
    else if (args[index].equals("-classpath") || args[index].equals("-cp")) {
      index++;
      classpath.clear();
      String path = args[index++];
      if(path.startsWith("@")) {
        try {
          BufferedReader lr = new BufferedReader(new FileReader(path.substring(1)));
          path = lr.readLine();
        } catch (FileNotFoundException e) {
          throw new InternalCompilerError(e);
        } catch (IOException e) {
          throw new InternalCompilerError(e);
        }        
      }
      StringTokenizer st = new StringTokenizer(path, File.pathSeparator);
      while (st.hasMoreTokens()) {
        URI uri = URI.create(st.nextToken());
        if (uri.isAbsolute())
          classpath.add(uri);
        else classpath.add(file.resolve(uri));
      }
    } 
    else if (args[index].equals("-sourcepath")) {
      index++;
      source_path = new ArrayList<URI>();
      String path = args[index++];
      if(path.startsWith("@")) {
        try {
          BufferedReader lr = new BufferedReader(new FileReader(path.substring(1)));
          path = lr.readLine();
        } catch (FileNotFoundException e) {
          throw new InternalCompilerError(e);
        } catch (IOException e) {
          throw new InternalCompilerError(e);
        }        
      }
      StringTokenizer st = new StringTokenizer(path, File.pathSeparator);
      while (st.hasMoreTokens()) {
        URI uri = URI.create(st.nextToken());
        if (uri.isAbsolute())
          source_path.add(uri);
        else source_path.add(file.resolve(uri));
      }
    }

    // parse jif options
    int i = super.parseCommand(args, index, source);
    if (i != index) {
      index = i;
      return index;
    }
    
    // parse fabil options
    return delegate.parseCommand(args, index, source);
  }

  public boolean createJavaSkel() {
    return delegate.createJavaSkel;
  }
  
  public String destinationStore() {
    return delegate.destinationStore();
  }
  
  public boolean runWorker() {
    return delegate.runWorker();
  }

  public String workerName() {
    return delegate.workerName();
  }
  
  public boolean publishOnly() {
    return publishOnly;
  }
  public String codebaseFilename() {
    return codebaseFilename;
  }
  
  public void addSigcp(String arg) {
    StringTokenizer st = new StringTokenizer(arg, File.pathSeparator);
    while (st.hasMoreTokens()) {
      URI uri = URI.create(st.nextToken());
      if (uri.isAbsolute())
        sigcp.add(uri);
      else sigcp.add(file.resolve(uri));
    }    
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
  public Map<String, URI> codebaseAliases() {
    return codebase_aliases;
  }

  @Override
  public File outputDirectory() {
    return output_directory;
  }

}
