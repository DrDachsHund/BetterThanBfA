package de.htwg.se.roguelike.model.levelComponent.levelBaseImpl

import scala.util.Random

class LevelCreator(sizeY: Int, sizeX: Int) {

  def createLevel(player: Player, enemies: Vector[Enemy]): Level = {
    var level = new Level(sizeY, sizeX)
    level = Level(level.map.replaceTile(player.posY, player.posX, Tile(5)))

    for (x <- enemies) {
      level = Level(level.map.replaceTile(x.posY, x.posX, Tile(3)))
    }

    level
  }

  def createRandom(player: Player, enemyCount: Int): (Level, Vector[Enemy]) = {
    var level = new Level(sizeY, sizeX)
    level = Level(level.map.replaceTile(player.posY, player.posX, Tile(5)))


    var row: Int = 0
    var col: Int = 0
    var enemies: Vector[Enemy] = Vector()

    for (_ <- 1 to enemyCount) {
      while (level.map.tile(col, row).isSet) {
        col = Random.nextInt(level.map.sizeX)
        row = Random.nextInt(level.map.sizeY)
      }


      //maybe hier noch ändern wgen this ka ob so richtig alles
      val enemyType = Random.nextInt(3) + 1 //ersma so vll noch ändern
      var newEnemy = Enemy(posX = row, posY = col, enemyType = enemyType)
      newEnemy = newEnemy.copy(name = newEnemy.setName())

      enemies = enemies :+ newEnemy
      level = Level(level.map.replaceTile(col, row, Tile(3)))

    }

    var freeTiles: Int = 0
    for (x <- 0 until level.map.sizeX) {
      for (y <- 0 until level.map.sizeY) {
        if (!level.map.tile(x, y).isSet) {
          freeTiles += 1
        }
      }
    }

    level = spawnRandomTile(level, Tile(2), freeTiles / 4)

    (level, enemies)
  }

  def spawnRandomTile(level: Level, tile: Tile, numberOfTiles: Int): Level = {

    var newLevel = level

    var row: Int = 0
    var col: Int = 0

    for (_ <- 1 to numberOfTiles) {
      while (newLevel.map.tile(col, row).isSet) {
        col = Random.nextInt(newLevel.map.sizeX)
        row = Random.nextInt(newLevel.map.sizeY)
      }

      newLevel = Level(newLevel.map.replaceTile(col, row, tile))
    }

    newLevel
  }

}
