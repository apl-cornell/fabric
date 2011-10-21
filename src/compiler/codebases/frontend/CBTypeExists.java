package codebases.frontend;

import java.net.URI;

import polyglot.ast.NodeFactory;
import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.Pass;
import polyglot.frontend.Scheduler;
import polyglot.frontend.goals.Goal;
import polyglot.frontend.goals.TypeExists;
import polyglot.types.TypeSystem;

public class CBTypeExists extends TypeExists {
  public static Goal create(Scheduler scheduler, URI ns, String name) {
    return scheduler.internGoal(new CBTypeExists(ns,name));
  }

  protected URI namespace;
  protected String typeName;

  protected CBTypeExists(URI ns, String name) {
    super(name);
    this.namespace = ns;
  }

  @Override
  public Pass createPass(ExtensionInfo extInfo) {
    TypeSystem ts = extInfo.typeSystem();
    return new CBTypeExistsPass(extInfo.scheduler(), ts, this);
  }


  @Override
  public boolean equals(Object o) {
    return o instanceof CBTypeExists
        && ((CBTypeExists) o).namespace.equals(namespace) && super.equals(o);
  }

  @Override
  public String toString() {
    return "CBTypeExists( ["+namespace+ "] " + typeName + ")";
  }

  public URI namespace() {
    // TODO Auto-generated method stub
    return namespace;
  }

}
