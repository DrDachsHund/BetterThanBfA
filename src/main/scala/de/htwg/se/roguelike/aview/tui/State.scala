package de.htwg.se.roguelike.aview.tui

trait State {
  def processInputLine(input: String)

  def handle()
}


/*TODO !!!!!!!!!!!!!!!:

WICHTIG BEIM EINLESEN VON INDEX GEHTS NICHT ÜBER 10 MASIVER FEHLER RIPRIP

Controller vll umbauen und nen enemy extra setzen in interact für bessere/einfachere behandlung und ausgaben!!!!

//0 Grass,1 Portal, 2 Flowers, 3 Enemy, 4 Merchant
//textures maybe 1-5 und je nach rarity noch +5 ??!?!??!?!?!
//pause with esc anstatt toolbar für besseres scaling maybe??
  => da dann maybe auch safe

maybe stamina

Tests:
  => GUI:
    => Start Screen mit options and load maybe                                                               (Marvin)[X}
    => Game Over Screen => noch buttons und eventuel generell bessermachen
    => Fight Section => noch buttons und maybe iwi animation anzeige ka wie viel dmg gegner player macht
    => Inventory => noch ein paar buttons mit ImageIcon versehen

    => maybe noch ne methode in inventory die die items automatisch nach wert atk oder rarity sortiert für bessere übersicht

MUSIC!!! => geht mit eventuel clip da kann mann dan auch iwi ton einstellen und loopen!??!?!!?!?

EXTRA FÜR SPÄTER:

LOOT: - 50 Wichtig-
  => Boss                                                                                                            [X}
    => BossItem                                                                                                      [X}
        => Special                                                                                                   [X}

Player: -10 Wichtig-
  => SkillPunkte????  !!                                                                                      (Andrê)[X}
  => Zukunft Klassen (VLLT nur Methode); Abilities?????  !                                                    (Andrê)[X}

Fight: - 50 Wichtig-
  => Armor/Weapon Specials !!!                                                                                       [X}

Level: - 10 Wichtig-
  => Truhen                                                                                                   (Andrê)[X}
  => Maybe Boss Level alle 5 (BOSS ITEMS), Portale/Höhlen etc. ! => in Interact anpassen                             [X}
  => MAYBE Hindernisse BIG FRAGEZEICHEN ????????????????????????                                                     [X}
  => Eingang Häuser,Shop                                                                                             [X}

Enemy Resistance + Weapon Interaction (FIRE 2x DMG to ICE RES)

ARMOR/Weapon vergleichen ROT die Schlecht sind Grün gut (WoW)

*/