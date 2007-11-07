package fabric.visit;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import fabric.extension.ClassDeclExt_c;

import polyglot.ast.ClassDecl;
import polyglot.ast.Ext;
import polyglot.ast.NodeFactory;
import polyglot.main.Version;
import polyglot.types.TypeSystem;
import polyglot.util.ErrorQueue;

public class ClassSerializer extends polyglot.visit.ClassSerializer {

  public ClassSerializer(TypeSystem ts, NodeFactory nf, Date date,
      ErrorQueue eq, Version ver) {
    super(ts, nf, date, eq, ver);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.visit.ClassSerializer#createSerializationMembers(polyglot.ast.ClassDecl)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List createSerializationMembers(ClassDecl cd) {
    Ext ext = cd.ext();
    if (ext instanceof ClassDeclExt_c
        && ((ClassDeclExt_c) ext).shouldSerializeType())
      return super.createSerializationMembers(cd);
    return Collections.emptyList();
  }

}
