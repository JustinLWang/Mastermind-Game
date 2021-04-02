import javalib.funworld.*;
// import javalib.worldcanvas.WorldCanvas;
import javalib.worldimages.*;
import tester.*;
import java.awt.Color;
import java.util.Random;

// represents a list of Colors
interface ILoColor {

  // generates the answer
  ILoColor generateAnswer(boolean duplicates, int length);

  // generates answer with duplicates
  ILoColor generateAnswerDupe(int ansLength);

  // generates answer without duplicates
  ILoColor generateAnswerNoDupe(int ansLength);

  // determines length
  int length();

  // removes first Color from list
  ILoColor remove();

  // determiens number of exact matches
  int numCorrectAll(ILoColor answer);

  // helper for numCorrectAll()
  int numCorrectAllHelp(Color given, ILoColor guess);

  // makes a row in the world
  WorldImage makeRow();

  // gets Color from list depending on given int
  Color getNthValue(int n);

  // reverses a list of Colors
  ILoColor reverse();

  // reverse() helper
  ILoColor reverseHelper(ILoColor acc);

  // determines if a list contains the given Color
  boolean contains(Color color);

  // determines inexact matches between this list and that list
  int totalInExactMatches(ILoColor that);

  // counts the total number of matches between this list and that list
  int countTotalMatches(ILoColor that);

  // helper for countTotalMatches()
  int countTotalMatchesHelp(Color c, ILoColor that);

  // removes the first Color from a guess list
  public ILoColor removeFromGuess(Color c); 

  // determines if a list has the given color c
  public boolean hasColor(Color c);




}

// represents a list of Guess
interface ILoGuess {

  // draws the guesses in the world
  WorldImage drawGuesses();

  // determines the length of the ILoGuess
  int length();
}

// represents the Utils class
class Utils {

  /*fields: 
   *    
   *Methods 
   *    this.checkConstraint(int value, int min, String msg) ... int
   *    this.checkLoColors(ILoColor colors, int min, String msg) ... ILoColor
   *    this.checkDuplicates(boolean duplicates, ILoColor colors, ...
   *    int length, string msg) ... boolean
   *Methods for fields:
   */

  // checks if given value is greater than min, if not throws exception
  int checkConstraint(int value, int min, String msg) {
    if (value > min) {
      return value;
    }
    else {
      throw new IllegalArgumentException(msg);
    }
  }

  // checks if given list of color's length is greater than min, if not throws exception
  ILoColor checkLoColors(ILoColor colors, int min, String msg) {
    /*EVEYTHING ABOVE AND ...
     * fields: 
     *Methods 
     *   colors.generateAnswer() ... ILoColor
     *   colors.generateAnswerDupe() ... ILoColor
     *   colors.generateAnswerNoDupe() ... ILoColor
     *   colors.length() ... iunt
     *   colors.remove() ... int
     *   colors.numCorectAll ... int
     *   colors.numCorrectAllHelp() ... int
     *   colors.makeRow() ... WorldImage
     *   colors.getNthValue() ... Color
     *   colors.reverse() ... ILoColor
     *   colors.reverseHelper() ... ILoColor
     *   colors.contains(_ ... boolean
     *   colors.totalInExactMatches() ... int
     *   colors.countTotalMatches() ... int
     *   colors.countTotalMatchesHelp() ... int
     *   colors.removeFromGuess(Color c) ... ILoCOlor
     *   colors.hasColor() ... boolean
     *   colors.makeRow ... WorldImage

     *Methods for fields:
     *   
     */
    if (colors.length() > min) {
      return colors;
    } else {
      throw new IllegalArgumentException(msg);
    }
  }

  // if there are no duplicates, checks if length is greater than the length of the given list
  boolean checkDuplicates(boolean duplicates, ILoColor colors, int length, String msg) {
    /*EVEYTHING ABOVE AND ...
     * fields: 
     *Methods 
     *   colors.generateAnswer() ... ILoColor
     *   colors.generateAnswerDupe() ... ILoColor
     *   colors.generateAnswerNoDupe() ... ILoColor
     *   colors.length() ... iunt
     *   colors.remove() ... int
     *   colors.numCorectAll ... int
     *   colors.numCorrectAllHelp() ... int
     *   colors.makeRow() ... WorldImage
     *   colors.getNthValue() ... Color
     *   colors.reverse() ... ILoColor
     *   colors.reverseHelper() ... ILoColor
     *   colors.contains(_ ... boolean
     *   colors.totalInExactMatches() ... int
     *   colors.countTotalMatches() ... int
     *   colors.countTotalMatchesHelp() ... int
     *   colors.removeFromGuess(Color c) ... ILoCOlor
     *   colors.hasColor() ... boolean
     *   colors.makeRow ... WorldImage

     *Methods for fields:
     *   
     */
    if (!duplicates && (length > colors.length())) {
      throw new IllegalArgumentException(msg);
    } else {
      return duplicates;
    }
  }

}

// represents a MastermindGame
class MastermindGame extends World {
  boolean duplicates;
  int length;
  int guesses;
  ILoColor colors;
  ILoColor currGuess;
  ILoGuess loGuesses;
  ILoColor answer;
  Random rand;
  ILoColor allColors =  new ConsLoColor(Color.red, new ConsLoColor(Color.green, 
      new ConsLoColor(Color.blue, new ConsLoColor(Color.cyan, 
          new ConsLoColor(Color.orange, new ConsLoColor(Color.pink, 
              new ConsLoColor(Color.gray, new ConsLoColor(Color.MAGENTA, 
                  new ConsLoColor(Color.YELLOW, new MtLoColor())))))))));

  // main constructor
  MastermindGame(boolean duplicates, int length, int guesses, ILoColor colors, ILoColor currGuess, 
      ILoGuess loGuesses, ILoColor answer) {
    this.duplicates = new Utils().checkDuplicates(
        duplicates, colors, length, "Duplicates Disallowed and length of "
            + "sequence is greater than the length of colors");
    this.length = new Utils().checkConstraint(
        length, 0, "Invalid length: " + Integer.toString(length));
    this.guesses = new Utils().checkConstraint(
        guesses, 0, "Invalid guesses: " + Integer.toString(guesses));
    this.colors = new Utils().checkLoColors(
        colors, 0, "Invalid length of list of colors: " + Integer.toString(colors.length()));
    this.currGuess = currGuess;
    this.loGuesses = loGuesses;
    this.answer = answer;
  }
  // constructor with a Random component

  MastermindGame(boolean duplicates, int length, int guesses, ILoColor colors, Random rand) {
    this.duplicates = new Utils().checkDuplicates(
        duplicates, colors, length, "Duplicates Disallowed and length of "
            + "sequence is greater than the length of colors");
    this.length = new Utils().checkConstraint(
        length, 0, "Invalid length: " + Integer.toString(length));
    this.guesses = new Utils().checkConstraint(
        guesses, 0, "Invalid guesses: " + Integer.toString(guesses));
    this.colors = new Utils().checkLoColors(
        colors, 0, "Invalid length of list of colors: " + Integer.toString(colors.length()));
    this.currGuess = new MtLoColor();
    this.loGuesses = new MtLoGuess();
    this.answer = this.colors.generateAnswer(this.duplicates, this.length);
  }
  // convenience constructor

  MastermindGame(boolean duplicates, int length, int guesses, ILoColor colors) {
    this(duplicates, length, guesses, colors, new Random());
  }

  /*fields: 
   *   this.duplicates ... boolean
   *   this.length ... int
   *   this.guesses ... int
   *   this.colors ... ILoCOlor
   *   this.currGuess ... ILoColor
   *   this.loGuesses ... ILoGuess
   *   this.answer ... ILoColor 
   *   this.rand ... int
   *   this.allColors ... ILoColor
   *Methods 
   *    this.makeScene() ... WorldScene
   *    this.onKeyEvent(String key) ... World
   *    this.lastScene(String msg) ... WorldScene
   *    this.drawEmptiesRow(int n) ... WorldImage
   *    this.drawEmptiesAll(int n) ... WorldImage
   *Methods for fields:
   *   
   */

