package jpa2fab.ext;

import polyglot.ast.Ext;
import polyglot.ast.Import;
import polyglot.ast.Node;
import polyglot.translate.ExtensionRewriter;
import polyglot.translate.ext.ImportToExt_c;
import polyglot.types.SemanticException;

/**
 * 
 */
public class ImportToFabIL_c extends ImportToExt_c implements Ext {
  @Override
  public Node toExt(ExtensionRewriter rw) throws SemanticException {
    JPA2FabILRewriter j2f = (JPA2FabILRewriter) rw;
    Import n = (Import) node();
    if (j2f.isCollection(n.name()))
      return rw.to_nf().Import(n.position(), n.kind(),
          n.name().replaceFirst("java", "fabric"));
    else return super.toExt(rw);
  }
}
