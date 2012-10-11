package mkdnstripper;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Strips Markdown codes from files.
 */
public class Main {

  private static int MAX_LINE_LENGTH = 72;

  /**
   * @param args
   *          list of source files to strip
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      System.err.println("Usage: mkdnstripper.Main [FILE]...");
      System.err.println();
      System.err.println("  FILE(s) are clobbered with stripped copies.");
      System.err.println();
      return;
    }

    // Construct a list of Files from the given arguments, ensuring we can read
    // each file.
    List<File> files = new ArrayList<File>(args.length);
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
      strip(file);
    }
  }

  public static void strip(File file) {
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(file));
    } catch (FileNotFoundException e) {
      System.err.println("File not found: " + file);
      return;
    }

    // Read the file into a buffer.
    List<String> text = new ArrayList<String>();
    while (true) {
      try {
        String line = reader.readLine();
        if (line == null) break;
        text.add(line);
      } catch (EOFException e) {
        break;
      } catch (IOException e) {
        e.printStackTrace();
        try {
          reader.close();
        } catch (IOException e1) {
        }
        return;
      }
    }

    text = unflow(text);
    text = unmark(text);
    text = flow(text);

    for (String s : text)
      System.out.println(s);

    try {
      reader.close();
    } catch (IOException e) {
    }

//    try {
//      Writer writer = new FileWriter(file, false);
//      writer.write((String) sym.value);
//      writer.close();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//
  }

  /**
   * Eliminates whitespace and newlines to unflow the given text.
   */
  private static List<String> unflow(List<String> text) {
    List<String> unflowed = new ArrayList<String>(text.size());
    for (int lineNum = 0; lineNum < text.size();) {
      // Read until we absolutely need a new line.
      String line = trimTrailingWhitespace(text.get(lineNum++));
      if (line.length() > 0 && !isSectionLine(line)) {
        while (lineNum < text.size()) {
          String nextLine = text.get(lineNum).trim();

          // Section lines ("----" and "====") go on their own line.
          if (isSectionLine(nextLine)) break;

          // Paragraph break.
          if (nextLine.length() == 0) break;

          // Bullet list.
          if (nextLine.startsWith("* ") || nextLine.startsWith("- ")) break;

          line += " " + nextLine;
          lineNum++;
        }
      }

      // Replace tabs with spaces.
      line = replaceTabs(line);
      unflowed.add(line);
    }

    return unflowed;
  }

  private static String trimTrailingWhitespace(String s) {
    return ("x" + s).trim().substring(1);
  }

  private static final Pattern TAB_PATTERN = Pattern.compile("\t");

  private static String replaceTabs(String line) {
    Matcher m = TAB_PATTERN.matcher(line);
    StringBuffer sb = new StringBuffer();
    while (m.find()) {
      m.appendReplacement(sb, "");
      int numSpaces = 8 - (sb.length() % 8);
      for (int i = 0; i < numSpaces; i++)
        sb.append(' ');
    }
    m.appendTail(sb);
    return sb.toString();
  }

  private static final Pattern SECTION_LINE_PATTERN = Pattern
      .compile("^((-+)|(=+))$");

  /**
   * Returns true iff given a string that, when trimmed, is entirely '='s or '-'s.
   */
  private static boolean isSectionLine(String s) {
    return SECTION_LINE_PATTERN.matcher(s.trim()).matches();
  }

  /**
   * Removes markdown annotations from the given text. Text is assumed to be
   * unflowed.
   */
  private static List<String> unmark(List<String> text) {
    List<String> unmarked = new ArrayList<String>(text.size());
    for (String line : text) {
      line = stripCitations(line);
      line = stripLinks(line);
      line = stripHeaderLabels(line);
      unmarked.add(line);
    }

    return unmarked;
  }

  private static List<String> flow(List<String> text) {
    List<String> flowed = new ArrayList<String>();
    for (int lineNum = 0; lineNum < text.size();) {
      String curLine = text.get(lineNum++);

      if (lineNum < text.size()) {
        // Handle section headings.
        String nextLine = text.get(lineNum);
        if (isSectionLine(nextLine)) {
          flowed.add(curLine);
          flowed.add(nextLine);
          lineNum++;
          continue;
        }
      }

      flowed.addAll(flow(curLine));
    }

    return flowed;
  }

  private static List<String> flow(String line) {
    if (line.length() < MAX_LINE_LENGTH)
      return Collections.singletonList(line);

    // Figure out how much the text has been indented.
    int indentSize = 0;
    while (line.charAt(indentSize) == ' ') {
      indentSize++;
    }
    String indent = line.substring(0, indentSize);

    // Unindent and detect whether we have a list.
    line = line.substring(indentSize);
    boolean haveList = line.startsWith("* ") || line.startsWith("- ");
    String listBullet;
    if (haveList) {
      // Strip list bullet for now.
      listBullet = line.substring(0, 2);
      line = line.substring(2).trim();
    } else {
      listBullet = "";
    }

    List<String> flowed =
        flow(line, MAX_LINE_LENGTH - indentSize - listBullet.length());
    List<String> result = new ArrayList<String>(flowed.size());
    for (String s : flowed) {
      // Handle list bullet/indent.
      if (result.isEmpty()) {
        s = listBullet + s;
      } else {
        s = (haveList ? "  " : "") + s;
      }

      result.add(indent + s);
    }

    return result;
  }

  private static final List<String> flow(String line, int length) {
    List<String> result = new ArrayList<String>();
    while (line.length() > length) {
      // Wrap once.
      // Figure out where to break the line.
      int breakIdx = line.lastIndexOf(' ', length - 1);
      int hyphenIdx = line.lastIndexOf('-', length - 1);
      if (breakIdx == -1 || breakIdx < hyphenIdx) breakIdx = hyphenIdx;
      if (breakIdx == -1) {
        breakIdx = line.indexOf(' ');
        hyphenIdx = line.indexOf('-');
        if (breakIdx == -1 || breakIdx < hyphenIdx) breakIdx = hyphenIdx;
        if (breakIdx == -1) break;
      }

      // Break the line.
      result.add(line.substring(0, breakIdx + 1).trim());
      line = line.substring(breakIdx + 1);
    }

    result.add(line);
    return result;
  }

  private static final Pattern CITE_PATTERN = Pattern
      .compile("\\s*@cite\\s+\\S+");

  private static String stripCitations(String line) {
    return trimTrailingWhitespace(regexpReplace(line, CITE_PATTERN, ""));
  }

  private static final Pattern LINK_PATTERN = Pattern
      .compile("\\[([^\\[]*?)\\]\\s*\\(.*?\\)");

  private static String stripLinks(String line) {
    return regexpReplace(line, LINK_PATTERN, "$1");
  }

  private static final Pattern HEADER_LABEL_PATTERN = Pattern
      .compile("\\s*\\{#\\S+?\\}");

  private static String stripHeaderLabels(String line) {
    return trimTrailingWhitespace(regexpReplace(line, HEADER_LABEL_PATTERN, ""));
  }

  private static String regexpReplace(String line, Pattern pattern,
      String replacement) {
    Matcher m = pattern.matcher(line);
    StringBuffer sb = new StringBuffer();
    while (m.find()) {
      m.appendReplacement(sb, replacement);
    }
    m.appendTail(sb);
    return sb.toString();
  }
}
