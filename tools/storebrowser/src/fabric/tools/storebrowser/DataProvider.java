/**
 * Copyright (C) 2010-2012 Fabric project group, Cornell University
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

import java.util.List;

/**
 * Hook to provide data to the tree navigation menu
 *
 * @author Lucas Waye
 */
public interface DataProvider {

  /**
   * @return root name of browser tree
   */
  public Object getRoot();

  /**
   * @param obj object to find all children for
   * @return all children of obj
   */
  List<Object> getChildrenForNode(Object obj);

  /**
   * @param obj object to get description for
   * @return description of obj
   */
  String getDescriptionForNode(Object obj);

}
