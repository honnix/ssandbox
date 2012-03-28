package bobsrockets

package navigation {
  private[bobsrockets] class Navigator {
    protected[navigation] def useStartChart() {}

    class LegOfJourney {
      private[Navigator] val distance = 100
    }

    private[this] var speed = 200

    val other = new Navigator
    // other.speed // does not compile
  }
}

package launch {
  import navigation._

  object Vehicle {
    private[launch] val guide = new Navigator
  }
}

class Rocket {
  import Rocket.fuel

  private def canGoHomeAgain = fuel > 20
}

object Rocket {
  private def fuel = 10

  def chooseStrategy(rocket: Rocket) {
    if (rocket.canGoHomeAgain)
      goHome()
    else
      pickAStar()
  }

  def goHome() {}

  def pickAStar() {}
}

package object bobsrockets {
  def sayHello() { println("hello") }
}
