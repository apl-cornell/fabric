package webapp.blog;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fabric.util.Iterator;
import fabric.util.Collection;

import fabric.lang.security.Label;

import webapp.PageLoader;
import webapp.Action;

public class BlogServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  private static final String PAGE_POSTS = "/jsp/posts.jsp";
  private static final String PAGE_VIEWPOST = "/jsp/viewpost.jsp";
  private static final String PAGE_CREATEPOST = "/jsp/newpost.jsp";
  
  private LocalStore localStore;
  private Store store;
  private Label currentLabel;
  
  public BlogServlet() {
    localStore = Diagnostics.getLocalStore();
    store = Diagnostics.getStore();
    currentLabel = Diagnostics.getCurrentLabel();

    atomic {
      Statistics.getInstance().newPage(localStore, currentLabel);
    }
    
    try {
      Diagnostics.createDatabase();
    } catch (TransactionFailure ex) {
      throw new RuntimeException(ex);
    }
  }

  public void service(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
    
      serviceHandler(request, response);

  }
  
  private void serviceHandler(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    long start = 0;
    String action = request.getParameter("action");
    String jsp = PAGE_POSTS;

    atomic {
      Statistics.getInstance().newPage(localStore, currentLabel);
      if (request.getParameter("workeraction") != null) {
        handleWorkerAction(request, response);
        return;
      }
  
      if (request.getParameter("debugaction") != null) {
        handleDebugAction(request);
        action = null;
      }
  
      if (action == null) {
        jsp = showPosts(request);
      } else if (action.equals("viewpost")) {
        jsp = showViewPost(request);
      } else if (action.equals("createpost")) {
        if (handleCreatePost(request)) {
          jsp = showPosts(request);
        } else {
          jsp = showCreatePost(request);
        }
      } else if (action.equals("deletecomment")) {
        handleDeleteComment(request);
        jsp = showViewPost(request);
      } else if (action.equals("deletepost")) {
        handleDeletePost(request);
        jsp = showPosts(request);
      } else if (action.equals("postcomment")) {
        handlePostComment(request);
        jsp = showViewPost(request);
      } else if (action.equals("massupdate")) {
        handleMassUpdate(request);
        jsp = showPosts(request);
      }
  
      prepareDebugMenu(request);
      Statistics.getInstance().doneApp();
      start = System.currentTimeMillis();
    }
    Statistics.getInstance().addTransaction(System.currentTimeMillis() - start);
    showJsp(request, response, jsp);
  }

  private void handleMassUpdate(HttpServletRequest request) {
    long start = 0;
    atomic {
      Collection posts = Blog.getInstance().getBlogPosts();
      for(Iterator i = posts.iterator(); i.hasNext();) {
        BlogPost post = (BlogPost)i.next();
        Collection comments = post.getComments();
        for(Iterator j = comments.iterator(); j.hasNext();) {
          Comment c = (Comment)j.next();
          if(c.getContent().contains("comment")) {
            c.setContent(c.getContent().replace("comment", "blag"));
            post.setTitle("Replaced");
          } else if(c.getContent().contains("blag")) {
            c.setContent(c.getContent().replace("blag", "comment"));
            post.setTitle("Reverted");
          }
        }
      }
      start = System.currentTimeMillis();
    }
    Statistics.getInstance().addTransaction(System.currentTimeMillis() - start);
  }
  
  private void handleWorkerAction(HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    atomic {
      request.setAttribute("debug", new Boolean~currentLabel@localStore(true));
    }
    String workerAction = request.getParameter("workeraction");
    if (workerAction.equals("connect")) {
      Statistics.addWorker(request.getRemoteAddr() + ":"
          + request.getParameter("port"));
      response.getOutputStream().print("ok");
    } else if (workerAction.equals("removeother")) {
      Statistics.removeWorker(request.getParameter("id"));
      redirect(response, "/web");
    } else if (workerAction.equals("removeme")) {
      Statistics.removeWorker(request.getRemoteAddr() + ":"
          + request.getParameter("port"));
    } else if (workerAction.equals("done")) {
      Statistics.setWorkerDone(request.getRemoteAddr() + ":"
          + request.getParameter("port"));
    } else if (workerAction.equals("error")) {
      Statistics.setWorkerError();
    }

  }

  private void handleDebugAction(HttpServletRequest request) {
    String debugAction = request.getParameter("debugaction");
    atomic {
      request.setAttribute("debug", new Boolean~currentLabel@localStore(true));
    }
    if (debugAction.equals("recreate")) {
      try {
        Diagnostics.createDatabase();
        request.setAttribute("passmsg", "DB recreated");
      } catch (TransactionFailure ex) {
        request.setAttribute("errormsg", "Could not recreate db");
        ex.printStackTrace();
      }
    } else if (debugAction.equals("addposts")) {
      // TODO
    } else if (debugAction.equals("benchmark")) {
      try {
        Statistics.unsetWorkerError();
        int numWorkers = Integer.parseInt(request.getParameter("numworkers"));
        String action = request.getParameter("action");
        int requests = Integer.parseInt(request.getParameter("size"));
        atomic {
          String[] workers = new String[Statistics.getWorkers().size()]~currentLabel@localStore;
          workers = (String[])Statistics.getWorkers().toArray(workers);
          for (int i = 0; i < numWorkers; i++) {
            Statistics.setBusyWorker(workers[i]);
            PageLoader.getPage("http://" + workers[i]
                + "/web?action=start&daction=" + action + "&requests=" + requests);
          }
        }
      } catch (Exception ex) {
        Statistics.removeAllBusyWorkers();
        request.setAttribute("errormsg", "Could not initiate benchmark: "
            + ex.getMessage());
        ex.printStackTrace();
      }
    } else if (debugAction.equals("reload")) {
      // ...
    }
  }

  private void handlePostComment(HttpServletRequest request) {
    if (request.getParameter("id") == null
        || request.getParameter("id").equals(""))
      return;
    BlogPost post = Blog.getInstance().getBlogPost(
        Integer.parseInt(request.getParameter("id")));
    try {
      Transactions.addComment(request.getParameter("name"),
          request.getParameter("comment"), post, store, currentLabel);
      request.setAttribute("passmsg", "Comment posted");
    } catch (TransactionFailure ex) {
      request.setAttribute("errormsg", "Could not post comment");
      ex.printStackTrace();
    }
  }

  private void handleDeletePost(HttpServletRequest request) {
    if (request.getParameter("id") == null
        || request.getParameter("id").equals(""))
      return;

    try {
      BlogPost post = Blog.getInstance().getBlogPost(
          Integer.parseInt(request.getParameter("id")));
      Transactions.deletePost(post);
      request.setAttribute("passmsg", "Blog post deleted");
    } catch (TransactionFailure ex) {
      request.setAttribute("errormsg", "Could not delete blog post");
      ex.printStackTrace();
    }

  }

  private void prepareDebugMenu(HttpServletRequest request) {
    request.setAttribute("blog", Blog.getInstance());
    request.setAttribute("workers", Statistics.getWorkers());
    request.setAttribute("actions", Action.values());
    request.setAttribute("busyworkers", Statistics.getBusyWorkers());
    java.util.LinkedList list = new java.util.LinkedList();
    for(Iterator iter = Statistics.getWorkers().iterator(); iter.hasNext();) {
      list.add(iter.next());
    }
    request.setAttribute("workerslist", list);
    atomic {
      request.setAttribute("workerError", new Boolean~currentLabel@localStore(
          Statistics.getWorkerError()));
    }
    request.setAttribute("statistics", Statistics.getInstance());
  }

  private void handleDeleteComment(HttpServletRequest request) {

  }

  private boolean handleCreatePost(HttpServletRequest request) {
    if (request.getParameter("title") != null) {
      String title = request.getParameter("title");
      String content = request.getParameter("content");
      try {
        Transactions.addBlogPost(title, content, store, currentLabel);
        return true;
      } catch (TransactionFailure ex) {
        ex.printStackTrace();
        request.setAttribute("errormsg", "Could not create new blog post");
      }
    }
    return false;
  }

  private String showCreatePost(HttpServletRequest request) {
    return PAGE_CREATEPOST;
  }

  private String showViewPost(HttpServletRequest request) {
    if (request.getParameter("id") == null)
      return showPosts(request);
    BlogPost p = Blog.getInstance().getBlogPost(
        Integer.parseInt(request.getParameter("id")));
    request.setAttribute("entry", p);
    java.util.LinkedList list = new java.util.LinkedList();
    for(Iterator iter = p.getComments().iterator(); iter.hasNext();) {
      list.add(iter.next());
    }
    request.setAttribute("comments", list);
    
    return PAGE_VIEWPOST;
  }

  private String showPosts(HttpServletRequest request) {
    java.util.LinkedList posts = new java.util.LinkedList();
    fabric.util.Iterator iter = Blog.getInstance().getBlogPosts().iterator();
    int size = Blog.getInstance().getBlogPosts().size();
    int start = 0;
    if(request.getParameter("start") != null)
      start = Integer.parseInt(request.getParameter("start"));
    int end = 3;
    if(request.getParameter("end") != null)
      end = Integer.parseInt(request.getParameter("end"));
    for(int j = 0; j < start; j++) 
      iter.next();
    for(int i = start; i <= end && i < size && i >= 0; i++) {
      posts.add(iter.next());
    }
    request.setAttribute("posts", posts);
    atomic {
      request.setAttribute("numPosts", new Integer~currentLabel@localStore(size));
    }
    return PAGE_POSTS;
  }

  private void redirect(HttpServletResponse response, String url)
      throws IOException {
    response.sendRedirect(url);
  }

  private void showJsp(HttpServletRequest request,
      HttpServletResponse response, String jsp) throws ServletException,
      IOException {
    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jsp);
    dispatcher.forward(request, response);

  }

}
