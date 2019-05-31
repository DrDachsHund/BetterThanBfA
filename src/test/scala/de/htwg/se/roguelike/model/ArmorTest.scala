package de.htwg.se.roguelike.model

import org.scalatest.{Matchers, WordSpec}

class ArmorTest extends WordSpec with Matchers {

  "Armor" when {
    "new" should {

      "can be a Chosen in different parts" in {
        Armor("Helmet") should be(Helmet(name = "Helmet", value = 10, usable = false, armor = 10, rarity = "Common"))
        Armor("Chest") should be(Chest(name = "Chest", value = 10, usable = false, armor = 10, rarity = "Common"))
        Armor("Pants") should be(Pants(name = "Pants", value = 10, usable = false, armor = 10, rarity = "Common"))
        Armor("Boots") should be(Boots(name = "Boots", value = 10, usable = false, armor = 10, rarity = "Common"))
        Armor("Gloves") should be(Gloves(name = "Gloves", value = 10, usable = false, armor = 10, rarity = "Common"))
        Armor("noHelmet") should be(Helmet(name = "Head", value = 0, usable = false, armor = 0, armorType = "nothing", rarity = "Common"))
        Armor("noChest") should be(Chest(name = "Body", value = 0, usable = false, armor = 0, armorType = "nothing", rarity = "Common"))
        Armor("noPants") should be(Pants(name = "Legs", value = 0, usable = false, armor = 0, armorType = "nothing", rarity = "Common"))
        Armor("noBoots") should be(Boots(name = "Feet", value = 0, usable = false, armor = 0, armorType = "nothing", rarity = "Common"))
        Armor("noGloves") should be(Gloves(name = "Hands", value = 0, usable = false, armor = 0, armorType = "nothing", rarity = "Common"))
      }

      "can be random" in {
        var armor = Armor("random")

        while (armor.armorType != "Helmet") {
          armor = Armor("random")
        }
        armor.armorType should be("Helmet")

        while (armor.armorType != "Chest") {
          armor = Armor("random")
        }
        armor.armorType should be("Chest")

        while (armor.armorType != "Pants") {
          armor = Armor("random")
        }
        armor.armorType should be("Pants")

        while (armor.armorType != "Boots") {
          armor = Armor("random")
        }
        armor.armorType should be("Boots")

        while (armor.armorType != "Gloves") {
          armor = Armor("random")
        }
        armor.armorType should be("Gloves")



        while (armor.rarity != "Common") {
          armor = Armor("random")
        }
        armor.rarity should be("Common")

        while (armor.rarity != "Uncommon") {
          armor = Armor("random")
        }
        armor.rarity should be("Uncommon")

        while (armor.rarity != "Rare") {
          armor = Armor("random")
        }
        armor.rarity should be("Rare")

        while (armor.rarity != "Epic") {
          armor = Armor("random")
        }
        armor.rarity should be("Epic")

        while (armor.rarity != "Legendary") {
          armor = Armor("random")
        }
        armor.rarity should be("Legendary")

        while (armor.rarity != "Golden-Legendary") {
          armor = Armor("random")
        }
        armor.rarity should be("Golden-Legendary")

        while (armor.rarity != "Seraph") {
          armor = Armor("random")
        }
        armor.rarity should be("Seraph")

        while (armor.rarity != "Pearlescent") {
          armor = Armor("random")
        }
        armor.rarity should be("Pearlescent")

        while (armor.rarity != "Unknown") {
          armor = Armor("random")
        }
        armor.rarity should be("Unknown")
      }

      "give a error when not able to load name" in {
        RandomArmor.getArmorName("FEHLER.TXT") should be("Error-Loading-Armor-Name")
      }

    }
  }
}
