package tee;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A couple of classes for capturing I/O to a file.
 */
public class TeeStream {
  public static class Input extends InputStream {
    private final InputStream in;
    private final FileOutputStream fileOut;

    public Input(String filename, InputStream in) throws FileNotFoundException {
      this.in = in;
      this.fileOut = new FileOutputStream(filename);
    }

    @Override
    public int read() throws IOException {
      int result = in.read();
      if (result != -1) {
        fileOut.write(result);
        fileOut.flush();
      }
      return result;
    }

    @Override
    public int read(byte[] b) throws IOException {
      return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
      int result = in.read(b, off, len);
      if (result > 0) {
        fileOut.write(b, off, result);
        fileOut.flush();
      }
      return result;
    }

    @Override
    public int available() throws IOException {
      return in.available();
    }

    @Override
    public void close() throws IOException {
      fileOut.close();
      in.close();
    }

    @Override
    public boolean markSupported() {
      return false;
    }
  }

  public static class Output extends OutputStream {
    private final OutputStream out;
    private final FileOutputStream fileOut;

    public Output(String filename, OutputStream out)
        throws FileNotFoundException {
      this.out = out;
      this.fileOut = new FileOutputStream(filename);
    }

    @Override
    public void write(int b) throws IOException {
      fileOut.write(b);
      out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
      fileOut.write(b);
      out.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
      fileOut.write(b, off, len);
      out.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
      fileOut.flush();
      out.flush();
    }

    @Override
    public void close() throws IOException {
      fileOut.close();
      out.close();
    }
  }
}
