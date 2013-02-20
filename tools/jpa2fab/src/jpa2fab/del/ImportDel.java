package jpa2fab.del;

import polyglot.ast.Import;
import polyglot.ast.JL;
import polyglot.util.CodeWriter;
import polyglot.visit.PrettyPrinter;
import codebases.ast.CodebaseImportDel_c;

/**
 * 
 */
public class ImportDel extends CodebaseImportDel_c implements JL {
  @Override
  public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
    Import im = (Import) node();
    // skip these imports
    if (!im.name().startsWith("javax.persistence")) super.prettyPrint(w, tr);
  }
}
