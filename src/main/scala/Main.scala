package example

import scala.scalajs.js
import org.scalajs.dom._
import scalatags.JsDom.all._
import scalatags.rx.all._
import rx._

case class Pos(x:Double,y:Double)

object Main extends js.JSApp {
  implicit val ctx: Ctx.Owner = Ctx.Owner.safe()


  var poss:List[Pos]=Nil
  val c = Var("blue")
  val text = Rx(s"It is a ${c()} text!")
  val dots = { 
  
    import scalatags.JsDom.svgAttrs._
    import scalatags.JsDom.svgTags._
    svg(width:=500,height:=500).render
  }
  val canv = div(width:=500,height:=500,border:="1px solid black",
    onmousemove:={(e:MouseEvent)=>
      import scalatags.JsDom.svgAttrs._
      import scalatags.JsDom.svgTags._
      console.log(e)
      if (e.buttons == 1) {
      val pos = Pos(e.clientX,e.clientY)
      poss = pos::poss
      console.log(poss.size) 
      dots.appendChild(circle(cx:=pos.x, cy:=pos.y, r:=4, fill:="black").render)
      }
    },dots)


  def toggle(): Unit = {
    c() = if (c.now == "blue") "green" else "blue"
  }

  override def main(): Unit = {
    document.body.appendChild(
      div(
        color := c,
        "It es", span(c, onclick := toggle _), "text! :)",canv
      ).render
    )
  }
}
