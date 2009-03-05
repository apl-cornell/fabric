package jif.lang;

import fabric.util.Set;

public interface JoinPolicy extends AbstractPolicy, Policy {
  Set joinComponents();
}
