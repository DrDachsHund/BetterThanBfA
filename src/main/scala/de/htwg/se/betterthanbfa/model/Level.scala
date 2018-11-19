package de.htwg.se.betterthanbfa.model

case class Level(size:Int) {

    val map = new Array[Int](size)

    def initArray = {
      var i = 0;
      while(i < map.length) {
        if(i != 0) {
          map.update(i,0)
        } else {
          map.update(i,1)
        }
        i += 1
      }
    }


  override def toString:String = {
    var s:String = ""
    for (i <- map) {
      s += " " + i
    }
    s
  }

}
