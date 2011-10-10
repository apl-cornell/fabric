package fabric.parse;

import java.net.URI;

import jif.parse.Amb;
import polyglot.ast.Id;
import polyglot.ast.PackageNode;
import polyglot.util.Position;
import codebases.types.CodebaseTypeSystem;

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
  public PackageNode toPackage() throws Exception {
    if (prefix == null) {
      return parser.nf.PackageNode(pos, ts.createPackage(ns, null, name));
    } else {
      return parser.nf.PackageNode(pos,
          ts.createPackage(ns, prefix.toPackage().package_(), name));
    }
  }

}
