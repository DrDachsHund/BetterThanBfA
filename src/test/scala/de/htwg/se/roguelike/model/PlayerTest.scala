package de.htwg.se.roguelike.model

import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PlayerTest extends WordSpec with Matchers {
  "A Player" when {
    "new" should {
      val player = Player("Your Name")
      "have a name" in {
        player.name should be("Your Name")
      }
      "have a nice String representation" in {
        player.toString should be(
          "Name: " + "Your Name" +
            "\nhealth: " + "100" +
            "\nAttack: " + "10" +
            "\nExperience: " + "0" +
            "\nposX: " + "0" +
            "\nposY: " + "0")
      }
      "when... alive" in {
        player.isAlive should be(true)
      }
      val player2 = player.copy(health = 0)
      "when not alive" in {
        player2.isAlive should be(false)
      }

      "have a combinate armor value" in {
        player.getArmor should be(0)
      }

      "can level up" in {
        var player3 = new Player(name = "Test", health = 1)
        player3 = player3.lvlUp(100)
        player3.lvl should be(2)
        player3.health should be(100)
        player3.exp should be(0)
        player3.maxExp should be(150)
      }

      "can collect exp but not level up" in {
        var player3 = new Player(name = "Test", health = 1)
        player3 = player3.lvlUp(1)
        player3.lvl should be(1)
        player3.health should be(1)
        player3.exp should be(1)
        player3.maxExp should be(100)
      }

      "when level up health" in {
        var player3 = new Player(name = "Test")
        player3 = player3.lvlUpHealth
        player3.health should be(110)
        player3.maxHealth should be(110)
      }

      "when level up mana" in {
        var player3 = new Player(name = "Test")
        player3 = player3.lvlUpMana
        player3.mana should be(110)
        player3.maxMana should be(110)
      }

      "when level up attack" in {
        var player3 = new Player(name = "Test")
        player3 = player3.lvlUpAttack
        player3.attack should be(20)
      }

      "have a score" in {
        val player3 = new Player(name = "Test", killCounter = 2)
        val score = player3.getScore(2)
        score should be(4)
      }

    }
  }


}
