package menu;


import animation.Animation;

/**
 * this is the Menu<T> interface.
 * @param <T>
 */
public interface Menu<T> extends Animation {
   /**
 * @param key - the key to press on
 * @param message - the description of what going to happen if key will pressed
 * @param returnVal - the task that should operate
 */
void addSelection(String key, String message, T returnVal);
   /**
 * @return the task that should operate
 */
T getStatus();
   /**
 * reset the boolean members.
 */
void reset();
   /**
 * @param key - the key to press on
 * @param message - the description of what going to happen if key will pressed
 * @param subMenu - the task of the subMenu that should operate
 */
void addSubMenu(String key, String message, Menu<T> subMenu);
}