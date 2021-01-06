# libgdx-chess

A starter project for learning some 2D game dev. There’s no AI, so you can just plan shared screen multiplayer.

Video Demo:
[![Video Demo](https://img.youtube.com/vi/1nRo-ucrgkk/0.jpg)](https://www.youtube.com/watch?v=1nRo-ucrgkk)


# Code organization


## GDXChess.java

GDXChess is an LibGDX ApplicationListener that is the heart of the program. It handles the setup, and is the entry point for the rendering callbacks.

## create()

This function:
1. Loads the sprites from a texture file
2. Creates an instance of the `Board` class
3. Creates an instance of the `GameState` class
4. Registers the gameState instance to handle inputs

## render()
The function clears the scren then calls on the board and gamestate objects to add there drawings.

# Board.java

This class manages the state of the chess board (what pieces are in which squares) and handles drawing the board and the pieces.

It also provides functions for moving the pieces around the board, but does not do anything to check for valid moves.

## Board(int size,TextureAtlas pieceAtlas)

This constructor handles initializing the board state (placing the pieces), and setting up the sprites that will be used to draw the pieces.

## GenerateTexture()

This function creates a Pixmap with the checkerboard pattern and creates a texture from it for drawing the board.

# GameState.java

This class handles the higher level game state of controlling who's turn it is, and handling selecting and pieces and choosing moves. It checks for valid moves and limits the player to selecting from them.

It also handles drawing a guide that shows the selected piece and allowed moves.

## GameState(int size, Board board)

This constructor starts with setting the active player to White, and creates a sprite for the rectangle for indicating selected pieces and valid moves.

## touchDown(int x, int y, int pointer, int button)

This function updates the game state based on the tile that is selected by clicking on it. It first needs to translate the clicked pixel to a chess square, then if the player selects a piece it will show the valid moves. In this state selecting one of the indicated moves chooses it. Otherwise the player can select a different piece. Once a move is made the turn switches to the other player.

# PieceInfo.java

A class that encodes the identity of a piece (the team and piece type)

# Rules.java

A class that encodes the valid moves for each piece type. It provides functions that generate the lists of valid moves for each piece.
