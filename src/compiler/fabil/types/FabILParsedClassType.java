package fabil.types;

import java.util.List;

import polyglot.types.ParsedClassType;
import codebases.types.CodebaseClassType;

public interface FabILParsedClassType extends CodebaseClassType,
ParsedClassType {
  public byte[] getClassHash();

  @Override
  public List<FabILParsedClassType> interfaces();
}
