package de.htwg.se.roguelike.controller

import de.htwg.se.roguelike.controller.controllerBaseImpl.Controller
import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl._
import org.scalatest.{Matchers, WordSpec}

class ControllerTest extends WordSpec with Matchers {

  "A Controller" when {
    "new" should {

      "be able to set SCALE" in {
        val controller: ControllerInterface = new Controller(level = new Level(9, 16), player = new Player("Test"))
        controller.setSCALE(10)
        controller.SCALE should be(10)
      }

      "be able to see interactions with enemies" in {
        val controller: ControllerInterface = new Controller(
          level = new Level(9, 16),
          player = new Player("Test",
            posX = 0,
            posY = 0), enemies = Vector(new Enemy("TestEnemy", posX = 0, posY = 0)))
        controller.interaction()
        controller.currentEnemy should be(new Enemy("TestEnemy", posX = 0, posY = 0))
        controller.gameStatus should be(GameStatus.FIGHT)
      }

      "be able to see interactions with portal" in {
        val controller: ControllerInterface = new Controller(
          level = new Level(9, 16),
          player = new Player("Test",
            posX = 0,
            posY = 0))
        controller.portal = new Portal(posX = 0, posY = 0)
        val oldDepth = controller.lvlDepth
        val oldLevel = controller.level
        controller.interaction()
        controller.portal should not be (new Portal(posX = 0, posY = 0))
        controller.lvlDepth should be(oldDepth + 1)
        controller.level should not be (oldLevel)

        val controller2: ControllerInterface = new Controller(
          level = new Level(9, 16),
          player = new Player("Test",
            posX = 0,
            posY = 0))
        controller2.portal = new Portal(posX = 0, posY = 0)
        controller2.lvlDepth = 10
        val oldDepth2 = controller2.lvlDepth
        val oldEnemy = controller.currentEnemy
        val oldLevel2 = controller.level
        controller2.interaction()
        controller2.portal should not be (new Portal(posX = 0, posY = 0))
        controller2.lvlDepth should be(oldDepth2 + 1)
        controller2.bossfight should be(true)
        controller2.currentEnemy should not be (oldEnemy)
        controller2.gameStatus should be(GameStatus.FIGHT)
        controller2.level should not be (oldLevel2)
      }

      "be able to see interactions with merchant" in {
        val controller: ControllerInterface = new Controller(
          level = new Level(9, 16),
          player = new Player("Test",
            posX = 0,
            posY = 0))
        controller.merchant = new Merchant(posX = 0, posY = 0)
        controller.interaction()
        controller.gameStatus should be(GameStatus.MERCHANT)
      }

      "be able to see interactions with crate" in {
        val controller: ControllerInterface = new Controller(
          level = new Level(9, 16),
          player = new Player("Test",
            posX = 0,
            posY = 0))
        controller.crate = new Crate(posX = 0, posY = 0)
        controller.interaction()
        controller.gameStatus should be(GameStatus.CRATE)
      }

      "be able to check for lvlUp" in {
        val controller: ControllerInterface = new Controller(
          level = new Level(9, 16),
          player = new Player("Test",
            posX = 0,
            posY = 0, exp = 1000, maxExp = 100))
        val oldPlayerLvl = controller.player.lvl
        controller.checkLvlUp()
        controller.player.lvl should be(oldPlayerLvl + 1)
        controller.gameStatus should be(GameStatus.PLAYERLEVELUP)
      }

      "be able to let the Player lootAll() Enemy Items" in {
        val controller: ControllerInterface = new Controller(
          level = new Level(9, 16),
          player = new Player("Test",
            posX = 0,
            posY = 0,
            inventory = new Inventory(Vector(), Vector(), Vector())),
          enemies = Vector(new Enemy(name = "TestEnemy",
            inventory = new Inventory(Vector(Weapon("random")),
              Vector(Potion("random")),
              Vector(Armor("random"))))))

        controller.currentEnemy = controller.enemies(0)
        controller.enemyLoot = controller.currentEnemy.inventory.weapons
        controller.enemyLoot = controller.enemyLoot ++ controller.currentEnemy.inventory.potions
        controller.enemyLoot = controller.enemyLoot ++ controller.currentEnemy.inventory.armor

        controller.lootAll()

        controller.player.inventory.armor.size should be(1)
        controller.player.inventory.potions.size should be(1)
        controller.player.inventory.weapons.size should be(1)
        controller.enemyLoot.size should be(0)
        controller.gameStatus should be(GameStatus.LEVEL)
      }

      "be able to let the Player loot single Enemy Items" in {
        val controller: ControllerInterface = new Controller(
          level = new Level(9, 16),
          player = new Player("Test",
            posX = 0,
            posY = 0,
            inventory = new Inventory(Vector(), Vector(), Vector())),
          enemies = Vector(new Enemy(name = "TestEnemy",
            inventory = new Inventory(Vector(Weapon("random")),
              Vector(Potion("random")),
              Vector(Armor("random"))))))

        controller.currentEnemy = controller.enemies(0)
        controller.enemyLoot = controller.currentEnemy.inventory.weapons
        controller.enemyLoot = controller.enemyLoot ++ controller.currentEnemy.inventory.potions
        controller.enemyLoot = controller.enemyLoot ++ controller.currentEnemy.inventory.armor

        controller.lootingEnemy(3)
        controller.enemyLoot.size should be(2)
        controller.lootingEnemy(5)
        controller.enemyLoot.size should be(2)
        controller.lootingEnemy(2)
        controller.enemyLoot.size should be(1)
        controller.lootingEnemy(1)
        controller.enemyLoot.size should be(0)
        controller.player.inventory.armor.size should be(1)
        controller.player.inventory.potions.size should be(1)
        controller.player.inventory.weapons.size should be(1)
        controller.gameStatus should be(GameStatus.LEVEL)
      }


      "be able to let the Player lootAllCrate() Items" in {
        val controller: ControllerInterface = new Controller(
          level = new Level(9, 16),
          player = new Player("Test",
            posX = 0,
            posY = 0,
            inventory = new Inventory(Vector(), Vector(), Vector())))

        controller.crate = new Crate(inventory = Vector(Weapon("random")
          , Potion("random")
          , Armor("random")))

        controller.lootAllCrate()
        controller.crate.posX should be(-1)
        controller.crate.posY should be(-1)
        controller.gameStatus should be(GameStatus.LEVEL)
        controller.player.inventory.armor.size should be(1)
        controller.player.inventory.potions.size should be(1)
        controller.player.inventory.weapons.size should be(1)
      }

      "be able to let the Player loot single items from crate" in {
        val controller: ControllerInterface = new Controller(
          level = new Level(9, 16),
          player = new Player("Test",
            posX = 0,
            posY = 0,
            inventory = new Inventory(Vector(), Vector(), Vector())))

        controller.crate = new Crate(inventory = Vector(Weapon("random")
          , Potion("random")
          , Armor("random")))

        controller.lootingCrate(1)
        controller.crate.inventory.size should be(2)
        controller.lootingCrate(5)
        controller.crate.inventory.size should be(2)
        controller.lootingCrate(2)
        controller.crate.inventory.size should be(1)
        controller.lootingCrate(1)
        controller.crate.inventory.size should be(0)
        controller.crate.posX should be(-1)
        controller.crate.posY should be(-1)
        controller.gameStatus should be(GameStatus.LEVEL)
        controller.player.inventory.armor.size should be(1)
        controller.player.inventory.potions.size should be(1)
        controller.player.inventory.weapons.size should be(1)
      }

    }
  }
}
