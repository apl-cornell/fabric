package fabil.parse;

import java.net.URI;

import polyglot.ast.Id;
import polyglot.ast.NodeFactory;
import polyglot.ast.PackageNode;
import polyglot.util.Position;
import codebases.types.CodebaseTypeSystem;

public class Name extends polyglot.parse.Name {
  public final CodebaseTypeSystem ts;
  public final URI ns;

  public Name(NodeFactory nf, CodebaseTypeSystem ts, URI ns,
      Position pos, Id name) {
    this(nf, ts, ns, pos, null, name);
  }

  public Name(NodeFactory nf, CodebaseTypeSystem ts, URI ns,
      Position pos, Name prefix, Id name) {
    super(nf, ts, pos, prefix, name);
    this.ns = ns;
    this.ts = ts;
  }

  @Override
  public PackageNode toPackage() {
    if (prefix == null) {
      return nf.PackageNode(pos, ts.createPackage(ns, null, name.id()));
    } else {
      return nf.PackageNode(pos,
          ts.createPackage(ns, prefix.toPackage().package_(), name.id()));
    }
  }

}
