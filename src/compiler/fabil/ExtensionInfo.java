package fabil;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import polyglot.ast.NodeFactory;
import polyglot.frontend.CupParser;
import polyglot.frontend.FileSource;
import polyglot.frontend.Parser;
import polyglot.frontend.Scheduler;
import polyglot.frontend.TargetFactory;
import polyglot.lex.Lexer;
import polyglot.main.Options;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.types.reflect.ClassFileLoader;
import polyglot.types.reflect.ClassPathLoader;
import polyglot.util.ErrorQueue;
import polyglot.util.InternalCompilerError;
import polyglot.util.TypeEncoder;
import codebases.frontend.CBTargetFactory;
import codebases.frontend.CodebaseSource;
import codebases.frontend.CodebaseSourceLoader;
import codebases.frontend.FileSourceLoader;
import codebases.frontend.LocalSource;
import codebases.frontend.RemoteSource;
import codebases.frontend.URISourceLoader;
import codebases.types.CodebaseResolver;
import codebases.types.CodebaseTypeSystem;
import codebases.types.PathResolver;
import codebases.types.NamespaceResolver;
import fabil.ast.FabILNodeFactory;
import fabil.ast.FabILNodeFactory_c;
import fabil.frontend.FabILScheduler;
import fabil.parse.Grm;
import fabil.parse.Lexer_c;
import fabil.types.FabILTypeSystem;
import fabil.types.FabILTypeSystem_c;
import fabric.common.SysUtil;
import fabric.lang.FClass;
import fabric.lang.security.LabelUtil;

/**
 * Extension information for FabIL extension.
 */
public class ExtensionInfo extends polyglot.frontend.JLExtensionInfo implements codebases.frontend.ExtensionInfo {
  static {
    // force Topics to load
    new Topics();
  }

  public ExtensionInfo() {
  }

  @Override
  public String compilerName() {
    return "filc";
  }

  @Override
  protected NodeFactory createNodeFactory() {
    return new FabILNodeFactory_c();
  }

  @Override
  protected Options createOptions() {
    return new FabILOptions_c(this);
  }

  @Override
  protected Scheduler createScheduler() {
    return new FabILScheduler(this);
  }

  @Override
  protected TypeSystem createTypeSystem() {
    return new FabILTypeSystem_c();
  }

  @Override
  public TargetFactory targetFactory() {
    if (target_factory == null) {
      target_factory =
          new CBTargetFactory(getOptions().output_directory,
              getOptions().output_ext, getOptions().output_stdout);
    }

    return target_factory;
  }

  @Override
  public Parser parser(Reader reader, FileSource source, ErrorQueue eq) {
    CodebaseSource src = (CodebaseSource) source;
    Lexer lexer = new Lexer_c(reader, source, eq);
    Grm grm = new Grm(lexer, ts, nf, eq, src.canonicalNamespace());
    return new CupParser(grm, source, eq);
  }
  
  @Override
  protected void initTypeSystem() {
    try {
      // There isn't a single toplevel resolver.
      ((CodebaseTypeSystem)ts).initialize(this);
    } catch (SemanticException e) {
      throw new InternalCompilerError("Unable to initialize type system: ", e);
    }
  }

  @Override
  public FabILTypeSystem typeSystem() {
    return (FabILTypeSystem) super.typeSystem();
  }

  @Override
  public FabILNodeFactory nodeFactory() {
    return (FabILNodeFactory) super.nodeFactory();
  }

  @Override
  public String defaultFileExtension() {
    return "fil";
  }

  @Override
  public Version version() {
    return new Version();
  }

  public FabILOptions getFabILOptions() {
    return (FabILOptions) getOptions();
  }

//  public Codebase codebase() {
//    return codebase;
//  }

  @Override
  public FileSource createFileSource(File f, boolean user) throws IOException {
    return new LocalSource(f, user, localNamespace());
  }

  public FileSource createRemoteSource(URI ns, fabric.lang.Object obj, boolean user)
      throws IOException {
    if (!(obj instanceof FClass))
      throw new InternalCompilerError("Expected FClass.");
    
    FClass fcls = (FClass) obj;    
    if (!LabelUtil._Impl.relabelsTo(fcls.get$label(), fcls.getCodebase()
        .get$label())) {
      // XXX: should we throw a more security-related exception here?
      throw new IOException("The label of class " + SysUtil.absoluteName(fcls)
          + " is higher than the label of its codebase ");
    }
    
    return new RemoteSource(ns, fcls, user);
  }

