package fabric.visit;

import polyglot.visit.TypeBuilder;
import fabil.frontend.CodebaseSource;

public interface CodebaseTypeBuilder {
   TypeBuilder pushSource(CodebaseSource cb);
   CodebaseSource currentSource();
}
