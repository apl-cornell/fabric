package fabric.frontend;

import java.util.HashSet;
import java.util.Set;

import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;

import fabric.common.FabricLocation;

import polyglot.frontend.Source;
import polyglot.frontend.TargetFactory;

public class FabricTargetFactory extends TargetFactory {

  protected Set<FileObject> outputFiles;

  public FabricTargetFactory(StandardJavaFileManager fm, FabricLocation outLoc,
      String outExt, boolean so) {
    super(fm, outLoc, outExt, so);
    this.outputFiles = new HashSet<FileObject>();
  }

  @Override
  public JavaFileObject outputFileObject(String packageName, String className,
      Source source) {
    JavaFileObject fo = super.outputFileObject(packageName, className, source);
    outputFiles.add(fo);
    return fo;
  }

  public Set<FileObject> outputFiles() {
    return outputFiles;
  }
}
