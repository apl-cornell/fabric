import java.lang.*;
import java.io.*;

class Bank {
    private Account [] accounts;
    private int numAccts;

    private double totalAssets;  // Should be the sum of the balances in the accounts + profit
                         // total assets should also equal liquid + high + low
    private double liquidAssets;   // Available for withdraw
    private double investedAssets; // Not available for withdraw
    private double dailyProfit;    // Difference between investment return and customer interest

    public static final double MAX_DAILY_WITHDRAW = 300.00;
    public static final double CUSTOMER_RATE = 0.003;     // .3% interest compounded daily (about 3% yearly)
    public static final double INVESTMENT_RATE = 0.005;   // .5% interest compounded daily (about 6% yearly)

    private BankLog log;

    public Bank(double assets) throws IOException {
	totalAssets = assets;
	liquidAssets = assets;
	log = new BankLog("Bank");

	accounts = new Account[5];
	numAccts = 0;
    }

    public synchronized int getLogSeqNumber() {
	return log.getSequenceNumber();
    }

    public synchronized double getTotalAssets () {
	return totalAssets;
    }

    public synchronized double getLiquidAssets () {
	return liquidAssets;
    }

    public synchronized double getInvestedAssets () {
	return investedAssets;
    }

    // returns the new account number
    public synchronized int addAccount(String name, RSAPublicKey key, double rate) {
	Account acct = new Account(name, numAccts, key, rate);
	if (numAccts < (accounts.length - 1)) {
	    // there's room in the current array
	    accounts[numAccts] = acct;
	    numAccts++;
	} else {
	    // need to grow the array
	    Account [] newAccounts = new Account[numAccts + 5];
	    for (int i=0; i<accounts.length; i++) {
		newAccounts[i] = accounts[i];
	    }
	    newAccounts[numAccts] = acct;
	    accounts = newAccounts;
	    numAccts++;
	}
	log.logMessage(new LogMessage(numAccts - 1, LogMessage.CREATED, 0.0));
	return numAccts - 1;
    }

    public synchronized Challenge getChallenge(int acctNumber) {
	log.logMessage(new LogMessage(acctNumber, LogMessage.CHALLENGE, 0.0));
	return new Challenge(accounts[acctNumber].key);
    }

    public synchronized Reply balance(int acctNumber) {
	log.logMessage(new LogMessage(acctNumber, LogMessage.BALANCE, 0.0));
	return new Reply(Reply.BALANCE, accounts[acctNumber].balance());
    }

    // deposits go into liquid assets
    public synchronized Reply deposit(int acctNumber, double amt) {
	log.logMessage(new LogMessage(acctNumber, LogMessage.DEPOSIT, amt));
	accounts[acctNumber].deposit(amt);
	liquidAssets += amt;
	totalAssets += amt;
	return new Reply(Reply.BALANCE, accounts[acctNumber].balance());
    }

    // withdrawals come from liquid assets
    public synchronized Reply withdraw(int acctNumber, double amt) {
	Account acct = accounts[acctNumber];
	if (amt > liquidAssets) {
	    // not enough money to issue 
	    log.logMessage(new LogMessage(acctNumber, LogMessage.WITHDRAW_DENIED_ATM, 0.0));
	    return new Reply(Reply.ATM_DEPLETED, acct.balance());
	}
	try{
	    acct.withdraw(amt);
	} catch (InsufficientFunds e) {
	    log.logMessage(new LogMessage(acctNumber, LogMessage.WITHDRAW_DENIED_FUNDS, 0.0));
	    return new Reply(Reply.INSUFFICIENT_FUNDS, acct.balance());
	} catch (ExceededDailyLimit e) {
	    log.logMessage(new LogMessage(acctNumber, LogMessage.WITHDRAW_DENIED_LIMIT, 0.0));
	    return new Reply(Reply.DENIED_DAILY_LIMIT, acct.balance());
	}
	liquidAssets -= amt;
	totalAssets -= amt;
	log.logMessage(new LogMessage(acctNumber, LogMessage.WITHDRAW_GRANTED, -amt));	
	return new Reply(Reply.BALANCE, acct.balance());
    }

    public synchronized boolean transferLiquidToInvestment(double amt) {
	if (liquidAssets >= amt) {
	    liquidAssets -= amt;
	    investedAssets += amt;
	    return true;
	} else {
	    return false;
	}
    }

    public synchronized boolean transferInvestmenttoLiquic(double amt) {
	if (investedAssets >= amt) {
	    investedAssets -= amt;
	    liquidAssets += amt;
	    return true;
	} else {
	    return false;
	}
    }
	
    public double logReplay(int seqNumber) throws Exception {
	LogMessage msg;
	msg = (LogMessage)log.getMessageAt(seqNumber);
	return msg.getDelta();
    }

    public boolean logAvailable(int seqNumber) {
	return log.available(seqNumber);
    }
    
    // called "daily"
    public synchronized void accrue() {
	// Calculate the interest earned for the day
	double interest = investedAssets * INVESTMENT_RATE;

	// Put it into liquid assets
	liquidAssets += interest;
	totalAssets  += interest;
	log.logMessage(new LogMessage(-1, LogMessage.ACCRUED, interest));

	double customerInterest = 0.0;
	
	for (int i=0; i<numAccts; i++) {
	    customerInterest += accounts[i].accrue();
	}

	dailyProfit = interest - customerInterest;
    }
}

    



