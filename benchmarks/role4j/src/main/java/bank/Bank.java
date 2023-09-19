package bank;

import java.util.List;
import java.util.LinkedList;

import net.role4j.ICompartment;
import net.role4j.IRole;
import net.role4j.IPlayer;

public class Bank implements ICompartment {

  private List<Person> customers;
  private List<Account> checkingAccounts;
  private List<Account> savingAccounts;

  public List<Account> getSavingAccounts() {
    return savingAccounts;
  }

  public List<Account> getCheckingAccounts() {
    return checkingAccounts;
  }

  public void addCustomer(Person c) {
		if (customers == null) {
			customers = new LinkedList<>();
		}
    try {
      c.bind(Customer.class);
    } catch (Throwable e) {
      //
    }
    customers.add(c);
	}

  public void addCheckingsAccount(Person c, Account a) {
		if (checkingAccounts == null) {
			checkingAccounts = new LinkedList<>();
		}
    if(c.interfaceOf(Customer.class) == null) {
      throw new RuntimeException("Person " + c + " is no customer.");
    }
    try {
      a.bind(CheckingsAccount.class);
    } catch (Throwable e) {
      //
    }
		checkingAccounts.add(a);
	}

  public void addSavingsAccount(Person c, Account a) {
    if (savingAccounts == null) {
      savingAccounts = new LinkedList<>();
    }
    if(c.interfaceOf(Customer.class) == null) {
      throw new RuntimeException("Person " + c + " is no customer.");
    }
    try {
      a.bind(SavingsAccount.class);
    } catch (Throwable e) {
      //
    }
    savingAccounts.add(a);
  }

  public void executeTransaction(Transaction t, float a) {
    t.execute(a);
  }
}
