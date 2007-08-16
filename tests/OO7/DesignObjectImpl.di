// You can redistribute this software and/or modify it under the terms of
// the Ozone Core License version 1 published by ozone-db.org.
//
// The original code and portions created by Thorsten Fiebig are
// Copyright (C) 2000-@year@ by Thorsten Fiebig. All rights reserved.
// Code portions created by SMB are
// Copyright (C) 1997-@year@ by SMB GmbH. All rights reserved.
//
// $Id: DesignObjectImpl.di,v 1.1 2007-08-16 23:02:52 jed Exp $

package OO7;

import org.ozoneDB.OzoneObject;

public class DesignObjectImpl extends OzoneObject implements
    DesignObject {
  long theId;
  String theType;
  long theBuildDate;

  DesignObjectImpl() {
    theType = new String("");
  }

  public void setId(long x) {
    theId = x;
  }

  public long id() {
    return theId;
  }

  public void setType(String x) {
    theType = x;
  }

  public String type() {
    return theType;
  }

  public void setBuildDate(long x) {
    theBuildDate = x;
  }

  public long buildDate() {
    return theBuildDate;
  }
}
