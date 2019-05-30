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
        enemy1.name should be("Skeleton")
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
        enemy1.toString should be("Name: " + "Skeleton" +
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
          println("ENEMY LVL" + enemy4.lvl)
        }
        enemy4.lvl should (be <= 8 and be >= 6)
      }
      "should scale Acordingly in minus" in {
        while (enemy4.lvl >= 5) {
          enemy4 = Enemy()
          enemy4 = enemy4.setScale(5)
          println("ENEMY LVL" + enemy4.lvl)
        }
        enemy4.lvl should (be >= 2 and be <= 4)
      }
      "should not scale under 1" in {
        while (enemy4.lvl != 1) {
          enemy4 = Enemy()
          enemy4 = enemy4.setScale(0)
          println("-LEVEL ENEMY LVL" + enemy4.lvl)
        }
        enemy4.lvl should be(1)
      }
    }
  }

}
