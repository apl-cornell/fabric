package codebases.types;

import java.net.URI;

import polyglot.frontend.MissingDependencyException;
import polyglot.frontend.Scheduler;
import polyglot.frontend.SchedulerException;
import polyglot.frontend.goals.Goal;
import polyglot.types.Named;
import polyglot.types.PlaceHolder_c;
import polyglot.types.SemanticException;
import polyglot.types.TypeObject;
import polyglot.types.TypeSystem;
import polyglot.util.CannotResolvePlaceHolderException;
import codebases.frontend.CBScheduler;
import codebases.types.CodebaseClassType;

/**
 * This class is basically copied from the superclass with following additions:
 * - A field for the namespace of the class this object is a place holder for. 
 * - Calls to systemResolver are replaced with calls to the appropriate namespace.
 * - resolve() calls resolveSafe();
 * - Calls to TypeExists(name) are replaced with calls to TypeExists(ns,name)
 */
public class CBPlaceHolder_c extends PlaceHolder_c implements CBPlaceHolder {
  protected URI namespace;

  /** Used for deserializing types. */
  protected CBPlaceHolder_c() {
  }

  /** Creates a place holder type for the type. */
  public CBPlaceHolder_c(CodebaseClassType t) {
    this(t.canonicalNamespace(), t.fullName());
  }

  public URI namespace() {
    return namespace;
  }

  @Override
  public int hashCode() {
    return namespace.hashCode() ^ name.hashCode();
  }

  public CBPlaceHolder_c(URI ns, String name) {
    this.namespace = ns;
    this.name = name;

  }

  @Override
  public TypeObject resolve(TypeSystem ts)
      throws CannotResolvePlaceHolderException {
    return resolveSafe(ts);
  }

  @Override
  public TypeObject resolveUnsafe(TypeSystem ts)
      throws CannotResolvePlaceHolderException {
    CodebaseTypeSystem cbts = (CodebaseTypeSystem) ts;
    Scheduler scheduler = ts.extensionInfo().scheduler();
    Goal g = ((CBScheduler) scheduler).TypeExists(namespace, name);

    try {
      return cbts.namespaceResolver(namespace).find(name);
    } catch (MissingDependencyException e) {
      // The type is in a source file that hasn't been parsed yet.
      g = e.goal();
      scheduler.currentGoal().setUnreachableThisRun();
      scheduler.addDependencyAndEnqueue(scheduler.currentGoal(), g, false);
      throw new CannotResolvePlaceHolderException(e);
    } catch (SchedulerException e) {
      // Some other scheduler error occurred.
      scheduler.currentGoal().setUnreachableThisRun();
      scheduler.addDependencyAndEnqueue(scheduler.currentGoal(), g, false);
      throw new CannotResolvePlaceHolderException(e);
    } catch (SemanticException e) {
      // The type could not be found.
      scheduler.currentGoal().setUnreachableThisRun();
      scheduler.addDependencyAndEnqueue(scheduler.currentGoal(), g, false);
      throw new CannotResolvePlaceHolderException(e);
    }
  }

  /** A potentially safer alternative implementation of resolve. */
  @Override
  public TypeObject resolveSafe(TypeSystem ts)
      throws CannotResolvePlaceHolderException {
    CodebaseTypeSystem cbts = (CodebaseTypeSystem) ts;

    Named n = cbts.namespaceResolver(namespace).check(name);

    if (n != null) {
      return n;
    }

    // The class has not been loaded yet. Set up a dependency
    // to load the class (coreq, in case this pass is the one to load it).
    Scheduler scheduler = ts.extensionInfo().scheduler();
    scheduler.currentGoal().setUnreachableThisRun();
    scheduler.addDependencyAndEnqueue(scheduler.currentGoal(),
        ((CBScheduler) scheduler).TypeExists(namespace, name), false);

    throw new CannotResolvePlaceHolderException("Could not resolve " + name);
  }
  public String toString() {
    return "CBPlaceHolder(" + name + ")";
}

}
