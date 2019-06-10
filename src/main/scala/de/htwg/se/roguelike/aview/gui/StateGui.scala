package de.htwg.se.roguelike.aview.gui

import de.htwg.se.roguelike.aview.tui.State

import scala.swing.Panel

trait StateGui extends State {
  def drawPanel(SCALE:Int): Panel
}
