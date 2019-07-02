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

    "cant buy items" in {
      controller.merchant = new Merchant(inventory = Vector())
      tui.state.processInputLine("0")
      controller.player.inventory.weapons.size should be(0)
    }

    "cant buy items with a wrong index" in {
      controller.merchant = new Merchant(inventory = Vector(Potion("random")))
      controller.buyItem(100)
      controller.player.inventory.weapons.size should be(0)
    }

    "cant buy items that are too expensive" in {
      var player2 = Player(gulden = 1,health = 75, name = "Player", posX = 5, posY = 5, inventory = new Inventory(Vector(), Vector(Potion("SmallHeal")), Vector()))
      val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
      val controller2 = new Controller(player = player2, enemies = enemies, level = new Level(10, 10))
      val tui = new Tui(controller2)
      tui.state = new tuiMerchant(controller2, tui)
      controller2.merchant = new Merchant(inventory = Vector(Weapon("random")))
      tui.state.processInputLine("0")
      controller.player.inventory.weapons.size should be(0)
    }

    "can buy a weapon" in {
      controller.merchant = new Merchant(inventory = Vector(Weapon("random")))
      tui.state.processInputLine("0")
      controller.player.inventory.weapons.size should be(1)
    }

    "can buy an armor" in {
      controller.merchant = new Merchant(inventory = Vector(Armor("random")))
      tui.state.processInputLine("0")
      controller.player.inventory.weapons.size should be(1)
    }

    "can buy a potion" in {
      controller.merchant = new Merchant(inventory = Vector(Potion("random")))
      tui.state.processInputLine("0")
      controller.player.inventory.weapons.size should be(1)
    }

    "cant sell items" in {
      controller.player = new Player(name = "Player", posX = 5, posY = 5,inventory = new Inventory(Vector(), Vector(), Vector()))
      controller.merchant = new Merchant(inventory = Vector())
      controller.sellItem(0)
      controller.merchant.inventory.size should be(0)
    }

    "cant sell items with a wrong index" in {
      controller.player = new Player(name = "Player", posX = 5, posY = 5,inventory = new Inventory(Vector(), Vector(), Vector()))
      controller.merchant = new Merchant(inventory = Vector())
      controller.sellItem(100)
      controller.merchant.inventory.size should be(0)
    }

    "cant sell items that are too expensive" in {
      controller.merchant = new Merchant(gulden = 1,inventory = Vector(Weapon("Sword")))
      controller.sellItem(0)
      controller.player.inventory.weapons.size should be(0)
    }

    "can sell a weapon" in {
      controller.player = new Player(name = "Player", posX = 5, posY = 5,inventory = new Inventory(Vector(Weapon("Sword")), Vector(), Vector()))
      controller.merchant = new Merchant(inventory = Vector())
      controller.sellItem(0)
      controller.merchant.inventory.size should be(1)
    }

    "can sell an armor" in {
      controller.player = new Player(name = "Player", posX = 5, posY = 5,inventory = new Inventory(Vector(), Vector(), Vector(Armor("Helmet"))))
      controller.merchant = new Merchant(inventory = Vector())
      controller.sellItem(0)
      controller.merchant.inventory.size should be(1)
    }

    "can sell a potion" in {
      controller.player = new Player(name = "Player", posX = 5, posY = 5,inventory = new Inventory(Vector(), Vector(Potion("SmallHeal")), Vector()))
      controller.merchant = new Merchant(inventory = Vector())
      controller.sellItem(0)
      controller.merchant.inventory.size should be(1)
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

    "do not restock merchant" in {
      var player2 = Player(gulden = 200,health = 75, name = "Player", posX = 5, posY = 5, inventory = new Inventory(Vector(), Vector(Potion("SmallHeal")), Vector()))
      val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
      val controller2 = new Controller(player = player2, enemies = enemies, level = new Level(10, 10))
      val tui = new Tui(controller2)
      tui.state = new tuiMerchant(controller2, tui)
      controller2.restock() should be (false)
    }

    "restock merchant" in {
      var player2 = Player(gulden = 1000,health = 75, name = "Player", posX = 5, posY = 5, inventory = new Inventory(Vector(), Vector(Potion("SmallHeal")), Vector()))
      val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
      val controller2 = new Controller(player = player2, enemies = enemies, level = new Level(10, 10))
      val tui = new Tui(controller2)
      tui.state = new tuiMerchant(controller2, tui)
      controller2.restock() should be (true)
    }

    "switch to inventory on input 'x'" in {
      val tuitest = tui.state
      tui.state.processInputLine("x")
      tui.state should not be (tuitest)
    }




  }
}
