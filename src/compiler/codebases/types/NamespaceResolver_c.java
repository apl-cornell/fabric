package codebases.types;

import java.io.InvalidClassException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import polyglot.frontend.FileSource;
import polyglot.frontend.Job;
import polyglot.frontend.MissingDependencyException;
import polyglot.frontend.Scheduler;
import polyglot.frontend.SchedulerException;
import polyglot.frontend.Source;
import polyglot.frontend.goals.Goal;
import polyglot.main.Report;
import polyglot.main.Version;
import polyglot.types.BadSerializationException;
import polyglot.types.ClassType;
import polyglot.types.Importable;
import polyglot.types.NoClassException;
import polyglot.types.Package;
import polyglot.types.ParsedTypeObject;
import polyglot.types.SemanticException;
import polyglot.types.TypeObject;
import polyglot.types.reflect.ClassFile;
import polyglot.util.CollectionUtil;
import polyglot.util.InternalCompilerError;
import polyglot.util.ObjectDumper;
import polyglot.util.SimpleCodeWriter;
import polyglot.util.StringUtil;
import polyglot.util.TypeEncoder;
import codebases.frontend.CodebaseSource;
import codebases.frontend.ExtensionInfo;
import fabric.lang.Codebase;
import fabric.lang.security.Label;
import fabric.lang.security.LabelUtil;
import fabric.lang.security.NodePrincipal;
import fabric.worker.Store;
import fabric.worker.Worker;

// /TODO: Disentangle the ideas of classpath and class cache/output directory
public abstract class NamespaceResolver_c implements NamespaceResolver {
  private static final Collection<String> TOPICS = CollectionUtil.list(
      Report.types, Report.resolver);
  protected static final List<String> REPORT_TOPICS = Arrays
      .asList(new String[] { Report.types, Report.resolver, Report.loader });
  protected static final int NOT_COMPATIBLE = -1;
  protected static final int MINOR_NOT_COMPATIBLE = 1;
  protected static final int COMPATIBLE = 0;
  protected final URI namespace;
  protected final ExtensionInfo extInfo;
  protected final TypeEncoder te;
  protected final NamespaceResolver parent;

  // /////////////////////////////////////////////////////////////////////////
  // Caches
  // /////////////////////////////////////////////////////////////////////////

  /** packageExists == true cache */
  protected Set<String> packages;
  /** packageExists == false cache */
  protected Set<String> noPackage;
  /** type cached */
  protected Map<String, Importable> cache;
  /** class not found cache */
  protected Map<String, SemanticException> notFound;

  /** alias cache */
  protected final Map<String, URI> aliasCache;
  /** no such alias cache */
  protected Set<String> noAlias;

  protected Label integrity;

  public NamespaceResolver_c(ExtensionInfo extInfo, URI namespace) {
    this(extInfo, namespace, null);
  }

  public NamespaceResolver_c(ExtensionInfo extInfo, URI namespace,
      NamespaceResolver parent) {
    this(extInfo, namespace, parent, Collections.<String, URI> emptyMap());
  }

  public NamespaceResolver_c(ExtensionInfo extInfo, URI namespace,
      NamespaceResolver parent, Map<String, URI> aliases) {
    this.cache = new HashMap<>();
    this.packages = new HashSet<>();
    this.noPackage = new HashSet<>();
    this.notFound = new HashMap<>();
    // A namespace URI must end with a '/' so we can properly
    // compare URI and create new ones using resolve()
    if (!namespace.isOpaque() //&& !namespace.isFileReference()
        && !namespace.getPath().endsWith("/"))
      throw new InternalCompilerError("Malformed namespace: " + namespace);
    this.namespace = namespace;
    this.extInfo = extInfo;
    this.te = extInfo.typeEncoder();
    this.parent = parent;
    this.aliasCache = new HashMap<>(aliases);
    this.noAlias = new HashSet<>();
  }

  @Override
  public final boolean packageExists(String name) {
    if (packages.contains(name))
      return true;
    else if (noPackage.contains(name))
      return false;
    else {
      URI aliasNS = null;
      if (!StringUtil.isNameShort(name)) {
        // First check if name uses a codebase alias.
        String first = StringUtil.getFirstComponent(name);
        aliasNS = resolveCodebaseName(first);
      }
      if (aliasNS != null) {
        CodebaseTypeSystem ts = extInfo.typeSystem();
        NamespaceResolver nr = ts.namespaceResolver(aliasNS);
        String pkg = StringUtil.removeFirstComponent(name);
        boolean res;
        if (!"".equals(pkg))
          res = nr.packageExists(pkg);
        else res = true;

        if (res)
          packages.add(name);
        else noPackage.add(name);
      }

      if (packageExistsImpl(name)) {
        packages.add(name);
        return true;
      } else {
        noPackage.add(name);
        return false;
      }
    }
  }

