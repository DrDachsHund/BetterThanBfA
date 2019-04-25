package de.htwg.se.roguelike

import de.htwg.se.roguelike.aview.Tui
import de.htwg.se.roguelike.model.{Enemy, Level, Player}

object RogueLike {

  var player = new Player(name = "Player",posX = 5, posY = 5)
  var enemies:List[Enemy] = List(new Enemy(name = "TestE1",posX = 0, posY = 0),
    new Enemy(name = "TestE2",posX = 1, posY = 0),
    new Enemy(name = "TestE3",posX = 0, posY = 1))
  var level = new Level(10)
  val tui = new Tui


  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      println("Level : \n" + level.toString)
      //print("Enemies: \n" + enemies + "\n")
      input = readLine()
      var tupel = tui.processInputLine(input, level, player, enemies)

      level = tupel._1
      player = tupel._2
      enemies = tupel._3

    } while (input != "q")
  }
}
