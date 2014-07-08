/**
 * Copyright (C) 2010-2012 Fabric project group, Cornell University
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
package webapp.blog;

import java.util.Date;

public class Comment {

  private final String username;
  private String content;
  private final Date creationTime;
  private final int id;
  private static int counter = 0;
  private final BlogPost post;

  public Comment(String username, String content, BlogPost post) {
    Statistics.getInstance().registerCreate();
    this.username = username;
    this.content = content;
    this.post = post;
    creationTime = new Date();
    id = ++counter;
  }

  public String getUsername() {
    Statistics.getInstance().registerRead(this);
    return username;
  }

  public String getContent() {
    Statistics.getInstance().registerRead(this);
    return content;
  }

  public void setContent(String v) {
    Statistics.getInstance().registerUpdate(this);
    content = v;
  }

  public Date getCreationTime() {
    Statistics.getInstance().registerRead(this);
    return creationTime;
  }

  public int getId() {
    Statistics.getInstance().registerRead(this);
    return id;
  }

  public BlogPost getBlogPost() {
    Statistics.getInstance().registerRead(this);
    return post;
  }

}
