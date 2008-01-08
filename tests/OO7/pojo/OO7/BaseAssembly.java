// You can redistribute this software and/or modify it under the terms of
// the Ozone Core License version 1 published by ozone-db.org.
//
// The original code and portions created by Thorsten Fiebig are
// Copyright (C) 2000-@year@ by Thorsten Fiebig. All rights reserved.
// Code portions created by SMB are
// Copyright (C) 1997-@year@ by SMB GmbH. All rights reserved.
//
// $Id: BaseAssembly.java,v 1.1 2008-01-08 18:38:02 mdgeorge Exp $

package OO7;

import java.util.Collection;
import java.util.LinkedList;

public class BaseAssembly extends Assembly {
  LinkedList/*CompositePart*/ componentsPriv;
  LinkedList/*CompositePart*/ componentsShar;

  public BaseAssembly() {
    componentsPriv = new LinkedList();
    componentsShar = new LinkedList();
  }

  public void addComponentsPriv(CompositePart x) {
    componentsPriv.addLast(x);
  }

  public Collection componentsPriv() {
    return componentsPriv;
  }

  public void addComponentsShar(CompositePart x) {
    componentsShar.addLast(x);
  }

  public Collection componentsShar() {
    return componentsShar;
  }

  public void accept (Traversal t) {
    t.visitBaseAssembly(this);
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
