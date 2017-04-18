package Chapter_6_Advanced_Lambdas.ex_1;

import Chapter_3_Migration_from_7_to_8.beans.Employee;

/**
 * Only those interfaces which have only one non-object abstract method can be used with lambda expressions
 */
@FunctionalInterface
public interface Hirable<You extends Employee, HR extends Employee, YourBoss extends Employee, YourProject extends Project> {
    YourProject interview(You candidate, HR hr, YourBoss teamLead);
    // Try to delete this method or add yet one - you will get a compilation error
}
