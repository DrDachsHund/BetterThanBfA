package de.htwg.se.roguelike.aview.gui

import java.awt.image.BufferedImage

import javax.imageio.ImageIO

private class SpriteSheet(val path: String) {

  private var loaded: Boolean = false

  private var sheet: BufferedImage = _ //hier maybe noch abfrage wegen fals falsch ist idk

  private var sheetLoad: Option[BufferedImage] = Option(ImageIO.read(getClass.getResourceAsStream(path)))
  sheetLoad match {
    case None => println("Fehler beim Einlesen bei GUI Spreadsheet!!")
    case Some(s) => {
      sheet = s
      loaded = true
    }
  }

  def isLoaded:Boolean = loaded

  def getImage(): BufferedImage = {
    if (loaded) {
      sheet
    } else {
      println("Topkek sollte nicht vorkommen ")
      new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB)
    }
  }

  def getSprite(x: Int, y: Int, pixelDensity: Int): BufferedImage = {
    if (loaded) {
      sheet.getSubimage(x, y, pixelDensity, pixelDensity)
    } else {
      println("Topkek sollte nicht vorkommen ")
      new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB)
    }
  }

  def horizontalFlip(img: BufferedImage): BufferedImage = {
    val w = img.getWidth
    val h = img.getHeight
    val flippedImage = new BufferedImage(w, h, img.getType)
    val g = flippedImage.createGraphics
    g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null)
    g.dispose()
    flippedImage
  }
}

