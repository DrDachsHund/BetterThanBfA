package de.htwg.se.roguelike.util

trait Command {

  def doStep:Unit
  def undoStep:Unit
  def redoStep:Unit

}
