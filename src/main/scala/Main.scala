package example

import scala.scalajs.js
import org.scalajs.dom._
import scalatags.JsDom.all._
import scalatags.rx.all._
import rx._
import Math._

case class Vec(x:Double,y:Double){
  def -(that:Vec) = Vec(this.x-that.x, this.y-that.y)
  def dot(that:Vec) = this.x*that.x+this.y*that.y
  def length = sqrt(x*x+y*y) 
  def angle(that:Vec) = acos( this.dot(that) / (this.length * that.length))
}

object Main extends js.JSApp {
  implicit val ctx: Ctx.Owner = Ctx.Owner.safe()

  def tangents (poss:List[Vec])=poss.sliding(2).map{case List(a,b) => a-b}
  def angels (tangents:Iterator[Vec])=tangents.sliding(2).map{case List(a,b) => a angle b}
  def diffs (angles:Iterator[Double])=angles.sliding(2).map{case List(a,b) => a-b}
  def extreme (diffs:Iterator[Double])=diffs.sliding(2).zipWithIndex.collect{case (List(a,b),i) if signum(a)!=signum(b) && abs(a-b) > 0.5 => i}
  
  val poss:Var[List[Vec]]=Var(Nil)
  val extr = Var[List[Vec]](Nil)
    
  def calculateExtremes()() {
    val t = tangents(poss.now) 
    val a = angels(t)
    val d = diffs(a)
    val e = extreme(d)
    extr() = e.map(poss.now).toList
  }
  val reddots = {
    import scalatags.JsDom.svgAttrs._
    import scalatags.JsDom.svgTags._
    Rx(
      g(
        extr().map((posi:Vec) => circle(cx:=posi.x, cy:=posi.y, r:=4, fill:="red")).toSeq:_*
      ).render
    )
  }
  val blackdots = {
    import scalatags.JsDom.svgAttrs._
    import scalatags.JsDom.svgTags._
    g().render
  }
  val dots = { 
    import scalatags.JsDom.svgAttrs._
    import scalatags.JsDom.svgTags._
    svg(width:=500,height:=500,pointerEvents:="none",
      blackdots,
      reddots
      ).render
  }
  val canv = div(width:=500,height:=500,border:="1px solid black",
    onmousemove:={(e:MouseEvent)=>
      import scalatags.JsDom.svgAttrs._
      import scalatags.JsDom.svgTags._
      if (e.buttons == 1) {
      console.log(e)
      val pos = Vec(e.asInstanceOf[js.Dynamic].offsetX.asInstanceOf[Double],e.asInstanceOf[js.Dynamic].offsetY.asInstanceOf[Double])
      poss() = pos::poss.now
      console.log(poss.now.size) 
      blackdots.appendChild(circle(cx:=pos.x, cy:=pos.y, r:=4, fill:="black").render)
      }
    },
    onmouseup:={() => calculateExtremes()} 
    ,dots)



  override def main(): Unit = {
    document.body.appendChild(
      div(
        canv
      ).render
    )
  }
}
