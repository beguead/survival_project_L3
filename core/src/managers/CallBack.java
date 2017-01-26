package managers;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;

import dungeon.LightBarrier;

public class CallBack implements QueryCallback {

	public boolean reportFixture(Fixture fixture) {
		
		if (fixture.getUserData() instanceof LightBarrier) {
			
			((LightBarrier)(fixture.getUserData())).inverseState();
			return false;
		}
		
		return true;
	}

}
