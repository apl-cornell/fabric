package fabil;

import polyglot.main.Report;

public class Topics {
  public static final String fabil = "fabil";
  public static final String mobile = "mobile";
  public static final String profile = "profile";
  static {
    Report.topics.add(fabil);
    Report.topics.add(mobile);
    Report.topics.add(profile);
  }

}
