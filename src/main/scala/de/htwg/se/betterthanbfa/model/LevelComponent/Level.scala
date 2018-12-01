package de.htwg.se.betterthanbfa.model.LevelComponent

import de.htwg.se.betterthanbfa.model.EnemyComponent.Enemy
import de.htwg.se.betterthanbfa.model.playerComponent.Player

case class Level(player: Player) {

  var level: Int = 1
  var size: Int = 5
  var map = Array.ofDim[Int](5, 5)

  var enemys: List[Enemy] = List()

  def createLevel(size: Int) = {
    map = Array.ofDim[Int](size, size)
    this.size = size
    randomEnemy()
  }

  private def randomEnemy() = {
    for (a <- 1 to level) {
      val coordinate = randomCooardinate()
      enemys = List.concat(enemys,List(new Enemy(player,coordinate(0),coordinate(1))))
      map(coordinate(1))(coordinate(0)) = 2
    }
  }

  private def randomCooardinate(): Array[Int] = {
    var x = 0
    var y = 0
    val r = new scala.util.Random
    do {
      x = r.nextInt(size)
      y = r.nextInt(size)
    } while (map(y)(x) != 0)
    Array(x,y)
  }

  override def toString: String = {
    var s: String = ""
    for (i <- map) {
      for (x <- i) {
        s += " " + x
      }
      s += "\n"
    }
    s
  }

}
