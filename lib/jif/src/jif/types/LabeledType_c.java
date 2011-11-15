package jif.types;

import jif.types.label.Label;
import polyglot.types.*;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/** An implementation of the <code>LabeledType</code> interface. 
 */
public class LabeledType_c extends Type_c implements LabeledType
{
    protected Type typePart;
    protected Label labelPart;

    public LabeledType_c(JifTypeSystem ts, Position pos, Type typePart, Label labelPart) {
	super(ts, pos);
	this.typePart = typePart;
	this.labelPart = labelPart;
	if (typePart == null || labelPart == null) {
	    throw new InternalCompilerError("Null args: " + typePart + " and " + labelPart);
	}
    }

    public boolean isCanonical() {
	return typePart.isCanonical() && labelPart.isCanonical();
    }

    public Type typePart() {
	return this.typePart;
    }

    public LabeledType typePart(Type typePart) {
	LabeledType_c n = (LabeledType_c) copy();
	n.typePart = typePart;
	return n;
    }

    public Label labelPart() {
	return this.labelPart;
    }

    public LabeledType labelPart(Label labelPart) {
	LabeledType_c n = (LabeledType_c) copy();
	n.labelPart = labelPart;
	return n;
    }

    public String toString() {
	return typePart.toString() + labelPart.toString();
    }

    public String translate(Resolver c) {
	return typePart.translate(c);
    }

    public boolean equalsImpl(TypeObject t) {
        // only return pointer equals. This method may occasionally be called, due to
        // the existence of the JifTypeSystem.equalsNoStrip method.
        return this == t;
        // throw new InternalCompilerError(this + ".equalsImpl(" + t + ") called");
    }
    
    public ClassType toClass() { return typePart.toClass(); }
    public NullType toNull() { return typePart.toNull(); }
    public ReferenceType toReference() { return typePart.toReference(); }
    public PrimitiveType toPrimitive() { return typePart.toPrimitive(); }
    public ArrayType toArray() { return typePart.toArray(); }

    public boolean isPrimitive() { return typePart.isPrimitive(); }
    public boolean isVoid() { return typePart.isVoid(); }
    public boolean isBoolean() { return typePart.isBoolean(); }
    public boolean isChar() { return typePart.isChar(); }
    public boolean isByte() { return typePart.isByte(); }
    public boolean isShort() { return typePart.isShort(); }
    public boolean isInt() { return typePart.isInt(); }
    public boolean isLong() { return typePart.isLong(); }
    public boolean isFloat() { return typePart.isFloat(); }
    public boolean isDouble() { return typePart.isDouble(); }
    public boolean isIntOrLess() { return typePart.isIntOrLess(); }
    public boolean isLongOrLess() { return typePart.isLongOrLess(); }
    public boolean isNumeric() { return typePart.isNumeric(); }

    public boolean isReference() { return typePart.isReference(); }
    public boolean isNull() { return typePart.isNull(); }
    public boolean isArray() { return typePart.isArray(); }
    public boolean isClass() { return typePart.isClass(); }

    public boolean isThrowable() { return typePart.isThrowable(); }
    public boolean isUncheckedException() { return typePart.isUncheckedException(); }
}