  // makes the scene for the game in the world scene
  public WorldScene makeScene() {
    int lineWidth = (int) (new BesideImage(
        new TextImage("0", 30, Color.BLACK).movePinhole(10, 0),
        new TextImage("0", 30, Color.BLACK)).movePinhole(-80, 0)).getWidth();
    int maxLength = Math.max(this.colors.length(), this.length);
    int width = (maxLength + 1) * 40 + 160;
    int height = (this.guesses + 2) * 40;
    return new WorldScene(width, height).placeImageXY(
        new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.BOTTOM,
            this.currGuess.reverse().makeRow(), 0, 40 * (this.loGuesses.length() + 1),
            new AboveAlignImage(AlignModeX.LEFT,
                new AboveAlignImage(AlignModeX.LEFT,
                    this.drawEmptiesAll(this.guesses + 2)),
                this.colors.makeRow())),
        20 * maxLength, 20 * (guesses + 2)).placeImageXY(
            new RectangleImage(40 * this.length, 40, OutlineMode.SOLID, Color.black), 
            this.length * 20, 20).placeImageXY(this.loGuesses.drawGuesses(), 
                this.length * 20 + lineWidth - 2, 
                height - (this.loGuesses.length() * 40 / 2) - 40);
  }




  // keyHandler -- Manages picking a number (1-9) deleting last guess and entering guess 
  public World onKeyEvent(String key) {

    if ("123456789".contains(key) && (Integer.valueOf(key) <= this.colors.length()) && 
        (this.currGuess.length() < this.length)) {
      if (this.duplicates) {
        return new MastermindGame(this.duplicates, this.length, this.guesses, this.colors, 
            new ConsLoColor(colors.getNthValue(Integer.valueOf(key) - 1), this.currGuess), 
            this.loGuesses, this.answer);
      } else if (!(this.duplicates) && !(this.currGuess.contains(
          this.colors.getNthValue(Integer.valueOf(key) - 1)))) {
        return new MastermindGame(this.duplicates, this.length, 
            this.guesses, this.colors, 
            new ConsLoColor(colors.getNthValue(Integer.valueOf(key) - 1), this.currGuess), 
            this.loGuesses, this.answer);
      } else {
        return this;
      }

    } else if (key.equals("backspace") && (this.currGuess.length() <= this.length)) {
      return new MastermindGame(this.duplicates, this.length, this.guesses, this.colors, 
          this.currGuess.remove(), this.loGuesses, this.answer);

    } else if (key.equals("enter") && (currGuess.length() == length)) {
      if (this.currGuess.reverse().numCorrectAll(this.answer) == this.answer.length()) {
        this.loGuesses = new ConsLoGuess(new Guess(this.currGuess.reverse(), 
            this.length, 0), this.loGuesses);
        this.currGuess = new MtLoColor();
        return this.endOfWorld("WIN");
      } else if (this.loGuesses.length() == this.guesses - 1) {
        this.loGuesses = new ConsLoGuess(new Guess(this.currGuess.reverse()), this.loGuesses);
        this.currGuess = new MtLoColor();
        return this.endOfWorld("LOSE");
      } else {
        return new MastermindGame(this.duplicates, this.length, this.guesses, this.colors, 
            new MtLoColor(), new ConsLoGuess(new Guess(this.currGuess.reverse(), 
                this.currGuess.reverse().numCorrectAll(this.answer), 
                this.currGuess.reverse().totalInExactMatches(this.answer)), 
                this.loGuesses), this.answer);
      }


    } else {
      return this;
    }
  }
  // displays the last scene when the game ends with the given message

  public WorldScene lastScene(String msg) {
    int lineWidth = (int) (new BesideImage(
        this.answer.makeRow(),
        new TextImage(msg, 40, Color.BLACK)).getWidth());
    WorldImage finalImage = new BesideImage(this.answer.makeRow().movePinhole(30, 0), 
        new TextImage(msg, 40, Color.BLACK));
    return this.makeScene().placeImageXY(finalImage, (this.length * 20 + lineWidth) / 2, 20);
  }

  // draws the empty rows that represent ILoGuesses
  WorldImage drawEmptiesRow(int n) {
    if (n == 1) {
      return new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK);
    } else {
      return new BesideImage(new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
          drawEmptiesRow(n - 1));
    }
  }
  // draws all the empty rows for guesses and stacks them

  WorldImage drawEmptiesAll(int n) {
    if (n == 1) {
      return new EmptyImage();
    } else {
      return new AboveAlignImage(AlignModeX.LEFT, this.drawEmptiesRow(this.length), 
          drawEmptiesAll(n - 1));
    }
  }


}

// represents a empty list of Colors
class MtLoColor implements ILoColor {
  /*fields: 
   *Methods 
   *   this.generateAnswer() ... ILoColor
   *   this.generateAnswerDupe() ... ILoColor
   *   this.generateAnswerNoDupe() ... ILoColor
   *   this.length() ... int
   *   this.remove() ... int
   *   this.numCorectAll ... int
   *   this.numCorrectAllHelp() ... int
   *   this.makeRow() ... WorldImage
   *   this.getNthValue() ... Color
   *   this.reverse() ... ILoColor
   *   this.reverseHelper() ... ILoColor
   *   this.contains(_ ... boolean
   *   this.totalInExactMatches() ... int
   *   this.countTotalMatches() ... int
   *   this.countTotalMatchesHelp() ... int
   *   this.removeFromGuess(Color c) ... ILoCOlor
   *   this.hasColor() ... boolean
   *   this.makeRow ... WorldImage
   *   

   *Methods for fields:
   *   

   */


  // determine length of an empty list
  public int length() {
    return 0;
  }

  // removes first Color from empty list
  public ILoColor remove() {
    return this;
  }

  // determines exact matches between empty list and given list(answer)
  public int numCorrectAll(ILoColor answer) {
    /*EVEYTHING ABOVE AND ...
     * fields: 
     *Methods 
     *   answer.generateAnswer() ... ILoColor
     *     answer.generateAnswerDupe() ... ILoColor
     *     answer.generateAnswerNoDupe() ... ILoColor
     *   answer.length() ... int
     *   answer.remove() ... int
     *   answer.numCorectAll ... int
     *   answer.numCorrectAllHelp() ... int
     *   answer.makeRow() ... WorldImage
     *   answer.getNthValue() ... Color
     *   answer.reverse() ... ILoColor
     *   answer.reverseHelper() ... ILoColor
     *   answer.contains(_ ... boolean
     *   answer.totalInExactMatches() ... int
     *   answer.countTotalMatches() ... int
     *   answer.countTotalMatchesHelp() ... int
     *   answer.removeFromGuess(Color c) ... ILoCOlor
     *   answer.hasColor() ... boolean
     *   answer.makeRow ... WorldImage

     *Methods for fields:
     *   
     */
    return 0;
  }

  // helper for numCorrectAll
  public int numCorrectAllHelp(Color given, ILoColor guess) {
    /*EVEYTHING ABOVE AND ...
     * fields: 
     *Methods 
     *   guess.generateAnswer() ... ILoColor
     *     guess.generateAnswerDupe() ... ILoColor
     *     guess.generateAnswerNoDupe() ... ILoColor
     *   guess.length() ... int
     *   guess.remove() ... int
     *   guess.numCorectAll ... int
     *   guess.numCorrectAllHelp() ... int
     *   guess.makeRow() ... WorldImage
     *   guess.getNthValue() ... Color
     *   guess.reverse() ... ILoColor
     *   guess.reverseHelper() ... ILoColor
     *   guess.contains(_ ... boolean
     *   guess.totalInExactMatches() ... int
     *   guess.countTotalMatches() ... int
     *   guess.countTotalMatchesHelp() ... int
     *   guess.removeFromGuess(Color c) ... ILoCOlor
     *   guess.hasColor() ... boolean
     *   guess.makeRow ... WorldImage

     *Methods for fields:
     *   
     */
    return 0;
  }

