package de.htwg.se.roguelike.aview

import de.htwg.se.roguelike.util.Observable

trait Tui extends Observable with State {
  def processInputLine(input: String): Unit
}
