package fabric.frontend;

import java.util.HashSet;
import java.util.Set;

import javax.tools.FileObject;
import javax.tools.JavaFileObject;

import polyglot.filemanager.FileManager;
import polyglot.frontend.Source;
import polyglot.frontend.TargetFactory;
import fabric.common.FabricLocation;

public class FabricTargetFactory extends TargetFactory {

  protected Set<FileObject> outputFiles;

  public FabricTargetFactory(FileManager fm, FabricLocation outLoc,
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
