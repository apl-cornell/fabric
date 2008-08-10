package fabil.extension;

import java.util.Collections;
import java.util.List;

import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.Initializer;
import polyglot.ast.Stmt;
import polyglot.types.Flags;
import fabil.visit.ProxyRewriter;

public class InitializerExt_c extends ClassMemberExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabil.extension.ClassMemberExt_c#implMember(fabil.visit.ProxyRewriter,
   *      polyglot.ast.ClassDecl)
   */
  @Override
  public List<ClassMember> implMember(ProxyRewriter pr, ClassDecl parent) {
    Initializer init = node();

    if (init.flags().isStatic()) return super.implMember(pr, parent);
    return Collections.singletonList((ClassMember) init);
  }

  /*
   * (non-Javadoc)
   *
   * @see fabil.extension.ClassMemberExt_c#staticImplInitMember(fabil.visit.ProxyRewriter)
   */
  @Override
  public List<Stmt> staticImplInitMember(ProxyRewriter pr) {
    Initializer init = node();
    Flags flags = init.flags();

    if (!flags.isStatic()) return super.staticImplInitMember(pr);
    
    return Collections.singletonList((Stmt) init.body());
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.Ext_c#node()
   */
  @Override
  public Initializer node() {
    return (Initializer) super.node();
  }
}
