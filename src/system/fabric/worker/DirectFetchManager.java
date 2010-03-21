package fabric.worker;

import fabric.common.ObjectGroup;
import fabric.common.exceptions.FetchException;
import fabric.dissemination.FetchManager;

/**
 * This simple FetchManger always goes directly to the store.
 */
public class DirectFetchManager implements FetchManager {

  public DirectFetchManager(Worker worker) {
  }

  public ObjectGroup fetch(RemoteStore store, long onum) throws FetchException {
    return store.readObjectFromStore(onum);
  }

  public void destroy() {
  }

}
