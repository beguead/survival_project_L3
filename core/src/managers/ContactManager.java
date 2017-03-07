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
import items.Core;
import items.Fragment;
import screens.GameScreen;
import utilities.Constants;
import utilities.Constants.PARASITE_STATES;
import characters.Player;

public class ContactManager implements ContactListener {
	
	private static ContactManager INSTANCE = new ContactManager();
	
	private ContactManager() {};
	
	public static ContactManager getInstance() { return INSTANCE; }

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
			
			if (!sensor) GameScreen.getInstance().setEnd(Constants.PLAYER_CATCHED); //The player has been catched by the parasite
			else {
				
				p.setNearPlayer(true);
				Maze.player.setHunted(true);
				
			}  //A parasite has feels the player
			
			
		} else {
			
			if (isContactBetween(fa, fb, Player.class, Core.class)) {
					
				if (Maze.core_near_the_player == null) {
						
					Core c = fa.getUserData() instanceof Core ? (Core)fa.getUserData() : (Core)fb.getUserData();
						
					c.setAuraActive(true);
					Maze.core_near_the_player = c;
						
				}
				
			} else {
				
				if (isContactBetween(fa, fb, LightBarrier.class, Parasite.class)) {

					if (fa.getUserData() instanceof LightBarrier) {

						if (!fa.isSensor() && fa.getBody().isActive())
							((Parasite)(fb.getUserData())).setState(PARASITE_STATES.stunned);
						
					} else {

						if (!fb.isSensor() && fb.getBody().isActive())
							((Parasite)(fa.getUserData())).setState(PARASITE_STATES.stunned);
						
					}
				} else {
				
					if (isContactBetween(fa, fb, Player.class, Fragment.class)) {
					
						if (fa.getUserData() instanceof Fragment) ((Fragment)(fa.getUserData())).catched = true;
						else ((Fragment)(fb.getUserData())).catched = true;
				
					} else	if (isContactBetween(fa, fb, Player.class, Portal.class))
								GameScreen.getInstance().setEnd(Constants.PLAYER_ESCAPED);
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
				
				if (fa.getUserData() instanceof Parasite) ((Parasite)(fa.getUserData())).setNearPlayer(false);
				else ((Parasite)(fb.getUserData())).setNearPlayer(false);
				
				Maze.player.setHunted((Maze.parasiteNearPlayer()));
			
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
