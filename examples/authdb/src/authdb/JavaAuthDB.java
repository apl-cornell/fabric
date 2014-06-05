package authdb;

import fabric.lang.security.Principal;
import fabric.worker.Worker;
import fabric.worker.Worker.Code;

public class JavaAuthDB {
  private final AuthDB authDB;

  private JavaAuthDB(AuthDB authDB) {
    this.authDB = authDB;
  }

  /**
   * Obtains a JavaAuthDB instance, backed by an AuthDB that is stored at the
   * given store.
   */
  public static JavaAuthDB getInstance(String storeName) {
    Worker worker = Worker.getWorker();
    Principal workerPrincipal = worker.getPrincipal();
    AuthDB authDB = Worker.runInTopLevelTransaction(new Code<AuthDB>() {
	public AuthDB run() {
	  return AuthDB._Impl.getInstance(workerPrincipal, workerPrincipal,
	      storeName);
	}
      }, true);
    return new JavaAuthDB(authDB);
  }

  /**
   * Authenticates a user against the database.
   */
  public boolean authenticate(String user, String password) {
    return authDB.authenticate(user, password);
  }

  /**
   * Adds a user to the database.
   */
  public void addUser(String user, String password) {
    authDB.addUser(user, password);
  }

  /**
   * Removes a user from the database.
   */
  public void removeUser(String user) {
    authDB.removeUser(user);
  }

  /**
   * Test harness.
   */
  public static void main(String[] args) throws Exception {
    Worker.initialize("worker");
    JavaAuthDB db = getInstance("store");
    if (db.authenticate("user0", "password")) {
      System.err.println("Test 1 failed: user0 unexpectedly authenticated");
    } else {
      System.err.println("Test 1 succeeded");
    }

    db.addUser("user1", "password");
    if (db.authenticate("user1", "passwor")) {
      System.err.println("Test 2 failed: user1 unexpectedly authenticated");
    } else {
      System.err.println("Test 2 succeeded");
    }

    if (!db.authenticate("user1", "password")) {
      System.err.println("Test 3 failed: user1 unexpectedly failed to "
	  + "authenticate");
    } else {
      System.err.println("Test 3 succeeded");
    }

    db.removeUser("user1");

    if (db.authenticate("user1", "password")) {
      System.err.println("Test 4 failed: user1 unexpectedly authenticated");
    } else {
      System.err.println("Test 4 succeeded");
    }

    System.err.println("End of tests.");

    // Exit harshly. Otherwise, the worker will continue to run indefinitely.
    System.exit(0);
  }
}
