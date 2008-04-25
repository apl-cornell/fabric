package fabnfs;

class TimeMapper extends java.lang.Object  {
    long Seconds(long localTime) {
	return localTime / (1 << 32);
    }
    long MilliSeconds(long localTime) {
	return localTime % (1 << 32);
    }
};
