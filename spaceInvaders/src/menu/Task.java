package menu;


/**
 * this is the task<task> interface.
 * @param <T>
 */
public interface Task<T> {
    /**
     * @return the task to run
     */
    T run();
}
