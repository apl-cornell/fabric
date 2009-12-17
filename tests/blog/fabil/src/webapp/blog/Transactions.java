package webapp.blog;

public class Transactions {

  public static BlogPost addBlogPost(String title, String content, Core c, Label l)
      throws TransactionFailure {
    if (content == null)
      throw new TransactionFailure("content is null");
    try {
      BlogPost post = null;
      long start = 0;
      atomic {
        post = new BlogPost~l@c(title, content);
        Blog.getInstance().addBlogPost(post);
        start = System.currentTimeMillis();
      }
      Statistics.getInstance().addTransaction(System.currentTimeMillis() - start);
      return post;
    } catch (RuntimeException ex) {
      throw new TransactionFailure("could not add blog post", ex);
    }
  }

  public static Comment addComment(String username, String content,
      BlogPost post, Core core, Label l) throws TransactionFailure {
    if (username == null || content == null)
      throw new TransactionFailure("username or content is null");
    try {
      Comment c = null;
      long start = 0;
      atomic {
        c = new Comment~l@core(username, content, post);
        post.addComment(c);
        Blog.getInstance().indexComment(c);
        start = System.currentTimeMillis();
      }
      Statistics.getInstance().addTransaction(System.currentTimeMillis() - start);
      return c;
    } catch (RuntimeException ex) {
      throw new TransactionFailure("could not add comment", ex);
    }
  }

  public static void deleteComment(Comment c) throws TransactionFailure {
    try {
      long start = 0;
      atomic {
        c.getBlogPost().removeComment(c);
        start = System.currentTimeMillis();
      }
      Statistics.getInstance().addTransaction(System.currentTimeMillis() - start);
    } catch (RuntimeException ex) {
      throw new TransactionFailure("could not delete comment", ex);
    }
  }

  public static void deletePost(BlogPost post) throws TransactionFailure {
    try {
      long start = 0;
      atomic {
        Blog.getInstance().removeBlogPost(post);
        start = System.currentTimeMillis();
      }
      Statistics.getInstance().addTransaction(System.currentTimeMillis() - start);
    } catch (RuntimeException ex) {
      throw new TransactionFailure("could not delete post", ex);
    }
  }

}
