// You can redistribute this software and/or modify it under the terms of
// the Ozone Core License version 1 published by ozone-db.org.
//
// The original code and portions created by Thorsten Fiebig are
// Copyright (C) 2000-@year@ by Thorsten Fiebig. All rights reserved.
// Code portions created by SMB are
// Copyright (C) 1997-@year@ by SMB GmbH. All rights reserved.
//
// $Id: ComplexAssemblyImpl.di,v 1.1 2007-08-16 23:02:52 jed Exp $

package OO7;

import java.util.Collection;
import java.util.List;
import java.util.LinkedList;

public class ComplexAssemblyImpl extends AssemblyImpl implements
    ComplexAssembly {
  List theSubAssemblies;

  public ComplexAssemblyImpl() {
    theSubAssemblies = new LinkedList();
  }

  public void addSubAssembly(Assembly x) {
    theSubAssemblies.addBack(x);
  }

  public Collection subAssemblies() {
    return theSubAssemblies;
  }
}
