package fabil;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.Map;

import fabric.common.FabricLocation;

public interface FabILOptions {

  public boolean signatureMode();

  public boolean dumpDependencies();

  public boolean createSkeleton();

  public String destinationStore();

  List<File> javaClasspathDirs();

  List<FabricLocation> classpath();

  List<FabricLocation> sourcepath();

  List<FabricLocation> filsignaturepath();

  List<FabricLocation> filbootclasspath();

  List<FabricLocation> bootclasspath();

  Map<String, FabricLocation> codebaseAliases();

  FabricLocation outputLocation();

  FabricLocation classOutputDirectory();

  boolean platformMode();

  boolean needWorker();

}
