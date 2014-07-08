/**
 * Copyright (C) 2010 Fabric project group, Cornell University
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

/**
 * Main is the main program of the compiler extension.
 * It simply invokes Polyglot's main, passing in the extension's
 * ExtensionInfo.
 */
public class Main
{
  public static void main(String[] args) {
      polyglot.main.Main polyglotMain = new polyglot.main.Main();

      try {
          polyglotMain.start(args, new fabric.ExtensionInfo());
      }
      catch (polyglot.main.Main.TerminationException e) {
          System.err.println(e.getMessage());
          System.exit(1);
      }
  }
}
