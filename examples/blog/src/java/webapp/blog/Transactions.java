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

public class Transactions {

  public static BlogPost addBlogPost(String title, String content)
      throws TransactionFailure {
    if (content == null)
      throw new TransactionFailure("content is null");
    try {
      BlogPost post = new BlogPost(title, content);
      Blog.getInstance().addBlogPost(post);
      Statistics.getInstance().addTransaction(0);
      return post;
    } catch (RuntimeException ex) {
      throw new TransactionFailure("could not add blog post", ex);
    }
  }

  public static Comment addComment(String username, String content,
      BlogPost post) throws TransactionFailure {
    if (username == null || content == null)
      throw new TransactionFailure("username or content is null");
    try {
      Comment c = new Comment(username, content, post);
      post.addComment(c);
      Blog.getInstance().indexComment(c);
      Statistics.getInstance().addTransaction(0);
      return c;
    } catch (RuntimeException ex) {
      throw new TransactionFailure("could not add comment", ex);
    }
  }

  public static void deleteComment(Comment c) throws TransactionFailure {
    try {
      c.getBlogPost().removeComment(c);
      Statistics.getInstance().addTransaction(0);
    } catch (RuntimeException ex) {
      throw new TransactionFailure("could not delete comment", ex);
    }
  }

  public static void deletePost(BlogPost post) throws TransactionFailure {
    try {
      Blog.getInstance().removeBlogPost(post);
      Statistics.getInstance().addTransaction(0);
    } catch (RuntimeException ex) {
      throw new TransactionFailure("could not delete post", ex);
    }
  }

}
