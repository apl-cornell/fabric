package fabric;
/**
 * This bootstrap class would probably be unnecessary if FabricClassloader were
 * written in java.
 */
public class Main extends polyglot.main.Main
{
  public native static void compile(fabric.lang.FClass fcls, java.util.Map/*String, bytes*/ bytecodeMap); 
}
