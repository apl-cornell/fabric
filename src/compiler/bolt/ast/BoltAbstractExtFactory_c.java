package bolt.ast;

import polyglot.ast.Ext;
import polyglot.ast.ExtFactory;
import polyglot.ext.jl7.ast.JL7AbstractExtFactory_c;

public abstract class BoltAbstractExtFactory_c extends JL7AbstractExtFactory_c
    implements BoltExtFactory {

  public BoltAbstractExtFactory_c() {
    super();
  }

  public BoltAbstractExtFactory_c(ExtFactory nextExtFactory) {
    super(nextExtFactory);
  }

  @Override
  public Ext extLabelComponent() {
    Ext e = extLabelComponentImpl();

    if (nextExtFactory() != null) {
      Ext e2;
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
  public Ext extPolicy() {
    Ext e = extPolicyImpl();

    if (nextExtFactory() != null) {
      Ext e2;
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
  public Ext extConfPolicy() {
    Ext e = extConfPolicyImpl();

    if (nextExtFactory() != null) {
      Ext e2;
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
  public Ext extIntegPolicy() {
    Ext e = extIntegPolicyImpl();

    if (nextExtFactory() != null) {
      Ext e2;
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
  public Ext extReaderPolicy() {
    Ext e = extReaderPolicyImpl();

    if (nextExtFactory() != null) {
      Ext e2;
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
  public Ext extWriterPolicy() {
    Ext e = extWriterPolicyImpl();

    if (nextExtFactory() != null) {
      Ext e2;
      if (nextExtFactory() instanceof BoltExtFactory) {
        e2 = ((BoltExtFactory) nextExtFactory()).extWriterPolicy();
      } else {
        e2 = nextExtFactory().extTerm();
      }
      e = composeExts(e, e2);
    }
    return postExtWriterPolicy(e);
  }

  @Override
  public Ext extLabel() {
    Ext e = extLabelImpl();

    if (nextExtFactory() != null) {
      Ext e2;
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
  public Ext extJoinLabel() {
    Ext e = extJoinLabelImpl();

    if (nextExtFactory() != null) {
      Ext e2;
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
  public Ext extPrincipal() {
    Ext e = extPrincipalImpl();

    if (nextExtFactory() != null) {
      Ext e2;
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
  public Ext extTopPrincipal() {
    Ext e = extTopPrincipalImpl();

    if (nextExtFactory() != null) {
      Ext e2;
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
  public Ext extBottomPrincipal() {
    Ext e = extBottomPrincipalImpl();

    if (nextExtFactory() != null) {
      Ext e2;
      if (nextExtFactory() instanceof BoltExtFactory) {
        e2 = ((BoltExtFactory) nextExtFactory()).extBottomPrincipal();
      } else {
        e2 = nextExtFactory().extTerm();
      }
      e = composeExts(e, e2);
    }
    return postExtBottomPrincipal(e);
  }

  protected Ext extLabelComponentImpl() {
    return extTermImpl();
  }

  protected Ext extPolicyImpl() {
    return extLabelComponentImpl();
  }

  protected Ext extConfPolicyImpl() {
    return extPolicyImpl();
  }

  protected Ext extIntegPolicyImpl() {
    return extPolicyImpl();
  }

  protected Ext extReaderPolicyImpl() {
    return extConfPolicyImpl();
  }

  protected Ext extWriterPolicyImpl() {
    return extIntegPolicyImpl();
  }

  protected Ext extLabelImpl() {
    return extLabelComponentImpl();
  }

  protected Ext extJoinLabelImpl() {
    return extLabelImpl();
  }

  protected Ext extPrincipalImpl() {
    return extTermImpl();
  }

  protected Ext extTopPrincipalImpl() {
    return extPrincipalImpl();
  }

  protected Ext extBottomPrincipalImpl() {
    return extPrincipalImpl();
  }

  protected Ext postExtLabelComponent(Ext e) {
    return postExtTerm(e);
  }

  protected Ext postExtPolicy(Ext e) {
    return postExtLabelComponent(e);
  }

  protected Ext postExtConfPolicy(Ext e) {
    return postExtPolicy(e);
  }

  protected Ext postExtIntegPolicy(Ext e) {
    return postExtPolicy(e);
  }

  protected Ext postExtReaderPolicy(Ext e) {
    return postExtConfPolicy(e);
  }

  protected Ext postExtWriterPolicy(Ext e) {
    return postExtIntegPolicy(e);
  }

  protected Ext postExtLabel(Ext e) {
    return postExtLabelComponent(e);
  }

  protected Ext postExtJoinLabel(Ext e) {
    return postExtLabel(e);
  }

  protected Ext postExtPrincipal(Ext e) {
    return postExtTerm(e);
  }

  protected Ext postExtTopPrincipal(Ext e) {
    return postExtPrincipal(e);
  }

  protected Ext postExtBottomPrincipal(Ext e) {
    return postExtPrincipal(e);
  }
}
