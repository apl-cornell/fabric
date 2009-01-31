package fabric.core;

import java.util.*;

import fabric.common.ComparablePair;
import fabric.common.RefTypeEnum;
import fabric.common.SerializedObject;
import fabric.common.Util;

/**
 * This is a simple surrogate policy. It keeps no state between requests, and
 * simply creates lots of new surrogate objects.
 * 
 * @author mdgeorge
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

    for (SerializedObject obj : Util.chain(req.creates, req.writes)) {
      Iterator<Long> intracore = obj.getIntracoreRefIterator();
      Iterator<ComparablePair<String, Long>> intercore =
          obj.getIntercoreRefIterator();

      boolean hadRemotes = false;
      List<Long> newrefs =
          new ArrayList<Long>(obj.getNumIntracoreRefs()
              + obj.getNumIntercoreRefs() + 1);

      long labelOnum;
      if (obj.labelRefIsIntercore()) {
        // Add a surrogate reference to the label.
        ComparablePair<String, Long> ref = obj.getIntercoreLabelRef();

        labelOnum = tm.newOnums(1)[0];
        surrogates.add(new SerializedObject(labelOnum, labelOnum, ref));
        cache.put(ref, labelOnum);
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
          newrefs.add(intracore.next());
          break;

        case REMOTE:
          // add surrogate reference
          ComparablePair<String, Long> ref = intercore.next();
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
