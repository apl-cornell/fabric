package fabil.extension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import polyglot.ast.Call;
import polyglot.ast.ClassBody;
import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.ConstructorDecl;
import polyglot.ast.Expr;
import polyglot.ast.FieldDecl;
import polyglot.ast.Formal;
import polyglot.ast.Id;
import polyglot.ast.Initializer;
import polyglot.ast.MethodDecl;
import polyglot.ast.New;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Stmt;
import polyglot.ast.TypeNode;
import polyglot.qq.QQ;
import polyglot.types.ClassType;
import polyglot.types.ConstructorInstance;
import polyglot.types.Flags;
import polyglot.types.MethodInstance;
import polyglot.types.PrimitiveType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import fabil.types.FabILFlags;
import fabil.types.FabILTypeSystem;
import fabil.visit.MemoizedMethodRewriter;
import fabil.visit.NoArgConstructorWriter;
import fabil.visit.ProxyRewriter;
import fabil.visit.RemoteCallRewriter;
import fabil.visit.ThreadRewriter;

public class ClassDeclExt_c extends ClassMemberExt_c {

  /**
   * A flag for determining whether the class's type information should be
   * serialized.
   */
  protected boolean shouldSerializeType;

  public ClassDeclExt_c() {
    shouldSerializeType = false;
  }

  public boolean shouldSerializeType() {
    return shouldSerializeType;
  }

  public ClassDeclExt_c shouldSerializeType(boolean shouldSerializeType) {
    ClassDeclExt_c result = (ClassDeclExt_c) copy();
    result.shouldSerializeType = shouldSerializeType;
    return result;
  }

  /**
   * Returns the interface translation of the class declaration.
   *
   * @see fabil.extension.FabILExt_c#rewriteProxies(fabil.visit.ProxyRewriter)
   */
  @Override
  public Node rewriteProxies(ProxyRewriter pr) {
    ClassDecl classDecl = node();

    classDecl = classDecl.flags(classDecl.flags().clear(FabILFlags.NONFABRIC));

    // Only translate if we're processing a Fabric class.
    if (!pr.typeSystem().isFabricClass(classDecl.type()))
    // Tag for type serialization.
      return classDecl.ext(shouldSerializeType(true));

    NodeFactory nf = pr.nodeFactory();
    FabILTypeSystem ts = pr.typeSystem();

    if (classDecl.flags().isInterface()) {
      // Already an interface. Leave existing members alone, but insert a proxy
      // class.
      List<ClassMember> members = new ArrayList<>(classDecl.body().members());
      members.add(makeProxy(pr, pr.typeSystem().FObject()));

      // If necessary, add fabric.lang.Object as a super-interface.
      List<TypeNode> interfaces = classDecl.interfaces();
      boolean needObject = true;
      for (TypeNode tn : interfaces) {
        if ("fabric.lang.Object".equals(tn.toString())) {
          needObject = false;
          break;
        }
      }

      if (needObject) {
        interfaces = new ArrayList<>(interfaces);
        interfaces.add(nf.CanonicalTypeNode(Position.compilerGenerated(),
            ts.FObject()));
      }

      // Flag the interface for serialization.
      return classDecl.body(classDecl.body().members(members))
          .interfaces(interfaces).ext(shouldSerializeType(true));
    }

    TypeNode superClass = classDecl.superClass();

    // Make the super class explicit.
    if (superClass == null) {
      superClass =
          nf.CanonicalTypeNode(Position.compilerGenerated(), ts.FObject());
    }

    // The super class will be turned into an interface that we will extend.
    List<TypeNode> interfaces = new ArrayList<>(classDecl.interfaces());
    interfaces.add(superClass);
    ClassDecl result = classDecl.interfaces(interfaces);
    result = result.superClass(null);

    // Tag the interface's ClassType for serialization.
    result = (ClassDecl) result.ext(shouldSerializeType(true));

    // We're generating an interface.
    Flags flags = ProxyRewriter.toInterface(classDecl.flags());
    result = result.flags(flags);

    // Rewrite the members.
    ClassBody body = classDecl.body();
    List<ClassMember> oldMembers = body.members();
    List<ClassMember> members = new ArrayList<>(oldMembers.size());
    for (ClassMember m : oldMembers) {
      members.addAll(ext(m).interfaceMember(pr, classDecl));

      // Preserve the Polyglot type information from fabc.
      if (!(m instanceof FieldDecl)) continue;

      FieldDecl f = (FieldDecl) m;
      Flags fieldFlags = f.flags();
      if (!(fieldFlags.isStatic() && fieldFlags.isFinal() && fieldFlags
          .isPublic())) continue;

      if (!f.name().startsWith("jlc$")) continue;

      members.add(m);
    }

    // Add the _Proxy, _Impl, and _Static classes.
    ClassType superType = (ClassType) superClass.type();
    members.add(makeProxy(pr, superType));
    members.add(makeImpl(pr, superType));
    members.addAll(makeStatic(pr, superType));

    // Set the class body.
    return result.body(body.members(members));
  }

  /**
   * Returns the Proxy translation of the class declaration.
   */
  private ClassDecl makeProxy(ProxyRewriter pr, ClassType superClass) {
    ClassDecl classDecl = node();

    // Rewrite the members.
    ClassBody body = classDecl.body();
    List<ClassMember> oldMembers = body.members();
    List<ClassMember> members = new ArrayList<>(oldMembers.size());
    for (ClassMember m : oldMembers)
      members.addAll(ext(m).proxyMember(pr, classDecl));

    // Create proxy methods based on the class instances of this class and all
    // the interfaces it implements.
    members.addAll(makeProxyMethods(pr, classDecl.type()));

    // Add constructors.
    QQ qq = pr.qq();

    if (!classDecl.flags().isInterface()) {
      ClassMember implConstr =
          qq.parseMember("public _Proxy(" + classDecl.id() + "._Impl impl) {"
              + "super(impl); }");
      members.add(implConstr);
    }

    ClassMember oidConstr =
        qq.parseMember("public _Proxy(fabric.worker.Store store, long onum) {"
            + "super(store, onum); }");
    members.add(oidConstr);

    // Create the class declaration.
    ClassDecl result =
        qq.parseDecl(
            "public static class _Proxy extends " + superClass.translate(null)
                + "._Proxy implements %T {%LM}", classDecl.type(), members);
    return result.type(classDecl.type());
  }

