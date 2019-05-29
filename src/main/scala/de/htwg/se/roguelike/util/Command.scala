package de.htwg.se.roguelike.util

trait Command {

  def doStep(): Unit

  def undoStep(): Unit

  def redoStep(): Unit

}

class TestCommand() extends Command {

  var state: Int = 0

  override def doStep(): Unit = {
    state = state + 1
  }

  override def undoStep(): Unit = {
    state = state - 1
  }

  override def redoStep(): Unit = {
    state = state + 1
  }
}
