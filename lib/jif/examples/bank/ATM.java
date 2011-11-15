import java.lang.*;
import java.io.*;

class ATM implements Runnable {
    private int number; // Identifying number of this ATM
    private Bank bank;  // Bank to which the ATM connects
    private ATMCard card;
    private RSAPrivateKey customerKey;
    private Log log;

    private ObjectInputStream keypadInput;
    public ObjectOutputStream keypad;
    public ObjectInputStream terminal;
    public ObjectOutputStream terminalOutput;

    public ATM(int number, Bank bank) throws IOException {
	this.bank = bank;
	this.number = number;
	this.log = new Log("ATM"+number);

	log.logMessage("Creating keypad stream");
	PipedInputStream pipeIn = new PipedInputStream();
	PipedOutputStream pipeOut = new PipedOutputStream(pipeIn);
	keypad = new ObjectOutputStream(pipeOut);
	keypadInput = new ObjectInputStream(pipeIn);

	log.logMessage("Creating terminal stream");
	pipeIn = new PipedInputStream();
	pipeOut = new PipedOutputStream(pipeIn);
	terminalOutput = new ObjectOutputStream(pipeOut);
        terminal = new ObjectInputStream(pipeIn);
    }

    public void ejectCard() {
	// close down the keypad  (?)
	// how?
	try{
	    keypad.flush();
	} catch (IOException ioe) {
	    log.logMessage("Exception " + ioe);
	}
	
	log.logMessage(card.owner + "'s card ejected.");
	this.card = null;  // need to syncrhonize on card?
    }
	
    private void logon () {
	log.logMessage("Waiting for customer");
	while (true) {
	    try {
		// get the card from the customer
		card = (ATMCard)keypadInput.readObject();
		log.logMessage("Customer " + card.owner + " inserted card.");
		terminalOutput.writeBoolean(true);		
		terminalOutput.flush();
		// get the password from the keypad
		Password psswd = (Password)keypadInput.readObject();
		// get the customer's private key from the card
		log.logMessage("Read password");

		if (psswd == null) {throw new Exception("psswd = null");}
		if (card == null) {throw new Exception("card = null");}
		if (card.encryptedKey == null) {throw new Exception("card.encryptedKey = null");}
		customerKey = (RSAPrivateKey)psswd.decrypt(card.encryptedKey);
		log.logMessage("Decrypted privateKey");
		// forget password
		psswd = null;

		log.logMessage("Getting challenge");
		// Check validity of card, password
		Challenge challenge = bank.getChallenge(card.accountNumber);
		log.logMessage("Computing response");
		Object response = customerKey.decrypt(challenge.customerPart);
		if (challenge.confirm(response)) {
		    log.logMessage("Challenge passed");
		    terminalOutput.writeBoolean(true);
		    terminalOutput.flush();
		    break;
		} else {
		    log.logMessage("Challenge failed");
		    terminalOutput.writeBoolean(false);
		    terminalOutput.flush();
		}
	    } catch (Exception e) {
		log.logMessage("Exception " + e);
	    }
	}
    }

    private void logoff () {
	// Stop acting for the customer
	log.logMessage("Logoff");
	customerKey = null;
    }

    /*
     * 1 - balance
     * 2 - deposit
     * 3 - withdraw
     * 4 - quit
     */ 
    private boolean transaction () {
	try {
	    int request = keypadInput.readInt();
	    Reply reply;
	    switch (request) {
	    case 1: {log.logMessage("Balance request"); reply = bank.balance(card.accountNumber); break;}
	    case 2: {log.logMessage("Deposit request"); reply = bank.deposit(card.accountNumber, keypadInput.readDouble()); break;}
	    case 3: {log.logMessage("Withdraw request"); reply = bank.withdraw(card.accountNumber, keypadInput.readDouble()); break;}
	    case 4: return false;
	    default: {log.logMessage("Bad selection"); return true;}
	    }
	    log.logMessage(reply.toString());
	    terminalOutput.writeObject(reply);
	    return true;
	} catch (IOException e) {
	    System.out.println(e);
	    return false;
	}
    }
	    
    public void run() {
	while (true) {
	    logon();
	    log.logMessage("Logon successful");	    
	    // Acquire the lock on the card, since it's now in the machine
	    synchronized(card) {
		while (transaction()) {
			// do transactions
		}
		logoff();
	    }
	    // finally, return card to user
	    ejectCard();
	}
    }
}


