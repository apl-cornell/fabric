package fabric;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
/**
 * This bootstrap class would probably be unnecessary if FabricClassloader were
 * written in java.
 */
public class Main extends polyglot.main.Main
{
  public native static long compile(fabric.lang.FClass fcls, java.util.Map/*String, bytes*/ bytecodeMap)
      throws GeneralSecurityException, IOException; 
  public native static long compile_from_shell(List<String> args, InputStream in, PrintStream out);
}
