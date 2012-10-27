package com.Broders.Entities;

import com.Broders.Logic.CoreLogic;
import com.Broders.Logic.Pos;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Ship extends Entities{	

	private Boolean thrust;

	private Texture texture;
	private Sprite sprite;

	public Ship(String id, EntityType type) {
		super(id, type);		

		{			
			Vector2 vertices[] = new Vector2[3];
			vertices[0] = new Vector2(0.0f, 1.0f);
			vertices[1] = new Vector2(0.0f, -1.0f);
			vertices[2] = new Vector2(0.44f, 0.0f);

			PolygonShape shape = new PolygonShape();
			shape.set(vertices);

			FixtureDef fixDef = new FixtureDef();
			fixDef.shape = shape;
			fixDef.density = 6.0f;

			BodyDef bodDef = new BodyDef();
			bodDef.type = BodyType.DynamicBody;
			bodDef.angularDamping = 5.0f;
			bodDef.linearDamping = 0.1f;

			bodDef.position.set(0.0f, 0.0f);
			bodDef.angle = MathUtils.PI;
			bodDef.allowSleep = false;
			super.createBody(bodDef, fixDef);
		}

		super.setSprite(((ShipType)type.getSubType()).getSprite1Path());
		super.getSprite().flip(false, true);
		super.getSprite().setScale(.05f, .05f);
		super.getSprite().setColor(Color.MAGENTA);
		super.getBody().getAngle();

		this.thrust = false;
		this.setSprite(((ShipType)type.getSubType()).getSprite1Path());
		this.getSprite().flip(false, true);
		this.getSprite().setScale(.05f, .05f);
		this.getSprite().setColor(Color.MAGENTA);
		this.getBody().getAngle();
	}

	public Boolean getThrust(){
		return this.thrust;
	}

	public void setThrust(boolean bool){
		this.thrust = bool;
	}

	@Override
	public void Draw(SpriteBatch sb, CoreLogic cl) {

		float screenWidth =  Gdx.graphics.getWidth();
		float screenHeight =  Gdx.graphics.getHeight();

		float x = super.getBody().getPosition().x;
		float y = super.getBody().getPosition().y;



		float posX;
		float posY;

		//this will only work for single player
		posX = screenWidth*(x/cl.getWidth());
		posY =  screenHeight*(y/cl.getHeight());


		if(this.getThrust()){
			this.sprite.setPosition(posX, posY);
			this.sprite.setRotation((float)super.getAngle());
			this.sprite.draw(sb);
		}else{
			super.getSprite().setPosition(posX, posY);
			super.getSprite().setRotation((float)super.getAngle());
			super.getSprite().draw(sb);
		}
	}

}
