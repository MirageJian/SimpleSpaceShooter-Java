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

This is very simple shooter game. It has 2 game modes, single and double player. More instruction in the first screen of Game.

### Textures

Changing background, and make it move. Make it look good and simulate flying

Scrolling background. Change almost all texture.

Engine fire, bullet direction.

**Bullet hit effect**

### Features

**General**: Modify the whole structure, different class, abstract class for `GameObject` and `EnemyObject`.

**Upgrade expandability**: Global Const for game basic config, such as speed of object and frame rate. CMath library.

**Optimization**: Game Resource is the object for Game Object. It stores the resource that loaded before user enter the game. Make game more smooth than original one.

**Bullets fire**: Fire cooldown period. Fire angle is different. Bullets will remove after reach boundaries.

**Pickup system**: there are 2 types of pickups. Pickups will not beyond the boundaries. Enemy died will generate pickup. When it reaches boundaries, it will rebound to opposite direction.

**Weapon upgrade system**: Bullet and laser has nineth level of weapon. Each level increase damage/numbers and appearance.

**Health system**: Every enemy has health. There will die when heal go below 0.

**Enemy fire**: Enemies have different bullet pattern, which will be changed as game time increases.

**Heal bar for enemy**: Enemies have heal. They will take damage if laser or bullets hited.

### Integrity:

**Smoother Game**: It can reach 60 frames per second.

**Score**: The score exists on the top right of screen

**Start and end**: Game has start and end, and it has restart screen.

**Game process control**: A method for game process, the difficulty will increase with time went by.

**Instruction**: Help in start page, key instruction. Score display how to restart

### Multiple Control

**Two Control**: One uses keyboard, another one uses mouse.

**Mouse Speed Limit**: For mouse control, there is speed limit.

**Weapon System**: Player can use R or Right mouse button to change weapon.
