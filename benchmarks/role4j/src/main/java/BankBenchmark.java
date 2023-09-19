import bank.Bank;
import bank.CheckingsAccount;
import bank.SavingsAccount;
import bank.Account;
import bank.Person;
import bank.Transaction;


import net.role4j.*;
import net.role4j.Registry;

public class BankBenchmark extends Benchmark {

	private final static Registry reg = Registry.getRegistry();
	private Bank bank;

	@Override
	public boolean innerBenchmarkLoop(final int innerIterations) {
		try {
			this.bank.activate();
			for (Account from : bank.getCheckingAccounts()) {
				float amount = from.getBalance() / (float) innerIterations;
				for (Account to : bank.getSavingAccounts()) {
					try {
						Transaction transaction = reg.newCore(Transaction.class);
						transaction.setAccounts(from, to);
						bank.executeTransaction(transaction, amount);
					} catch (RuntimeException e) {
						e.printStackTrace();
					}
				}
			}
			this.bank.deactivate();
		}
		catch(Throwable e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean setUp(final int innerIterations) {
		try {
			this.bank = reg.newCompartment(Bank.class);
			this.bank.activate();

			for (int i = 0; i < innerIterations; ++i) {
				Person p = reg.newCore(Person.class);
				bank.addCustomer(p);

				Account sa = reg.newCore(Account.class);
				Account ca = reg.newCore(Account.class);
				sa.setBalance(1000.0f);
				ca.setBalance(1000.0f);
				bank.addSavingsAccount(p, sa);
				bank.addCheckingsAccount(p, ca);
			}
			bank.deactivate();
		}
		catch(Throwable e) {
			e.printStackTrace();
			return false;
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
