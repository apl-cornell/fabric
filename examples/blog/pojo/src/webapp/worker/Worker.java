package webapp.worker;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import webapp.Action;
import webapp.PageLoader;
import webapp.PageStats;

public class Worker {

  public enum State {
    Idle, InProgress, Error
  }

  private String name;
  private State state;
  private int requestsRemaining;
  private Action action;
  private String host;
  private String error;
  private List<PageStats> pageStats;

  public Worker(String name) {
    this.name = name;
    state = State.Idle;
    pageStats = new LinkedList<PageStats>();
  }

  public String getLoadTimeValues() {
    StringBuilder b = new StringBuilder();
    int totalSkip = pageStats.size() / 101;
    int skip = 0;
    for (PageStats p : pageStats) {
      if (skip < totalSkip) {
        skip++;
        continue;
      }
      skip = 0;
      b.append(p.pageLoadTime + p.transmissionTime).append(',');
    }
    return b.substring(0, b.length() - 1);
  }

  public long getMaxLoadTime() {
    long max = 0;
    for (PageStats p : pageStats) {
      if (max < p.pageLoadTime + p.transmissionTime)
        max = p.pageLoadTime + p.transmissionTime;
    }
    return max;
  }

  public PageStats getMedian() {
    if (pageStats.size() == 0)
      return null;

    TreeSet<PageStats> sorted = new TreeSet<PageStats>(
        new Comparator<PageStats>() {
          @Override
          public int compare(PageStats o1, PageStats o2) {
            if ((o1.transactionTime + o1.pageLoadTime) > (o2.transactionTime + o2.pageLoadTime))
              return 1;
            else
              return -1;
          }
        });
    for (PageStats p : pageStats)
      sorted.add(p);
    PageStats median = null;
    Iterator<PageStats> iter = sorted.iterator();
    for (int i = 0; i < pageStats.size() / 2; i++)
      median = iter.next();

    return median;

  }

  public static String getRequestForAction(Action action) {
    switch (action) {
      case MassUpdateTitles:
        return "/web?action=massupdate";
      case NewPost:
        return "/web?action=createpost&title=Hello+From+Worker&content=Greetings.";
      case NumOccurrencesComment:
        return "/web?action=numoccurrences&word=comment";
      case ReadManyPosts:
        return "/web?start=0&end=300";
    }
    return "";
  }

  public void startAction(final Action action, int numRequests) {
    requestsRemaining = numRequests;
    this.action = action;
    pageStats = new LinkedList<PageStats>();
    new Thread(new Runnable() {
      public void run() {
        state = State.InProgress;
        try {
          String page = host;
          page += getRequestForAction(action);
          while (requestsRemaining > 0) {
            long start = System.currentTimeMillis();
            PageStats p = PageLoader.getPageBenchmark(page);
            long end = System.currentTimeMillis() - start;
            p.transmissionTime = end - p.getPageLoadTime();
            pageStats.add(p);
            requestsRemaining--;
          }
          PageLoader.getPage(host + "/web?workeraction=done&port="
              + WorkerServer.port);
          state = State.Idle;
        } catch (IOException ex) {
          state = State.Error;
          error = "Could not complete benchmark";
          error += "\n" + ex.getMessage();
          try {
            PageLoader.getPage(host + "/web?workeraction=error");
          } catch (IOException ex2) {}
        }
      }
    }).start();
  }

  public boolean isActive() {
    return state == State.InProgress;
  }

  public boolean isIdle() {
    return state == State.Idle;
  }

  public boolean isError() {
    return state == State.Error;
  }

  public String getErrorMessage() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
    state = State.Error;
  }

  public boolean setHost(String host) {
    if (host == null && this.host != null) {
      try {
        PageLoader.getPage(this.host + "/web?workeraction=removeme&port="
            + WorkerServer.port);
      } catch (IOException ex) {}
      this.host = null;
      return true;
    } else {
      try {
        String res = PageLoader.getPage(host
            + "/web?workeraction=connect&port=" + WorkerServer.port);
        if (!res.trim().equals("ok"))
          return false;
        this.host = host;
        return true;
      } catch (IOException ex) {
        return false;
      }
    }
  }

  public Collection<PageStats> getPageStats() {
    return Collections.unmodifiableCollection(pageStats);
  }

  public String getHost() {
    return host;
  }

  public Action getAction() {
    return action;
  }

  public String getName() {
    return name;
  }

  public State getState() {
    return state;
  }

  public int getRequestsRemaining() {
    return requestsRemaining;
  }

}
