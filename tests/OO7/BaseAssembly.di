// You can redistribute this software and/or modify it under the terms of
// the Ozone Core License version 1 published by ozone-db.org.
//
// The original code and portions created by Thorsten Fiebig are
// Copyright (C) 2000-@year@ by Thorsten Fiebig. All rights reserved.
// Code portions created by SMB are
// Copyright (C) 1997-@year@ by SMB GmbH. All rights reserved.
//
// $Id: BaseAssembly.di,v 1.4 2007-09-04 20:30:21 jed Exp $

package OO7;

import diaspora.util.Collection;

public interface BaseAssembly extends Assembly {
  void addComponentsPriv(CompositePart x);

  Collection componetsPriv();

  void addComponentsShar(CompositePart x);

  Collection componentsShar();
}
