package de.htwg.se.roguelike.model

case class Sword(name: String,
                 value: Int,
                 usable: Boolean,
                 dmg: Int,
                 block: Int,
                 oneHanded:Boolean,
                rarity:String) extends Weapon {

  //Weapon lvl hinzufügen
  //random 1-5 um player lvl herum

  override def getScaledWeapon(lvl:Int): Weapon = {
    this.copy(name,
      value = value * (lvl/50),
      usable,
      dmg = dmg * lvl,
      block = block * lvl,
      oneHanded, rarity)
  }

}
