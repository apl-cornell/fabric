package fabric.worker;

import fabric.common.ObjectGroup;
import fabric.common.exceptions.FetchException;
import fabric.dissemination.FetchManager;

/**
 * This simple FetchManger always goes directly to the core.
 */
public class DirectFetchManager implements FetchManager {

  public DirectFetchManager(Worker worker) {
  }

  public ObjectGroup fetch(RemoteCore c, long onum) throws FetchException {
    return c.readObjectFromCore(onum);
  }

  public void destroy() {
  }

}
