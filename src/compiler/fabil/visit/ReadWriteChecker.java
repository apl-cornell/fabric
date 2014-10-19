package fabil.visit;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import polyglot.ast.Call;
import polyglot.ast.ConstructorCall;
import polyglot.ast.ConstructorDecl;
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
import polyglot.ast.Unary;
import polyglot.ast.Unary.Operator;
import polyglot.frontend.Job;
import polyglot.types.LocalInstance;
import polyglot.types.TypeSystem;
import polyglot.visit.DataFlow;
import polyglot.visit.FlowGraph;
import polyglot.visit.FlowGraph.EdgeKey;
import polyglot.visit.FlowGraph.Peer;
import fabil.ast.Atomic;
import fabil.extension.CallExt_c;
import fabil.extension.FieldAssignExt_c;
import fabil.extension.FieldExt_c;
import fabil.extension.UnaryExt_c;
import fabil.types.FabILTypeSystem;

/**
 * This dataflow analysis checks whether or not a local variable (or more
 * precisely what that variable points to) is guaranteed to have already been
 * read from or written to at a point in a method.
 *
 * @author xinz
 */
public class ReadWriteChecker extends DataFlow<ReadWriteChecker.DataFlowItem> {

  private final FabILTypeSystem ts;

  public ReadWriteChecker(Job job, TypeSystem ts, NodeFactory nf) {
    super(job, ts, nf, true);
    this.ts = (FabILTypeSystem) ts;
  }

  @Override
  protected DataFlowItem createInitialItem(FlowGraph<DataFlowItem> graph,
      Term node, boolean entry) {
    if (node instanceof ConstructorDecl) {
      return DataFlowItem.BOTTOM_C;
    } else {
      return DataFlowItem.BOTTOM;
    }
  }

  @Override
  protected DataFlowItem confluence(List<DataFlowItem> items,
      Peer<DataFlowItem> peer, FlowGraph<DataFlowItem> graph) {
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

    return out;
  }