  /**
   * Generates proxy methods for methods contained in the given class type and
   * all the interfaces it implements.
   */
  private List<ClassMember> makeProxyMethods(ProxyRewriter pr,
      final ClassType ct) {
    final FabILTypeSystem ts = pr.typeSystem();
    List<ClassMember> result = new ArrayList<>();

    Queue<ClassType> toVisit = new LinkedList<>();
    Set<ClassType> visitedTypes = new HashSet<>();

    // Maps method names to sets of formal argument types. This prevents us
    // from generating duplicate methods.
    Map<String, Set<List<? extends Type>>> translatedInstances =
        new HashMap<>();

    // First populate the above data structures with the super class and the
    // type hierarchy above that. The proxy's super class will already have
    // methods for those types.
    toVisit.add(ct.superType().toClass());
    while (!toVisit.isEmpty()) {

      ClassType type = toVisit.remove();
      if (type.superType() != null) toVisit.add(type.superType().toClass());

      if (visitedTypes.contains(type)) continue;
      visitedTypes.add(type);

      List<? extends MethodInstance> methods = type.methods();
      for (MethodInstance mi : methods) {
        // We actually want to generate proxies for static methods even if they
        // exist already in a super type. This way, static method dispatching
        // will work correctly.
        if (mi.flags().isStatic()) continue;

        // Don't count private methods either.
        if (mi.flags().isPrivate()) continue;

        String name = mi.name();
        Set<List<? extends Type>> formalTypes = translatedInstances.get(name);
        if (formalTypes == null) {
          formalTypes = new HashSet<>();
          translatedInstances.put(name, formalTypes);
        }
        formalTypes.add(mi.formalTypes());
      }
    }

    // Now go through the class itself and the interfaces it implements.
    toVisit.add(ct);
    while (!toVisit.isEmpty()) {
      ClassType type = toVisit.remove();
      if (visitedTypes.contains(type)) continue;
      visitedTypes.add(type);

      List<? extends MethodInstance> methods = type.methods();
      for (MethodInstance mi : methods) {
        String name = mi.name();

        // Don't generate proxies for private methods.
        if (mi.flags().isPrivate()) continue;

        List<? extends Type> types = mi.formalTypes();

        // Ensure this isn't a duplicate method.
        Set<List<? extends Type>> formalTypes = translatedInstances.get(name);
        if (formalTypes == null) {
          formalTypes = new HashSet<>();
          translatedInstances.put(name, formalTypes);
        }

        if (formalTypes.contains(types)) continue;
        formalTypes.add(types);

        // Don't generate proxies for methods that were already implemented by
        // a super class.
        if (ts.findImplementingMethod(ct.superType().toClass(), mi) != null)
          continue;

        result.add(makeProxyMethod(pr, mi));
      }

      toVisit.addAll((Collection<? extends ClassType>) type.interfaces());
    }

    return result;
  }

  /**
   * Generates a proxy method for the given method instance.
   */
  private ClassMember makeProxyMethod(ProxyRewriter pr, MethodInstance mi) {
    FabILTypeSystem ts = pr.typeSystem();

    // The end result will be quasiquoted. Construct the quasiquoted string and
    // list of substitution arguments in tandem.
    StringBuffer methodDecl = new StringBuffer();
    List<Object> subst = new ArrayList<>();

    // Since the method will be implementing part of an interface, make the
    // method public and non-abstract.
    Flags flags = ProxyRewriter.toPublic(mi.flags()).clearAbstract();
    flags = flags.clear(FabILFlags.MEMOIZED);
    methodDecl.append(flags + " ");

    Type returnType = mi.returnType();
    if (returnType.isArray() && ts.isFabricArray(returnType)) {
      returnType = ts.toFabricRuntimeArray(returnType.toArray());
    }

    String name = mi.name();
    methodDecl.append("%T " + name + "(");
    subst.add(returnType);

    // Generate the formals list. While we're doing this, may as well generate
    // the args list too.
    StringBuffer args = new StringBuffer();
    List<? extends Type> formalTypes = mi.formalTypes();
    int argCount = 1;
    for (Type t : formalTypes) {
      methodDecl.append((argCount == 1 ? "" : ", ") + "%T arg" + argCount);
      args.append((argCount == 1 ? "" : ", ") + "arg" + argCount);

      if (ts.isFabricArray(t)) t = ts.toFabricRuntimeArray(t.toArray());
      subst.add(t);
      argCount++;
    }

    methodDecl.append(") ");

    // Figure out the throws list.
    List<? extends Type> throwTypes = mi.throwTypes();
    if (!throwTypes.isEmpty()) {
      methodDecl.append("throws %LT ");
      subst.add(new ArrayList<>(throwTypes));
    }
    if (!mi.flags().isNative()) {
      methodDecl.append("{ " + (returnType.isVoid() ? "" : "return "));

      // Figure out the call target.
      String implType = node().type().translate(null);
      if (flags.isStatic()) {
        methodDecl.append(implType + "._Impl");
      } else {
        methodDecl.append("((" + implType + ") fetch())");
      }

      // Call the delegate.
      methodDecl.append("." + name + "(" + args + "); }");
    } else {
      methodDecl.append(";");
    }
    QQ qq = pr.qq();
    return qq.parseMember(methodDecl.toString(), subst);
  }

