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
