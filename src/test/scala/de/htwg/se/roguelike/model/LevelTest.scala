package de.htwg.se.roguelike.model

import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl.{Level, Player, Tile}
import org.scalatest.WordSpec
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class LevelTest extends WordSpec with Matchers{

  "LevelTest" when {
    "new" should {
      val level = new Level(2,2)

      val upLvl = Level(level.map.replaceTile(1,0,Tile(5)))
      val downLvl = Level(level.map.replaceTile(0,0,Tile(5)))
      val rightLvl = Level(level.map.replaceTile(0,0,Tile(5)))
      val leftLvl = Level(level.map.replaceTile(0,1,Tile(5)))

      "moveUp" in {
        val up = upLvl.moveUp(Player("Test", posY = 1))
        up._1.map.tile(0,1).value should be(0)
        up._1.map.tile(1,1).value should be(0)
        up._1.map.tile(1,0).value should be(0)
        up._1.map.tile(0,0).value should be(5)
        up._2.posY should be(0)
      }

      "moveDown" in {
        val down = downLvl.moveDown(Player("Test"))
        down._1.map.tile(0,0).value should be(0)
        down._1.map.tile(0,1).value should be(0)
        down._1.map.tile(1,1).value should be(0)
        down._1.map.tile(1,0).value should be(5)
        down._2.posY should be(1)
      }

      "moveRight" in {
        val right = rightLvl.moveRight(Player("Test" ))
        right._1.map.tile(0,0).value should be(0)
        right._1.map.tile(1,0).value should be(0)
        right._1.map.tile(1,1).value should be(0)
        right._1.map.tile(0,1).value should be(5)
        right._2.posX should be(1)
      }

      "moveLeft" in {
        val left = leftLvl.moveLeft(Player("Test",posX = 1))
        left._1.map.tile(0,1).value should be(0)
        left._1.map.tile(1,0).value should be(0)
        left._1.map.tile(1,1).value should be(0)
        left._1.map.tile(0,0).value should be(5)
        left._2.posX should be(0)
      }


      "cant moveUp" in {
        val up = downLvl.moveUp(Player("Test"))
        up._1.map.tile(0,1).value should be(0)
        up._1.map.tile(1,0).value should be(0)
        up._1.map.tile(1,1).value should be(0)
        up._1.map.tile(0,0).value should be(5)
        up._2.posY should be(0)
      }

      "cant moveDown" in {
        val down = upLvl.moveDown(Player("Test", posY = 1))
        down._1.map.tile(0,0).value should be(0)
        down._1.map.tile(0,1).value should be(0)
        down._1.map.tile(1,1).value should be(0)
        down._1.map.tile(1,0).value should be(5)
        down._2.posY should be(1)
      }

      "cant moveRight" in {
        val right = leftLvl.moveRight(Player("Test",posX = 1))
        right._1.map.tile(0,0).value should be(0)
        right._1.map.tile(1,0).value should be(0)
        right._1.map.tile(1,1).value should be(0)
        right._1.map.tile(0,1).value should be(5)
        right._2.posX should be(1)
      }

      "cant moveLeft" in {
        val left = rightLvl.moveLeft(Player("Test"))
        left._1.map.tile(0,1).value should be(0)
        left._1.map.tile(1,0).value should be(0)
        left._1.map.tile(1,1).value should be(0)
        left._1.map.tile(0,0).value should be(5)
        left._2.posX should be(0)
      }

      "remove a Tile" in {
        var level2 = new Level(2,2)
        level2 = level2.removeElement(0,0,1)
        level2.map.tile(0,0) should be(Tile(1))
      }

      "have a nice String representation" in {
        level.toString should be("0 0 \n0 0 \n")
      }

    }
  }
}
