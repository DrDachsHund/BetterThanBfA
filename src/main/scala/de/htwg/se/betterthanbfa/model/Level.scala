package de.htwg.se.betterthanbfa.model

case class Level(size:Int) {

  val map = Array.ofDim[Int](size,size)

  def update(x:Int,y:Int,e:Int) = {
    map(x)(y) = e
  }

  override def toString:String = {
    var s:String = ""
    for (i <- map) {
      for (x <- i) {
        s += " " + x
      }
      s += "\n"
    }
    s
  }

}
