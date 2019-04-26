package de.htwg.se.roguelike.model

import org.scalatest.WordSpec

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class LevelTest extends WordSpec with Matchers{

  "LevelTest" when {
    "new" should {
      val level = new Level(2)

      var upLvl = new Level(level.map.replaceTile(1,0,Tile(5)))
      var downLvl = new Level(level.map.replaceTile(0,0,Tile(5)))
      var rightLvl = new Level(level.map.replaceTile(0,0,Tile(5)))
      var leftLvl = new Level(level.map.replaceTile(0,1,Tile(5)))

      "moveUp" in {
        var up = upLvl.moveUp(new Player("Test",posX = 0, posY = 1))
        up._1.map.tile(0,1).value should be(0)
        up._1.map.tile(0,0).value should be(5)
        up._2.posY should be(0)
      }

      "moveDown" in {
        var down = downLvl.moveDown(new Player("Test",posX = 0, posY = 0))
        down._1.map.tile(0,0).value should be(0)
        down._1.map.tile(1,0).value should be(5)
        down._2.posY should be(1)
      }

      "moveRight" in {
        var right = rightLvl.moveRight(new Player("Test",posX = 0, posY = 0))
        right._1.map.tile(0,0).value should be(0)
        right._1.map.tile(0,1).value should be(5)
        right._2.posX should be(1)
      }

      "moveLeft" in {
        var left = leftLvl.moveLeft(new Player("Test",posX = 1, posY = 0))
        left._1.map.tile(0,0).value should be(5)
        left._1.map.tile(0,1).value should be(0)
        left._2.posX should be(0)
      }


      "cant moveUp" in {
        var up = downLvl.moveUp(new Player("Test",posX = 0, posY = 0))
        up._1.map.tile(0,1).value should be(0)
        up._1.map.tile(0,0).value should be(5)
        up._2.posY should be(0)
      }

      "cant moveDown" in {
        var down = upLvl.moveDown(new Player("Test",posX = 0, posY = 1))
        down._1.map.tile(0,0).value should be(0)
        down._1.map.tile(1,0).value should be(5)
        down._2.posY should be(1)
      }

      "cant moveRight" in {
        var right = leftLvl.moveRight(new Player("Test",posX = 1, posY = 0))
        right._1.map.tile(0,0).value should be(0)
        right._1.map.tile(0,1).value should be(5)
        right._2.posX should be(1)
      }

      "cant moveLeft" in {
        var left = rightLvl.moveLeft(new Player("Test",posX = 0, posY = 0))
        left._1.map.tile(0,0).value should be(5)
        left._1.map.tile(0,1).value should be(0)
        left._2.posX should be(0)
      }




      "have a nice String representation" in {
        level.toString should be("0 0 \n0 0 \n")
      }

    }
  }
}
