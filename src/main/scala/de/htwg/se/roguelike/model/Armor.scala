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
    case "Helmet" => Helmet(name = "Helmet", value = 10,usable = false, armor = 10) //.randomItem
    case "Chest" => Chest(name = "Chest", value = 10, usable = false, armor = 10)
    case "Pants" => Pants(name = "Pants", value = 10, usable = false, armor = 10)
    case "Boots" => Boots(name = "Boots", value =10, usable = false, armor = 10)
    case "Gloves" => Gloves(name = "Gloves", value = 10, usable = false, armor = 10)
    case "noHelmet" => Helmet(name = "Head", value = 0, usable = false, armor = 0,armorType = "nothing")
    case "noChest" => Chest(name = "Body", value = 0, usable = false, armor = 0,armorType = "nothing")
    case "noPants" => Pants(name = "Legs", value = 0,usable =  false, armor = 0,armorType = "nothing")
    case "noBoots" => Boots(name = "Feet", value = 0,usable =  false, armor = 0,armorType = "nothing")
    case "noGloves" => Gloves(name = "Hands", value = 0,usable =  false,armor = 0,armorType = "nothing")
  }
}