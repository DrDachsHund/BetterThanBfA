package de.htwg.se.roguelike

import de.htwg.se.roguelike.model.{Player, Weapon}

object TestMain {
  def main(args: Array[String]): Unit = {

    var player:Player = new Player(name = "Test",lvl = 1)
    var weapon = Weapon("random")
    println(weapon.toString)
    player = player.copy(lvl = 10)
    println(weapon.getScaledWeapon(player.lvl))


    /*
    while (weapon.rarity != "Unknown") {
      if (weapon.rarity == "FEHLER") println(weapon.toString)
      weapon = Weapon("random")
    }
    */
/*
    for (_ <- 0 to 100) {
      println(weapon.toString)
      weapon = Weapon("random")
    }
*/
    //println(weapon.getRandomWeaponStats)
    //println(weapon.getRarity())

  }
}
