
import java.applet.Applet;
import java.applet.AppletStub;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.security.KeyStore;
import java.util.Locale;
import java.util.Map;

import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.lang.FabricClassLoader;
import fabric.lang.security.Label;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.applet.FabricApplet;

/**
 * A container for {@link FabricApplet}s.  
 * Modeled on sun.applet.AppletPanel and AppletViewerPanel by Arthur van Hoff
 * 
 * @author Owen Arden
 */
public class FabricAppletPanel extends Applet implements AppletStub {
  
  /* This static block must run before the static block in ConfigProperties
   * that reads the fabric.prefix property.  This should always be the 
   * case for FabricAppletPanel since it is the main class.
   * 
   * XXX: Is there a better way to do this that also doesn't require setting 
   * VM arguments? */
  static {
    String home = System.getProperty("user.home");
    System.setProperty("fabric.prefix", home + File.separator + ".fabric");
  }
  /**
   * The applet (if loaded).
   */
  protected FabricApplet fabapp;
  protected Applet applet;
  /**
   * Applet will allow initialization. Should be set to false if loading a
   * serialized applet that was pickled in the init=true state.
   */
  protected boolean doInit = true;

  /**
   * The initial applet size.
   */
  protected Dimension defaultAppletSize = new Dimension(10, 10);

  /**
   * The current applet size.
   */
  protected Dimension currentAppletSize = new Dimension(10, 10);

  private Codebase getFabricCodeBase() {
    /*
     * if the codebase is specified, load codebase object directly.
     */

    String codebaseOid = getCodebaseOid();
    String classOid = getClassOid();

    if (codebaseOid != null) {
      /* get object */
      Object o = getObjectByOid(codebaseOid);
      if (o instanceof Codebase)
        return (Codebase) o;
      else throw new RuntimeException("codebaseOid is not a Codebase");
    }
    /*
     * else, fetch class object, and retrieve codebase from the class reference.
     */
    else if (classOid != null) {
      Object o = getObjectByOid(classOid);
      if (o instanceof FClass) {
        FClass cls = (FClass) o;
        return cls.getCodebase();
      } else throw new RuntimeException("classOid is not an FClass");
    } else throw new RuntimeException(
        "Cannot get codebase without codebaseOid or classOid");
  }

  private String getClassName() {
    return getParameter("className");
  }
  private String getClassOid() {
    return getParameter("classOid");
  }
  private String getObjectOid() {
    return getParameter("objectOid");
  }
  private String getCodebaseOid () {
    return getParameter("codebaseOid");
  }
  private String getWorkerName() {
    return getParameter("workerName");
  }

  private Label getLabel() {
    Label l = Worker.getWorker().getPrincipal().get$label();
    return l;
  }

  private String getStoreName() {
    return getParameter("storeName");
  }
  
  private Store getStore() {
    String storeName = getStoreName();
    if (storeName == null)
      throw new RuntimeException("Cannot get store without store name");

    Store store = Worker.getWorker().getStore(storeName);

    return store;
  }
  
  protected FabricClassLoader getClassLoader(Codebase codebase) {
    return FabricClassLoader.getClassLoader(codebase);
  }

  /**
   * Construct an applet viewer and start the applet.
   */
  @Override
  public void init() {
    try {
      // Get the width (if any)
      defaultAppletSize.width = getWidth();
      currentAppletSize.width = defaultAppletSize.width;

      // Get the height (if any)
      defaultAppletSize.height = getHeight();
      currentAppletSize.height = defaultAppletSize.height;

    } catch (NumberFormatException e) {
      // Turn on the error flag and let TagAppletPanel
      // do the right thing.

      showAppletException(e);
    }

    setLayout(new BorderLayout());

    try {
      Worker.initialize(getWorkerName());
    } catch (IllegalStateException ise) {
      // already initialized
    } catch (Exception e) {
      showAppletException(e);
    } catch (Error e) {
      showAppletException(e);
    }
    
    runLoader();

    fabapp.getApplet().resize(defaultAppletSize);
    if (doInit) {
      fabapp.getApplet().init();
    }

    // Need the default(fallback) font to be created in this AppContext
    Font f = getFont();
    if (f == null || "dialog".equals(f.getFamily().toLowerCase(Locale.ENGLISH))
        && f.getSize() == 12 && f.getStyle() == Font.PLAIN) {
      setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
    }

    doInit = true; // allow restarts
    validate();
  }

  @Override
  public void start() {
    /* always run init */
    if (fabapp == null)
      runLoader();
    
    fabapp.getApplet().resize(currentAppletSize);
    fabapp.getApplet().start();
    validate();
    fabapp.getApplet().setVisible(true);
  }

  @Override
  public void stop() {
    fabapp.getApplet().setVisible(false);
    fabapp.getApplet().stop();
  }

  @Override
  public void destroy() {
    remove(fabapp.getApplet());
    fabapp.getApplet().destroy();
    Worker.getWorker().shutdown();
  }

  /**
   * Load the applet into memory. Runs in a seperate (and interruptible) thread
   * from the rest of the applet event processing so that it can be gracefully
   * interrupted from things like HotJava.
   */
  private void runLoader() {
    /*
     * get codebase either from explicit parameter, or by fetching the class by
     * oid and finding its codebase. This demonstrates an improvement over the
     * Applet loader. The relationship with the source URL and codebases is
     * decoupled allow richer dependencies while at the same time providing
     * stronger security guarantees.
     */
    
    try {
      applet = loadApplet();
      if (applet != null) {
        // Stick it in the frame
        applet.setStub(this);
        applet.setVisible(false);
        add("Center", applet);
        validate();
      }
    } catch (ClassNotFoundException e) {
      showAppletException(e);
      return;
    } catch (Exception e) {
      showAppletException(e);
      return;
    } catch (Error e) {
      showAppletException(e);
      return;
    }
  }


