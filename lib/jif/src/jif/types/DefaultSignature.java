package jif.types;

import jif.types.label.Label;
import polyglot.ast.*;
import polyglot.types.Type;
import polyglot.util.Position;

public interface DefaultSignature
{
    Label defaultPCBound(Position pos, String name);    
    Label defaultArgBound(Formal f);
    Label defaultReturnValueLabel(ProcedureDecl pd);    
    Label defaultReturnLabel(ProcedureDecl pd);    
    Label defaultFieldLabel(FieldDecl fd);
    Label defaultArrayBaseLabel(Type baseType);
}
