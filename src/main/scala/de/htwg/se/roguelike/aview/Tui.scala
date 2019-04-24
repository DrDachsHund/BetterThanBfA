package de.htwg.se.roguelike.aview

import de.htwg.se.roguelike.model.Level

class Tui {

  def processInputLine(input: String, level:Level):Level = {
    input match {
      case "q" => level
      case "n"=> level
      case "r" => level
      case "s" => level
      case _ => {
        print("DXDXXDXDDXDX")
        level
        }
      }
    }
}