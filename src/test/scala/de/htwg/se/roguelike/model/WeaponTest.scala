package de.htwg.se.roguelike.model

import org.scalatest.{Matchers, WordSpec}

class WeaponTest extends WordSpec with Matchers {
  "A Weapon when new" should {
    val weapon1 = new Weapon("test1",100,true,10,10)
    val weapon2 = new Weapon("test1",100,false,10,10)

    "when usable" in {
      weapon1.isUseable should be(true)
    }
    "when not usable" in {
      weapon2.isUseable should be(false)
    }
  }

}
