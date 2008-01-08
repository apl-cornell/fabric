// You can redistribute this software and/or modify it under the terms of
// the Ozone Core License version 1 published by ozone-db.org.
//
// The original code and portions created by Thorsten Fiebig are
// Copyright (C) 2000-@year@ by Thorsten Fiebig. All rights reserved.
// Code portions created by SMB are
// Copyright (C) 1997-@year@ by SMB GmbH. All rights reserved.
//
// $Id: Assembly.java,v 1.1 2008-01-08 18:38:02 mdgeorge Exp $

package OO7;

public abstract class Assembly extends DesignObject {
  ComplexAssembly superAssembly;
  Module          module;

  public Assembly() {
  }

  public void setSuperAssembly(ComplexAssembly x) {
    superAssembly = x;
  }

  public ComplexAssembly superAssembly() {
    return superAssembly;
  }

  public void setModule(Module x) {
    module = x;
  }

  public Module module() {
    return module;
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
