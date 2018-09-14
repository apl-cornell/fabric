package fabil.extension;

import java.util.ArrayList;
import java.util.List;

import fabil.ast.Atomic;
import fabil.types.FabILTypeSystem;
import fabil.visit.AtomicRewriter;
import polyglot.ast.Assign;
import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Stmt;
import polyglot.types.ArrayType;
import polyglot.types.Flags;
import polyglot.types.LocalInstance;
import polyglot.types.Type;
import polyglot.util.Position;

public class AtomicExt_c extends FabILExt_c {
  @Override
  public Node rewriteAtomic(AtomicRewriter ar) {
    Atomic atomic = (Atomic) node();
    NodeFactory nf = ar.nodeFactory();

    // Note: this needs to be built by the NF rather than the QQ because
    // otherwise it will be ambiguous and will fail further translation
    Position CG = Position.compilerGenerated();
    Stmt begin = nf.Eval(CG,
        nf.Call(CG, ar.transactionManager(), nf.Id(CG, "startTransaction")));
    Stmt commit = nf.Eval(CG,
        nf.Call(CG, ar.transactionManager(), nf.Id(CG, "commitTransaction")));
    Stmt abort = nf.Eval(CG,
        nf.Call(CG, ar.transactionManager(), nf.Id(CG, "abortTransaction")));
    Stmt abortWrites = nf.Eval(CG,
        nf.Call(CG, ar.transactionManager(), nf.Id(CG, "abortTransactionUpdates")));

    FabILTypeSystem ts = ar.typeSystem();

    List<Stmt> lds = new ArrayList<>();
    List<Stmt> restores = new ArrayList<>();

    for (LocalInstance li : atomic.updatedLocals()) {
      Id lName = nf.Id(Position.compilerGenerated(), li.name());
      Id vName = nf.Id(Position.compilerGenerated(),
          li.name() + "$var" + (freshTid++));

      Type lt = li.type();
      if (lt.isArray()) {
        // Arrays have been translated in ProxyRewriter,
        // so we have to do it manually here.
        ArrayType at = lt.toArray();
        if (ts.isPureFabricType(at)) {
          lt = ts.toFabricRuntimeArray(at);
        }
      }

      lds.add(nf.LocalDecl(Position.compilerGenerated(), Flags.NONE,
          nf.CanonicalTypeNode(Position.compilerGenerated(), lt), vName,
          nf.Local(Position.compilerGenerated(), lName)));
      restores.add(nf.Eval(Position.compilerGenerated(),
          nf.LocalAssign(Position.compilerGenerated(),
              nf.Local(Position.compilerGenerated(), lName), Assign.ASSIGN,
              nf.Local(Position.compilerGenerated(), vName))));
    }

    String label = "$label" + (freshTid++);
    String successFlag = "$commit" + (freshTid++);
    String retryFlag = "$retry" + (freshTid++);
    String keepReadsFlag = "$keepReads" + (freshTid++);
    String e = "$e" + (freshTid++);
    String currentTid = "$currentTid" + (freshTid++);
    String tm = "$tm" + (freshTid++);
    String backoff = "$backoff" + (freshTid++);
    String doBackoff = "$doBackoff" + (freshTid++);
    String backoffEnabled = "$backoffEnabled" + (freshTid++);

    // @formatter:off
    String block = "{\n" + "  %LS\n"
        + "  fabric.worker.transaction.TransactionManager " + tm
        + " = fabric.worker.transaction.TransactionManager.getInstance();\n"
        + "  boolean " + backoffEnabled
        + " = fabric.worker.Worker.getWorker().config.txRetryBackoff;\n"
        + "  int " + backoff + " = 1;\n"
        + "  boolean " + doBackoff + " = true;\n"
        + "  boolean " + retryFlag + " = true;\n"
        + "  boolean " + keepReadsFlag + " = false;\n"
        + "  " + label + ": for (boolean " + successFlag + " = false; !" + successFlag + "; ) {\n"
        + "    if (" + backoffEnabled +") {\n"
        + "      if (" + doBackoff + ") {"
        + "        if (" + backoff + " > 32) {\n"
        + "          while (true) {\n"
        + "            try {\n"
        + "              java.lang.Thread.sleep(java.lang.Math.round(java.lang.Math.random() * " + backoff + "));\n"
        + "              break;\n"
        + "            } catch (java.lang.InterruptedException " + e + ") {\n"
        + "            }\n"
        + "          }\n"
        + "        }\n"
        + "        if (" + backoff + " < 5000) " + backoff + " *= 2;\n"
        + "      }\n"
        + "      " + doBackoff + " = " + backoff + " <= 32 || !" + doBackoff + ";\n"
        + "    }\n"
        + "    " + successFlag + " = true;\n"
        + "    %S\n"
        + "    try {\n"
        + "      try {\n"
        + "        %LS\n"
        + "      }\n"
        + "      catch (final fabric.worker.RetryException " + e + ") {\n"
        + "        throw " + e + ";\n"
        + "      }\n"
        + (atomic.mayAbort()
            ?
            "      catch (final fabric.worker.UserAbortException " + e + ") {\n"
          + "        throw " + e + ";\n"
          + "      }\n"
            :
            "")
        + "      catch (final fabric.worker.TransactionAbortingException " + e + ") {\n"
        + "        throw " + e + ";\n"
        + "      }\n"
        + "      catch (final fabric.worker.TransactionRestartingException " + e + ") {\n"
        + "        throw " + e + ";\n"
        + "      }\n"
        + "      catch (final fabric.worker.metrics.LockConflictException " + e + ") {\n"
        + "        throw " + e + ";\n"
        + "      }\n"
        + "      catch (final Throwable " + e + ") {\n"
        + "        " + tm + ".getCurrentLog().checkRetrySignal();\n"
        + "        throw " + e + ";\n"
        + "      }\n"
        + "    }\n"
        + "    catch (final fabric.worker.RetryException " + e + ") {\n"
        + "      " + successFlag + " = false;\n"
        + "      continue " + label + ";\n"
        + "    }\n"
        + (atomic.mayAbort()
            ?
            "    catch (final fabric.worker.UserAbortException " + e + ") {\n"
          + "      " + successFlag + " = false;\n"
          + "      " + retryFlag + " = false;\n"
          + "    }\n"
            :
            "")
        + "    catch (fabric.worker.TransactionAbortingException " + e + ") {\n"
        + "      " + successFlag + " = false;\n"
        + "      " + retryFlag + " = false;\n"
        + "      " + keepReadsFlag + " = " + e + ".keepReads;\n"
        + "      if (" + tm + ".checkForStaleObjects()) {\n"
        + "        " + retryFlag + " = true;\n"
        + "        " + keepReadsFlag + " = false;\n"
        + "        continue " + label + ";\n"
        + "      }\n"
        + "      fabric.common.TransactionID " + currentTid + " = " + tm + ".getCurrentTid();\n"
        + "      if (" + e + ".tid == null || !" + e + ".tid.isDescendantOf(" + currentTid + ")) {\n"
        + "        throw " + e + ";\n"
        + "      }\n"
        + "      throw new fabric.worker.UserAbortException();\n"
        + "    }\n"
        + "    catch (final fabric.worker.TransactionRestartingException " + e + ") {\n"
        + "      " + successFlag + " = false;\n"
        + "      fabric.common.TransactionID " + currentTid + " = \n"
        + "        " + tm + ".getCurrentTid();\n"
        + "      if (" + e + ".tid.isDescendantOf(" + currentTid + "))\n"
        + "        continue " + label + ";\n"
        + "      if (" + currentTid + ".parent != null) {\n"
        + "        " + retryFlag + " = false;\n"
        + "        throw " + e + ";\n"
        + "      }\n"
        + "      throw new InternalError(\"Something is broken with \"\n"
        + "          + \"transaction management. Got a signal to restart a \"\n"
        + "          +  \"different transaction than the one being managed.\");\n"
        + "    }\n"
        + "    catch (final fabric.worker.metrics.LockConflictException " + e + ") {\n"
        + "      " + successFlag + " = false;\n"
        + "      if (" + tm + ".checkForStaleObjects()) continue " + label + ";\n"
        + "      fabric.common.TransactionID " + currentTid + " = " + tm + ".getCurrentTid();\n"
        + "      if (" + e + ".tid.isDescendantOf(" + currentTid + ")) {\n"
        + "        " + retryFlag + " = true;\n"
        + "      } else if (" + currentTid + ".parent != null) {\n"
        + "        " + retryFlag + " = false;\n"
        + "        throw " + e + ";\n"
        + "      } else {\n"
        + "        throw new InternalError(\"Something is broken with transaction \"\n"
        + "            + \"management. Got a signal for a lock conflict in a different \"\n"
        + "            + \"transaction than the one being managed.\");\n"
        + "      }\n"
        + "    }\n"
        + "    catch (final Throwable " + e + ") {\n"
        + "      " + successFlag + " = false;\n"
        + "      if (" + tm + ".checkForStaleObjects()) continue " + label + ";\n"
        + "      " + retryFlag + " = false;\n"
        + "      throw new fabric.worker.AbortException(" + e + ");\n"
        + "    }\n"
        + "    finally {\n"
        + "      if (" + successFlag + ") {\n"
        + "        fabric.common.TransactionID " + currentTid + " = \n"
        + "          " + tm + ".getCurrentTid();\n"
        + "        try {\n"
        + "          %S\n"
        + "        }\n"
        + "        catch (final fabric.worker.AbortException " + e + ") {\n"
        + "          " + successFlag + " = false;\n"
        + "        } catch (final fabric.worker.TransactionAbortingException " + e + ") {\n"
        + "          " + successFlag + " = false;\n"
        + "          " + retryFlag + " = false;\n"
        + "          " + keepReadsFlag + " = " + e + ".keepReads;\n"
        + "          if (" + tm + ".checkForStaleObjects()) {\n"
        + "            " + retryFlag + " = true;\n"
        + "            " + keepReadsFlag + " = false;\n"
        + "            continue " + label + ";\n"
        + "          }\n"
        + "          if (" + e + ".tid == null || !" + e + ".tid.isDescendantOf(" + currentTid + "))\n"
        + "            throw " + e + ";\n"
        + "          throw new fabric.worker.UserAbortException();\n"
        + "        }\n"
        + "        catch (final fabric.worker.TransactionRestartingException " + e + ") {\n"
        + "          " + successFlag + " = false;\n"
        + "          " + currentTid + " = \n"
        + "            " + tm + ".getCurrentTid();\n"
        + "          if (" + currentTid + " != null) {\n"
        + "            if (" + e + ".tid.equals(" + currentTid + ")\n"
        + "                || !" + e + ".tid.isDescendantOf(" + currentTid + ")) {\n"
        + "              throw " + e + ";\n"
        + "            }\n"
        + "          }\n"
        + "        }\n"
        + "      } else if (" + keepReadsFlag + ") {\n"
        + "        %S\n"
        + "      }\n"
        + "      else {\n"
        + "        %S\n"
        + "      }"
        + "      if (!" + successFlag + ") {\n"
        + "        { %LS }\n"
        + "        if (" + retryFlag + ") {\n"
        + "          continue " + label + ";\n"
        + "        }\n"
        + "      }\n"
        + "    }\n"
        + "  }\n"
        + "}\n";
    // @formatter:on

    return ar.qq().parseStmt(block, lds, begin, atomic.statements(), commit,
        abortWrites, abort, restores);
  }

  private static int freshTid = 0;
}
