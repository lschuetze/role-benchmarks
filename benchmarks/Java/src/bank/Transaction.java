package bank;

public class Transaction {

    public void execute(Source source, Target target, float amount) {
        source.decrease(amount);
        target.increase(amount);
    }
}
