package de.htwg.se.roguelike.model

import scala.util.Random

class LevelCreator(size:Int) {

  def createLevel(player: Player, enemies:Vector[Enemy]): Level = {
    var level = new Level(size)

    level = new Level(level.map.replaceTile(player.posY,player.posX,new Tile(5)))

    for (x <- enemies) {
      level = new Level(level.map.replaceTile(x.posX,x.posY,new Tile(3)))
    }

    level
  }

//col und row noch (fehlerhaft beschrieben) wahrescheinlich vertauschen
  def createRandom(player: Player, enemyCount:Int): (Level,Vector[Enemy]) = {
    var level = new Level(size)

    level = new Level(level.map.replaceTile(player.posY,player.posX,new Tile(5)))

    var row:Int = 0
    var col:Int = 0

    var enemies:Vector[Enemy] = Vector()

    for (x <- 1 to enemyCount) {

      do {
        col = Random.nextInt(level.map.size)
        row = Random.nextInt(level.map.size)
      } while (level.map.tile(col,row).isSet)

      var enemy = new Enemy(name = "RandomEnemy",posX = row, posY = col)
      enemies = enemies :+ enemy

      level = new Level(level.map.replaceTile(col,row,new Tile(3)))
    }

    //print(enemies)

    (level,enemies)
  }

}
