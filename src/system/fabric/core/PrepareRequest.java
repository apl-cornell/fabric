package fabric.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyMap;

/**
 * A convenience class for grouping together the created, modified, and read
 * object sets of a prepare request.
 * 
 * @author mdgeorge
 */
public final class PrepareRequest implements Iterable<Long>, Serializable {
  
  /** The set of created objects */
  public Collection<SerializedObject> creates;
  
  /** The collection of modified objects */
  public Collection<SerializedObject> writes;
  
  /** The object numbers and version numbers of the read objects */
  public LongKeyMap<Integer> reads;
  
  /** Create a PrepareRequest with the provided fields */
  public PrepareRequest (Collection<SerializedObject> creates,
                         Collection<SerializedObject> writes,
                         LongKeyMap<Integer>          reads) {
    this.creates = creates;
    this.writes  = writes;
    this.reads   = reads;
  }

  /**
   * Returns an iterator over all of the object numbers that are read, written,
   * or modified by this request.
   */
  public Iterator<Long> iterator() {
    return new Iterator<Long>() {
      Iterator<SerializedObject> createsIter = creates.iterator();
      Iterator<SerializedObject> writesIter  = writes.iterator();
      LongIterator               readsIter   = reads.keySet().iterator();

      public boolean hasNext() {
        return createsIter.hasNext() || writesIter.hasNext() || readsIter.hasNext();
      }

      public Long next() {
        if (createsIter.hasNext())
          return createsIter.next().getOnum();
        if (writesIter.hasNext())
          return writesIter.next().getOnum();
        if (readsIter.hasNext())
          return readsIter.next();
        throw new NoSuchElementException();
      }

      public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
      }
    };
  }
  
  /**
   * Returns an Iterable that will allow you to iterate over all of the objects
   * that were modified and created in this request. This is a convenience
   * method for use in for loops: 
   * 
   * <code>for (SerializedObject o : request.written()) {...}</code>.
   */
  public Iterable<SerializedObject> written() {
    return new Iterable<SerializedObject>() {
      public Iterator<SerializedObject> iterator() {
        return new Iterator<SerializedObject>() {
          Iterator<SerializedObject> createsIter = creates.iterator();
          Iterator<SerializedObject> writesIter  = writes.iterator();

          public boolean hasNext() {
            return createsIter.hasNext() || writesIter.hasNext();
          }

          public SerializedObject next() {
            if (createsIter.hasNext())
              return createsIter.next();
            if (writesIter.hasNext())
              return writesIter.next();
            throw new NoSuchElementException();
          }

          public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
          }
        };
      }
    };
  }
  
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0
*/
