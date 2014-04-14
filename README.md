Black Jack
=========

This program implements the basic logic of the Black Jack game between two players: casino-player and human-player.

Casino-player follows simple "stand on 17" rule.

Human-player interacts with user via standard input/standard output.

This implementation supports the following Black Jack actions:

HIT, SPLIT, DOUBLE, STAND, SURRENDER.

Various actions might have various specific limitations.

The game will end if any of the player runs out of money or human-player quits the game.

Enjoy!

Requirements:
- JDK 1.6 or higher

Installation:
- Download and unpack the source code to the download folder.
- Build the JAR file by invoking the following command in the installation folder, e.g. a folder containing build.xml file, from command line:

$ant jar

Usage:
- Once the JAR file is built, you can run the code by invoking:

$java -jar BlackJack.jar
