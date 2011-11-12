package fabric.extension;

import jif.extension.JifFieldDeclDel;
import jif.types.LabeledType;
import jif.types.label.ArgLabel;
import jif.types.label.CovariantParamLabel;
import jif.types.label.JoinLabel;
import jif.types.label.Label;
import jif.types.label.MeetLabel;
import jif.types.label.NotTaken;
import jif.types.label.ThisLabel;
import jif.types.label.UnknownLabel;
import jif.types.label.VarLabel;
import jif.types.label.WritersToReadersLabel;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.visit.AmbiguityRemover;
import fabric.ast.FabricFieldDecl;
import fabric.types.FabricDefaultSignature;
import fabric.types.FabricFieldInstance;
import fabric.types.FabricTypeSystem;

public class FabricFieldDeclDel extends JifFieldDeclDel {

  @Override
  public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
    FabricFieldDecl n = (FabricFieldDecl) super.disambiguate(ar);
    FabricFieldInstance ffi = (FabricFieldInstance) n.fieldInstance();
    FabricTypeSystem fts = (FabricTypeSystem) ar.typeSystem();
    FabricDefaultSignature fds = fts.fabricDefaultSignature();
    Label Li;
    
    // First check if the update label contains 'this'
    // If it does, then the access label has to be explicit
    // and should certainly not contain 'this'
    
    Label ul = ((LabeledType)ffi.type()).labelPart();
    if (n.accessLabel() == null && containsNAL(ul)) {
      throw new SemanticException("Explicit access label required");
    }
    
    if (n.accessLabel() == null) {
      Li = fds.defaultAccessLabel(n);
    } else {
      Li = n.accessLabel().label();
    }
    ffi.setAccessLabel(Li);
    return n.fieldInstance(ffi);
  }
  
  
  /**
   * 
   * @param lab
   * @return True, if lab contains any component
   *         that renders lab unfit for use as an access label
   *         e.g. if it contains this, a covariant parameter
   *         False, otherwise
   * @throws SemanticException, if the return value should be
   *         True and a clear error message for why can be printed
   * 
   * containsNAL = contains Non Access Label material
   */
  private boolean containsNAL(Label lab) throws SemanticException {
    if (lab instanceof ArgLabel || lab instanceof NotTaken || lab instanceof UnknownLabel ||
        lab instanceof VarLabel || lab instanceof WritersToReadersLabel) {
      // None of these have meaningful interpretations here
      throw new InternalCompilerError("Compiler Error: Wrong Label");
    }
    
    if (lab instanceof CovariantParamLabel) {
      throw new SemanticException("The update label has a covariant parameter and cannot be used as the default access label");
    }
    
    if (lab instanceof JoinLabel) {
      JoinLabel lab_ = (JoinLabel) lab;
      for (Label l : lab_.joinComponents()) {
        if (containsNAL(l)) return true;  
      }
      return false;
    }
    
    if (lab instanceof MeetLabel) {
      MeetLabel lab_ = (MeetLabel) lab;
      for (Label l : lab_.meetComponents()) {
        if (containsNAL(l)) return true;  
      }
      return false;
    }
    
    if (lab instanceof ThisLabel) {
      throw new SemanticException("The update label has the covariant parameter 'this' and cannot be used as the default access label");
    }

    return false;
  }

}
