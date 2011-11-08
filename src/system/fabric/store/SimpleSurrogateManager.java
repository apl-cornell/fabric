package fabric.store;

import java.util.*;

import fabric.common.RefTypeEnum;
import fabric.common.SerializedObject;
import fabric.common.SysUtil;
import fabric.common.util.ComparablePair;

/**
 * This is a simple surrogate policy. It keeps no state between requests, and
 * simply creates lots of new surrogate objects.
 */
public class SimpleSurrogateManager implements SurrogateManager {

  private TransactionManager tm;

  public SimpleSurrogateManager(final TransactionManager tm) {
    this.tm = tm;
  }

  @SuppressWarnings("unchecked")
  public void createSurrogates(PrepareRequest req) {
    Map<ComparablePair<String, Long>, Long> cache =
        new TreeMap<ComparablePair<String, Long>, Long>();
    Collection<SerializedObject> surrogates = new ArrayList<SerializedObject>();

    for (SerializedObject obj : SysUtil.chain(req.creates, req.writes)) {
      Iterator<Long> intraStore = obj.getIntraStoreRefIterator();
      Iterator<ComparablePair<String, Long>> interStore =
          obj.getInterStoreRefIterator();

      boolean hadRemotes = false;
      List<Long> newrefs =
          new ArrayList<Long>(obj.getNumIntraStoreRefs()
              + obj.getNumInterStoreRefs() + 1);

      long labelOnum;
      if (obj.labelRefIsInterStore()) {
        ComparablePair<String, Long> ref = obj.getInterStoreLabelRef();
        Long cachedOnum = cache.get(ref);

        if (cachedOnum == null) {
          // Add a surrogate reference to the access label.
          accessLabelOnum = tm.newOnums(1)[0];
          surrogates.add(new SerializedObject(accessLabelOnum, accessLabelOnum,
              accessLabelOnum, ref));
          cache.put(ref, accessLabelOnum);
        } else {
          accessLabelOnum = cachedOnum;
        }
        hadRemotes = true;
        newrefs.add(labelOnum);
      } else {
        labelOnum = obj.getLabelOnum();
      }

      for (Iterator<RefTypeEnum> it = obj.getRefTypeIterator(); it.hasNext();) {
        RefTypeEnum type = it.next();
        switch (type) {
        case NULL:
        case INLINE:
          break;

        case ONUM:
          // add reference unchanged
          newrefs.add(intraStore.next());
          break;

        case REMOTE:
          // add surrogate reference
          ComparablePair<String, Long> ref = interStore.next();
          Long onum = cache.get(ref);

          if (onum == null) {
            // create surrogate
            onum = tm.newOnums(1)[0];
            surrogates.add(new SerializedObject(onum, labelOnum, ref));
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