  @Override
  protected Map<EdgeKey, DataFlowItem> flow(DataFlowItem in,
      FlowGraph<DataFlowItem> graph, Peer<DataFlowItem> peer) {
    DataFlowItem out = in;

    final Term n = peer.node();
    if (peer.isEntry()) {
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
        Field f = a.left();
        out = flowField(out, f, true);
      }

      if (n instanceof Unary) {
        Unary u = (Unary) n;

        if (isIncDec(u)) {
          Expr e = u.expr();

          if (e instanceof Field) {
            Field f = (Field) e;
            out = flowField(out, f);
          }
        }
      }

      if (n instanceof LocalAssign) {
        LocalAssign a = (LocalAssign) n;
        Local l = a.left();
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

      // hack: no optimizations can be done for the arguments of a constructor
      // call (that is, this(...) or super(...)), so we basically start from
      // bottom after the constructor call.
      if (n instanceof ConstructorCall) {
        out = DataFlowItem.BOTTOM_C;
      }
    }

    return itemToMap(out, peer.succEdgeKeys());
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

  private boolean isIncDec(Unary n) {
    Operator o = n.operator();
    return o == Unary.POST_DEC || o == Unary.POST_INC || o == Unary.PRE_DEC
        || o == Unary.PRE_INC;
  }

  private DataFlowItem flowField(DataFlowItem in, Field f, boolean write) {
    DataFlowItem out = in;
    Receiver e = f.target();

    if (!f.fieldInstance().flags().isStatic()) {
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
    }

    return out;
  }

  private DataFlowItem flowField(DataFlowItem in, Field f) {
    DataFlowItem out = in;
    Receiver e = f.target();

    if (!f.fieldInstance().flags().isStatic()) {
      if (e instanceof Local) {
        Local l = (Local) e;
        out = new DataFlowItem(in);
        out.all(l.localInstance());
      } else if (isThis(e)) {
        out = new DataFlowItem(in);
        out.all(null);
      }
    }

    return out;
  }

  private DataFlowItem flowCall(DataFlowItem in, Call c) {
    DataFlowItem out = in;
    Receiver e = c.target();

    if (!c.methodInstance().flags().isStatic()) {
      if (e instanceof Local) {
        Local l = (Local) e;
        out = new DataFlowItem(in);
        out.reside(l.localInstance());
      }
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
    out.alloc(li);
    return out;
  }

  private DataFlowItem atomic(DataFlowItem in) {
    DataFlowItem out = new DataFlowItem(in);
    out.atomic();
    return out;
  }

  @Override
  protected void check(FlowGraph<DataFlowItem> graph, Term n, boolean entry,
      DataFlowItem inItem, Map<EdgeKey, DataFlowItem> outItems) {
    DataFlowItem in = inItem;

    if (!entry) {
      if (n instanceof Field) {
        Field f = (Field) n;
        Receiver e = f.target();

        if (ts.isPureFabricType(e.type())
            && !f.fieldInstance().flags().isStatic()) {
          if (e instanceof Local) {
            Local l = (Local) e;
            ((FieldExt_c) f.ext()).accessState(in.state(l.localInstance()));
          } else if (isThis(e)) {
            ((FieldExt_c) f.ext()).accessState(in.state(null));
          }
        }
      }

      if (n instanceof FieldAssign) {
        FieldAssign a = (FieldAssign) n;
        Field f = a.left();
        Receiver e = f.target();

        if (ts.isPureFabricType(e.type())
            && !f.fieldInstance().flags().isStatic()) {
          if (e instanceof Local) {
            Local l = (Local) e;
            ((FieldAssignExt_c) a.ext())
                .accessState(in.state(l.localInstance()));
          } else if (isThis(e)) {
            ((FieldAssignExt_c) a.ext()).accessState(in.state(null));
          }
        }
      }

      if (n instanceof Call) {
        Call c = (Call) n;
        Receiver e = c.target();

        if (ts.isPureFabricType(e.type())
            && !c.methodInstance().flags().isStatic()) {
          if (e instanceof Local) {
            Local l = (Local) e;
            ((CallExt_c) c.ext()).accessState(in.state(l.localInstance()));
          }
        }
      }

      if (n instanceof Unary) {
        Unary u = (Unary) n;

        if (isIncDec(u)) {
          Expr e = u.expr();

          if (e instanceof Field) {
            Field f = (Field) e;
            Receiver r = f.target();

            if (ts.isPureFabricType(r.type())
                && !f.fieldInstance().flags().isStatic()) {
              if (r instanceof Local) {
                Local l = (Local) r;
                ((UnaryExt_c) u.ext()).accessState(in.state(l.localInstance()));
              } else if (isThis(r)) {
                ((UnaryExt_c) u.ext()).accessState(in.state(null));
              }
            }
          }
        }
      }
    }
  }

  /** The access state of a variable (resident, read, written). */
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

    public boolean all() {
      return read && written;
    }

  }

  protected static class DataFlowItem extends DataFlow.Item {

    /** Bottom item. No reads or writes, only 'this' resident. */
    public static final DataFlowItem BOTTOM = new DataFlowItem();

    /** Initial item for constructors. 'this' resident, read, and written. */
    public static final DataFlowItem BOTTOM_C = new DataFlowItem(true);

    // in these sets, null means the 'this' pointer
    private final Set<LocalInstance> resident;
    public final Set<LocalInstance> read;
    public final Set<LocalInstance> written;

    private DataFlowItem() {
      resident = Collections.singleton(null);
      read = Collections.emptySet();
      written = Collections.emptySet();
    }

    private DataFlowItem(boolean ctor) {
      resident = Collections.singleton(null);
      read = Collections.singleton(null);
      written = Collections.singleton(null);
    }

    public DataFlowItem(DataFlowItem i) {
      resident = new HashSet<>(i.resident);
      read = new HashSet<>(i.read);
      written = new HashSet<>(i.written);
    }

    /** Destructive meet of this item with another. */
    public DataFlowItem meet(DataFlowItem i) {
      resident.retainAll(i.resident);
      read.retainAll(i.read);
      written.retainAll(i.written);
      return this;
    }

    /** Destructive add of a local instance to resident. */
    public DataFlowItem reside(LocalInstance l) {
      resident.add(l);
      return this;
    }

    /** Destructive add of a local instance to read. */
    public DataFlowItem read(LocalInstance l) {
      resident.add(l);
      read.add(l);
      return this;
    }

    /** Destructive add of a local instance to written. */
    public DataFlowItem write(LocalInstance l) {
      resident.add(l);
      written.add(l);
      return this;
    }

    /** Destructive update of local instance that is assigned a new object. */
    public DataFlowItem alloc(LocalInstance l) {
      kill(l);
      read.add(l);
      written.add(l);
      return this;
    }

    /** Destructive add of local instance to resident, read, and written. */
    public DataFlowItem all(LocalInstance l) {
      resident.add(l);
      read.add(l);
      written.add(l);
      return this;
    }

    /**
     * Destructive update for a copy operation. For the statement to = from, we
     * use information about from to set the state of to.
     */
    public DataFlowItem copy(LocalInstance to, LocalInstance from,
        DataFlowItem in) {
      kill(to);

      if (in.read.contains(from)) {
        read.add(to);
      }

      if (in.written.contains(from)) {
        written.add(to);
      }

      return this;
    }

    /** Destructive kill of local instance l. */
    public DataFlowItem kill(LocalInstance l) {
      resident.remove(l);
      read.remove(l);
      written.remove(l);
      return this;
    }

    /** Destructive update on entering an atomic block. Removes all writes. */
    public DataFlowItem atomic() {
      written.clear();
      return this;
    }

    /** Returns a state object describing local instance l. */
    public State state(LocalInstance l) {
      return new State(resident.contains(l), read.contains(l),
          written.contains(l));
    }

    @Override
    public boolean equals(Object i) {
      if (i instanceof DataFlowItem) {
        DataFlowItem o = (DataFlowItem) i;
        return this.resident.equals(o.resident) && this.read.equals(o.read)
            && this.written.equals(o.written);
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
