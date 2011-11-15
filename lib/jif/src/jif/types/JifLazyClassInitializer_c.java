package jif.types;

import polyglot.types.SchedulerClassInitializer;
import polyglot.types.TypeSystem;

/**
 * A LazyClassInitializer is responsible for initializing members of
 * a class after it has been created.  Members are initialized lazily
 * to correctly handle cyclic dependencies between classes.
 */
public class JifLazyClassInitializer_c extends SchedulerClassInitializer
{
    public JifLazyClassInitializer_c(TypeSystem ts) {
        super(ts);
    }

    /** Override to prevent the "class" field from being added to
      * every class.
      *
      * no longer needed --nystrom
    public void initFields(ParsedClassType ct) {
        super.initFields(ct);
    }
      */
}
