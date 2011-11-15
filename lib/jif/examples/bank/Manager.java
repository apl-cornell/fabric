import java.lang.*;
import java.io.*;

class Manager {
    public static void main(String [] args) {
    	if (args.length != 2) {
	    System.out.println("Usage: Manager <#CUSTOMERS> <#ATMS>");
	    System.exit(1);
	}
	int numCust = Integer.parseInt(args[0]);
	int numATMS = Integer.parseInt(args[1]);
	
	Bank bank; 
       	Log log;
	try {
	    log = new Log("Manager");
	    bank = new Bank(1000.00);  // Start bank with 1000.00
	} catch (IOException ioe) {
	    System.out.println("Initialization failed.");
	    System.exit(1);
	    return; // so Java knows that the variables have been initialized
	}

	ATM[] ATMS = new ATM[numATMS];
	Customer[] customers = new Customer[numCust];

	// Start time
	log.logMessage("Starting Time");
	Time time = new Time(bank);
	new Thread(time).start();


	try {
	// Start accountant
	log.logMessage("Starting accountant");
	Accountant accountant = new Accountant(bank);
	new Thread(accountant).start();

	for (int i=0; i<numATMS; i++) {
	    log.logMessage("Creating ATM# " + i);
	    ATMS[i] = new ATM(i, bank);
	    new Thread(ATMS[i]).start();
	}
	for (int i=0; i<numCust; i++) {
	    // create the customer and account
	    String name = "Customer" + i;
	    Password psswd = new Password(name);
	    RSAPrivateKey privKey = new RSAPrivateKey();
	    RSAPublicKey pubKey = privKey.createPublicKey();
	    EncryptedObject ePrivKey = psswd.encrypt(privKey);
	    int acctNumber = bank.addAccount(name, pubKey, Bank.CUSTOMER_RATE);
	    ATMCard card = new ATMCard(ePrivKey, acctNumber, name);
	    log.logMessage("Creating " + name);
	    customers[i] = new Customer(name, psswd, card, ATMS);
	    new Thread(customers[i]).start();
	}
	} catch (Exception e) {
	    log.logMessage("Exception " + e);
	    log.logMessage("Initialization failed.");
	    System.exit(1);
	}
	while (!(Input.getInt() == 0)) {
	    // do
	}
	// crude way to kill all the threads
	System.exit(0);
    }
}
