package fabil.types;

import polyglot.frontend.Source;
import polyglot.types.ImportTable;
import polyglot.types.Package;
import polyglot.types.TypeSystem;

public interface CodebaseTypeSystem extends TypeSystem {
   ImportTable importTable(Source source, Package pkg);
}
