/**
 * Copyright (C) 2010-2014 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
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
