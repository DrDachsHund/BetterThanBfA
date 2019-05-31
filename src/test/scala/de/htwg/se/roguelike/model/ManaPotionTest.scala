package de.htwg.se.roguelike.model

import org.scalatest.{Matchers, WordSpec}

class ManaPotionTest extends WordSpec with Matchers {

  "A Mana Potion" when {
    "When new" should {
      val manaPotion = Potion("FullMana")
      "have a use" in {
        var player = new Player(name = "Test",mana = 1)
        player = manaPotion.usePotion(player)
        player.mana should be(player.maxMana)
      }
    }
  }

}
