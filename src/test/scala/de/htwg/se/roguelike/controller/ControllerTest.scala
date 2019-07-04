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
        var enemies = Vector(new Enemy("TestEnemy", posX = 0, posY = 0))
        val controller: ControllerInterface = new Controller(
          level = new Level(9, 16),
          player = new Player("Test",
            posX = 0,
            posY = 0))
        controller.enemies = enemies
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
        var enemies = Vector(new Enemy(name = "TestEnemy",
          inventory = new Inventory(Vector(Weapon("random")),
            Vector(Potion("random")),
            Vector(Armor("random")))))
        val controller: ControllerInterface = new Controller(
          level = new Level(9, 16),
          player = new Player("Test",
            posX = 0,
            posY = 0,
            inventory = new Inventory(Vector(), Vector(), Vector())))
        controller.enemies = enemies

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
        var enemies = Vector(new Enemy(name = "TestEnemy",
          inventory = new Inventory(Vector(Weapon("random")),
            Vector(Potion("random")),
            Vector(Armor("random")))))
        val controller: ControllerInterface = new Controller(
          level = new Level(9, 16),
          player = new Player("Test",
            posX = 0,
            posY = 0,
            inventory = new Inventory(Vector(), Vector(), Vector())))
        controller.enemies = enemies

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

      "be able to let the enemy think" in {
        val controller: ControllerInterface = new Controller(
          level = new Level(9, 16),
          player = new Player("Test",
            posX = 0,
            posY = 0))

        controller.currentEnemy = new Enemy("TestEnemy", health = 1)
        controller.enemyThinking("") should be("WRONG PLAYER MOVE")
        controller.enemyThinking("attack") should be("block")

        controller.currentEnemy = new Enemy("TestEnemy", health = 20)
        controller.player = new Player("Test")
        while (controller.enemyThinking("attack") != "block") {
          controller.currentEnemy = new Enemy("TestEnemy", health = 20)
          controller.player = new Player("Test")
        }
        controller.player.health should be(100)
        controller.currentEnemy.health should be(6)

        controller.currentEnemy = new Enemy(name = "TestEnemy", health = 20, lvl = 1, inventory = new Inventory(potions = Vector(Potion("random"))))
        while (controller.enemyThinking("attack") != "heal") {
          controller.currentEnemy = new Enemy(name = "TestEnemy", health = 20, lvl = 1, inventory = new Inventory(potions = Vector(Potion("random"))))
          controller.player = new Player("Test")
        }
        controller.currentEnemy.health should be(45 - 17) //eHealth - pAttack

        controller.currentEnemy = new Enemy(name = "TestEnemy", health = 20, lvl = 1, inventory = new Inventory(potions = Vector(Potion("random"))))
        controller.player = new Player("Test")
        while (controller.enemyThinking("attack") != "attack") {
          controller.currentEnemy = new Enemy(name = "TestEnemy", health = 20, lvl = 1, inventory = new Inventory(potions = Vector(Potion("random"))))
          controller.player = new Player("Test")
        }
        controller.player.health should be(85)

        controller.currentEnemy = new Enemy("TestEnemy", health = 10000)
        controller.player = new Player("Test")
        while (controller.enemyThinking("attack") != "attack") {
          controller.currentEnemy = new Enemy("TestEnemy", health = 10000)
          controller.player = new Player("Test")
        }
        controller.player.health should be(85)


        controller.currentEnemy = new Enemy(name = "TestEnemy", health = 100, lvl = 1, inventory = new Inventory(potions = Vector(Potion("random"))))
        controller.player = new Player("Test")
        while (controller.enemyThinking("block") != "special") {
          controller.currentEnemy = new Enemy(name = "TestEnemy", health = 100, lvl = 1, inventory = new Inventory(potions = Vector(Potion("random"))))
          controller.player = new Player("Test")
        }
        controller.player.health should be(85)

        controller.currentEnemy = new Enemy(name = "TestEnemy", health = 100, lvl = 1, inventory = new Inventory(potions = Vector(Potion("random"))))
        controller.player = new Player("Test")
        while (controller.enemyThinking("block") != "heal") {
          controller.currentEnemy = new Enemy(name = "TestEnemy", health = 100, lvl = 1, inventory = new Inventory(potions = Vector(Potion("random"))))
          controller.player = new Player("Test")
        }
        controller.currentEnemy.health should be(125)

        controller.currentEnemy = new Enemy(name = "TestEnemy", health = 100, lvl = 1, inventory = new Inventory(potions = Vector(Potion("random"))))
        controller.player = new Player("Test")
        while (controller.enemyThinking("block") != "block") {
          controller.currentEnemy = new Enemy(name = "TestEnemy", health = 100, lvl = 1, inventory = new Inventory(potions = Vector(Potion("random"))))
          controller.player = new Player("Test")
        }
        controller.player.health should be(100)
        controller.currentEnemy.health should be(100)

        controller.currentEnemy = new Enemy(name = "TestEnemy", health = 100, lvl = 1, inventory = new Inventory(potions = Vector(Potion("random"))))
        controller.player = new Player("Test")
        while (controller.enemyThinking("block") != "attack") {
          controller.currentEnemy = new Enemy(name = "TestEnemy", health = 100, lvl = 1, inventory = new Inventory(potions = Vector(Potion("random"))))
          controller.player = new Player("Test")
        }
        controller.player.health should be(87)
        controller.currentEnemy.health should be(100)

        controller.currentEnemy = new Enemy(name = "TestEnemy", health = 100, lvl = 1)
        controller.player = new Player("Test")
        while (controller.enemyThinking("block") != "attack") {
          controller.currentEnemy = new Enemy(name = "TestEnemy", health = 100, lvl = 1)
          controller.player = new Player("Test")
        }
        controller.player.health should be(87)
        controller.currentEnemy.health should be(100)

        controller.currentEnemy = new Enemy(name = "TestEnemy", health = 100, lvl = 1)
        controller.player = new Player("Test")
        while (controller.enemyThinking("block") != "special") {
          controller.currentEnemy = new Enemy(name = "TestEnemy", health = 100, lvl = 1)
          controller.player = new Player("Test")
        }
        controller.player.health should be(85)
        controller.currentEnemy.health should be(100)


        controller.currentEnemy = new Enemy(name = "TestEnemy", health = 100, lvl = 1)
        controller.player = new Player("Test")
        while (controller.enemyThinking("special") != "special") {
          controller.currentEnemy = new Enemy(name = "TestEnemy", health = 100, lvl = 1)
          controller.player = new Player("Test")
        }
        controller.player.health should be(85)
        controller.currentEnemy.health should be(83)

        controller.currentEnemy = new Enemy(name = "TestEnemy", health = 100, lvl = 1, mana = 0, maxMana = 0)
        controller.player = new Player("Test")
        while (controller.enemyThinking("special") != "special") {
          controller.currentEnemy = new Enemy(name = "TestEnemy", health = 100, lvl = 1, mana = 0, maxMana = 0)
          controller.player = new Player("Test")
        }
        controller.currentEnemy.mana should be(0)

        controller.currentEnemy = new Enemy(name = "TestEnemy", health = 100, lvl = 1)
        controller.player = new Player("Test")
        while (controller.enemyThinking("special") != "attack") {
          controller.currentEnemy = new Enemy(name = "TestEnemy", health = 100, lvl = 1)
          controller.player = new Player("Test")
        }
        controller.player.health should be(85)
        controller.currentEnemy.health should be(83)

        controller.enemies = Vector(new Enemy("TEST"), new Enemy("TEST"), new Enemy("TEST"), new Enemy("TEST"), new Enemy("TEST"))
        controller.currentEnemy = new Enemy(name = "TestEnemy", health = 1, lvl = 1)
        controller.player = new Player("Test", exp = 1000)
        while (controller.enemyThinking("special") != "attack") {
          controller.currentEnemy = new Enemy(name = "TestEnemy", health = 1, lvl = 1)
          controller.player = new Player("Test", exp = 1000)
        }
        controller.portal.posX should not be (-1)
        controller.portal.posY should not be (-1)
        controller.gameStatus should be(GameStatus.PLAYERLEVELUP)


        controller.currentEnemy = new Enemy(name = "TestEnemy", health = 100, lvl = 1)
        controller.player = new Player("Test")
        while (controller.enemyThinking("special") != "block") {
          controller.currentEnemy = new Enemy(name = "TestEnemy", health = 100, lvl = 1)
          controller.player = new Player("Test")
        }
        controller.player.health should be(100)
        controller.currentEnemy.health should be(83)

        controller.currentEnemy = new Enemy(name = "TestEnemy", health = 100, lvl = 1)
        controller.player = new Player("Test", health = 0)
        while (controller.enemyThinking("special") != "attack") {
          controller.currentEnemy = new Enemy(name = "TestEnemy", health = 100, lvl = 1)
          controller.player = new Player("Test", health = 0)
        }
        controller.playerLastAction should be("")
        controller.enemyLastAction should be("")
        controller.bossfight should be(false)
        controller.gameStatus should be(GameStatus.GAMEOVER)

      }

      "be able to create different portel in level depth 11 " in {
        val controller: ControllerInterface = new Controller(
          level = new Level(9, 16),
          player = new Player("Test",
            posX = 0,
            posY = 0))
        controller.lvlDepth = 11
        controller.createPortal()
        controller.portal.portalType should be(0)
      }

      "be able to set the merchant properly" in {
        val controller: ControllerInterface = new Controller(
          level = new Level(9, 16),
          player = new Player("Test",
            posX = 0,
            posY = 0))
        controller.lvlDepth = 0
        controller.createMerchant()
        controller.merchant.posX should be(-1)
        controller.merchant.posY should be(-1)
      }

      "be able to give a string from lootCrate" in {
        val controller: ControllerInterface = new Controller(
          level = new Level(9, 16),
          player = new Player("Test",
            posX = 0,
            posY = 0))
        controller.crate = new Crate(inventory = Vector(Weapon("random")))
        controller.setGameStatus(GameStatus.CRATE)
        controller.strategy.updateToString should not be ("")

        controller.enemyLoot = Vector(Weapon("random"))
        controller.setGameStatus(GameStatus.LOOTENEMY)
        controller.strategy.updateToString should not be ("")
      }

      "be able to see if player has potion" in {
        val controller: ControllerInterface = new Controller(
          level = new Level(9, 16),
          player = new Player("Test",
            posX = 0,
            posY = 0, inventory = new Inventory(Vector(), Vector(), Vector())))
        controller.usePotion(1)
        controller.player.inventory.potions.size should be(0)
        controller.player = new Player("Test",
          posX = 0,
          posY = 0, inventory = new Inventory(Vector(), Vector(Potion("random")), Vector()))
        controller.usePotion(-1)
        controller.player.inventory.potions.size should be(1)
      }

      "be able to se if player has armor" in {
        val controller: ControllerInterface = new Controller(
          level = new Level(9, 16),
          player = new Player("Test",
            posX = 0,
            posY = 0, inventory = new Inventory(Vector(), Vector(), Vector())))
        controller.equipArmor(-1)
        controller.player.inventory.armor.size should be(0)
        controller.equipArmor(1)
        controller.player.inventory.armor.size should be(0)
        controller.player = new Player("Test",
          posX = 0,
          posY = 0, inventory = new Inventory(Vector(), Vector(), Vector(Armor("Helmet"))),
          helmet = Armor("Helmet"))
        controller.equipArmor(1)
        controller.player.inventory.armor.size should be(1)
      }

      "be able to detect when player weres no armor or weapon" in {
        val controller: ControllerInterface = new Controller(
          level = new Level(9, 16),
          player = new Player("Test",
            posX = 0,
            posY = 0, inventory = new Inventory(Vector(), Vector(), Vector())))
        controller.unEquipHelmet()
        controller.unEquipChest()
        controller.unEquipBoots()
        controller.unEquipPants()
        controller.unEquipGloves()
        controller.unEquipLeftHand()
        controller.unEquipRightHand()
        controller.player.inventory.weapons.size should be(0)
        controller.player.inventory.armor.size should be(0)
      }

      "be able to only equip weapon when something to equip" in {
        val controller: ControllerInterface = new Controller(
          level = new Level(9, 16),
          player = new Player("Test",
            posX = 0,
            posY = 0, inventory = new Inventory(Vector(), Vector(), Vector())))
        controller.equipWeapon(0, 0)
        controller.player.inventory.weapons.size should be(0)

        controller.player = new Player("Test",
          posX = 0,
          posY = 0, inventory = new Inventory(Vector(Weapon("random"), Weapon("random"), Weapon("random"), Weapon("random")), Vector(), Vector()))
        controller.equipWeapon(0, 1)
        controller.equipWeapon(0, 1)
        controller.player.inventory.weapons.size should be(3)
        controller.equipWeapon(1, 1)
        controller.equipWeapon(1, 1)
        controller.player.inventory.weapons.size should be(2)
        controller.equipWeapon(0, -1)
        controller.player.inventory.weapons.size should be(2)

      }

      "be able to sell items to the merchant when he has enough money" in {
        val controller: ControllerInterface = new Controller(
          level = new Level(9, 16),
          player = new Player("Test",
            posX = 0,
            posY = 0, inventory = new Inventory(Vector(Weapon("Sword"), Weapon("Sword")), Vector(Potion("random")), Vector(Armor("random")))))

        controller.merchant = new Merchant(gulden = 0, inventory = Vector(Weapon("Sword"), Potion("random"), Armor("random")))
        controller.sellItem(0)
        controller.player.inventory.weapons.size should be(2)
        controller.sellItem(-1)
        controller.player.inventory.weapons.size should be(2)
        controller.merchant = controller.merchant.nextMerchant(gulden = 10000)
        val test = controller.inventoryAsOneVector().size
        controller.sellItem(0)
        controller.inventoryAsOneVector().size should be(test - 1)


      }

    }
  }
}
