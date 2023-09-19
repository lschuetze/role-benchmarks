package bank

import scroll.internal.compartment.impl.Compartment
import scroll.internal.dispatch.DispatchQuery
import scroll.internal.dispatch.DispatchQuery.Bypassing
import scroll.internal.dispatch.DispatchQuery.From
import scroll.internal.dispatch.DispatchQuery.Through
import scroll.internal.dispatch.DispatchQuery.To

import scala.collection.mutable.ListBuffer

case class Bank() extends Compartment {

  roleGraph.reconfigure(true, false)

  var checkingAccounts = ListBuffer[Account]()
  var savingAccounts   = ListBuffer[Account]()

  def getCheckingAccounts(): ListBuffer[Account] = checkingAccounts
  def getSavingAccounts(): ListBuffer[Account]   = savingAccounts

  def addSavingsAccount(acc: Account) =
    savingAccounts = savingAccounts :+ acc

  def addCheckingsAccount(acc: Account) =
    checkingAccounts = checkingAccounts :+ acc

  case class Customer() {
    var accounts = ListBuffer[Accountable]()

    def addAccount(acc: Accountable): Unit =
      accounts = accounts :+ acc

  }

  case class CheckingsAccount() extends Decreasable {

    def decrease(amount: Float): Unit = {
      // println("CheckingsAccount:decrease")
      given DispatchQuery = From(_.isInstanceOf[Account])
        .To(_.isInstanceOf[CheckingsAccount])
        // so we won't calling decrease() recursively on this
        .Bypassing(_.isInstanceOf[CheckingsAccount])
      val _ = (+this).decrease(amount)
    }

  }

  case class SavingsAccount() extends Increasable {
    private def transactionFee(amount: Float): Float = amount * 0.1f

    def increase(amount: Float): Unit = {
      // println("SavingsAccount.increase")
      given DispatchQuery = From(_.isInstanceOf[Account])
        .To(_.isInstanceOf[SavingsAccount])
        // so we won't calling increase() recursively on this
        .Bypassing(_.isInstanceOf[SavingsAccount])
      val _ = (+this).increase(amount - transactionFee(amount))
    }

  }

  case class TransactionRole() {

    def execute(amount: Float): Unit = {
      // println("TransactionRole.execute")
      given DispatchQuery = From(_.isInstanceOf[Transaction])
        .To(_.isInstanceOf[TransactionRole])
        // so we won't calling execute() recursively on this
        .Bypassing(_.isInstanceOf[TransactionRole])
      val _ = (+this).execute(amount)
    }

  }

}
