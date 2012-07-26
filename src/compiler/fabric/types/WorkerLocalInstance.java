package fabric.types;

import jif.types.JifLocalInstance;
import polyglot.util.Internable;

/**
 * There can be only one worker$. When we read signatures from class files, use
 * the TypeSystem's workerLocalInstance
 */
public interface WorkerLocalInstance extends Internable, JifLocalInstance {

}
