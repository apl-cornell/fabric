package sif.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import sif.html.*;

import fabric.lang.security.*;

/**
 * @author andru
 * 
 * A class for generating indented output. Has begin() and end() to indicate
 * structure. Does not try to optimize line breaking or anything fancy.
 */
public abstract class HTMLWriter  {
    /**
     * Indicates if the Java script for color coding of
     * output based on labels should be produced.
     */
    public final boolean labelColorCodings;
    /**
     * Indicates if the span elements for pop-up titles for
     * output based on labels should be produced.
     */
    public final boolean labelTitleCodings;
    
    
    protected final sif.servlet.CodeWriter cw;
    /**
     * State indicating if the HTML writer is currently
     * writing in verbose mode or not.
     */
    private boolean verbatim = false;
    /**
     * Indicates what the CSS class of the output currently
     * being produced is. 
     */
    private String currentClass = null;
    
    /**
     * Counter used to assign unique names to label by the color label coding.
     * The label names are then used by the generated java script for color coding
     */    
    private int labelNameCounter  = 0;
    
    /**
     * Maps from label names to confidentiality policies and integrity policies respectively.
     */
    private final Map<String, Policy> confLatticeMap; 
    private final Map<String, Policy> integLatticeMap;

    
    /**
     * Provides a stack describing the current level of the HTML, and whether
     * it is input or output. It is a stack of LevelInfo classes.
     */
    protected LevelStack levelStack = new LevelStack();
    
    public static final int DEFAULT_WIDTH = 80;
    
    public HTMLWriter(PrintWriter p, String colorCoding) {
        this(p, DEFAULT_WIDTH, colorCoding);
    }
    public HTMLWriter(PrintWriter p, int lineWidth, String colorCoding) {
        boolean color = colorCoding!=null && colorCoding.equals("true")?true:false;
        this.labelColorCodings = color;
        this.labelTitleCodings = color;

        cw = new SimplestCodeWriter(p, lineWidth);
        // cw = new SimpleCodeWriter(p, lineWidth);
        //cw = new OptimalCodeWriter(p, lineWidth);
        if (labelColorCodings) {
            confLatticeMap = new HashMap<String, Policy>();
            integLatticeMap = new HashMap<String, Policy>();
        }
        else {
            confLatticeMap = null;
            integLatticeMap = null;            
        }
    }
    
    private String nextUniqueLabelName() {
    	return "label"+(labelNameCounter++);
    }
           
    public void setLevelStack(LevelStack stack) {
        this.levelStack = stack;
    }
    /*
     * Should the node n be ignored in terms of outputting 
     * label information?
     * Since none of them have any content in themselves, it would seem okay to do so.
     */
    protected boolean ignoreNode(Node n) {
        return n instanceof Page || n instanceof Head || n instanceof Body ||
            n instanceof TRow || n instanceof TCell || 
            n instanceof NodeList || n instanceof Table || 
            n instanceof Form || n instanceof Div || n instanceof Span;
    }
    public void enterNode(Node parent, Node child) {
        if (Servlet.debug(3)) {
            Servlet.DEBUG.println("*** HTMLWriter: entering node " + child.getClass().getName());
        }
        if (child instanceof InputNode) {
            enterInputNode(parent, (InputNode)child);
        }
        else {
            // do we need to output a tag?
            boolean output = (labelTitleCodings || labelColorCodings) && !ignoreNode(child) && levelStack.needOutput(child.getL(), null);

            levelStack.push(child, child.getL(), null, output);
            if (output) {
            	Label outLevel = levelStack.outputLevel();
                openSpan("Output is " + labelToString(outLevel), outLevel);
            }
        }
    }
    public void leaveNode(Node parent, Node child) { 
        if (child instanceof InputNode) {
            leaveInputNode(parent, (InputNode)child);
        }
        else {
            LevelInfo li = levelStack.pop();
            if (li.openedTag) {
                closeSpan();
            }
        }
    }
    protected void enterInputNode(Node parent, InputNode n) {
	// Ignore off-site inputs.
	if (n.input == null) return;

        Label l = n.input.inputLbl;
        addInput(n.input);
        // do we need to output a tag?
        boolean output = (labelTitleCodings || labelColorCodings) && !ignoreNode(n) && levelStack.needOutput(n.getL(), l);
        
        levelStack.push(n, n.getL(), l, output);
        
        if (output) {
        	Label outLevel = levelStack.outputLevel();
            openSpan("Input will be " + labelToString(l) + 
                     " :: Output is " + labelToString(outLevel), outLevel);
        }
    }
    protected void leaveInputNode(Node parent, InputNode n) { 
	// Ignore off-site inputs.
	if (n.input == null) return;

        LevelInfo li = levelStack.pop();
        if (li.openedTag) {
            closeSpan();
        }
    }
    