  /**
   * Returns the Impl translation of the class declaration.
   */
  private ClassDecl makeImpl(ProxyRewriter pr, ClassType superClass) {
    ClassDecl classDecl = node();
    ClassType classType = classDecl.type();

    // The Impl class will be public and static.
    Flags flags = ProxyRewriter.toPublic(classDecl.flags()).Static();

    // Rewrite the members.
    ClassBody body = classDecl.body();
    List<ClassMember> oldMembers = body.members();
    List<ClassMember> members = new ArrayList<>(oldMembers.size());
    for (ClassMember m : oldMembers) {
      members.addAll(ext(m).implMember(pr, classDecl));
    }

    // Create the $makeProxy method.
    QQ qq = pr.qq();
    ClassMember makeProxyDecl =
        qq.parseMember("protected fabric.lang.Object._Proxy $makeProxy() {"
            + "return new " + classType.translate(null) + "._Proxy(this); }");
    members.add(makeProxyDecl);

    // Create serializers.
    members.addAll(makeSerializers(pr, members));

    // Create the $copyAppStateFrom method.
    ClassMember copyAppStateFrom = makeCopyAppStateFrom(pr, members);
    if (copyAppStateFrom != null) members.add(copyAppStateFrom);

    // Create the $makeSemiDeepCopy method.
    //ClassMember copyConstructor = makeCopyConstructor(pr, members);
    //if (copyConstructor != null) members.add(copyConstructor);

    // Create the class declaration.
    ClassDecl result =
        qq.parseDecl(
            flags + " class _Impl extends " + superClass.translate(null)
                + "._Impl implements %T {%LM}", classType, members);
    return result.type(classDecl.type());
  }

  /**
   * Produces the _Static interface declaration for the static non-final fields.
   */
  private List<ClassMember> makeStatic(ProxyRewriter pr, ClassType superClass) {
    ClassDecl classDecl = node();
    ClassType classType = classDecl.type();

    // Rewrite the members.
    ClassBody body = classDecl.body();
    List<ClassMember> oldMembers = body.members();
    List<ClassMember> interfaceMembers = new ArrayList<>(oldMembers.size());
    List<ClassMember> proxyMembers = new ArrayList<>(oldMembers.size());
    List<ClassMember> implMembers = new ArrayList<>(oldMembers.size());
    List<Stmt> implInitMembers = new ArrayList<>(oldMembers.size());

    for (ClassMember m : oldMembers) {
      interfaceMembers.addAll(ext(m).staticInterfaceMember(pr, classDecl));
      proxyMembers.addAll(ext(m).staticProxyMember(pr, classDecl));
      implMembers.addAll(ext(m).staticImplMember(pr, classDecl));
      implInitMembers.addAll(ext(m).staticImplInitMember(pr));
    }

    // Create the proxy constructor declarations and add them to the list of
    // static proxy members.
    QQ qq = pr.qq();
    ClassMember proxyConstructorDecl =
        qq.parseMember("public _Proxy(" + classType.translate(null)
            + "._Static._Impl impl) { super(impl); }");
    proxyMembers.add(proxyConstructorDecl);
    proxyConstructorDecl =
        qq.parseMember("public _Proxy(fabric.worker.Store store, long onum) {"
            + "super(store, onum); }");
    proxyMembers.add(proxyConstructorDecl);

    // Create the $instance declaration and add it to the list of static proxy
    // members.
    String staticIfaceName = classType.translate(null) + "._Static";
    FieldDecl fieldDecl =
        (FieldDecl) qq.parseMember("public static final " + staticIfaceName
            + " $instance;");
    proxyMembers.add(fieldDecl);

    // Create the static initializer for initializing $instance.
    Initializer init =
        (Initializer) qq.parseMember("static {" + staticIfaceName
            + "._Impl impl = " + "  (" + staticIfaceName + "._Impl)"
            + "    fabric.lang.Object._Static._Proxy.$makeStaticInstance("
            + "      " + staticIfaceName + "._Impl.class);" + "$instance = ("
            + staticIfaceName + ") impl.$getProxy();" + "impl.$init();" + "}");
    proxyMembers.add(init);

    // Create the proxy declaration and add it to the list of interface members.
    ClassDecl proxyDecl =
        qq.parseDecl("final class _Proxy extends fabric.lang.Object._Proxy "
            + "implements " + classType.translate(null) + "._Static {%LM}",
            (Object) proxyMembers);
    interfaceMembers.add(proxyDecl);

    // Create the serialization method and deserialization constructor, and add
    // them to the list of static impl members.
    List<ClassMember> serializationDecls = makeSerializers(pr, implMembers);
    implMembers.addAll(serializationDecls);

    // Create the impl constructor declarations and add them to the list of
    // static impl members.
    ClassMember implConstructorDecl =
        qq.parseMember("public _Impl(fabric.worker.Store store) {"
            + "super(store); }");
    implMembers.add(implConstructorDecl);

    // Create the $makeProxy method declaration and add it to the list of static
    // impl members.
    ClassMember makeProxyDecl =
        qq.parseMember("protected fabric.lang.Object._Proxy "
            + "$makeProxy() { return new " + classType.translate(null)
            + "._Static._Proxy(this); }");
    implMembers.add(makeProxyDecl);

    // Create the $init method declaration and add it to the list of static impl
    // members.
    ClassMember initDecl =
        qq.parseMember("private void $init() { %LS }", (Object) implInitMembers);
    implMembers.add(initDecl);

    // Create the impl declaration and add it to the list of interface members.
    ClassDecl implDecl =
        qq.parseDecl("class _Impl extends fabric.lang.Object._Impl "
            + "implements " + classType.translate(null) + "._Static {%LM}",
            (Object) implMembers);
    interfaceMembers.add(implDecl);

    // Create the interface declaration.
    ClassDecl interfaceDecl =
        qq.parseDecl("interface _Static extends fabric.lang.Object, Cloneable "
            + "{%LM}", (Object) interfaceMembers);

    List<ClassMember> result = new ArrayList<>(2);
    result.add(interfaceDecl);
    return result;
  }

