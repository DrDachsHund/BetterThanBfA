package de.htwg.se.roguelike.model

case class Tile(value:Int) {
  def isSet = value != 0
}
