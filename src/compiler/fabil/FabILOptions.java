package fabil;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Map;

public interface FabILOptions {

  public int optLevel();

  public boolean signatureMode();
  
  public boolean dumpDependencies();

  public boolean createJavaSkel();

  public boolean runWorker();

  public String destinationStore();

  List<URI> classpath();

  List<URI> sourcepath();

  Map<String, URI> codebaseAliases();

  List<URI> signaturepath();

  File outputDirectory();
  
  
}
