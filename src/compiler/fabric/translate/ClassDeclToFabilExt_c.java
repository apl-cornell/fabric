package fabric.translate;

import java.util.ArrayList;
import java.util.List;

import fabil.ast.FabILNodeFactory;
import fabil.types.FabILTypeSystem;

import polyglot.ast.ClassBody;
import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.types.ClassType;
import polyglot.types.Flags;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import jif.translate.ClassDeclToJavaExt_c;
import jif.translate.JifToJavaRewriter;

public class ClassDeclToFabilExt_c extends ClassDeclToJavaExt_c {
  protected static final String jifConstructorTranslatedName(ClassType ct) {
    return ClassDeclToJavaExt_c.constructorTranslatedName(ct);
  }
  
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    ClassDecl cd = (ClassDecl)super.toJava(rw);

    FabILNodeFactory nf = (FabILNodeFactory)rw.nodeFactory();
    FabILTypeSystem ts = (FabILTypeSystem)rw.java_ts();

    TypeNode client = nf.CanonicalTypeNode(Position.compilerGenerated(), ts.Client());
    
    List<ClassMember> members = new ArrayList<ClassMember>(cd.body().members().size() + 1);
    members.add(nf.FieldDecl(Position.compilerGenerated(), 
        Flags.FINAL.Static(), 
        client, 
        nf.Id(Position.compilerGenerated(), 
              "client$"),
        nf.Call(Position.compilerGenerated(), 
                client, 
                nf.Id(Position.compilerGenerated(), 
                      "getClient"))));
    members.addAll(cd.body().members());
    
    return cd.body(cd.body().members(members));
  }

  @Override
  protected ClassBody addInitializer(ClassBody cb, JifToJavaRewriter rw) {
    List inits = new ArrayList(rw.getInitializations());
    if (!inits.isEmpty()) {
      FabILNodeFactory nf = (FabILNodeFactory)rw.nodeFactory();
      FabILTypeSystem ts = (FabILTypeSystem)rw.java_ts();
      
      List newInits = new ArrayList(inits.size() + 1);
      
      TypeNode client = nf.CanonicalTypeNode(Position.compilerGenerated(), ts.Client());
//      newInits.add(nf.LocalDecl(Position.compilerGenerated(), 
//                                Flags.FINAL, 
//                                client, 
//                                nf.Id(Position.compilerGenerated(), 
//                                      "client$"),
//                                nf.Call(Position.compilerGenerated(), 
//                                        client, 
//                                        nf.Id(Position.compilerGenerated(), 
//                                              "getClient"))));
      newInits.addAll(inits);
      inits = newInits;
    }
    rw.getInitializations().clear();
    return cb.addMember(rw.qq().parseMember("private void %s() { %LS }", 
                                            INITIALIZATIONS_METHOD_NAME,
                                            inits));
  }
}
