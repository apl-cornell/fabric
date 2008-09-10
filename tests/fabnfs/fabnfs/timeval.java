package fabnfs;

// A simple timeval class to copy them to and from XDR packets.
class timeval extends java.lang.Object  {
    long seconds;
    long milliseconds;

    timeval(XDRPacket init) {
	Read(init);
    };

    timeval(long s, long ms) {
	Set(s, ms);
    };

    timeval(long l) {
	// convert a 64 bit long into 2 32 bit longs to pass to the regular
	//   set function
	long top = l >> 16;
	long bottom = l & 0xffff;

	bottom += (top & 0xffff) << 16;
	top = top >> 16;

	Set(top, bottom);
    }

    boolean Read(XDRPacket from) {
	seconds = from.GetLong();
	milliseconds = from.GetLong();
	return true;
    };

    boolean Emit(XDRPacket to) {
	to.AddLong(seconds);
	to.AddLong(milliseconds);
	return true;
    };

    boolean Set(long s, long ms) {
	seconds = s;
	milliseconds = ms;
	return true;
    };

    void Print() {
	System.out.print("Timeval: sec=" + seconds +
			 " msec=" + milliseconds + "\n");
    }
};