  // makes a row for the empty list (empty image)
  public WorldImage makeRow() {
    return new EmptyImage();
  }

  // produces the color(black) in the empty list, given the int n
  public Color getNthValue(int n) {
    return Color.black;
  }

  // reverses the empty list
  public ILoColor reverse() {
    return this;
  }

  // helper for reverse()
  public ILoColor reverseHelper(ILoColor acc) {
    /*EVEYTHING ABOVE AND ...
     * fields: 
     *Methods 
     *   acc.generateAnswer() ... ILoColor
     *     acc.generateAnswerDupe() ... ILoColor
     *     acc.generateAnswerNoDupe() ... ILoColor
     *   acc.length() ... int
     *   acc.remove() ... int
     *   acc.numCorectAll ... int
     *   acc.numCorrectAllHelp() ... int
     *   acc.makeRow() ... WorldImage
     *   acc.getNthValue() ... Color
     *   acc.reverse() ... ILoColor
     *   acc.reverseHelper() ... ILoColor
     *   acc.contains(_ ... boolean
     *   acc.totalInExactMatches() ... int
     *   acc.countTotalMatches() ... int
     *   acc.countTotalMatchesHelp() ... int
     *   acc.removeFromGuess(Color c) ... ILoCOlor
     *   acc.hasColor() ... boolean
     *   acc.makeRow ... WorldImage

     *Methods for fields:
     *   
     */
    return acc;
  }

  // determines if the empty list contains the given color
  public boolean contains(Color color) {
    return false;
  }

  // determines the inexact matches between the empty list and that list
  public int totalInExactMatches(ILoColor that) {
    /*EVEYTHING ABOVE AND ...
     * fields: 
     *Methods 
     *   that.generateAnswer() ... ILoColor
     *     that.generateAnswerDupe() ... ILoColor
     *     that.generateAnswerNoDupe() ... ILoColor
     *   that.length() ... int
     *   that.remove() ... int
     *   that.numCorectAll ... int
     *   that.numCorrectAllHelp() ... int
     *   that.makeRow() ... WorldImage
     *   that.getNthValue() ... Color
     *   that.reverseHelper() ... ILoColor
     *   that.contains(_ ... boolean
     *   that.totalInExactMatches() ... int
     *   that.countTotalMatches() ... int
     *   that.countTotalMatchesHelp() ... int
     *   that.removeFromGuess(Color c) ... ILoCOlor
     *   that.hasColor() ... boolean
     *   that.makeRow ... WorldImage

     *Methods for fields:
     *   
     */
    return 0;
  }

  // counts total matches between the empty list and that list
  public int countTotalMatches(ILoColor that) {
    /*EVEYTHING ABOVE AND ...
     * fields: 
     *Methods 
     *   that.generateAnswer() ... ILoColor
     *     that.generateAnswerDupe() ... ILoColor
     *     that.generateAnswerNoDupe() ... ILoColor
     *   that.length() ... int
     *   that.remove() ... int
     *   that.numCorectAll ... int
     *   that.numCorrectAllHelp() ... int
     *   that.makeRow() ... WorldImage
     *   that.getNthValue() ... Color
     *   that.reverseHelper() ... ILoColor
     *   that.contains(_ ... boolean
     *   that.totalInExactMatches() ... int
     *   that.countTotalMatches() ... int
     *   that.countTotalMatchesHelp() ... int
     *   that.removeFromGuess(Color c) ... ILoCOlor
     *   that.hasColor() ... boolean
     *   that.makeRow ... WorldImage

     *Methods for fields:
     *   
     */
    return 0;
  }

  // helper for countTotalMatches()
  public int countTotalMatchesHelp(Color c, ILoColor that) {
    /*EVEYTHING ABOVE AND ...
     * fields: 
     *Methods 
     *   that.generateAnswer() ... ILoColor
     *     that.generateAnswerDupe() ... ILoColor
     *     that.generateAnswerNoDupe() ... ILoColor
     *   that.length() ... int
     *   that.remove() ... int
     *   that.numCorectAll ... int
     *   that.numCorrectAllHelp() ... int
     *   that.makeRow() ... WorldImage
     *   that.getNthValue() ... Color
     *   that.reverseHelper() ... ILoColor
     *   that.contains(_ ... boolean
     *   that.totalInExactMatches() ... int
     *   that.countTotalMatches() ... int
     *   that.countTotalMatchesHelp() ... int
     *   that.removeFromGuess(Color c) ... ILoCOlor
     *   that.hasColor() ... boolean
     *   that.makeRow ... WorldImage

     *Methods for fields:
     *   
     */
    return  0;
  }


  // removes the first color c from the guess list
  public ILoColor removeFromGuess(Color c) {
    return this;
  }

  // determines if the empty list has the color c
  public boolean hasColor(Color c) {
    return false;
  }

  // generates the answer for the empty list
  public ILoColor generateAnswer(boolean duplicates, int length) {
    // TODO Auto-generated method stub
    return new MtLoColor();
  }

  // generates the answer with duplicates for the empty list
  public ILoColor generateAnswerDupe(int ansLength) {
    // TODO Auto-generated method stub
    return new MtLoColor();
  }

  // generates the answer without duplicates for the empty list
  public ILoColor generateAnswerNoDupe(int ansLength) {
    // TODO Auto-generated method stub
    return new MtLoColor();
  }



}

// represents the Cons class for ILoColor
class ConsLoColor implements ILoColor {
  Color first;
  ILoColor rest;

  ConsLoColor(Color first, ILoColor rest) {
    this.first = first;
    this.rest = rest;
  }

  /*fields: 
   *    this.first ... Color
   *    this.rest ... ILoColor
   *Methods 
   *   this.generateAnswer() ... ILoColor
   *   this.generateAnswerDupe() ... ILoColor
   *   this.generateAnswerNoDupe() ... ILoColor
   *   this.length() ... int
   *   this.remove() ... int
   *   this.numCorectAll ... int
   *   this.numCorrectAllHelp() ... int
   *   this.makeRow() ... WorldImage
   *   this.getNthValue() ... Color
   *   this.reverse() ... ILoColor
   *   this.reverseHelper() ... ILoColor
   *   this.contains(_ ... boolean
   *   this.totalInExactMatches() ... int
   *   this.countTotalMatches() ... int
   *   this.countTotalMatchesHelp() ... int
   *   this.removeFromGuess(Color c) ... ILoCOlor
   *   this.hasColor() ... boolean
   *   this.makeRow ... WorldImage

   *Methods for fields:
   *   this.rest.generateAnswer() ... ILoColor
   *   this.rest.generateAnswerDupe() ... ILoColor
   *   this.rest.generateAnswerNoDupe() ... ILoColor
   *   this.rest.length() ... int
   *   this.rest.remove() ... int
   *   this.rest.numCorectAll ... int
   *   this.rest.numCorrectAllHelp() ... int
   *   this.rest.makeRow() ... WorldImage
   *   this.rest.getNthValue() ... Color
   *   this.rest.reverse() ... ILoColor
   *   this.rest.everseHelper() ... ILoColor
   *   this.rest.contains(_ ... boolean
   *   this.rest.totalInExactMatches() ... int
   *   this.rest.countTotalMatches() ... int
   *   this.rest.countTotalMatchesHelp() ... int
   *   this.rest.removeFromGuess(Color c) ... ILoCOlor
   *   this.rest.hasColor() ... boolean
   *   this.rest.makeRow ... WorldImage

   */

  // determines the length for Cons list of Colors
  public int length() {
    return 1 + this.rest.length();
  }

  // removes the first color from a Cons list of Colors
  public ILoColor remove() {
    return this.rest;
  }

