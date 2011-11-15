package jif.types.principal;


/** 
 * The top principal is able to act for all other principals.
 * The top principal can never appear in an authority clause, caller clause, 
 * etc.---it can only appear in a policy.
 */
public interface TopPrincipal extends Principal {
}
