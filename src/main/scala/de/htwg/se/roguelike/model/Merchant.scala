package de.htwg.se.roguelike.model

case class Merchant(posX:Int = -1, posY:Int = -1, inventory:Vector[Item] = Vector(Weapon("random"),Armor("random"),Potion("random")), gulden:Int = 50) {

  def restock():Merchant = {
    var newInventory = this.inventory

    var newWeapon = Weapon("random")
    var newArmor = Armor("random")
    var newPotion = Potion("random")

    while (newWeapon.rarity == "Common" || newWeapon.rarity == "Uncommon")
      newWeapon = Weapon("random")

    while (newArmor.rarity == "Common" || newArmor.rarity == "Uncommon")
      newArmor = Armor("random")

    while (newPotion.rarity == "Common" || newPotion.rarity == "Uncommon")
      newPotion = Potion("random")

    newInventory ++= newWeapon :: Nil
    newInventory ++= newArmor :: Nil
    newInventory ++= newPotion :: Nil
    this.copy(inventory = newInventory,gulden = this.gulden + 250)
  }

}
