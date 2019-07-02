package de.htwg.se.roguelike.model.levelComponent.levelBaseImpl

import de.htwg.se.roguelike.model.levelComponent._

case class Fight() extends FightInterface {

  def interaction(player: PlayerInterface, enemies: Vector[EnemyInterface]): Boolean = {
    for (enemy <- enemies) {
      if (player.posX == enemy.posX && player.posY == enemy.posY)
        return true
    }
    false
  }

  def playerAttack(player: PlayerInterface, enemy: EnemyInterface, enemyAction: String): EnemyInterface = {
    var enemy2 = enemy
    if (enemyAction != "block") enemy2 = enemy.nextEnemy(health = enemy.health - calcAttack(player.getAttack, enemy.getArmor))
    else enemy2 = enemy.nextEnemy(health = enemy.health - calcAttack(player.getAttack, enemy.getArmor + enemy.rightHand.block + enemy.leftHand.block * 2))
    enemy2
  }

  def enemyAttack(player: PlayerInterface, enemy: EnemyInterface, playerAction: String): PlayerInterface = {
    //println("EnemyAttack: " + calcAttack(enemy.getAttack, player.getArmor))
    var player2 = player
    if (playerAction != "block") player2 = player.nextPlayer(health = player.health - calcAttack(enemy.getAttack, player.getArmor))
    else player2 = player.nextPlayer(health = player.health - calcAttack(enemy.getAttack, player.getArmor + player.rightHand.block + player.leftHand.block * 2))
    player2
  }

  private def calcAttack(attack: Double, armor: Double): Int = (attack * (100.0 / (100.0 + armor))).toInt

  override def toString: String = "Fight:\n[1]Attack\n[2]:Block\n[3]:Special\n"

  def shouldBlock(player: PlayerInterface, enemy: EnemyInterface): String = {
    val enemyTest = playerAttack(player, enemy, "")

    if (enemyTest.health <= 0) "yes"
    else if (enemyTest.health <= enemy.health / 2) "maybe"
    else "no"
  }

  def enemySpecial(player: PlayerInterface, currentEnemy: EnemyInterface): PlayerInterface = {
    //println("EnemySpecial: " + currentEnemy.getAttack.toInt)
    player.nextPlayer(health = player.health - currentEnemy.getAttack.toInt)
  }

  def playerSpecial(player: PlayerInterface, currentEnemy: EnemyInterface): EnemyInterface = {
    //println("PlayerSpecial: " + player.getAttack.toInt)
    currentEnemy.nextEnemy(health = currentEnemy.health - player.getAttack.toInt)
  }

}
