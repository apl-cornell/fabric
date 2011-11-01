package fabric.tools.fabth;

import java.util.List;

public class Options extends polyglot.pth.Options {

  @Override
  protected void parseCommandLine(String[] args) {
    super.parseCommandLine(args);
  }

  public List getInputFilenames() {
    return inputFilenames;
  }

  public void setInputFilenames(List inputFilenames) {
    this.inputFilenames = inputFilenames;
  }

  public int getVerbosity() {
    return verbosity;
  }

  public void setVerbosity(int verbosity) {
    this.verbosity = verbosity;
  }

  public String getClasspath() {
    return classpath;
  }

  public void setClasspath(String classpath) {
    this.classpath = classpath;
  }

  public String getExtraArgs() {
    return extraArgs;
  }

  public void setExtraArgs(String extraArgs) {
    this.extraArgs = extraArgs;
  }

  public String getTestFilter() {
    return testFilter;
  }

  public void setTestFilter(String testFilter) {
    this.testFilter = testFilter;
  }

  public boolean isShowResultsOnly() {
    return showResultsOnly;
  }

  public void setShowResultsOnly(boolean showResultsOnly) {
    this.showResultsOnly = showResultsOnly;
  }

  public boolean isTestPreviouslyFailedOnly() {
    return testPreviouslyFailedOnly;
  }

  public void setTestPreviouslyFailedOnly(boolean testPreviouslyFailedOnly) {
    this.testPreviouslyFailedOnly = testPreviouslyFailedOnly;
  }

  public boolean isDeleteOutputFiles() {
    return deleteOutputFiles;
  }

  public void setDeleteOutputFiles(boolean deleteOutputFiles) {
    this.deleteOutputFiles = deleteOutputFiles;
  }

}
