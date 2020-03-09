package benchmark;

import benchmark.bank.Account;
import benchmark.bank.Bank;
import benchmark.bank.Person;
import benchmark.bank.CallinTransaction;

public class BankBenchmark extends Benchmark {

    private Bank bank;

    @Override
    public boolean innerBenchmarkLoop(final int innerIterations) {
        bank.activate();
        for (Account from : bank.getCheckingAccounts()) {
            float amount = 100.0f;
            for (Account to : bank.getSavingAccounts()) {
                    CallinTransaction transaction = new CallinTransaction();
                    transaction.activate();
                    try {
                        transaction.execute(from, to, amount);
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    } finally {
                        transaction.deactivate();
                    }
            }
        }
        bank.deactivate();
        return true;
    }

    public boolean setUp(final int innerIterations) {
        bank = new Bank();
        bank.activate();

        for (int i = 0; i < innerIterations; ++i) {
            Person p = new Person();
            bank.addCustomer(p);

            Account sa = new Account(0, 1000.0f);
            Account ca = new Account(0, 1000.0f);
            bank.addSavingsAccount(p, sa);
            bank.addCheckingsAccount(p, ca);
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
