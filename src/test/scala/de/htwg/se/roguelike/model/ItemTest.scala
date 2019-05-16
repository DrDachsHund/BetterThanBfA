package de.htwg.se.roguelike.model

import org.scalatest.{Matchers, WordSpec}

class ItemTest extends WordSpec with Matchers {

  "ItemTest" should {
    val item1 = new Item {
      override val name: String = "test"
      override val value: Int = 0
      override val usable: Boolean = true
    }
    val item2 = new Item {
      override val name: String = "test"
      override val value: Int = 0
      override val usable: Boolean = false
    }
    "when Useable" in {
      item1.usable should be (true)
    }
    "when not isUseable" in {
      item2.usable should be (false)
    }

  }
}
