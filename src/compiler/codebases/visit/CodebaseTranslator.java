/**
 * Copyright (C) 2010-2013 Fabric project group, Cornell University
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
package codebases.visit;

import java.net.URI;

import polyglot.ast.Import;
import polyglot.ast.NodeFactory;
import polyglot.ast.SourceFile;
import polyglot.frontend.Job;
import polyglot.frontend.TargetFactory;
import polyglot.types.TypeSystem;
import polyglot.util.CodeWriter;
import polyglot.visit.Translator;
import codebases.frontend.CodebaseSource;
import codebases.frontend.ExtensionInfo;

public class CodebaseTranslator extends Translator {
  protected final ExtensionInfo extInfo;

  public CodebaseTranslator(Job job, TypeSystem ts, NodeFactory nf,
      TargetFactory tf) {
    super(job, ts, nf, tf);
    this.extInfo = (ExtensionInfo) job.extensionInfo();
  }

  public String namespaceToJavaPackagePrefix(URI ns) {
    return extInfo.namespaceToJavaPackagePrefix(ns);
  }

  @Override
  protected void writeHeader(SourceFile sfn, CodeWriter w) {
    // XXX: HACK -- The translator hasn't entered the scope of the file yet,
    // so we basically are inlining what translate() would do.
    URI ns = ((CodebaseSource) sfn.source()).canonicalNamespace();
    if (sfn.package_() != null) {
      w.write("package ");
      w.write(namespaceToJavaPackagePrefix(ns));
      sfn.package_().del().translate(w, this);
      w.write(";");
      w.newline(0);
      w.newline(0);
    } else {
      ExtensionInfo extInfo = (ExtensionInfo) job.extensionInfo();
      if (!ns.equals(extInfo.localNamespace())) {
        String pkgName = extInfo.namespaceToJavaPackagePrefix(ns);
        w.write("package ");
        w.write(pkgName);
        w.write(";");
        w.newline(0);
        w.newline(0);
      }
    }

    boolean newline = false;
    for (String pkg : ts.defaultPackageImports()) {
      w.write("import ");
      w.write(pkg + ".*;");
      w.newline(0);
    }
    for (Import imp : sfn.imports()) {
      imp.del().translate(w, this);
      newline = true;
    }

    if (newline) {
      w.newline(0);
    }
  }
}
