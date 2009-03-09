// You can redistribute this software and/or modify it under the terms of
// the Ozone Core License version 1 published by ozone-db.org.
//
// The original code and portions created by Thorsten Fiebig are
// Copyright (C) 2000-@year@ by Thorsten Fiebig. All rights reserved.
// Code portions created by SMB are
// Copyright (C) 1997-@year@ by SMB GmbH. All rights reserved.
//
// $Id: BaseAssembly.java,v 1.3 2009-03-09 09:32:21 jed Exp $

package OO7;

import OO7.traversals.Traversal;

import java.util.Collection;
import java.util.ArrayList;

public class BaseAssembly extends Assembly {
  ArrayList/* CompositePart */componentsPriv;
  ArrayList/* CompositePart */componentsShar;

  public BaseAssembly(Benchmark db) {
    super(db);
    db.baseAssembliesById.put(new Long(id()), this);

    componentsPriv = new ArrayList();
    componentsShar = new ArrayList();
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
