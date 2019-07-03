package de.htwg.se.roguelike.model.levelComponent.levelBaseImpl

import com.google.inject.Inject

case class Land[T] (map: Vector[Vector[T]]) {
  def this(sizeY: Int, sizeX: Int, filling: T) = this(Vector.tabulate(sizeY, sizeX) { (row, col) => filling })

  val sizeX: Int = map.size
  val sizeY: Int = map(0).size


  def tile(col: Int, row: Int): T = map(col)(row)

  def replaceTile(col: Int, row: Int, tile: T): Land[T] = copy(map.updated(col, map(col).updated(row, tile)))
}
