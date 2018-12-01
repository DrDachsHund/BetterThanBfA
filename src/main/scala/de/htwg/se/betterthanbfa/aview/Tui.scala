package de.htwg.se.betterthanbfa.aview

import de.htwg.se.betterthanbfa.model.LevelComponent.Level
import de.htwg.se.betterthanbfa.model._
import de.htwg.se.betterthanbfa.model.playerComponent.Player

class Tui {
  def processInputLine(input: String, level: Level): Unit = {
    input match {
      case "n" => level.createLevel(10)
      case default => level
    }
  }


}
