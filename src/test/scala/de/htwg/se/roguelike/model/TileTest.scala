package de.htwg.se.roguelike.model


import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TileTest extends WordSpec with Matchers {
  "A Tile " when {
    "new" should {
      val tile = Tile(3)
      "have a value" in {
        tile.value should be(3)
      }
    "set" should {
      "bla" in {
        tile.isSet should be(true)
      }
    }
      val tile2 = tile.copy(value = 0)
      "when not set" in {
        tile2.isSet should be(false)
      }
    }
  }
}
