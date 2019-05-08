package de.htwg.se.roguelike.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner


class LevelCreatorTest extends WordSpec with Matchers {
  "A LevelCreater creates a specific or random Level. A LevelCreator" should {
    val level = new LevelCreator(2).
      createLevel(new Player(name = "TestPlayer",posX = 0,posY = 0)
      ,Vector(new Enemy(posX = 1,posY = 1)))

    "createLevel" in {
      level.map.tile(0,0) should be(Tile(5))
      level.map.tile(1,1) should be(Tile(3))
    }
    var test = false
    val level2 = new LevelCreator(2).
      createRandom(new Player(name = "TestPlayer",posX = 0,posY = 0)
        ,1)

    "createRandom" in {
      for (x <- 0 to level2._1.map.size) {
        if (level2._2.length == 1) test = true
      }
      test should be (true)
    }
  }
}