  // determines the number of exact matches between a Cons list Colors and the given answer list
  public int numCorrectAll(ILoColor answer) {
    /*EVEYTHING ABOVE AND ...
     * fields: 
     *Methods 
     *   answer.generateAnswer() ... ILoColor
     *     answer.generateAnswerDupe() ... ILoColor
     *     answer.generateAnswerNoDupe() ... ILoColor
     *   answer.length() ... int
     *   answer.remove() ... int
     *   answer.numCorectAll ... int
     *   answer.numCorrectAllHelp() ... int
     *   answer.makeRow() ... WorldImage
     *   answer.getNthValue() ... Color
     *   answer.reverse() ... ILoColor
     *   answer.reverseHelper() ... ILoColor
     *   answer.contains(_ ... boolean
     *   answer.totalInExactMatches() ... int
     *   answer.countTotalMatches() ... int
     *   answer.countTotalMatchesHelp() ... int
     *   answer.removeFromGuess(Color c) ... ILoCOlor
     *   answer.hasColor() ... boolean
     *   answer.makeRow ... WorldImage

     *Methods for fields:
     *   
     */
    return answer.numCorrectAllHelp(this.first, this.rest);
  }

  // helper for numCorrectAll()
  public int numCorrectAllHelp(Color given, ILoColor guess) {
    /*EVEYTHING ABOVE AND ...
     * fields: 
     *Methods 
     *   guess.generateAnswer() ... ILoColor
     *     guess.generateAnswerDupe() ... ILoColor
     *     guess.generateAnswerNoDupe() ... ILoColor
     *   guess.length() ... int
     *   guess.remove() ... int
     *   guess.numCorectAll ... int
     *   guess.numCorrectAllHelp() ... int
     *   guess.makeRow() ... WorldImage
     *   guess.getNthValue() ... Color
     *   guess.reverse() ... ILoColor
     *   guess.reverseHelper() ... ILoColor
     *   guess.contains(_ ... boolean
     *   guess.totalInExactMatches() ... int
     *   guess.countTotalMatches() ... int
     *   guess.countTotalMatchesHelp() ... int
     *   guess.removeFromGuess(Color c) ... ILoCOlor
     *   guess.hasColor() ... boolean
     *   guess.makeRow ... WorldImage

     *Methods for fields:
     *   
     */
    if (this.first.equals(given)) {
      return 1 + this.rest.numCorrectAll(guess);
    } else {
      return this.rest.numCorrectAll(guess);
    }
  }

  // makes a row for the Cons list of Colors
  public WorldImage makeRow() {
    return new BesideImage(new CircleImage(20, OutlineMode.SOLID, this.first),
        this.rest.makeRow());
  }

  // produces the color corresponding to the given int value in the Cons case
  public Color getNthValue(int n) {
    if (n == 0) {
      return this.first;
    } else {
      return this.rest.getNthValue(n - 1);
    }
  }

  // reverses a Cons list of Colors
  public ILoColor reverse() {
    return this.reverseHelper(new MtLoColor());
  }

  // helper for reverse()
  public ILoColor reverseHelper(ILoColor acc) {
    /*EVEYTHING ABOVE AND ...
     * fields: 
     *Methods 
     *   acc.generateAnswer() ... ILoColor
     *     acc.generateAnswerDupe() ... ILoColor
     *     acc.generateAnswerNoDupe() ... ILoColor
     *   acc.length() ... int
     *   acc.remove() ... int
     *   acc.numCorectAll ... int
     *   acc.numCorrectAllHelp() ... int
     *   acc.makeRow() ... WorldImage
     *   acc.getNthValue() ... Color
     *   acc.reverse() ... ILoColor
     *   acc.reverseHelper() ... ILoColor
     *   acc.contains(_ ... boolean
     *   acc.totalInExactMatches() ... int
     *   acc.countTotalMatches() ... int
     *   acc.countTotalMatchesHelp() ... int
     *   acc.removeFromGuess(Color c) ... ILoCOlor
     *   acc.hasColor() ... boolean
     *   acc.makeRow ... WorldImage

     *Methods for fields:
     *   
     */
    return this.rest.reverseHelper(new ConsLoColor(this.first, acc));
  }

  // determines if the Cons list of Colors contains the given color
  public boolean contains(Color color) {
    if (this.first.equals(color)) {
      return true;
    } else {
      return this.rest.contains(color);
    }
  }

  // determines the inexact matches between the given cons list and that list
  public int totalInExactMatches(ILoColor that) {
    /*EVEYTHING ABOVE AND ...
     * fields: 
     *Methods 
     *   that.generateAnswer() ... ILoColor
     *     that.generateAnswerDupe() ... ILoColor
     *     that.generateAnswerNoDupe() ... ILoColor
     *   that.length() ... int
     *   that.remove() ... int
     *   that.numCorectAll ... int
     *   that.numCorrectAllHelp() ... int
     *   that.makeRow() ... WorldImage
     *   that.getNthValue() ... Color
     *   that.reverseHelper() ... ILoColor
     *   that.contains(_ ... boolean
     *   that.totalInExactMatches() ... int
     *   that.countTotalMatches() ... int
     *   that.countTotalMatchesHelp() ... int
     *   that.removeFromGuess(Color c) ... ILoCOlor
     *   that.hasColor() ... boolean
     *   that.makeRow ... WorldImage

     *Methods for fields:
     *   
     */
    return that.countTotalMatches(this) - this.numCorrectAll(that);

  }

  // determines the total matches between the given cons list and that list
  public int countTotalMatches(ILoColor that) {
    /*EVEYTHING ABOVE AND ...
     * fields: 
     *Methods 
     *   that.generateAnswer() ... ILoColor
     *     that.rest.generateAnswerDupe() ... ILoColor
     *     that.rest.generateAnswerNoDupe() ... ILoColor
     *   that.length() ... int
     *   that.remove() ... int
     *   that.numCorectAll ... int
     *   that.numCorrectAllHelp() ... int
     *   that.makeRow() ... WorldImage
     *   that.getNthValue() ... Color
     *   that.reverseHelper() ... ILoColor
     *   that.contains(_ ... boolean
     *   that.totalInExactMatches() ... int
     *   that.countTotalMatches() ... int
     *   that.countTotalMatchesHelp() ... int
     *   that.removeFromGuess(Color c) ... ILoCOlor
     *   that.hasColor() ... boolean
     *   that.makeRow ... WorldImage

     *Methods for fields:
     *   
     */
    return that.countTotalMatchesHelp(this.first, this.rest);

  }

  // helper for countTotalMatches()
  public int countTotalMatchesHelp(Color c, ILoColor that) {
    /*EVEYTHING ABOVE AND ...
     * fields: 
     *Methods 
     *   that.generateAnswer() ... ILoColor
     *     that.rest.generateAnswerDupe() ... ILoColor
     *     that.rest.generateAnswerNoDupe() ... ILoColor
     *   that.length() ... int
     *   that.remove() ... int
     *   that.numCorectAll ... int
     *   that.numCorrectAllHelp() ... int
     *   that.makeRow() ... WorldImage
     *   that.getNthValue() ... Color
     *   that.reverseHelper() ... ILoColor
     *   that.contains(_ ... boolean
     *   that.totalInExactMatches() ... int
     *   that.countTotalMatches() ... int
     *   that.countTotalMatchesHelp() ... int
     *   that.removeFromGuess(Color c) ... ILoCOlor
     *   that.hasColor() ... boolean
     *   that.makeRow ... WorldImage

     *Methods for fields:
     *   
     */
    if (this.hasColor(c)) {
      return 1 + that.countTotalMatches(this.removeFromGuess(c));
    } else {
      return that.countTotalMatches(this);
    }

  }

  // detemines if the cons list of Colors has the given Color c
  public boolean hasColor(Color c) {
    if (this.first.equals(c)) {
      return true;
    } else {
      return this.rest.hasColor(c);
    }
  }

