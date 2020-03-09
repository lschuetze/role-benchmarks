import bank.*;

public class Bank extends Benchmark {
    private bank.Bank bank;

    @Override
    public boolean innerBenchmarkLoop(final int innerIterations) {
        for (Account from : bank.getCheckingAccounts()) {
            float amount = from.getBalance() / (float) innerIterations;
            for (Account to : bank.getSavingAccounts()) {
                Transaction transaction = new Transaction();

                Source source = new Source(from);
                Target target = new Target(to);

                transaction.execute(source, target, amount);
            }
        }
        return true;
    }

    public boolean setUp(final int innerIterations) {
        bank = new bank.Bank();

        for (int i = 0; i < innerIterations; ++i) {
            Customer customer = new Customer(new PersonCore());
            bank.addCustomer(customer);

            SavingsAccount sa = new SavingsAccount(new AccountCore(1000.0f));
            CheckingsAccount ca = new CheckingsAccount(new AccountCore(1000.0f));

            bank.addSavingsAccount(customer, sa);
            bank.addCheckingsAccount(customer, ca);
        }
        return true;
    }

    @Override
    public Object benchmark() {
        throw new RuntimeException("Should never be reached");
    }

    @Override
    public boolean verifyResult(Object result) {
        throw new RuntimeException("Should never be reached");
    }
}
