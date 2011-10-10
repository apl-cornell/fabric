package fabric.types;

import java.net.URI;

import polyglot.types.ParsedClassType;

import jif.translate.LabelToJavaExpr;
import jif.types.JifClassType;
import jif.types.label.ProviderLabel_c;
import codebases.frontend.ExtensionInfo;
import codebases.types.CodebaseClassType;

public class FabricProviderLabel_c extends ProviderLabel_c {

  public FabricProviderLabel_c(JifClassType classType, LabelToJavaExpr toJava) {
    super(classType, toJava);

    //Disable trusted compilation until we figure out a couple things
    this.isTrusted = false;

//    System.err.println("Provider label of " + classType);
//    if (classType instanceof ParsedClassType) {
//    System.err.println("Source of " + classType + " is " + ((ParsedClassType) classType).fromSource());
//
//    }
    // Classes published to Fabric never have trusted providers.
//    if (classType instanceof ParsedClassType) {
//      ExtensionInfo extInfo = (ExtensionInfo) classType.typeSystem().extensionInfo();
//      CodebaseClassType cbct = (CodebaseClassType) classType;
//      URI ns = cbct.canonicalNamespace();
//      System.err.println("NS of " + classType + " is " + ns);
//      //If this class isn't from the local or platform namespace, 
//      // then we should never trust it.
//      if (!ns.equals(extInfo.localNamespace()) 
//          && !ns.equals(extInfo.platformNamespace())) {
//        this.isTrusted = false;
//      }
//    }
  }

}