  private List<ClassMember> makeSerializers(ProxyRewriter pr,
      List<ClassMember> members) {
    FabILTypeSystem ts = pr.typeSystem();
    List<ClassMember> result = new ArrayList<>(3);
    List<FieldDecl> fields = new LinkedList<>();

    // Determine the list of fields to serialize.
    for (ClassMember m : members) {
      if (m instanceof FieldDecl) {
        FieldDecl f = (FieldDecl) m;
        TypeNode fieldType = f.type();
        if (ts.isFabricType(fieldType) && !f.flags().isTransient())
          fields.add(f);
      }
    }

    QQ qq = pr.qq();
    StringBuilder out = new StringBuilder();
    StringBuilder in = new StringBuilder();
    List<Object> inSubst = new ArrayList<>();

    for (FieldDecl f : fields) {
      Type t = f.declType();

      if (t.isBoolean()) {
        out.append("out.writeBoolean(this." + f.name() + ");");
        in.append("this." + f.name() + " = in.readBoolean();");
      } else if (t.isByte()) {
        out.append("out.writeByte(this." + f.name() + ");");
        in.append("this." + f.name() + " = in.readByte();");
      } else if (t.isChar()) {
        out.append("out.writeChar(this." + f.name() + ");");
        in.append("this." + f.name() + " = in.readChar();");
      } else if (t.isDouble()) {
        out.append("out.writeDouble(this." + f.name() + ");");
        in.append("this." + f.name() + " = in.readDouble();");
      } else if (t.isFloat()) {
        out.append("out.writeFloat(this." + f.name() + ");");
        in.append("this." + f.name() + " = in.readFloat();");
      } else if (t.isInt()) {
        out.append("out.writeInt(this." + f.name() + ");");
        in.append("this." + f.name() + " = in.readInt();");
      } else if (t.isLong()) {
        out.append("out.writeLong(this." + f.name() + ");");
        in.append("this." + f.name() + " = in.readLong();");
      } else if (t.isShort()) {
        out.append("out.writeShort(this." + f.name() + ");");
        in.append("this." + f.name() + " = in.readShort();");
      } else if (ts.isSubtype(t, ts.JavaInlineable())) {
        out.append("$writeInline(out, this." + f.name() + ");");
        in.append("this." + f.name() + " = (%T) in.readObject();");
        inSubst.add(t);
      } else {
        out.append("$writeRef($getStore(), this." + f.name() + ", refTypes, "
            + "out, intraStoreRefs, interStoreRefs);");
        in.append("this." + f.name() + " = (" + f.declType().translate(null)
            + ") $readRef(" + f.declType().translate(null) + "._Proxy.class, "
            + "(fabric.common.RefTypeEnum) "
            + "refTypes.next(), in, store, intraStoreRefs, interStoreRefs);");
      }
    }

    ClassMember serialize =
        qq.parseMember("public void $serialize(java.io.ObjectOutput out, "
            + "java.util.List refTypes, java.util.List intraStoreRefs, "
            + "java.util.List interStoreRefs) throws java.io.IOException {"
            + "super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);"
            + out + " }");
    result.add(serialize);

    ClassMember deserialize =
        qq.parseMember(
            "public _Impl(fabric.worker.Store store, long onum, int version, "
                + "long expiry, long label, long accessLabel, java.io.ObjectInput in, "
                + "java.util.Iterator refTypes, java.util.Iterator intraStoreRefs, "
                + "java.util.Iterator interStoreRefs) "
                + "throws java.io.IOException, java.lang.ClassNotFoundException {"
                + "super(store, onum, version, expiry, label, accessLabel, in, "
                + "refTypes, intraStoreRefs, interStoreRefs);" + in + " }",
            inSubst);
    result.add(deserialize);

    return result;
  }

  private ClassMember makeCopyAppStateFrom(ProxyRewriter pr,
      List<ClassMember> members) {
    QQ qq = pr.qq();
    StringBuilder body = new StringBuilder();
    String implType = node().type().translate(null) + "._Impl";

    // Determine the list of fields to copy.
    for (ClassMember m : members) {
      if (m instanceof FieldDecl) {
        FieldDecl f = (FieldDecl) m;
        body.append("this." + f.name() + " = src." + f.name() + ";");
      }
    }

    if (body.length() == 0) return null;

    return qq
        .parseMember("public void $copyAppStateFrom(fabric.lang.Object._Impl other) {"
            + "super.$copyAppStateFrom(other);"
            + implType
            + " src = ("
            + implType + ") other;" + body + " }");
  }

  @Override
  public List<ClassMember> interfaceMember(ProxyRewriter pr, ClassDecl parent) {
    ClassDecl decl = node();
    decl = decl.flags(ProxyRewriter.toPublic(decl.flags()));
    return Collections.singletonList((ClassMember) decl);
  }

