/**
 * Copyright (C) 2010-2014 Fabric project group, Cornell University
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
package codebases.frontend;

import java.net.URI;

import polyglot.frontend.goals.Goal;

/**
 * Ideally this interface would extend an interface that all schedulers
 * implement, but polyglot.frontend.Scheduler is a class, not a scheduler.
 * 
 * @author owen
 */
public interface CBScheduler {
  Goal TypeExists(URI ns, String name);
}
