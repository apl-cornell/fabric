package jif.ast;


/** An immutable representation of the Jif <code>declassify</code> expression. 
 *  <p>Grammar: <code>declassify(expression, label_1, label_2)</code> </p>
 *  If the label of the <code>expression</code> is less restrictive than 
 *  <code>label_2</code>, then declassify its label to <code>label_1</code>.
 */
public interface DeclassifyExpr extends DowngradeExpr
{

}
