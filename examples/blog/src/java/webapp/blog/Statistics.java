package webapp.blog;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import webapp.PageStats;

public class Statistics {

  private static ThreadLocal<Statistics> instance;
  private static Set<String> workers;
  private static Set<String> busyWorkers;
  private static boolean workerError;

  private Set<Object> updates;
  private Set<Object> reads;
  private PageStats currentPage;
  private long startTime;

  public static Statistics getInstance() {
    if (instance == null)
      instance = new ThreadLocal<Statistics>();
    Statistics s = instance.get();
    if (s == null) {
      busyWorkers = new HashSet<String>();
      workers = new HashSet<String>();
      workerError = false;
      s = new Statistics();
      instance.set(s);
    }
    return s;
  }

  private Statistics() {
    newPage();
  }

  public void newPage() {
    startTime = System.currentTimeMillis();
    updates = new HashSet<Object>();
    reads = new HashSet<Object>();
    currentPage = new PageStats();
  }

  public void registerUpdate(Object t) {
    updates.add(t);
  }

  public void registerCreate() {
    currentPage.numCreates++;
  }

  public void registerRead(Object t) {
    reads.add(t);
  }

  public void addTransaction(int time) {
    currentPage.transactionTime += time;
    currentPage.numTransactions++;
  }

  public static void addWorker(String workerHost) {
    workers.add(workerHost);
  }

  public static void removeWorker(String workerHost) {
    workers.remove(workerHost);
  }

  public static Collection<String> getWorkers() {
    return Collections.unmodifiableCollection(workers);
  }

  public void doneApp() {
    currentPage.appTime = System.currentTimeMillis() - startTime;
  }

  public void done() {
    currentPage.numReads = reads.size();
    currentPage.numUpdates = updates.size();
    currentPage.pageLoadTime = System.currentTimeMillis() - startTime;
  }

  public PageStats getCurrentPageStats() {
    done();
    return currentPage;
  }

  public static void setWorkerDone(String worker) {
    busyWorkers.remove(worker);
  }

  public static void setBusyWorker(String worker) {
    busyWorkers.add(worker);
  }

  public static Collection<String> getBusyWorkers() {
    return Collections.unmodifiableCollection(busyWorkers);
  }

  public static void removeAllBusyWorkers() {
    busyWorkers.clear();
  }

  public static void setWorkerError() {
    workerError = true;
    removeAllBusyWorkers();
  }

  public static void unsetWorkerError() {
    workerError = false;
  }

  public static boolean getWorkerError() {
    return workerError;
  }
}
