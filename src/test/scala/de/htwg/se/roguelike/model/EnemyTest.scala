package de.htwg.se.roguelike.model

//import org.scalatest.{Matchers, WordSpec}
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class EnemyTest extends WordSpec with Matchers {
  "A Enemy" when {
    "new" should {
      val enemy1 = Enemy()
      "have a name and stats" in {
        enemy1.name should be("Empty-Name")
        enemy1.health should be(100)
        enemy1.mana should be(100)
        enemy1.attack should be(10)
        enemy1.exp should be(20)
      }
      "when alive" in {
        enemy1.isAlive should be(true)
      }
      val enemy3 = enemy1.copy(health = 0)
      "when not alive" in {
        enemy3.isAlive should be(false)
      }
      "have a nice String representation" in {
        enemy1.toString should be("Name: " + "Empty-Name" +
          "\nhealth: " + "100" +
          "\nAttack: " + "10" +
          "\nExperience: " + "20" +
          "\nposX: " + "0" +
          "\nposY: " + "0")
      }

      "have a getAttack methode" in {
        enemy1.getAttack should be(enemy1.attack + enemy1.rightHand.dmg + (enemy1.leftHand.dmg / 2) - 2)
      }

      var enemy4 = Enemy()
      "should scale Acordingly in plus" in {
        while (enemy4.lvl <= 5) {
          enemy4 = Enemy()
          enemy4 = enemy4.setScale(5)
          //println("ENEMY LVL" + enemy4.lvl)
        }
        enemy4.lvl should (be <= 8 and be >= 6)
      }
      "should scale Acordingly in minus" in {
        while (enemy4.lvl >= 5) {
          enemy4 = Enemy()
          enemy4 = enemy4.setScale(5)
          //println("ENEMY LVL" + enemy4.lvl)
        }
        enemy4.lvl should (be >= 2 and be <= 4)
      }
      "should not scale under 1" in {
        while (enemy4.lvl != 1) {
          enemy4 = Enemy()
          enemy4 = enemy4.setScale(0)
          //println("-LEVEL ENEMY LVL" + enemy4.lvl)
        }
        enemy4.lvl should be(1)
      }

      val enemy5 = Enemy(lvl = 1)
      "have loot" in {
        val enemyLoot = enemy5.setLoot()
        enemyLoot.inventory should not be(new Inventory(Vector(), Vector(), Vector()))
      }

      var enemy6 = Enemy(lvl = 1)
      "when getting a piece of armor" in {
        while (enemy6.helmet == Armor("noHelmet"))
          enemy6 = enemy6.setLoot()
        enemy6.helmet should not be(Armor("noHelmet"))

        while (enemy6.chest == Armor("noChest"))
          enemy6 = enemy6.setLoot()
        enemy6.chest should not be(Armor("noChest"))

        while (enemy6.pants == Armor("noPants"))
          enemy6 = enemy6.setLoot()
        enemy6.pants should not be(Armor("noPants"))

        while (enemy6.boots == Armor("noBoots"))
          enemy6 = enemy6.setLoot()
        enemy6.boots should not be(Armor("noBoots"))

        while (enemy6.gloves == Armor("noGloves"))
          enemy6 = enemy6.setLoot()
        enemy6.gloves should not be(Armor("noGloves"))
      }

      var bossenemy = Enemy(health = 50,maxHealth = 50,mana = 50, maxMana = 50,attack = 4,lvl = 1)
      "when creating a RandomBoss" in {
        var boss = Enemy().createRandomBoss(1)
        boss should be (bossenemy)
      }

    }
  }

}
