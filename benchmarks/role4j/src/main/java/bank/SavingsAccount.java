package bank;

import net.role4j.IRole;

public class SavingsAccount implements IRole {

  private static final float LIMIT = 100.0f;

  public void decrease(float a) {
    //System.out.println("limited");
    if (a <= LIMIT) {
      getPlayer(Account.class).decrease(a);
    } else {
      throw new RuntimeException(a + "is over the limit " + LIMIT);
    }
  }
}
