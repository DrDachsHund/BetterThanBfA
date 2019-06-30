package de.htwg.se.roguelike.model.levelComponent.levelBaseImpl

import de.htwg.se.roguelike.model.levelComponent.PortalInterface

case class Portal(posX:Int = -1,posY:Int = -1,portalType: Int = 0) extends PortalInterface{

  override def nextPortal(posX:Int = this.posX,posY:Int = this.posY,portalType: Int = this.portalType): PortalInterface = {
    this.copy(posX = posX,posY = posY,portalType = portalType)
  }
}
