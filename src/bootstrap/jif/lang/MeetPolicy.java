package jif.lang;

import fabric.util.Set;

public interface MeetPolicy extends AbstractPolicy, Policy {
  Set meetComponents();
}
