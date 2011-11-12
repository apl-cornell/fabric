package fabric.frontend;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import polyglot.frontend.Source;
import polyglot.frontend.TargetFactory;

public class FabricTargetFactory extends TargetFactory {

  protected Set<File> outputFiles;
  public FabricTargetFactory(File outDir, String outExt, boolean so) {
    super(outDir, outExt, so);
    this.outputFiles = new HashSet<File>();
  }
    
  @Override
  public File outputFile(String packageName, String className, Source source) {
    File f = super.outputFile(packageName, className, source);
    outputFiles.add(f);
    return f;
  }

  public Set<File> outputFiles() {
    return outputFiles;
  }
}
