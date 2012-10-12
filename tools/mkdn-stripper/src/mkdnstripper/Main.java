package mkdnstripper;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    if (args.length != 2) {
      System.err.println("Usage: mkdnstripper.Main SRC DEST");
      System.err.println();
      System.err.println("  Reads from SRC, strips Markdown codes, and writes "
          + "the result to DEST.");
      System.err.println();
      return;
    }

    // Ensure we can read the input file.
    File source = new File(args[0]);
    if (!source.canRead()) {
      System.err.println("Unable to read " + args[0]);
      return;
    }
    
    List<String> text = readFile(source);
    if (text == null) return;
    
    text = strip(text);

    try {
      Writer writer = new FileWriter(new File(args[1]), false);
      boolean first = true;
      for (String s : text) {
        writer.write((first ? "" : "\n") + s);
        first = false;
      }
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static List<String> readFile(File file) {
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(file));
    } catch (FileNotFoundException e) {
      System.err.println("File not found: " + file);
      return null;
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
        return null;
      }
    }

    try {
      reader.close();
    } catch (IOException e) {
    }

    return text;
  }

  /**
   * Strips markdown from a chunk of text and reflows it.
   */
  public static List<String> strip(List<String> text) {
    return flow(stripNoFlow(text, Collections.<String, Integer> emptyMap()));
  }

  private static List<String> stripNoFlow(List<String> text,
      Map<String, Integer> citationMap) {
    text = unflow(text);
    text = unmark(text, citationMap);
    return text;
  }

  /**
   * Eliminates whitespace and newlines to unflow the given text.
   */
  private static List<String> unflow(List<String> text) {
    boolean inCodeBlock = false;
    List<String> unflowed = new ArrayList<String>(text.size());
    for (int lineNum = 0; lineNum < text.size();) {
      // Read until we absolutely need a new line.
      String line = trimTrailingWhitespace(text.get(lineNum++));
      if (line.length() > 0 && !isSectionLine(line) && !isCodeBlockFence(line)
          && !inCodeBlock) {
        while (lineNum < text.size()) {
          String nextLine = text.get(lineNum).trim();

          // Section lines ("----", "====") go on their own line.
          if (isSectionLine(nextLine)) break;

          // Code block fences ("~~~") go on their own line.
          if (isCodeBlockFence(nextLine)) break;

          // Paragraph break.
          if (nextLine.length() == 0) break;

          // Bullet list.
          if (listBullet(nextLine).length() > 0) break;

          line += " " + nextLine;
          lineNum++;
        }
      }

      // Replace tabs with spaces.
      line = replaceTabs(line);
      unflowed.add(line);

      inCodeBlock ^= isCodeBlockFence(line);
    }

    return unflowed;
  }

  private static String trimTrailingWhitespace(String s) {
    return ("x" + s).trim().substring(1);
  }

  private static final Pattern TAB_PATTERN = Pattern.compile("\t");

  /**
   * Replaces tabs with spaces.
   */
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
      .compile("^(-+|=+)$");

  /**
   * Returns true iff given a string that, when trimmed, is entirely '='s or '-'s.
   */
  private static boolean isSectionLine(String s) {
    return SECTION_LINE_PATTERN.matcher(s.trim()).matches();
  }

  private static final Pattern LIST_BULLET_PATTERN = Pattern
      .compile("^(\\*|-|\\d+\\.|\\[\\d+\\]) ");

  /**
   * Returns the bullet portion of the line, if any.
   */
  private static String listBullet(String line) {
    Matcher matcher = LIST_BULLET_PATTERN.matcher(line);
    if (matcher.find())
      return matcher.group();
    else return "";
  }

  /**
   * Removes markdown annotations from the given text. Text is assumed to be
   * unflowed.
   */
  private static List<String> unmark(List<String> text,
      Map<String, Integer> citationMap) {
    List<String> unmarked = new ArrayList<String>(text.size());
    for (String line : text) {
      line = stripCitations(line, citationMap);
      line = stripLinks(line);
      line = stripHeaderLabels(line);
      line = stripCodeBlockFences(line);
      if (isInclude(line)) {
        unmarked.addAll(handleInclude(line));
      } else {
        unmarked.add(line);
      }
    }

    return unmarked;
  }

  /**
   * Does line-wrapping on unwrapped text.
   */
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

  /**
   * Does line-wrapping on a single line of text.
   */
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
    String listBullet = listBullet(line);
    String hangingIndent = "";
    for (int i = 0; i < listBullet.length(); i++)
      hangingIndent += " ";

    // Strip list bullet for now.
    line = line.substring(listBullet.length()).trim();

    List<String> flowed =
        flow(line, MAX_LINE_LENGTH - indentSize - listBullet.length());
    List<String> result = new ArrayList<String>(flowed.size());
    for (String s : flowed) {
      // Handle list bullet/indent.
      if (result.isEmpty()) {
        s = listBullet + s;
      } else {
        s = hangingIndent + s;
      }

      result.add(indent + s);
    }

    return result;
  }

  /**
   * Wraps a single line of text to a certain line length.
   */
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

  private static final Pattern INCLUDE_PATTERN = Pattern
      .compile("^#include\\s+(\\S+)((\\s+\\S+)*)");

  private static boolean isInclude(String line) {
    return INCLUDE_PATTERN.matcher(line).matches();
  }

  /**
   * Handles an "#include" directive.
   */
  private static List<String> handleInclude(String line) {
    Matcher m = INCLUDE_PATTERN.matcher(line);
    m.find();
    String fileName = m.group(1);
    Map<String, Integer> citationMap = makeCitationMap(m.group(2).trim());

    List<String> file = readFile(new File(fileName));

    return stripNoFlow(file, citationMap);
  }

  private static final Pattern CITATION_MAP_ENTRY_PATTERN = Pattern
      .compile("(\\d+)=(\\S+)");

  /**
   * Turns "1=key1 2=key2 3=key3" to a map (key1→1, key2→2, key3→3).
   */
  private static Map<String, Integer> makeCitationMap(String s) {
    Matcher m = CITATION_MAP_ENTRY_PATTERN.matcher(s);
    Map<String, Integer> result = new HashMap<String, Integer>();
    while (m.find()) {
      String key = m.group(2);
      int value = Integer.parseInt(m.group(1));
      result.put(key, value);
    }

    return result;
  }

  private static final Pattern CITE_PATTERN = Pattern
      .compile("\\s*@cite\\s+([^\\s.]+)");

  private static String stripCitations(String line,
      Map<String, Integer> citationMap) {
    Matcher m = CITE_PATTERN.matcher(line);
    StringBuffer sb = new StringBuffer();
    while (m.find()) {
      String citation = m.group(1);
      String replacement;
      if (!citationMap.containsKey(citation)) {
        replacement = "";
      } else {
        replacement = " [" + citationMap.get(citation) + "]";
      }

      m.appendReplacement(sb, replacement);
    }

    m.appendTail(sb);
    return sb.toString();
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

  private static final Pattern CODEBLOCK_FENCE_PATTERN = Pattern
      .compile("^~~~.*$");

  private static boolean isCodeBlockFence(String s) {
    return CODEBLOCK_FENCE_PATTERN.matcher(s).matches();
  }

  private static String stripCodeBlockFences(String line) {
    return regexpReplace(line, CODEBLOCK_FENCE_PATTERN, "");
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
