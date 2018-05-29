package core;
/**
 * this is the Counter class.
 *
 */
public class Counter {
    //member
    private int counter;
    //constructor
    /**
     * @param counter -this is the number to set the counter to start with
     */
    public Counter(int counter) {
        this.counter = counter;
    }
   /**
 * @param number -this is the number to add to the counter
 */
public void increase(int number) {
       this.counter += number;
   }
   /**
 * @param number -this is the number to subtract to the counter
 */
public void decrease(int number) {
       this.counter -= number;
   }
   /**
 * @return -get current count.
 */
public int getValue() {
       return this.counter;
   }
}