package com.Broders.Screens;

import java.util.LinkedList;

import com.Broders.Entities.*;
import com.Broders.Logic.CoreLogic;
import com.Broders.Logic.InputDir;
import com.Broders.Logic.Pos;
import com.Broders.Logic.tail;
import com.Broders.mygdxgame.BaseGame;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Shape.Type;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.OrderedMap;




public class GameScreen implements Screen{






	private BaseGame myGame;
	private boolean Multiplayer;
	private boolean DEBUG;
	private boolean THRUSTER;

	private EntityType type;
	private Ship PlayerShip;


	private SpriteBatch spriteBatch;

	private Texture btail;
	private Texture Ship;

	private Sprite Tailsprite;

	private tail Tail;
	private OrderedMap<String,Entities> EntityMap;

	private BitmapFont font;
	
	private CoreLogic core;
	
	float xx;
	float yy;



	public GameScreen(BaseGame game, boolean m){
		this.myGame = game;
		this.Multiplayer = m;

		if(m){
			System.out.println("Multi");
		}else{
			System.out.println("Single");
		}


		Tail = new tail(5);
		font = new BitmapFont();
		DEBUG = true;
		THRUSTER = false;

		EntityMap = new OrderedMap<String, Entities>();
		core = new CoreLogic();
		core.initCore();
		PlayerShip = core.getShip();
		EntityMap.put("player", PlayerShip);
		
		xx = Gdx.graphics.getWidth();
		yy = Gdx.graphics.getHeight();

	}

	@Override
	public void render(float delta) {

		//handle Input and update Backend
		//it is up to the backend team to decide if they want to handle input seperatly or not
		HandleInput(delta);
		Update();

		//server interactions here?

		//update the models on the screen
		Paint();


	}

	private void Paint() {
		GL10 g1 = Gdx.graphics.getGL10();
		Gdx.gl.glClearColor(0, 0, 0.2f, 1); //its blue so you know you changed screens
		g1.glClear(GL20.GL_COLOR_BUFFER_BIT);



		spriteBatch.begin();


		Tail.draw(spriteBatch);


		for(Entry<String, Entities> E :EntityMap.entries()){
			E.value.Draw(spriteBatch, core);
		}


		if(DEBUG){
			String out;

			

			out = String.format("Ship Pos in Meters: (%f,%f) ", PlayerShip.getBody().getPosition().x,PlayerShip.getBody().getPosition().y);
			font.draw(spriteBatch, out, xx * .01f, yy-(yy * .01f));
			
			if(THRUSTER)
				font.draw(spriteBatch, "Thruster", xx * .01f, yy-(yy * .05f));

		}

		spriteBatch.end();




	}

	private void Update() {

		//EntityMap.get("player").SetPos(new Pos(.45f, .25f));
		Tail.Update();

	}

	private void HandleInput(float delta) {

		//Special Debug keys
		if(Gdx.input.isKeyPressed(Keys.F1)){
			double x = ((float)Gdx.input.getX()/(float)Gdx.graphics.getWidth());
			double y = ((float)Gdx.input.getY()/(float)Gdx.graphics.getHeight());
			System.out.println("Mouse Pos: "+x+" "+y);
		}

		if(Gdx.input.isKeyPressed(Keys.F2)){

			System.out.println("Mouse Pos: "+Gdx.input.getX()+" "+Gdx.input.getY());
		}

		if(Gdx.input.isKeyPressed(Keys.F3)){
			System.out.println("Resize: "+Gdx.graphics.getWidth()+" "+Gdx.graphics.getHeight());
		}


		//touch tail
		if(Gdx.input.isTouched()){
			Tail.add(new Pos(Gdx.input.getX(),Gdx.input.getY()));
		}

		if(Gdx.input.isKeyPressed(Keys.UP)){
			core.execute(delta, InputDir.FORWARD);
			THRUSTER = true;
		}else{
			THRUSTER = false;
		}
		

		//Backout to main menu
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)){
			myGame.setScreen(myGame.GetMain());
		}
	}

	@Override
	public void resize(int width, int height) {


	}

	@Override
	public void show() {


		Ship = new Texture(Gdx.files.internal("data/bullet.png"));

		spriteBatch = new SpriteBatch();

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {



	}

}
