# Tic Tac Toe Game
<p align="center"><img width="350" src = "https://github.com/user-attachments/assets/2c73ec97-8dbe-41b9-ae38-7dcede090cc8"></p>
<p align="center" >
  <a href="#Overview">Overview</a> •
  <a href="#Features">Features</a> •
  <a href="#Algorithm">Algorithm</a> •
  <a href="#getting-started">Getting Started</a> •
  <a href="#Screenshots">Screenshots</a> •
  <a href="#Future-Improvements">Future Improvements</a> 
</p>

## Overview
This is a simple Tic Tac Toe application for Android using Kotlin. It features gameplay between two players (X and O),  the ability to save and replay game history, and a user-friendly interface with dynamic colors for players. The app provides two modes of play:
1. Single Player: Play against an AI opponent.
2. Two Player: Play with a friend on the same device.

## Features

- **Game Modes :** Single Player (Challenge yourself against a smart AI opponent.) and Two Player (Enjoy the game with a friend.)

- **Replay Functionality:** View the history of moves in the game.

- **Win Detection:** Automatically detects the winner or a draw.

- **Simple UI:** The app features an easy-to-navigate user interface.

## Algorithm
**Single Player Mode (AI Logic)**

- Check if the AI can win in the next move.

- Block the player’s winning move if possible.

- Choose a random position if no immediate winning/blocking move is available.

**Win Detection Algorithm**

- Check all possible winning conditions (Rows, columns, and diagonals.)

- If any condition matches, declare the winner.

- If the board is full and no winner, declare a draw.


## Getting Started

To run this app on your local machine or Android device, follow these steps:
1. Clone this repository to your local machine using `git clone https://github.com/............
2. Open the project in Android Studio or your preferred Android development environment.
3. Build and run the application on an Android emulator or a physical Android device.


## Screenshots
<p align="center"><img width="250" src = "https://github.com/user-attachments/assets/a230e943-4fc9-4cde-a789-90d6f10f3f59">   <img width="250" src = "https://github.com/user-attachments/assets/fc0c35d5-82e8-4ad9-9254-92c5844fbd0a">   <img width="250" src = "https://github.com/user-attachments/assets/064a8d6d-cd6e-4c94-a347-7322f7669d24"> </p>

## Future Improvements

- Add online multiplayer functionality.

- Enhance AI to use a minimax algorithm for smarter moves.

- Implement a leaderboard to track player scores.
