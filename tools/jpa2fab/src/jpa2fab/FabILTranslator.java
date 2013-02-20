package jpa2fab;

import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.frontend.TargetFactory;
import polyglot.types.TypeSystem;
import polyglot.visit.Translator;

/**
 * 
 */
public class FabILTranslator extends Translator {

  public FabILTranslator(Job job, TypeSystem ts, NodeFactory nf,
      TargetFactory tf) {
    super(job, ts, nf, tf);
  }

}
