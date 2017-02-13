package bolt.ast;

public abstract class BoltAbstractExtFactory_c extends polyglot.ext.jl7.ast.JL7AbstractExtFactory_c implements BoltExtFactory {
    public BoltAbstractExtFactory_c() { super(); }
    
    public BoltAbstractExtFactory_c(polyglot.ast.ExtFactory nextExtFactory) {
        super(nextExtFactory);
    }
    
    @Override
    public final polyglot.ast.Ext extBottomPrincipal() {
        polyglot.ast.Ext e = extBottomPrincipalImpl();
        if (nextExtFactory() != null) {
            polyglot.ast.Ext e2;
            if (nextExtFactory() instanceof BoltExtFactory) {
                e2 = ((BoltExtFactory) nextExtFactory()).extBottomPrincipal();
            } else {
                e2 = nextExtFactory().extTerm();
            }
            e = composeExts(e, e2);
        }
        return postExtBottomPrincipal(e);
    }
    
    @Override
    public final polyglot.ast.Ext extConfPolicy() {
        polyglot.ast.Ext e = extConfPolicyImpl();
        if (nextExtFactory() != null) {
            polyglot.ast.Ext e2;
            if (nextExtFactory() instanceof BoltExtFactory) {
                e2 = ((BoltExtFactory) nextExtFactory()).extConfPolicy();
            } else {
                e2 = nextExtFactory().extTerm();
            }
            e = composeExts(e, e2);
        }
        return postExtConfPolicy(e);
    }
    
    @Override
    public final polyglot.ast.Ext extConjunctivePrincipal() {
        polyglot.ast.Ext e = extConjunctivePrincipalImpl();
        if (nextExtFactory() != null) {
            polyglot.ast.Ext e2;
            if (nextExtFactory() instanceof BoltExtFactory) {
                e2 = ((BoltExtFactory)
                        nextExtFactory()).extConjunctivePrincipal();
            } else {
                e2 = nextExtFactory().extTerm();
            }
            e = composeExts(e, e2);
        }
        return postExtConjunctivePrincipal(e);
    }
    
    @Override
    public final polyglot.ast.Ext extDisjunctivePrincipal() {
        polyglot.ast.Ext e = extDisjunctivePrincipalImpl();
        if (nextExtFactory() != null) {
            polyglot.ast.Ext e2;
            if (nextExtFactory() instanceof BoltExtFactory) {
                e2 = ((BoltExtFactory)
                        nextExtFactory()).extDisjunctivePrincipal();
            } else {
                e2 = nextExtFactory().extTerm();
            }
            e = composeExts(e, e2);
        }
        return postExtDisjunctivePrincipal(e);
    }
    
    @Override
    public final polyglot.ast.Ext extExprLabel() {
        polyglot.ast.Ext e = extExprLabelImpl();
        if (nextExtFactory() != null) {
            polyglot.ast.Ext e2;
            if (nextExtFactory() instanceof BoltExtFactory) {
                e2 = ((BoltExtFactory) nextExtFactory()).extExprLabel();
            } else {
                e2 = nextExtFactory().extTerm();
            }
            e = composeExts(e, e2);
        }
        return postExtExprLabel(e);
    }
    
    @Override
    public final polyglot.ast.Ext extExprPrincipal() {
        polyglot.ast.Ext e = extExprPrincipalImpl();
        if (nextExtFactory() != null) {
            polyglot.ast.Ext e2;
            if (nextExtFactory() instanceof BoltExtFactory) {
                e2 = ((BoltExtFactory) nextExtFactory()).extExprPrincipal();
            } else {
                e2 = nextExtFactory().extTerm();
            }
            e = composeExts(e, e2);
        }
        return postExtExprPrincipal(e);
    }
    
    @Override
    public final polyglot.ast.Ext extIntegPolicy() {
        polyglot.ast.Ext e = extIntegPolicyImpl();
        if (nextExtFactory() != null) {
            polyglot.ast.Ext e2;
            if (nextExtFactory() instanceof BoltExtFactory) {
                e2 = ((BoltExtFactory) nextExtFactory()).extIntegPolicy();
            } else {
                e2 = nextExtFactory().extTerm();
            }
            e = composeExts(e, e2);
        }
        return postExtIntegPolicy(e);
    }
    
    @Override
    public final polyglot.ast.Ext extJoinLabel() {
        polyglot.ast.Ext e = extJoinLabelImpl();
        if (nextExtFactory() != null) {
            polyglot.ast.Ext e2;
            if (nextExtFactory() instanceof BoltExtFactory) {
                e2 = ((BoltExtFactory) nextExtFactory()).extJoinLabel();
            } else {
                e2 = nextExtFactory().extTerm();
            }
            e = composeExts(e, e2);
        }
        return postExtJoinLabel(e);
    }
    
