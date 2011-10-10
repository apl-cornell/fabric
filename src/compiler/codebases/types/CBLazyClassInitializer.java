package codebases.types;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import polyglot.main.Report;
import polyglot.types.ClassType;
import polyglot.types.MethodInstance;
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import polyglot.types.reflect.ClassFile;
import polyglot.types.reflect.ClassFileLazyClassInitializer;
import polyglot.types.reflect.Method;
import polyglot.util.InternalCompilerError;
import polyglot.util.StringUtil;
import codebases.frontend.ExtensionInfo;

/**
 * This class is basically identical to it's superclass with calls to
 * ts.systemResolver() replaced by calls to ts.platformResolver()
 * 
 * @author owen
 */
//    protected List interfaces;
//protected List methods;
//protected List fields;
//protected List constructors;

public class CBLazyClassInitializer extends ClassFileLazyClassInitializer {
  private CodebaseTypeSystem ts;
  private ExtensionInfo extInfo;

  public CBLazyClassInitializer(ClassFile file, CodebaseTypeSystem ts) {
    super(file, ts);
    this.ts = ts;
    this.extInfo = (ExtensionInfo) ts.extensionInfo();
  }

  @Override
  public void initInterfaces() {
    if(interfacesInitialized)
      return;
    // Clear first in case we were interrupted 
    ct.setInterfaces(new ArrayList());
    super.initInterfaces();
  }

  @Override
  public void initFields() {
    if(fieldsInitialized)
      return;
    // Clear first in case we were interrupted 
    ct.setFields(new ArrayList());
    super.initFields();
  }

  @Override
  public void initConstructors() {
    if(constructorsInitialized)
      return;
    // Clear first in case we were interrupted 
    ct.setConstructors(new ArrayList());
    super.initConstructors();
  }

  @Override
  public void initMemberClasses() {
    if (memberClassesInitialized)
      return;

    // Clear first in case we were interrupted 
    ct.setMemberClasses(new ArrayList());
    super.initMemberClasses();
  }
  
  @Override
  public void initMethods() {
    if (methodsInitialized) 
      return;

    // Clear first in case we were interrupted 
    ct.setMethods(new ArrayList());     
    super.initMethods();
  }

  @Override
  protected ParsedClassType createType() throws SemanticException {
    // The name is of the form "p.q.C$I$J".
    String name = clazz.classNameCP(clazz.getThisClass());

    if (Report.should_report(verbose, 2))
      Report.report(2, "creating ClassType for " + name);

    // Create the ClassType.
    ParsedClassType ct = ts.createClassType(this);
    ct.flags(ts.flagsForBits(clazz.getModifiers()));
    ct.position(position());

    // This is the "p.q" part.
    String packageName = StringUtil.getPackageComponent(name);

    // Set the ClassType's package.
    if (!packageName.equals("")) {
      ct.package_(ts.packageForName(extInfo.platformNamespace(),packageName));
    }

    // This is the "C$I$J" part.
    String className = StringUtil.getShortNameComponent(name);

    String outerName; // This will be "p.q.C$I"
    String innerName; // This will be "J"

    outerName = name;
    innerName = null;

    while (true) {
      int dollar = outerName.lastIndexOf('$');

      if (dollar >= 0) {
        outerName = name.substring(0, dollar);
        innerName = name.substring(dollar + 1);
      } else {
        outerName = name;
        innerName = null;
        break;
      }

      // Try loading the outer class.
      // This will recursively load its outer class, if any.
      try {
        if (Report.should_report(verbose, 2))
          Report.report(2, "resolving " + outerName + " for " + name);
        ct.outer(this.typeForName(outerName));
        break;
      } catch (SemanticException e) {
        // Failed. The class probably has a '$' in its name.
        if (Report.should_report(verbose, 3))
          Report.report(2, "error resolving " + outerName);
      }
    }

    ClassType.Kind kind = ClassType.TOP_LEVEL;

    if (innerName != null) {
      // A nested class. Parse the class name to determine what kind.
      StringTokenizer st = new StringTokenizer(className, "$");

      while (st.hasMoreTokens()) {
        String s = st.nextToken();

        if (Character.isDigit(s.charAt(0))) {
          // Example: C$1
          kind = ClassType.ANONYMOUS;
        } else if (kind == ClassType.ANONYMOUS) {
          // Example: C$1$D
          kind = ClassType.LOCAL;
        } else {
          // Example: C$D
          kind = ClassType.MEMBER;
        }
      }
    }

    if (Report.should_report(verbose, 3))
      Report.report(3, name + " is " + kind);

    ct.kind(kind);

    if (ct.isTopLevel()) {
      ct.name(className);
    } else if (ct.isMember() || ct.isLocal()) {
      ct.name(innerName);
    }

    // Add unresolved class into the cache to avoid circular resolving.

    ts.platformResolver().add(name, ct);
    ts.platformResolver().add(ct.fullName(), ct);
    return ct;
  }

  @Override
  protected ClassType quietTypeForName(String name) {
    if (Report.should_report(verbose, 2))
      Report.report(2, "resolving " + name);

    try {
      return (ClassType) ts.platformResolver().find(name);
    } catch (SemanticException e) {
      throw new InternalCompilerError("could not load " + name, e);
    }
  }

  @Override
  protected ClassType typeForName(String name) throws SemanticException {
    if (Report.should_report(verbose, 2))
      Report.report(2, "resolving " + name);
    return (ClassType) ts.platformResolver().find(name);
  }

}