  // removes the matching color in the guess list (cons case)
  public ILoColor removeFromGuess(Color c) {
    if (this.first.equals(c)) {
      return this.rest;
    } else {
      return new ConsLoColor(this.first, this.rest.removeFromGuess(c));
    }

  }

  // generates the answer for the cons case
  public ILoColor generateAnswer(boolean duplicates, int length) {

    if (duplicates) {
      return this.generateAnswerDupe(length);
    } else {
      return this.generateAnswerNoDupe(length);
    }

  }

  // generates the answers with duplicates for the cons case
  public ILoColor generateAnswerDupe(int ansLength) {
    Random rand = new Random();
    if (ansLength == 0) {
      return new MtLoColor();
    } else {
      return new ConsLoColor(
          this.getNthValue(rand.nextInt(this.length())), generateAnswerDupe(ansLength - 1));
    }
  }

  // generates the answers without duplicates for the cons case
  public ILoColor generateAnswerNoDupe(int ansLength) {
    Random rand = new Random();
    int index = rand.nextInt(this.length());
    if (ansLength > -1) {
      return new ConsLoColor(this.getNthValue(index), 
          this.removeFromGuess(this.getNthValue(index)).generateAnswerNoDupe(ansLength - 1));
    } else {
      return new MtLoColor();
    }
  }


}

// represents a Guess
class Guess {
  ILoColor guess;
  int exactMatches;
  int inexactMatches;

  // main constructor
  Guess(ILoColor guess, int exactMatches, int inexactMatches) {
    this.guess = guess;
    this.exactMatches = exactMatches;
    this.inexactMatches = inexactMatches;
  }
  // convenience constructor

  Guess(ILoColor guess) {
    this(guess, 0, 0);
  }

  /*fields: 
   *    this.guess ... ILoColor
   *    this.exactMatches ... int
   *    this.inexactMatches ... int
   *Methods 
   *    this.drawGuess() ... WorldImage

   *Methods for fields:
   *   this.guess.drawGuess() ... WorldImage

   */

  // draws a guess
  WorldImage drawGuess() {
    return new BesideImage(this.guess.makeRow().movePinhole(10, 0),
        new TextImage(Integer.toString(this.exactMatches), 30, Color.BLACK).movePinhole(10, 0),
        new TextImage(Integer.toString(this.inexactMatches), 30, Color.BLACK)).movePinhole(-80, 0);
  }

}

// represents an empty list of Guess
class MtLoGuess implements ILoGuess {

  /*fields: 
   *    
   *Methods 
   *    this.length() ... int
   *    this.drawGuesses() ... WorldImage

   *Methods for fields:

   */

  // determines the length of an empty list of Guess
  public int length() {
    return 0;
  }

  // draws an empty list of Guess
  public WorldImage drawGuesses() {
    return new EmptyImage();
  }

}  

// represents a Cons list of Guess
class ConsLoGuess implements ILoGuess {
  Guess first;
  ILoGuess rest;

  ConsLoGuess(Guess first, ILoGuess rest) {
    this.first = first;
    this.rest = rest;
  }

  /*fields: 
   *    this.first ... Guess
   *    this.rest ... ILoGuess
   *Methods 
   *    this.length() ... int
   *    this.drawGuesses() ... WorldImage

   *Methods for fields:
   *   this.rest.length() ... int
   *   this.rest.drawGuesses() ... WorldImage

   */

  // draws a Cons list of Guesses
  public WorldImage drawGuesses() {
    return new AboveAlignImage(AlignModeX.CENTER, this.first.drawGuess(), this.rest.drawGuesses());
  }

  // determines the length of a Cons list of Guesses
  public int length() {
    return 1 + this.rest.length();
  }

}


// examples and tests for MastermindGame
class MastermindExamples {
  ILoColor colors3 = new ConsLoColor(Color.RED, new ConsLoColor(
      Color.BLUE, new ConsLoColor(Color.GREEN, new MtLoColor())));
  ILoColor colors3a = new ConsLoColor(Color.CYAN, new ConsLoColor(
      Color.ORANGE, new ConsLoColor(Color.BLACK, new MtLoColor())));
  ILoColor colors7 = new ConsLoColor(Color.green, new ConsLoColor(Color.red, 
      new ConsLoColor(Color.blue, new ConsLoColor(Color.cyan, 
          new ConsLoColor(Color.orange, new ConsLoColor(Color.pink, 
              new ConsLoColor(Color.gray, new MtLoColor())))))));

  ILoColor colors4 = new ConsLoColor(Color.blue, new ConsLoColor(Color.red, 
      new ConsLoColor(Color.orange, new ConsLoColor(Color.green, new MtLoColor()))));

  MastermindGame defaultGame = new MastermindGame(true, 3, 7, this.colors3, 
      new MtLoColor(), new MtLoGuess(), this.colors3);


  MastermindGame testGame1 = new MastermindGame(true, 3, 8, this.colors3, new MtLoColor(),
      new ConsLoGuess(new Guess(this.colors3), new ConsLoGuess(new Guess(this.colors3a), 
          new MtLoGuess())), new MtLoColor());
  MastermindGame testGame2 = new MastermindGame(true, 7, 10, this.colors4, 
      new MtLoColor(), new MtLoGuess(), this.colors7);
  /*\
  boolean testDrawMobile(Tester t) {
    WorldCanvas c = new WorldCanvas(500, 500);
    WorldScene s = new WorldScene(500, 500);
    return c.drawScene(s.placeImageXY(this.defaultGame.drawEmptiesAll(
    this.defaultGame.guesses), 250, 250))
        && c.show();
  }

   */

  MastermindGame defaultGame1 = new MastermindGame(true, 3, 7, this.colors3);
  MastermindGame testGame3 = new MastermindGame(true, 3, 8, this.colors3, new MtLoColor(),
      new ConsLoGuess(new Guess(this.colors3), new ConsLoGuess(new Guess(this.colors3a), 
          new MtLoGuess())), new MtLoColor());
  MastermindGame testGame4 = new MastermindGame(false, 7, 6, this.colors7);


  // test for testGame()
  boolean testGame(Tester t) {
    return defaultGame1.bigBang(1000,1000, 1.0 / 10.0);
  }

  ILoColor mtlist = new MtLoColor();
  ILoColor list1 = new ConsLoColor(Color.red, this.mtlist);
  ILoColor list2 = new ConsLoColor(Color.black, this.list1);
  ILoColor list3 = new ConsLoColor(Color.blue, this.list2);

  ILoColor list4 = new ConsLoColor(Color.red, this.mtlist);
  ILoColor list5 = new ConsLoColor(Color.green, this.list4);
  ILoColor list6 = new ConsLoColor(Color.black, this.list5);

  ILoColor list7 = new ConsLoColor(Color.red, this.mtlist);
  ILoColor list8 = new ConsLoColor(Color.green, this.list7);
  ILoColor list9 = new ConsLoColor(Color.black, this.list8);
  ILoColor list10 = new ConsLoColor(Color.black, this.list9);
  ILoColor list11 = new ConsLoColor(Color.cyan, this.list10);

  ILoColor list12 = new ConsLoColor(Color.red, this.mtlist);
  ILoColor list13 = new ConsLoColor(Color.cyan, this.list12);
  ILoColor list14 = new ConsLoColor(Color.black, this.list13);
  ILoColor list15 = new ConsLoColor(Color.black, this.list14);
  ILoColor list16 = new ConsLoColor(Color.green, this.list15);

  ILoColor list62 = new MtLoColor();
  ILoColor list63 = new MtLoColor();
  ILoColor list64 = new MtLoColor();
  ILoColor list65 = new MtLoColor();
  ILoColor list66 = new MtLoColor();

  ILoColor list17 = new ConsLoColor(Color.red, this.mtlist);
  ILoColor list18 = new ConsLoColor(Color.green, this.list17);
  ILoColor list19 = new ConsLoColor(Color.blue, this.list18);
  ILoColor list20 = new ConsLoColor(Color.green, this.list19);
  ILoColor list21 = new ConsLoColor(Color.red, this.list20);
  ILoColor list22 = new ConsLoColor(Color.green, this.list21);

