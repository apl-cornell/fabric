package fabric.visit;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import polyglot.ast.Call;
import polyglot.ast.Expr;
import polyglot.ast.Field;
import polyglot.ast.FieldAssign;
import polyglot.ast.Local;
import polyglot.ast.LocalAssign;
import polyglot.ast.New;
import polyglot.ast.NewArray;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Receiver;
import polyglot.ast.Special;
import polyglot.ast.Term;
import polyglot.frontend.Job;
import polyglot.types.LocalInstance;
import polyglot.types.TypeSystem;
import polyglot.visit.DataFlow;
import polyglot.visit.FlowGraph;
import fabric.ast.Atomic;
import fabric.extension.CallExt_c;
import fabric.extension.FieldExt_c;

/**
 * This dataflow analysis checks whether or not a local variable (or more
 * precisely what that variable points to) is guaranteed to have already been
 * read from or written to at a point in a method.
 * 
 * @author xinz
 */
public class ReadWriteChecker extends DataFlow {
  
  public ReadWriteChecker(Job job, TypeSystem ts, NodeFactory nf) {
    super(job, ts, nf, true);
  }

  @Override
  protected Item createInitialItem(FlowGraph graph, Term node, boolean entry) {
    return DataFlowItem.BOTTOM;
  }

  @SuppressWarnings("unchecked")
  @Override
  protected Item confluence(List items, Term node, boolean entry,
      FlowGraph graph) {
    DataFlowItem out = null;
    
    for (Object o : items) {
      DataFlowItem in = (DataFlowItem) o;
      
      if (in == DataFlowItem.BOTTOM) {
        return in;
      }
      
      if (out == null) {
        out = new DataFlowItem(in);
      } else {
        out.meet(in);
      }
    }
    
    return out != null ? out : DataFlowItem.BOTTOM;
  }

  @SuppressWarnings("unchecked")
  @Override
  protected Map flow(Item in, FlowGraph graph, Term n, boolean entry,
      Set edgeKeys) {
    DataFlowItem out = (DataFlowItem) in;
    
    if (entry) {
      if (n instanceof Atomic) {
        // inside a sub-atomic block, we need to register writes again, so here
        // we conservatively kill all writes. ideally, when we return to the
        // outer block, we won't register the write again if the variable is not
        // changed inside the sub-atomic block. however, this is not yet
        // implemented. once we kill the writes for the sub-atomic block, they
        // will remained killed when we exit the sub-atomic block, unless the
        // sub-block updates the state.
        out = atomic(out);
      }
    } else {
      if (n instanceof Field) {
        Field f = (Field) n;
        out = flowField(out, f, false);
      }
      
      if (n instanceof Call) {
        Call c = (Call) n;
        out = flowCall(out, c);
      }
      
      if (n instanceof FieldAssign) {
        FieldAssign a = (FieldAssign) n;
        Field f = (Field) a.left();
        out = flowField(out, f, true);
      }
      
      if (n instanceof LocalAssign) {
        LocalAssign a = (LocalAssign) n;
        Local l = (Local) a.left();
        Expr e = a.right();
        
        if (e instanceof Local) {
          out = copy(out, l, (Local) e);
        } else if (isThis(e)) {
          out = copy(out, l, null);
        } else if (isNew(e)) {
          out = alloc(out, l);
        } else {
          out = kill(out, l);
        }
      }
    }
    
    return itemToMap(out, edgeKeys);
  }
  
  private boolean isThis(Node n) {
    if (n instanceof Special) {
      return ((Special) n).qualifier() == null;
    } else {
      return false;
    }
  }
  
  private boolean isNew(Node n) {
    return n instanceof New || n instanceof NewArray;
  }
  
  private DataFlowItem flowField(DataFlowItem in, Field f, boolean write) {
    DataFlowItem out = in;
    Receiver e = f.target();
    
    if (e instanceof Local) {
      Local l = (Local) e;
      out = new DataFlowItem(in);
      
      if (write) {
        out.write(l.localInstance());
      } else {
        out.read(l.localInstance());
      }
    } else if (isThis(e)) {
      out = new DataFlowItem(in);
      
      if (write) {
        out.write(null);
      } else {
        out.read(null);
      }
    }
    
    return out;
  }

  private DataFlowItem flowCall(DataFlowItem in, Call c) {
    DataFlowItem out = in;
    Receiver e = c.target();
    
    if (e instanceof Local) {
      Local l = (Local) e;
      out = new DataFlowItem(in);
      out.reside(l.localInstance());
    }
    
    return out;
  }
  
