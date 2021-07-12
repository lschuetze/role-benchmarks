package benchmark.bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Account {

    static Logger logger = LoggerFactory.getLogger(Account.class);

    public  int id;
    private float balance;

    public Account(int id, float balance) {
        this.balance = balance;
        this.id = id;
    }

    public float decrease(float amount) {
        balance -= amount;
        logger.info("BASE Account {} decrease {}", id, amount);
        return amount;
    }

    public float increase(float amount) {
        balance += amount;
        logger.info("BASE Account {} increase {}", id, amount);
        return amount;
    }

    public float getBalance() {
        return balance;
    }
}
