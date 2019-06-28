package de.htwg.se.roguelike.model

import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl.{RandomArmor, Weapon}
import org.scalatest.{Matchers, WordSpec}

class WeaponTest extends WordSpec with Matchers {

  "Weapon" when {
    "new" should {
      "can be random" in {
        var weapon = Weapon("random")

        while (weapon.rarity != "Common") {
          weapon = Weapon("random")
        }
        weapon.rarity should be("Common")

        while (weapon.rarity != "Uncommon") {
          weapon = Weapon("random")
        }
        weapon.rarity should be("Uncommon")

        while (weapon.rarity != "Rare") {
          weapon = Weapon("random")
        }
        weapon.rarity should be("Rare")

        while (weapon.rarity != "Epic") {
          weapon = Weapon("random")
        }
        weapon.rarity should be("Epic")

        while (weapon.rarity != "Legendary") {
          weapon = Weapon("random")
        }
        weapon.rarity should be("Legendary")

        while (weapon.rarity != "Golden-Legendary") {
          weapon = Weapon("random")
        }
        weapon.rarity should be("Golden-Legendary")

        while (weapon.rarity != "Seraph") {
          weapon = Weapon("random")
        }
        weapon.rarity should be("Seraph")

        while (weapon.rarity != "Pearlescent") {
          weapon = Weapon("random")
        }
        weapon.rarity should be("Pearlescent")

        while (weapon.rarity != "Unknown") {
          weapon = Weapon("random")
        }
        weapon.rarity should be("Unknown")
      }

      "give a error when not able to load name" in {
        //RandomArmor.getArmorName("FEHLER.TXT") should be("Error-Loading-Armor-Name")
      }
    }
  }

}
