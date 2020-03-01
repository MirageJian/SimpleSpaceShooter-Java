# Simple Space Shooting Game

It is based on a 'Game Template'.

## Graphics Resources

Main source of this game: [Opengameart](http://opengameart.org) 

Space ship (player) and enemy [Link](https://opengameart.org/content/space-shooter-ships-and-sprites-from-the-game-frozen-moons)

Splash [Link1](https://opengameart.org/content/splash-effect-32x32) [Link2](https://opengameart.org/content/water-splash)

Laser [Link](https://opengameart.org/content/laser-effect-sheet)

Player Bullet [Link](https://opengameart.org/content/m484-lightning-weapon)

Numbers [Link](https://opengameart.org/content/numbers-collection)

Explosion [Link](https://opengameart.org/content/explosion)

Background [Link](https://opengameart.org/content/grid-hud)

Background for start and in game [Link](https://opengameart.org/content/stars-parallax-backgrounds)

[Pickups](https://opengameart.org/content/pickup-items-icons)

[Effect of bullet and laser](https://opengameart.org/content/explosions-2)

## Sound Resources

Main sources from [Opengameart](http://opengameart.org) as well

[Start Screen Background Music Orchestral](https://opengameart.org/content/space-orchestral)

[Background Music](https://opengameart.org/content/space-1)

[Player Explosion](https://opengameart.org/content/explosion-0) 

[Laser hit](https://opengameart.org/content/force-field-electric-hum)

[Pickup when it is picked up](https://opengameart.org/content/positive-item-pickup-yo-frankie)

[Enemy Fire](https://opengameart.org/content/q009s-weapon-sounds)

## Other Resources

[Mouse Control Source Code](https://www.gamedev.net/tutorials/programming/general-and-gameplay-programming/java-games-keyboard-and-mouse-r2439/)

## Introduction

This is a shooting game. Player control a spaceship to kill enemies and gain high score. There is no end for this game until player was killed by enemies’ bullets. However, the difficulty will increase if player survival longer.

It has 2 game modes, single and double player. There are two types of pickups, weapon upgrade package and shield. Spaceships have two types of weapons, bullets and laser. There also are 7 types of enemies in this game. They have different fire pattern and movement pattern.

Laser can penetrate enemy deal with continues damage, and it has narrow width but long height. Bullets can deal damage when reach enemy ships, and they have wider fire range. Shield can withstand bullets, but shield time will reduce when it is hit by bullets.

By the way, this game is based on a template. The code contains template information.

### Textures

Almost all textures in the template have been replaced.

**Background**: Change start background and make it scroll. There are meteor effects on the background.

**Player**: Different player have different spaceship. Add engine fire effect with spirte.

**Bullet, laser and their hit effect**: Add hit effects for bullet and laser. Bullet will blast when it deals damage. Laser can create small blue blast when dealing damage.

**Explosion**: Add explosion effect. Enemy and player will explode when they die.

**Shield**: Add shield texture.

**Enemy**: Increase types of enemy. There can fire different color of bullets.

**Sound**: Add background music, hit sound, explosion sound etc.

### Features

**General**: Modify the whole structure. For example, there are abstract classes for GameObject and EnemyObject, GameResouce to control resources such as image and sound, GlobalConst contains game basic config const like frame rate, CMath does some custom calculation, ui package etc. These thing make project have better expandability.

**Optimization**: Game Resource is the object for Game Object. It stores the resource that loaded before user enter the game. Make game smoother than template.

**Bullets fire**: Fire cooldown time (10 frames). Fire angle can be increase by bullets lv. Bullets will remove after reach boundaries and add a blast effect.

**Laser and weapon changing**: Use “R” to change weapon between laser and bullet. Laser has penetration effect and deal damage continuously.

**Pickups**: There are 2 types of pickups. When it reaches boundaries, it will bounce back to opposite direction. Dead enemy may generate a pickup.

**Shield**: When player pick a shield up, the shield time will increase 10s. It will reduce by time or enemy bullet.

**Weapon upgrade**: Weapon upgrade packages increases the level of current weapon and increase damage/numbers and appearance.

**Health for enemy**: Every enemy has health. There will die when heal go below 0. They will take damage if laser or bullets hit on their body. Heal bar is on the top of enemy.

**Enemy fire and movement**: Enemies have different bullet pattern, which will be changed as game time increases. Different enemy also has different movement pattern.

### Integrity:

**Smoother Game**: It can reach 60 frames per second.

**Score**: The score exists on the top right of screen.

**Weapon level, shield time and other info**: On the right of game window.

**Start and end**: Game has start and end, and it has restart screen.

**Game process control**: A method for game process, the difficulty will increase with time went by.

**Instruction**: Help in start page, key instruction. Score display how to restart

### Multiple Control

**Two Control**: One uses keyboard, another one uses mouse.

**Mouse Speed Limit**: For mouse control, there is speed limit.

**Weapon System**: Player can use R or Right mouse button to change weapon.
