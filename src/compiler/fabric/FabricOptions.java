package fabric;

import java.net.URI;
import java.util.Collection;
import java.util.Set;

import jif.JifOptions;
import polyglot.main.UsageError;
import polyglot.main.Main.TerminationException;
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

  @Override
  public void setDefaultValues() {
    super.setDefaultValues();
    
    // Override default in Jif: do not trust providers.
    this.trustedProviders = false;
    
    this.fully_qualified_names = true;
    this.fatalExceptions = true;
    this.publishOnly = false;
  }

  /* FabIL Options (forwarded to delegate ) ***********************************/
  
  protected FabILOptions_c delegate;
  
  
  public String constructFabILClasspath() {
    // XXX: copied from swift.  Not convinced it's right
    delegate.classpath     = this.classpath;
    delegate.bootclasspath = this.bootclasspath;
    return delegate.constructFabILClasspath();
  }
  
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
  
  @SuppressWarnings("rawtypes")
  @Override
  protected int parseCommand(String[] args, int index, Set source)
    throws UsageError, TerminationException {
    
    // parse new options from fabric
    if (args[index].equals("-filsigcp")) {
      index++;
      delegate.sigcp = args[index++];
      return index;
    }
    else if (args[index].equals("-addfilsigcp")) {
      index++;
      delegate.addSigcp.add(args[index++]);
      return index;
    } else if (args[index].equals("-publish-only")) {
      index++;
      post_compiler = null;
      publishOnly = true;
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
  
  public Collection<URI> codebasePath() {
    return delegate.codebasePath();
  }

  public boolean fixBrokenDeps() {
    return false;
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
}
