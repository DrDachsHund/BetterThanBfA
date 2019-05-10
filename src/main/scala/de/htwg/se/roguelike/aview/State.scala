package de.htwg.se.roguelike.aview

trait State {
    def handle(e: Event) : State
}



