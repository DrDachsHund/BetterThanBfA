package de.htwg.se.roguelike.model

import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl._
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

      var inventory3 = new Inventory(
        Vector(new Sword(name = "Test",value = 5,usable = true,dmg = 10,block = 10,oneHanded = true,rarity = "test")
        ,new Sword(name = "Test",value = 10,usable = true,dmg = 5,block = 10,oneHanded = true,rarity = "test"))
        ,Vector(new HealPotion(name = "test",value = 5,usable = true,power = 10,rarity = "test")
        ,new HealPotion(name = "test",value = 10,usable = true,power = 5,rarity = "test"))
        ,Vector(new Helmet(name = "test", value = 5,usable = true,armor = 10,rarity = "test")
          ,new Helmet(name = "test", value = 10,usable = true,armor = 5,rarity = "test")))

      val testW = Vector(new Sword(name = "Test",value = 10,usable = true,dmg = 5,block = 10,oneHanded = true,rarity = "test")
        ,new Sword(name = "Test",value = 5,usable = true,dmg = 10,block = 10,oneHanded = true,rarity = "test"))
      val testP = Vector(new HealPotion(name = "test",value = 10,usable = true,power = 5,rarity = "test")
        ,new HealPotion(name = "test",value = 5,usable = true,power = 10,rarity = "test"))
      val testA = Vector(new Helmet(name = "test", value = 10,usable = true,armor = 5,rarity = "test")
        ,new Helmet(name = "test", value = 5,usable = true,armor = 10,rarity = "test"))

      "should be able to sort by value" in {
        inventory3 = inventory3.invSortValue()
        inventory3.weapons(0) should be(testW(0))
        inventory3.weapons(1) should be(testW(1))
        inventory3.potions(0) should be(testP(0))
        inventory3.potions(1) should be(testP(1))
        inventory3.armor(0) should be(testA(0))
        inventory3.armor(1) should be(testA(1))
      }

      "should be able to sort by power" in {
        inventory3 = inventory3.invSortPower()
        inventory3.weapons(0) should be(testW(1))
        inventory3.weapons(1) should be(testW(0))
        inventory3.potions(0) should be(testP(1))
        inventory3.potions(1) should be(testP(0))
        inventory3.armor(0) should be(testA(1))
        inventory3.armor(1) should be(testA(0))
      }



    }
  }

}
