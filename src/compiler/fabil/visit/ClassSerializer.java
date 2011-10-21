package fabil.visit;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import polyglot.ast.ClassDecl;
import polyglot.ast.Ext;
import polyglot.ast.NodeFactory;
import polyglot.main.Version;
import polyglot.types.TypeSystem;
import polyglot.util.ErrorQueue;
import codebases.frontend.ExtensionInfo;
import fabil.extension.ClassDeclExt_c;

public class ClassSerializer extends polyglot.visit.ClassSerializer {

  protected boolean sig_mode;

  public ClassSerializer(TypeSystem ts, NodeFactory nf, Date date,
      ErrorQueue eq, Version ver, boolean signatureMode) {
    super(ts, nf, date, eq, ver);
    //Replace TypeEncode with call to factory method. 
    this.te = ((ExtensionInfo)ts.extensionInfo()).typeEncoder();
    this.sig_mode = signatureMode;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public List createSerializationMembers(ClassDecl cd) {
    Ext ext = cd.ext();
    if (sig_mode || ext instanceof ClassDeclExt_c
        && ((ClassDeclExt_c) ext).shouldSerializeType()) {
      return super.createSerializationMembers(cd);
    }
    return Collections.emptyList();
  }

}
