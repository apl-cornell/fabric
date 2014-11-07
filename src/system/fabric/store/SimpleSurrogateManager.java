package fabric.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import fabric.common.ONumConstants;
import fabric.common.RefTypeEnum;
import fabric.common.SerializedObject;
import fabric.common.SysUtil;
import fabric.common.util.ComparablePair;
import fabric.common.util.Pair;

/**
 * This is a simple surrogate policy. It keeps no state between requests, and
 * simply creates lots of new surrogate objects.
 */
public class SimpleSurrogateManager implements SurrogateManager {

  private TransactionManager tm;

  public SimpleSurrogateManager(final TransactionManager tm) {
    this.tm = tm;
  }

  @Override
  public void createSurrogates(PrepareRequest req) {
    // Maps remote refs -> surrogate onums.
    Map<Pair<String, Long>, Long> cache = new HashMap<>();
    Collection<SerializedObject> surrogates = new ArrayList<>();

    Iterable<SerializedObject> chain = SysUtil.chain(req.creates, req.writes);
    for (SerializedObject obj : chain) {
      Iterator<Long> intraStore = obj.getIntraStoreRefIterator();
      Iterator<ComparablePair<String, Long>> interStore =
          obj.getInterStoreRefIterator();

      boolean hadRemotes = false;
      List<Long> newrefs =
          new ArrayList<>(obj.getNumIntraStoreRefs()
              + obj.getNumInterStoreRefs() + 1);

      long updateLabelOnum;
      if (obj.updateLabelRefIsInterStore()) {
        // Add a surrogate reference to the label.
        ComparablePair<String, Long> ref = obj.getInterStoreUpdateLabelRef();

        updateLabelOnum = tm.newOnums(1)[0];
        surrogates.add(new SerializedObject(updateLabelOnum, updateLabelOnum,
            ONumConstants.BOTTOM_CONFIDENTIALITY, ref));
        cache.put(ref, updateLabelOnum);
        hadRemotes = true;
        newrefs.add(updateLabelOnum);
      } else {
        updateLabelOnum = obj.getUpdateLabelOnum();
      }

      long accessPolicyOnum;
      if (obj.accessPolicyRefIsInterStore()) {
        // Add a surrogate reference to the access policy.
        ComparablePair<String, Long> ref = obj.getInterStoreAccessPolicyRef();

        accessPolicyOnum = tm.newOnums(1)[0];
        surrogates.add(new SerializedObject(accessPolicyOnum,
            ONumConstants.PUBLIC_READONLY_LABEL, accessPolicyOnum, ref));
        hadRemotes = true;
        newrefs.add(accessPolicyOnum);
      } else {
        accessPolicyOnum = obj.getAccessPolicyOnum();
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
            surrogates.add(new SerializedObject(onum, updateLabelOnum,
                accessPolicyOnum, ref));
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
