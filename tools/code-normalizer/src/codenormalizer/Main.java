package codenormalizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import java_cup.runtime.Symbol;
import polyglot.util.ErrorInfo;
import polyglot.util.ErrorQueue;
import polyglot.util.SimpleErrorQueue;

/**
 * Normalizes Java, Fabric, and FabIL source files for LOC metrics by removing
 * comments and blank lines.
 */
public class Main {

  /**
   * @param args
   *          list of source files to normalize
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      System.err.println("Usage: codenormalizer.Main [FILE]...");
      System.err.println();
      System.err.println("  FILE(s) are clobbered with normalized copies.");
      System.err.println();
      return;
    }

    // Construct a list of Files from the given arguments, ensuring we can read
    // each file.
    List<File> files = new ArrayList<>(args.length);
    for (String filename : args) {
      File file = new File(filename);
      if (!file.canRead()) {
        System.err.println("Unable to read " + filename);
      } else {
        files.add(file);
      }
    }

    // Abort if any files were unreadable.
    if (files.size() != args.length) {
      System.err.println("Aborting -- no files changed.");
      return;
    }

    for (File file : files) {
      normalize(file);
    }
  }

  public static void normalize(File file) {
    try (Reader reader = new FileReader(file)) {
      ErrorQueue eq = new SimpleErrorQueue();
      Lexer lexer = new Lexer(reader, file.getPath(), eq);
      Grm grm = new Grm(lexer, eq);

      Symbol sym;
      try {
        sym = grm.parse();
      } catch (IOException e) {
        eq.enqueue(ErrorInfo.IO_ERROR, e.getMessage());
        return;
      } catch (RuntimeException e) {
        throw e;
      } catch (Exception e) {
        eq.enqueue(ErrorInfo.SYNTAX_ERROR, e.getMessage());
        return;
      }

      if (sym.value == null) {
        System.err.println("Error parsing " + file);
        return;
      }

      try (Writer writer = new FileWriter(file, false)) {
        writer.write((String) sym.value);
      } catch (IOException e) {
        eq.enqueue(ErrorInfo.IO_ERROR, e.getMessage());
      }
    } catch (FileNotFoundException e) {
      System.err.println("File not found: " + file);
      return;
    } catch (IOException e) {
    }
  }
}
