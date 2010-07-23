package fabnfs;

class MTListItem extends java.lang.Object  {
    Object value;
    MTListItem next;

    MTListItem(Object o) {
	value = o;
	next = null;
    }
    Object Value() {
	return value;
    }

    MTListItem Next() {
	return next;
    }
    void SetNext(MTListItem to) {
	next = to;
    }
};

class MTList extends java.lang.Object  {
    MTListItem head;
    MTListItem tail;

    public MTList() {
	head = null;
	tail = null;
    }

    synchronized Object Get() {
	while (head == null) {
	    try {
		wait();
	    } catch(InterruptedException e) {
		System.err.print("MTList: Get: interrupted in wait.\n");
	    }
	}

	MTListItem item = head;
	head = head.Next();
	return item.Value();
    }

    synchronized void Add(Object toadd) {
	MTListItem item = new MTListItem(toadd);
	if (head == null) {
	    head = tail = item;
	}
	else {
	    tail.SetNext(item);
	    tail = item;
	}
	notify();
    }
};

