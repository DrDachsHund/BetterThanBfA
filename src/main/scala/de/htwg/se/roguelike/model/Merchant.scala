package de.htwg.se.roguelike.model

case class Merchant(posX:Int = -1, posY:Int = -1, inventory:Vector[Item] = Vector(Weapon("random"),Armor("random"),Potion("random")), gulden:Int = 50) {

  def restock():Merchant = {
    var newInventory = this.inventory
    newInventory ++= Weapon("random") :: Nil
    newInventory ++= Armor("random") :: Nil
    newInventory ++= Potion("random") :: Nil
    this.copy(inventory = newInventory,gulden = this.gulden + 250)
  }

}
