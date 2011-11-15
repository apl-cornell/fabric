package jif.lang;

public abstract class Closure_JIF_IMPL {
    public static boolean jif$Instanceof(final Principal jif$P, final Label jif$L, final Object o) {
        if (o instanceof Closure) {
            Closure c = (Closure) o;
            boolean ok = true;
            ok = ok && PrincipalUtil.equivalentTo(c.jif$getjif_lang_Closure_P(), jif$P);
            ok = ok && LabelUtil.singleton().equivalentTo(c.jif$getjif_lang_Closure_L(), jif$L);
            return ok;
        }
        return false;
    }
    
    public static Closure jif$cast$jif_lang_Closure(final Principal jif$P, final Label jif$L, final Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(jif$P, jif$L, o)) return (Closure) o;
        throw new ClassCastException();
    }
    
    public Closure_JIF_IMPL() { super(); }
}
