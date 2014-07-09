/**
 * Copyright (C) 2010-2014 Fabric project group, Cornell University
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
package fabric.translate;

import java.net.URI;

import jif.translate.JifToJavaRewriter;
import jif.translate.PackageNodeToJavaExt_c;
import jif.translate.ToJavaExt;
import polyglot.ast.Node;
import polyglot.ast.PackageNode;
import polyglot.types.Package;
import polyglot.types.SemanticException;
import codebases.types.CBPackage;
import fabil.types.FabILTypeSystem;
import fabric.ExtensionInfo;
import fabric.common.NSUtil;
import fabric.lang.Codebase;
import fabric.types.FabricTypeSystem;
import fabric.visit.FabricToFabilRewriter;

public class PackageNodeToFabilExt_c extends PackageNodeToJavaExt_c implements
    ToJavaExt {

  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    PackageNode n = (PackageNode) node();
    Package p = n.package_();
    FabricTypeSystem fab_ts = (FabricTypeSystem) rw.jif_ts();
    FabILTypeSystem fil_ts = (FabILTypeSystem) rw.java_ts();
    FabricToFabilRewriter ftfr = (FabricToFabilRewriter) rw;
    ExtensionInfo fab_ext = (ExtensionInfo) ftfr.jif_ts().extensionInfo();

    URI pkgNS = ((CBPackage) p).namespace();
    if (ftfr.fabIsPublished() && pkgNS.equals(fab_ext.localNamespace())) {

      Codebase cb = fab_ts.codebaseFromNS(pkgNS);
      pkgNS = NSUtil.namespace(cb);//getLocation(false, NSUtil.namespace(cb));
    }

    p = fil_ts.packageForName(pkgNS, p.fullName());
    return rw.java_nf().PackageNode(n.position(), p);
  }

}
