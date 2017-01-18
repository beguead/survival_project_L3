package managers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import characters.Parasite;
import characters.Entity;
import dungeon.Maze;
import dungeon.Portal;
import items.BlueCore;
import items.Core;
import items.GreenCore;
import items.WhiteCore;
import items.YellowCore;
import screens.GameScreen;
import characters.Player;

public class SensorManager implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
			
		if (isContactBetween(fa, fb, Player.class, Parasite.class)) {
			
			Parasite s;
			boolean near_of_player;

			if (fa.getUserData() instanceof Player) {
			
				s = (Parasite)(fb.getUserData());
				near_of_player = fb.isSensor();
			
			} else {
			
				s = (Parasite)(fa.getUserData());
				near_of_player = fa.isSensor();
			
			}
			
			if (near_of_player) {
				
			}
		} else {
			
			if (isContactBetween(fa, fb, Player.class, Core.class)) {
					
					if (Maze.core_near_the_player == null) {
						
						Core c;
						if (fa.getUserData() instanceof Core) {
							
							if (fa.getUserData() instanceof BlueCore) c = (BlueCore)(fa.getUserData());
							else	if (fa.getUserData() instanceof GreenCore) c = (GreenCore)(fa.getUserData());
									else	if (fa.getUserData() instanceof YellowCore) c = (YellowCore)(fa.getUserData());
											else c = (WhiteCore)(fa.getUserData());
							
						} else {
							
							if (fb.getUserData() instanceof BlueCore) c = (BlueCore)(fb.getUserData());
							else	if (fb.getUserData() instanceof GreenCore) c = (GreenCore)(fb.getUserData());
									else	if (fb.getUserData() instanceof YellowCore) c = (YellowCore)(fb.getUserData());
											else c = (WhiteCore)(fb.getUserData());						
							
						}
						Maze.core_near_the_player = c;	
					}
				
			} else	if (isContactBetween(fa, fb, Player.class, Entity.class)) GameScreen.spark_catched = true;
					else if (isContactBetween(fa, fb, Player.class, Portal.class)) System.out.println("EndOfTheGame");
			
		}
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		if (isContactBetween(fa, fb, Player.class, Core.class)) {
			
			if (Maze.core_near_the_player != null) Maze.core_near_the_player = null;
			
		}
	}
		
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {}

	private boolean isContactBetween(Fixture fa, Fixture fb, Class<?> ca, Class<?> cb) {
		
		return ((ca.isInstance(fa.getUserData()) && cb.isInstance(fb.getUserData())) || (cb.isInstance(fb.getUserData()) && ca.isInstance(fa.getUserData())));
				
	}
	
}