    @Override
    public final polyglot.ast.Ext extLabel() {
        polyglot.ast.Ext e = extLabelImpl();
        if (nextExtFactory() != null) {
            polyglot.ast.Ext e2;
            if (nextExtFactory() instanceof BoltExtFactory) {
                e2 = ((BoltExtFactory) nextExtFactory()).extLabel();
            } else {
                e2 = nextExtFactory().extTerm();
            }
            e = composeExts(e, e2);
        }
        return postExtLabel(e);
    }
    
    @Override
    public final polyglot.ast.Ext extLabelComponent() {
        polyglot.ast.Ext e = extLabelComponentImpl();
        if (nextExtFactory() != null) {
            polyglot.ast.Ext e2;
            if (nextExtFactory() instanceof BoltExtFactory) {
                e2 = ((BoltExtFactory) nextExtFactory()).extLabelComponent();
            } else {
                e2 = nextExtFactory().extTerm();
            }
            e = composeExts(e, e2);
        }
        return postExtLabelComponent(e);
    }
    
    @Override
    public final polyglot.ast.Ext extMeetLabel() {
        polyglot.ast.Ext e = extMeetLabelImpl();
        if (nextExtFactory() != null) {
            polyglot.ast.Ext e2;
            if (nextExtFactory() instanceof BoltExtFactory) {
                e2 = ((BoltExtFactory) nextExtFactory()).extMeetLabel();
            } else {
                e2 = nextExtFactory().extTerm();
            }
            e = composeExts(e, e2);
        }
        return postExtMeetLabel(e);
    }
    
    @Override
    public final polyglot.ast.Ext extNewLabel() {
        polyglot.ast.Ext e = extNewLabelImpl();
        if (nextExtFactory() != null) {
            polyglot.ast.Ext e2;
            if (nextExtFactory() instanceof BoltExtFactory) {
                e2 = ((BoltExtFactory) nextExtFactory()).extNewLabel();
            } else {
                e2 = nextExtFactory().extExpr();
            }
            e = composeExts(e, e2);
        }
        return postExtNewLabel(e);
    }
    
    @Override
    public final polyglot.ast.Ext extNewPrincipal() {
        polyglot.ast.Ext e = extNewPrincipalImpl();
        if (nextExtFactory() != null) {
            polyglot.ast.Ext e2;
            if (nextExtFactory() instanceof BoltExtFactory) {
                e2 = ((BoltExtFactory) nextExtFactory()).extNewPrincipal();
            } else {
                e2 = nextExtFactory().extExpr();
            }
            e = composeExts(e, e2);
        }
        return postExtNewPrincipal(e);
    }
    
    @Override
    public final polyglot.ast.Ext extPolicy() {
        polyglot.ast.Ext e = extPolicyImpl();
        if (nextExtFactory() != null) {
            polyglot.ast.Ext e2;
            if (nextExtFactory() instanceof BoltExtFactory) {
                e2 = ((BoltExtFactory) nextExtFactory()).extPolicy();
            } else {
                e2 = nextExtFactory().extTerm();
            }
            e = composeExts(e, e2);
        }
        return postExtPolicy(e);
    }
    
    @Override
    public final polyglot.ast.Ext extPrincipal() {
        polyglot.ast.Ext e = extPrincipalImpl();
        if (nextExtFactory() != null) {
            polyglot.ast.Ext e2;
            if (nextExtFactory() instanceof BoltExtFactory) {
                e2 = ((BoltExtFactory) nextExtFactory()).extPrincipal();
            } else {
                e2 = nextExtFactory().extTerm();
            }
            e = composeExts(e, e2);
        }
        return postExtPrincipal(e);
    }
    
    @Override
    public final polyglot.ast.Ext extReaderPolicy() {
        polyglot.ast.Ext e = extReaderPolicyImpl();
        if (nextExtFactory() != null) {
            polyglot.ast.Ext e2;
            if (nextExtFactory() instanceof BoltExtFactory) {
                e2 = ((BoltExtFactory) nextExtFactory()).extReaderPolicy();
            } else {
                e2 = nextExtFactory().extTerm();
            }
            e = composeExts(e, e2);
        }
        return postExtReaderPolicy(e);
    }
    
    @Override
    public final polyglot.ast.Ext extTopPrincipal() {
        polyglot.ast.Ext e = extTopPrincipalImpl();
        if (nextExtFactory() != null) {
            polyglot.ast.Ext e2;
            if (nextExtFactory() instanceof BoltExtFactory) {
                e2 = ((BoltExtFactory) nextExtFactory()).extTopPrincipal();
            } else {
                e2 = nextExtFactory().extTerm();
            }
            e = composeExts(e, e2);
        }
        return postExtTopPrincipal(e);
    }
    
