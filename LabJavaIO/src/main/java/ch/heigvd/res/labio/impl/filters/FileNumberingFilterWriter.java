package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int lines;   // used to print the line number
  boolean firstFlag;   // as I used the char version of the write to do all the work I need a flag to
                       // know when I first used the write for the current line
  boolean carriageFlag;// same as before, I use this flag to check what follows a /r and act in consequence

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    lines = 1;
    firstFlag = true;
    carriageFlag = false;

  }

  @Override
  public void write(String str, int off, int len) throws IOException {

    char[] strChar = str.toCharArray();
    write(strChar, off, len);

  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {

    for (int i = off; i < off + len; i++ ){
      write(cbuf[i]);

    }
  }

  @Override
  public void write(int c) throws IOException {
    // if first execution
    if (firstFlag && lines == 1) {
      out.write(lines + "\t");
      firstFlag = false;
    }


    // for every other execution, check the char to seek for special char like \n, \r or a sequence
    // of them (with the help of carriageFlag)
    if (c == '\n') {

      if (carriageFlag) {
        out.write("\r\n" + ++lines + "\t");
        carriageFlag = false;
      } else {
        out.write("\n" + ++lines + "\t");
      }

    } else if (c == '\r') {

      if (carriageFlag) {
        carriageFlag = false;
        out.write("\r" + ++lines + "\t");
      } else {
        carriageFlag = true;
      }

    } else {

      if (carriageFlag) {
        out.write("\r" + ++lines + "\t" + (char) c);
        carriageFlag = false;
      } else {
        out.write((char) c);
      }


    }
  }
}
