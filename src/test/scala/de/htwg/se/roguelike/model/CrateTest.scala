package de.htwg.se.roguelike.model

import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl.{Armor, Crate, Potion, Weapon}
import org.scalatest.{Matchers, WordSpec}

class CrateTest extends WordSpec with Matchers{

  " A Crate " when {
    "new " should {
      var crate = Crate()
      "should have been initialized" in {
        crate.posY should be(-1)
        crate.posX should be(-1)
        crate.inventory.size should be(0)
      }
    "filled" should {
       "with 1 Item" in {
         var crate1 = Crate()
         var test = false
         crate1 = crate1.fillCrate(0,0)
         while (crate1.inventory.size != 1 || !test) {
           crate1 = Crate()
           crate1 = crate1.fillCrate(0,0)
           crate1.inventory(0) match {
             case w: Weapon => test = true
             case _ =>
           }
         }
         crate1.inventory.size should be (1)
       }
      "with 2 Items" in {
        var crate2 = Crate()
        var test = false
        crate2.fillCrate(50,1)
        while (crate2.inventory.size != 2 || !test) {
          crate2 = Crate()
          crate2 = crate2.fillCrate(50,1)
          crate2.inventory(0) match {
            case a: Armor => test = true
            case _ =>
          }
        }
        crate2.inventory.size should be (2)
      }
      "with 3 Items" in {
        var crate3 = Crate()
        var test = false
        crate3.fillCrate(85,1)
        while (crate3.inventory.size != 3 || !test) {
          crate3 = Crate()
          crate3 = crate3.fillCrate(85,1)
          crate3.inventory(0) match {
            case p: Potion => test = true
            case _ =>
          }
        }
        crate3.inventory.size should be (3)
      }
    }

    }
  }
}
