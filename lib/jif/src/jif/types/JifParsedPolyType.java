package jif.types;

import java.util.List;

import polyglot.ext.param.types.PClass;
import polyglot.types.ParsedClassType;

/** Jif parsed polymorphic class type. 
 */
public interface JifParsedPolyType extends ParsedClassType, JifPolyType {
    void setParams(List params);
    void setAuthority(List principals);
    void setInstantiatedFrom(PClass pc);
}