  // Resolves types
  public NamespaceResolver createNamespaceResolver(URI ns) {
    FabILOptions opt = (FabILOptions) getOptions();
    if ("fab".equals(ns.getScheme())) {
      if(ns.getSchemeSpecificPart().startsWith("local")) {
        List<NamespaceResolver> path = new ArrayList<NamespaceResolver>();
        path.add(typeSystem().platformResolver());
        path.addAll(typeSystem().classpathResolvers());
        path.addAll(typeSystem().sourcepathResolvers());
        return new PathResolver(this, ns, path, getFabILOptions().codebaseAliases());
      }
      else if(ns.getSchemeSpecificPart().startsWith("platform")) {
        // A platform resolver is really just a local resolver that is treated
        // specially.
        // Loading the appropriate platform classes and signatures
        // is handled by the classpathloader and sourceloader
        CodebaseTypeSystem cbts = (CodebaseTypeSystem) ts;

        List<NamespaceResolver> resolvers =
            new ArrayList<NamespaceResolver>(cbts.signatureResolvers());
        //FabIL also allows direct linking to Java classes 
        //TODO: Make this play nice with cmdline args
        String path = System.getProperty("java.class.path");
        path += File.pathSeparator + System.getProperty("sun.boot.class.path");
        URI file = URI.create("file:///");
        for (String dir : path.split(File.pathSeparator)) {
          File f = new File(dir);
          NamespaceResolver nr = new SimpleResolver(this, file.resolve(f
              .getAbsolutePath()));
          nr.loadRawClasses(true);
          resolvers.add(nr);
        }
        return new PathResolver(this, ns, resolvers);
      }
      else {
        List<NamespaceResolver> path = new ArrayList<NamespaceResolver>(2);
        //Codebases may never resolve platform types.
        path.add(typeSystem().platformResolver());
        path.add(new CodebaseResolver(this, ns));
        return new PathResolver(this, ns, path);
      }
    } else if ("file".equals(ns.getScheme())) {
      return new SimpleResolver(this, ns);
    } else throw new InternalCompilerError("Unexpected scheme in URI: " + ns);
  }


  //TODO: support multiple platform namespaces
  private static URI platform_ns = URI.create("fab:platform");
  public URI platformNamespace() {
    return platform_ns;
  }

  //TODO: support multiple local namespaces
  private static URI local_ns = URI.create("fab:local");
  public URI localNamespace() {
    return local_ns;
  }

  @Override
  public TypeEncoder typeEncoder() {
    return new TypeEncoder(ts);
  }

  // Loads source files
  @Override
  public URISourceLoader sourceLoader(URI uri) {
    if ("fab".equals(uri.getScheme())) {
      if(uri.isOpaque())
        throw new InternalCompilerError("Unexpected URI:" + uri);
      return new CodebaseSourceLoader(this, uri);
    } else if ("file".equals(uri.getScheme())) {
      return new FileSourceLoader(this, uri);
    } else throw new InternalCompilerError("Unexpected scheme in URI: " + uri);
  }

  //Loads class files
  @Override
  public ClassPathLoader classpathLoader(URI ns) {
    if ("fab".equals(ns.getScheme())) {
      // Load previously compiled classes from cache
      if(ns.isOpaque())
        throw new InternalCompilerError("Unexpected URI:" + ns);
 
      String store = ns.getAuthority();
      long onum = Long.parseLong(ns.getPath().substring(1));
      
      FabILOptions opt = (FabILOptions) getOptions();
      //At the Fabric/FabIL layer, class names do not include the codebases
      String cachedir = opt.outputDirectory() + File.separator
          + SysUtil.pseudoname(store, onum).replace('.', File.separatorChar);          
      return new ClassPathLoader(cachedir, new ClassFileLoader(this));
      
    } else if ("file".equals(ns.getScheme())) {
      return new ClassPathLoader(ns.getPath(), new ClassFileLoader(this));
    } else throw new InternalCompilerError("Unexpected scheme in URI: " + ns.getScheme());
  }
}
