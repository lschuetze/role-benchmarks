package bank;

import net.role4j.IRole;

public class CheckingsAccount implements IRole {
  private static final float FEE = 0.1f;

  public void decrease(float a) {
    //System.out.println("withFee");
    getPlayer(Account.class).decrease(a + a * FEE);
  }
}
