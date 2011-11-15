package jif.types;

import java.util.ArrayList;
import java.util.Collections;

import jif.types.label.Label;
import polyglot.types.*;
import polyglot.util.Position;


public class ConstArrayType_c extends ArrayType_c implements ConstArrayType
{
    protected boolean isConst;
    protected boolean isNonConst;

    /** Used for deserializing types. */
    protected ConstArrayType_c() { }

    public ConstArrayType_c(JifTypeSystem ts, Position pos, Type base, boolean isConst) {
        this(ts, pos, base, isConst, !isConst); 
    }
    public ConstArrayType_c(JifTypeSystem ts, Position pos, Type base, boolean isConst, boolean isNonConst) {
        super(ts, pos, base);
        this.isConst = isConst;
        this.isNonConst = isNonConst;
    }

    public String toString() {
        Type base = base();
        Type ultBase = ultimateBase();
        JifTypeSystem ts = (JifTypeSystem)this.typeSystem();
        boolean isBaseArray = base.isArray();
        if (isBaseArray && ts.isLabeled(base) && ts.isLabeled(ultBase)) {
            // both the base of this array is labeled, and the
            // ultimate base of this array is labeled. Don't print
            // the label for the base if it is the same as
            // the ultimate base.
            if (ts.labelOfType(base).equals(ts.labelOfType(ultBase))) {
                base = ts.unlabel(base);
            }
        }

        String s = base.toString();
        
        if (!isBaseArray && isConst && !isNonConst) return s + " const[]";
        if (!isBaseArray && isConst && isNonConst) return s + " const?[]";
        return s + "[]";
    }

    public boolean equalsImpl(TypeObject o) {
        if (o instanceof ConstArrayType) {
            ConstArrayType t = (ConstArrayType) o;
            return t.isConst() == isConst && 
               t.isNonConst() == isNonConst && 
               ts.equals(base, t.base());
        }

        if (o instanceof ArrayType) {
            ArrayType t = (ArrayType) o;
            return !isConst && !isNonConst && ts.equals(base, t.base());
        }

        return false;
    }

    public boolean isConst() {
        return isConst;
    }

    public boolean isNonConst() {
        return isNonConst;
    }

    public boolean isImplicitCastValidImpl(Type toType) {
        if (isNonConst && !isConst) {
            if (toType.isArray()) {
                // toType must be a non-const
                if (toType instanceof ConstArrayType && ((ConstArrayType)toType).isConst()) {
                    return false;
                }
                // non-const arrays are invariant.
                return ts.equals(this.base(), toType.toArray().base());
            }
            else {
                // Object = int[] 
                return super.isImplicitCastValidImpl(toType);
            }
        }
        if (isConst) {
            if (toType.isArray()) {
                // if we are strictly const then, toType must be a const
                if (!isNonConst && (!(toType instanceof ConstArrayType) || !((ConstArrayType)toType).isConst())) {
                    // we are strictly const, and to type is not const
                    return false;
                }

                // const arrays are covariant
                return ts.isImplicitCastValid(this.base(), toType.toArray().base());
            }
            else {
                // Object = int[] 
                return super.isImplicitCastValidImpl(toType);
            }
        }

        return false;
    }
    
    protected void init() {
        JifTypeSystem ts = (JifTypeSystem)this.ts;
        if (methods == null) {
            methods = new ArrayList(1);
            
            // Add method public T const?[] clone()
            Type retType = ts.constArrayOf(position(), this.base(), 1, true, true);
            
            methods.add(ts.jifMethodInstance(position(),
                                          this,
                                          ts.Public(),
                                          retType,
                                          "clone",
                                          ts.topLabel(),
                                          false,
                                          Collections.EMPTY_LIST,
                                          Collections.EMPTY_LIST,
                                          ts.bottomLabel(),
                                          false,
                                          Collections.EMPTY_LIST,
                                          Collections.EMPTY_LIST));
        }
        
        if (fields == null) {
            fields = new ArrayList(2);
            
            // Add field public final int length
            Label fieldLabel = ts.thisLabel(this);
            JifFieldInstance fi = (JifFieldInstance)ts.fieldInstance(position(),
                                                this,
                                                ts.Public().Final(),
                                                ts.labeledType(position(), ts.Int(), fieldLabel),
                                                "length");
            fi.setLabel(fieldLabel);
            fi.setNotConstant();
            fields.add(fi);
        }
        
        if (interfaces == null) {
            interfaces = Collections.EMPTY_LIST;
        }
    }
    
}
