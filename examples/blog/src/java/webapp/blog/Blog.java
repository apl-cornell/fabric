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
package webapp.blog;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;

public class Blog {

  private static final Blog instance = new Blog();

  private final Map<Integer, BlogPost> blogPosts;

  private final Map<Integer, Comment> comments;

  private Blog() {
    blogPosts = new TreeMap<Integer, BlogPost>(new Comparator<Integer>() {
      @Override
      public int compare(Integer o1, Integer o2) {
        return o2 - o1;
      }
    });
    comments = new HashMap<Integer, Comment>();
  }

  public void emptyDatabase() {
    Statistics.getInstance().registerUpdate(blogPosts);
    Statistics.getInstance().registerUpdate(comments);
    Statistics.getInstance().registerRead(this);
    blogPosts.clear();
    comments.clear();
  }

  public Collection<BlogPost> getBlogPosts() {
    Statistics.getInstance().registerRead(this);
    return Collections.unmodifiableCollection(blogPosts.values());
  }

  public Collection<Comment> getComments() {
    Statistics.getInstance().registerRead(this);
    return Collections.unmodifiableCollection(comments.values());
  }

  public void addBlogPost(BlogPost post) {
    Statistics.getInstance().registerRead(this);
    Statistics.getInstance().registerUpdate(blogPosts);
    blogPosts.put(post.getId(), post);
  }

  public void indexComment(Comment c) {
    Statistics.getInstance().registerRead(this);
    Statistics.getInstance().registerUpdate(comments);
    comments.put(c.getId(), c);
  }

  public void unindexComment(Comment c) {
    Statistics.getInstance().registerRead(this);
    Statistics.getInstance().registerUpdate(comments);
    comments.remove(c.getId());
  }

  public static Blog getInstance() {
    return instance;
  }

  public void removeBlogPost(BlogPost p) {
    Statistics.getInstance().registerRead(this);
    Statistics.getInstance().registerUpdate(blogPosts);
    Statistics.getInstance().registerUpdate(comments);
    blogPosts.remove(p.getId());
    for (Comment c : p.getComments())
      comments.remove(c.getId());
  }

  public BlogPost getBlogPost(int id) {
    Statistics.getInstance().registerRead(this);
    return blogPosts.get(id);
  }

  public Comment getComment(int id) {
    Statistics.getInstance().registerRead(this);
    return comments.get(id);
  }

}
