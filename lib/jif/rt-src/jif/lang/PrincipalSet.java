package jif.lang;

import java.util.*;

public class PrincipalSet
{
    private Set set;

    public PrincipalSet() {
	set = new LinkedHashSet();
    }

    public PrincipalSet add(Principal p) {
	PrincipalSet ps = new PrincipalSet();
	ps.set.addAll(set);
	ps.set.add(p);
	return ps;
    }

    Set getSet() {
	return set;
    }
}
