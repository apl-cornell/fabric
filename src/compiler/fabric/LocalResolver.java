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
package fabric;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import polyglot.main.Report;
import polyglot.types.Importable;
import polyglot.types.LazyClassInitializer;
import polyglot.types.NoClassException;
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import codebases.frontend.ExtensionInfo;
import codebases.types.NamespaceResolver;
import codebases.types.SimpleResolver;

/**
 * 
 */
public class LocalResolver extends SimpleResolver implements NamespaceResolver {
  private static final Collection<String> TOPICS;
  static {
    TOPICS = new ArrayList<String>(2);
    TOPICS.add(Report.types);
    TOPICS.add(Report.resolver);
  }

  protected NamespaceResolver platform;

  /**
   */
  public LocalResolver(ExtensionInfo extInfo, URI namespace,
      NamespaceResolver parent, Map<String, URI> codebaseAliases) {
    super(extInfo, namespace, parent, codebaseAliases);
    this.platform = extInfo.typeSystem().platformResolver();

  }

  @Override
  public Importable findImpl(String name) throws SemanticException {
    // Codebases may never resolve platform types, so always resolve against
    // the platformResolver first.
    ParsedClassType ct = null;
    try {
      ct = (ParsedClassType) platform.find(name);
    } catch (NoClassException e) {
    }
    if (ct == null) {
      return super.findImpl(name);
    }

    LazyClassInitializer init = (LazyClassInitializer) ct.initializer();
    // Is this type from a raw class?
    if (init.fromClassFile()) {
      if (Report.should_report(TOPICS, 4))
        Report.report(3, "[" + namespace + "] "
            + "NamespaceResolver_c: found raw class for: " + name);
    }
    return ct;
  }

}
