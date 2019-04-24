package de.htwg.se.roguelike.model

case class Level(map: Land[Tile]) {
  def this(size: Int) = this(new Land[Tile](size, Tile(0)))

  override def toString: String = {
    val sb = new StringBuilder
    for (x <- 0 to map.size-1) {
      for (y <- 0 to map.size - 1) {
        sb++=(map.tile(x,y).value + " ")
      }
      sb++=("\n")
    }
    sb.toString
  }
}
