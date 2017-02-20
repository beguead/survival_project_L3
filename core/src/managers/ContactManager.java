package managers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import characters.Parasite;
import dungeon.LightBarrier;
import dungeon.Maze;
import dungeon.Portal;
import items.BlueCore;
import items.Core;
import items.OrangeCore;
import items.Particle;
import items.CyanCore;
import items.PurpleCore;
import items.RedCore;
import screens.GameScreen;
import characters.Player;

public class ContactManager implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
			
		if (isContactBetween(fa, fb, Player.class, Parasite.class)) {
			
			Parasite p;
			boolean sensor;
			if (fa.getUserData() instanceof Parasite) {

				p = (Parasite)(fa.getUserData());
				sensor = fa.isSensor();
			
			} else {

				p = (Parasite)(fb.getUserData());
				sensor = fb.isSensor();
			
			}
			
			if (sensor) p.isNearOfThePlayer(true); //The parasite is near of the player
			else /*GameScreen.end = true*/; //The player has been catched by the parasite
			
		} else {
			
			if (isContactBetween(fa, fb, Player.class, Core.class)) {
					
					if (Maze.core_near_the_player == null) {
						
						Core c;
						if (fa.getUserData() instanceof Core) {
							
							if (fa.getUserData() instanceof BlueCore) c = (BlueCore)(fa.getUserData());
							else	if (fa.getUserData() instanceof OrangeCore) c = (OrangeCore)(fa.getUserData());
									else	if (fa.getUserData() instanceof PurpleCore) c = (PurpleCore)(fa.getUserData());
											else c = (CyanCore)(fa.getUserData());
							
						} else {
							
							if (fb.getUserData() instanceof BlueCore) c = (BlueCore)(fb.getUserData());
							else	if (fb.getUserData() instanceof OrangeCore) c = (OrangeCore)(fb.getUserData());
									else	if (fb.getUserData() instanceof PurpleCore) c = (PurpleCore)(fb.getUserData());
											else	if (fb.getUserData() instanceof RedCore) c = (RedCore)(fb.getUserData());
													else c = (CyanCore)(fb.getUserData());						
							
						}
						
						c.setAuraActive(true);
						Maze.core_near_the_player = c;
						
					}
				
			} else {
				
				if (isContactBetween(fa, fb, LightBarrier.class, Parasite.class)) {
					
					if (!(fa.isSensor() || fb.isSensor())) {
						
						if (fa.getUserData() instanceof Parasite) ((Parasite)(fa.getUserData())).setStunned();
						else ((Parasite)(fb.getUserData())).setStunned();
					
					}
					
				} else {
				
					if (isContactBetween(fa, fb, Player.class, Particle.class)) {
					
						if (fa.getUserData() instanceof Particle) ((Particle)(fa.getUserData())).catched = true;
						else ((Particle)(fb.getUserData())).catched = true;
				
					} else if (isContactBetween(fa, fb, Player.class, Portal.class)) GameScreen.end = true;
				}
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		if (isContactBetween(fa, fb, Player.class, Core.class)) {
			
			if (Maze.core_near_the_player != null) {
				
				Maze.core_near_the_player.setAuraActive(false);
				Maze.core_near_the_player = null;
				
			}
			
		} else if (isContactBetween(fa, fb, Player.class, Parasite.class)) {
				
				if (fa.getUserData() instanceof Parasite) ((Parasite)(fa.getUserData())).isNearOfThePlayer(false);
				else ((Parasite)(fb.getUserData())).isNearOfThePlayer(false);
			
		}
	}
		
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {}

	private boolean isContactBetween(Fixture fa, Fixture fb, Class<?> ca, Class<?> cb) {
		
		return ((ca.isInstance(fa.getUserData()) && cb.isInstance(fb.getUserData())) || (ca.isInstance(fb.getUserData()) && cb.isInstance(fa.getUserData())));
				
	}
	
}
