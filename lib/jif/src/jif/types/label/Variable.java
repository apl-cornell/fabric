package jif.types.label;

public interface Variable {
    String name();
    
    /**
     * Set the mustRuntimeRepresentable flag, meaning that any solution for this
     * VarLabel must be to a runtime representable label.
     */
    void setMustRuntimeRepresentable();
    
    /**
     * Does this var label need to map to a runtime representable label?
     */
    boolean mustRuntimeRepresentable();

}
