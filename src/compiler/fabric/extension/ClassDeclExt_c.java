package fabric.extension;

import java.util.*;

import polyglot.ast.*;
import polyglot.qq.QQ;
import polyglot.types.ClassType;
import polyglot.types.Flags;
import polyglot.types.MethodInstance;
import polyglot.types.Type;
import polyglot.util.Position;
import fabric.types.FabricTypeSystem;
import fabric.visit.ProxyRewriter;

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
   * @see fabric.extension.FabricExt_c#rewriteProxies(fabric.visit.ProxyRewriter)
   */
  @SuppressWarnings("unchecked")
  @Override
  public Node rewriteProxies(ProxyRewriter pr) {
    ClassDecl classDecl = node();

    // Only translate if we're processing a Fabric class.
    if (!pr.typeSystem().isFabric(classDecl.type())) return classDecl;

    NodeFactory nf = pr.nodeFactory();
    FabricTypeSystem ts = pr.typeSystem();

    if (classDecl.flags().isInterface()) {
      // Already an interface. Leave existing members alone, but insert a proxy
      // class.
      List<ClassMember> members =
          new ArrayList<ClassMember>(classDecl.body().members());
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
        interfaces = new ArrayList<TypeNode>(interfaces);
        interfaces.add(nf.CanonicalTypeNode(Position.compilerGenerated(), ts
            .FObject()));
      }

      // Flag the interface for serialization.
      return classDecl.body(classDecl.body().members(members)).interfaces(
          interfaces).ext(shouldSerializeType(true));
    }

    TypeNode superClass = classDecl.superClass();

    // Make the super class explicit.
    // TODO This is probably the wrong way to tell.
    if (superClass == null || "java.lang.Object".equals(superClass.toString())) {
      superClass =
          nf.CanonicalTypeNode(Position.compilerGenerated(), ts.FObject());
    }

    // The super class will be turned into an interface that we will extend.
    List<TypeNode> interfaces = new ArrayList<TypeNode>(classDecl.interfaces());
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
    List<ClassMember> members = new ArrayList<ClassMember>(oldMembers.size());
    for (ClassMember m : oldMembers) {
      members.addAll(ext(m).interfaceMember(pr, classDecl));
    }

    // Add the $Proxy, $Impl, and $Static classes, as well as the $static field.
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
  @SuppressWarnings("unchecked")
  private ClassDecl makeProxy(ProxyRewriter pr, ClassType superClass) {
    ClassDecl classDecl = node();

    // Rewrite the members.
    ClassBody body = classDecl.body();
    List<ClassMember> oldMembers = body.members();
    List<ClassMember> members = new ArrayList<ClassMember>(oldMembers.size());
    for (ClassMember m : oldMembers)
      members.addAll(ext(m).proxyMember(pr, classDecl));

    // Create proxy methods based on the class instances of this class and all
    // the interfaces it implements.
    members.addAll(makeProxyMethods(pr, classDecl.type()));

    // Add constructors.
    QQ qq = pr.qq();

    if (!classDecl.flags().isInterface()) {
      ClassMember implConstr =
          qq.parseMember("public $Proxy(" + classDecl.id() + ".$Impl impl) {"
              + "super(impl); }");
      members.add(implConstr);
    }

    ClassMember oidConstr =
        qq.parseMember("public $Proxy(fabric.client.Core core, long onum) {"
            + "super(core, onum); }");
    members.add(oidConstr);

    // Create the class declaration.
    ClassDecl result =
        qq.parseDecl("public static class $Proxy extends "
            + superClass.fullName() + ".$Proxy implements %T {%LM}", classDecl
            .type(), members);
    return result.type(classDecl.type());
  }

  /**
   * Generates proxy methods for methods contained in the given class type and
   * all the interfaces it implements.
   */
  @SuppressWarnings("unchecked")
  private List<ClassMember> makeProxyMethods(ProxyRewriter pr, ClassType ct) {
    List<ClassMember> result = new ArrayList<ClassMember>();

    Queue<ClassType> toVisit = new LinkedList<ClassType>();
    Set<ClassType> visitedTypes = new HashSet<ClassType>();
    visitedTypes.add(pr.typeSystem().Object());
    visitedTypes.add(pr.typeSystem().FObject());

    // Maps method names to sets of formal argument types. This prevents us
    // from generating duplicate methods.
    Map<String, Set<List<Type>>> translatedInstances =
        new HashMap<String, Set<List<Type>>>();

    // First populate the above data structures with the super class and the
    // type hierarchy above that. The proxy's super class will already have
    // methods for those types.
    toVisit.add(ct.superType().toClass());
    while (!toVisit.isEmpty()) {
      ClassType type = toVisit.remove();
      if (visitedTypes.contains(type)) continue;
      visitedTypes.add(type);

      List<MethodInstance> methods = type.methods();
      for (MethodInstance mi : methods) {
        // We actually want to generate proxies for static methods even if they
        // exist already in a super type. This way, static method dispatching
        // will work correctly.
        if (mi.flags().isStatic()) continue;

        String name = mi.name();
        Set<List<Type>> formalTypes = translatedInstances.get(name);
        if (formalTypes == null) {
          formalTypes = new HashSet<List<Type>>();
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

      List<MethodInstance> methods = type.methods();
      for (MethodInstance mi : methods) {
        String name = mi.name();
        List<Type> types = mi.formalTypes();

        // Ensure this isn't a duplicate method.
        Set<List<Type>> formalTypes = translatedInstances.get(name);
        if (formalTypes == null) {
          formalTypes = new HashSet<List<Type>>();
          translatedInstances.put(name, formalTypes);
        }

        if (formalTypes.contains(types)) continue;
        formalTypes.add(types);

        result.add(makeProxyMethod(pr, mi));
      }

      toVisit.addAll(type.interfaces());
    }

    return result;
  }

  /**
   * Generates a proxy method for the given method instance.
   */
  @SuppressWarnings("unchecked")
  private ClassMember makeProxyMethod(ProxyRewriter pr, MethodInstance mi) {
    FabricTypeSystem ts = pr.typeSystem();

    // The end result will be quasiquoted. Construct the quasiquoted string and
    // list of substitution arguments in tandem.
    StringBuffer methodDecl = new StringBuffer();
    List<Object> subst = new ArrayList<Object>();

    // Since the method will be implementing part of an interface, make the
    // method public and non-abstract.
    Flags flags = ProxyRewriter.toPublic(mi.flags()).clearAbstract();
    methodDecl.append(flags + " ");

    Type returnType = mi.returnType();
    if (returnType.isArray()) returnType = ts.toFArray(returnType.toArray());

    String name = mi.name();
    methodDecl.append("%T " + name + "(");
    subst.add(returnType);

    // Generate the formals list. While we're doing this, may as well generate
    // the args list too.
    StringBuffer args = new StringBuffer();
    List<Type> formalTypes = mi.formalTypes();
    int argCount = 1;
    for (Type t : formalTypes) {
      methodDecl.append((argCount == 1 ? "" : ", ") + "%T arg" + argCount);
      args.append((argCount == 1 ? "" : ", ") + "arg" + argCount);

      if (t.isArray()) t = ts.toFArray(t.toArray());
      subst.add(t);
      argCount++;
    }

    methodDecl.append(") ");

    // Figure out the throws list.
    List<Type> throwTypes = mi.throwTypes();
    if (!throwTypes.isEmpty()) {
      methodDecl.append("throws %LT ");
      subst.add(new ArrayList<Type>(throwTypes));
    }

    methodDecl.append("{ " + (returnType.isVoid() ? "" : "return "));

    // Figure out the call target.
    String implType = node().type().fullName();
    if (flags.isStatic()) {
      methodDecl.append(implType + ".$Impl");
    } else {
      methodDecl.append("((" + implType + ") fetch())");
    }

    // Call the delegate.
    methodDecl.append("." + name + "(" + args + "); }");

    QQ qq = pr.qq();
    return qq.parseMember(methodDecl.toString(), subst);
  }

  /**
   * Returns the Impl translation of the class declaration.
   */
  @SuppressWarnings("unchecked")
  private ClassDecl makeImpl(ProxyRewriter pr, ClassType superClass) {
    ClassDecl classDecl = node();
    ClassType classType = classDecl.type();

    // The Impl class will be public and static.
    Flags flags = ProxyRewriter.toPublic(classDecl.flags()).Static();

    // Rewrite the members.
    ClassBody body = classDecl.body();
    List<ClassMember> oldMembers = body.members();
    List<ClassMember> members = new ArrayList<ClassMember>(oldMembers.size());
    for (ClassMember m : oldMembers) {
      members.addAll(ext(m).implMember(pr, classDecl));
    }

    // Create and the $makeProxy method.
    QQ qq = pr.qq();
    ClassMember makeProxyDecl =
        qq.parseMember("protected fabric.lang.Object.$Proxy $makeProxy() {"
            + "return new " + classType.fullName() + ".$Proxy(this); }");
    members.add(makeProxyDecl);

    members.addAll(makeSerializers(pr, members));

    // Create the class declaration.
    ClassDecl result =
        qq.parseDecl(flags + " class $Impl extends " + superClass.fullName()
            + ".$Impl implements %T {%LM}", classType, members);
    return result.type(classDecl.type());
  }

  /**
   * Produces the $Static interface declaration for the static non-final fields.
   */
  @SuppressWarnings("unchecked")
  private List<ClassMember> makeStatic(ProxyRewriter pr, ClassType superClass) {
    ClassDecl classDecl = node();
    ClassType classType = classDecl.type();

    // Rewrite the members.
    ClassBody body = classDecl.body();
    List<ClassMember> oldMembers = body.members();
    List<ClassMember> interfaceMembers =
        new ArrayList<ClassMember>(oldMembers.size());
    List<ClassMember> proxyMembers =
        new ArrayList<ClassMember>(oldMembers.size());
    List<ClassMember> implMembers =
        new ArrayList<ClassMember>(oldMembers.size());

    for (ClassMember m : oldMembers) {
      interfaceMembers.addAll(ext(m).staticInterfaceMember(pr, classDecl));
      proxyMembers.addAll(ext(m).staticProxyMember(pr, classDecl));
      implMembers.addAll(ext(m).staticImplMember(pr, classDecl));
    }

    // Create the proxy constructor declarations and add them to the list of
    // static proxy members.
    QQ qq = pr.qq();
    ClassMember proxyConstructorDecl =
        qq.parseMember("public $Proxy(" + classType.fullName()
            + ".$Static.$Impl impl) { super(impl); }");
    proxyMembers.add(proxyConstructorDecl);
    proxyConstructorDecl =
        qq.parseMember("public $Proxy(fabric.client.Core core, long onum) {"
            + "super(core, onum); }");
    proxyMembers.add(proxyConstructorDecl);

    // Create the proxy declaration and add it to the list of interface members.
    ClassDecl proxyDecl =
        qq.parseDecl("class $Proxy extends " + superClass.fullName()
            + ".$Static.$Proxy implements " + classType.fullName()
            + ".$Static {%LM}", (Object) proxyMembers);
    interfaceMembers.add(proxyDecl);

    // Create the impl constructor declarations and add them to the list of
    // static impl members.
    ClassMember implConstructorDecl =
        qq.parseMember("public $Impl(fabric.client.Core core) "
            + "throws fabric.client.UnreachableCoreException {"
            + "super(core); }");
    implMembers.add(implConstructorDecl);
    implConstructorDecl =
        qq.parseMember("public $Impl(fabric.client.Core core, "
            + "fabric.common.Policy policy) "
            + "throws fabric.client.UnreachableCoreException {"
            + "super(core, policy); }");
    implMembers.add(implConstructorDecl);

    // Create the $makeProxy method declaration and add it to the list of static
    // impl members.
    ClassMember makeProxyDecl =
        qq.parseMember("protected fabric.lang.Object.$Proxy "
            + "$makeProxy() { return new " + classType.fullName()
            + ".$Static.$Proxy(this); }");
    implMembers.add(makeProxyDecl);

    // Create the impl declaration and add it to the list of interface members.
    ClassDecl implDecl =
        qq.parseDecl("class $Impl extends " + superClass.fullName()
            + ".$Static.$Impl implements " + classType.fullName()
            + ".$Static {%LM}", (Object) implMembers);
    interfaceMembers.add(implDecl);

    // Create the interface declaration.
    ClassDecl interfaceDecl =
        qq.parseDecl("interface $Static extends " + superClass.fullName()
            + ".$Static {%LM}", (Object) interfaceMembers);

    // Create the field declaration for $static.
    // TODO Where should the static object be located when it's created?
    FieldDecl fieldDecl =
        (FieldDecl) qq.parseMember(classType.fullName()
            + ".$Static $static = new " + classType.fullName()
            + ".$Static.$Impl(fabric.client.Client.getClient().getCore(0));");

    List<ClassMember> result = new ArrayList<ClassMember>(2);
    result.add(interfaceDecl);
    result.add(fieldDecl);
    return result;
  }

  private List<ClassMember> makeSerializers(ProxyRewriter pr,
      List<ClassMember> members) {
    List<ClassMember> result = new ArrayList<ClassMember>(3);
    List<FieldDecl> fields = new LinkedList<FieldDecl>();

    for (ClassMember m : members) {
      if (m instanceof FieldDecl) {
        FieldDecl f = (FieldDecl) m;

        if (!f.flags().isTransient()) {
          fields.add(f);
        }
      }
    }

    QQ qq = pr.qq();
    ClassMember numFieldsDecl =
        qq.parseMember("public int $numFields() {"
            + "return super.$numFields() + " + fields.size() + "; }");
    result.add(numFieldsDecl);

    StringBuilder header = new StringBuilder();
    StringBuilder out = new StringBuilder();
    StringBuilder in = new StringBuilder();

    for (FieldDecl f : fields) {
      Type t = f.declType();

      if (t.isBoolean()) {
        header.append("out.writeByte(0);");
        out.append("out.writeBoolean(this." + f.name() + ");");
        in.append("this." + f.name() + " = in.readBoolean();");
      } else if (t.isByte()) {
        header.append("out.writeByte(1);");
        out.append("out.writeByte(this." + f.name() + ");");
        in.append("this." + f.name() + " = in.readByte();");
      } else if (t.isChar()) {
        header.append("out.writeByte(2);");
        out.append("out.writeChar(this." + f.name() + ");");
        in.append("this." + f.name() + " = in.readChar();");
      } else if (t.isDouble()) {
        header.append("out.writeByte(3);");
        out.append("out.writeDouble(this." + f.name() + ");");
        in.append("this." + f.name() + " = in.readDouble();");
      } else if (t.isFloat()) {
        header.append("out.writeByte(4);");
        out.append("out.writeFloat(this." + f.name() + ");");
        in.append("this." + f.name() + " = in.readFloat();");
      } else if (t.isInt()) {
        header.append("out.writeByte(5);");
        out.append("out.writeInt(this." + f.name() + ");");
        in.append("this." + f.name() + " = in.readInt();");
      } else if (t.isLong()) {
        header.append("out.writeByte(6);");
        out.append("out.writeLong(this." + f.name() + ");");
        in.append("this." + f.name() + " = in.readLong();");
      } else if (t.isShort()) {
        header.append("out.writeByte(7);");
        out.append("out.writeShort(this." + f.name() + ");");
        in.append("this." + f.name() + " = in.readShort();");
      } else {
        header.append("out.writeByte(8);");
        out.append("$writeRef(out, this." + f.name() + ");");

        in.append("core = in.readLong();");
        in.append("onum = in.readLong();");

        if (!f.declType().toString().equals("java.lang.Object")) {
          in.append("if (core != 0 || onum != 0) " + "this." + f.name()
              + " = new " + f.declType() + ".$Proxy("
              + "fabric.client.Client.getClient().getCore(core), onum);");
        }
      }
    }

    ClassMember serializeHeader =
        qq.parseMember("public void $serializeHeader(java.io.DataOutput out) "
            + "throws java.io.IOException {" + "super.$serializeHeader(out);"
            + header + " }");
    result.add(serializeHeader);

    ClassMember serialize =
        qq.parseMember("public void $serialize(java.io.DataOutput out) "
            + "throws java.io.IOException {" + "super.$serialize(out);" + out
            + " }");
    result.add(serialize);

    ClassMember deserialize =
        qq.parseMember("public $Impl"
            + "(fabric.core.SerializedObject.DataInput in) "
            + "throws java.io.IOException {" + "super(in);"
            + "long core, onum;" + in + " }");
    result.add(deserialize);

    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ClassMemberExt#interfaceMember(fabric.visit.ProxyRewriter,
   *      polyglot.ast.ClassDecl)
   */
  @Override
  public List<ClassMember> interfaceMember(ProxyRewriter pr, ClassDecl parent) {
    return Collections.singletonList((ClassMember) node());
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.Ext_c#node()
   */
  @Override
  public ClassDecl node() {
    return (ClassDecl) super.node();
  }

  private ClassMemberExt ext(ClassMember m) {
    return (ClassMemberExt) m.ext();
  }
}
