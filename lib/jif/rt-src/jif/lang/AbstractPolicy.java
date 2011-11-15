package jif.lang;



/**
 * A Label is the runtime representation of a Jif label. A Label consists of a
 * set of components, each of which is a {@link jif.lang.Policy Policy}.
 *  
 */
public abstract class AbstractPolicy implements Policy
{
    protected final LabelUtil labelUtil;
    protected AbstractPolicy(LabelUtil labelUtil) {
        this.labelUtil = labelUtil;
    }
    
    abstract public boolean equals(Object that);
    abstract public int hashCode();
}
