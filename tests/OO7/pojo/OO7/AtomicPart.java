// You can redistribute this software and/or modify it under the terms of
// the Ozone Core License version 1 published by ozone-db.org.
//
// The original code and portions created by Thorsten Fiebig are
// Copyright (C) 2000-@year@ by Thorsten Fiebig. All rights reserved.
// Code portions created by SMB are
// Copyright (C) 1997-@year@ by SMB GmbH. All rights reserved.
//
// $Id: AtomicPart.java,v 1.1 2008-01-08 18:38:02 mdgeorge Exp $

package OO7;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class AtomicPart extends DesignObject {
  long x, y;
  long docId;
  List/*Connection*/ to;
  List/*Connection*/ from;
  CompositePart partOf;

  public AtomicPart() {
    to   = new LinkedList();
    from = new LinkedList();
  }

  public void setX(long x) {
    this.x = x;
  }

  public long x() {
    return this.x;
  }

  public void setY(long y) {
    this.y = y;
  }

  public long y() {
    return this.y;
  }

  public void setDocId(long docId) {
    this.docId = docId;
  }

  public long docId() {
    return this.docId;
  }

  public void addTo(Connection x) {
    to.add(x);
  }

  public Collection to() {
    return to;
  }

  public void addFrom(Connection x) {
    from.add(x);
  }

  public Collection from() {
    return from;
  }

  public void setPartOf(CompositePart x) {
    this.partOf = x;
  }

  public CompositePart partOf() {
    return this.partOf;
  }

  public void accept (Traversal t) {
    t.visitAtomicPart(this);
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
