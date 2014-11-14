/* Java 1.4 scanner for JFlex.
 * Based on JLS, 2ed, Chapter 3.
 */

package codenormalizer;

import java_cup.runtime.Symbol;
import polyglot.lex.*;
import polyglot.util.Position;
import polyglot.util.ErrorQueue;
import polyglot.util.ErrorInfo;
import java.util.HashMap;
import java.math.BigInteger;


@SuppressWarnings("all")
%%

%public
%class Lexer
%type Token
%function nextToken

%unicode
%pack

%line
%column

%state STRING, CHARACTER, TRADITIONAL_COMMENT, END_OF_LINE_COMMENT

%{
    StringBuffer sb = new StringBuffer();
    String file;
    ErrorQueue eq;
    HashMap keywords;

    public Lexer(java.io.Reader reader, String filename, ErrorQueue eq) {
        this(reader);
        this.file = filename;
        this.eq = eq;
        this.keywords = new HashMap();
        init_keywords();
    }

    protected void init_keywords() {
	/* extension */
	keywords.put("Override",     new Integer(sym.OVERRIDE));
	keywords.put("SuppressWarnings", new Integer(sym.SUPPRESS_WARNINGS));
    }

    public String file() {
        return file;
    }

    private Position pos() {
        return new Position(null, file, yyline+1, yycolumn, yyline+1,
                            yycolumn + yytext().length());
    }

    private Position pos(int len) {
        return new Position(null, file, yyline+1, yycolumn-len-1, yyline+1,
                            yycolumn+1);
    }

    private Token key(int symbol) {
        return new Keyword(pos(), yytext(), symbol);
    }

    private Token op(int symbol) {
        return new Operator(pos(), yytext(), symbol);
    }

    private Token id() {
        return new Identifier(pos(), yytext(), sym.IDENTIFIER);
    }

    private Token int_lit(String s, int radix) {
        BigInteger x = new BigInteger(s, radix);
        boolean boundary = (radix == 10 && s.equals("2147483648"));
        int bits = radix == 10 ? 31 : 32;
        if (x.bitLength() > bits && ! boundary) {
            eq.enqueue(ErrorInfo.LEXICAL_ERROR, "Integer literal \"" +
                        yytext() + "\" out of range.", pos());
        }
        return new IntegerLiteral(pos(), x.intValue(),
                boundary ? sym.INTEGER_LITERAL_BD : sym.INTEGER_LITERAL);
    }

    private Token long_lit(String s, int radix) {
        BigInteger x = new BigInteger(s, radix);
        boolean boundary = (radix == 10 && s.equals("9223372036854775808"));
        int bits = radix == 10 ? 63 : 64;
        if (x.bitLength() > bits && ! boundary) {
            eq.enqueue(ErrorInfo.LEXICAL_ERROR, "Long literal \"" +
                        yytext() + "\" out of range.", pos());
        }
        return new LongLiteral(pos(), x.longValue(),
                boundary ? sym.LONG_LITERAL_BD : sym.LONG_LITERAL);
    }

    private Token float_lit(String s) {
        try {
            Float x = Float.valueOf(s);
	    boolean zero = true;
	    for (int i = 0; i < s.length(); i++) {
		if ('1' <= s.charAt(i) && s.charAt(i) <= '9') {
		    zero = false;
		    break;
		}
                if (s.charAt(i) == 'e' || s.charAt(i) == 'E') {
                    break; // 0e19 is still 0
                }
	    }
	    if (x.isInfinite() || x.isNaN() || (x.floatValue() == 0 && ! zero)) {
		eq.enqueue(ErrorInfo.LEXICAL_ERROR,
			   "Illegal float literal \"" + yytext() + "\"", pos());
	    }
            return new FloatLiteral(pos(), x.floatValue(), sym.FLOAT_LITERAL);
        }
        catch (NumberFormatException e) {
            eq.enqueue(ErrorInfo.LEXICAL_ERROR,
                       "Illegal float literal \"" + yytext() + "\"", pos());
            return new FloatLiteral(pos(), 0f, sym.FLOAT_LITERAL);
        }
    }

    private Token double_lit(String s) {
        try {
            Double x = Double.valueOf(s);
	    boolean zero = true;
	    for (int i = 0; i < s.length(); i++) {
		if ('1' <= s.charAt(i) && s.charAt(i) <= '9') {
		    zero = false;
		    break;
		}
                if (s.charAt(i) == 'e' || s.charAt(i) == 'E') {
                    break; // 0e19 is still 0
                }
	    }
	    if (x.isInfinite() || x.isNaN() || (x.doubleValue() == 0 && ! zero)) {
		eq.enqueue(ErrorInfo.LEXICAL_ERROR,
			   "Illegal double literal \"" + yytext() + "\"", pos());
	    }
            return new DoubleLiteral(pos(), x.doubleValue(), sym.DOUBLE_LITERAL);
        }
        catch (NumberFormatException e) {
            eq.enqueue(ErrorInfo.LEXICAL_ERROR,
                       "Illegal double literal \"" + yytext() + "\"", pos());
            return new DoubleLiteral(pos(), 0., sym.DOUBLE_LITERAL);
        }
    }

    private Token char_lit(String s) {
        if (s.length() == 1) {
            char x = s.charAt(0);
            return new CharacterLiteral(pos(), x, sym.CHARACTER_LITERAL);
        }
        else {
            eq.enqueue(ErrorInfo.LEXICAL_ERROR,
                       "Illegal character literal \'" + s + "\'", pos(s.length()));
            return new CharacterLiteral(pos(), '\0', sym.CHARACTER_LITERAL);
        }
    }

    private Token boolean_lit(boolean x) {
        return new BooleanLiteral(pos(), x, sym.BOOLEAN_LITERAL);
    }

    private Token null_lit() {
        return new NullLiteral(pos(), sym.NULL_LITERAL);
    }

    private Token string_lit() {
        return new StringLiteral(pos(sb.length()), sb.toString(),
                                 sym.STRING_LITERAL);
    }

    private String chop(int i, int j) {
        return yytext().substring(i,yylength()-j);
    }

    private String chop(int j) {
        return chop(0, j);
    }

    private String chop() {
        return chop(0, 1);
    }
%}

