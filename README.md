Black Jack
=========

This program implements the basic logic of the Black Jack game between two players: casino-player and human-player. Casino-player follows simple "stand on 17" rule. Human-player interacts with user via standard input/standard output. This implementation supports the following Black Jack actions:

HIT - Player takes one card from Dealer,

SPLIT - If Player have two equally valued cards in hand, e.g. two 7s, or a King and a 10 (They both have value of 10), then it is possible to create two separate hands out of this one, deal one card to each one of them.

DOUBLE - Player can choose to double the bet, take one card and stand.

STAND - Player makes a current hand final.

SURRENDER - Player surrenders from the game and lose half of the bet.

Various actions might have various specific limitations. The game will end if any of the players run out of money or human-player quits the game.

Requirements:
- JDK 1.6 or higher

Installation:
- Download and unpack the source code to the download folder.
- Build the JAR file by invoking the following command in the installation folder, e.g. a folder containing build.xml file, from command line:

$ant jar

Usage:
- Once the JAR file is built, you can run the code by invoking:

$java -jar BlackJack.jar

Enjoy!
