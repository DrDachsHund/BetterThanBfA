package de.htwg.se.betterthanbfa.aview

import de.htwg.se.betterthanbfa.model._

class Tui {

  def InputLine(input: String): Unit = {
    input match {
      case "c" => new Player("Test")
      case "cl" => new Level(10)
      //case "k" =>   ...
      //case "e" => new Enemy()
      //case "q" => exit
    }

  }

}
