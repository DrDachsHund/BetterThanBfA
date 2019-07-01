package de.htwg.se.roguelike.aview.tui

import de.htwg.se.roguelike.controller.GameStatus
import de.htwg.se.roguelike.controller.controllerBaseImpl.Controller
import de.htwg.se.roguelike.model._
import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl._
import org.scalatest.{Matchers, WordSpec}

class tuiMerchantTest extends WordSpec with Matchers {
  "A Rogue-Like Tui with state tuiInventoryPotion" should {
    val player = Player(gulden = 999999999,health = 75, name = "Player", posX = 5, posY = 5, inventory = new Inventory(Vector(), Vector(Potion("SmallHeal")), Vector()))
    val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
    val controller = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
    val tui = new Tui(controller)
    tui.state = new tuiMerchant(controller, tui)

    "can buy items" in {
      controller.merchant = new Merchant(inventory = Vector(Weapon("random")))
      tui.state.processInputLine("0")
      controller.player.inventory.weapons.size should be(1)
    }

    "dont switch wrong gamestatus" in {
      val tuitest = tui.state
      controller.setGameStatus(GameStatus.FIGHT)
      tui.state should be (tuitest)
    }

    "switch to Merchant" in {
      val tuitest = tui.state
      controller.setGameStatus(GameStatus.MERCHANT)
      tui.state should be (tuitest)
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



  }
}
