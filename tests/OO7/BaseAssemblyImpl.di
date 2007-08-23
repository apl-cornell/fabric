// You can redistribute this software and/or modify it under the terms of
// the Ozone Core License version 1 published by ozone-db.org.
//
// The original code and portions created by Thorsten Fiebig are
// Copyright (C) 2000-@year@ by Thorsten Fiebig. All rights reserved.
// Code portions created by SMB are
// Copyright (C) 1997-@year@ by SMB GmbH. All rights reserved.
//
// $Id: BaseAssemblyImpl.di,v 1.2 2007-08-23 17:09:18 jed Exp $

package OO7;

import OO7.util.Collection;
import OO7.util.LinkedList;

public class BaseAssemblyImpl extends AssemblyImpl implements
    BaseAssembly {
  LinkedList theComponentsPriv;
  LinkedList theComponentsShar;

  public BaseAssemblyImpl() {
    theComponentsPriv = new LinkedList();
    theComponentsShar = new LinkedList();
  }

  public void addComponentsPriv(CompositePart x) {
    theComponentsPriv.addLast(x);
  }

  public Collection componetsPriv() {
    return theComponentsPriv;
  }

  public void addComponentsShar(CompositePart x) {
    theComponentsShar.addLast(x);
  }

  public Collection componentsShar() {
    return theComponentsShar;
  }
}
