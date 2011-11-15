package jif.lang;

/**
 * See the doucmentation for the Jif source file, $JIF/sig-src/jif/lang/Principal.jif.
 */
public abstract class Principal_JIF_IMPL {
    public static boolean jif$Instanceof(final Object o) {
        return o instanceof Principal;
    }
    
    public static Principal jif$cast$jif_lang_Principal(final Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(o)) return (Principal) o;
        throw new ClassCastException();
    }
    
    public Principal_JIF_IMPL() { super(); }
}
