/**
 * Copyright (C) 2010-2012 Fabric project group, Cornell University
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
package codebases.types;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import polyglot.frontend.SchedulerException;
import polyglot.main.Report;
import polyglot.types.TypeObject;
import polyglot.types.TypeSystem;
import polyglot.util.Base64;
import polyglot.util.InternalCompilerError;
import polyglot.util.TypeEncoder;
import polyglot.util.TypeInputStream;

public class CBTypeEncoder extends TypeEncoder {

  public CBTypeEncoder(TypeSystem ts) {
    super(ts);
  }

  /**
   * Decode a serialized type object. If deserialization fails because a type
   * could not be resolved, the method returns null. The calling pass should
   * abort in that case.
   * 
   * @param s
   *          String containing the encoded type object.
   * @return The decoded TypeObject, or null if deserialization fails.
   * @throws InvalidClassException
   *           If the string is malformed.
   */
  @Override
  public TypeObject decode(String s, String name) throws InvalidClassException {
    TypeInputStream ois;
    byte[] b;

    if (base64) {
      b = Base64.decode(s.toCharArray());
    } else {
      char[] source;
      source = s.toCharArray();
      b = new byte[source.length];
      for (int i = 0; i < source.length; i++)
        b[i] = (byte) source[i];
    }

    Map<Object, Object> oldCache = placeHolderCache;
    placeHolderCache = new HashMap<Object, Object>();
    if (oldCache != null) {
      placeHolderCache.putAll(oldCache);
    }

    if (Report.should_report(Report.serialize, 1))
      Report.report(1, "TypeEncoder depth " + depth + " at " + name);
    depth++;

    try {
      boolean gzip = zip && !base64;
      if (gzip) {
        // The base64 decoder automatically unzips byte streams, so
        // we only need an explicit GZIPInputStream if we are not
        // using base64 encoding.
        ois =
            new CBTypeInputStream(new GZIPInputStream(new ByteArrayInputStream(
                b)), ts, placeHolderCache);
      } else {
        ois =
            new CBTypeInputStream(new ByteArrayInputStream(b), ts,
                placeHolderCache);
      }

      TypeObject o = (TypeObject) ois.readObject();

      if (ois.deserializationFailed()) {
        return null;
      }

      return o;
    } catch (InvalidClassException e) {
      throw e;
    } catch (IOException e) {
      throw new InternalCompilerError("IOException thrown while "
          + "decoding serialized type info: " + e.getMessage(), e);
    } catch (ClassNotFoundException e) {
      throw new InternalCompilerError("Unable to find one of the classes "
          + "for the serialized type info: " + e.getMessage(), e);
    } catch (SchedulerException e) {
      throw new InternalCompilerError("SchedulerException thrown while "
          + "decoding serialized type info: " + e.getMessage(), e);
    } finally {
      placeHolderCache = oldCache;
      depth--;
    }
  }

}
