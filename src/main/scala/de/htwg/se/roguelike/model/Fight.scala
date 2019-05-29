package de.htwg.se.roguelike.model

case class Fight() {

  def interaction(player: Player, enemies: Vector[Enemy]): Boolean = {
    for (enemy <- enemies) {
      if (player.posX == enemy.posX && player.posY == enemy.posY)
        return true
    }
    false
  }

  def playerAttack(player: Player, enemy: Enemy): Enemy = {
    println("PlayerAttack: " + player.getAttack) //zum testen
    val enemy2 = enemy.copy(health = enemy.health - player.getAttack.toInt)
    enemy2
  }

  def enemyAttack(player: Player, enemy: Enemy): Player = {
    println("EnemyAttack: " + calcAttack(enemy.getAttack, player.getArmor))
    //zum testen
    val player2 = player.copy(health = player.health - calcAttack(enemy.getAttack, player.getArmor))
    player2
  }

  private def calcAttack(attack: Double, armor: Double): Int = (attack * (100.0 / (100.0 + armor))).toInt

  override def toString: String = "Fight:\n[1]Attack\n[2]:Block\n"

}
