package bank;

import java.util.List;
import java.util.LinkedList;

public abstract class Account {

  private List<Account> accountRoles;

  public Account() {
    this.accountRoles = new LinkedList<>();
  }

  public abstract void decrease(float amount);

  public abstract void increase(float amount);

  public abstract float getBalance();

  public void addRole(Account role) {
    accountRoles.add(role);
  }

  public boolean removeRole(Account role) {
    return accountRoles.remove(role);
  }
}
