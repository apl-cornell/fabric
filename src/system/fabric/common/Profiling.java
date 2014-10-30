package fabric.common;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import fabric.worker.Worker;

/**
 * Class for offline profiling. When this class is loaded, it starts a thread
 * that will periodically save CPU profile information to the file system. The
 * location is determined by the "fabric.profiling.dir" system property, and the
 * period is specified in seconds by the "fabric.profiling.interval" system
 * property.
 */
public class Profiling {

  /**
   * A hacky shim so that this code compiles even if the JProfiler agent is
   * absent. (We do not distribute the JProfiler agent.)
   */
  private static class Controller {
    static final Class<?> jprofiler;

    static {
      // Attempt to load the JProfiler controller.
      Class<?> clazz;
      try {
        clazz = Class.forName("com.jprofiler.api.agent.Controller");
      } catch (ClassNotFoundException e) {
        clazz = null;
      }
      jprofiler = clazz;
    }

    static void startCPURecording(boolean reset) {
      try {
        Method method = jprofiler.getMethod("startCPURecording", boolean.class);
        method.invoke(null, reset);
      } catch (NoSuchMethodException | SecurityException
          | IllegalAccessException | IllegalArgumentException
          | InvocationTargetException e) {
      }
    }

    static void saveSnapshot(File file) {
      try {
        Method method = jprofiler.getMethod("saveSnapshot", File.class);
        method.invoke(null, file);
      } catch (NoSuchMethodException | SecurityException
          | IllegalAccessException | IllegalArgumentException
          | InvocationTargetException e) {
      }
    }
  }

  private static class ProfilingThread extends Thread {
    ProfilingThread() {
      setDaemon(true);
    }

    @Override
    public void run() {
      if (Controller.jprofiler == null) {
        // Profiling not supported: JProfiler agent is absent.
        return;
      }

      String dir = System.getProperty("fabric.profiling.dir");
      if (dir == null) return;

      String interval = System.getProperty("fabric.profiling.interval");
      if (interval == null) return;

      long interval_ms = Long.parseLong(interval) * 1000;
      if (interval_ms < 1) return;

      int snapshotNum = 0;
      String filePrefix =
          dir + System.getProperty("file.separator")
          + Worker.getWorker().getName() + "-";

      try {
        while (true) {
          Controller.startCPURecording(true);

          sleep(interval_ms);

          String snapshot = "" + (snapshotNum++);
          while (snapshot.length() < 5) {
            snapshot = "0" + snapshot;
          }

          Controller.saveSnapshot(new File(filePrefix + snapshot + ".jps"));
        }
      } catch (InterruptedException e) {
      }
    }
  }

  static {
    new ProfilingThread().start();
  }
}
