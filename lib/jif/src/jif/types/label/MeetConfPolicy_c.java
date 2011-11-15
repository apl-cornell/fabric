package jif.types.label;

import java.util.ArrayList;
import java.util.Set;

import jif.types.JifTypeSystem;
import jif.types.hierarchy.LabelEnv;
import jif.types.hierarchy.LabelEnv.SearchState;
import polyglot.util.Position;
import polyglot.util.TypedList;


/** Represents the meet of a number of confidentiality policies. 
 */
public class MeetConfPolicy_c extends MeetPolicy_c implements ConfPolicy {

    public MeetConfPolicy_c(Set components, JifTypeSystem ts, Position pos) {
        super(components, ts, pos);
        // check that all the components are confidentiality policies
        TypedList.check(new ArrayList(components), ConfPolicy.class);
    }

    protected Policy constructMeetPolicy(Set components, Position pos) {
        return new MeetConfPolicy_c(components, (JifTypeSystem)ts, pos);
    }

    public boolean isBottomConfidentiality() {
        return isBottom();
    }

    public boolean isTopConfidentiality() {
        return isTop();
    }

    public boolean leq_(ConfPolicy p, LabelEnv env, SearchState state) {
        return leq_((Policy)p, env, state);
    }
    
    public ConfPolicy meet(ConfPolicy p) {
        JifTypeSystem ts = (JifTypeSystem)this.ts;
        return ts.meet(this, p);
    }
    public ConfPolicy join(ConfPolicy p) {
        JifTypeSystem ts = (JifTypeSystem)this.ts;
        return ts.join(this, p);
    }
}
