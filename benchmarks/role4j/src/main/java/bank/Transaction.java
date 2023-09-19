package bank;

import net.role4j.IPlayer;
import net.role4j.IRole;

public class Transaction implements IPlayer {

  private Account source;
  private Account target;

  public void setAccounts(Account source, Account target) {
    try {
      source.bind(Source.class);
      target.bind(Target.class);
    } catch (Throwable e) {
      //
    }
    this.source = source;
    this.target = target;
  }

  public void execute(float amount) {
    source.decrease(amount);
    target.increase(amount);
  }
}