    protected void openSpan(String title, Label outLevel) {
        if (!labelTitleCodings && !labelColorCodings) return;
        
        begin(2);
        cw.write("<span");
        if (labelTitleCodings) {
            cw.write(" title=");
            printq(title);
        }
        
        if (labelColorCodings) {
            String name = nextUniqueLabelName();
            cw.write(" conf="+name);

            // possible optimization - have a single copy for equivalent policies
            // right now outLevel.confPolicy returns a new object for every node
            // in the HTML tree
            confLatticeMap.put(name, outLevel.confPolicy());
            name = nextUniqueLabelName();
            cw.write(" integ=" + name);
            integLatticeMap.put(name, outLevel.integPolicy());
        }
        cw.write(">");
        unifiedBreak(1);
    }
    protected void closeSpan() {
        if (!labelTitleCodings && !labelColorCodings) return;

        end();
        unifiedBreak(1);
        cw.write("</span>");
    }
    
    public void finishBody() {
        if (labelColorCodings) {
            colorCoding(confLatticeMap, "conf");
            colorCoding(integLatticeMap, "integ");
        }
    }
    
    // Takes a lattice and generate names for the elements
    private void colorCoding(Map<String, Policy> labelLattice, String uniqPrefix) {
    	// called after the entire html is written and just before the <body> tag is closed
    	// first construct the confidentiality lattice
    	Map<Policy, Policy> labelToActual = new HashMap<Policy, Policy>();
    	Vector<Integer> bottoms = new Vector<Integer>();
    	Policy[] labels;
    	Policy[] confList;
    	if (uniqPrefix.equals("conf")) {
    		labels = labelLattice.values().toArray(new ConfPolicy[0]); 
    		confList = new ConfPolicy[labels.length];
    	} else {
    		labels = labelLattice.values().toArray(new IntegPolicy[0]); 
    		confList = new IntegPolicy[labels.length];    		
    	}
    	int numConf = 0;
    	for (int i = 0; i < labels.length; i++) {
    		boolean isNew = true;
    		for (int j = 0; j < i; j++) {
    			if (LabelUtil._Impl.relabelsTo(labels[i], labels[j]) && LabelUtil._Impl.relabelsTo(labels[j], labels[i])) {
    				isNew = false;
    				labelToActual.put(labels[i], labels[j]);
    				break;
    			}
    		}
    		if (isNew) {
    			confList[numConf++] = labels[i];
    			labelToActual.put(labels[i], labels[i]);
    		}
    	}
        
    	int[][] confTable = new int[numConf][numConf];
    	
    	// by default, the entries of confTable are 0.
        // initialize the table with 0s
    	// 0 : unknown if relation exists; 1 : less than, maybe immediately; 2 : less than with at least distance 2
    	// -1 : more than, immediately; -2 : more than with distance at least 2
//    	for (int i = 0; i < numConf; i++) {
//    		for (int j = 0; j < numConf; j++) {
//    			confTable[i][j] = 0;
//    		}
//    	}
    	
    	// fill up the table by making as few queries as possible
    	for (int i = 0; i < numConf; i++) {
    		for (int j = 0; j < numConf; j++) {
    			if (i != j) {
    				if (confTable[i][j] == 0 && LabelUtil._Impl.relabelsTo(confList[i], confList[j])) {
    					confTable[i][j] = 1;
    					confTable[j][i] = -1;
    					for (int k = 0; k < numConf; k++) {
    						if (confTable[j][k] > 0) {
    							confTable[i][k] = 2;
    							confTable[k][i] = -2;
    						}
    					}
    				}
    			}
    		}
    	}
    	
    	// now convert as many 1s to 2s as possible
    	// also, try to figure out all the bottoms of all chains
    	for (int i = 0; i < numConf; i++) {
			boolean isBottom = true;
    		for (int j = 0; j < numConf; j++) {
    			if (i != j) {
    				if (confTable[i][j] < 0) {
    					isBottom = false;
    				}
    				if (confTable[i][j] > 0) {
    					for (int k = 0; k < numConf; k++) {
    						if (confTable[j][k] > 0) {
    							confTable[i][k] = 2;
    							confTable[k][i] = -2;
    						}
    					}    					
    				}
    			}
    		}
    		if (isBottom) {
    			bottoms.add(Integer.valueOf(i));
    		}
    	}
    	
//		Output the confList and confTable for debugging
//    	for (int i = 0; i < numConf; i++) {
//    		System.out.print(confList[i]+", ");
//    	}
//    	System.out.println();
//    	for (int i = 0; i < numConf; i++) {
//    		for (int j = 0; j < numConf; j++) {
//    			System.out.print(confTable[i][j]+"  ");
//    		}
//    		System.out.println();
//    	}
//    	for (int i = 0; i < bottoms.size(); i++) {
//    		System.out.print(bottoms.get(i) + ", ");
//    	}
//    	System.out.println();
    	
    	// map of labels to names, with each name being a set of integers
    	Map<Policy, Set<Integer>> namesMap = new HashMap<Policy, Set<Integer>>();
    	Map<Policy, String> arrayObjectMap = new HashMap<Policy, String>();
    	
    	// now give structured names to each label indicating its position in the lattice
    	// first associate each label with a set of integers that represent the name
    	int uniqCounter = 0;
    	for (int i = 0; i < bottoms.size(); i++) {
    		int bot = bottoms.get(i).intValue();
    		Set<Integer> set = new HashSet<Integer>();
    		if (bottoms.size() > 1) {
    			set.add(Integer.valueOf(uniqCounter++));
    		}
    		namesMap.put(confList[bot], set);
    	}
    	
    	int[] incoming = new int[numConf];
    	int[] outgoing = new int[numConf];
    	for (int i = 0; i < numConf; i++) {
    		for (int j = 0; j < numConf; j++) {
    			if (confTable[i][j] == 1) {
    				incoming[j]++;
    				outgoing[i]++;
    			}
    		}
    	}
    	
    	while(!bottoms.isEmpty()) {
    		int bot = bottoms.remove(0).intValue();
    		for (int j = 0; j < numConf; j++) {
    			if (confTable[bot][j] == 1) {
    					Set<Integer> setPrime = namesMap.get(confList[j]);
    					if (setPrime == null) {
    						setPrime = new HashSet<Integer>();
    						namesMap.put(confList[j], setPrime);
    					}
    					setPrime.addAll((HashSet<Integer>)namesMap.get(confList[bot]));
    					if (outgoing[bot] > 1 || incoming[j] == 1) {
    						setPrime.add(Integer.valueOf(uniqCounter++));
    					}
    					bottoms.add(Integer.valueOf(j));
    			}
    		}
    	}

    	//System.out.println(namesMap);
    	
    	
    	cw.write("<SCRIPT>\n");
    	String legMapVar = uniqPrefix + "legMap";
    	String arrayVar = uniqPrefix + "Array";
    	cw.write("var " + legMapVar + " = new Object;\n");
    	cw.write("var " + arrayVar + " = new Array("+numConf+");\n");
    	int maxLength = 0;
    	int lblCounter = 0;
    	for (int i = 0; i < numConf; i++) {
    		Set colSet = namesMap.get(confList[i]);
    		if (colSet.size() > maxLength) {
    			maxLength = colSet.size();
    		}
    		String newManVar = uniqPrefix + "blah" + (lblCounter++);
    		cw.write("var " + newManVar + " = new Array("+ colSet.size() +");\n");
    		arrayObjectMap.put(confList[i], newManVar);
    		cw.write(legMapVar + "[" + i + "] = \"" + confList[i].toString()+"\";\n");
    		cw.write(arrayVar + "["+i+"] = " + newManVar + ";\n");
    		Iterator it = colSet.iterator();
    		int index = 0;
    		while(it.hasNext()) {
    			int num = ((Integer)it.next()).intValue();
        		cw.write(newManVar + "[" + (index++) + "] = " + num + ";\n");
    		}    		
    	}
    	String mapVar = uniqPrefix + "map";
    	
    	cw.write("var " + mapVar + " = new Object;\n");
    	Iterator<String> keys = labelLattice.keySet().iterator();
    	while(keys.hasNext()) {
    		Object key = keys.next();
    		Object label = labelLattice.get(key);
    		Object actualLabel = labelToActual.get(label);
    		cw.write(mapVar + "[\""+ key.toString() +"\"] = " + arrayObjectMap.get(actualLabel) + ";\n");
    	}
    	String lengthVar = uniqPrefix + "maxLength";
    	String numLabelsVar = uniqPrefix + "numLabels";
    	cw.write("var " + lengthVar + " = " + maxLength + ";\n");
    	cw.write("var " + numLabelsVar + " = " + numConf + ";\n");
    	cw.write("</SCRIPT>");
    }

