package benchmark.bank;

import java.util.List;
import java.util.LinkedList;

public team class Bank {
    private List<Customer> customers;
    private List<Account> checkingsAccounts;
    private List<Account> savingsAccounts;

    precedence SavingsAccount,CheckingsAccount;

    public class Customer playedBy Person {
        private List<Account> accounts;

        public void addAccount(Account account) {
            if (accounts == null) {
                accounts = new LinkedList<>();
            }
            accounts.add(account);
        }
    }

    public List<Account> getSavingAccounts() {
        return savingsAccounts;
    }

    public List<Account> getCheckingAccounts() {
        return checkingsAccounts;
    }

    public void addCustomer(Person as Customer customer) {
        if (customers == null) {
            customers = new LinkedList<>();
        }
        customers.add(customer);
    }

    public void addCheckingsAccount(Person as Customer c, Account as CheckingsAccount a) {
        c.addAccount(a);
        if (checkingsAccounts == null) {
            checkingsAccounts = new LinkedList<>();
        }
        checkingsAccounts.add(a);
    }

    public void addSavingsAccount(Person as Customer c, Account as SavingsAccount a) {
        c.addAccount(a);
        if (savingsAccounts == null) {
            savingsAccounts = new LinkedList<>();
        }
        savingsAccounts.add(a);
    }

    public class CheckingsAccount playedBy Account {

        // private static final float LIMIT = 100.0f;

        callin float limited(float amount) {
            // System.out.println("CheckingsAccount replace BEGIN");
            float f = base.limited(amount);
            // System.out.println("CheckingsAccount replace END");
            return f;
        }

        public void after() {
            // System.out.println("after checkingsaccount decrease");
        }

        public void before() {
            //System.out.println("before CheckingsAccount decrease");
        }

        //void after() <- after void decrease(float amount);

        //void before() <- before float decrease(float amount);

        float limited(float amount) <- replace float decrease(float amount);

        }

    public class SavingsAccount playedBy Account {

            private static final float FEE = 1.1f;

            callin float withFee(float amount) {
                //System.out.println("SavingsAccount replace BEGIN");
                float f = base.withFee(amount * FEE);
                //System.out.println("SavingsAccount replace END");
                return f;
            }

            public void before() {
                //System.out.println("before SavingsAccount decrease");
            }

            void before() <- before float decrease(float amount);
            float withFee(float amount) <- replace float decrease(float amount);
        }
}
