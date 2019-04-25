package de.htwg.se.roguelike.model


import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class LandTest extends WordSpec with Matchers {
  "A Land is a two-dimensional Vector of Tiles. A Land" when {
    "new" should {
    val land1 = new Land[Tile](10, Tile(0))
      "have a size" in {
        land1.size should be(10)
      }
      val land2 = new Land[Tile](3,Tile(2))
      "give acces to tiles" in {
        land2.tile(0,0) should be(Tile(2))
      }
      val land3 = land2.replaceTile(0,0,Tile(4))
      "replace cells and return a new Land" in {
        land3.tile(0,0) should be(Tile(4))
      }
    }
  }


}
