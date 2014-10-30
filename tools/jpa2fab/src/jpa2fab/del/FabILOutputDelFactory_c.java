package jpa2fab.del;

import polyglot.ast.DelFactory;
import polyglot.ast.JLDel;
import fabil.extension.FabILDelFactory_c;

/**
 * 
 */
public class FabILOutputDelFactory_c extends FabILDelFactory_c implements
    DelFactory {

  @Override
  protected JLDel delCallImpl() {
    return new CallDel();
  }

  @Override
  protected JLDel delNewImpl() {
    return new NewDel();
  }

  @Override
  protected JLDel delImportImpl() {
    return new ImportDel();
  }
}
