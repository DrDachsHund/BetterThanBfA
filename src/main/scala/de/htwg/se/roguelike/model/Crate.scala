package de.htwg.se.roguelike.model

import scala.util.Random

case class Crate(posX:Int = -1,posY:Int = -1, inventory:Vector[Item] = Vector()) {

  def fillCrate (depth:Int,playerlvl:Int): Crate = {
    val random = new Random()
    val itemRandom = random.nextInt(100) + depth
    itemRandom match {
      case x if 1 until 50 contains x => randomCrate(this,1,playerlvl)
      case x if 50 until 85 contains x => randomCrate(this,2,playerlvl)
      case _ => randomCrate(this,3,playerlvl)
    }
  }

  def randomCrate (crate:Crate, items:Int, playerlvl:Int): Crate = {
    var newInventory = this.inventory
    val random = new Random()
    val itemRandom = random.nextInt(100) + 1
    for (x <- 1 to items) {
      itemRandom match {
        case y if 1 until 50 contains y => newInventory ++= Potion("random") :: Nil
        case y if 50 until 85 contains y => newInventory ++= Armor("random") :: Nil
        case _ => newInventory ++= Weapon("random").getScaledWeapon(playerlvl) :: Nil
      }
    }
    crate.copy(inventory = newInventory)
  }
}
