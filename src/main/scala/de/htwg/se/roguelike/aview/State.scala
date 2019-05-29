package de.htwg.se.roguelike.aview

trait State {
  def processInputLine(input: String)

  def handle()
}


/*TODO !!!!!!!!!!!!!!!:

WICHTIG BEIM EINLESEN VON INDEX GEHTS NICHT ÜBER 10 MASIVER FEHLER RIPRIP

Controller vll umbauen und nen enemy extra setzen in interact für bessere/einfachere behandlung und ausgaben!!!!

GUI:
  => Start Screen


LOOT: - 50 Wichtig-
  => Boss                                                                                                            [X}
    => BossItem                                                                                                      [X}
        => Special                                                                                                   [X}
  => Inventory Enemies mit Methode füllen (Loot Drop/Gold) !!!!                                                      [✓} //LOOOT => Nach neuem fight nochmal schauen!!!!!!!!!!


Player: -10 Wichtig-
  => SkillPunkte????  !!                                                                                             [X}
  => Zukunft Klassen (VLLT nur Methode); Abilities?????  !                                                           [X}


Fight: - 50 Wichtig-
  => Gegner Schlau !!!!                                                                                              [X}
  => Mehr Tiefe (Attack,Block,Ability,Fliehen etc.) !!!!                                                             [X}
  => Armor/Weapon Specials !!!                                                                                       [X}


Level: - 10 Wichtig-
  => Truhen                                                                                                          [X}
  => Neue Level, Maybe Boss Level alle 5 (BOSS ITEMS), Portale/Höhlen etc. ! => in Interact anpassen                 [X}
  => MAYBE Hindernisse BIG FRAGEZEICHEN ????????????????????????                                                     [X}
  => Eingang Häuser,Shop                                                                                             [X}


Merchant: - 0 Wichtig-
  => Shop
  => Random Items (Mehr/Bessere Items bei größerer Level Tiefe)

Enemy Resistance + Weapon Interaction (FIRE 2x DMG to ICE RES)


ARMOR/Weapon vergleichen ROT die Schlecht sind Grün gut (WoW)

*/