INI(HOU) - Controller Concern
===
[![Build Status](https://travis-ci.org/sepr-hou/ini-hou.png?branch=master)](https://travis-ci.org/sepr-hou/ini-hou)

INI The Pharoah (or the airport) -- Now owned by HOU.

Code structure
---

### Game logic files
All the mains files (that actually consist of the game logic) can be found under the ini-atc folder.

### Assets 
Using a little workaround, all assets are now in the desktop project folder and can be found simply under ini-atc-desktop -> assets
~~Due to limitation of Android (and the creators of the graphics library wanting to support Android seamlessly), all assests must be in the 'ini-atc-android' project, specifically folder 'assets'.~~



### Launching the game (during development)
To launch to game on a PC, you have to run the application from the 'ini-atc-desktop' project. Open up the file 'Main.java' and just run the game from there. Obviously it can be compiled into a jar file and ran from there, but for development purposes the game is started as instructed before.