  @Override
  public Node rewriteThreads(ThreadRewriter tr) {
    ClassDecl decl = node();
    ClassType type = decl.type();
    if (type == null || !tr.shouldRewrite(type))
      return super.rewriteThreads(tr);

    // Rewrite to implement fabric.common.FabricThread.
    QQ qq = tr.qq();
    NodeFactory nf = tr.nodeFactory();
    FabILTypeSystem ts = tr.typeSystem();
    List<TypeNode> interfaces = new ArrayList<>(decl.interfaces());
    interfaces.add(nf.CanonicalTypeNode(Position.compilerGenerated(),
        ts.FabricThread()));
    ClassDecl result = decl.interfaces(interfaces);

    // Add the transaction manager field and accessors.
    ClassBody body = result.body();
    body =
        body.addMember(qq
            .parseMember("private fabric.worker.transaction.TransactionManager $tm;"));
    body =
        body.addMember(qq
            .parseMember("public final fabric.worker.transaction.TransactionManager "
                + "getTransactionManager() { return $tm; }"));
    body =
        body.addMember(qq
            .parseMember("public final void "
                + "setTransactionManager(fabric.worker.transaction.TransactionManager tm) "
                + "{$tm = tm;}"));

    // Add the start() method if one doesn't yet exist.
    if (type.methods("start", Collections.<Type> emptyList()).isEmpty()) {
      body =
          body.addMember(qq.parseMember("public void start() {"
              + "fabric.worker.transaction.TransactionManager.getInstance()"
              + ".registerThread(this); super.start();}"));
    }
    result = result.body(body);

    return result;
  }

  @Override
  public Node rewriteRemoteCalls(RemoteCallRewriter rr) {
    NodeFactory nf = rr.nodeFactory();
    FabILTypeSystem ts = rr.typeSystem();

    ClassDecl cd = node();
    if (!cd.name().equals("_Proxy")) {
      // Only translate the proxy class.
      return cd;
    }

    TypeNode tnClass = rr.qq().parseType("java.lang.Class");
    TypeNode tnObject = rr.qq().parseType("java.lang.Object");

    List<ClassMember> members = new ArrayList<>(cd.body().members().size());

    for (ClassMember cm : cd.body().members()) {
      members.add(cm);
      if (!(cm instanceof MethodDecl)) continue;

      MethodDecl md = (MethodDecl) cm;
      if (md.flags().isPublic() && !md.flags().isStatic()
          && md.name().endsWith("_remote")) {
        // Every public instance method has a wrapper method for remote calls.
        // XXX Generate a fabil remote call wrapper for each fabric remote call
        // wrapper.

        String realName = md.name().substring(0, md.name().length() - 7);
        List<Formal> realFormals = md.formals().subList(1, md.formals().size());
        // List<Formal> realFormals = md.formals();

        // First, use a static field to store the parameter types.
        String fieldName = "$paramTypes" + (freshTid++);

        // Skip the first formal in the Fabric remote wrapper
        List<Expr> formalTypes = new ArrayList<>(realFormals.size());
        for (Formal f : realFormals) {
          TypeNode tn = f.type();
          formalTypes.add(nf.ClassLit(Position.compilerGenerated(), tn));
        }

        Expr init;
        if (formalTypes.size() == 0) {
          init = nf.NullLit(Position.compilerGenerated());
        } else {
          init = nf.ArrayInit(Position.compilerGenerated(), formalTypes);
        }
        FieldDecl fd =
            nf.FieldDecl(Position.compilerGenerated(), Flags.STATIC.Public()
                .Final(), nf.ArrayTypeNode(Position.compilerGenerated(),
                tnClass), nf.Id(Position.compilerGenerated(), fieldName), init);
        members.add(fd);

        // Now create the wrapper method.
        List<Expr> locals = new ArrayList<>(realFormals.size());
        for (Formal f : realFormals) {
          locals.add(nf.Local(Position.compilerGenerated(), f.id()));
        }

        Expr args;
        if (locals.size() == 0) {
          args = nf.NullLit(Position.compilerGenerated());
        } else {
          args = nf.NewArray(Position.compilerGenerated(), tnObject, 1, // one-dimensional
              // array
              nf.ArrayInit(Position.compilerGenerated(), locals));
        }

        List<Expr> arguments = new ArrayList<>(4);
        arguments.add(nf.This(Position.compilerGenerated()));
        arguments.add(nf.StringLit(Position.compilerGenerated(), realName));
        arguments.add(nf.AmbExpr(Position.compilerGenerated(),
            nf.Id(Position.compilerGenerated(), fieldName)));
        arguments.add(args);

        Id rcId = nf.Id(Position.compilerGenerated(), "$remoteWorker");
        Formal remoteWorker =
            nf.Formal(
                Position.compilerGenerated(),
                Flags.FINAL,
                nf.CanonicalTypeNode(Position.compilerGenerated(),
                    ts.RemoteWorker()), rcId);

        Call call =
            nf.Call(Position.compilerGenerated(),
                nf.Local(Position.compilerGenerated(), rcId),
                nf.Id(Position.compilerGenerated(), "issueRemoteCall"),
                arguments);

        Stmt ret;
        Type retType = md.returnType().type();
        if (retType.isVoid()) {
          // void is also a primitive type!
          ret = nf.Eval(Position.compilerGenerated(), call);
        } else if (retType.isPrimitive()) {
          // Cannot cast Object to a primitive type directly
          PrimitiveType pt = (PrimitiveType) retType;
          ret =
              rr.qq().parseStmt(
                  " return (" + pt.wrapperTypeString(ts) + ")%E;", call);
        } else {
          Expr castExpr = call;
          TypeNode returnType = md.returnType();
          if (ts.isFabricReference(returnType)) {
            // Do a little dance to get the exact proxy.
            QQ qq = rr.qq();
            castExpr =
                qq.parseExpr("fabric.lang.Object._Proxy.$getProxy(%E)",
                    castExpr);
          }

          ret =
              nf.Return(Position.compilerGenerated(), nf.Cast(
                  Position.compilerGenerated(), md.returnType(), castExpr));
        }

        List<Stmt> catchStmts = new ArrayList<>();
        catchStmts.add(rr.qq().parseStmt(
            "java.lang.Throwable $t = $e.getCause();"));
        // We need to catch RemoteCallException, and rethrow the cause.
        for (TypeNode exception : md.throwTypes()) {
          catchStmts.add(rr.qq().parseStmt(
              "if ($t instanceof %T) throw (%T)$t;", exception, exception));
        }
        catchStmts.add(rr.qq().parseStmt(
            "throw new fabric.common.exceptions.InternalError($e);"));

        Stmt tryCatch =
            rr.qq().parseStmt(
                "try {\n" + "  %S\n" + "}\n" + "catch (%T $e) {\n" + "  %LS\n"
                    + "}", ret, ts.RemoteCallException(), catchStmts);

        List<Formal> newFormals = new ArrayList<>(md.formals().size() + 1);
        newFormals.add(remoteWorker);
        newFormals.addAll(md.formals());
        MethodDecl wrapper =
            nf.MethodDecl(Position.compilerGenerated(), Flags.PUBLIC,
                md.returnType(),
                nf.Id(Position.compilerGenerated(), realName + "$remote"),
                newFormals, md.throwTypes(),
                nf.Block(Position.compilerGenerated(), tryCatch));

        members.add(wrapper);
      }
    }

    return cd.body(nf.ClassBody(Position.compilerGenerated(), members));
  }

