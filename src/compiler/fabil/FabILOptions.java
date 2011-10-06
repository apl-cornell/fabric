package fabil;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Map;

public interface FabILOptions {

  public int optLevel();

  public boolean signatureMode();
  
  public boolean dumpDependencies();

  public boolean createSkeleton();

  public String destinationStore();

  List<URI> classpath();

  List<URI> sourcepath();

  List<URI> signaturepath();

  List<URI> bootclasspath();

  Map<String, URI> codebaseAliases();

  File outputDirectory();  
  
}
