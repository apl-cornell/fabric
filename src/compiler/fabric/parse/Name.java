package fabric.parse;

import java.net.URI;

import jif.parse.Amb;
import polyglot.ast.Id;
import polyglot.ast.PackageNode;
import polyglot.ast.QualifierNode;
import polyglot.ast.TypeNode;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import codebases.ast.CodebaseNode;
import codebases.ast.CodebaseNodeFactory;
import codebases.types.CodebaseTypeSystem;
import fabric.ast.FabricNodeFactory;

public class Name extends jif.parse.Name {
  public final CodebaseTypeSystem ts;
  public final URI ns;

  public Name(Grm parser, Position pos, Id name) throws Exception {
    this(parser,pos, name.id());
  }
  
  public Name(Grm parser, Position pos, String name) throws Exception {
    this(parser, pos, null, name);
  }

  public Name(Grm parser, Position pos, Amb prefix, Id name) throws Exception {
    this(parser, pos, prefix, name.id());
  }

  public Name(Grm parser, Position pos, Amb prefix, String name) throws Exception {
    super(parser, pos, prefix, name);
    this.ns = parser.ns;
    this.ts = parser.ts;
  }

  @Override
  //This should only be used for the package declaration
  public PackageNode toPackage() throws Exception {
    return (PackageNode) toQualifier();
//    Thread.dumpStack();
//    throw new UnsupportedOperationException("Use toQualifier instead:" + this);
//
//    if (prefix == null) {
//      return parser.nf.PackageNode(pos, ts.createPackage(this.ns, null, name));
//    } else {
//      return parser.nf.PackageNode(pos,
//          ts.createPackage(ns, prefix.toPackage().package_(), name));
//    }
  }
  public TypeNode toType() throws Exception {
    if (prefix == null) {
        return parser.nf.AmbTypeNode(pos, name);
    }
    
    return parser.nf.AmbTypeNode(pos, ((Name)prefix).toQualifier(), name);
}

  public QualifierNode toQualifier() throws Exception {
    if (prefix == null) {
      try {
        FabricNodeFactory nf = (FabricNodeFactory) parser.nf;
        URI cb = ts.namespaceResolver(ns).resolveCodebaseName(name);
        return nf.CodebaseNode(pos, ns, name, cb);
      }
      catch(SemanticException e) {}
      return parser.nf.PackageNode(pos, ts.createPackage(this.ns, null, name));
    } else {
      Name p = (Name)prefix;
      QualifierNode qn = p.toQualifier();
      if(qn instanceof CodebaseNode) {
        CodebaseNode cn = (CodebaseNode)qn;
        CodebaseNodeFactory nf = (CodebaseNodeFactory) parser.nf;
        return nf.CodebaseNode(pos, ns, cn.alias(), cn.externalNamespace(), 
            ts.createPackage(cn.externalNamespace(), null, name));
      }
      else if(qn instanceof PackageNode) {
        PackageNode pn = (PackageNode) qn;
        return parser.nf.PackageNode(pos, ts.createPackage(this.ns, pn.package_(), name));
      }
      else {
        throw new InternalCompilerError("Unexpected qualifier "+ qn); 
      }
    }
  }

}
