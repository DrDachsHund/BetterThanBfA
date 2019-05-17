package de.htwg.se.roguelike.model

trait Item {
  val name:String
  val value:Int
  val usable:Boolean

  def isUseable: Boolean = usable
  //def randomItem
}

//FactoryMethod
object Item {
  def apply(kind:String) = kind match {
    case "Weapon" => new Weapon("Sword",10,true,10,2) //rabdomItem
   // case "Armor" => new Armor("Helmet",10,true)
      //case "Potion"
      //case "Gold"
      //case "Quest"
  }
}