    @Override
    public final polyglot.ast.Ext extWriterPolicy() {
        polyglot.ast.Ext e = extWriterPolicyImpl();
        if (nextExtFactory() != null) {
            polyglot.ast.Ext e2;
            if (nextExtFactory() instanceof BoltExtFactory) {
                e2 = ((BoltExtFactory) nextExtFactory()).extWriterPolicy();
            } else {
                e2 = nextExtFactory().extTerm();
            }
            e = composeExts(e, e2);
        }
        return postExtWriterPolicy(e);
    }
    
    protected polyglot.ast.Ext extBottomPrincipalImpl() {
        return extTermImpl();
    }
    
    protected polyglot.ast.Ext extConfPolicyImpl() { return extTermImpl(); }
    
    protected polyglot.ast.Ext extConjunctivePrincipalImpl() {
        return extTermImpl();
    }
    
    protected polyglot.ast.Ext extDisjunctivePrincipalImpl() {
        return extTermImpl();
    }
    
    protected polyglot.ast.Ext extExprLabelImpl() { return extTermImpl(); }
    
    protected polyglot.ast.Ext extExprPrincipalImpl() { return extTermImpl(); }
    
    protected polyglot.ast.Ext extIntegPolicyImpl() { return extTermImpl(); }
    
    protected polyglot.ast.Ext extJoinLabelImpl() { return extTermImpl(); }
    
    protected polyglot.ast.Ext extLabelImpl() { return extTermImpl(); }
    
    protected polyglot.ast.Ext extLabelComponentImpl() { return extTermImpl(); }
    
    protected polyglot.ast.Ext extMeetLabelImpl() { return extTermImpl(); }
    
    protected polyglot.ast.Ext extNewLabelImpl() { return extExprImpl(); }
    
    protected polyglot.ast.Ext extNewPrincipalImpl() { return extExprImpl(); }
    
    protected polyglot.ast.Ext extPolicyImpl() { return extTermImpl(); }
    
    protected polyglot.ast.Ext extPrincipalImpl() { return extTermImpl(); }
    
    protected polyglot.ast.Ext extReaderPolicyImpl() { return extTermImpl(); }
    
    protected polyglot.ast.Ext extTopPrincipalImpl() { return extTermImpl(); }
    
    protected polyglot.ast.Ext extWriterPolicyImpl() { return extTermImpl(); }
    
    protected polyglot.ast.Ext postExtBottomPrincipal(polyglot.ast.Ext ext) {
        return postExtTerm(ext);
    }
    
    protected polyglot.ast.Ext postExtConfPolicy(polyglot.ast.Ext ext) {
        return postExtTerm(ext);
    }
    
    protected polyglot.ast.Ext postExtConjunctivePrincipal(polyglot.ast.Ext ext) {
        return postExtTerm(ext);
    }
    
    protected polyglot.ast.Ext postExtDisjunctivePrincipal(polyglot.ast.Ext ext) {
        return postExtTerm(ext);
    }
    
    protected polyglot.ast.Ext postExtExprLabel(polyglot.ast.Ext ext) {
        return postExtTerm(ext);
    }
    
    protected polyglot.ast.Ext postExtExprPrincipal(polyglot.ast.Ext ext) {
        return postExtTerm(ext);
    }
    
    protected polyglot.ast.Ext postExtIntegPolicy(polyglot.ast.Ext ext) {
        return postExtTerm(ext);
    }
    
    protected polyglot.ast.Ext postExtJoinLabel(polyglot.ast.Ext ext) {
        return postExtTerm(ext);
    }
    
    protected polyglot.ast.Ext postExtLabel(polyglot.ast.Ext ext) {
        return postExtTerm(ext);
    }
    
    protected polyglot.ast.Ext postExtLabelComponent(polyglot.ast.Ext ext) {
        return postExtTerm(ext);
    }
    
    protected polyglot.ast.Ext postExtMeetLabel(polyglot.ast.Ext ext) {
        return postExtTerm(ext);
    }
    
    protected polyglot.ast.Ext postExtNewLabel(polyglot.ast.Ext ext) {
        return postExtExpr(ext);
    }
    
    protected polyglot.ast.Ext postExtNewPrincipal(polyglot.ast.Ext ext) {
        return postExtExpr(ext);
    }
    
    protected polyglot.ast.Ext postExtPolicy(polyglot.ast.Ext ext) {
        return postExtTerm(ext);
    }
    
    protected polyglot.ast.Ext postExtPrincipal(polyglot.ast.Ext ext) {
        return postExtTerm(ext);
    }
    
    protected polyglot.ast.Ext postExtReaderPolicy(polyglot.ast.Ext ext) {
        return postExtTerm(ext);
    }
    
    protected polyglot.ast.Ext postExtTopPrincipal(polyglot.ast.Ext ext) {
        return postExtTerm(ext);
    }
    
    protected polyglot.ast.Ext postExtWriterPolicy(polyglot.ast.Ext ext) {
        return postExtTerm(ext);
    }
}
