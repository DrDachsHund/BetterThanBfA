package de.htwg.se.roguelike.model

case class Fight() {

  def interaction(player: Player, enemies: Vector[Enemy]): Boolean = {
    for (enemy <- enemies) {
      if (player.posX == enemy.posX && player.posY == enemy.posY)
        return true
    }
    false
  }

  def getEnemy(player: Player, enemies: Vector[Enemy]): Enemy = {
    for (enemy <- enemies) {
      if (player.posX == enemy.posX && player.posY == enemy.posY)
        return enemy
    }
    println("FEHLER SOLLTE NIE HIER VORBEI KOMMEN")
    null
  }

  def playerAttack(player:Player, enemy: Enemy): Enemy = {
    println("PlayerAttack")
    val enemy2 = enemy.copy(health = (enemy.health - player.getAttack()))
    enemy2
  }

  def enemyAttack(player:Player, enemy: Enemy): Player = {
    println("EnemyAttack")
    val player2 = player.copy(health = (player.health - enemy.getAttack()))
    player2
  }

  override def toString: String = "Fight:\n[1]Attack\n[2]:Block"

}