  ILoColor list23 = new ConsLoColor(Color.red, this.mtlist);
  ILoColor list24 = new ConsLoColor(Color.blue, this.list23);
  ILoColor list25 = new ConsLoColor(Color.green, this.list24);
  ILoColor list26 = new ConsLoColor(Color.green, this.list25);
  ILoColor list27 = new ConsLoColor(Color.blue, this.list26);
  ILoColor list28 = new ConsLoColor(Color.blue, this.list27);



  ILoColor list29 = new ConsLoColor(Color.red, this.mtlist);
  ILoColor list30 = new ConsLoColor(Color.blue, this.list29);
  ILoColor list31 = new ConsLoColor(Color.green, this.list30);
  ILoColor list32 = new ConsLoColor(Color.red, this.list31);

  ILoColor list33 = new ConsLoColor(Color.red, this.mtlist);
  ILoColor list34 = new ConsLoColor(Color.red, this.list33);
  ILoColor list35 = new ConsLoColor(Color.blue, this.list34);
  ILoColor list36 = new ConsLoColor(Color.red, this.list35);

  ILoColor list37 = new ConsLoColor(Color.blue, this.mtlist);
  ILoColor list38 = new ConsLoColor(Color.blue, this.list37);
  ILoColor list39 = new ConsLoColor(Color.green, this.list38);
  ILoColor list40 = new ConsLoColor(Color.green, this.list39);
  ILoColor list41 = new ConsLoColor(Color.blue, this.list40);
  ILoColor list42 = new ConsLoColor(Color.red, this.list41);
  ILoColor list49 = new ConsLoColor(Color.red, this.list42);
  ILoColor list50 = new ConsLoColor(Color.green, this.list49);

  ILoColor list43 = new ConsLoColor(Color.green, this.mtlist);
  ILoColor list44 = new ConsLoColor(Color.red, this.list43);
  ILoColor list45 = new ConsLoColor(Color.green, this.list44);
  ILoColor list46 = new ConsLoColor(Color.blue, this.list45);
  ILoColor list47 = new ConsLoColor(Color.green, this.list46);
  ILoColor list48 = new ConsLoColor(Color.red, this.list47);
  ILoColor list51 = new ConsLoColor(Color.green, this.list48);
  ILoColor list52 = new ConsLoColor(Color.red, this.list51);

  ILoGuess mtguess = new MtLoGuess();
  ILoGuess guess1 = new ConsLoGuess(new Guess(list33, 0, 0), this.mtguess);
  ILoGuess guess2 = new ConsLoGuess(new Guess(list36, 2, 1), this.guess1);
  ILoGuess guess3 = new ConsLoGuess(new Guess(list22, 1, 1), this.guess2);




  // test for length()
  boolean testLength(Tester t ) {
    return t.checkExpect(this.mtlist.length(), 0)
        && t.checkExpect(this.list3.length(), 3)
        && t.checkExpect(this.list5.length(), 2)
        && t.checkExpect(this.mtguess.length(), 0)
        && t.checkExpect(this.guess1.length(), 1)
        && t.checkExpect(this.guess2.length(), 2);
  }

  //test for remove()
  boolean testRemove(Tester t ) {
    return t.checkExpect(this.mtlist.remove(), this.mtlist)
        && t.checkExpect(this.list3.remove(), this.list2)
        && t.checkExpect(this.list5.remove(), this.list4);
  }

  //tests for numCorrectAll()
  boolean testNumCorrectAll(Tester t) {
    return t.checkExpect(this.mtlist.numCorrectAll(this.list10), 0)
        && t.checkExpect(this.list16.numCorrectAll(this.list11), 3)
        && t.checkExpect(this.list28.numCorrectAll(this.list22), 2)
        && t.checkExpect(this.list36.numCorrectAll(this.list32), 2); 
  }

  //tests for numCorrectAllHelp()
  boolean testNumCorrectAllHelp(Tester t) {
    return t.checkExpect(this.mtlist.numCorrectAllHelp(Color.red, list10), 0)
        && t.checkExpect(this.list33.numCorrectAllHelp(Color.red, list32), 1)
        && t.checkExpect(this.list13.numCorrectAllHelp(Color.green, list15), 0)
        && t.checkExpect(this.list28.numCorrectAllHelp(Color.green, list22), 2)
        && t.checkExpect(this.list16.numCorrectAll(this.list11), 3)
        && t.checkExpect(this.list28.numCorrectAll(this.list22), 2)
        && t.checkExpect(this.list42.numCorrectAll(this.list48), 2);


  }

  // tests for numInexactMatches()
  boolean testTotalInexactMatches(Tester t) {
    return  t.checkExpect(this.list42.totalInExactMatches(this.list48), 2)
        && t.checkExpect(this.list3.totalInExactMatches(this.list6), 1)
        && t.checkExpect(this.list16.totalInExactMatches(this.list11), 2)
        && t.checkExpect(this.list28.totalInExactMatches(this.list22), 2)
        && t.checkExpect(this.list32.totalInExactMatches(this.list36), 1)
        && t.checkExpect(this.list50.totalInExactMatches(this.list52), 4);
  }

  //tests for makeRow()
  boolean testMakeRow(Tester t) {
    return t.checkExpect(this.mtlist.makeRow(), new EmptyImage())
        && t.checkExpect(this.list3.makeRow(), new BesideImage(
            new CircleImage(20, OutlineMode.SOLID, Color.blue),
            this.list2.makeRow()))
        && t.checkExpect(this.list10.makeRow(), new BesideImage(
            new CircleImage(20, OutlineMode.SOLID, Color.black),
            this.list9.makeRow()))
        && t.checkExpect(this.list36.makeRow(), new BesideImage(
            new CircleImage(20, OutlineMode.SOLID, Color.red),
            this.list35.makeRow())); 
  }
  //tests for getNthValue()

  boolean testGetNthValue(Tester t ) {
    return t.checkExpect(this.mtlist.getNthValue(10), Color.black)
        && t.checkExpect(this.colors3.getNthValue(0), Color.red) 
        && t.checkExpect(this.colors3.getNthValue(1), Color.blue)
        && t.checkExpect(this.colors3.getNthValue(2), Color.green);
  }

  //tests for reverse()
  boolean testReverse(Tester t ) {
    return t.checkExpect(this.mtlist.reverse(), this.mtlist)
        && t.checkExpect(this.list36.reverse(), 
            new ConsLoColor(Color.red, new ConsLoColor(Color.red, 
                new ConsLoColor(Color.blue, new ConsLoColor(Color.red, this.mtlist)))))
        && t.checkExpect(this.list32.reverse(), 
            new ConsLoColor(Color.red, new ConsLoColor(Color.blue, 
                new ConsLoColor(Color.green, new ConsLoColor(Color.red, this.mtlist)))))
        && t.checkExpect(this.list14.reverse(), 
            new ConsLoColor(Color.red, new ConsLoColor(Color.cyan, 
                new ConsLoColor(Color.black, this.mtlist))));       
  }


  //tests for reverseHelper()
  boolean testReverseHelper(Tester t ) {
    return t.checkExpect(this.mtlist.reverseHelper(this.mtlist), this.mtlist)
        && t.checkExpect(this.list36.reverseHelper(this.list1), 
            new ConsLoColor(Color.red, new ConsLoColor(Color.red, 
                new ConsLoColor(Color.blue, new ConsLoColor(Color.red, 
                    new ConsLoColor(Color.red, this.mtlist))))))
        && t.checkExpect(this.list32.reverseHelper(this.list28), 
            new ConsLoColor(Color.red, new ConsLoColor(Color.blue, 
                new ConsLoColor(Color.green, 
                    new ConsLoColor(Color.red, new ConsLoColor(Color.blue, 
                    new ConsLoColor(Color.blue, 
                        new ConsLoColor(Color.green, 
                            new ConsLoColor(Color.green,
                        new ConsLoColor(Color.blue, 
                            new ConsLoColor(Color.red, 
                                this.mtlist)))))))))));                        
  }
  //tests for contains()

