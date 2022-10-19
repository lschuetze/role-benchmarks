package benchmark.bank;

import java.util.List;
import java.util.LinkedList;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.NoSuchMethodException;
import java.lang.IllegalAccessException;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

public team class Bank {

    // static Logger logger = LoggerFactory.getLogger(Bank.class);

    private List<Customer> customers;
    private List<Account> checkingsAccounts;
    private List<Account> savingsAccounts;

    precedence CheckingsAccount,SavingsAccount;

    public Bank() {
        // We cannot call this._OT$implicitlyActivate() directly
        // due to "Illegal identifier _OT$implicitlyActivate (OTJLD A.0.3)"
        try {
            Method m = Bank.class.getDeclaredMethod("_OT$implicitlyActivate()");
            m.invoke(this);
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {

        }
    }

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

        public void before() {
            // logger.info("before CheckingsAccount decrease");
        }

        public void before2() {
            // logger.info("before CheckingsAccount increase");
        }

        callin float limited(float amount) {
            // logger.info("replace CheckingsAccount decrease BEGIN");
            float f = base.limited(amount);
            // logger.info("replace CheckingsAccount decrease END");
            return f;
        }

        callin float replace(float amount) {
            // logger.info("replace CheckingsAccount increase BEGIN");
            float f = base.replace(amount);
            // logger.info("replace CheckingsAccount increase END");
            return f;
        }

        public void after() {
            // logger.info("after CheckingsAccount decrease");
        }

        void before() <- before float decrease(float amount);

        // void before2() <- before float increase(float amount);

        float limited(float amount) <- replace float decrease(float amount);

        // float replace(float amount) <- replace float increase(float amount);

        void after() <- after float decrease(float amount);
    }

    public class SavingsAccount playedBy Account {

        private static final float FEE = 1.1f;

        public void before() {
            // logger.info("before SavingsAccount decrease");
        }

        public void before2() {
            // logger.info("before SavingsAccount increase");
        }

        callin float withFee(float amount) {
            // logger.info("replace SavingsAccount decrease BEGIN");
            float f = base.withFee(amount * FEE);
            // logger.info("replace SavingsAccount decrease END");
            return f;
        }

        callin float replace(float amount) {
            // logger.info("replace SavingsAccount increase BEGIN");
            float f = base.replace(amount);
            // logger.info("replace SavingsAccount increase END");
            return f;
        }

        public void after() {
            // logger.info("after SavingsAccount decrease");
        }

        void before() <- before float decrease(float amount);

        // void before2() <- before float increase(float amount);

        float withFee(float amount) <- replace float decrease(float amount);

        // float replace(float amount) <- replace float increase(float amount);

        void after() <- after float decrease(float amount);

    }
}
