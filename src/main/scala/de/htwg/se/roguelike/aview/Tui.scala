package de.htwg.se.roguelike.aview

import de.htwg.se.roguelike.model._

class Tui {

  def processInputLine(input: String, level:Level, player:Player, enemies:List[Enemy]):(Level,Player,List[Enemy]) = {
    input match {
      case "q" => {
        var tupel = (level,player,enemies)
        (tupel._1,tupel._2 ,tupel._3)
      }

      case "r"=> {
        var tupel = new LevelCreator(10).createRandom(player,5)
        (tupel._1, tupel._2, tupel._3)
      }

      case "n" => {
        var tupel = (new LevelCreator(10).createLevel(player, enemies), player, enemies)
        (tupel._1, tupel._2, tupel._3)
      }

      case "i" => {
        print("Interaction: " + new Fight().interaction(player,enemies) + "\n")
        (level,player,enemies)
      }

      case "w" => {
        var tupel1 = level.moveUp(player)
        var tupel = (tupel1._1,tupel1._2,enemies)
        (tupel._1,tupel._2 ,tupel._3)
      }

      case "a" => {
        var tupel1 = level.moveLeft(player)
        var tupel = (tupel1._1,tupel1._2,enemies)
        (tupel._1,tupel._2 ,tupel._3)
      }

      case "s" => {
        var tupel1 = level.moveDown(player)
        var tupel = (tupel1._1,tupel1._2,enemies)
        (tupel._1,tupel._2 ,tupel._3)
      }

      case "d" => {
        var tupel1 = level.moveRight(player)
        var tupel = (tupel1._1,tupel1._2,enemies)
        (tupel._1,tupel._2 ,tupel._3)
      }

      case _ => {
        print("Wrong Input!!!")
        (level,player,enemies)
        }

      }
    }
}