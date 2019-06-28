package de.htwg.se.roguelike.model

import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl.Merchant
import org.scalatest.{Matchers, WordSpec}

class MerchantTest extends WordSpec with Matchers {

  "A Merchant" when {
    "new" should {

      val merchant = new Merchant()
      "have been initialized" in {
        merchant.posX should be(-1)
        merchant.posY should be(-1)
        merchant.inventory.size should be(3)
        merchant.gulden should be(50)
      }

      val merchant2 = merchant.restock(0)
      "be able to restock" in {
        merchant2.inventory.size should be(6)
      }
    }



    }


}
