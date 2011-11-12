package fabric.common;

import java.io.DataOutput;
import java.io.IOException;

/**
 * A common interface for all classes that implement custom serialization. It's
 * called <code>FastSerializable</code> because Java serialization is so
 * frick'n slow.
 * <p>
 * (We'd call this "FabricSerializable", but then that kinda sounds like a
 * Fabric object that's somehow "serializable".)
 * </p>
 */
public interface FastSerializable {
  void write(DataOutput out) throws IOException;
}
