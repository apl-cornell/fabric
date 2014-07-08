/**
 * Copyright (C) 2010 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
package webapp;

import fabric.util.*;

public class FabricUtils {

public static Integer length(Object o) {
  if(o instanceof fabric.util.Collections.UnmodifiableCollection._Proxy)
    return length((fabric.util.Collections.UnmodifiableCollection._Proxy)o);
  else if(o instanceof fabric.util.Map._Proxy) {
    return length((fabric.util.Map._Proxy)o);
  } else {
    return -1;
  }
}

public static Integer length(fabric.util.Collections.UnmodifiableCollection._Proxy p) {
  return new Integer(p.size());
}
public static Integer length(fabric.util.Collection p) {
  return new Integer(p.size());
}
public static Integer length(fabric.util.Map._Proxy c) {
  return new Integer(c.size());
}
}
