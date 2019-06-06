package de.htwg.se.roguelike.controller

import de.htwg.se.roguelike.model._
import de.htwg.se.roguelike.util.Observer
import org.scalatest.{Matchers, WordSpec}
/*
class ControllerTest extends WordSpec with Matchers {

  "A Controller" when {
    "observed by an Observer" should {
      val smallLevel = new Level(10)
      val player1 = Player("New Player", posX = 5, posY = 5)
      val enemies = Vector(Enemy("TestEnemy", posX = 5, posY = 6), Enemy("TestEnemy2", posX = 7, posY = 8))
      val enemies1 = Vector(Enemy("TestEnemy", posX = 5, posY = 5), Enemy("TestEnemy2", posX = 7, posY = 8))
      val controller = new Controller(smallLevel, player1, enemies)
      val controller1 = new Controller(smallLevel, player1, enemies1)
      val observer = new Observer {
        var updated: Boolean = false

        def isUpdated: Boolean = updated

        override def update(): Unit = {
          updated = true
          updated
        }
      }
      //controller.add(observer)

      "notify its Observer after creating Level" in {
        controller.createLevel()
        observer.updated should be(true)
        controller.level.map.size should be(10)
      }

      "notify its Observer after not interacting" in {
        controller.interaction()
        observer.updated should be(true)
        controller.moveDown()
        //controller.interaction should be (true)
      }

      "notify its Observer after creating a random Level" in {
        controller.createRandomLevel()
        observer.updated should be(true)
        controller.level.map.size should be(10)
        controller.enemies.size should be(10)
      }

      "notify its Observer after moving Up" in {
        val old = controller.player.posY
        controller.moveUp()
        observer.updated should be(true)
        controller.player.posX should be(5)
        controller.player.posY should be(old - 1)
      }


      "notify its Observer after moving Down" in {
        val old = controller.player.posY
        controller.moveDown()
        observer.updated should be(true)
        controller.player.posX should be(5)
        controller.player.posY should be(old + 1)
      }

      "notify its Observer after moving Right" in {
        val old = controller.player.posX
        controller.moveRight()
        observer.updated should be(true)
        controller.player.posX should be(old + 1)
        controller.player.posY should be(6)
      }

      "notify its Observer after moving Left" in {
        val old = controller.player.posX
        controller.moveLeft()
        observer.updated should be(true)
        controller.player.posX should be(old - 1)
        controller.player.posY should be(6)
      }
      "notify its Observer after atack" in {
        controller1.interaction()
        controller1.gameStatus should be(GameStatus.FIGHT)
        controller1.attack()
        observer.updated should be(true)
      }
      "change game status when player dies" in {
        controller1.player = controller.player.copy(health = 1, posX = 5, posY = 5)
        controller1.currentEnemy = Enemy("TestEnemy", posX = 5, posY = 5)
        controller1.attack()
        controller1.gameStatus should be(GameStatus.GAMEOVER)
      }

      "change game status when enemy dies" in {
        controller1.player = controller.player.copy(health = 100, posX = 5, posY = 5)
        controller1.currentEnemy = Enemy("TestEnemy", health = 1, posX = 5, posY = 5)
        controller1.attack()
        controller1.gameStatus should be(GameStatus.LOOTENEMY)
      }



      //String Abfrage------
      "updateToString" when {
        "gameStatus is LEVEL" in {
          controller.setGameStatus(GameStatus.LEVEL)
          controller.strategy.updateToString should be(controller.level.toString)
        }

        "gameStatus is FIGHT" in {
          controller.setGameStatus(GameStatus.FIGHT)
          controller.strategy.updateToString should be(controller.fight.toString + "[i]Inventory\n[r]:Run\n")
        }

        "gameStatus is FIGHTSTATUS" in {
          controller.player = controller.player.copy(health = 1, posX = 5, posY = 5)
          controller.enemies = Vector(Enemy("TestEnemy", posX = 5, posY = 5))
          controller.setGameStatus(GameStatus.FIGHTSTATUS)
          controller.strategy.updateToString should be(controller.fightStatus)
        }

        "gameStatus is GAMEOVER" in {
          controller.setGameStatus(GameStatus.GAMEOVER)
          controller.strategy.updateToString should be("GAME OVER" + controller.player.getScore(controller.lvlDepth) + "\n[n]New Game\n[q]Quit\n")
        }

        "gameStatus is INVENTORY" in {
          controller.setGameStatus(GameStatus.INVENTORY)
          controller.strategy.updateToString should be(
            "L or R to remove Weapon\n" +
              controller.player.helmet.name + ": " + controller.player.helmet.armor + "\n" +
              controller.player.chest.name + ": " + controller.player.chest.armor + "\n" +
              controller.player.pants.name + ": " + controller.player.pants.armor + "\n" +
              controller.player.boots.name + ": " + controller.player.boots.armor + "\n" +
              controller.player.gloves.name + ": " + controller.player.gloves.armor + "\n" +
              controller.player.rightHand.name + ": " + controller.player.rightHand.dmg + "\n" +
              controller.player.leftHand.name + ": " + controller.player.leftHand.dmg + "\n" +
              "[1]Potions\n" +
              "[2]Weapons\n" +
              "[3]Armor\n" +
              "[x]Back\n")
        }

        "gameStatus is INVENTORYPOTION" in {
          controller.setGameStatus(GameStatus.INVENTORYPOTION)
          controller.strategy.updateToString should be(
            "Player Health: <" + controller.player.health + "/" + controller.player.maxHealth + ">\n" +
              "Player Mana: <" + controller.player.mana + "/" + controller.player.maxMana + ">\n" +
              controller.player.inventory.potionsToString + "[x]Back\n")
        }

        "gameStatus is INVENTORYWEAPON" in {
          controller.setGameStatus(GameStatus.INVENTORYWEAPON)
          controller.strategy.updateToString should be(
            "[Hand(0,1)][WeaponIndex]\n" +
              controller.player.rightHand.toString + "\n" +
              controller.player.leftHand.toString + "\n" +
              controller.player.inventory.weaponsToString + "[x]Back\n"
          )
        }

        "gameStatus is INVENTORYARMOR" in {
          controller.setGameStatus(GameStatus.INVENTORYARMOR)
          controller.strategy.updateToString should be(
            controller.player.helmet.name + ": " + controller.player.helmet.armor + "\n" +
              controller.player.chest.name + ": " + controller.player.chest.armor + "\n" +
              controller.player.pants.name + ": " + controller.player.pants.armor + "\n" +
              controller.player.boots.name + ": " + controller.player.boots.armor + "\n" +
              controller.player.gloves.name + ": " + controller.player.gloves.armor + "\n" +
              controller.player.inventory.armorToString + "[x]Back\n"
          )
        }
      }
      //String Abfrage------

      "when undo after move" in {
        controller.player = Player(name = "Test")
        controller.moveRight()
        controller.moveRight()
        controller.player.posX should be(2)
        controller.undo()
        controller.player.posX should be(1)
      }
      "when redo after undo" in {
        controller.redo()
        controller.player.posX should be(2)
      }

      "use Potion" when {
        "do nothing if no potion available" in {
          controller.player = Player(name = "Test", health = 10, inventory = Inventory(potions = Vector()))
          controller.usePotion(1)
          controller.player.health should be(10)
        }
        "do nothing if no potion index is out of bounds" in {
          controller.player = Player(name = "Test", health = 10, inventory = Inventory(potions = Vector(Potion("SmallHeal"))))
          controller.usePotion(1000)
          controller.player.health should be(10)
        }
        "heal player" in {
          controller.player = Player(name = "Test", health = 10, inventory = Inventory(potions = Vector(Potion("SmallHeal"))))
          controller.usePotion(1)
          controller.player.health should be(35)
        }
      }
    }
    "un-/equip armor" should {
      val smallLevel = new Level(10)
      val player = Player(name = "Test", inventory = Inventory(armor = Vector(Armor("Helmet"), Armor("Chest"), Armor("Pants"), Armor("Boots"), Armor("Gloves"), Armor("Gloves"))))
      val enemies = Vector()
      val controller = new Controller(smallLevel, player, enemies)
      "equip Helmet" in {
        controller.player.helmet should be(Armor("noHelmet"))
        controller.equipArmor(1)
        controller.player.helmet should be(Armor("Helmet"))
      }
      "equip Chest" in {
        controller.player.chest should be(Armor("noChest"))
        controller.equipArmor(1)
        controller.player.chest should be(Armor("Chest"))
      }
      "equip Pants" in {
        controller.player.pants should be(Armor("noPants"))
        controller.equipArmor(1)
        controller.player.pants should be(Armor("Pants"))
      }
      "equip Boots" in {
        controller.player.boots should be(Armor("noBoots"))
        controller.equipArmor(1)
        controller.player.boots should be(Armor("Boots"))
      }
      "equip Gloves" in {
        controller.player.gloves should be(Armor("noGloves"))
        controller.equipArmor(1)
        controller.player.gloves should be(Armor("Gloves"))
      }
      "replace equiped Armor" in {
        controller.player.gloves should be(Armor("Gloves"))
        controller.equipArmor(1)
        controller.player.gloves should be(Armor("Gloves"))
      }
      "do nothing when out of bound index" in {
        controller.player.gloves should be(Armor("Gloves"))
        controller.equipArmor(1000)
        controller.player.gloves should be(Armor("Gloves"))
      }
      "unequip Armor" in {
        controller.unEquipHelmet()
        controller.unEquipChest()
        controller.unEquipPants()
        controller.unEquipBoots()
        controller.unEquipGloves()
        controller.player.helmet should be(Armor("noHelmet"))
        controller.player.chest should be(Armor("noChest"))
        controller.player.pants should be(Armor("noPants"))
        controller.player.boots should be(Armor("noBoots"))
        controller.player.gloves should be(Armor("noGloves"))
      }
      "do nothing without armor" in {
        controller.player = Player(name = "Test", inventory = Inventory(armor = Vector()))
        controller.equipArmor(1)
      }
    }
    "un-/equip weapons" should {
      val smallLevel = new Level(10)
      val player = Player(name = "Test", inventory = Inventory(weapons = Vector(Weapon("Sword"), Weapon("Sword"), Weapon("Sword"), Weapon("Sword"))))
      val enemies = Vector()
      val controller = new Controller(smallLevel, player, enemies)

      "equip rightHand" in {
        controller.player.rightHand should be(Weapon("rightFist"))
        controller.equipWeapon(0, 1)
        controller.player.rightHand should be(Weapon("Sword"))
      }

      "equip leftHand" in {
        controller.player.leftHand should be(Weapon("leftFist"))
        controller.equipWeapon(1, 1)
        controller.player.leftHand should be(Weapon("Sword"))
      }

      "replace rightHand" in {
        controller.player.rightHand should be(Weapon("Sword"))
        controller.equipWeapon(0, 1)
        controller.player.rightHand should be(Weapon("Sword"))
      }

      "replace leftHand" in {
        controller.player.leftHand should be(Weapon("Sword"))
        controller.equipWeapon(1, 1)
        controller.player.leftHand should be(Weapon("Sword"))
      }

      "unequip rightHand" in {
        controller.player.rightHand should be(Weapon("Sword"))
        controller.unEquipRightHand()
        controller.player.rightHand should be(Weapon("rightFist"))
      }

      "dont unequip rightHand when nothing i hold" in {
        controller.player.rightHand should be(Weapon("rightFist"))
        controller.unEquipRightHand()
        controller.player.rightHand should be(Weapon("rightFist"))
      }

      "unequip leftHand" in {
        controller.player.leftHand should be(Weapon("Sword"))
        controller.unEquipLeftHand()
        controller.player.leftHand should be(Weapon("leftFist"))
      }

      "dont unequip leftHand when nothing i hold" in {
        controller.player.leftHand should be(Weapon("leftFist"))
        controller.unEquipLeftHand()
        controller.player.leftHand should be(Weapon("leftFist"))
      }

      "do nothing when incorect index" in {
        controller.player.leftHand should be(Weapon("leftFist"))
        controller.equipWeapon(1, 10000)
        controller.player.leftHand should be(Weapon("leftFist"))
      }

      "do nothing when no weapons avaible" in {
        controller.player = Player(name = "Test", inventory = Inventory(weapons = Vector()))
        controller.player.leftHand should be(Weapon("leftFist"))
        controller.equipWeapon(1, 1)
        controller.player.leftHand should be(Weapon("leftFist"))
      }
    }
  }
}
*/