package bank;

public class AccountCore extends Account {
    private float balance;

    public AccountCore(float balance) {
        super();
        this.balance = balance;
    }

    @Override
    public void decrease(float amount) {
        balance -= amount;
    }

    @Override
    public void increase(float amount) {
        balance += amount;
    }

    @Override
    public float getBalance() {
        return balance;
    }
}