    public void print(String s) {
        // XXX Just write the string directly for the moment.
        // XXX When we get around to caring about the output formatting,
        // the code below can be uncommented.
        cw.write(s);
//        if (verbatim) {
//            cw.write(s);
//        } else {
//            int lastWhite = -1;
//            int length = s.length();
//            for (int i = 0; i < length; i++) {
//                char c = s.charAt(i);
//                if (Character.isWhitespace(c)) {
//                    cw.write(s.substring(lastWhite+1, i));
//                    while (i < length-1 && Character.isWhitespace(s.charAt(i+1)))
//                        i++;
//                    allowBreak(2, 5*getLevel()+1, " ");
//                    // character in location i is whitespace
//                    lastWhite = i;
//                }
//            }
//            if (lastWhite < length-1) {
//                cw.write(s.substring(lastWhite+1));
//            }
//        }
    }
    public void allowBreak(int n, int level, String alt) {
        cw.allowBreak(n, level, alt, alt.length());
    }
    public void unifiedBreak(int level) {
        cw.unifiedBreak(0, level, "", 0);
    }
    public void print(int i) {
        print(Integer.toString(i));
    }
    /* Output s verbatim with quotes around it. */
    public void printq(String s) {
        boolean tmp = verbatim;
        verbatim = true;
        print("\"");
        escape(s, false);
        print("\"");
        verbatim = tmp;
    }
    public void printq(int i) {
        printq(Integer.toString(i));
    }
    public void breakLine() {
        cw.allowBreak(0, 0, "", 0);
    }    
    public void breakLine(int n) {
        cw.allowBreak(n, 0, "", 0);
    }    
    
