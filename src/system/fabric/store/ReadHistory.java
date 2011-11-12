package fabric.store;

import java.util.LinkedList;
import java.util.Queue;

import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;

/** This class is responsible for keeping a log of the recent reads, for
 *  checking commit conflicts.  Each read has a time associated with it, and the
 *  log only keeps the most recent entries.
 */
public class ReadHistory {
  /** the default length of a read history, in milliseconds */
  public static final long DEFAULT_LENGTH = 1000 * 60; // one minute
  
  /** the amount of time, in milliseconds, that entries are kept */
  private final long length;
  
  /** a map from oids to read times */ 
  private final LongKeyMap<Long> reads;
  
  /** a list of read transactions, ordered by time */
  private final Queue<PrepareRequest> history;
  
  /** the oldest time for which the history is complete */
  private long currentSince;
  
  public ReadHistory() {
    this(DEFAULT_LENGTH, System.currentTimeMillis());
  }
  
  /** create a new read history with duration length
   * 
   * @param length    the duration, in milliseconds, of entries in the history
   * @param beginning a time such that no reads have happened since that time (typically now)
   */
  public ReadHistory(long length, long beginning) {
    this.length       = length;
    this.history      = new LinkedList<PrepareRequest>();
    this.reads        = new LongKeyHashMap<Long>();
    this.currentSince = beginning;
    new Cleaner().start();
  }
  
  /** record a prepare request in the history */
  public synchronized void record(PrepareRequest req) {
    LongIterator i = req.reads.keySet().iterator();
    while (i.hasNext()) {
      long oid = i.next();
      Long oldTime = reads.get(oid);
      if (oldTime == null || oldTime < req.commitTime)
        reads.put(oid, req.commitTime);
    }
    
    history.add(req);
  }
  
  /** remove all entries older than now - length */  
  private synchronized void clean(long now) {
    long cutoff = now - length;
    while(!history.isEmpty()) {
      PrepareRequest req = history.peek();
      if (req.commitTime > cutoff)
        break;
      
      LongIterator i = req.reads.keySet().iterator();
      while (i.hasNext()) {
        long oid = i.next();
        Long time = reads.get(oid);
        if (time != null && time < cutoff)
          reads.remove(oid);
      }
      // remove the entry from history
      history.poll();
    }
  }
  
  /** determine whether preparing an update to an object at a given time would
   *  cause a conflict with any reads that have occurred since that time.
   *  @param oid  the object to check
   *  @param time the commit time
   *  @return true if the object is safe to prepare
   */
  public synchronized boolean check(long oid, long time) {
    if (time < this.currentSince)
      return false;
    Long lastRead = reads.get(oid);
    return lastRead == null || lastRead < time;
  }
  
  /** This thread is responsible for cleaning up the history.  It wakes up every
   *  <code>length</code> milliseconds and removes any entries older than
   *  now - length, updating <code>currentSince</code> accordingly.
   */ 
  private class Cleaner extends Thread {
    public Cleaner() {
      super("Read History Cleaner");
      this.setDaemon(true);
    }
    
    @Override
    public synchronized void run() {
      while(true) {
        try { wait(length); } catch (InterruptedException e) { }
        clean(System.currentTimeMillis());
      }
    }
  }
}
