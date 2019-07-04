package de.htwg.se.roguelike.model

import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl.Potion
import org.scalatest.{Matchers, WordSpec}

class PotionTest extends WordSpec with Matchers {

  "A Potion" when {
    "crated" should {

      "have a SmallHeal" in {
        val potion = Potion("SmallHeal")
        potion.power should be(25)
      }

      "have a MediumHeal" in {
        val potion = Potion("MediumHeal")
        potion.power should be(50)
      }

      "have a BigHeal" in {
        val potion = Potion("BigHeal")
        potion.power should be(75)
      }

      "have a FullHeal" in {
        val potion = Potion("FullHeal")
        potion.power should be(100)
      }

      "have a SmallMana" in {
        val potion = Potion("SmallMana")
        potion.power should be(25)
      }

      "have a MediumMana" in {
        val potion = Potion("MediumMana")
        potion.power should be(50)
      }

      "have a BigMana" in {
        val potion = Potion("BigMana")
        potion.power should be(75)
      }

      "have a FullMana" in {
        val potion = Potion("FullMana")
        potion.power should be(100)
      }


      "have all avaible potions in random" in {
        var potion = Potion("random")
        while (potion.name != "SmallHeal") {
          potion = Potion("random")
        }
        potion.power should be(25)

        while (potion.name != "MediumHeal") {
          potion = Potion("random")
        }
        potion.power should be(50)

        while (potion.name != "BigHeal") {
          potion = Potion("random")
        }
        potion.power should be(75)

        while (potion.name != "FullHeal") {
          potion = Potion("random")
        }
        potion.power should be(100)

        while (potion.name != "SmallMana") {
          potion = Potion("random")
        }
        potion.power should be(25)

        while (potion.name != "MediumMana") {
          potion = Potion("random")
        }
        potion.power should be(50)

        while (potion.name != "BigMana") {
          potion = Potion("random")
        }
        potion.power should be(75)

        while (potion.name != "FullMana") {
          potion = Potion("random")
        }
        potion.power should be(100)

      }

    }
  }


}