  boolean testContains(Tester t ) {
    return t.checkExpect(this.mtlist.contains(Color.red), false)
        && t.checkExpect(this.list36.contains(Color.red), true)
        && t.checkExpect(this.list32.contains(Color.cyan), false)
        && t.checkExpect(this.list16.contains(Color.cyan), true);
  }


  // constructor exception tests
  boolean checkConstructorException(Tester t) {
    return t.checkConstructorException(new IllegalArgumentException(
        "Duplicates Disallowed and length of "
            + "sequence is greater than the length of colors"),
        "MastermindGame", false, 10, 5, this.list36, this.list32, this.guess2, this.list32)
        && t.checkConstructorException(new IllegalArgumentException(
            "Duplicates Disallowed and length of "
                + "sequence is greater than the length of colors"),
            "MastermindGame", false, 7, 5, this.list3, this.list6, this.guess2, this.list32)
        && t.checkConstructorException(new IllegalArgumentException(
            "Invalid length: " + Integer.toString(-1)),
            "MastermindGame", true, -1, 5, this.list36, this.list32, this.guess2, this.list32)
        && t.checkConstructorException(new IllegalArgumentException(
            "Invalid length: " + Integer.toString(0)),
            "MastermindGame", true, 0, 5, this.list36, this.list32, this.guess2, this.list32)
        && t.checkConstructorException(new IllegalArgumentException(
            "Invalid guesses: " + Integer.toString(-1)),
            "MastermindGame", true, 10, -1, this.list36, this.list32, this.guess2, this.list32)
        && t.checkConstructorException(new IllegalArgumentException(
            "Invalid guesses: " + Integer.toString(0)),
            "MastermindGame", true, 15, 0, this.list36, this.list32, this.guess2, this.list32)
        && t.checkConstructorException(new IllegalArgumentException(
            "Invalid length of list of colors: " + Integer.toString(0)),
            "MastermindGame", true, 15, 2, this.mtlist, this.list32, this.guess2, this.list32);

  }
  // test for KeyHandler

  boolean testKeyHandler(Tester t) {
    return t.checkExpect(this.defaultGame.onKeyEvent("3"), 
        new MastermindGame(true, 3, 7, this.colors3, 
            new ConsLoColor(Color.green, new MtLoColor()), new MtLoGuess(), this.colors3)) &&
        t.checkExpect(this.defaultGame.onKeyEvent("4"), this.defaultGame);
  }


  //tests for checkDuplicates
  boolean testCheckDuplicates(Tester t) {
    return t.checkException(new IllegalArgumentException(
        "Duplicates Disallowed and length of "
            + "sequence is greater than the length of colors"), 
        new Utils(),"checkDuplicates", false, this.list36, 10,  
        "Duplicates Disallowed and length of "
            + "sequence is greater than the length of colors")
        && t.checkExpect(new Utils().checkDuplicates(false, this.list36, 2, 
            "Duplicates Disallowed and length of "
                + "sequence is greater than the length of colors"), false)
        && t.checkExpect(new Utils().checkDuplicates(true, this.list36, 2, 
            "Duplicates Disallowed and length of "
                + "sequence is greater than the length of colors"), true)
        && t.checkExpect(new Utils().checkDuplicates(true, this.list36, 10, 
            "Duplicates Disallowed and length of "
                + "sequence is greater than the length of colors"), true)
        && t.checkException(new IllegalArgumentException(
            "Duplicates Disallowed and length of "
                + "sequence is greater than the length of colors"), 
            new Utils(),"checkDuplicates", false, this.list36, 12,
            "Duplicates Disallowed and length of "
                + "sequence is greater than the length of colors");

  }

  //tests for chechConstraint
  boolean testCheckConstraint(Tester t) {
    return t.checkException(new IllegalArgumentException(
        "Invalid length: " + Integer.toString(5)), 
        new Utils(),"checkConstraint", 5, 10, 
        "Invalid length: " + Integer.toString(5))
        && t.checkException(new IllegalArgumentException(
            "Invalid length: " + Integer.toString(3)), 
            new Utils(),"checkConstraint", 3, 3, 
            "Invalid length: " + Integer.toString(3))
        && t.checkException(new IllegalArgumentException(
            "Invalid length: " + Integer.toString(-1)), 
            new Utils(),"checkConstraint", -1, 10, 
            "Invalid length: " + Integer.toString(-1))
        && t.checkException(new IllegalArgumentException(
            "Invalid guess: " + Integer.toString(5)), 
            new Utils(),"checkConstraint", 5, 10, ""
                + "Invalid guess: " + Integer.toString(5))
        && t.checkException(new IllegalArgumentException(
            "Invalid guess: " + Integer.toString(3)), 
            new Utils(),"checkConstraint", 3, 3, 
            "Invalid guess: " + Integer.toString(3))
        && t.checkException(new IllegalArgumentException(
            "Invalid guess: " + Integer.toString(-1)), 
            new Utils(),"checkConstraint", -1, 10, 
            "Invalid guess: " + Integer.toString(-1))
        && t.checkExpect(new Utils().checkConstraint(5, 2, 
            "Invalid length: " + Integer.toString(5)), 5)
        && t.checkExpect(new Utils().checkConstraint(3, 2, 
            "Invalid guess: " + Integer.toString(3)), 3);

  }

  //test for generateAnswer()
  boolean testGenerateAnswer(Tester t ) {
    return t.checkExpect(this.mtlist.generateAnswer(true, 0), this.mtlist)
        && t.checkExpect(this.list62.generateAnswer(false, 4), new MtLoColor())
        && t.checkExpect(this.list63.generateAnswer(true, 10), new MtLoColor())
        && t.checkExpect(this.list64.generateAnswer(true, 6), new MtLoColor())
        && t.checkExpect(this.list65.generateAnswer(false, 3), new MtLoColor())
        && t.checkExpect(this.list62.generateAnswer(false, 2), new MtLoColor());


  }


  //tests for checkLoColors
  boolean testCheckLoColors(Tester t) {
    return t.checkException(new IllegalArgumentException(
        "Invalid length of list of colors: " + Integer.toString(0)), 
        new Utils(),"checkLoColors", this.mtlist, 0, 
        "Invalid length of list of colors: " + Integer.toString(0))
        && t.checkException(new IllegalArgumentException(
            "Invalid length of list of colors: " + Integer.toString(10)), 
            new Utils(),"checkLoColors", this.list36, 10, 
            "Invalid length of list of colors: " + Integer.toString(10))
        && t.checkExpect(new Utils().checkLoColors(this.list36, 2, 
            "Invalid length of list of colors: " + Integer.toString(2)), this.list36)
        && t.checkExpect(new Utils().checkLoColors(this.list3, 0, 
            "Invalid length of list of colors: " + Integer.toString(0)), this.list3);

  }


  //tests for drawGuesses()
  boolean testDrawGuesses(Tester t ) {
    return t.checkExpect(this.mtguess.drawGuesses(), new EmptyImage())
        && t.checkExpect(this.guess1.drawGuesses(), new AboveAlignImage(AlignModeX.CENTER, 
            new Guess(list33, 0, 0).drawGuess(), this.mtguess.drawGuesses()))
        && t.checkExpect(this.guess2.drawGuesses(), new AboveAlignImage(AlignModeX.CENTER, 
            new Guess(list36, 2, 1).drawGuess(), this.guess1.drawGuesses()))
        && t.checkExpect(this.guess3.drawGuesses(), new AboveAlignImage(AlignModeX.CENTER, 
            new Guess(list22, 1, 1).drawGuess(), this.guess2.drawGuesses()));

  }

