// You can redistribute this software and/or modify it under the terms of
// the Ozone Core License version 1 published by ozone-db.org.
//
// The original code and portions created by Thorsten Fiebig are
// Copyright (C) 2000-@year@ by Thorsten Fiebig. All rights reserved.
// Code portions created by SMB are
// Copyright (C) 1997-@year@ by SMB GmbH. All rights reserved.
//
// $Id: AtomicPart.di,v 1.5 2007-09-04 20:30:21 jed Exp $

package OO7;

import diaspora.util.Collection;

public interface AtomicPart extends DesignObject {
  void setX(long x);

  long x();

  void setY(long y);

  long y();

  void setDocId(long y);

  long docId();

  void addTo(Connection x);

  Collection to();

  void addFrom(Connection x);

  Collection from();

  void setPartOf(CompositePart x);

  CompositePart partOf();
}
