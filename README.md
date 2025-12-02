# 1. Feladat  
Amőba játékleírása 
Az amőba egy klasszikus logikai játék, amelyben a stratégiai gondolkodás kulcsfontosságú. 
A játékot két játékos játssza egymás ellen: lehet két ember, vagy egy ember és számítógép. 
Mindkét játékos egy-egy jelet használ: az egyik a kört (O), a másik az ikszet (X). 
A cél az, hogy a játékos saját jeléből ötöt egymás mellé helyezzen el vízszintesen, függőlegesen 
vagy átlósan a játéktáblán. Aki ezt először megteszi, megnyeri a játékot. 
# 2. Feladatspecifikáció 
A feladat egy egyszerű, klasszikus amőba játék megtervezése és megvalósítása Java nyelven, 
objektumorientált programozás és grafikus felhasználói felület (GUI) alkalmazásával. 
A játéktábla (pálya) 15×15 mezőből áll. 
## Főmenü 
A program indításakor egy menü jelenik meg, amely három menüpontot tartalmaz: 
• Beállítások: a játék paramétereinek testreszabása 
➢ az alakzat beállítása (kör = A játékos; iksz = B játékos) 
➢ a játékmódja (1 = gép ellen (bot); 2 = két játékos) 
➢ ha gép ellen játszunk 
▪ a kezdés sorrendje 
▪ a nehézségi szint (könnyű, közepes, nehéz)  
• Játék (új játék): új játék indítása 
• Kilépés: a játékból való kilépés 
## A játék menete: 
Az „Új játék” gomb megnyomása után megjelenik a játéktábla, és a felső sávban látható, hogy 
ki kezdi a játékot. A játékos a pálya egyik üres mezőjére kattintva helyezheti el saját 
alakzatunkat. A játék során a cél, hogy a saját jeleinkből öt darabot egymás mellé helyezzünk 
el vízszintesen, függőlegesen vagy átlósan. 
A program ellenőrzi a játék állapotát (sor, oszlop, átló), ha valamelyik játkos nyer vagy győz, 
akkor a megadott a nyerő pontok színt váltanak. 
Ritkább esetekben van olyan amikor a játék kimenete döntetlen, ez abban az esetben fordulhat 
elő, mikor egy játékos sem tudta kirakni a saját jeléből egymás mellé az öt darabot. 
## Játék vége 
A játék befejezése után a program visszatér a főmenübe, ahol a játékos új játékot indíthat, 
módosíthatja a beállításokat, vagy kiléphet az alkalmazásból.
