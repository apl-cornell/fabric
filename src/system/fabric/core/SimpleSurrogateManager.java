package fabric.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import fabric.common.InternalError;
import fabric.common.Pair;
import fabric.common.Util;
import fabric.core.SerializedObject.RefTypeEnum;
import fabric.core.store.StoreException;
import fabric.lang.auth.Label;

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
    Map<Pair<String,Long>, Long> cache = new TreeMap<Pair<String,Long>,Long>();

    for (SerializedObject obj : Util.chain(req.creates, req.writes)) {
      Iterator<Long> intracore = obj.getIntracoreRefs().iterator();
      Iterator<Pair<String,Long>> intercore = obj.getIntercoreRefs().iterator();
      
      List<Long> newrefs = new ArrayList<Long> (obj.getIntracoreRefs().size() +
                                                obj.getIntercoreRefs().size());
      List<RefTypeEnum> newtypes = new ArrayList<RefTypeEnum>();

      for (RefTypeEnum type : obj.getRefTypes()) {
        switch(type) {
        
        case NULL:
        case INLINE:
          newtypes.add(type);
          break;

        case ONUM:
          // add reference unchanged
          newrefs.add(intracore.next());
          newtypes.add(type);
          break;
          
        case REMOTE:
          // add surrogate reference
          Pair<String,Long> ref = intercore.next();
          Long onum = cache.get(ref);
          
          if (onum == null) {
            // create surrogate
            try {
              onum = store.newOnums(1)[0];
            } catch (StoreException e) {
              throw new InternalError(e);
            }
            // TODO: policy?
            req.creates.add(new SerializedObject(onum, Label.DEFAULT, ref));
          }
          newtypes.add(RefTypeEnum.ONUM);
          newrefs.add(onum);
          break;
        }
      }
    }
  }
  
}
