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
  def apply(kind: String) = kind match {
    case "Helmet" => new Helmet("Helmet",10,false,10) //.randomItem
    case "Chest" => new Chest("Chest", 10, false, 10)
    case "Pants" => new Pants("Pants", 10, false, 10)
    case "Boots" => new Boots("Boots", 10, false, 10)
    case "Gloves" => new Gloves("Gloves", 10, false, 10)
    case "noHelmet" => new Helmet("Head", 0, false, 0,armorType = "nothing")
    case "noChest" => new Chest("Body", 0, false, 0,armorType = "nothing")
    case "noPants" => new Pants("Legs", 0, false, 0,armorType = "nothing")
    case "noBoots" => new Boots("Feet", 0, false, 0,armorType = "nothing")
    case "noGloves" => new Gloves("Hands", 0, false, 0,armorType = "nothing")
  }
}