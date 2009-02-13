package fabric;

import java.util.Set;

import polyglot.main.UsageError;
import polyglot.main.Main.TerminationException;
import fabil.FabILOptions;
import fabil.FabILOptions_c;
import jif.JifOptions;

public class FabricOptions extends JifOptions implements FabILOptions {

  public FabricOptions(ExtensionInfo extension) {
    super(extension);
    this.delegate = new FabILOptions_c(extension);
  }
  
  @Override
  public void setDefaultValues() {
    super.setDefaultValues();
    this.fully_qualified_names = true;
  }

  /* FabIL Options (forwarded to delegate ) ***********************************/
  
  protected FabILOptions_c delegate;
  
  public String constructFabILClasspath() {
    return delegate.constructFabILClasspath();
  }

  public int optLevel() {
    return delegate.optLevel();
  }

  public boolean signatureMode() {
    return delegate.signatureMode();
  }

  /* Parsing ******************************************************************/
  
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
    
}
