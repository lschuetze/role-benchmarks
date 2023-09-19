package bank;

import net.role4j.IRole;

public class Target implements IRole {

  public void increase(float amount) {
    //System.out.println("deposit");
    getPlayer(Account.class).increase(amount);
  }
}
