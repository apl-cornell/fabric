package fabric.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import fabric.common.FabricException;

public class TransactionPrepareFailedException extends FabricException {
  public final List<String> messages;

  public TransactionPrepareFailedException(Map<Core, TransactionPrepareFailedException> causes) {
    messages = new ArrayList<String>();
    for (Map.Entry<Core, TransactionPrepareFailedException> e : causes.entrySet())
      for (String s : e.getValue().messages)
        messages.add(e.getKey() + ": " + s);
  }
  
  public TransactionPrepareFailedException(List<TransactionPrepareFailedException> causes) {
    messages = new ArrayList<String>();
    for (TransactionPrepareFailedException exc : causes)
      messages.addAll(exc.messages);
  }

  public TransactionPrepareFailedException(String message) {
    messages = java.util.Collections.singletonList(message);
  }

  @Override
  public String getMessage() {
    String result = "Transaction failed to prepare.";
    
    for (String m : messages) {
      result += System.getProperty("line.separator") + "    " + m;
    }
    
    return result;
  }

}
