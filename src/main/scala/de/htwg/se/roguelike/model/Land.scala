package de.htwg.se.roguelike.model

case class Land[T] (map:Vector[Vector[T]]) {
  def this(size:Int, filling:T) = this(Vector.tabulate(size, size){(row, col) => filling})

  val size:Int = map.size

  def tile(col:Int, row:Int):T = map(col)(row)

  def replaceTile(col:Int,row:Int,tile:T):Land[T] = copy(map.updated(col, map(col).updated(row, tile)))
}
