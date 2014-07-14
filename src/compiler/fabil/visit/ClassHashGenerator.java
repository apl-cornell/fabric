package fabil.visit;

import java.io.BufferedReader;
import java.io.IOException;

import polyglot.ast.ClassBody;
import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.Node;
import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.Job;
import polyglot.qq.QQ;
import polyglot.visit.NodeVisitor;
import codebases.frontend.CodebaseSource;
import fabil.types.FabILParsedClassType_c;

/**
 * Hashes the sources and stores them as static "$classHash" fields in the
 * generated interfaces.
 */
public class ClassHashGenerator extends NodeVisitor {

  public static String toSourceString(CodebaseSource src) throws IOException {
    StringBuilder result = new StringBuilder();
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(src.open());
      char[] buf = new char[1024];
      int r = 0;
      while ((r = reader.read(buf)) != -1)
        result.append(buf, 0, r);
    } finally {
      if (reader != null) reader.close();
      src.close();
    }
    return result.toString();
  }

  protected QQ qq;

  public ClassHashGenerator(Job job, ExtensionInfo extInfo) {
    this.qq = new QQ(extInfo);
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (n instanceof ClassDecl) {
      ClassDecl classDecl = (ClassDecl) n;
      boolean isInterface = classDecl.flags().isInterface();
      String name = classDecl.name();
      if (isInterface && !name.equals("_Static")) {
        // ClassDecl is an interface whose name is not "_Static". Assume this
        // is an interface corresponding to a Fabric type and generate the
        // $classHash static field.
        FabILParsedClassType_c ct = (FabILParsedClassType_c) classDecl.type();
        byte[] hash = ct.getClassHash();

        StringBuilder fieldDeclBuilder = new StringBuilder();
        fieldDeclBuilder
        .append("public static final byte[] $classHash = new byte[] {");
        for (int i = 0; i < hash.length; i++) {
          if (i > 0) fieldDeclBuilder.append(", ");
          fieldDeclBuilder.append(hash[i]);
        }
        fieldDeclBuilder.append("};");

        ClassMember fieldDecl = qq.parseMember(fieldDeclBuilder.toString());
        ClassBody body = classDecl.body().addMember(fieldDecl);
        return classDecl.body(body);
      }
    }
    return super.leave(old, n, v);
  }

}
