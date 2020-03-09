package bank;

public class CheckingsAccount extends Account {
    private Account core;
    private static final float LIMIT = 100.0f;

    public CheckingsAccount(Account core) {
        super();
        this.core = core;
        core.addRole(this);
    }

    @Override
    public void decrease(float amount) {
        if (amount <= LIMIT) {
            core.decrease(amount);
        } else {
            // do nothing
        }
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
