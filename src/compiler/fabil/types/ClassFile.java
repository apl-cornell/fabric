package fabil.types;

import java.io.File;
import java.security.MessageDigest;

import fabric.common.Crypto;

import polyglot.frontend.ExtensionInfo;

/**
 * Just like Polyglot's ClassFile, except this carries a secure hash of the
 * class's bytecode.
 */
public class ClassFile extends polyglot.types.reflect.ClassFile {
  
  protected byte[] hash;

  public ClassFile(File classFileSource, byte[] code, ExtensionInfo ext) {
    super(classFileSource, code, ext);
    
    MessageDigest digest = Crypto.digestInstance();
    digest.update(code);
    this.hash = digest.digest();
  }
  
  public byte[] getHash() {
    return hash;
  }

}
