package de.htwg.se.roguelike.model

import org.scalatest.{Matchers, WordSpec}

class SwordTest extends WordSpec with Matchers {

  "A Sword " when {
    "new" should {
      val sword:Weapon = Weapon("Sword")
      "not be usable" in {
        sword.isUseable should be(false)
      }
    }
  }

}
