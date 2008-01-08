// You can redistribute this software and/or modify it under the terms of
// the Ozone Core License version 1 published by ozone-db.org.
//
// The original code and portions created by Thorsten Fiebig are
// Copyright (C) 2000-@year@ by Thorsten Fiebig. All rights reserved.
// Code portions created by SMB are
// Copyright (C) 1997-@year@ by SMB GmbH. All rights reserved.
//
// $Id: Module.java,v 1.1 2008-01-08 18:38:02 mdgeorge Exp $

package OO7;

import java.util.Collection;
import java.util.List;
import java.util.LinkedList;

public class Module extends DesignObject {
  Manual           man;
  LinkedList/*Assembly*/ assemblies;
  ComplexAssembly  designRoot;

  public Module() {
    assemblies = new LinkedList();
  }

  public void setManual(Manual x) {
    man = x;
  }

  public Manual manual() {
    return man;
  }

  public void addAssembly(Assembly x) {
    assemblies.addLast(x);
  }

  public Collection assembly() {
    return assemblies;
  }

  public void setDesignRoot(ComplexAssembly x) {
    designRoot = x;
  }

  public ComplexAssembly designRoot() {
    return designRoot;
  }

  public void accept(Traversal t) {
    t.visitModule(this);
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
