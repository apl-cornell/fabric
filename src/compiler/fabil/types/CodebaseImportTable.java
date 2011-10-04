package fabil.types;

import fabil.frontend.CodebaseSource;

//If ImportTable were an interface, this would extend it.
public interface CodebaseImportTable {
  CodebaseSource source();
}
