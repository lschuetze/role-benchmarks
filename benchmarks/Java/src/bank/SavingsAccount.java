package bank;

public class SavingsAccount extends Account {
    private Account core;
    private static final float FEE = 0.1f;

    public SavingsAccount(Account core) {
        super();
        this.core = core;
        core.addRole(this);
    }

    @Override
    public void decrease(float amount) {
        core.decrease(amount + amount * FEE);
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
