package jif.types.principal;


/** The external principal is used primarily for testing purposes. 
 *  It is specified by the name of a principal, such as
 *  "Alice" and "Bob". See lib-src/jif/lang/ExternalPrincipal 
 * 
 */
public interface ExternalPrincipal extends Principal {
    String name();
}
