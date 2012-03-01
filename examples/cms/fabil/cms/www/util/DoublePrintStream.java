package cms.www.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;

/**
 * @author jfg32
 */
public class DoublePrintStream extends PrintStream {

    PrintStream two = null;
    
    /**
     * @param out
     */
    public DoublePrintStream(OutputStream out, PrintStream two) {
        super(out);
        this.two = two;
    }
    
    public String dateTime() {
        Timestamp stamp = new Timestamp(System.currentTimeMillis());
        return "[" + DateTimeUtil.DATE_TIME_NUMERIC.format(stamp) + "]   ";
    }
    /*
    public void println(String str) {
        super.println(dateTime() + str);
        two.println(str);
    }

    public void println() {
        super.println(dateTime());
        //two.println();
    }

    public void println(boolean b) {
        super.println(dateTime() + b);
        //two.println(b);
    }
    
    public void println(char c) {
        super.println(dateTime() + c);
        //two.println(c);
    }
    
    public void println(char[] cs) {
        super.println(dateTime() + String.valueOf(cs));
        //two.println(cs);
    }
    
    public void println(double d) {
        super.println(dateTime() + d);
        //two.println(d);
    }
    
    public void println(float f) {
        super.println(dateTime() + f);
        //two.println(f);
    }
    
    public void println(int i) {
        super.println(dateTime() + i);
        //two.println(i);
    }
    
    public void println(long l) {
        super.println(dateTime() + l);
        //two.println(l);
    }
    
    public void println(Object o) {
        super.println(dateTime() + o);
        //two.println(o);
    }
    */
    
    public void print(String str) {
        super.print(dateTime() + str);
        two.print(str);
    }

    public void print(boolean b) {
        super.print(dateTime() + b);
        two.print(b);
    }
    
    public void print(char c) {
        super.print(dateTime() + c);
        two.print(c);
    }
    
    public void print(char[] cs) {
        super.print(dateTime() + String.valueOf(cs));
        two.print(cs);
    }
    
    public void print(double d) {
        super.print(dateTime() + d);
        two.print(d);
    }
    
    public void print(float f) {
        super.print(dateTime() + f);
        two.print(f);
    }
    
    public void print(int i) {
        super.print(dateTime() + i);
        two.print(i);
    }
    
    public void print(long l) {
        super.print(dateTime() + l);
        two.print(l);
    }
    
    public void print(Object o) {
        super.print(dateTime() + o);
        two.print(o);
    }
    
    /*
    public void write(int b) {
        super.write(b);
        //two.write(b);
    }
    
    public void write(byte[] b) throws IOException {
        super.write(b);
        //two.write(b);
    }
    
    public void write(byte[] b, int off, int len) {
        super.write(b, off, len);
        //two.write(b, off, len);
    }
    
    */
}

