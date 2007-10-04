package fabric.extension;

import java.util.ArrayList;
import java.util.List;

import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.FieldDecl;
import polyglot.ast.TypeNode;
import polyglot.qq.QQ;
import polyglot.types.Flags;
import fabric.visit.ProxyRewriter;

public class FieldDeclExt_c extends FabricExt_c implements ClassMemberExt {

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ClassMemberExt#implMember(fabric.visit.ProxyRewriter,
   *      polyglot.ast.ClassDecl)
   */
  public List<ClassMember> implMember(ProxyRewriter pr, ClassDecl parent) {
    FieldDecl fieldDecl = node();
    String fieldName = fieldDecl.name();

    // Make the field non-final and private.
    Flags fieldFlags = ProxyRewriter.toPrivate(fieldDecl.flags()).clearFinal();

    List<ClassMember> result = new ArrayList<ClassMember>();
    for (ClassMember m : accessors(pr))
      result.addAll(ext(m).implMember(pr, parent));
    result.add(fieldDecl.flags(fieldFlags).name(fieldName));
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ClassMemberExt#interfaceMember(fabric.visit.ProxyRewriter,
   *      polyglot.ast.ClassDecl)
   */
  public List<ClassMember> interfaceMember(ProxyRewriter pr, ClassDecl parent) {
    List<ClassMember> result = new ArrayList<ClassMember>();
    for (ClassMember m : accessors(pr))
      result.addAll(ext(m).interfaceMember(pr, parent));
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ClassMemberExt#proxyMember(fabric.visit.ProxyRewriter,
   *      polyglot.ast.ClassDecl)
   */
  public List<ClassMember> proxyMember(ProxyRewriter pr, ClassDecl parent) {
    FieldDecl fieldDecl = node();
    Flags flags = fieldDecl.flags();
    TypeNode type = fieldDecl.type();
    String name = fieldDecl.name();

    // TODO Need to handle static fields.

    // Make the method public, non-final and non-transient.
    flags = ProxyRewriter.toPublic(flags).clearTransient().clearFinal();

    // Figure out the call target for the delegates.
    String target = "((" + parent.type() + ".$Impl) fetch())";

    QQ qq = pr.qq();
    List<ClassMember> result = new ArrayList<ClassMember>(2);
    result.add(qq.parseMember(flags + " %T get$" + name + "() {" + "return "
        + target + ".get$" + name + "(); }", type));
    result.add(qq.parseMember(flags + " %T set$" + name + "(%T val) {"
        + "return " + target + ".set$" + name + "(val); }", type, type));

    // Add post-incrementer and post-decrementer proxies if type is numeric.
    if (type.type().isNumeric()) {
      result.add(qq.parseMember(flags + " %T postInc$" + name + "() {"
          + "return " + target + ".postInc$" + name + "(); }", type));
      result.add(qq.parseMember(flags + " %T postDec$" + name + "() {"
          + "return " + target + ".postDec$" + name + "(); }", type));
    }

    return result;
  }

  /**
   * Produces the getter, setter, and incrementer methods for the field.
   */
  protected List<ClassMember> accessors(ProxyRewriter pr) {
    FieldDecl fieldDecl = node();
    Flags flags = fieldDecl.flags();
    QQ qq = pr.qq();

    // TODO Need to handle static fields.

    TypeNode typeNode = fieldDecl.type();
    String name = fieldDecl.name();

    flags = flags.clearTransient().clearFinal();
    List<ClassMember> members = new ArrayList<ClassMember>();
    members.add(qq.parseMember(flags + " %T get$" + name + "() {"
        + "fabric.client.TransactionManager.INSTANCE" + ".registerRead(this);"
        + "return this." + name + "; }", typeNode));
    members.add(qq.parseMember(flags + " %T set$" + name + "(%T val) {"
        + "fabric.client.TransactionManager.INSTANCE" + ".registerWrite(this);"
        + "return this." + name + " = val; }", typeNode, typeNode));

    // Add post-incrementer and post-decrementer if type is numeric.
    if (typeNode.type().isNumeric()) {
      members.add(qq.parseMember(flags + " %T postInc$" + name + "() {"
          + "%T tmp = this.get$" + name + "();" + "this.set$" + name
          + "(tmp + 1);" + "return tmp; }", typeNode, typeNode, typeNode));
      members.add(qq.parseMember(flags + " %T postDec$" + name + "() {"
          + "%T tmp = this.get$" + name + "();" + "this.set$" + name
          + "(tmp - 1);" + "return tmp; }", typeNode, typeNode, typeNode));
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
