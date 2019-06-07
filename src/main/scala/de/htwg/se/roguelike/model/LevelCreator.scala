package de.htwg.se.roguelike.model

import scala.util.Random

class LevelCreator(sizeY: Int,sizeX:Int) {

  def createLevel(player: Player, enemies: Vector[Enemy]): Level = {
    var level = new Level(sizeY,sizeX)
    level = Level(level.map.replaceTile(player.posY, player.posX, Tile(5)))

    for (x <- enemies) {
      level = Level(level.map.replaceTile(x.posX, x.posY, Tile(3)))
    }

    level
  }

  def createRandom(player: Player, enemyCount: Int): (Level, Vector[Enemy]) = {
    var level = new Level(sizeY,sizeX)
    level = Level(level.map.replaceTile(player.posY, player.posX, Tile(5)))


    var row: Int = 0
    var col: Int = 0
    var enemies: Vector[Enemy] = Vector()

    for (_ <- 1 to enemyCount) {
      do {
        col = Random.nextInt(level.map.sizeX)
        row = Random.nextInt(level.map.sizeY)
      } while (level.map.tile(col, row).isSet)


      //maybe hier noch ändern wgen this ka ob so richtig alles
      val newEnemy = Enemy(name = "RandomEnemy", posX = row, posY = col)
      val enemy = newEnemy.setScale(player.lvl).setLoot() //Name vll noch anpassen idk wie grad => Inventory hinzugefügt random


      enemies = enemies :+ enemy
      level = Level(level.map.replaceTile(col, row, Tile(3)))

    }

    var freeTiles:Int = 0
    for (x <- 0 until level.map.sizeX) {
      for (y <- 0 until level.map.sizeY) {
          if (!level.map.tile(x,y).isSet) {
            freeTiles += 1
          }
      }
    }

    for (_ <- 1 to (freeTiles/4)) {
      do {
        col = Random.nextInt(level.map.sizeX)
        row = Random.nextInt(level.map.sizeY)
      } while (level.map.tile(col, row).isSet)

      level = Level(level.map.replaceTile(col, row, Tile(2)))
    }

    (level, enemies)
  }

}
