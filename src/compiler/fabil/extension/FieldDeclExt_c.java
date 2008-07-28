package fabil.extension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.FieldDecl;
import polyglot.ast.TypeNode;
import polyglot.qq.QQ;
import polyglot.types.Flags;
import fabil.visit.ProxyRewriter;

public class FieldDeclExt_c extends ClassMemberExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabil.extension.ClassMemberExt#implMember(fabil.visit.ProxyRewriter,
   *      polyglot.ast.ClassDecl)
   */
  @Override
  public List<ClassMember> implMember(ProxyRewriter pr, ClassDecl parent) {
    return implMember(pr, parent, false);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabil.extension.ClassMemberExt_c#staticImplMember(fabil.visit.ProxyRewriter,
   *      polyglot.ast.ClassDecl)
   */
  @Override
  public List<ClassMember> staticImplMember(ProxyRewriter pr, ClassDecl parent) {
    return implMember(pr, parent, true);
  }

  private List<ClassMember> implMember(ProxyRewriter pr, ClassDecl parent,
      boolean doStatic) {
    FieldDecl fieldDecl = node();
    String fieldName = fieldDecl.name();
    Flags flags = fieldDecl.flags();

    // Only handle fields that have the static flag properly set. Also, if we're
    // handling static fields, ignore final fields; those will be put in the
    // $Static interface.
    if (doStatic != flags.isStatic() || doStatic && flags.isFinal())
      return super.implMember(pr, parent);

    // Make the field private and non-static, non-final.
    // XXX why are we making the field private? breaks read/write optimization
    flags = flags.clearStatic().clearFinal();

    List<ClassMember> result = new ArrayList<ClassMember>();
    for (ClassMember m : accessors(pr))
      result.addAll(ext(m).implMember(pr, parent));
    result.add(fieldDecl.flags(flags).name(fieldName));
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabil.extension.ClassMemberExt#interfaceMember(fabil.visit.ProxyRewriter,
   *      polyglot.ast.ClassDecl)
   */
  @Override
  public List<ClassMember> interfaceMember(ProxyRewriter pr, ClassDecl parent) {
    // Omit static fields. These will be put in the $Static type.
    if (node().flags().isStatic()) return super.interfaceMember(pr, parent);

    List<ClassMember> result = new ArrayList<ClassMember>();
    for (ClassMember m : accessors(pr))
      result.addAll(ext(m).interfaceMember(pr, parent));
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabil.extension.ClassMemberExt#proxyMember(fabil.visit.ProxyRewriter,
   *      polyglot.ast.ClassDecl)
   */
  @Override
  public List<ClassMember> proxyMember(ProxyRewriter pr, ClassDecl parent) {
    return proxyMember(pr, parent, false);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabil.extension.ClassMemberExt_c#staticProxyMember(fabil.visit.ProxyRewriter,
   *      polyglot.ast.ClassDecl)
   */
  @Override
  public List<ClassMember> staticProxyMember(ProxyRewriter pr, ClassDecl parent) {
    return proxyMember(pr, parent, true);
  }

  private List<ClassMember> proxyMember(ProxyRewriter pr, ClassDecl parent,
      boolean doStatic) {
    FieldDecl fieldDecl = node();
    Flags flags = fieldDecl.flags();
    TypeNode type = fieldDecl.type();
    String name = fieldDecl.name();

    // Only handle fields that have the static flag properly set. Also, if we're
    // handling static fields, ignore final fields; those will be put in the
    // $Static interface.
    if (doStatic != flags.isStatic() || doStatic && flags.isFinal())
      return super.implMember(pr, parent);

    // Make the method public, non-final, non-static and non-transient.
    flags =
        ProxyRewriter.toPublic(flags).clearTransient().clearFinal()
            .clearStatic();

    // Figure out the call target for the delegates.
    String target =
        "((" + parent.type().fullName() + (doStatic ? ".$Static" : "")
            + ".$Impl) fetch())";

    QQ qq = pr.qq();
    List<ClassMember> result = new ArrayList<ClassMember>(4);
    result.add(qq.parseMember(flags + " %T get$" + name + "() {" + "return "
        + target + ".get$" + name + "(); }", type));

    if (!fieldDecl.flags().isFinal()) {
      result.add(qq.parseMember(flags + " %T set$" + name + "(%T val) {"
          + "return " + target + ".set$" + name + "(val); }", type, type));

      // Add post-incrementer and post-decrementer proxies if type is numeric.
      if (type.type().isNumeric()) {
        result.add(qq.parseMember(flags + " %T postInc$" + name + "() {"
            + "return " + target + ".postInc$" + name + "(); }", type));
        result.add(qq.parseMember(flags + " %T postDec$" + name + "() {"
            + "return " + target + ".postDec$" + name + "(); }", type));
      }
    }

    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabil.extension.ClassMemberExt_c#staticInterfaceMember(fabil.visit.ProxyRewriter,
   *      polyglot.ast.ClassDecl)
   */
  @Override
  public List<ClassMember> staticInterfaceMember(ProxyRewriter pr,
      ClassDecl parent) {
    FieldDecl fieldDecl = node();
    Flags flags = fieldDecl.flags();

    // Ignore non-static fields.
    if (!flags.isStatic()) return super.interfaceMember(pr, parent);

    // Include final static fields in the interface, ensuring that they're
    // neither protected nor private.
    if (flags.isFinal()) {
      return Collections.singletonList((ClassMember) fieldDecl.flags(flags
          .clearPrivate().clearProtected()));
    }

    List<ClassMember> result = new ArrayList<ClassMember>();
    for (ClassMember m : accessors(pr))
      result.addAll(ext(m).interfaceMember(pr, parent));
    return result;
  }

  /**
   * Produces the getter, setter, and incrementer methods for the field.
   */
  protected List<ClassMember> accessors(ProxyRewriter pr) {
    FieldDecl fieldDecl = node();
    Flags flags = fieldDecl.flags();
    QQ qq = pr.qq();

    TypeNode typeNode = fieldDecl.type();
    String name = fieldDecl.name();
    boolean finalField = flags.isFinal();

    flags = flags.clearTransient().clearFinal().clearStatic();
    List<ClassMember> members = new ArrayList<ClassMember>(4);
    String regRead =
        finalField ? ""
            : "fabric.client.transaction.TransactionManager.getInstance().registerRead(this);";
    members.add(qq.parseMember(flags + " %T get$" + name + "() {" + regRead
        + "return this." + name + "; }", typeNode));

    if (!finalField) {
      members.add(qq.parseMember(
          flags + " %T set$" + name + "(%T val) {"
              + "fabric.client.transaction.TransactionManager tm = "
              + "fabric.client.transaction.TransactionManager.getInstance();"
              + "boolean transactionCreated = tm.registerWrite(this);"
              + "this." + name + " = val;"
              + "if (transactionCreated) tm.commitTransaction();"
              + "return val; }", typeNode, typeNode));

      // Add post-incrementer and post-decrementer if type is numeric.
      if (typeNode.type().isNumeric()) {
        members.add(qq.parseMember(flags + " %T postInc$" + name + "() {"
            + "%T tmp = this.get$" + name + "();" + "this.set$" + name
            + "((%T) (tmp + 1));" + "return tmp; }", typeNode, typeNode,
            typeNode, typeNode));
        members.add(qq.parseMember(flags + " %T postDec$" + name + "() {"
            + "%T tmp = this.get$" + name + "();" + "this.set$" + name
            + "((%T) (tmp - 1));" + "return tmp; }", typeNode, typeNode,
            typeNode, typeNode));
      }
    }

    return members;
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.Ext_c#node()
   */
  @Override
  public FieldDecl node() {
    return (FieldDecl) super.node();
  }

  private ClassMemberExt ext(ClassMember m) {
    return (ClassMemberExt) m.ext();
  }
}
