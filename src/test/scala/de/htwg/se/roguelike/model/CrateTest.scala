package de.htwg.se.roguelike.model

import de.htwg.se.roguelike.model.levelComponent.{ArmorInterface, CrateInterface, PotionInterface, WeaponInterface}
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
       "with 1 Items" in {
         var crate1:CrateInterface = Crate()
         var test = false
         crate1 = crate1.fillCrate(0,0)
         while (crate1.inventory.size != 1 || !test) {
           crate1 = Crate()
           crate1 = crate1.fillCrate(0,0)
           crate1.inventory(0) match {
             case w: WeaponInterface => test = true
             case _ =>
           }
         }
         crate1.inventory.size should be (1)
       }
      "with 2 Items" in {
        var crate2:CrateInterface = Crate()
        var test = false
        crate2.fillCrate(50,1)
        while (crate2.inventory.size != 2 || !test) {
          crate2 = Crate()
          crate2 = crate2.fillCrate(50,1)
          crate2.inventory(0) match {
            case a: ArmorInterface => test = true
            case _ =>
          }
        }
        crate2.inventory.size should be (2)
      }
      "with 3 Items" in {
        var crate3:CrateInterface = Crate()
        var test = false
        crate3.fillCrate(85,1)
        while (crate3.inventory.size != 3 || !test) {
          crate3 = Crate()
          crate3 = crate3.fillCrate(85,1)
          crate3.inventory(0) match {
            case p: PotionInterface => test = true
            case _ =>
          }
        }
        crate3.inventory.size should be (3)
      }
    }

    }
  }
}
