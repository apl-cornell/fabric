package codebases.ast;

import java.util.List;

import polyglot.ast.SourceFile;

public interface CBSourceFile extends SourceFile {
  List<CodebaseDecl> codebaseDecls();

  CBSourceFile codebaseDecls(List<CodebaseDecl> codebaseDecls);
}
