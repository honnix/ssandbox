package async

import concurrent.ExecutionContext.Implicits.global
import scala.async.Async.{async, await}

/**
 *
 *
 * @author honnix
 */
object AsyncDemo extends App {
  val future = async {
    val f1 = async {
      true
    }
    val f2 = async {
      Thread.sleep(5000)
      42
    }
    if (await(f1)) await(f2) else 0
  }

  future onSuccess {
    case x ⇒ println(x)
  }
}
