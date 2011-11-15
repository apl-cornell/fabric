import java.io.*;

class ATMCard extends Object implements Serializable {
    public EncryptedObject encryptedKey;
    public int accountNumber;
    public String owner;

    public ATMCard(EncryptedObject eKey, int acct, String owner) {
	this.encryptedKey = eKey;
	this.accountNumber = acct;
	this.owner = owner;
    }

}
