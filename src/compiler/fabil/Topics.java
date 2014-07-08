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
package fabil;

import polyglot.main.Report;

public class Topics {
  public static final String fabil = "fabil";
  public static final String mobile = "mobile";
  public static final String profile = "profile";
  static {
    Report.topics.add(fabil);
    Report.topics.add(mobile);
    Report.topics.add(profile);
  }

}