  /**
   * Makes a method for calling any memoized method's nonmemoized body by name.
   */
  private MethodDecl makeNonMemoizedCaller(MemoizedMethodRewriter mmr,
      List<MethodDecl> methods) {
    QQ qq = mmr.qq();
    FabILTypeSystem ts = mmr.typeSystem();

    Stmt body =
        qq.parseStmt("throw new InternalError(\"Unknown memoized call!\");");
    for (MethodDecl method : methods) {
      String argsList = "";
      boolean first = true;
      /* TODO: Do check for number of arguments. */
      for (int count = 0; count < method.formals().size(); count++) {
        Formal formal = method.formals().get(count);
        if (!first) {
          argsList += ", ";
        }
        first = false;
        if (formal.type().type().isPrimitive()) {
          argsList +=
              "(" + ts.wrapperTypeString((PrimitiveType) formal.type().type())
                  + ") arguments[" + count + "]";
        } else {
          argsList += "(" + formal.type() + ") arguments[" + count + "]";
        }
      }

      String methodName =
          method.memberInstance().container().toString() + "."
              + method.methodInstance().signature();
      body =
          qq.parseStmt("if (methodName.equals(\"" + methodName + "\")) {\n"
              + "  return this." + method.name() + "$NonMemoized(" + argsList
              + ");\n" + "} else {\n" + "  %S\n" + "}", body);
    }

    return (MethodDecl) qq
        .parseMember(
            "public java.lang.Object $callNonMemoized(String methodName, java.lang.Object[] arguments) {\n"
                + "  %S\n" + "}", body);
  }

  private MethodDecl makeMemoizedMethod(MemoizedMethodRewriter mmr,
      MethodDecl md) {
    if (md.body() == null) return md;
    QQ qq = mmr.qq();
    NodeFactory nf = mmr.nodeFactory();
    FabILTypeSystem ts = mmr.typeSystem();
    Position CG = Position.compilerGenerated();

    TypeNode callInstanceType =
        nf.TypeNodeFromQualifiedName(CG, "fabric.worker.memoize.CallInstance");
    TypeNode callResultType =
        nf.TypeNodeFromQualifiedName(CG,
            "fabric.worker.memoize.WarrantiedCallResult");
    Type returnType = md.returnType().type();
    Type wrappedReturnType = returnType;
    if (returnType.isPrimitive()) {
      try {
        wrappedReturnType =
            ts.typeForName(ts.wrapperTypeString((PrimitiveType) returnType));
      } catch (SemanticException e) {
        System.err.println("Tried to wrap type " + returnType);
      }
    }

    int count = 0;
    String argList = "";
    String unwrappedArgList = "";
    List<Stmt> finals = new ArrayList<Stmt>(md.formals().size());
    List<Stmt> unpacks = new ArrayList<Stmt>(md.formals().size());
    for (Formal f : md.formals()) {
      argList += ", ";
      if (ts.isJavaInlineable(f.type().type()) || f.type().type().isPrimitive()) {
        argList += "fabric.lang.WrappedJavaInlineable.$wrap(" + f.name() + ")";
      } else {
        argList += f.name();
      }

      finals.add(qq.parseStmt("final %T $arg" + count + " = %s;", f.type(),
          f.name()));
      unpacks
          .add(qq.parseStmt("%T %s = $arg" + count + ";", f.type(), f.name()));

      if (count != 0) {
        unwrappedArgList += ", ";
      }
      unwrappedArgList += f.name();

      count++;
    }
    finals.add(qq.parseStmt("final %T $oldThis = this;", md.memberInstance()
        .container()));

    Stmt callCreate =
        qq.parseStmt("final %T $call = new %T(this, \""
            + md.memberInstance().container().toString() + "."
            + md.methodInstance().signature() + "\"" + argList + ");",
            callInstanceType, callInstanceType);

    /* Handle lookup before computing */
    Stmt callLookup =
        qq.parseStmt("%T $resultObj = this.$getStore().lookupCall($call);",
            callResultType);
    Stmt callUnpack =
        qq.parseStmt(
            "$cacheResult = (%T) $resultObj.getValueCopy();\n",
            wrappedReturnType, wrappedReturnType);
    if (ts.isJavaInlineable(wrappedReturnType)) {
      callUnpack =
          qq.parseStmt("$cacheResult = (%T) "
              + "fabric.lang.WrappedJavaInlineable.$unwrap($resultObj.getValueCopy());",
              wrappedReturnType);
    }

    Stmt checkLookup =
        qq.parseStmt(
            "if ($resultObj != null) {\n"
                + "  %T $cacheResult;\n"
                + "  %S\n"
                + "  fabric.worker.transaction.TransactionManager.getInstance().registerSemanticWarrantyUse($call, $resultObj);\n"
                + "  return $cacheResult;\n" + "} else {\n" + "  return "
                + md.name() + "$NonMemoized(" + unwrappedArgList + ");\n" + "}",
                wrappedReturnType, callUnpack);

    return (MethodDecl) md.body(nf.Block(CG, callCreate, callLookup,
        checkLookup));
  }

