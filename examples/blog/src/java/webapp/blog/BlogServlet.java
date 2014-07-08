/**
 * Copyright (C) 2010 Fabric project group, Cornell University
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

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webapp.PageLoader;
import webapp.Action;

public class BlogServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  private static final String PAGE_POSTS = "/jsp/posts.jsp";
  private static final String PAGE_VIEWPOST = "/jsp/viewpost.jsp";
  private static final String PAGE_CREATEPOST = "/jsp/newpost.jsp";

  public BlogServlet() {
    try {
      Diagnostics.createDatabase();
    } catch (TransactionFailure ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Statistics.getInstance().newPage();
    String action = request.getParameter("action");
    String jsp = PAGE_POSTS;

    if (request.getParameter("clientaction") != null) {
      handleClientAction(request, response);
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
    }

    prepareDebugMenu(request);
    Statistics.getInstance().doneApp();
    showJsp(request, response, jsp);
  }

  private void handleClientAction(HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    request.setAttribute("debug", true);
    String clientAction = request.getParameter("clientaction");
    if (clientAction.equals("connect")) {
      Statistics.addClient(request.getRemoteAddr() + ":"
          + request.getParameter("port"));
      response.getOutputStream().print("ok");
    } else if (clientAction.equals("removeother")) {
      Statistics.removeClient(request.getParameter("id"));
      redirect(response, "/web");
    } else if (clientAction.equals("removeme")) {
      Statistics.removeClient(request.getRemoteAddr() + ":"
          + request.getParameter("port"));
    } else if (clientAction.equals("done")) {
      Statistics.setClientDone(request.getRemoteAddr() + ":"
          + request.getParameter("port"));
    } else if (clientAction.equals("error")) {
      Statistics.setClientError();
    }

  }

  private void handleDebugAction(HttpServletRequest request) {
    String debugAction = request.getParameter("debugaction");
    request.setAttribute("debug", true);
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
        Statistics.unsetClientError();
        int numClients = Integer.parseInt(request.getParameter("numclients"));
        String action = request.getParameter("action");
        int requests = Integer.parseInt(request.getParameter("size"));
        String[] clients = new String[Statistics.getClients().size()];
        clients = Statistics.getClients().toArray(clients);
        for (int i = 0; i < numClients; i++) {
          Statistics.setBusyClient(clients[i]);
          PageLoader.getPage("http://" + clients[i]
              + "/web?action=start&daction=" + action + "&requests=" + requests);
        }
      } catch (IOException ex) {
        Statistics.removeAllBusyClients();
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
          request.getParameter("comment"), post);
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
    request.setAttribute("clients", Statistics.getClients());
    request.setAttribute("actions", Action.values());
    request.setAttribute("busyclients", Statistics.getBusyClients());
    request.setAttribute("clientError", Statistics.getClientError());
    request.setAttribute("statistics", Statistics.getInstance());
  }

  private void handleDeleteComment(HttpServletRequest request) {

  }

  private boolean handleCreatePost(HttpServletRequest request) {
    if (request.getParameter("title") != null) {
      String title = request.getParameter("title");
      String content = request.getParameter("content");
      try {
        Transactions.addBlogPost(title, content);
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
    request.setAttribute("entry", Blog.getInstance().getBlogPost(
        Integer.parseInt(request.getParameter("id"))));
    return PAGE_VIEWPOST;
  }

  private String showPosts(HttpServletRequest request) {
    request.setAttribute("posts", Blog.getInstance().getBlogPosts());
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
