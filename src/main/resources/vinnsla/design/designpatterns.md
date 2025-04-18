Notað Hönnunarsniðmát: 
Observer Pattern

Tilgangur: 
Uppfæra notendaviðmót sjálfkrafa þegar breytingar verða á viðskiptalógík (business logic).

Notkun í verkefninu:
JavaFX Property hlutar gera kleift að fylgjast með breytingum.


Hlutverk:	      Klasi:
Subject	          Leikmadur, Leikur, Teningur (þeir hafa Property)
Observer	      SlangaController (bindur UI við property)

Dæmi: 
fxSkilabod1.textProperty().bind(leikur.getLeikmadur(0).reiturProperty().asString("Reitur: %d"));