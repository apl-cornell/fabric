// You can redistribute this software and/or modify it under the terms of
// the Ozone Core License version 1 published by ozone-db.org.
//
// The original code and portions created by Thorsten Fiebig are
// Copyright (C) 2000-@year@ by Thorsten Fiebig. All rights reserved.
// Code portions created by SMB are
// Copyright (C) 1997-@year@ by SMB GmbH. All rights reserved.
//
// $Id: ModuleImpl.di,v 1.3 2007-09-04 20:30:21 jed Exp $

package OO7;

import diaspora.util.Collection;
import diaspora.util.List;
import diaspora.util.LinkedList;

public class ModuleImpl extends DesignObjectImpl implements Module {
  Manual theManual;
  LinkedList theAssembly;
  ComplexAssembly theDesignRoot;

  public ModuleImpl() {
    theAssembly = new LinkedList();
  }

  public void setManual(Manual x) {
    theManual = x;
  }

  public Manual manual() {
    return theManual;
  }

  public void addAssembly(Assembly x) {
    theAssembly.addLast(x);
  }

  public Collection assembly() {
    return theAssembly;
  }

  public void setDesignRoot(ComplexAssembly x) {
    theDesignRoot = x;
  }

  public ComplexAssembly designRoot() {
    return theDesignRoot;
  }
}
