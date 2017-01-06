package managers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import characters.Berserker;
import characters.Player;
import dungeon.Wall;
import utilities.Constants.berserker_state;

public class SensorManager implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
			
		if ((fa.getUserData() instanceof Player && fb.getUserData() instanceof Berserker) || (fa.getUserData() instanceof Berserker && fb.getUserData() instanceof Player)) {
			
			Berserker b;
			boolean hearing; //The Berserker has heard the player

			if (fa.getUserData() instanceof Player) {
				
				b = (Berserker)(fb.getUserData());
				hearing = fb.isSensor();
				
			} else {
				
				b = (Berserker)(fa.getUserData());
				hearing = fa.isSensor();
				
			}
				
			if (hearing) b.canHearThePlayer(true);
			else /*Maze.player.die()*/;
			
		} else {
			
			if ((fa.getUserData() instanceof Wall && fb.getUserData() instanceof Berserker) || (fa.getUserData() instanceof Berserker && fb.getUserData() instanceof Wall)) {
				
				Berserker b;
				boolean hearing; //The Berserker has heard the player

				if (fa.getUserData() instanceof Wall) {
				
					b = (Berserker)(fb.getUserData());
					hearing = fb.isSensor();
				
				} else {
				
					b = (Berserker)(fa.getUserData());
					hearing = fa.isSensor();
				
				}
				
				if (!hearing) {
					
					if (b.getState() == berserker_state.Rage && System.currentTimeMillis() >= b.getTimer() - 3500) b.majState(berserker_state.Stunned);
					else b.purchaseThePlayer();
					
				}
			}
		}		
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		if ((fa.getUserData() instanceof Player && fb.getUserData() instanceof Berserker) || (fa.getUserData() instanceof Berserker && fb.getUserData() instanceof Player)) {
			
			Berserker b;
			boolean hearing; //The Berserker has heard the player

			if (fa.getUserData() instanceof Player) {
				
				b = (Berserker)(fb.getUserData());
				hearing = fb.isSensor();
				
			} else {
				
				b = (Berserker)(fa.getUserData());
				hearing = fa.isSensor();
				
			}
				
			if (hearing) b.canHearThePlayer(false);

		}
	}
		
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {}

}
