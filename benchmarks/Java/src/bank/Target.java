package bank;

public class Target extends Account {

    final Account core;

    public Target(Account core) {
        super();
        this.core = core;
        core.addRole(this);
    }

    @Override
    public void increase(float amount) {
        core.increase(amount);
    }

    @Override
    public void decrease(float amount) {
        core.decrease(amount);
    }

    @Override
    public float getBalance() {
        return core.getBalance();
    }
}
