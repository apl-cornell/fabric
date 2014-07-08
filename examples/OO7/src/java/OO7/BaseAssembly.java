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
// You can redistribute this software and/or modify it under the terms of
// the Ozone Core License version 1 published by ozone-db.org.
//
// The original code and portions created by Thorsten Fiebig are
// Copyright (C) 2000-@year@ by Thorsten Fiebig. All rights reserved.
// Code portions created by SMB are
// Copyright (C) 1997-@year@ by SMB GmbH. All rights reserved.
//

package OO7;

import OO7.traversals.Traversal;

import java.util.Collection;
import java.util.LinkedList;

public class BaseAssembly extends Assembly {
  LinkedList/* CompositePart */componentsPriv;
  LinkedList/* CompositePart */componentsShar;

  public BaseAssembly(Benchmark db) {
    super(db);
    db.baseAssembliesById.put(new Long(id()), this);

    componentsPriv = new LinkedList();
    componentsShar = new LinkedList();
  }

  public void addComponentsPriv(CompositePart x) {
    componentsPriv.add(x);
  }

  public Collection componentsPriv() {
    return componentsPriv;
  }

  public void addComponentsShar(CompositePart x) {
    componentsShar.add(x);
  }

  public Collection componentsShar() {
    return componentsShar;
  }

  public void accept(Traversal t) {
    t.visitBaseAssembly(this);
  }
}

/*
 * * vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
 */