  @Override
  public final Importable find(String name) throws SemanticException {

    if (Report.should_report(TOPICS, 2))
      Report.report(2, "[" + namespace + "] " + "NamespaceResolver_c: find: "
          + name);

    Importable q = cache.get(name);

    if (q == null) {
      SemanticException se = notFound.get(name);
      if (se != null) throw se;

      if (Report.should_report(TOPICS, 3))
        Report.report(3, "[" + namespace + "] "
            + "NamespaceResolver_c: not cached: " + name);

      try {
        URI aliasNS = null;
        if (!StringUtil.isNameShort(name)) {
          // First check if name uses a codebase alias.
          String first = StringUtil.getFirstComponent(name);
          aliasNS = resolveCodebaseName(first);
        }
        if (aliasNS != null) {
          CodebaseTypeSystem ts = extInfo.typeSystem();
          NamespaceResolver nr = ts.namespaceResolver(aliasNS);
          q = nr.find(StringUtil.removeFirstComponent(name));
        } else {
          q = findImpl(name);
        }
      } catch (NoClassException e) {
        // Not found in this namespace, try parent.
        if (parent != null) {
          try {
            q = parent.find(name);
          } catch (NoClassException pe) {
            e = pe;
          }
        }
        if (q == null) {
          if (Report.should_report(TOPICS, 3)) {
            Report.report(3, "[" + namespace + "] " + "NamespaceResolver_c: "
                + e.getMessage());
            Report.report(3, "[" + namespace + "] "
                + "NamespaceResolver_c: installing " + name
                + "-> (not found) in resolver cache");
          }
          notFound.put(name, e);
          throw e;
        }
      }
      add(name, q);

      if (Report.should_report(TOPICS, 3))
        Report.report(3, "[" + namespace + "] "
            + "NamespaceResolver_c: loaded: " + name + "(" + q + ")"
            + " from NS:" + ((CodebaseClassType) q).canonicalNamespace());
    } else {
      if (Report.should_report(TOPICS, 3))
        Report.report(3, "[" + namespace + "] "
            + "NamespaceResolver_c: cached: " + name + "(" + q + ")");
    }

    return q;
  }

  @Override
  public NamespaceResolver copy() {
    try {
      NamespaceResolver_c r = (NamespaceResolver_c) super.clone();
      r.packages = new HashSet<>(this.packages);
      r.noPackage = new HashSet<>(this.noPackage);
      r.notFound = new HashMap<>();
      r.cache = new HashMap<>(this.cache);
      return r;
    } catch (CloneNotSupportedException e) {
      throw new InternalCompilerError("clone failed");
    }
  }

  /**
   * Check if a type object is in the cache, returning null if not.
   *
   * @param name
   *          The name to search for.
   */
  @Override
  public Importable check(String name) {
    return cache.get(name);
  }

  @Override
  public void add(String name, Importable q) throws SemanticException {
    if (!(q instanceof CodebaseClassType) || !(q instanceof ParsedTypeObject))
      throw new InternalCompilerError(
          "Expected entry to implement CodebaseClassType and ParsedTypeObject, but got "
              + q.getClass());

    // /TODO: This method may need to check more things related to clashes with
    // package names.
    if (packageExists(name))
      throw new SemanticException("Type \"" + name
          + "\" clashes with package of the same name.", q.position());

    if (q instanceof ParsedTypeObject) {
      if (((ParsedTypeObject) q).initializer() == null)
        throw new InternalCompilerError("No initializer for " + name);
    }

    if (q instanceof ParsedTypeObject) {
      if (!((ParsedTypeObject) q).initializer().isTypeObjectInitialized()) {
        if (Report.should_report(TOPICS, 2))
          Report.report(3, "[" + namespace + "] initializing " + q);
        ((ParsedTypeObject) q).initializer().initTypeObject();
      }
    } else throw new InternalCompilerError(q + " is not a ParsedTypeObject: "
        + q.getClass());

    replace(name, q);

    // If we are loading a class from another namespace,
    // add the class to that namespace too.
    if (q instanceof CodebaseClassType) {
      CodebaseClassType cct = (CodebaseClassType) q;
      if (!namespace.equals(cct.canonicalNamespace())) {
        CodebaseTypeSystem ts = extInfo.typeSystem();
        ts.namespaceResolver(cct.canonicalNamespace()).add(name, q);
      }
    }
  }

  @Override
  public void replace(String name, Importable q) {

    if (Report.should_report(TOPICS, 3))
      Report.report(3, "[" + namespace + "] "
          + "NamespaceResolver_c: installing " + name + "->" + q
          + " in resolver cache");
    if (Report.should_report(TOPICS, 5)) new Exception().printStackTrace();

    Package pkg = q.package_();
    while (pkg != null) {
      packages.add(pkg.fullName());
      pkg = pkg.prefix();
    }
    cache.put(name, q);
  }

  @Override
  public URI namespace() {
    return namespace;
  }

