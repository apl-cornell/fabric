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
import java.util.List;
import java.util.LinkedList;

public class CompositePart extends DesignObject {
  Document documentation;

  LinkedList/* BaseAssembly */usedInPriv;
  LinkedList/* BaseAssembly */usedInShar;
  List/* AtomicPart */parts;

  AtomicPart rootPart;

  public CompositePart(Benchmark db) {
    super(db);
    db.compositePartsById.put(new Integer(id()), this);

    usedInPriv = new LinkedList();
    usedInShar = new LinkedList();
    parts = new LinkedList();
  }

  public void setDocumentation(Document d) {
    documentation = d;
  }

  public Document documentation() {
    return documentation;
  }

  public void addUsedInPriv(BaseAssembly ba) {
    usedInPriv.add(ba);
  }

  public Collection usedInPriv() {
    return usedInPriv;
  }

  public void addUsedInShar(BaseAssembly ba) {
    usedInShar.add(ba);
  }

  public Collection usedInShar() {
    return usedInShar;
  }

  public void addPart(AtomicPart x) {
    parts.add(x);
  }

  public Collection parts() {
    return parts;
  }

  public void setRootPart(AtomicPart x) {
    rootPart = x;
  }

  public AtomicPart rootPart() {
    return rootPart;
  }

  public void accept(Traversal t) {
    t.visitCompositePart(this);
  }
}

/*
 * * vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
 */
