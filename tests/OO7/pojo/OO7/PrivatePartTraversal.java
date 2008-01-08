package OO7;

import java.util.Iterator;

/** Common base class for various traversals.  This class represents a visitor
 *  that traverses the assembly hierarchy and then operates on the composite
 *  parts in some way.
 */
public abstract class PrivatePartTraversal extends Traversal {

  public void visitBenchmark (Benchmark b) {
    visitModule(b.module);
  }
  
  public void visitModule (Module m) {
    m.designRoot().accept(this);
  }
  
  public void visitComplexAssembly (ComplexAssembly ca) {
    Iterator i = ca.subAssemblies().iterator();
    while (i.hasNext())
      ((Assembly) i.next()).accept(this);
  }
  
  public void visitBaseAssembly (BaseAssembly ba) {
    Iterator i = ba.componentsPriv().iterator();
    while (i.hasNext())
      ((CompositePart) i.next()).accept(this);
  }
}

/*
** vim: ts=2 sw=2 cindent cino=\:0 et syntax=java
*/
