package de.htwg.se.roguelike.model

case class Fight() {

  def interaction(player: Player, enemies: Vector[Enemy]): Boolean = {
    for (enemy <- enemies) {
      if (player.posX == enemy.posX && player.posY == enemy.posY)
        return true
    }
    false
  }



}
