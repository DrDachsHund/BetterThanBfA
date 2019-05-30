package de.htwg.se.roguelike.model

import scala.util.Random

class LevelCreator(size: Int) {

  def createLevel(player: Player, enemies: Vector[Enemy]): Level = {
    var level = new Level(size)
    level = Level(level.map.replaceTile(player.posY, player.posX, Tile(5)))

    for (x <- enemies) {
      level = Level(level.map.replaceTile(x.posX, x.posY, Tile(3)))
    }

    level
  }

  def createRandom(player: Player, enemyCount: Int): (Level, Vector[Enemy]) = {
    var level = new Level(size)
    level = Level(level.map.replaceTile(player.posY, player.posX, Tile(5)))


    var row: Int = 0
    var col: Int = 0
    var enemies: Vector[Enemy] = Vector()

    for (_ <- 1 to enemyCount) {
      do {
        col = Random.nextInt(level.map.size)
        row = Random.nextInt(level.map.size)
      } while (level.map.tile(col, row).isSet)


      val enemy = Enemy(name = "RandomEnemy", posX = row, posY = col).setScale(player.lvl).setLoot() //Name vll noch anpassen idk wie grad => Inventory hinzugefügt random


      enemies = enemies :+ enemy
      level = Level(level.map.replaceTile(col, row, Tile(3)))
    }

    (level, enemies)
  }

}
