package de.htwg.se.roguelike.model

case class Land[T] (map:Vector[Vector[T]]) {
  def this(size:Int, filling:T) = this(Vector.tabulate(size, size){(row, col) => filling})

  val size:Int = map.size

  def tile(row:Int, col:Int):T = map(row)(col)

  def replaceTile(row:Int,col:Int,tile:T):Land[T] = copy(map.updated(row, map(row).updated(col, tile)))
}
