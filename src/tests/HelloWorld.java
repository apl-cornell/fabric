import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.logging.Logger;


public class HelloWorld extends Example {

  public HelloWorld(String[] args) throws GeneralSecurityException, IOException {
    super(args);
  }

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {
    new HelloWorld(args).run();
  }

  @Override
  public void run() throws Exception {
    Logger log = Logger.getLogger("Example");
    
    log.info("Running Hello World Example");
    
    
  }

}
