package bank

trait Accountable

trait Decreasable extends Accountable {
  def decrease(amount: Float): Unit
}

trait Increasable extends Accountable {
  def increase(amount: Float): Unit
}

case class Account(var balance: Float = 0.0f) extends Increasable with Decreasable {

  def increase(amount: Float): Unit =
    balance = balance + amount

  def decrease(amount: Float): Unit =
    balance = balance - amount

  def getBalance(): Float = balance
}
