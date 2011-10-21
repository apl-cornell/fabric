package fabric.worker;

import java.util.Properties;

import fabric.common.ObjectGroup;
import fabric.common.exceptions.AccessException;
import fabric.dissemination.FetchManager;

/**
 * This simple FetchManger always goes directly to the store.
 */
public class DirectFetchManager implements FetchManager {

  public DirectFetchManager(Worker worker, Properties dissemConfig) {
  }

  @Override
  public ObjectGroup fetch(RemoteStore store, long onum) throws AccessException {
    return store.readObjectFromStore(onum);
  }

  @Override
  public void destroy() {
  }

}
