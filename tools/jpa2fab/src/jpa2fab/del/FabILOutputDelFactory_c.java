package jpa2fab.del;

import polyglot.ast.DelFactory;
import polyglot.ast.JL;
import fabil.extension.FabILDelFactory_c;

/**
 * 
 */
public class FabILOutputDelFactory_c extends FabILDelFactory_c implements
    DelFactory {

  @Override
  protected JL delCallImpl() {
    return new CallDel();
  }

  @Override
  protected JL delNewArrayImpl() {
    return new NewArrayDel();
  }

  @Override
  protected JL delNewImpl() {
    return new NewDel();
  }

  @Override
  protected JL delImportImpl() {
    return new ImportDel();
  }
}
