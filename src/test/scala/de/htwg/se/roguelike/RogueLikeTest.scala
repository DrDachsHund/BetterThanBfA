package de.htwg.se.roguelike

import org.scalatest.{Matchers, WordSpec}

class RogueLikeTest extends WordSpec with Matchers {

  "The RogueLike main class" should {
    "accept text input as argument without readline loop, to test it from command line " in {
      RogueLike.main(Array[String]("q"))
    }
  }

}
