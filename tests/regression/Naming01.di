package regression;

import diaspora.client.*;

public class Naming01 {
  public static void main(String[] args) {
    System.out.println(Client.getClient().getCore("cornell.edu"));
  }
}

