package de.htwg.se.roguelike.model

import org.scalatest.{Matchers, WordSpec}

class ArmorTest extends WordSpec with Matchers {


  //da wir nicht alle irgendwo aufrufen brauchen wir hier die um zu covern
      Armor("Helmet") should be(Helmet("Helmet",10,usable = false,10))
      Armor("Chest") should be(Chest("Chest", 10, usable = false, 10))
      Armor("Pants") should be(Pants("Pants", 10,usable = false, 10))
      Armor("Boots") should be(Boots("Boots", 10,usable = false, 10))
      Armor("Gloves") should be(Gloves("Gloves", 10, usable = false, 10))
      Armor("noHelmet") should be(Helmet("Head", 0, usable = false, 0,armorType = "nothing"))
      Armor("noChest") should be(Chest("Body", 0,usable = false, 0,armorType = "nothing"))
      Armor("noPants") should be(Pants("Legs", 0, usable = false, 0,armorType = "nothing"))
      Armor("noBoots") should be(Boots("Feet", 0, usable = false, 0,armorType = "nothing"))
      Armor("noGloves") should be(Gloves("Hands", 0, usable = false, 0,armorType = "nothing"))


}
