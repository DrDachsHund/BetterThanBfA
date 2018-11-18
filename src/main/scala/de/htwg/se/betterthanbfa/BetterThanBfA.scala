package de.htwg.se.betterthanbfa

import de.htwg.se.betterthanbfa.model.Player

object BetterThanBfA {
  def main(args: Array[String]): Unit = {
    val student = Player("Ândre Göhring")
    val student2 = Player("Marvin Klett")
    println("Hello, " + student.name)
    println("Hello, " + student2.name)
    println("Ich hoffe mal das, das so richtig ist und nicht wieder mit dem sbt rumspackt!!!!!!!!!!!!!!!!!!!!!!!!!")
  }
}
