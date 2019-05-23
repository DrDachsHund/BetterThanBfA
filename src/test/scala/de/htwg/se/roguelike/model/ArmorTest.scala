package de.htwg.se.roguelike.model

import org.scalatest.{Matchers, WordSpec}

class ArmorTest extends WordSpec with Matchers {


  //da wir nicht alle irgendwo aufrufen brauchen wir hier die um zu covern
      Armor("Helmet") should be(new Helmet("Helmet",10,false,10))
      Armor("Chest") should be(new Chest("Chest", 10, false, 10))
      Armor("Pants") should be(new Pants("Pants", 10, false, 10))
      Armor("Boots") should be(new Boots("Boots", 10, false, 10))
      Armor("Gloves") should be(new Gloves("Gloves", 10, false, 10))
      Armor("noHelmet") should be(new Helmet("Head", 0, false, 0,armorType = "nothing"))
      Armor("noChest") should be(new Chest("Body", 0, false, 0,armorType = "nothing"))
      Armor("noPants") should be(new Pants("Legs", 0, false, 0,armorType = "nothing"))
      Armor("noBoots") should be(new Boots("Feet", 0, false, 0,armorType = "nothing"))
      Armor("noGloves") should be(new Gloves("Hands", 0, false, 0,armorType = "nothing"))


}
