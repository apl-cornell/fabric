package jif;

import jif.ast.JifNodeFactory;
import jif.types.JifTypeSystem;
import jif.visit.FieldLabelResolver;
import polyglot.frontend.Job;
import polyglot.frontend.Pass;
import polyglot.frontend.VisitorPass;
import polyglot.frontend.goals.SourceFileGoal;

public class FieldLabelInferenceGoal extends SourceFileGoal {
    public FieldLabelInferenceGoal(Job job) {
        super(job);
    }

    public Pass createPass(polyglot.frontend.ExtensionInfo extInfo) {
        ExtensionInfo jifext = (ExtensionInfo)extInfo;
        final JifTypeSystem ts = (JifTypeSystem) jifext.typeSystem();
        final JifNodeFactory nf = (JifNodeFactory) jifext.nodeFactory();
        return new VisitorPass(this, new FieldLabelResolver(this.job(), ts, nf));
    }
}