%eofval{
    return new EOF(pos(), sym.EOF);
%eofval}

/* From Chapter 3 of the JLS: */

/* 3.4 Line Terminators */
/* LineTerminator:
      the ASCII LF character, also known as "newline"
      the ASCII CR character, also known as "return"
      the ASCII CR character followed by the ASCII LF character
*/
LineTerminator = \n|\r|\r\n

/* 3.6 White Space */
/*
WhiteSpace:
    the ASCII SP character, also known as "space"
    the ASCII HT character, also known as "horizontal tab"
    the ASCII FF character, also known as "form feed"
    // LineTerminator
*/
WhiteSpace = [ \t\f]

/* 3.8 Identifiers */
Identifier = [:jletter:] [:jletterdigit:]*

/* 3.10.1 Integer Literals */
DecimalNumeral = 0 | [1-9][0-9]*
HexNumeral = 0 [xX] [0-9a-fA-F]+
OctalNumeral = 0 [0-7]+

/* 3.10.2 Floating-Point Literals */
FloatingPointLiteral = [0-9]+ "." [0-9]* {ExponentPart}?
                     | "." [0-9]+ {ExponentPart}?
                     | [0-9]+ {ExponentPart}

ExponentPart = [eE] {SignedInteger}
SignedInteger = [-+]? [0-9]+

/* 3.10.4 Character Literals */
OctalEscape = \\ [0-7]
            | \\ [0-7][0-7]
            | \\ [0-3][0-7][0-7]
UnicodeEscape = \\ u[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F]

%%