  @Override
  public Node rewriteMemoizedMethods(MemoizedMethodRewriter mmr) {
    ClassDecl cd = node();

    // Gather all memoized methods (static or not)
    List<MethodDecl> memoizedMethods =
        new ArrayList<MethodDecl>(cd.body().members().size());
    List<ClassMember> allMembers =
        new ArrayList<ClassMember>(cd.body().members().size() + 2);
    for (ClassMember cm : cd.body().members()) {
      if (!(cm instanceof MethodDecl)) {
        allMembers.add(cm);
      } else {
        MethodDecl method = (MethodDecl) cm;
        if (method.flags().contains(FabILFlags.MEMOIZED)) {
          // TODO: Error on static method explaining we don't do that yet.
          method = method.flags(method.flags().clear(FabILFlags.MEMOIZED));
          if (!cd.flags().isInterface()) {
            allMembers.add(makeMemoizedMethod(mmr, method));
            memoizedMethods.add(method);
            method = method.name(method.name() + "$NonMemoized");
          }
        }
        allMembers.add(method);
      }
    }

    // Make methods for calling memoized methods
    if (memoizedMethods.size() > 0 && !cd.flags().isInterface()) {
      ClassMember instanceCaller;
      instanceCaller = makeNonMemoizedCaller(mmr, memoizedMethods);
      allMembers.add(instanceCaller);
    }

    return cd.body(cd.body().members(allMembers));
  }

  @Override
  public Node addNoArgumentConstructor(NoArgConstructorWriter nacw) {
    ClassDecl cd = node();
    FabILTypeSystem ts = nacw.typeSystem();
    if (!cd.flags().isInterface() && ts.isFabricClass(cd.type())) {
      NodeFactory nf = nacw.nodeFactory();
      Position cg = Position.compilerGenerated();

      //Make constructor with no arguments.
      boolean needsNoArg = true;
      ConstructorInstance noArgIns = null;
      for (ClassMember cm : cd.body().members()) {
        if (cm instanceof ConstructorDecl) {
          ConstructorDecl consDecl = (ConstructorDecl) cm;
          if (consDecl.formals().size() == 0) {
            needsNoArg = false;
            noArgIns = consDecl.constructorInstance();
            break;
          }
        }
      }

      if (needsNoArg) {
        ArrayList<Stmt> body = new ArrayList<Stmt>();
        body.add(nf.SuperCall(cg, new ArrayList<Expr>()).constructorInstance(
            ts.constructorInstance(cg, cd.type().superType().toClass(),
                Flags.PUBLIC, new ArrayList<Type>(), new ArrayList<Type>())));
        ConstructorDecl noArgCons =
            nf.ConstructorDecl(cg, Flags.PUBLIC, nf.Id(cg, cd.name()),
                new ArrayList<Formal>(), new ArrayList<TypeNode>(),
                nf.Block(cg, body));
        noArgCons =
            noArgCons.constructorInstance(ts.constructorInstance(cg, cd.type(),
                Flags.PUBLIC, new ArrayList<Type>(), new ArrayList<Type>()));
        noArgIns = noArgCons.constructorInstance();
        cd = cd.body(cd.body().addMember(noArgCons));
        cd.type().addConstructor(noArgCons.constructorInstance());
      }

      // We shouldn't have to create copies of abstract classes directly, so
      // this is fine.
      if (!cd.flags().isAbstract()) {
        //Now add factory method to use dynamically.
        New newCall = null;
        if (cd.type().isInnerClass()) {
          //If it's an inner class, you need a qualifier for later compiler passes
          //to operate correctly.
          newCall =
              nf.New(cg,
                  nf.This(cg, nf.CanonicalTypeNode(cg, cd.type().container())),
                  nf.CanonicalTypeNode(cg, cd.type()), new ArrayList<Expr>());
        } else {
          newCall =
              nf.New(cg, nf.CanonicalTypeNode(cg, cd.type()),
                  new ArrayList<Expr>());
        }
        newCall = (New) newCall.constructorInstance(noArgIns).type(cd.type());
        ArrayList<Stmt> factoryBody = new ArrayList<Stmt>();
        factoryBody.add(nf.Return(cg, newCall));
        MethodDecl factoryMethod =
            nf.MethodDecl(cg, Flags.PUBLIC,
                nf.CanonicalTypeNode(cg, ts.FObject()),
                nf.Id(cg, "$makeBlankCopy"), new ArrayList<Formal>(),
                new ArrayList<TypeNode>(), nf.Block(cg, factoryBody));
        factoryMethod =
            factoryMethod.methodInstance(ts.methodInstance(cg, cd.type(),
                Flags.PUBLIC, ts.FObject(), "$makeBlankCopy",
                new ArrayList<Type>(), new ArrayList<Type>()));
        cd = cd.body(cd.body().addMember(factoryMethod));
        cd.type().addMethod(factoryMethod.methodInstance());
      }
    }
    return cd;
  }

