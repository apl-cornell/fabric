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
package codebases.types;

import java.io.IOException;
import java.security.MessageDigest;

import javax.tools.FileObject;

import polyglot.frontend.ExtensionInfo;
import fabric.common.Crypto;

/**
 * Just like Polyglot's ClassFile, except this carries a secure hash of the
 * class's bytecode.
 */

public class ClassFile_c extends polyglot.types.reflect.ClassFile_c implements
    ClassFile {

  protected byte[] hash;

  public ClassFile_c(FileObject classFileSource, byte[] code, ExtensionInfo ext)
      throws IOException {
    super(classFileSource, code, ext);

    MessageDigest digest = Crypto.digestInstance();
    digest.update(code);
    this.hash = digest.digest();
  }

  @Override
  public byte[] getHash() {
    return hash;
  }

}
