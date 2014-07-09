/**
 * Copyright (C) 2010-2014 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
// You can redistribute this software and/or modify it under the terms of
// the Ozone Core License version 1 published by ozone-db.org.
//
// The original code and portions created by Thorsten Fiebig are
// Copyright (C) 2000-@year@ by Thorsten Fiebig. All rights reserved.
// Code portions created by SMB are
// Copyright (C) 1997-@year@ by SMB GmbH. All rights reserved.
//

package OO7;

public class Document {
  String title;
  int id;
  char[] text;

  CompositePart part;

  public Document(Benchmark db, int size) {
    this.id = db.newId();
    db.documentsById.put(new Integer(id()), this);

	 this.title = db.randomString(16);
	 this.text = db.randomChars(size);
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String title() {
    return this.title;
  }

  public int id() {
    return this.id;
  }

  public void setText(char[] text) {
    this.text = text;
  }

  public char[] text() {
    return this.text;
  }
}

/*
 * * vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
 */