  private MethodDecl makeCopyConstructor(ProxyRewriter pr,
      List<ClassMember> members) {
    ClassDecl cd = node();
    FabILTypeSystem ts = pr.typeSystem();
    QQ qq = pr.qq();
    NodeFactory nf = pr.nodeFactory();
    Position cg = Position.compilerGenerated();

    ArrayList<Stmt> bodyStmts = new ArrayList<Stmt>();
    bodyStmts.add(qq.parseStmt(cd.type().toString() + "._Impl copy = null;"));
    bodyStmts.add(qq.parseStmt("if (oldToNew.containsKey(this.$getOnum())) {\n"
        + "  copy = (" + cd.type().toString()
        + "._Impl) oldToNew.get(this.$getOnum());\n" + "} else {\n"
        + "  copy = (" + cd.type().toString()
        + "._Impl) this.$makeBlankCopy().fetch();\n"
        + "  oldToNew.put(this.$getOnum(), copy);\n" + "}"));
    bodyStmts.add(qq.parseStmt("super.$makeSemiDeepCopy(oldSet, oldToNew);"));

    Set<String> hasSet = new HashSet<String>();
    for (ClassMember cm : members) {
      if (!(cm instanceof MethodDecl)) continue;
      MethodDecl md = (MethodDecl) cm;
      if (md.name().startsWith("set$")) {
        hasSet.add(md.name().substring(4));
      }
    }

    for (ClassMember cm : members) {
      if (!(cm instanceof FieldDecl)) continue;
      FieldDecl fd = (FieldDecl) cm;
      if (fd.flags().isStatic()) continue;
      if (!ts.isFabricClass(fd.declType())
          || ts.isJavaInlineable(fd.declType())) {
        if (hasSet.contains(fd.name())) {
          bodyStmts.add(qq.parseStmt("copy.set$" + fd.id() + "(this.get$"
              + fd.id() + "());"));
        } else {
          bodyStmts.add(qq.parseStmt("copy." + fd.id() + " = this.get$"
              + fd.id() + "();"));
        }
      } else if (hasSet.contains(fd.name())) {
        String bodyStr =
            "if (this.get$" + fd.id() + "() == null) {\n" + "  copy.set$"
                + fd.id() + "(null);\n"
                + "} else if (oldSet.contains(this.get$" + fd.id()
                + "().$getOnum())) {\n"
                + "  if (oldToNew.containsKey(this.get$" + fd.id()
                + "().$getOnum())) {\n" + "    copy.set$" + fd.id()
                + "((%T) oldToNew.get(this.get$" + fd.id()
                + "().$getOnum()));\n" + "  } else {\n" + "    copy.set$"
                + fd.id() + "((%T) this.get$" + fd.id()
                + "().$makeSemiDeepCopy(oldSet, oldToNew));\n" + "  }\n"
                + "} else {\n" + "  copy.set$" + fd.id() + "(this.get$"
                + fd.id() + "());\n" + "}";
        bodyStmts.add(qq.parseStmt(bodyStr, fd.type(), fd.type()));
      } else {
        String bodyStr =
            "if (this.get$" + fd.id() + "() == null) {\n" + "  copy." + fd.id()
                + " = null;\n" + "} else if (oldSet.contains(this.get$"
                + fd.id() + "().$getOnum())) {\n"
                + "  if (oldToNew.containsKey(this.get$" + fd.id()
                + "().$getOnum())) {\n" + "    copy." + fd.id()
                + " = (%T) oldToNew.get(this.get$" + fd.id()
                + "().$getOnum());\n" + "  } else {\n" + "    copy." + fd.id()
                + " = (%T) this.get$" + fd.id()
                + "().$makeSemiDeepCopy(oldSet, oldToNew);\n" + "  }\n"
                + "} else {\n" + "  copy." + fd.id() + " = this.get$" + fd.id()
                + "();\n" + "}";
        bodyStmts.add(qq.parseStmt(bodyStr, fd.type(), fd.type()));
      }
    }
    bodyStmts.add(qq.parseStmt("return (%T) copy.$makeProxy();", cd.type()));

    ArrayList<Formal> copyFormals = new ArrayList<Formal>();
    ArrayList<Type> argTypes = new ArrayList<Type>();
    Formal arg1 =
        nf.Formal(cg, Flags.NONE,
            nf.TypeNodeFromQualifiedName(cg, "fabric.common.util.LongSet"),
            nf.Id(cg, "oldSet"));
    Type arg1Type = null;
    try {
      arg1Type = ts.typeForName("fabric.common.util.LongSet");
    } catch (SemanticException e) {
    }
    arg1 =
        arg1.localInstance(ts.localInstance(cg, Flags.NONE, arg1Type,
            arg1.name()));
    copyFormals.add(arg1);
    argTypes.add(arg1Type);

    Formal arg2 =
        nf.Formal(cg, Flags.NONE,
            nf.TypeNodeFromQualifiedName(cg, "fabric.common.util.LongKeyMap"),
            nf.Id(cg, "oldToNew"));
    Type arg2Type = null;
    try {
      arg2Type = ts.typeForName("fabric.common.util.LongKeyMap");
    } catch (SemanticException e) {
    }
    arg2 =
        arg2.localInstance(ts.localInstance(cg, Flags.NONE, arg2Type,
            arg2.name()));
    copyFormals.add(arg2);
    argTypes.add(arg2Type);

    MethodDecl copyMethod =
        nf.MethodDecl(cg, Flags.PUBLIC, nf.CanonicalTypeNode(cg, cd.type()),
            nf.Id(cg, "$makeSemiDeepCopy"), copyFormals,
            new ArrayList<TypeNode>(), nf.Block(cg, bodyStmts));

    return copyMethod;
  }

  @Override
  public ClassDecl node() {
    return (ClassDecl) super.node();
  }

  private ClassMemberExt ext(ClassMember m) {
    return (ClassMemberExt) m.ext();
  }

  private static int freshTid = 0;
}
