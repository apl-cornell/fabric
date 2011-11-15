package jif.types.label;

import java.util.*;

import jif.translate.CannotLabelToJavaExpr_c;
import jif.types.JifTypeSystem;
import jif.types.LabelSubstitution;
import jif.types.hierarchy.LabelEnv;
import polyglot.main.Report;
import polyglot.types.SemanticException;
import polyglot.types.TypeObject;
import polyglot.types.TypeSystem;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/** An implementation of the <code>DynamicLabel</code> interface. 
 */
public class WritersToReadersLabel_c extends Label_c implements WritersToReadersLabel {
    final Label label;
    public WritersToReadersLabel_c(Label label, JifTypeSystem ts, Position pos) {
        super(ts, pos, new CannotLabelToJavaExpr_c());
        this.label = label;
        setDescription("finds an upper bound of " + label
                       + " and converts the writers of the bound into readers");
    }
    public Label label() {
        return label;
    }
    public boolean isRuntimeRepresentable() {
        return false;
    }
    public boolean isCovariant() {
        return false;
    }
    public boolean isComparable() {
        return true;
    }
    public boolean isCanonical() { return true; }
    protected boolean isDisambiguatedImpl() { return label.isCanonical(); }
    public boolean isEnumerable() {
        return true;
    }

    public IntegPolicy integProjection() {
        return ((JifTypeSystem)ts).bottomIntegPolicy(position());
    }

    public boolean equalsImpl(TypeObject o) {
        if (this == o) return true;
        if (! (o instanceof WritersToReadersLabel)) {
            return false;
        }           
        WritersToReadersLabel that = (WritersToReadersLabel) o;
        return (this.label.equals(that.label()));
    }
    public int hashCode() {
        return label.hashCode() ^ 597829;
    }
    
    public String componentString(Set printedLabels) {
        if (Report.should_report(Report.debug, 1)) { 
            return "<writersToReaders " + label + ">";
        }
        return "writersToReaders("+label()+")";
    }

    public boolean leq_(Label L, LabelEnv env, LabelEnv.SearchState state) {
        return false;
    }

    public List throwTypes(TypeSystem ts) {
        return Collections.EMPTY_LIST;
    }
    public Label subst(LabelSubstitution substitution) throws SemanticException {
        WritersToReadersLabel lbl = this;
        if (substitution.recurseIntoChildren(lbl)) {
            Label newLabel = lbl.label().subst(substitution);
                
            if (newLabel != lbl.label()) {
                JifTypeSystem ts = (JifTypeSystem)typeSystem();
                lbl = ts.writersToReadersLabel(lbl.position(), newLabel);
            }
        }
        return substitution.substLabel(lbl);
    }
    
    public boolean hasWritersToReaders() {
        return true;        
    }
    
    
    public Label transform(LabelEnv env) {
        Label bound = env.findUpperBound(label());
        return transformImpl(bound);
    }
    protected static Label transformImpl(Label label) {
        JifTypeSystem ts = (JifTypeSystem)label.typeSystem();
        if (label instanceof VarLabel_c) {
            // cant do anything.
            return ts.writersToReadersLabel(label.position(), label);
        }        
        else if (label instanceof PairLabel) {
            PairLabel pl = (PairLabel)label;   
            ConfPolicy newCP = transformIntegToConf(pl.integPolicy());
            return ts.pairLabel(pl.position(), newCP, ts.bottomIntegPolicy(pl.position()));
        }
        else if (label instanceof JoinLabel) {
            JoinLabel L = (JoinLabel)label;

            Set comps = new LinkedHashSet();
            for (Iterator iter = L.joinComponents().iterator(); iter.hasNext();) {
                Label c = (Label)iter.next();
                comps.add(transformImpl(c));
            }
            return ts.meetLabel(label.position(), comps);
        }
        else if (label instanceof MeetLabel) {
            MeetLabel L = (MeetLabel)label;

            Set comps = new LinkedHashSet();
            for (Iterator iter = L.meetComponents().iterator(); iter.hasNext();) {
                Label c = (Label)iter.next();
                comps.add(transformImpl(c));
            }
            return ts.joinLabel(label.position(), comps);
        }
        throw new InternalCompilerError("WritersToReaders undefined " +
                                        "for " + label);
    }
        
    protected static ConfPolicy transformIntegToConf(IntegPolicy pol) {
        JifTypeSystem ts = (JifTypeSystem)pol.typeSystem();
        if (pol instanceof WriterPolicy) {
            WriterPolicy wp = (WriterPolicy)pol;
            return ts.readerPolicy(wp.position(), wp.owner(), wp.writer());
        }
        if (pol instanceof JoinIntegPolicy_c) {
            JoinPolicy_c jp = (JoinPolicy_c)pol;
            Set newPols = new HashSet(jp.joinComponents().size());
            for (Iterator iter = jp.joinComponents().iterator(); iter.hasNext();) {
                IntegPolicy ip = (IntegPolicy)iter.next();
                ConfPolicy cp = transformIntegToConf(ip);
                newPols.add(cp);
            }
            return ts.meetConfPolicy(jp.position(), newPols);
        }
        if (pol instanceof MeetIntegPolicy_c) {
            MeetPolicy_c mp = (MeetPolicy_c)pol;
            Set newPols = new HashSet(mp.meetComponents().size());
            for (Iterator iter = mp.meetComponents().iterator(); iter.hasNext();) {
                IntegPolicy ip = (IntegPolicy)iter.next();
                ConfPolicy cp = transformIntegToConf(ip);
                newPols.add(cp);
            }
            return ts.joinConfPolicy(mp.position(), newPols);
        }
        throw new InternalCompilerError("Unexpected integ policy: " + pol);
    }
    
}
