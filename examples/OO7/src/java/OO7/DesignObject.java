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

public abstract class DesignObject {
  int id;
  String type;
  long buildDate;

  DesignObject(Benchmark db) {
    this.id = db.newId();
    type = "";
  }

  public int id() {
    return this.id;
  }

  public String type() {
    return this.type;
  }

  public void setBuildDate(long date) {
    this.buildDate = date;
  }

  public long buildDate() {
    return this.buildDate;
  }

  /*
   * Visitor method
   */
  public abstract void accept(Traversal t);
}

/*
 * * vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
 */
