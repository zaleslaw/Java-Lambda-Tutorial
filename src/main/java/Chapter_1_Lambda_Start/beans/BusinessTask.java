package Chapter_1_Lambda_Start.beans;

/**
 * Typical interface
 */
public interface BusinessTask {
    void updateStateInDB();

    default void updateStateInDBByDefault() {
        System.out.println("I'm non-abstract method in interface!!!");
    }
}
