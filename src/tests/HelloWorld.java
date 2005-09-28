import diaspora.common.*;
import diaspora.client.*;
import java.net.*;
import java.util.*;

public class HelloWorld {
  public static void main(String[] args) throws Throwable {
    Core core =
      new Core(Collections.singletonList(new InetSocketAddress("localhost", 
	      3372)));
    Client client = new Client(core, 30, 3);
    long[] oids = client.getNewOIDs();
    for (int i = 0; i < oids.length; i++)
      System.out.println(oids[i]);
  }
}

