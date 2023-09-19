package bank

import scroll.internal.compartment.impl.Compartment
import scroll.internal.dispatch.DispatchQuery

case class Transaction() extends Compartment {

  roleGraph.reconfigure(true, false)

  def execute(amount: Float): Unit = {
    roleQueries.one[Source]().withDraw(amount)
    roleQueries.one[Target]().deposit(amount)
  }

  case class Source() {

    def withDraw(m: Float): Unit = {
      // println("Source.withDraw")
      val _ = (+this).decrease(m)
    }

  }

  case class Target() {

    def deposit(m: Float): Unit = {
      // println("Target.deposit")
      val _ = (+this).increase(m)
    }

  }

}
