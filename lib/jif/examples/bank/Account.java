import java.lang.*;

// Probably need to synchronize these methods.

public class Account {
    // final principal customer
    public String name;    // not really needed
    public int number;     // -1 if not added to bank yet
    public RSAPublicKey key; // Used to authenticate customer

    private double balance;
    private double interestRate;
    private double cumulativeInterest;

    private double dailyWithdraw;

    public Account(String name, int number, RSAPublicKey key, double rate) {
	this.name = name;
	this.number = number;
	this.key = key;
	this.balance = 0.0;
	this.interestRate = rate;
	this.cumulativeInterest = 0.0;
	this.dailyWithdraw = 0.0;
    }

    public double balance() {
	return balance;
    }

    public void deposit(double amt) {
	balance += amt;
    }

    public void withdraw(double amt) throws InsufficientFunds, ExceededDailyLimit {
	if (amt + dailyWithdraw > Bank.MAX_DAILY_WITHDRAW) {
	    throw new ExceededDailyLimit();
	}
	if (amt > balance) {
	    throw new InsufficientFunds();
	} else {
	    balance -= amt;
	    dailyWithdraw += amt;
	}
    }

    // the accrue method is called daily to simulate interest accumulation
    // returns the interest earned on this account
    public double accrue() {
	double interest = balance * interestRate;
	balance += interest;
	cumulativeInterest += interest;

	// Set daily withdraw to 0.0 here.
	dailyWithdraw = 0.0;
	return interest;
    }
}
    
