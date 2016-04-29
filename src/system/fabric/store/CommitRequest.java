package fabric.store;

import java.util.Collection;

import fabric.common.SerializedObject;

/**
 * A convenience class for grouping together the created and modified
 * object sets of a commit request.
 */
public final class CommitRequest {
  public final long tid;

  /** The set of created objects */
  public final Collection<SerializedObject> creates;

  /** The collection of modified objects */
  public final Collection<SerializedObject> writes;

  /** Create a PrepareRequest with the provided fields */
  public CommitRequest(long tid, Collection<SerializedObject> creates,
      Collection<SerializedObject> writes) {
    this.tid = tid;
    this.creates = creates;
    this.writes = writes;
  }

}

/*
 * * vim: ts=2 sw=2 et cindent cino=\:0
 */
