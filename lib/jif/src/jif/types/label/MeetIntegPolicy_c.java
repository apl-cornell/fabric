package jif.types.label;

import java.util.ArrayList;
import java.util.Set;

import jif.types.JifTypeSystem;
import jif.types.hierarchy.LabelEnv;
import jif.types.hierarchy.LabelEnv.SearchState;
import polyglot.util.Position;
import polyglot.util.TypedList;


/** Represents the meet of a number of integrity policies. 
 */
public class MeetIntegPolicy_c extends MeetPolicy_c implements IntegPolicy {

    public MeetIntegPolicy_c(Set components, JifTypeSystem ts, Position pos) {
        super(components, ts, pos);
        // check that all the components are integrity policies
        TypedList.check(new ArrayList(components), IntegPolicy.class);
    }

    protected Policy constructMeetPolicy(Set components, Position pos) {
        return new MeetIntegPolicy_c(components, (JifTypeSystem)ts, pos);
    }

    public boolean isBottomIntegrity() {
        return isBottom();
    }

    public boolean isTopIntegrity() {
        return isTop();
    }

    public boolean leq_(IntegPolicy p, LabelEnv env, SearchState state) {
        return leq_((Policy)p, env, state);
    }
    public IntegPolicy meet(IntegPolicy p) {
        JifTypeSystem ts = (JifTypeSystem)this.ts;
        return ts.meet(this, p);
    }
    public IntegPolicy join(IntegPolicy p) {
        JifTypeSystem ts = (JifTypeSystem)this.ts;
        return ts.join(this, p);
    }
}
