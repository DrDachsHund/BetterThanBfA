package de.htwg.se.roguelike.model

case class Fight() {

  def interaction(player: Player, enemies: Vector[Enemy]): Boolean = {
    for (enemy <- enemies) {
      if (player.posX == enemy.posX && player.posY == enemy.posY)
        return true
    }
    false
  }

  def playerAttack(player: Player, enemy: Enemy, enemyAction: String): Enemy = {
    var enemy2 = enemy
    if (enemyAction != "block")  enemy2 = enemy.copy(health = enemy.health - calcAttack(player.getAttack, enemy.getArmor))
    else enemy2 = enemy.copy(health = enemy.health - calcAttack(player.getAttack, enemy.getArmor + enemy.rightHand.block + enemy.leftHand.block * 2))
    enemy2
  }

  def enemyAttack(player: Player, enemy: Enemy, playerAction: String): Player = {
    println("EnemyAttack: " + calcAttack(enemy.getAttack, player.getArmor))
    var player2 = player
    if (playerAction != "block") player2 = player.copy(health = player.health - calcAttack(enemy.getAttack, player.getArmor))
    else player2 = player.copy(health = player.health - calcAttack(enemy.getAttack, player.getArmor + player.rightHand.block + player.leftHand.block * 2))
    player2
  }

  private def calcAttack(attack: Double, armor: Double): Int = (attack * (100.0 / (100.0 + armor))).toInt

  override def toString: String = "Fight:\n[1]Attack\n[2]:Block\n[3]:Special\n"

  def shouldBlock(player: Player, enemy: Enemy): String = {
    val enemyTest = playerAttack(player, enemy, "")

    if (enemyTest.health <= 0) "yes"
    else if (enemyTest.health <= enemy.health / 2) "maybe"
    else "no"
  }

  def enemySpecial(player: Player, currentEnemy: Enemy): Player = {
    println("EnemySpecial: " + currentEnemy.getAttack.toInt)
    player.copy(health = player.health - currentEnemy.getAttack.toInt)
  }

  def playerSpecial(player: Player, currentEnemy: Enemy): Enemy = {
    println("PlayerSpecial: " + player.getAttack.toInt)
    currentEnemy.copy(health = currentEnemy.health - player.getAttack.toInt)
  }

}
