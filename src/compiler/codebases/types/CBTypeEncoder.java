package codebases.types;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import polyglot.frontend.SchedulerException;
import polyglot.main.Report;
import polyglot.types.PlaceHolder;
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
   * Decode a serialized type object.  If deserialization fails because
   * a type could not be resolved, the method returns null.  The calling
   * pass should abort in that case.
   * @param s String containing the encoded type object.
   * @return The decoded TypeObject, or null if deserialization fails.
   * @throws InvalidClassException If the string is malformed.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public TypeObject decode(String s, String name) throws InvalidClassException {
      TypeInputStream ois;
      byte[] b;
      
      if (base64) {
          b = Base64.decode(s.toCharArray());    
      }
      else {
          char[] source;        
          source = s.toCharArray();
          b = new byte[source.length];
          for (int i = 0; i < source.length; i++)
              b[i] = (byte) source[i];
      }
      
      Map<PlaceHolder, TypeObject> oldCache = placeHolderCache;
      placeHolderCache = new HashMap<PlaceHolder, TypeObject>();
      if (oldCache != null) {
          placeHolderCache.putAll(oldCache);
      }

      Map oldDeps = dependencies;
      if (oldDeps == null) {
          dependencies = new HashMap(); 
      }

      if (Report.should_report(Report.serialize, 1))
          Report.report(1, "TypeEncoder depth " + depth + " at " + name);
      depth++;
      
      try {
          if (zip && !base64) {
              // The base64 decoder automatically unzips byte streams, so
              // we only need an explicit GZIPInputStream if we are not
              // using base64 encoding.
              ois = new CBTypeInputStream(new GZIPInputStream(new ByteArrayInputStream(b)), ts, placeHolderCache);
          }
          else {
              ois = new CBTypeInputStream(new ByteArrayInputStream(b), ts, placeHolderCache);
          }
    
          TypeObject o = (TypeObject) ois.readObject();
          
          if (ois.deserializationFailed()) {
              return null;
          }
          
          return o;
      }
      catch (InvalidClassException e) {
          throw e;
      }
      catch (IOException e) {
          throw new InternalCompilerError("IOException thrown while " +
                                          "decoding serialized type info: " + e.getMessage(), e);
      }
      catch (ClassNotFoundException e) {
          throw new InternalCompilerError("Unable to find one of the classes " +
                                          "for the serialized type info: " + e.getMessage(), e);
      }
      catch (SchedulerException e) {
          throw new InternalCompilerError("SchedulerException thrown while " +
                                          "decoding serialized type info: " + e.getMessage(), e);
      }
      finally {
          placeHolderCache = oldCache;
          dependencies = oldDeps;
          depth--;
      }
  }

}
