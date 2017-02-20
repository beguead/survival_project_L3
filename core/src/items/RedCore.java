package items;

import com.badlogic.gdx.graphics.Color;

import utilities.Constants;

public class RedCore extends Core {

		@Override
		public float getDistance() { return Constants.RED_CORE_DISTANCE; }

		@Override
		public float getConeDegree() { return Constants.RED_CORE_CONE_DEGREE; }
		
		@Override
		public Color getColor() { return Color.RED; }

}
