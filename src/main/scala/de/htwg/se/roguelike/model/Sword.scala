package de.htwg.se.roguelike.model

case class Sword(name: String,
                 value: Int,
                 usable: Boolean,
                 dmg: Int,
                 block: Int,
                 oneHanded:Boolean,
                rarity:String) extends Weapon {


  override def getScaledWeapon(player: Player): Weapon = {
    this.copy(name,
      value = value * player.lvl,
      usable,
      dmg = dmg * player.lvl,
      block = block * player.lvl,
      oneHanded, rarity)
  }

}
