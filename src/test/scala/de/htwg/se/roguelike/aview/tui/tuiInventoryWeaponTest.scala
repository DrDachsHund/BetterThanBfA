package de.htwg.se.roguelike.aview.tui

import de.htwg.se.roguelike.controller.{Controller, GameStatus}
import de.htwg.se.roguelike.model._
import org.scalatest.{Matchers, WordSpec}

class tuiInventoryWeaponTest extends WordSpec with Matchers {

  "A Rogue-Like Tui with state tuiLevel" should {
    val player = Player(name = "Player", posX = 5, posY = 5,rightHand = Weapon("Sword"),leftHand = Weapon("Sword"))
    val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
    val controller = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
    val tui = new Tui(controller)
    tui.state = new tuiInventoryWeapon(controller, tui)

    "on input R" in {
      tui.state.processInputLine("R")
      controller.player.rightHand should be(Weapon("rightFist"))
      controller.player.inventory.weapons.size should be(1)
    }

    "on input L" in {
      tui.state.processInputLine("L")
      controller.player.leftHand should be(Weapon("leftFist"))
      controller.player.inventory.weapons.size should be(2)
    }

    "equip on input number of index 01 and 10" in {
      val weapon = controller.player.inventory.weapons(0)
      tui.state.processInputLine("01")
      controller.player.rightHand should be(weapon)

      val oldSize = controller.player.inventory.weapons.size
      tui.state.processInputLine("9999999")
      controller.player.inventory.weapons.size should be(oldSize)

      val weapon2 = controller.player.inventory.weapons(0)
      tui.state.processInputLine("11")
      controller.player.leftHand should be(weapon2)
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

    "switch to InventoryWeapon" in {
      val controller3 = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui3 = new Tui(controller3)
      tui3.state = new tuiInventoryWeapon(controller3,tui3)
      val tui3Test = tui3.state
      controller3.setGameStatus(GameStatus.INVENTORYWEAPON)
      tui3.state should be (tui3Test)
    }

  }

}
