package codebases.types;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import polyglot.main.Report;
import polyglot.types.ClassContextResolver;
import polyglot.types.ClassType;
import polyglot.types.MemberInstance;
import polyglot.types.Named;
import polyglot.types.NoClassException;
import polyglot.types.ParsedTypeObject;
import polyglot.types.ReferenceType;
import polyglot.types.Resolver;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.CollectionUtil;
import polyglot.util.InternalCompilerError;
import polyglot.util.StringUtil;

public class CBClassContextResolver extends ClassContextResolver {
  public CBClassContextResolver(TypeSystem ts, ClassType type) {
    super(ts, type);
  }

  private static final List<String> TOPICS = CollectionUtil.list(Report.types,
      Report.resolver);

  /**
   * Find a type object in the context of the class.
   *
   * @param name
   *          The name to search for.
   */
  @Override
  public Named find(String name, ClassType accessor) throws SemanticException {

    if (Report.should_report(TOPICS, 2))
      Report.report(2, "Looking for " + name + " in " + this);

    if (!StringUtil.isNameShort(name)) {
      throw new InternalCompilerError("Cannot lookup qualified name " + name);
    }

    // Check if the name is for a member class.
    ClassType mt = null;

    Named m;

    String fullName = type.fullName() + "." + name;
    String rawName = ts.getTransformedClassName(type) + "$" + name;
    CodebaseClassType cct = (CodebaseClassType) type;

    NamespaceResolver nsr =
        ((CodebaseTypeSystem) ts).namespaceResolver(cct.canonicalNamespace());

    // First check the system resolver.
    m = nsr.check(fullName);

    // Try the raw class file name.
    if (m == null) {
      m = nsr.check(rawName);
    }

    // Check if the member was explicitly declared.
    if (m == null) {
      m = type.memberClassNamed(name);
    }

    // Go to disk, but only if there is no job for the type.
    // If there is a job, all members should be in the resolver
    // already.
    boolean useLoadedResolver = true;

    if (type instanceof ParsedTypeObject) {
      ParsedTypeObject pto = (ParsedTypeObject) type;
      if (pto.job() != null) {
        useLoadedResolver = false;
      }
    }

    if (m == null && useLoadedResolver) {
      try {
        m = nsr.find(rawName);
      } catch (SemanticException e) {
        // Not found; will fall through to error handling code
      }
    }

    if (m instanceof ClassType) {
      mt = (ClassType) m;

      if (!mt.isMember()) {
        throw new SemanticException("Class " + mt + " is not a member class, "
            + " but was found in " + type + ".");
      }

      if (mt.outer().declaration() != type.declaration()) {
        throw new SemanticException("Class " + mt + " is not a member class "
            + " of " + type + ".");
      }

      if (!canAccess(mt, accessor)) {
        throw new SemanticException("Cannot access member type \"" + mt + "\".");
      }

      return mt;
    }

    // Collect all members of the super types.
    // Use a Set to eliminate duplicates.
    Set<Named> acceptable = new HashSet<>();

    if (type.superType() != null) {
      Type sup = type.superType();
      if (sup instanceof ClassType) {
        Resolver r = ts.classContextResolver((ClassType) sup, accessor);
        try {
          Named n = r.find(name);
          acceptable.add(n);
        } catch (SemanticException e) {
        }
      }
    }

    for (ReferenceType sup : type.interfaces()) {
      if (sup instanceof ClassType) {
        Resolver r = ts.classContextResolver((ClassType) sup, accessor);
        try {
          Named n = r.find(name);
          acceptable.add(n);
        } catch (SemanticException e) {
        }
      }
    }

    if (acceptable.size() == 0) {
      throw new NoClassException(name, type);
    } else if (acceptable.size() > 1) {
      Set<Type> containers = new HashSet<>(acceptable.size());
      for (Named n : acceptable) {
        if (n instanceof MemberInstance) {
          MemberInstance mi = (MemberInstance) n;
          containers.add(mi.container());
        }
      }

      if (containers.size() == 2) {
        Iterator<Type> i = containers.iterator();
        Type t1 = i.next();
        Type t2 = i.next();
        throw new SemanticException("Member \"" + name + "\" of " + type
            + " is ambiguous; it is defined in both " + t1 + " and " + t2 + ".");
      } else {
        throw new SemanticException("Member \"" + name + "\" of " + type
            + " is ambiguous; it is defined in " + containers + ".");
      }
    }

    Named t = acceptable.iterator().next();

    if (Report.should_report(TOPICS, 2))
      Report.report(2, "Found member class " + t);

    return t;
  }

}
