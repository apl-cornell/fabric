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
package fabric.worker;

import java.lang.ref.SoftReference;

import fabric.common.SerializedObject;

public class SerializedObjectSoftRef extends SoftReference<SerializedObject> {
  
  final long onum;

  public SerializedObjectSoftRef(RemoteStore store, SerializedObject obj) {
    super(obj, store.serializedRefQueue);
    this.onum = obj.getOnum();
  }

}
