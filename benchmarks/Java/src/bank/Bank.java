package bank;

import java.util.List;
import java.util.LinkedList;

public class Bank {
    private List<Customer> customers;
    private List<Account> checkingAccounts;
    private List<Account> savingAccounts;

    public List<Account> getCheckingAccounts() {
        return checkingAccounts;
    }

    public List<Account> getSavingAccounts() {
        return savingAccounts;
    }

    public void addCustomer(Customer customer) {
        if (customers == null) {
            customers = new LinkedList<>();
        }
        customers.add(customer);
    }

    public void addCheckingsAccount(Customer c, CheckingsAccount a) {
        c.addAccount(a);
        if (checkingAccounts == null) {
            checkingAccounts = new LinkedList<>();
        }
        checkingAccounts.add(a);
    }

    public void addSavingsAccount(Customer c, SavingsAccount a) {
        c.addAccount(a);
        if (savingAccounts == null) {
            savingAccounts = new LinkedList<>();
        }
        savingAccounts.add(a);
    }
}