  private DataFlowItem kill(DataFlowItem in, Local l) {
    DataFlowItem out = new DataFlowItem(in);
    out.kill(l.localInstance());
    return out;
  }
  
  private DataFlowItem copy(DataFlowItem in, Local to, Local from) {
    DataFlowItem out = new DataFlowItem(in);
    // from == null means from is 'this'
    out.copy(to.localInstance(), from == null ? null : from.localInstance(), in);
    return out;
  }
  
  private DataFlowItem alloc(DataFlowItem in, Local l) {
    DataFlowItem out = new DataFlowItem(in);
    LocalInstance li = l.localInstance();
    out.read(li);
    out.write(li);
    return out;
  }
  
  private DataFlowItem atomic(DataFlowItem in) {
    DataFlowItem out = new DataFlowItem(in);
    out.killWritten();
    return out;
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void check(FlowGraph graph, Term n, boolean entry, Item inItem,
      Map outItems) {
    DataFlowItem in = (DataFlowItem) inItem;
    
    if (!entry) {
      if (n instanceof Field) {
        Field f = (Field) n;
        Receiver e = f.target();
        
        if (e instanceof Local) {
          Local l = (Local) e;
          ((FieldExt_c) f.ext()).accessState(in.state(l.localInstance()));
        } else if (isThis(e)) {
          ((FieldExt_c) f.ext()).accessState(in.state(null));
        }
      }
      
      if (n instanceof Call) {
        Call c = (Call) n;
        Receiver e = c.target();
        
        if (e instanceof Local) {
          Local l = (Local) e;
          ((CallExt_c) c.ext()).accessState(in.state(l.localInstance()));
        }
      }
    }
  }
  
  public static class State {
    
    private final boolean resident;
    private final boolean read;
    private final boolean written;
    
    public State(boolean resident, boolean read, boolean written) {
      this.resident = resident;
      this.read = read;
      this.written = written;
    }
    
    public boolean resident() {
      return resident;
    }
    
    public boolean read() {
      return read;
    }
    
    public boolean written() {
      return written;
    }
    
  }
  
  protected static class DataFlowItem extends DataFlow.Item {
    
    public static final DataFlowItem BOTTOM = new DataFlowItem();
    
    // in these sets, null means the 'this' pointer
    private final Set<LocalInstance> resident;
    public final Set<LocalInstance> read;
    public final Set<LocalInstance> written;
    
    public DataFlowItem() {
      resident = Collections.singleton(null);
      read = Collections.emptySet();
      written = Collections.emptySet();
    }
    
    public DataFlowItem(DataFlowItem i) {
      resident = new HashSet<LocalInstance>(i.resident);
      read = new HashSet<LocalInstance>(i.read);
      written = new HashSet<LocalInstance>(i.written);
    }
    
    /** Destructive meet of this item with another. */
    public void meet(DataFlowItem i) {
      resident.retainAll(i.resident);
      read.retainAll(i.read);
      written.retainAll(i.written);
    }
    
    /** Destructive add of a local instance to resident. */
    public void reside(LocalInstance l) {
      resident.add(l);
    }
    
    /** Destructive add of a local instance to read. */
    public void read(LocalInstance l) {
      resident.add(l);
      read.add(l);
    }
    
    /** Destructive add of a local instance to written. */
    public void write(LocalInstance l) {
      resident.add(l);
      written.add(l);
    }
    
    /**
     * Destructive update for a copy operation. For the statement to = from,
     * we use information about from to set the state of to.
     */
    public void copy(LocalInstance to, LocalInstance from, DataFlowItem in) {
      kill(to);
      
      if (in.resident.contains(from)) {
        resident.add(to);
      }
      
      if (in.read.contains(from)) {
        read.add(to);
      }
      
      if (in.written.contains(from)) {
        written.add(to);
      }
    }
    
    /** Destructive kill of local instance l. */
    public void kill(LocalInstance l) {
      resident.remove(l);
      read.remove(l);
      written.remove(l);
    }
    
    /** Destructive remove of all writes. */
    public void killWritten() {
      written.clear();
    }
    
    /** Returns a state object describing local instance l. */
    public State state(LocalInstance l) {
      return new State(resident.contains(l), read.contains(l), written.contains(l));
    }
    
    @Override
    public boolean equals(Object i) {
      if (i instanceof DataFlowItem) {
        DataFlowItem o = (DataFlowItem) i;
        return this.resident.equals(o.resident) && this.read.equals(o.read) && 
          this.written.equals(o.written);
      } else {
        return false;
      }
    }

    @Override
    public int hashCode() {
      return resident.hashCode() ^ read.hashCode() ^ written.hashCode();
    }
    
  }

}
