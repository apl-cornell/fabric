import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

import diaspora.client.Client;
import diaspora.client.Core;
import diaspora.client.TransactionManager;

public abstract class Example {
  protected Example(String[] args) throws GeneralSecurityException, IOException {
    if (args.length != 4) {
      System.out.println("Usage: " + Tree.class.getName()
          + " keystore passwd truststore passwd");
      System.exit(1);
    }

    KeyStore keyStore = KeyStore.getInstance("JKS");
    char[] passwd = args[1].toCharArray();
    keyStore.load(new FileInputStream(args[0]), passwd);

    KeyStore trustStore = KeyStore.getInstance("JKS");
    trustStore.load(new FileInputStream(args[2]), args[3].toCharArray());

    Client.initialize(keyStore, passwd, trustStore, 1000, 50, 2, 3);
    Client client = Client.getClient();

    core0 = client.getCore(0);
    core1 = client.getCore(1);
    
    tx = TransactionManager.INSTANCE;
  }
  
  public abstract void run() throws Exception;
  
  protected Core   core0;
  protected Core   core1;
  
  protected TransactionManager tx;
}