    public void begin() {
        cw.begin(0);
    }
    public void begin(int n) {
        cw.begin(n);
    }
    
    public void end() {
        cw.end();
    }
    
    public void noindent(boolean b) {
        verbatim = b;
    }
    
    public void flush() throws IOException {
        try {
            cw.flush();
        }
        catch (IOException e) { e.printStackTrace(Servlet.DEBUG); throw e; }
    }

    public void close() throws IOException {
        flush();
    }
    
    /** Escape HTML special characters in s and
     * print the result. */
    
    private static final char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public void escape(String s) {
        escape(s, true);
    }
    public void escape(String s, boolean insertBreaks) {
        if (s == null) return;
        int breakCount = 0;
        int lastOut = -1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
            case '&':
                escapeDump(lastOut, i, s);
                print("&amp;");
                breakCount+=5;
                lastOut = i;
                break;					
            case '<':
                escapeDump(lastOut, i, s);
                print("&lt;");
                breakCount+=4;
                lastOut = i;
                break;
            case '>':
                escapeDump(lastOut, i, s);
                print("&gt;");
                breakCount+=4;
                lastOut = i;
                break;
            case ' ':
                if (verbatim || !insertBreaks || breakCount <= 30) {
                    breakCount+=1;
                } else {
                    escapeDump(lastOut, i, s);
                    cw.allowBreak(2, 1, " ", 1);
                    lastOut = i;
                    breakCount = 0;
                }
                break;
            default:
                int code = c;
            	if (code >= 0x21 && code <= 0x7E) {
                    breakCount+=1;
            	} else {
                    escapeDump(lastOut, i, s);
            	    cw.write("&#" + code + ";");
                    lastOut = i;
                    breakCount+=7;
            	}
            break;
            }		
        }
        escapeDump(lastOut, s.length(), s);
    }
    private void escapeDump(int lastOut, int i, String s) {
        if (lastOut == i-1) return;
        cw.write(s.substring(lastOut+1, i));
        
    }
    /** Escape HTTP-special characters in s and
     * print the result.
     * @author andru
     * @param s the string to escape
     */
    public static String escape_URI(String s) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
            case '"': buf.append("%22"); break; // otherwise, messes up HTML			
            case ' ': buf.append("%20"); break; // may help pasting
            case '%': buf.append("%25"); break;
            default:
                int code = c;
                if (code >= 0x21 && code <= 0x7E) {
                    buf.append(c);		    
                } else {
                    // technically, codes 00-1f are not allowed in a URI, but they
                    // are escaped here anyway.
                    buf.append("%");
                    buf.append(hex[c / 16]);
                    buf.append(hex[c % 16]);
                }
                break;
            }		
        }
        return buf.toString();	
    }
    
    public boolean produceLabelColorCodings() {
        return labelColorCodings;
    }
    
    public String currentClass() {
        return currentClass;
    }
    /**
     * @param class_
     */
    public void setClass(String class_) {
        currentClass = class_;
    }
        
    protected String labelToString(Label inputLbl) {
        if (inputLbl == null) return "<null label>";
//        if (LabelUtil._Impl.isReadableBy(inputLbl, PrincipalUtil.nullPrincipal())) {
//            return "public information";
//        }
        return inputLbl.toString();
    }

    private static class LevelInfo {
        LevelInfo(Node n, 
                  Label currOutputLvl, 
                  Label currInputLvl, 
                  boolean openedTag) {
            this.n = n;
            this.currOutputLvl = currOutputLvl;
            this.currInputLvl = currInputLvl;
            this.openedTag = openedTag;
        }
        final Node n;
        final Label currOutputLvl; // what the current output label being displayed is (null if none)
        final Label currInputLvl;  // what the current input label being displayed is (null if none)      
        final boolean openedTag;   // was a tag opened when this LevelInfo was pushed?
        public String toString() {
            return "[currOut="+LabelUtil._Impl.toString(currOutputLvl)+
                      "; currInp="+LabelUtil._Impl.toString(currInputLvl)+
                      "; opened="+openedTag+
                      "; "+n+"]";
        }
    }

    public class LevelStack {
        private final List<LevelInfo> stack = new ArrayList<LevelInfo>(30);
        
        public LevelStack() {
        }
        private LevelStack(LevelStack ls) {
            this.stack.addAll(ls.stack);
        }
        private void push(Node n, Label outputNodeLevel, 
                                  Label inputLevel, 
                                  boolean openedTag) {
            Label currOutputLevel = null;
            Label currInputLevel = null;
            
            if (!stack.isEmpty()) {
                LevelInfo li = stack.get(stack.size()-1);
                currOutputLevel = li.currOutputLvl;
                currInputLevel = li.currInputLvl;
            }
            
            if (openedTag) {
                currOutputLevel = outputNodeLevel;
                currInputLevel = inputLevel;
            }
            LevelInfo li = new LevelInfo(n,
                                         currOutputLevel, 
                                         currInputLevel, 
                                         openedTag);
            if (Servlet.debug(2)) {
                Servlet.DEBUG.println("*** HTMLWriter: pushing " + li);
            }
            stack.add(stack.size(), li);
        }
        private LevelInfo pop() {
            LevelInfo li = stack.remove(stack.size()-1); 
            if (Servlet.debug(2)) {
                Servlet.DEBUG.println("*** HTMLWriter: popping " + li);
            }
            return li;
            
        }

        private boolean needOutput(Label outputNodeLevel, 
                                   Label inputLevel) {
            if (stack.isEmpty()) return true;
            
            LevelInfo li = stack.get(stack.size()-1);
            
            return !(LabelUtil._Impl.equivalentTo(inputLevel, li.currInputLvl) && 
                    LabelUtil._Impl.equivalentTo(outputNodeLevel, li.currOutputLvl));
        }
        private Label outputLevel() {
            LevelInfo li = stack.get(stack.size()-1);
            return li.currOutputLvl;
        }
        public LevelStack deepClone() {
            return new LevelStack(this);
        }
    }
    
    public abstract void addAction(Action action, Node n);
    protected abstract void addInput(Input n);
    public abstract void printServletURL();
    public abstract void printActionName(Action action, Node n);
    public abstract void printActionURL(Action action, Node n);
    public abstract void printHyperlinkRequestURL(Action action, HyperlinkRequest n);
    public LevelStack currentLevelStack() {
        return levelStack.deepClone();
    }
    
}
 
