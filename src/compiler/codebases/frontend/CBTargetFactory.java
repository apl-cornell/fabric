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
package codebases.frontend;

import java.net.URI;

import javax.tools.JavaFileManager.Location;
import javax.tools.JavaFileObject;

import polyglot.filemanager.FileManager;
import polyglot.frontend.Source;
import polyglot.frontend.TargetFactory;

public class CBTargetFactory extends TargetFactory {
  protected final ExtensionInfo extInfo;

  public CBTargetFactory(ExtensionInfo extInfo, FileManager fm,
      Location outDir, String outExt, boolean so) {
    super(fm, outDir, outExt, so);
    this.extInfo = extInfo;
  }

  @Override
  public JavaFileObject outputFileObject(String packageName, String className,
      Source source) {
    // Prefix java package name to create a unique class for this namespace.
    URI ns = ((CodebaseSource) source).canonicalNamespace();
    return super.outputFileObject(extInfo.namespaceToJavaPackagePrefix(ns)
        + packageName, className, source);
  }

}
