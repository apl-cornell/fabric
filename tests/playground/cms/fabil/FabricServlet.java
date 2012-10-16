
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.net.InetAddress;

import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.lang.FabricClassLoader;
import fabric.util.*;
import java.util.Date;
import java.util.Iterator;

import fabric.worker.Store;
import fabric.worker.Worker;

import java.text.ParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

public class FabricServlet extends HttpServlet {
  
  private HttpServlet delegate;
  
  
  /**
   * Initialize the Servlet
   * 
   * @param config
   *                Configuration info for the servlet
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    
    final String delOid = config.getInitParameter("servletoid");
    try {
      String name = config.getInitParameter("fabric.client");
      if (name == null)
        name = InetAddress.getLocalHost().getHostName();
      Worker.initialize(name);
    }
    catch(Exception ex) { 
      throw new ServletException(ex);
    }
    
    Worker.runInSubTransaction(new Worker.Code<Void>() {
      public Void run() {
        try {
          ClassLoader nLoader = this.getClass().getClassLoader();
          FClass c = (FClass)getObjectByOid(delOid);
          FabricClassLoader loader = FabricClassLoader.getClassLoader(c.getCodebase());
          final Class cls = loader.loadClass(c.getName());
          delegate = (HttpServlet)cls.newInstance();
        } catch(Exception ex) {
          throw new RuntimeException(ex);
        }
        return null;
      }
    });
    delegate.init(config);
  }
  
  public void service(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
    try {
      delegate.service(request, response);
    } catch(Exception ex) {
      throw new ServletException(ex);
    }
  }
  

  private Object getObjectByOid(Store s, long onum) {
    return fabric.lang.Object._Proxy.$getProxy(
        new fabric.lang.Object._Proxy(s, onum));
  }
  
  private Object getObjectByOid(String oid) {
    String[] splits = oid.split("\\/");
    if (splits.length != 4)
      throw new IllegalArgumentException("Malformed oid: " + oid);
    
    return getObjectByOid(Worker.getWorker().getStore(splits[2]), 
        Long.parseLong(splits[3]));
  }

}