/*
 * TODO: 
 * Display check status
 * Display end of game status (check vs stale mate)
 * 
 * Change to highlighting tiles by making different colored (or tinted?) textures instead of drawing rectangles
 * 
 */


package com.axlan.gdxchess;

import java.util.ArrayList;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameState implements InputProcessor {

	Board board;

	Texture overlayBoxTexture;
	Sprite overlayBoxSprite;

	int size;

	Team currentTurn;

	ArrayList<IntPoint2D> validMoves;
	IntPoint2D selected;

	GameState(int size, Board board) {
		validMoves = new ArrayList<IntPoint2D>();

		currentTurn = Team.WHITE;

		this.size = size;


		IntRect rect = board.GetRectangle(new IntPoint2D(0,0));
		int nextPow2 = Integer.highestOneBit(rect.getHeight() - 1) << 1;
		Pixmap pixmap = new Pixmap(nextPow2, nextPow2, Format.RGBA8888);
		int borderWidth=rect.getWidth()/10+1;
		pixmap.setColor(Color.WHITE);
		for (int i = 0; i < borderWidth; i++) {
			pixmap.drawRectangle(rect.getX() + i, rect.getY() + i,
					rect.getWidth() - 2 * i, rect.getHeight() - 2 * i);
		}
		overlayBoxTexture=new Texture(pixmap);
		pixmap.dispose();
		overlayBoxSprite=new Sprite(overlayBoxTexture,rect.getWidth(),rect.getHeight());
		

		this.board = board;
	}

	public void Draw(SpriteBatch batch) {

		if(selected!=null)
		{
			IntRect tile = board.GetRectangle(selected);
			overlayBoxSprite.setPosition(tile.getX(), tile.getY());
			overlayBoxSprite.setColor(Color.GREEN);
			overlayBoxSprite.draw(batch);
		}
		
		
		for(IntPoint2D moveTile:validMoves)
		{
			IntRect tile = board.GetRectangle(moveTile);
			overlayBoxSprite.setPosition(tile.getX(), tile.getY());
			Color color=(board.GetPiece(moveTile)==null)?Color.YELLOW:Color.RED;
			overlayBoxSprite.setColor(color);
			overlayBoxSprite.draw(batch);
		}
		
		
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {

	
		IntPoint2D tileIdx = board.GetPoint(x, y);

		boolean moved = false;
		for (IntPoint2D move : validMoves) {
			if (tileIdx.equals(move)) {

				board.MovePiece(selected, tileIdx);

				currentTurn = (currentTurn == Team.WHITE) ? Team.BLACK
						: Team.WHITE;
				moved = true;

			}

		}

		validMoves.clear();
		selected=null;

		if (!moved) {

			PieceInfo piece = board.GetPiece(tileIdx);

			if (piece != null) {

				if (piece.team == currentTurn) {

					selected=tileIdx;
				
					Rules.GetValidMoves(validMoves, tileIdx, piece, board);
					
					
					
				}
			}
		}

		return false;
	}
	

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
}