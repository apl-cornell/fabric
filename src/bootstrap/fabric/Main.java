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
  public native static void compile(fabric.lang.FClass fcls, java.util.Map/*String, bytes*/ bytecodeMap)
      throws GeneralSecurityException, IOException; 
  public native static void compileFromShell(List<String> args, InputStream in, PrintStream out);
}
