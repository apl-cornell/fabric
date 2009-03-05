package fabric.lang;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.HashSet;
import java.util.Set;

import jif.lang.*;
import fabric.client.Core;
import fabric.client.UnreachableNodeException;
import fabric.common.Crypto;
import fabric.common.exceptions.InternalError;
import fabric.util.Iterator;

/**
 * A signed statement that a principal enforces a label.
 */
public interface Enforces extends Object {

  Principal signer();

  Principal subject();

  Label label();

  boolean validate() throws SignatureException;

  public static final class $Proxy extends Object.$Proxy implements Enforces {
    public $Proxy(Enforces.$Impl impl) {
      super(impl);
    }

    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    public Principal signer() {
      return ((Enforces) fetch()).signer();
    }

    public Principal subject() {
      return ((Enforces) fetch()).subject();
    }

    public Label label() {
      return ((Enforces) fetch()).label();
    }

    public boolean validate() throws SignatureException {
      return ((Enforces) fetch()).validate();
    }
  }

  public static final class $Impl extends Object.$Impl implements Enforces {

    private final Principal signer;
    private final Principal subject;
    private final Label label;
    private final byte[] signature;

    public $Impl(Core core, Label label_, Principal signer, Principal subject,
        Label label) throws UnreachableNodeException, SignatureException {
      super(core, label_);

      this.signer = signer;
      this.subject = subject;
      this.label = label;
      this.signature = sign();
    }

    public Principal signer() {
      // Final field. No need to register read.
      return signer;
    }

    public Principal subject() {
      // Final field. No need to register read.
      return subject;
    }

    public Label label() {
      // Final field. No need to register read.
      return label;
    }

    public boolean validate() throws SignatureException {
      // First, verify the signature.
      Set<Principal> owners = new HashSet<Principal>();
      try {
        Signature verifier = Crypto.signatureInstance();
        verifier.initVerify(signer.getPublicKey());
        updateSignature(verifier, owners);
        if (!verifier.verify(signature)) return false;
      } catch (NoSuchAlgorithmException e) {
        throw new InternalError(e);
      } catch (InvalidKeyException e) {
        throw new InternalError(e);
      }

      // Next, check that the signer acts for all policy owners.
      for (Principal owner : owners) {
        if (!owner.delegatesTo(signer)) return false;
      }
      
      return true;
    }

    private byte[] sign() throws SignatureException {
      try {
        Signature signer = Crypto.signatureInstance();
        signer.initSign(this.signer.getPrivateKeyObject().getKey());
        updateSignature(signer, null);
        return signer.sign();
      } catch (NoSuchAlgorithmException e) {
        throw new InternalError(e);
      } catch (InvalidKeyException e) {
        throw new InternalError(e);
      }
    }

    private void updateSignature(Signature sig, Set<Principal> owners)
        throws SignatureException {
      try {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bos);

        // Serialize the subject's oid.
        serialize(out, subject);

        // Serialize the label.
        serialize(out, owners, label.confPolicy());
        serialize(out, owners, label.integPolicy());

        // Sign the serialized label.
        sig.update(bos.toByteArray());
      } catch (IOException e) {
        throw new InternalError(e);
      }
    }

    private void serialize(DataOutput out, Principal principal)
        throws IOException {
      out.writeUTF(principal.$getCore().name());
      out.writeLong(principal.$getOnum());
    }

    private void serialize(DataOutput out, Set<Principal> owners, Policy policy)
        throws IOException {

      if (policy instanceof ConfPolicy) {
        serialize(out, owners, (ConfPolicy) policy);
        return;
      }

      if (policy instanceof IntegPolicy) {
        serialize(out, owners, (IntegPolicy) policy);
        return;
      }

      throw new InternalError("Unknown policy type: " + policy.getClass());
    }

    private void serialize(DataOutput out, Set<Principal> owners,
        ConfPolicy policy) throws IOException {

      if (policy instanceof ReaderPolicy) {
        serialize(out, owners, (ReaderPolicy) policy);
        return;
      }

      if (policy instanceof JoinConfPolicy) {
        serialize(out, owners, (JoinConfPolicy) policy);
        return;
      }

      if (policy instanceof MeetConfPolicy) {
        serialize(out, owners, (MeetConfPolicy) policy);
        return;
      }

      throw new InternalError("Unknown confidentiality policy type: "
          + policy.getClass());
    }

    private void serialize(DataOutput out, Set<Principal> owners,
        IntegPolicy policy) throws IOException {

      if (policy instanceof WriterPolicy) {
        serialize(out, owners, (WriterPolicy) policy);
        return;
      }

      if (policy instanceof JoinIntegPolicy) {
        serialize(out, owners, (JoinIntegPolicy) policy);
        return;
      }

      if (policy instanceof MeetIntegPolicy) {
        serialize(out, owners, (MeetIntegPolicy) policy);
        return;
      }

      throw new InternalError("Unknown integrity policy type: "
          + policy.getClass());
    }

    private enum PolicyType {
      READER, JOIN_CONF, MEET_CONF, WRITER, JOIN_INTEG, MEET_INTEG
    }

    private void serialize(DataOutput out, Set<Principal> owners,
        ReaderPolicy policy) throws IOException {
      out.writeByte(PolicyType.READER.ordinal());

      Principal owner = policy.owner();
      if (owners != null) owners.add(owner);
      serialize(out, owner);
      serialize(out, policy.reader());
    }

    private void serialize(DataOutput out, Set<Principal> owners,
        JoinConfPolicy policy) throws IOException {
      out.writeByte(PolicyType.JOIN_CONF.ordinal());
      serialize(out, owners, (JoinPolicy) policy);
    }

    private void serialize(DataOutput out, Set<Principal> owners,
        MeetConfPolicy policy) throws IOException {
      out.writeByte(PolicyType.MEET_CONF.ordinal());
      serialize(out, owners, (MeetPolicy) policy);
    }

    private void serialize(DataOutput out, Set<Principal> owners,
        WriterPolicy policy) throws IOException {
      out.writeByte(PolicyType.WRITER.ordinal());

      Principal owner = policy.owner();
      if (owners != null) owners.add(owner);
      serialize(out, owner);
      serialize(out, policy.writer());
    }

    private void serialize(DataOutput out, Set<Principal> owners,
        JoinIntegPolicy policy) throws IOException {
      out.writeByte(PolicyType.JOIN_INTEG.ordinal());
      serialize(out, owners, (JoinPolicy) policy);
    }

    private void serialize(DataOutput out, Set<Principal> owners,
        MeetIntegPolicy policy) throws IOException {
      out.writeByte(PolicyType.MEET_INTEG.ordinal());
      serialize(out, owners, (MeetPolicy) policy);
    }

    private void serialize(DataOutput out, Set<Principal> owners,
        JoinPolicy policy) throws IOException {
      fabric.util.Set components = policy.joinComponents();
      for (Iterator it = components.iterator(); it.hasNext();) {
        Policy p = (Policy) it.next();
        serialize(out, owners, p);
      }
    }

    private void serialize(DataOutput out, Set<Principal> owners,
        MeetPolicy policy) throws IOException {
      fabric.util.Set components = policy.meetComponents();
      for (Iterator it = components.iterator(); it.hasNext();) {
        Policy p = (Policy) it.next();
        serialize(out, owners, p);
      }
    }
  }
}
