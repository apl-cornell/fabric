package jif.ast;


/** An immutable representation of the Jif <code>declassify</code> statement. 
 *  <p>Grammar: <code>declassify(label_1, label_2) stmt</code> </p>
 *  If the <I>pc</I> label of this declassify statement is less restrictive than 
 *  <code>label_2</code>, then declassify the <I>pc</I> label of <tt>stmt</tt>
 *  to <code>label_1</code>.
 */
public interface DeclassifyStmt extends DowngradeStmt
{
}
