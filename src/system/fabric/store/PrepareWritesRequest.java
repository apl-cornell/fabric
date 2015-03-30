package fabric.store;

import java.util.Collection;

import fabric.common.SerializedObject;
import fabric.worker.memoize.ComputationWarrantyRequest;

/**
 * A convenience class for grouping together the created and modified object
 * sets of a prepare-writes request.
 */
public final class PrepareWritesRequest {
  public final long tid;

  /** The set of created objects */
  public final Collection<SerializedObject> creates;

  /** The collection of modified objects */
  public final Collection<SerializedObject> writes;

  /** The collection of requested semantic warrantied calls */
  public final Collection<ComputationWarrantyRequest> calls;

  /** Create a PrepareWritesRequest with the provided fields */
  public PrepareWritesRequest(long tid, Collection<SerializedObject> creates,
      Collection<SerializedObject> writes,
      Collection<ComputationWarrantyRequest> calls) {
    this.tid = tid;
    this.creates = creates;
    this.writes = writes;
    this.calls = calls;
  }

}

/*
 * * vim: ts=2 sw=2 et cindent cino=\:0
 */
