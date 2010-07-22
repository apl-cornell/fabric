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
