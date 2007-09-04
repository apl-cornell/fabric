// You can redistribute this software and/or modify it under the terms of
// the Ozone Core License version 1 published by ozone-db.org.
//
// The original code and portions created by Thorsten Fiebig are
// Copyright (C) 2000-@year@ by Thorsten Fiebig. All rights reserved.
// Code portions created by SMB are
// Copyright (C) 1997-@year@ by SMB GmbH. All rights reserved.
//
// $Id: DocumentImpl.di,v 1.2 2007-09-04 20:30:21 jed Exp $

package OO7;

public class DocumentImpl implements Document {
  String theTitle;
  long theId;
  String theText;

  public DocumentImpl() {
  }

  public void setTitle(String x) {
    theTitle = x;
  }

  public String title() {
    return theTitle;
  }

  public void setId(long x) {
    theId = x;
  }

  public long id() {
    return theId;
  }

  public void setText(String x) {
    theText = x;
  }

  public String text() {
    return theText;
  }
}