<YYINITIAL> {
    /* 3.7 Comments */
    "/*"    { yybegin(TRADITIONAL_COMMENT); }
    "//"    { yybegin(END_OF_LINE_COMMENT); }

    /* 3.10.4 Character Literals */
    \'      { yybegin(CHARACTER); sb.setLength(0); }

    /* 3.10.5 String Literals */
    \"      { yybegin(STRING); sb.setLength(0); }

    /* 3.10.3 Boolean Literals */
    "true"  { return boolean_lit(true);  }
    "false" { return boolean_lit(false); }

    /* 3.10.6 Null Literal */
    "null"  { return null_lit(); }

    /* 3.9 Keywords */
    /* 3.8 Identifiers */
    {Identifier}   { Integer i = (Integer) keywords.get(yytext());
                    if (i == null) return id();
                    else return key(i.intValue()); }

    /* 3.11 Separators */
    "@"    { return op(sym.AT);        }
    "("    { return op(sym.LPAREN);    }
    ")"    { return op(sym.RPAREN);    }
    "{"    { return op(sym.LBRACE);    }
    "}"    { return op(sym.RBRACE);    }
    "["    { return op(sym.LBRACK);    }
    "]"    { return op(sym.RBRACK);    }
    ";"    { return op(sym.SEMICOLON); }
    ","    { return op(sym.COMMA);     }
    "."    { return op(sym.DOT);       }

    /* 3.12 Operators */
    "="    { return op(sym.EQ);         }
    ">"    { return op(sym.GT);         }
    "<"    { return op(sym.LT);         }
    "!"    { return op(sym.NOT);        }
    "~"    { return op(sym.COMP);       }
    "?"    { return op(sym.QUESTION);   }
    ":"    { return op(sym.COLON);      }
    "=="   { return op(sym.EQEQ);       }
    "<="   { return op(sym.LTEQ);       }
    ">="   { return op(sym.GTEQ);       }
    "!="   { return op(sym.NOTEQ);      }
    "&&"   { return op(sym.ANDAND);     }
    "||"   { return op(sym.OROR);       }
    "++"   { return op(sym.PLUSPLUS);   }
    "--"   { return op(sym.MINUSMINUS); }
    "+"    { return op(sym.PLUS);       }
    "-"    { return op(sym.MINUS);      }
    "*"    { return op(sym.MULT);       }
    "/"    { return op(sym.DIV);        }
    "&"    { return op(sym.AND);        }
    "|"    { return op(sym.OR);         }
    "^"    { return op(sym.XOR);        }
    "%"    { return op(sym.MOD);        }
    "<<"   { return op(sym.LSHIFT);     }
    ">>"   { return op(sym.RSHIFT);     }
    ">>>"  { return op(sym.URSHIFT);    }
    "+="   { return op(sym.PLUSEQ);     }
    "-="   { return op(sym.MINUSEQ);    }
    "*="   { return op(sym.MULTEQ);     }
    "/="   { return op(sym.DIVEQ);      }
    "&="   { return op(sym.ANDEQ);      }
    "|="   { return op(sym.OREQ);       }
    "^="   { return op(sym.XOREQ);      }
    "%="   { return op(sym.MODEQ);      }
    "<<="  { return op(sym.LSHIFTEQ);   }
    ">>="  { return op(sym.RSHIFTEQ);   }
    ">>>=" { return op(sym.URSHIFTEQ);  }

    /* 3.10.1 Integer Literals */
    {OctalNumeral} [lL]          { return long_lit(chop(), 8); }
    {HexNumeral} [lL]            { return long_lit(chop(2,1), 16); }
    {DecimalNumeral} [lL]        { return long_lit(chop(), 10); }
    {OctalNumeral}               { return int_lit(yytext(), 8); }
    {HexNumeral}                 { return int_lit(chop(2,0), 16); }
    {DecimalNumeral}             { return int_lit(yytext(), 10); }

    /* 3.10.2 Floating-Point Literals */
    {FloatingPointLiteral} [fF]  { return float_lit(chop()); }
    {DecimalNumeral} [fF]        { return float_lit(chop()); }
    {FloatingPointLiteral} [dD]  { return double_lit(chop()); }
    {DecimalNumeral} [dD]        { return double_lit(chop()); }
    {FloatingPointLiteral}       { return double_lit(yytext()); }

    /* 3.6 White Space */
    {WhiteSpace}                 { /* ignore */ }

    /* Jif extensions */
    "\u2293" { return op(sym.MEET); }
    "\u2294" { return op(sym.JOIN); }
    "\u2190" { return op(sym.LEFTARROW); }
    "\u2192" { return op(sym.RIGHTARROW); }
    "\u22a4" { return op(sym.TOP);      }
    "\u22a5" { return op(sym.BOTTOM);   }    

    /* codenormalizer extensions */
    {LineTerminator}             { return op(sym.NEWLINE); }
}

<TRADITIONAL_COMMENT> {
    "*/"                         { yybegin(YYINITIAL); }
    [^]                          { /* ignore */ }
}

