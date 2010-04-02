package fabric.lang.security;

import fabric.util.Set;

public interface MeetPolicy extends AbstractPolicy, Policy {
  Set meetComponents();
}
