package de.htwg.se.betterthanbfa.model

case class Enemy(player: Player) {

  var level:Int = setLevel()
  def setLevel() = {
    player.level
  }


  override def toString:String = "Level: " + level
}
