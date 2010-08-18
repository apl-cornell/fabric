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
