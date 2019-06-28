package de.htwg.se.roguelike.model.levelComponent.levelBaseImpl

case class Tile(value: Int) {
  def isSet: Boolean = value != 0
}
