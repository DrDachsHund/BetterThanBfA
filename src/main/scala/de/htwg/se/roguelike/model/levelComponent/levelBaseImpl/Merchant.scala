package de.htwg.se.roguelike.model.levelComponent.levelBaseImpl

import de.htwg.se.roguelike.model.levelComponent.{ItemInterface, MerchantInterface}

case class Merchant(posX:Int = -1, posY:Int = -1, inventory:Vector[ItemInterface] = Vector(Weapon("random"),Armor("random"),Potion("random")), gulden:Int = 50) extends MerchantInterface {

  def restock(lvl:Int):Merchant = {
    var newInventory = this.inventory

    var newWeapon = Weapon("Sword")
    var newArmor = Armor("Helmet")
    var newPotion = Potion("SmallHeal")

    while (newWeapon.rarity == "Common" || newWeapon.rarity == "Uncommon")
      newWeapon = Weapon("random").getScaledWeapon(lvl)

    while (newArmor.rarity == "Common" || newArmor.rarity == "Uncommon")
      newArmor = Armor("random")

    while (newPotion.rarity == "Common" || newPotion.rarity == "Uncommon")
      newPotion = Potion("random")

    newInventory ++= newWeapon :: Nil
    newInventory ++= newArmor :: Nil
    newInventory ++= newPotion :: Nil
    newInventory = sortItems(newInventory)
    this.copy(inventory = newInventory,gulden = this.gulden + 250)
  }

  def sortItems(inventory: Vector[ItemInterface]) : Vector[ItemInterface] = {
    var newInventory = inventory.sortWith(_.value > _.value)
    newInventory = newInventory.sortWith(_.getClass.getName > _.getClass.getName)
    newInventory
  }

}
