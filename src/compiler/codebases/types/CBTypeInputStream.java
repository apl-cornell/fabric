package codebases.types;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import polyglot.main.Report;
import polyglot.types.Importable;
import polyglot.types.PlaceHolder;
import polyglot.types.TypeObject;
import polyglot.types.TypeSystem;
import polyglot.util.TypeInputStream;

public class CBTypeInputStream extends TypeInputStream {
  private CodebaseTypeSystem ts;

  @SuppressWarnings("rawtypes")
  public CBTypeInputStream(InputStream in, TypeSystem ts, Map cache)
      throws IOException {
    super(in, ts, cache);
    this.ts = (CodebaseTypeSystem) ts;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void installInPlaceHolderCache(PlaceHolder p, TypeObject t) {
    cache.put(p, t);

    if (t instanceof Importable && p instanceof CBPlaceHolder) {
      CBPlaceHolder pp = (CBPlaceHolder) p;
      if (Report.should_report(Report.serialize, 2))
        Report.report(2, "Forcing " + pp.name() + " into system resolver");
      ts.namespaceResolver(pp.namespace()).replace(pp.name(), (Importable) t);
    }

    String s = "";
    if (Report.should_report(Report.serialize, 2)) {
      try {
        s = t.toString();
      } catch (NullPointerException e) {
        s = "<NullPointerException thrown>";
      }
    }

    if (Report.should_report(Report.serialize, 2)) {
      Report.report(2, "- Installing " + p + " -> " + s
          + " in place holder cache");
    }
  }
}
