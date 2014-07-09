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
package sif.html;

import sif.servlet.HTMLServlet;
import sif.servlet.HTMLWriter;
import fabric.lang.security.Label;
import fabric.lang.security.Principal;

/**
 * A "file" input that allows the client to upload the contents of a file.
 * 
 * @author andru
 *
 */
public final class FileChooser extends InputNode {
  private final int size;

  public FileChooser(Principal servletP, Label L, Label E, Input input, int size) {
    super(servletP, L, E, input);
    this.size = size;
  }

  public FileChooser(Principal servletP, Label L, Label E, HTMLServlet s,
      Label inputLabel, int size) {
    super(servletP, L, E, new Input(servletP, s, inputLabel));
    this.size = size;
  }

  public FileChooser(Principal servletP, Label L, Label E, String name, int size) {
    super(servletP, L, E, name);
    this.size = size;
  }

  @Override
  void writeImpl(HTMLWriter p) {
    p.print("<input type=file name=");
    p.printq(getName());
    if (size > 0) {
      p.print(" size=");
      p.printq(size);
    }
    p.print(" />");
  }

  public static boolean jif$Instanceof(Principal P, Label l, Label e, Object o) {
    return ((o instanceof FileChooser) && InputNode.jif$Instanceof(P, l, e, o));
  }

  public static FileChooser jif$cast$sif_html_FileChooser(Principal P, Label l,
      Label e, Object o) {
    if (o == null) return null;
    if (jif$Instanceof(P, l, e, o)) return (FileChooser) o;
    throw new ClassCastException();
  }

}
