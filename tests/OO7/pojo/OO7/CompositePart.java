// You can redistribute this software and/or modify it under the terms of
// the Ozone Core License version 1 published by ozone-db.org.
//
// The original code and portions created by Thorsten Fiebig are
// Copyright (C) 2000-@year@ by Thorsten Fiebig. All rights reserved.
// Code portions created by SMB are
// Copyright (C) 1997-@year@ by SMB GmbH. All rights reserved.
//
// $Id: CompositePart.java,v 1.1 2008-01-08 18:38:02 mdgeorge Exp $

package OO7;

import java.util.Collection;
import java.util.List;
import java.util.LinkedList;

public class CompositePart extends DesignObject {
  Document documentation;
  
  LinkedList/*BaseAssembly*/ usedInPriv;
  LinkedList/*BaseAssembly*/ usedInShar;
  List/*AtomicPart*/         parts;
  
  AtomicPart rootPart;

  public CompositePart() {
    usedInPriv = new LinkedList();
    usedInShar = new LinkedList();
    parts      = new LinkedList();
  }

  public void setDocumentation(Document d) {
    documentation = d;
  }

  public Document documentation() {
    return documentation;
  }

  public void addUsedInPriv(BaseAssembly ba) {
    usedInPriv.addLast(ba);
  }

  public Collection usedInPriv() {
    return usedInPriv;
  }

  public void addUsedInShar(BaseAssembly ba) {
    usedInShar.addLast(ba);
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

  public void accept (Traversal t) {
    t.visitCompositePart(this);
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
