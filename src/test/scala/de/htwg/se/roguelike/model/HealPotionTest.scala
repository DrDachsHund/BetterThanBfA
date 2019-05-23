package de.htwg.se.roguelike.model

import org.scalatest.{Matchers, WordSpec}

class HealPotionTest extends WordSpec with Matchers {
  "HealPotion" when {
    "new" should {

    val healPotion = Potion("SmallHeal")
    var player = new Player(name = "TestPlayer",health = 90)

    "should heal when used" in {
      player = healPotion.usePotion(player)
      player.health should be(100)
    }

  }}
}
