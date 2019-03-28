package de.htwg.se.roguelike

import de.htwg.se.roguelike.model.Player

object RogueLike {
  def main(args: Array[String]): Unit = {
    val student = Player("Roguelike")
    println("Hello, " + student.name)
  }
}
