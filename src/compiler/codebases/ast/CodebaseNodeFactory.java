package codebases.ast;

import java.util.List;

import polyglot.ast.Id;
import polyglot.ast.NodeFactory;
import polyglot.ast.PackageNode;
import polyglot.ast.SourceFile;
import polyglot.types.Package;
import polyglot.util.Position;
import fabric.lang.Codebase;

public interface CodebaseNodeFactory extends NodeFactory {
  CodebaseNode CodebaseNode(Position pos, Codebase c);

  SourceFile SourceFile(Position pos, PackageNode packageName, List codebases,
      List imports, List decls);

  CodebaseDecl CodebaseDecl(Position pos, Id name);
}
