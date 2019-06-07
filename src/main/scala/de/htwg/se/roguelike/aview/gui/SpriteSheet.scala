package de.htwg.se.roguelike.aview.gui

import java.awt.image.BufferedImage

import javax.imageio.ImageIO

private class SpriteSheet(val path: String) {

  var loaded:Boolean = false

  var sheet:BufferedImage = _

  var sheetLoad: Option[BufferedImage] = Option(ImageIO.read(getClass.getResourceAsStream(path)))
  sheetLoad match {
    case None =>  println("Fehler beim Einlesen bei GUI Spreadsheet!!")
    case Some(s) => {
      sheet = s
      loaded = true
    }
  }
  def getSprite(x: Int, y: Int): BufferedImage = {
    if (loaded) {
      sheet.getSubimage(x, y, 16, 16)
    } else {
      println("Topkek sollte nicht vorkommen ")
      new BufferedImage(0,0,BufferedImage.TYPE_INT_RGB)
    }
  }
}
