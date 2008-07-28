package fabil.extension;

import java.util.Collections;
import java.util.List;

import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.Initializer;
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
   * @see fabil.extension.ClassMemberExt_c#staticImplMember(fabil.visit.ProxyRewriter,
   *      polyglot.ast.ClassDecl)
   */
  @Override
  public List<ClassMember> staticImplMember(ProxyRewriter pr,
      ClassDecl classDecl) {
    Initializer init = node();
    Flags flags = init.flags();

    if (!flags.isStatic()) return super.staticImplMember(pr, classDecl);
    
    flags = flags.clearStatic();
    return Collections.singletonList((ClassMember) init.flags(flags));
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
