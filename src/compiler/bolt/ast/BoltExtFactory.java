package bolt.ast;

public interface BoltExtFactory extends polyglot.ext.jl7.ast.JL7ExtFactory {
    /**
     * Creates an extension object for {@link bolt.ast.BottomPrincipal}.
     */
    polyglot.ast.Ext extBottomPrincipal();
    
    /**
     * Creates an extension object for {@link bolt.ast.ConfPolicy}.
     */
    polyglot.ast.Ext extConfPolicy();
    
    /**
     * Creates an extension object for {@link bolt.ast.ConjunctivePrincipal}.
     */
    polyglot.ast.Ext extConjunctivePrincipal();
    
    /**
     * Creates an extension object for {@link bolt.ast.DisjunctivePrincipal}.
     */
    polyglot.ast.Ext extDisjunctivePrincipal();
    
    /**
     * Creates an extension object for {@link bolt.ast.ExprLabel}.
     */
    polyglot.ast.Ext extExprLabel();
    
    /**
     * Creates an extension object for {@link bolt.ast.ExprPrincipal}.
     */
    polyglot.ast.Ext extExprPrincipal();
    
    /**
     * Creates an extension object for {@link bolt.ast.IntegPolicy}.
     */
    polyglot.ast.Ext extIntegPolicy();
    
    /**
     * Creates an extension object for {@link bolt.ast.JoinLabel}.
     */
    polyglot.ast.Ext extJoinLabel();
    
    /**
     * Creates an extension object for {@link bolt.ast.Label}.
     */
    polyglot.ast.Ext extLabel();
    
    /**
     * Creates an extension object for {@link bolt.ast.LabelComponent}.
     */
    polyglot.ast.Ext extLabelComponent();
    
    /**
     * Creates an extension object for {@link bolt.ast.MeetLabel}.
     */
    polyglot.ast.Ext extMeetLabel();
    
    /**
     * Creates an extension object for {@link bolt.ast.NewLabel}.
     */
    polyglot.ast.Ext extNewLabel();
    
    /**
     * Creates an extension object for {@link bolt.ast.NewPrincipal}.
     */
    polyglot.ast.Ext extNewPrincipal();
    
    /**
     * Creates an extension object for {@link bolt.ast.Policy}.
     */
    polyglot.ast.Ext extPolicy();
    
    /**
     * Creates an extension object for {@link bolt.ast.Principal}.
     */
    polyglot.ast.Ext extPrincipal();
    
    /**
     * Creates an extension object for {@link bolt.ast.ReaderPolicy}.
     */
    polyglot.ast.Ext extReaderPolicy();
    
    /**
     * Creates an extension object for {@link bolt.ast.TopPrincipal}.
     */
    polyglot.ast.Ext extTopPrincipal();
    
    /**
     * Creates an extension object for {@link bolt.ast.WriterPolicy}.
     */
    polyglot.ast.Ext extWriterPolicy();
}
