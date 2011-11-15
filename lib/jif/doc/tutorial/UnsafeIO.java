import java.io.*;


public class UnsafeIO {
   public static void print(String s) {
      System.out.print(s);
   }

   private static BufferedReader stdin = null;

   public static String readLine() throws IOException {
      if(stdin == null)
         stdin = new BufferedReader(new InputStreamReader(System.in));

      return stdin.readLine();
   }
}
