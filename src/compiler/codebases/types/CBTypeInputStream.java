/**
 * Copyright (C) 2010-2012 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
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

  public CBTypeInputStream(InputStream in, TypeSystem ts,
      Map<Object, Object> cache) throws IOException {
    super(in, ts, cache);
    this.ts = (CodebaseTypeSystem) ts;
  }

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
