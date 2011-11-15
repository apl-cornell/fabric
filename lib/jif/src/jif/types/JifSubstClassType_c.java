package jif.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jif.types.label.ThisLabel;
import polyglot.ext.param.types.PClass;
import polyglot.ext.param.types.SubstClassType_c;
import polyglot.main.Report;
import polyglot.types.ClassType;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

public class JifSubstClassType_c extends SubstClassType_c
    implements JifSubstType
{
    public JifSubstClassType_c(JifTypeSystem ts, Position pos,
                               ClassType base, JifSubst subst) {
        super(ts, pos, base, subst);

        if (! (base instanceof JifPolyType)) {
            throw new InternalCompilerError("Cannot perform subst on \"" +
                                            base + "\".");
        }
    }

    ////////////////////////////////////////////////////////////////
    // Implement methods of JifSubstType

    public PClass instantiatedFrom() {
        return ((JifPolyType) base).instantiatedFrom();
    }

    public List actuals() {
        JifPolyType pt = (JifPolyType) base;
        JifSubst subst = (JifSubst) this.subst;

        List actuals = new ArrayList(pt.params().size());

        for (Iterator i = pt.params().iterator(); i.hasNext(); ) {
            ParamInstance pi = (ParamInstance) i.next();
            Param p = subst.get(pi);
            actuals.add(p);
        }

        return actuals;
    }

    ////////////////////////////////////////////////////////////////
    // Implement methods of JifClassType

    public List authority() {
        JifClassType base = (JifClassType) this.base;
        JifSubst subst = (JifSubst) this.subst;
        return subst.substPrincipalList(base.authority());
    }

    public List constructorCallAuthority() {
        JifClassType base = (JifClassType) this.base;        
        JifSubst subst = (JifSubst) this.subst;
        return subst.substPrincipalList(base.constructorCallAuthority());
    }

    public ThisLabel thisLabel() {
	return ((JifTypeSystem)ts).thisLabel(this);
    }

    public String toString() {
        if (Report.should_report(Report.debug, 1)) {
            return super.toString();
        }

        // do something a little more readable
        JifPolyType jpt = (JifPolyType)base;
        String s = "";

        if (jpt.params() != null) {
            for (Iterator i = jpt.params().iterator(); i.hasNext(); ) {
                ParamInstance pi = (ParamInstance) i.next();
                s += subst.substitutions().get(pi);

                if (i.hasNext()) {
                    s += ", ";
                }
            }
        }

        if (s.length() > 0) {
            s = "[" + s + "]";
        }

        return jpt.name() + s;
    }
}
