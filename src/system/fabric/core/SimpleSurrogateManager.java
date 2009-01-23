package fabric.core;

import java.util.*;

import fabric.common.*;
import fabric.common.InternalError;
import fabric.core.store.ObjectStore;
import fabric.core.store.StoreException;

/**
 * This is a simple surrogate policy. It keeps no state between requests, and
 * simply creates lots of new surrogate objects.
 * 
 * @author mdgeorge
 */
public class SimpleSurrogateManager implements SurrogateManager {

  /** The object store in which to allocate surrogates. */
  private ObjectStore store;

  public SimpleSurrogateManager(final ObjectStore store) {
    this.store = store;
  }

  @SuppressWarnings("unchecked")
  public void createSurrogates(PrepareRequest req) {
    Map<ComparablePair<String, Long>, Long> cache =
        new TreeMap<ComparablePair<String, Long>, Long>();
    Collection<SerializedObject> surrogates = new ArrayList<SerializedObject>();

    for (SerializedObject obj : Util.chain(req.creates, req.writes)) {
      Iterator<Long> intracore = obj.getIntracoreRefIterator();
      Iterator<ComparablePair<String, Long>> intercore =
          obj.getIntercoreRefIterator();

      boolean hadRemotes = false;
      List<Long> newrefs =
          new ArrayList<Long>(obj.getNumIntracoreRefs()
              + obj.getNumIntercoreRefs());

      for (Iterator<RefTypeEnum> it = obj.getRefTypeIterator(); it.hasNext();) {
        RefTypeEnum type = it.next();
        switch (type) {
        case NULL:
        case INLINE:
          break;

        case ONUM:
          // add reference unchanged
          newrefs.add(intracore.next());
          break;

        case REMOTE:
          // add surrogate reference
          ComparablePair<String, Long> ref = intercore.next();
          Long onum = cache.get(ref);

          if (onum == null) {
            // create surrogate
            try {
              onum = store.newOnums(1)[0];
            } catch (StoreException e) {
              throw new InternalError(e);
            }
            // TODO: policy?
            surrogates.add(new SerializedObject(onum, obj.getLabel(), ref));
            cache.put(ref, onum);
          }
          hadRemotes = true;
          newrefs.add(onum);
          break;
        }
      }

      // set the refs on the object
      if (hadRemotes) obj.setRefs(newrefs);
    }

    // add the surrogates to the creates list
    req.creates.addAll(surrogates);
  }

}
