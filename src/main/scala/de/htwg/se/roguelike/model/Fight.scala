package de.htwg.se.roguelike.model

class Fight {

  def interaction(player: Player, enemies: List[Enemy]): Boolean = {
    for (enemy <- enemies) {
      if (player.posX == enemy.posX && player.posY == enemy.posY)
        return true
    }
    false
  }


}
