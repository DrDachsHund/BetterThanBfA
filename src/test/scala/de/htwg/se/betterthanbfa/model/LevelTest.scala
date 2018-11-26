package de.htwg.se.betterthanbfa.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class LevelTest extends WordSpec with Matchers {

  "A Level" when { "new" should {
    val level = Level(3)
    "have a size"  in {
      level.map.length should be(3)
    }
    "have when init" should {
      "initialise Array" in {
        level.map should be(Array(0,0,0),(0,0,0))
      }
    }
    "have a nice String representation" in {
      level.toString should be(" 1 0 0")
    }
  }}


}

