package fabric.worker.admin;

/**
 * Indicates that an attempt was made to connect to a worker on its admin port,
 * but no worker was listening.
 */
public class WorkerNotRunningException extends Exception {

}
