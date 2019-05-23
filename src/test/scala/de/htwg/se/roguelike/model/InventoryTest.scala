package de.htwg.se.roguelike.model

import org.scalatest.{Matchers, WordSpec}

class InventoryTest extends WordSpec with Matchers {

  "An Inventory" when {
    "new" should {
      val inventory = new Inventory(
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
        inventory.potionsToString() should be("[1]"+Potion("SmallHeal").name+"\n")
      }

      "should have a nice String representation for Armor" in {
        inventory.armorToString() should be("[1]"+Armor("Gloves").name+"\n")
      }

      "should have a nice String representation for Weapons" in {
        inventory.weaponsToString() should be("[1]"+Weapon("Sword").name+"\n")
      }

    }
  }

}
