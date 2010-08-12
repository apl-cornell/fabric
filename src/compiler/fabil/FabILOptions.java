package fabil;

public interface FabILOptions {

  public String constructSignatureClasspath();

  public String constructFabILClasspath();

  public String constructPostCompilerClasspath();

  public int optLevel();

  public boolean signatureMode();
  
  public boolean dumpDependencies();

  public boolean createJavaSkel();

}
