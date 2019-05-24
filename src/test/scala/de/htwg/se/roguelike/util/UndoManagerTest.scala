package de.htwg.se.roguelike.util

import org.scalatest.{Matchers, WordSpec}

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class UndoManagerTest extends WordSpec with Matchers {

  "An UndoManager" should {
    val undoManager = new UndoManager

    "have a do, undo and redo" in {
      val command = new TestCommand()
      command.state should be(0)
      undoManager.doStep(command)
      command.state should be(1)
      undoManager.undoStep()
      command.state should be(0)
      undoManager.redoStep()
      command.state should be(1)
    }

    "handle multiple undo steps correctly" in {
      val command = new TestCommand()
      command.state should be(0)
      undoManager.doStep(command)
      command.state should be(1)
      undoManager.doStep(command)
      command.state should be(2)
      undoManager.undoStep()
      command.state should be(1)
      undoManager.undoStep()
      command.state should be(0)
      undoManager.redoStep()
      command.state should be(1)
    }

    "when empty should do nothing" in {
      val command = new TestCommand
      val undoManager = new UndoManager
      undoManager.undoStep()
      command.state should be(0)
      undoManager.redoStep()
      command.state should be(0)
    }
  }
}
