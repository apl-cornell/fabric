// You can redistribute this software and/or modify it under the terms of
// the Ozone Core License version 1 published by ozone-db.org.
//
// The original code and portions created by Thorsten Fiebig are
// Copyright (C) 2000-@year@ by Thorsten Fiebig. All rights reserved.
// Code portions created by SMB are
// Copyright (C) 1997-@year@ by SMB GmbH. All rights reserved.
//
// $Id: Document.java,v 1.1 2008-01-08 18:38:02 mdgeorge Exp $

package OO7;

public class Document {
  String title;
  long   id;
  String text;
  
  CompositePart part;

  public Document() {
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String title() {
    return this.title;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long id() {
    return this.id;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String text() {
    return this.text;
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
