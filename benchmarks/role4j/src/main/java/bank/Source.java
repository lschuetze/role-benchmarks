package bank;

import net.role4j.IRole;

public class Source implements IRole {

  public void decrease(float amount) {
    //System.out.println("withDraw");
    getPlayer(Account.class).decrease(amount);
  }
}
