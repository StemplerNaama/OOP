package arkanoid;

/**
 * this is the colors parser class.
 */
public class ColorsParser {
    /**
     * constructor.
     */
    public ColorsParser() {
    }
   // parse color definition and return the specified color.
   /**
    * parse color definition and return the specified color.
 * @param s - the color in a string form
 * @return - the specified color
 */
public java.awt.Color colorFromString(String s) {
       java.awt.Color color;
       if (s.startsWith("RGB")) {
           String rgb = s.substring(4, s.length() - 1);
           String[] lineSplit = rgb.split(",");
           int r = Integer.parseInt(lineSplit[0]);
           int g = Integer.parseInt(lineSplit[1]);
           int b = Integer.parseInt(lineSplit[2]);
           color = new java.awt.Color(r, g, b);
       } else {
           switch (s) {
       case "blue":
           color = java.awt.Color.blue;
           break;
       case "cyan":
           color = java.awt.Color.cyan;
           break;
       case "gray":
           color = java.awt.Color.gray;
           break;
       case "lightGray":
           color = java.awt.Color.lightGray;
           break;
       case "green":
           color = java.awt.Color.green;
           break;
       case "orange":
           color = java.awt.Color.orange;
           break;
       case "pink":
           color = java.awt.Color.pink;
           break;
       case "red":
           color = java.awt.Color.red;
           break;
       case "white":
           color = java.awt.Color.white;
           break;
       case "yellow":
           color = java.awt.Color.yellow;
           break;
           default:
               color = java.awt.Color.black;
       }
       }
       return color;
   }
}