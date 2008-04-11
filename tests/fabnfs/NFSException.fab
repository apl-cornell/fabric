package fabnfs;

class NFSException extends Exception {
    long xid;
    long nfserr;
    
    NFSException(long x, long e) {
	xid = x;
	nfserr = e;
    }
    
    long GetXID() {
	return xid;
    }
    long GetError() {
	return nfserr;
    }
};
