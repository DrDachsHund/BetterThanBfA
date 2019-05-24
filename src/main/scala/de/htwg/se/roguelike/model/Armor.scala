package de.htwg.se.roguelike.model

trait Armor extends Item {
  val name:String
  val value: Int
  val usable: Boolean
  val armor:Int
  val armorType:String
  //val rarity boosts wahrscheinlichkeit gute specails und vll wie viele

  //def randomItem

  //var curse zeigs z.b pro schlag 10 hp verlieren 10%attack miss

  //var special = specaial random!!!!
  //
  //def specals1 ... usw
}

//FactoryMethod
//noch schöner machen name = etc ...
object Armor {
  def apply(kind: String):Armor = kind match {
    case "Helmet" => Helmet("Helmet",10,usable = false,10) //.randomItem
    case "Chest" => Chest("Chest", 10, usable = false, 10)
    case "Pants" => Pants("Pants", 10, usable = false, 10)
    case "Boots" => Boots("Boots", 10, usable = false, 10)
    case "Gloves" => Gloves("Gloves", 10, usable = false, 10)
    case "noHelmet" => Helmet("Head", 0, usable = false, 0,armorType = "nothing")
    case "noChest" => Chest("Body", 0, usable = false, 0,armorType = "nothing")
    case "noPants" => Pants("Legs", 0,usable =  false, 0,armorType = "nothing")
    case "noBoots" => Boots("Feet", 0,usable =  false, 0,armorType = "nothing")
    case "noGloves" => Gloves("Hands", 0,usable =  false, 0,armorType = "nothing")
  }
}