package de.htwg.se.betterthanbfa.model.LevelComponent

import de.htwg.se.betterthanbfa.model.CombatComponent.Fight
import de.htwg.se.betterthanbfa.model.EnemyComponent.Enemy
import de.htwg.se.betterthanbfa.model.playerComponent.Player

case class Level(player: Player) {

  var level: Int = 10
  var size: Int = 5
  var map = Array.ofDim[Int](5, 5)

  var enemys: List[Enemy] = List()

  val fight: Fight = new Fight // Weis nicht ob hier oder inmain oder ka!

  //public
  def createLevel(size: Int) = {
    map = Array.ofDim[Int](size, size)
    this.size = size
    map(player.posY)(player.posX) = 1
    randomEnemy()
  }

  def removeTile(x: Int, y: Int) = {
    map(y)(x) = 0
  }

  def moveRight() = {
    removeTile(player.posX, player.posY)
    player.posX += 1
    if (player.posX >= size)
      player.posX = size - 1
    update()
  }

  def moveLeft() = {
    removeTile(player.posX, player.posY)
    player.posX -= 1
    if (player.posX < 0)
      player.posX = 0
    update()
  }

  def moveDown() = {
    removeTile(player.posX, player.posY)
    player.posY += 1
    if (player.posY >= size)
      player.posY = size - 1
    update()
  }

  def moveUp() = {
    removeTile(player.posX, player.posY)
    player.posY -= 1
    if (player.posY < 0)
      player.posY = 0
    update()
  }


  //Private
  private def update(): Unit = {
    for (x <- enemys) {
      if (x.isAlive) {
        map(x.posY)(x.posX) = 2
        if (x.posX == player.posX && x.posY == player.posY) {
          println("Krass am fighten der BOI")
          fight.fight(player,x)
        }
      }
    }
    map(player.posY)(player.posX) = 1 // ersma am ende so das man sieht wo er steht
  }

  private def randomEnemy() = {
    for (a <- 1 to level) {
      val coordinate = randomCooardinate()
      enemys = List.concat(enemys, List(new Enemy(player, coordinate(0), coordinate(1))))
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
    Array(x, y)
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
