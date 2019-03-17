package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {

      String[] nextTab = {"",""};   // initialized 2 element array to return
      int flag = 0;                 // I use this flag to direct the chars to the first (0) or second (1) element of the array


      // if the line contains at least one of the line separators (or both)
      if (lines.contains("\r") || lines.contains("\n")) {

          char[] charTab = lines.toCharArray();
          for (int i = 0; i < charTab.length; i++) {

              // the chars are directed to the first element
              nextTab[flag] += charTab[i];

              if (charTab[i] == '\n' || (charTab[i] == '\r' && (i == charTab.length - 1 || charTab[i + 1] != '\n'))) {
                  // until we arrive to a line separator (or end of line)
                  flag = 1;
              }
          }

      // if there are not separators
      } else {
          nextTab[0] = "";
          nextTab[1] = lines;

      }
    return nextTab;
  }

}