<END_OF_LINE_COMMENT> {
    {LineTerminator}             { yybegin(YYINITIAL);
				   return op(sym.NEWLINE); }
    .                            { /* ignore */ }
}

<CHARACTER> {
    /* End of the character literal */
    \'                           { yybegin(YYINITIAL);
                                   return char_lit(sb.toString()); }

    /* 3.10.6 Escape Sequences for Character and String Literals */
    "\\b"                        { sb.append('\b'); }
    "\\t"                        { sb.append('\t'); }
    "\\n"                        { sb.append('\n'); }
    "\\f"                        { sb.append('\f'); }
    "\\r"                        { sb.append('\r'); }
    "\\\""                       { sb.append('\"'); }
    "\\'"                        { sb.append('\''); }
    "\\\\"                       { sb.append('\\'); }
    {OctalEscape}                { try {
                                       int x = Integer.parseInt(chop(1,0), 8);
                                       sb.append((char) x);
                                   }
                                   catch (NumberFormatException e) {
                                       eq.enqueue(ErrorInfo.LEXICAL_ERROR,
                                                  "Illegal octal escape \""
                                                  + yytext() + "\"", pos());
                                   }
                                 }
    {UnicodeEscape}              { try {
                                       int x = Integer.parseInt(chop(2,0), 16);
				       sb.append((char) x);
                                   }
				   catch (NumberFormatException e) {
				       eq.enqueue(ErrorInfo.LEXICAL_ERROR,
				                  "Illegal unicode escape \""
						  + yytext() + "\"", pos());
				   }
                                 }

    /* Illegal escape character */
    \\.                          { eq.enqueue(ErrorInfo.LEXICAL_ERROR,
                                              "Illegal escape character \"" +
                                              yytext() + "\"", pos()); }

    /* Unclosed character literal */
    {LineTerminator}             { yybegin(YYINITIAL);
                                  eq.enqueue(ErrorInfo.LEXICAL_ERROR,
                                             "Unclosed character literal",
                                             pos(sb.length())); }

    /* Anything else is okay */
    [^\r\n\'\\]+                 { sb.append( yytext() ); }
}

<STRING> {
    /* End of string */
    \"                           { yybegin(YYINITIAL);
                                   return string_lit(); }

    /* 3.10.6 Escape Sequences for Character and String Literals */
    "\\b"                        { sb.append( '\b' ); }
    "\\t"                        { sb.append( '\t' ); }
    "\\n"                        { sb.append( '\n' ); }
    "\\f"                        { sb.append( '\f' ); }
    "\\r"                        { sb.append( '\r' ); }
    "\\\""                       { sb.append( '\"' ); }
    "\\'"                        { sb.append( '\'' ); }
    "\\\\"                       { sb.append( '\\' ); }
    {OctalEscape}                { try {
                                       int x = Integer.parseInt(chop(1,0), 8);
                                       sb.append((char) x);
                                   }
                                   catch (NumberFormatException e) {
                                       eq.enqueue(ErrorInfo.LEXICAL_ERROR,
                                                  "Illegal octal escape \""
                                                  + yytext() + "\"", pos());
                                   }
                                 }
    {UnicodeEscape}              { try {
                                       int x = Integer.parseInt(chop(2,0), 16);
				       sb.append((char) x);
                                   }
				   catch (NumberFormatException e) {
				       eq.enqueue(ErrorInfo.LEXICAL_ERROR,
				                  "Illegal unicode escape \""
						  + yytext() + "\"", pos());
				   }
                                 }

    /* Illegal escape character */
    \\.                          { eq.enqueue(ErrorInfo.LEXICAL_ERROR,
                                              "Illegal escape character \"" +
                                              yytext() + "\"", pos()); }

    /* Unclosed string literal */
    {LineTerminator}             { yybegin(YYINITIAL);
                                   eq.enqueue(ErrorInfo.LEXICAL_ERROR,
                                              "Unclosed string literal",
                                              pos(sb.length())); }

    /* Anything else is okay */
    [^\r\n\"\\]+                 { sb.append( yytext() ); }
}

/* Fallthrough case: anything not matched above is an error */
[^]                              { eq.enqueue(ErrorInfo.LEXICAL_ERROR,
                                              "Illegal character \"" +
                                              yytext() + "\"", pos()); }
