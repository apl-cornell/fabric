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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BlogPost implements Comparable<BlogPost> {

  private String content;
  private String title;
  private final Date creationTime;
  private int views;
  private final int id;
  private final List<Comment> comments;
  private static int counter = 0;

  public BlogPost(String title, String content) {
    Statistics.getInstance().registerCreate();
    this.content = content;
    this.title = title;
    creationTime = new Date();
    views = 0;
    comments = new ArrayList<Comment>();
    id = ++counter;
  }

  @Override
  public int compareTo(BlogPost o) {
    Statistics.getInstance().registerRead(this);
    return o.id - id;
  }

  public String getContent() {
    Statistics.getInstance().registerRead(this);
    return content;
  }

  public void setContent(String content) {
    Statistics.getInstance().registerUpdate(this);
    this.content = content;
  }

  public Date getCreationTime() {
    Statistics.getInstance().registerRead(this);
    return creationTime;
  }

  public int getViews() {
    Statistics.getInstance().registerRead(this);
    return views;
  }

  public void incrementViews() {
    Statistics.getInstance().registerUpdate(this);
    views++;
  }

  public void setTitle(String title) {
    Statistics.getInstance().registerUpdate(comments);
    this.title = title;
  }

  public String getTitle() {
    Statistics.getInstance().registerRead(this);
    return title;
  }

  public void addComment(Comment c) {
    Statistics.getInstance().registerUpdate(comments);
    comments.add(c);
  }

  public List<Comment> getComments() {
    Statistics.getInstance().registerRead(this);
    return Collections.unmodifiableList(comments);
  }

  public void removeComment(Comment c) {
    Statistics.getInstance().registerUpdate(comments);
    comments.remove(c);
  }

  public int getId() {
    Statistics.getInstance().registerRead(this);
    return id;
  }

}
