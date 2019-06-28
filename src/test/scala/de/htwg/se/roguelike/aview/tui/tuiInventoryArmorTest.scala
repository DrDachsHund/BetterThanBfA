package de.htwg.se.roguelike.aview.tui

import de.htwg.se.roguelike.controller.GameStatus
import de.htwg.se.roguelike.controller.controllerBaseImpl.Controller
import de.htwg.se.roguelike.model._
import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl.{Armor, Enemy, Level, Player}
import org.scalatest.{Matchers, WordSpec}

class tuiInventoryArmorTest extends WordSpec with Matchers {

  "A Rogue-Like Tui with state tuInventoryArmor" should {
    val player = Player(name = "Player", posX = 5, posY = 5,
      helmet = Armor("Helmet"),
      chest = Armor("Chest"),
      pants = Armor("Pants"),
      boots = Armor("Boots"),
      gloves = Armor("Gloves"))
    val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
    val controller = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
    val tui = new Tui(controller)
    tui.state = new tuiInventoryArmor(controller, tui)

    "on input H" in {
      tui.state.processInputLine("H")
      controller.player.helmet should be(Armor("noHelmet"))
      controller.player.inventory.armor.size should be(1)
    }

    "on input C" in {
      tui.state.processInputLine("C")
      controller.player.chest should be(Armor("noChest"))
      controller.player.inventory.armor.size should be(2)
    }

    "on input P" in {
      tui.state.processInputLine("P")
      controller.player.pants should be(Armor("noPants"))
      controller.player.inventory.armor.size should be(3)
    }

    "on input B" in {
      tui.state.processInputLine("B")
      controller.player.boots should be(Armor("noBoots"))
      controller.player.inventory.armor.size should be(4)
    }

    "on input G" in {
      tui.state.processInputLine("G")
      controller.player.gloves should be(Armor("noGloves"))
      controller.player.inventory.armor.size should be(5)
    }

    "equip on input number of index 0" in {
      val armor = controller.player.inventory.armor(0)
      tui.state.processInputLine("1")
      controller.player.helmet should be(armor)

      val oldSize = controller.player.inventory.armor.size
      tui.state.processInputLine("9999999")
      controller.player.inventory.armor.size should be(oldSize)
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
      tui.state should not be(tuitest)
    }

    "switch to InventoryArmor" in {
      val controller3 = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui3 = new Tui(controller3)
      tui3.state = new tuiInventoryArmor(controller3,tui3)
      val tui3Test = tui3.state
      controller3.setGameStatus(GameStatus.INVENTORYARMOR)
      tui3.state should be (tui3Test)
    }


  }
}
