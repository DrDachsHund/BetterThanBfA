package de.htwg.se.roguelike.aview

import de.htwg.se.roguelike.controller.Controller
import de.htwg.se.roguelike.model.{Enemy, Level, LevelCreator, Player}
import org.scalatest.{Matchers, WordSpec}

/*class TuiMainTest extends WordSpec with Matchers{

  "A Rogue-Like Tui" should {
    val player = new Player(name = "Player",posX = 5, posY = 5)
    val enemies = Vector(new Enemy(name = "TestE1",posX = 0, posY = 0), new Enemy(name = "TestE2",posX = 1, posY = 0), new Enemy(name = "TestE3",posX = 0, posY = 1))
    val controller = new Controller(player = player, enemies = enemies ,level = new Level(10))
    val tui = new TuiMain(controller)
    "do nothing  on input 'q'" in {
      tui.processInputLine("q")
    }
    "create a Level on input 'n'" in {
      tui.processInputLine("n")
      controller.level should be(new LevelCreator(10).createLevel(player,enemies))
    }
    "create a random Level on input 'r'" in {
      tui.processInputLine("r")
      controller.level.map.size should be(10)
      controller.enemies.size should be(10)
    }
    "find interactions with input i" in {
      tui.processInputLine("i")
      controller.interaction should be(false)
    }
    "move up with input 'w'" in {
      val old = controller.player.posY
      tui.processInputLine("w")
      controller.player.posY should be(old-1)
    }
    "find interactions with input 'a'" in {
      val old = controller.player.posX
      tui.processInputLine("a")
      controller.player.posX should be(old-1)
    }
    "find interactions with input 's'" in {
      val old = controller.player.posY
      tui.processInputLine("s")
      controller.player.posY should be(old+1)
    }
    "find interactions with input 'd'" in {
      val old = controller.player.posX
      tui.processInputLine("d")
      controller.player.posX should be(old+1)
    }

    "do nothing on bad input like'99999'" in {
      val old = controller.levelToString
      tui.processInputLine("99999")
      controller.levelToString should be(old)
    }
  }
}*/

