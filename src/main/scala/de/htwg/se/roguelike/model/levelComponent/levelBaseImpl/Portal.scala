package de.htwg.se.roguelike.model.levelComponent.levelBaseImpl

import de.htwg.se.roguelike.model.levelComponent.PortalInterface

case class Portal(posX:Int = -1,posY:Int = -1,portalType: Int = 0) extends PortalInterface{}
