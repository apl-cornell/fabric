// You can redistribute this software and/or modify it under the terms of
// the Ozone Core License version 1 published by ozone-db.org.
//
// The original code and portions created by Thorsten Fiebig are
// Copyright (C) 2000-@year@ by Thorsten Fiebig. All rights reserved.
// Code portions created by SMB are
// Copyright (C) 1997-@year@ by SMB GmbH. All rights reserved.
//
// $Id: AtomicPartImpl.di,v 1.2 2007-08-23 17:09:18 jed Exp $

package OO7;

import OO7.util.Collection;
import OO7.util.LinkedList;
import OO7.util.List;

public class AtomicPartImpl extends DesignObjectImpl implements
    AtomicPart {
  long theX;
  long theY;
  long theDocId;
  List theToConnections;
  List theFromConnections;
  CompositePart thePartOf;

  public AtomicPartImpl() {
    theToConnections = new LinkedList();
    theFromConnections = new LinkedList();
  }

  public void setX(long x) {
    theX = x;
  }

  public long x() {
    return theX;
  }

  public void setY(long x) {
    theY = x;
  }

  public long y() {
    return theY;
  }

  public void setDocId(long x) {
    theDocId = x;
  }

  public long docId() {
    return theDocId;
  }

  public void addTo(Connection x) {
    theToConnections.add(x);
  }

  public Collection to() {
    return theToConnections;
  }

  public void addFrom(Connection x) {
    theFromConnections.add(x);
  }

  public Collection from() {
    return theFromConnections;
  }

  public void setPartOf(CompositePart x) {
    thePartOf = x;
  }

  public CompositePart partOf() {
    return thePartOf;
  }
}
