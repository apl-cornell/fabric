package codebases.types;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import polyglot.types.TypeSystem;
import polyglot.util.TypeEncoder;
import polyglot.util.TypeInputStream;

public class CBTypeEncoder extends TypeEncoder {

  public CBTypeEncoder(TypeSystem ts) {
    super(ts);
  }

  @Override
  public TypeInputStream ois(ByteArrayInputStream bais) throws IOException {
    return new CBTypeInputStream(bais, ts, placeHolderCache);
  }

}
