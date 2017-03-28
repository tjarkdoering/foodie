package example

import scala.scalajs.js
import org.scalajs.dom._
import scalatags.JsDom.all._
import scalatags.rx.all._
import rx._

object Main extends js.JSApp {
  implicit val ctx: Ctx.Owner = Ctx.Owner.safe()

  val c = Var("blue")
  val text = Rx(s"It is a ${c()} text!")

  def toggle(): Unit = {
    c() = if (c.now == "blue") "green" else "blue"
  }

  override def main(): Unit = {
    document.body.appendChild(
      div(
        color := c, onclick := toggle _,
        text
      ).render
    )
  }
}
