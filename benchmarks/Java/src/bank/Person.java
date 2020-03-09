package bank;

import java.util.List;
import java.util.LinkedList;

public abstract class Person {

    private List<Person> personRoles;

    public Person() {
        this.personRoles = new LinkedList<>();
    }

    public void addRole(Person role) {
        personRoles.add(role);
    }

    public boolean removeRole(Person role) {
        return personRoles.remove(role);
    }
}
