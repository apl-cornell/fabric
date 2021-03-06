package webapp.blog;

public class Diagnostics {

  public static final int NUM_INITIAL_BLOGS = 1000;
  public static final int NUM_INITLAL_COMMENTS = 10;

  public static void createDatabase() throws TransactionFailure {
    Blog.getInstance().emptyDatabase();
    addBlogsAndComments(NUM_INITIAL_BLOGS, NUM_INITLAL_COMMENTS);
  }

  private static String duplicateString(String msg, int n) {
    StringBuilder b = new StringBuilder();
    for (int i = 0; i < n; i++) {
      b.append(msg);
    }
    return b.toString();
  }

  public static void addBlogs(int n) throws TransactionFailure {
    for (int i = 0; i < n; i++) {
      Transactions.addBlogPost("Title", duplicateString(
          "This is a blog post. ", 50));
    }
  }

  public static void addComments(BlogPost p, int m) throws TransactionFailure {
    for (int i = 0; i < m; i++) {
      Transactions.addComment("username", duplicateString(
          "This is a comment. ", 10), p);
    }
  }

  public static void addBlogsAndComments(int n, int m)
      throws TransactionFailure {
    for (int i = 0; i < n; i++) {
      BlogPost p = Transactions.addBlogPost("Title", duplicateString(
          "This is a blog post. ", 50));
      for (int j = 0; j < m; j++) {
        Transactions.addComment("username", duplicateString(
            "This is a comment. ", 10), p);
      }
    }
  }

}
