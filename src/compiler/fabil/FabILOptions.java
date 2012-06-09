package fabil;

import java.io.File;
import java.util.Set;
import java.util.Map;

import fabric.common.FabricLocation;

public interface FabILOptions {

  public int optLevel();

  public boolean signatureMode();

  public boolean dumpDependencies();

  public boolean createSkeleton();

  public String destinationStore();

  Set<File> javaClasspathDirs();

  Set<FabricLocation> classpath();

  Set<FabricLocation> sourcepath();

  Set<FabricLocation> filsignaturepath();

  Set<FabricLocation> filbootclasspath();

  Set<FabricLocation> bootclasspath();

  Map<String, FabricLocation> codebaseAliases();

  FabricLocation outputDirectory();

  FabricLocation classOutputDirectory();

  boolean platformMode();

  boolean needWorker();
  
  boolean needMemClassObjects();

}
