package benchmark.bank;

public class Account {

    public  int id;
    private float balance;

    public Account(int id, float balance) {
        this.balance = balance;
        this.id = id;
    }

    public float decrease(float amount) {
        balance -= amount;
        //System.out.println("Account " + id + " decrease" + amount);
        return amount;
    }

    public float increase(float amount) {
        balance += amount;
        // System.out.println("Account increase");
        return amount;
    }

    public float getBalance() {
        return balance;
    }
}
