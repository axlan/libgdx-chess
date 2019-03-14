package com.axlan.gdxchess;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class GDXChess implements ApplicationListener {
	private SpriteBatch batch;
	
	Board board;
	
	GameState gameState;
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		TextureAtlas myTextures = new TextureAtlas("data/chess_sprites.txt");
		
		int size=(int)Math.min(h,w);
		
		board=new Board(size,myTextures);
		
		batch = new SpriteBatch();
		
		gameState = new GameState(size,board);
		Gdx.input.setInputProcessor(gameState);
		
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		board.Draw(batch);
		
		gameState.Draw(batch);
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
