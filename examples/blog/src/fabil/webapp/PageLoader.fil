package webapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class PageLoader {

  public static PageStats getPageBenchmark(String page) throws IOException {
    URL url = new URL(page);
    URLConnection yc = url.openConnection();
    BufferedReader in = new BufferedReader(new InputStreamReader(
        yc.getInputStream()));
    String inputLine;
    String last = null;
    String second = null;
    while ((inputLine = in.readLine()) != null) {
      second = last;
      last = inputLine;
    }
    in.close();

    return PageStats.fromSerialized(second);
  }

  public static String getPage(String page) throws IOException {
    URL url = new URL(page);
    URLConnection yc = url.openConnection();
    BufferedReader in = new BufferedReader(new InputStreamReader(
        yc.getInputStream()));
    String inputLine;
    String total = "";
    while ((inputLine = in.readLine()) != null)
      total += inputLine + "\n";
    in.close();

    return total;
  }
}
