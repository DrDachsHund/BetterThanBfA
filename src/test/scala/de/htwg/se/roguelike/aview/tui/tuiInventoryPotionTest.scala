/*package de.htwg.se.roguelike.aview.tui

import de.htwg.se.roguelike.controller.GameStatus
import de.htwg.se.roguelike.controller.controllerBaseImpl.Controller
import de.htwg.se.roguelike.model._
import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl._
import org.scalatest.{Matchers, WordSpec}

class tuiInventoryPotionTest extends WordSpec with Matchers {

  "A Rogue-Like Tui with state tuiInventoryPotion" should {
    val player = Player(health = 75, name = "Player", posX = 5, posY = 5, inventory = new Inventory(Vector(), Vector(Potion("SmallHeal")), Vector()))
    val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
    val controller = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
    val tui = new Tui(controller)
    tui.state = new tuiInventoryPotion(controller, tui)

    "use on input number of index 0" in {
      tui.state.processInputLine("9999999")
      controller.player.inventory.potions.size should be(1)

      controller.player.health should be(75)
      tui.state.processInputLine("1")
      controller.player.health should be (100)
    }


    "do nothing when state equals startScreen on input 'q'" in {
      val old = controller.strategy.updateToString
      tui.state.processInputLine("q")
      controller.strategy.updateToString should be(old)
    }
    "do nothing on when state equals startScreen bad input like'abc'" in {
      val old = controller.strategy.updateToString
      tui.state.processInputLine("abc")
      controller.strategy.updateToString should be(old)
    }
    "switch to inventory on input 'x'" in {
      val tuitest = tui.state
      tui.state.processInputLine("x")
      tui.state should not be (tuitest)
    }

    "switch to InventoryPotion" in {
      val controller3 = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui3 = new Tui(controller3)
      tui3.state = new tuiInventoryPotion(controller3,tui3)
      val tui3Test = tui3.state
      controller3.setGameStatus(GameStatus.INVENTORYPOTION)
      tui3.state should be (tui3Test)
    }


  }
}
*/