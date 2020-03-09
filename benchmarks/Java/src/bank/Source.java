package bank;

public class Source extends Account {
    Account core;

    public Source(Account core) {
        super();
        this.core = core;
        core.addRole(this);
    }

    @Override
    public void decrease(float amount) {
        core.decrease(amount);
    }

    @Override
    public void increase(float amount) {
        core.increase(amount);
    }

    @Override
    public float getBalance() {
        return core.getBalance();
    }
}
