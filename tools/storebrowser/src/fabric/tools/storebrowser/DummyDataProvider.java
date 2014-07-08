/**
 * Copyright (C) 2010-2013 Fabric project group, Cornell University
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
package fabric.tools.storebrowser;

import java.util.Arrays;
import java.util.List;

public class DummyDataProvider implements DataProvider {

  @Override
  public Object getRoot() {
    return "fab://store0";
  }

  @Override
  public List<Object> getChildrenForNode(Object obj) {
    if (obj.equals("fab://store0")) {
      return Arrays.asList(new Object[] { "one", "two", "three" });
    }
    return Arrays.asList(new Object[] { "blah", "fang" });
  }

  @Override
  public String getDescriptionForNode(Object obj) {
    return "info";
  }

}