  protected Applet loadApplet() throws ClassNotFoundException,
      IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, SecurityException, NoSuchMethodException {

    final String objectOid = getObjectOid();
    final String classOid = getClassOid();
    final String className = getClassName();
    Applet applet;
    
    if (classOid != null && className == null && objectOid == null) {
      FabricClassLoader loader = getClassLoader(getFabricCodeBase());

      Object o = getObjectByOid(classOid);
      if (!(o instanceof FClass))
        throw new RuntimeException("classOid is not an FClass." + o);

      Class cls = loader.findClass(((FClass) o).getName());

      if (FabricApplet.class.isAssignableFrom(cls)) {
        fabapp = createAppletInstance(cls);
        if (fabapp != null) {
          System.out.println("Created instance of " + cls.getName() 
              + " at " + getOid(fabapp));
          applet = fabapp.getApplet();          
        }
        else
          throw new RuntimeException("Could not instantiate FabricApplet");
        
      } else throw new RuntimeException("classOid is not a FabricApplet.");

      doInit = true;
    } else if (className != null && classOid == null && objectOid == null) {
      FabricClassLoader loader = getClassLoader(getFabricCodeBase());

      Class cls = loader.findClass(className);
      if (FabricApplet.class.isAssignableFrom(cls)) {
        fabapp = createAppletInstance(cls);
       
        if (fabapp != null) {
          System.out.println("Created instance of " + cls.getName() 
              + " at " + getOid(fabapp));
          applet = fabapp.getApplet();
        }
        else
          throw new RuntimeException("Could not instantiate FabricApplet");

      }
      else
        throw new RuntimeException("className is not a FabricApplet.");

      doInit = true;
    } else if (objectOid != null && classOid == null && className == null) {
      /* get object */
      Object o = getObjectByOid(objectOid);
      if (!(o instanceof FabricApplet))
        throw new RuntimeException("objectOid is not a FabricApplet." + o);
      
      fabapp = (FabricApplet)o;
      applet = fabapp.getApplet();      
      doInit = false; // skip over the first init
    } else throw new RuntimeException("Invalid parameter combination.");
    
    return applet;
  }

  private FabricApplet createAppletInstance(Class cls)
      throws SecurityException, NoSuchMethodException,
      IllegalArgumentException {

    final Label lbl = getLabel();
    final Store store = getStore();
    if (lbl == null || store == null) 
      throw new RuntimeException("Cannot create applet instance with label and store");

    FabricApplet fabapp = null;
    for (Class c : cls.getDeclaredClasses()) {
      if (fabric.lang.Object._Impl.class.isAssignableFrom(c)) {
        final Constructor ctor = c.getConstructor(Store.class, Label.class);
        fabapp = Worker.runInSubTransaction(new Worker.Code<FabricApplet>() {
          public FabricApplet run () {
            try {
              return (FabricApplet) ((FabricApplet._Impl)ctor.newInstance(
                    store, lbl)).$getProxy();
            } catch(Exception ex) { 
              showAppletException(ex);
              return null;
            }
          }
        });
        break;
      }
    }
    return fabapp;
  }
  
  /**
   * Return true when the applet has been started.
   */
  @Override
  public boolean isActive() {
    if (fabapp == null) return false;
    return fabapp.getApplet().isActive();
  }

  /**
   * Is called when the applet wants to be resized.
   */
  public void appletResize(int width, int height) {
    currentAppletSize.width = width;
    currentAppletSize.height = height;
    final Dimension currentSize =
        new Dimension(currentAppletSize.width, currentAppletSize.height);
    /* resize this applet */
    resize(currentSize);
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    super.setBounds(x, y, width, height);
    currentAppletSize.width = width;
    currentAppletSize.height = height;
  }

  public Applet getApplet() {
    if (fabapp == null) return null;
    return fabapp.getApplet();
  }
  
  public void paint(Graphics g) {
    // need to re-add applet object if fabapp made a 
    // new instance.
    if (fabapp != null && applet != fabapp.getApplet()) {
      applet = fabapp.getApplet();
      applet.setStub(this);
      applet.setVisible(false);
      removeAll();
      add("Center", applet);
      applet.setVisible(true);
      validate();
    }
  }
  
  /**
   * Provide feedback when an exception has happened.
   */
  protected void showAppletException(Throwable t) {
    t.printStackTrace();
    repaint();
  }

  private Object getObjectByOid(Store s, long onum) {
    return fabric.lang.Object._Proxy.$getProxy(new fabric.lang.Object._Proxy(s,
        onum));
  }

  private Object getObjectByOid(String oid) {
    String[] splits = oid.split("\\/");
    if (splits.length != 4)
      throw new IllegalArgumentException("Malformed oid: " + oid);

    return getObjectByOid(Worker.getWorker().getStore(splits[2]), Long
        .parseLong(splits[3]));
  }
  private String getOid(fabric.lang.Object obj) {
    return "fab://" + obj.$getStore().name() + "/" + obj.$getOnum();
  }
}
