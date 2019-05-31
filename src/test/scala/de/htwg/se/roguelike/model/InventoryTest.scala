package de.htwg.se.roguelike.model

import org.scalatest.{Matchers, WordSpec}

class InventoryTest extends WordSpec with Matchers {

  "An Inventory" when {
    "new" should {
      val inventory = Inventory(
        weapons = Vector(Weapon("Sword")),
        potions = Vector(Potion("SmallHeal")),
        armor = Vector(Armor("Gloves")))

      "when u get a Potion" in {
        inventory.getPotion(1) should be(Potion("SmallHeal"))
      }

      "when u get a Armor" in {
        inventory.getArmor(1) should be(Armor("Gloves"))
      }

      "when u get a potion" in {
        inventory.getWeapon(1) should be(Weapon("Sword"))
      }

      "should have a nice String representation for Potions" in {
        inventory.potionsToString() should be("[1]"+Potion("SmallHeal").toString+"\n")
      }

      "should have a nice String representation for Armor" in {
        inventory.armorToString() should be("[1]"+Armor("Gloves").toString+"\n")
      }

      "should have a nice String representation for Weapons" in {
        inventory.weaponsToString() should be("[1]"+Weapon("Sword").toString+"\n")
      }

      val inventory2 = new Inventory(Vector(),Vector(),Vector())
      "should have a nice String representation for Potions when empty" in {
        inventory2.potionsToString() should be("Keine Potions\n")
      }

      "should have a nice String representation for Armor when empty" in {
        inventory2.armorToString() should be("Keine Armor\n")
      }

      "should have a nice String representation for Weapons when empty" in {
        inventory2.weaponsToString() should be("Keine Weapons\n")
      }

    }
  }

}