  //tests for drawEmptiesRow()
  boolean testDrawEmptiesRow(Tester t ) {
    return t.checkExpect(this.defaultGame.drawEmptiesRow(1), 
        new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK))
        && t.checkExpect(this.defaultGame.drawEmptiesRow(2), 
            new BesideImage(new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK)))
        && t.checkExpect(this.defaultGame.drawEmptiesRow(3),
            new BesideImage(new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                new BesideImage(new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                    new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK))));
  }

  //tests for drawEmptiesAll()
  boolean testDrawEmptiesAll(Tester t ) {
    return t.checkExpect(this.defaultGame.drawEmptiesAll(1), new EmptyImage())
        && t.checkExpect(this.defaultGame.drawEmptiesAll(2), new AboveAlignImage(AlignModeX.LEFT, 
            new BesideImage(new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                new BesideImage(new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                    new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK)))))
        && t.checkExpect(this.testGame1.drawEmptiesAll(2), new AboveAlignImage(AlignModeX.LEFT, 
            new BesideImage(new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                new BesideImage(new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                    new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK)))))
        && t.checkExpect(this.defaultGame.drawEmptiesAll(9), new AboveAlignImage(AlignModeX.LEFT, 
            new BesideImage(new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                new BesideImage(new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                    new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK))),
            new AboveAlignImage(AlignModeX.LEFT, 
                new BesideImage(new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                    new BesideImage(new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                        new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK))),
                new AboveAlignImage(AlignModeX.LEFT, 
                    new BesideImage(new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                        new BesideImage(new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                            new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK))),
                    new AboveAlignImage(AlignModeX.LEFT, 
                        new BesideImage(new CircleImage(20, 
                            OutlineMode.OUTLINE, Color.BLACK), 
                            new BesideImage(new CircleImage(20, 
                                OutlineMode.OUTLINE, Color.BLACK), 
                                new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK))),
                        new AboveAlignImage(AlignModeX.LEFT, 
                            new BesideImage(new CircleImage(20, 
                                OutlineMode.OUTLINE, Color.BLACK), 
                                new BesideImage(new CircleImage(20, 
                                    OutlineMode.OUTLINE, Color.BLACK), 
                                    new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK))),
                            new AboveAlignImage(AlignModeX.LEFT, 
                                new BesideImage(new CircleImage(20, 
                                    OutlineMode.OUTLINE, Color.BLACK), 
                                    new BesideImage(new CircleImage(20, 
                                        OutlineMode.OUTLINE, Color.BLACK), 
                                        new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK))),
                                new AboveAlignImage(AlignModeX.LEFT, 
                                    new BesideImage(new CircleImage(20, 
                                        OutlineMode.OUTLINE, Color.BLACK), 
                                        new BesideImage(new CircleImage(20, 
                                            OutlineMode.OUTLINE, Color.BLACK), 
                                            new CircleImage(20, 
                                                OutlineMode.OUTLINE, Color.BLACK))),
                                    new AboveAlignImage(AlignModeX.LEFT, 
                                        new BesideImage(new CircleImage(20, 
                                            OutlineMode.OUTLINE, Color.BLACK), 
                                            new BesideImage(new CircleImage(20, 
                                                OutlineMode.OUTLINE, Color.BLACK), 
                                                new CircleImage(20, 
                                                    OutlineMode.OUTLINE, Color.BLACK))),
                                        new EmptyImage())))))))));

  }

  //test for generateAnswerDupe()
  boolean testGenerateAnswerDupe(Tester t ) {
    return t.checkExpect(this.mtlist.generateAnswerDupe(0), this.mtlist)
        && t.checkExpect(this.list62.generateAnswerDupe(4), new MtLoColor())
        && t.checkExpect(this.list63.generateAnswerDupe(4), new MtLoColor())
        && t.checkExpect(this.list64.generateAnswerDupe(4), new MtLoColor())
        && t.checkExpect(this.list65.generateAnswerDupe(4), new MtLoColor())
        && t.checkExpect(this.list62.generateAnswerDupe(4), new MtLoColor());

  }

  //test for generateAnswerNoDupe()
  boolean testGenerateAnswerNoDupe(Tester t ) {
    return t.checkExpect(this.mtlist.generateAnswerNoDupe(-2), this.mtlist)
        && t.checkExpect(this.list62.generateAnswerNoDupe(4), new MtLoColor())
        && t.checkExpect(this.list63.generateAnswerNoDupe(4), new MtLoColor())
        && t.checkExpect(this.list64.generateAnswerNoDupe(4), new MtLoColor())
        && t.checkExpect(this.list65.generateAnswerNoDupe(4), new MtLoColor())
        && t.checkExpect(this.list62.generateAnswerNoDupe(4), new MtLoColor());


  }




  //tests for lastScene(String msg)
  boolean testLastScene(Tester t ) {
    return t.checkExpect(this.defaultGame.lastScene("you lose"),
        this.defaultGame.makeScene().placeImageXY(new BesideImage(
            this.colors3.makeRow().movePinhole(30, 0), 
            new TextImage("you lose", 40, Color.BLACK)),
            (3 * 20 + (int) (new BesideImage(
                this.colors3.makeRow(),
                new TextImage("you lose", 40, Color.BLACK)).getWidth())) / 2, 20));
  }






  //tests for makeScene()
  boolean testMakeScene(Tester t ) {
    return t.checkExpect(this.defaultGame.makeScene(), new WorldScene(320, 360).placeImageXY(
        new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.BOTTOM,
            this.mtlist.reverse().makeRow(), 0, 40 * (new MtLoColor().length() + 1),
            new AboveAlignImage(AlignModeX.LEFT, 
                new BesideImage(new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                    new BesideImage(new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                        new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK))),
                new AboveAlignImage(AlignModeX.LEFT, 
                    new BesideImage(new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                        new BesideImage(new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                            new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK))),
                    new AboveAlignImage(AlignModeX.LEFT, 
                        new BesideImage(
                            new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                            new BesideImage(new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                                new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK))),
                        new AboveAlignImage(AlignModeX.LEFT, 
                            new BesideImage(
                                new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                                new BesideImage(
                                    new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                                    new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK))),
                            new AboveAlignImage(AlignModeX.LEFT, 
                                new BesideImage(
                                    new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                                    new BesideImage(
                                        new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                                        new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK))),
                                new AboveAlignImage(AlignModeX.LEFT, 
                                    new BesideImage(
                                        new CircleImage(20, OutlineMode.OUTLINE, Color.BLACK), 
                                        new BesideImage(
                                            new CircleImage(20, 
                                                OutlineMode.OUTLINE, Color.BLACK), 
                                            new CircleImage(20, 
                                                OutlineMode.OUTLINE, Color.BLACK))),
                                    new AboveAlignImage(AlignModeX.LEFT, 
                                        new BesideImage(
                                            new CircleImage(20, 
                                                OutlineMode.OUTLINE, Color.BLACK), 
                                            new BesideImage(
                                                new CircleImage(20, 
                                                    OutlineMode.OUTLINE, Color.BLACK), 
                                                new CircleImage(20, 
                                                    OutlineMode.OUTLINE, Color.BLACK))),
                                        new AboveAlignImage(AlignModeX.LEFT, 
                                            new BesideImage(
                                                new CircleImage(20, 
                                                    OutlineMode.OUTLINE, Color.BLACK), 
                                                new BesideImage(
                                                    new CircleImage(20, 
                                                        OutlineMode.OUTLINE, Color.BLACK), 
                                                    new CircleImage(20, 
                                                        OutlineMode.OUTLINE, Color.BLACK))),
                                            new EmptyImage()))))))),
                this.colors3.makeRow())),
        20 * 3, 20 * (7 + 2)).placeImageXY(new RectangleImage(40 * 3, 40, 
            OutlineMode.SOLID, Color.black),
            3 * 20, 20).placeImageXY(this.mtguess.drawGuesses(), 3 * 20 + 0
                - 2, 360 - (this.mtguess.length() * 40 / 2) - 40));
  }


}














