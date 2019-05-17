package de.htwg.se.roguelike.model

trait Armor extends Item {
  val name:String
  val value: Int
  val usable: Boolean
  val armor:Int

  //def randomItem
}

//FactoryMethod
object Armor {
  def apply(kind: String) = kind match {
    case "Helmet" => new Helmet("Helmet",10,false,10) //.randomItem
    case "Chest" => new Chest("Chest", 10, false, 10)
  }
}