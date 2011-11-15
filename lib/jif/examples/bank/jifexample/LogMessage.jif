import java.io.*;

class LogMessage implements Serializable {
    public final static int CREATED = 0x00;
    public final static int CHALLENGE = 0x01;
    public final static int BALANCE = 0x02;
    public final static int DEPOSIT = 0x03;
    public final static int WITHDRAW_DENIED_ATM = 0x04;
    public final static int WITHDRAW_DENIED_FUNDS = 0x05;
    public final static int WITHDRAW_DENIED_LIMIT = 0x06;
    public final static int WITHDRAW_GRANTED = 0x07;
    public final static int ACCRUED = 0x08;

    public final static int OTHER = 0x09;

    private int acct;       // account number -1 if none
    private double delta;   // affect on relevant sum
    private String stringRep;
    private int type;
    private String timeStamp;
    private int sequenceNumber;

    public LogMessage(String s) {
	acct = -1;
	delta = 0.0;
	stringRep = s;
	type = OTHER;
	sequenceNumber = -1;
	timeStamp = "Unknown";
    }

    public LogMessage(int acct, int type, double delta) {
	this.acct = acct;
	this.type = type;
	this.delta = delta;
	stringRep = typeToString(type);
	sequenceNumber = -1;
	timeStamp = "Unknown";
    }

    public String toString() {
	return stringRep;
    }

    private String typeToString(int type) {
	switch(type) {
	case CREATED: return "Created";
	case CHALLENGE: return "Challenge";
	case BALANCE: return "Balance";
	case DEPOSIT: return "Deposit " + delta;
	case WITHDRAW_DENIED_ATM: return "Denied : ATM_DEPLETED";
	case WITHDRAW_DENIED_FUNDS: return "Denied : INSUFFICIENT_FUNDS";
	case WITHDRAW_DENIED_LIMIT: return "Denied : Daily Limit";
	case WITHDRAW_GRANTED: return "Granted : " + delta;
	case ACCRUED: return "Accrued";
	case OTHER: return "Other";
	default: return "Unknown";
	}
    }

    public void setTime(String time) {
	timeStamp = time;
    }

    public void setSeq(int seq) {
	sequenceNumber = seq;
    }

    public int getSeq() {
	return sequenceNumber;
    }

    public double getDelta() {
	return delta;
    }

}
