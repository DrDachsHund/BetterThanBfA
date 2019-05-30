package de.htwg.se.roguelike.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}


@RunWith(classOf[JUnitRunner])
class SwordTest extends WordSpec with Matchers {
  "A Sword " when {
    "new" should {
      val player: Player = Player(name = "Player1")
      val player2: Player = Player(name = "Player1", lvl = 10)
      "get a scaled Weapon in Positive Range" in {
        var sword2 = Weapon("Sword")
        while (sword2.itemLevel < player.lvl) {
          sword2 = Weapon("Sword")
          sword2 = sword2.getScaledWeapon(player.lvl)
        }
        sword2.name should be("Sword")
        sword2.value should (be >= 10 and be <= 12)
        sword2.itemLevel should (be >= 1 and be <= 5)
        sword2.usable should be(false)
        sword2.dmg should (be >= 10 and be <= 12)
        sword2.block should (be >= 5 and be <= 6)
        sword2.oneHanded should be(true)
        sword2.rarity should be("Common")
      }
      "get a scaled Weapon in Negative Range" in {
        var sword = Weapon("Sword").getScaledWeapon(player2.lvl)
        while (sword.itemLevel > player2.lvl) {
          sword = Weapon("Sword")
          sword = sword.getScaledWeapon(player2.lvl)
        }
        println("\nWeaponItemLevel: " + sword.itemLevel)
        println("\n" + sword.toString)
        sword.name should be("Sword")
        sword.value should (be >= 10 and be <= 14)
        sword.itemLevel should (be >= 6 and be <= 10)
        sword.usable should be(false)
        sword.dmg should (be >= 12 and be <= 14)
        sword.block should (be >= 6 and be <= 7)
        sword.oneHanded should be(true)
        sword.rarity should be("Common")
      }
    }
  }
}

