package fabil.types;

import polyglot.types.ParsedClassType;
import codebases.types.CodebaseClassType;

public interface FabILParsedClassType extends CodebaseClassType,
    ParsedClassType {
  public byte[] getClassHash();
}
