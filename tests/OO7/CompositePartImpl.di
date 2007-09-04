// You can redistribute this software and/or modify it under the terms of
// the Ozone Core License version 1 published by ozone-db.org.
//
// The original code and portions created by Thorsten Fiebig are
// Copyright (C) 2000-@year@ by Thorsten Fiebig. All rights reserved.
// Code portions created by SMB are
// Copyright (C) 1997-@year@ by SMB GmbH. All rights reserved.
//
// $Id: CompositePartImpl.di,v 1.3 2007-09-04 20:30:21 jed Exp $

package OO7;

import diaspora.util.Collection;
import diaspora.util.List;
import diaspora.util.LinkedList;

public class CompositePartImpl extends DesignObjectImpl implements
    CompositePart {
  Document theDocumentation;
  LinkedList theUsedInPriv;
  LinkedList theUsedInShar;
  List theParts;
  AtomicPart theRootPart;

  public CompositePartImpl() {
    theUsedInPriv = new LinkedList();
    theUsedInShar = new LinkedList();
    theParts = new LinkedList();
  }

  public void setDocumentation(Document x) {
    theDocumentation = x;
  }

  public Document documentation() {
    return theDocumentation;
  }

  public void addUsedInPriv(BaseAssembly x) {
    theUsedInPriv.addLast(x);
  }

  public Collection usedInPriv() {
    return theUsedInPriv;
  }

  public void addUsedInShar(BaseAssembly x) {
    theUsedInShar.addLast(x);
  }

  public Collection usedInShar() {
    return theUsedInShar;
  }

  public void addPart(AtomicPart x) {
    theParts.add(x);
  }

  public Collection parts() {
    return theParts;
  }

  public void setRootPart(AtomicPart x) {
    theRootPart = x;
  }

  public AtomicPart rootPart() {
    return theRootPart;
  }
}
