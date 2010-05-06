package webapp;

public class PageStats {
  public int numReads;
  public int numUpdates;
  public int numCreates;
  public int numTransactions;
  public int transactionTime;
  public long pageLoadTime;
  public long appTime;
  public long transmissionTime;

  public PageStats() {}

  public int getNumReads() {
    return numReads;
  }

  public int getNumUpdates() {
    return numUpdates;
  }

  public int getNumCreates() {
    return numCreates;
  }

  public int getNumTransactions() {
    return numTransactions;
  }

  public int getTransactionTime() {
    return transactionTime;
  }

  public long getPageLoadTime() {
    return pageLoadTime;
  }

  public long getAppTime() {
    return appTime;
  }

  public long getTrasmissionTime() {
    return transmissionTime;
  }

  public String getSerialized() {
    StringBuilder str = new StringBuilder();
    str.append(numReads).append(',').append(numUpdates).append(',').append(
        numCreates);
    str.append(',').append(numTransactions).append(',').append(transactionTime);
    str.append(',').append(pageLoadTime).append(',').append(appTime);
    return str.toString();
  }

  public static PageStats fromSerialized(String str) {
    String[] splits = str.split(",");
    PageStats stats = new PageStats();
    int i = 0;
    stats.numReads = Integer.parseInt(splits[i++]);
    stats.numUpdates = Integer.parseInt(splits[i++]);
    stats.numCreates = Integer.parseInt(splits[i++]);
    stats.numTransactions = Integer.parseInt(splits[i++]);
    stats.transactionTime = Integer.parseInt(splits[i++]);
    stats.pageLoadTime = Long.parseLong(splits[i++]);
    stats.appTime = Long.parseLong(splits[i++]);
    return stats;
  }

}