  /**
   * Get a type from a source file.
   */
  protected Importable getTypeFromSource(Source source, String name)
      throws SemanticException {
    CodebaseTypeSystem ts = extInfo.typeSystem();
    Scheduler scheduler = extInfo.scheduler();

    Job job = scheduler.loadSource((FileSource) source, true);
    CodebaseSource cbsrc = (CodebaseSource) source;

    // The type may be remote and not added to our namespace yet.
    Importable n = ts.namespaceResolver(cbsrc.canonicalNamespace()).check(name);

    if (n != null) {
      return n;
    }

    // The type may not have reached the proper compilation pass yet.
    if (job != null) {

      Goal g = scheduler.TypesInitialized(job);

      if (!scheduler.reached(g)) {

        throw new MissingDependencyException(g);
      }

    }
    if (extInfo.extFileManager().caseInsensitive())
      // Sigh... This source may have originally been enqueued under a
      // case insensitive name.  Allow the TypeSystem to keep looking.
      throw new NoClassException(name);

    // The source has already been compiled, but the type was not created there.
    throw new SemanticException("Could not find \"" + name + "\" in " + source
        + ".");
  }

  /**
   * Extract an encoded type from a class file.
   */
  protected ClassType getEncodedType(ClassFile clazz, String name)
      throws SemanticException {
    // At this point we've decided to go with the Class. So if something
    // goes wrong here, we have only one choice, to throw an exception.
    String version = extInfo.version().name();

    TypeObject dt;

    try {
      if (Report.should_report(Report.serialize, 1))
        Report.report(1, "Decoding " + name + " in " + clazz);

      dt = te.decode(clazz.encodedClassType(version), name);

      if (dt == null) {
        if (Report.should_report(Report.serialize, 1))
          Report.report(1, "* Decoding " + name + " failed");

        // Deserialization failed because one or more types could not
        // be resolved. Abort this pass. Dependencies have already
        // been set up so that this goal will be reattempted after
        // the types are resolved.
        throw new SchedulerException("Could not decode " + name);
      }
    } catch (InternalCompilerError e) {
      throw e;
    } catch (InvalidClassException e) {
      throw new BadSerializationException(clazz.name() + "@"
          + clazz.getClassFileURI());
    }

    if (dt instanceof ClassType) {
      ClassType ct = (ClassType) dt;
      add(name, ct);

      if (Report.should_report(Report.serialize, 1))
        Report.report(1, "* Decoding " + name + " succeeded");

      if (Report.should_report("typedump", 1)) {
        new ObjectDumper(new SimpleCodeWriter(System.out, 72)).dump(dt);
      }

      if (Report.should_report(REPORT_TOPICS, 2))
        Report.report(2, "Returning serialized ClassType for " + clazz.name()
            + ".");

      return ct;
    } else {
      throw new SemanticException("Class " + name + " not found in "
          + clazz.name() + ".");
    }
  }

  /**
   * Compare the encoded type's version against the loader's version.
   */
  protected int checkCompilerVersion(String clazzVersion) {
    if (clazzVersion == null) {
      return NOT_COMPATIBLE;
    }

    StringTokenizer st = new StringTokenizer(clazzVersion, ".");

    try {
      int v;
      v = Integer.parseInt(st.nextToken());
      Version version = extInfo.version();

      if (v != version.major()) {
        // Incompatible.
        return NOT_COMPATIBLE;
      }

      v = Integer.parseInt(st.nextToken());

      if (v != version.minor()) {
        // Not the best option, but will work if its the only one.
        return MINOR_NOT_COMPATIBLE;
      }
    } catch (NumberFormatException e) {
      return NOT_COMPATIBLE;
    }

    // Everything is way cool.
    return COMPATIBLE;
  }

  @Override
  public final URI resolveCodebaseName(String name) {
    URI ns = aliasCache.get(name);
    if (ns != null) return ns;
    if (!noAlias.contains(name)) {
      ns = resolveCodebaseNameImpl(name);
      if (ns == null)
        noAlias.add(name);
      else {
        aliasCache.put(name, ns);
      }
      return ns;
    }
    return null;
  }

  @Override
  public Map<String, URI> codebaseAliases() {
    return Collections.unmodifiableMap(aliasCache);
  }

  @Override
  public Codebase codebase() {
    return null;
  }

  @Override
  public Label label() {
    if (Worker.isInitialized()) {
      if (integrity == null) {
        Store s = extInfo.destinationStore();
        NodePrincipal sp = s.getPrincipal();
        NodePrincipal np = Worker.getWorker().getPrincipal();
        integrity =
            LabelUtil._Impl.toLabel(s, LabelUtil._Impl.writerPolicy(s, sp, np));
      }
      return integrity;
    }
    throw new InternalCompilerError("Not implemented yet! Hurry up!");
  }

  @Override
  public String toString() {
    return namespace.toString();
  }
}
