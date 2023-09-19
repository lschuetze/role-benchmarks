import org.scalatest._
import bank._

import scroll.internal.support.DispatchQuery
import DispatchQuery._

class TransactionSpec extends FlatSpec with Matchers {

  "A Transaction" should "transfer money from one account to another" in {
    val bank = new Bank
    val p    = new Person
    bank.addPlaysRelation(p, new bank.Customer)
    val acc1 = new Account(100.0f)
    val acc2 = new Account(0.0f)

    bank.addPlaysRelation(acc1, new bank.SavingsAccount)
    bank.addPlaysRelation(acc2, new bank.SavingsAccount)

    bank.addSavingsAccount(acc1)
    bank.addSavingsAccount(acc2)

    val transaction = new Transaction

    transaction.addPlaysRelation(acc1, new transaction.Source)
    transaction.addPlaysRelation(acc2, new transaction.Target)

    transaction partOf bank

    val transactionRole = new bank.TransactionRole
    bank.addPlaysRelation(transaction, transactionRole)
    transactionRole.execute(100.0f)

    acc2.getBalance should be(90.0f)
    acc1.getBalance should be(0.0f)
  }

}
