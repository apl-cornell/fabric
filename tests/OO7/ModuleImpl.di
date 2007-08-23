// You can redistribute this software and/or modify it under the terms of
// the Ozone Core License version 1 published by ozone-db.org.
//
// The original code and portions created by Thorsten Fiebig are
// Copyright (C) 2000-@year@ by Thorsten Fiebig. All rights reserved.
// Code portions created by SMB are
// Copyright (C) 1997-@year@ by SMB GmbH. All rights reserved.
//
// $Id: ModuleImpl.di,v 1.2 2007-08-23 17:09:18 jed Exp $

package OO7;

import OO7.util.Collection;
import OO7.util.List;
import OO7.util.LinkedList;

public class ModuleImpl extends DesignObjectImpl implements Module {
  Manual theManual;
  List theAssembly;
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
    theAssembly.addBack(x);
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
