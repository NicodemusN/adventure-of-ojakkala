package com.almasb.ojakkala;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import static com.almasb.fxgl.dsl.FXGL.*;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class Adventure extends GameApplication{
    private final EntityDeclare adventure = new EntityDeclare();
    private Entity player;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	protected void initSettings(GameSettings settings) {
		// TODO Auto-generated method stub
		settings.setTitle("Ojakkala Adventure Game");
		settings.setWidth(800);
		settings.setHeight(600);
		settings.setGameMenuEnabled(true);
		settings.setEntityPreloadEnabled(true);
		settings.setFullScreenAllowed(true);
		settings.setAppIcon("src/main/resources/textures/appicon.png");
	}
	
	public enum EntityType {
	    PLAYER, BULLET, ENEMY
	}
	@Override
	protected void initInput() {
	    onKey(KeyCode.W, new Runnable() {
			public void run() {
				getGameWorld().getSingleton(EntityType.PLAYER).translateY(-5);
			}
		});
	    onKey(KeyCode.S, new Runnable() {
			public void run() {
				getGameWorld().getSingleton(EntityType.PLAYER).translateY(5);
			}
		});
	    onKey(KeyCode.A, new Runnable(){
	    	public void run() {
	    		getGameWorld().getSingleton(EntityType.PLAYER).translateX(-5);
	    	}
	    });
	    onKey(KeyCode.D, new Runnable(){
	    	public void run() {
	    		getGameWorld().getSingleton(EntityType.PLAYER).translateX(5);
	    	}
	    });
	 
	    onBtnDown(MouseButton.PRIMARY, new Runnable() {
			public void run() {
			    double y = getGameWorld().getSingleton(EntityType.PLAYER).getY();
			    spawn("projectile", 0, y + 10);
			    spawn("projectile", 0, y + 50);
			}
		});
	}
	@Override
	protected void initGame() {
	    getGameScene().setBackgroundRepeat("appicon.png");
	 
	    getGameWorld().addEntityFactory(new EntityDeclare());
	 
	    spawn("player", 0, getAppHeight() / 2 - 30);
	 
	    run(new Runnable() {
			public void run() {
			    double x = getAppWidth();
			    double y = FXGLMath.random(0, getAppHeight() - 20);
 
			    spawn("enemy", x, y);
			}
		}, Duration.seconds(0.25));
	}
	@Override
	protected void initPhysics() {
	    onCollisionBegin(EntityType.BULLET, EntityType.ENEMY, new BiConsumer<Entity, Entity>() {
			public void accept(Entity proj, Entity enemy) {
			    proj.removeFromWorld();
			    enemy.removeFromWorld();
			}
		});
	}
	@Override
	protected void onUpdate(double tpf) {
	    List<Entity> enemiesThatReachedBase = getGameWorld().getEntitiesFiltered(new Predicate<Entity>() {
			public boolean test(Entity e) {
				return e.isType(EntityType.ENEMY) && e.getX() < 0;
			}
		});
	 
	    if (!enemiesThatReachedBase.isEmpty()) {
	        showMessage("Game Over!", new Runnable() {
				public void run() {
					getGameController().startNewGame();
				}
			});
	    }
	}
}
