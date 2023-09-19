package bank;

import net.role4j.IPlayer;

public class Account implements IPlayer {
  private float balance;

  public Account() {

  }

  public void setBalance(float amount) {
    balance = amount;
  }

  public void decrease(float amount) {
    //System.out.println("decrease");
    balance -= amount;
  }

  public void increase(float amount) {
    //System.out.println("increase");
    balance += amount;
  }

  public float getBalance() {
    return balance;
  }
}
