package webapp.blog;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import webapp.PageStats;

public class Statistics {

  private static ThreadLocal<Statistics> instance;
  private static Set<String> clients;
  private static Set<String> busyClients;
  private static boolean clientError;

  private Set<Object> updates;
  private Set<Object> reads;
  private PageStats currentPage;
  private long startTime;

  public static Statistics getInstance() {
    if (instance == null)
      instance = new ThreadLocal<Statistics>();
    Statistics s = instance.get();
    if (s == null) {
      busyClients = new HashSet<String>();
      clients = new HashSet<String>();
      clientError = false;
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

  public static void addClient(String clientHost) {
    clients.add(clientHost);
  }

  public static void removeClient(String clientHost) {
    clients.remove(clientHost);
  }

  public static Collection<String> getClients() {
    return Collections.unmodifiableCollection(clients);
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

  public static void setClientDone(String client) {
    busyClients.remove(client);
  }

  public static void setBusyClient(String client) {
    busyClients.add(client);
  }

  public static Collection<String> getBusyClients() {
    return Collections.unmodifiableCollection(busyClients);
  }

  public static void removeAllBusyClients() {
    busyClients.clear();
  }

  public static void setClientError() {
    clientError = true;
    removeAllBusyClients();
  }

  public static void unsetClientError() {
    clientError = false;
  }

  public static boolean getClientError() {
    return clientError;
  }
}
