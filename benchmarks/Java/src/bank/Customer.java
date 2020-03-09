package bank;

public class Customer extends Person {
    private Person core;

    public Customer(Person core) {
        super();
        this.core = core;
        core.addRole(this);
    }

    public void addAccount(Account a) {

    }
}
