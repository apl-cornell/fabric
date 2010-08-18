package webapp.blog;

import fabric.util.Collection;
import fabric.util.Collections;
import fabric.util.HashSet;
import fabric.util.Set;

import webapp.PageStats;

public class Statistics {

  private static ThreadLocal/*Statistics*/ instance;
  private static Set/*String*/ workers;
  private static Set/*String*/ busyWorkers;
  private static boolean workerError;

  private Set/*Object*/ updates;
  private Set/*Object*/ reads;
  private PageStats currentPage;
  private long startTime;

  public static Statistics getInstance() {
    if (instance == null) {
      instance = new ThreadLocal/*Statistics*/();
      busyWorkers = new HashSet/*String*/();
      workers = new HashSet/*String*/();
      workerError = false; 
    }
    Statistics s = (Statistics)instance.get();
    if (s == null) {
      s = new Statistics();
      instance.set(s);
    }
    return s;
  }

  private Statistics() {
    newPage(Diagnostics.getLocalStore(), Diagnostics.getCurrentLabel());
  }

  public void newPage(Store s, Label l) {
    atomic {
      startTime = System.currentTimeMillis();
      updates = new HashSet~l@s/*Object*/();
      reads = new HashSet~l@s/*Object*/();
      currentPage = new PageStats~l@s();
    }
  }

  public synchronized void registerUpdate(Object t) {
    synchronized(updates) {
      atomic {
        updates.add(t);
      }
    }
  }

  public synchronized void registerCreate() {
    currentPage.numCreates++;
  }

  public synchronized void registerRead(Object t) {
    synchronized(reads) {
      atomic {
        reads.add(t);
      }
    }
  }

  public void addTransaction(long time) {
    currentPage.transactionTime += (int)time;
    currentPage.numTransactions++;
  }

  public static void addWorker(String workerHost) {
    workers.add(workerHost);
  }

  public static void removeWorker(String workerHost) {
    workers.remove(workerHost);
  }

  public static Collection/*String*/ getWorkers() {
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

  public static Collection/*String*/ getBusyWorkers() {
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
