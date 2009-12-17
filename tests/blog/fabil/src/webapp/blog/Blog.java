package webapp.blog;

import fabric.util.Collection;
import fabric.util.Collections;
import fabric.util.Comparator;
import fabric.util.HashMap;
import fabric.util.TreeMap;
import fabric.util.Map;

import fabric.util.Iterator;

public class Blog {

  private static Blog instance;

  private final Map/*Integer, BlogPost*/ blogPosts;

  private final Map/*Integer, Comment*/ comments;

  private Blog() {
    blogPosts = new TreeMap/*Integer, BlogPost*/(new Comparator/*Integer*/() {
      public int compare(Object o1, Object o2) {
        return ((Integer)o2).intValue() - ((Integer)o1).intValue();
      }
    });
    comments = new HashMap/*Integer, Comment*/();
  }

  public void emptyDatabase() {
    Statistics.getInstance().registerRead(this);
    blogPosts.clear();
    comments.clear();
  }

  public Collection/*BlogPost*/ getBlogPosts() {
    Statistics.getInstance().registerRead(this);
    return Collections.unmodifiableCollection(blogPosts.values());
  }

  public Collection/*Comment*/ getComments() {
    Statistics.getInstance().registerRead(this);
    return Collections.unmodifiableCollection(comments.values());
  }

  public void addBlogPost(BlogPost post) {
    Statistics.getInstance().registerRead(this);
    Statistics.getInstance().registerUpdate(blogPosts);
    blogPosts.put(new Integer(post.getId()), post);
  }

  public void indexComment(Comment c) {
    Statistics.getInstance().registerRead(this);
    comments.put(new Integer(c.getId()), c);
  }

  public void unindexComment(Comment c) {
    Statistics.getInstance().registerRead(this);
    comments.remove(new Integer(c.getId()));
  }
  
  public int getNumBlogPosts() {
    atomic {
      return blogPosts.size();
    }
  }
  
  public int getNumComments() {
    atomic {
      return comments.size();
    }
  }

  public static Blog getInstance() {
    return instance;
  }
  
  public static void createNewInstance(Core c, Label l) {
    instance = new Blog~l@c();
  }
  
  public static void setInstance(Blog b) {
    instance = b;
  }

  public void removeBlogPost(BlogPost p) {
    Statistics.getInstance().registerRead(this);
    blogPosts.remove(new Integer(p.getId()));
    for (Iterator i = p.getComments().iterator(); i.hasNext(); ) {
      Comment c = (Comment)i.next();
      comments.remove(new Integer(c.getId()));
    }
  }

  public BlogPost getBlogPost(int id) {
    Statistics.getInstance().registerRead(this);
    return (BlogPost)blogPosts.get(new Integer(id));
  }

  public Comment getComment(int id) {
    Statistics.getInstance().registerRead(this);
    return (Comment)comments.get(new Integer(id));
  }

}
