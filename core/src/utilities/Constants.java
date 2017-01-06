package utilities;

public final class Constants {
	
	private Constants() {} //Static class
	
	public static enum cardinal_point {North, East, South, West};
	
	public static enum berserker_state {Normal, Rage, Stunned};

	/*Application*/
	public static final String GAME_TITLE = "My first game";
    public static final int APP_WIDTH = 1200;
    public static final int APP_HEIGHT = 800;
    
    /*Conversion*/
    public static final float PPM = 32f;
    public static final double TO_DEGREE = 180d / Math.PI;

    /*Camera*/
    public static final float CAMERA_LERP = 0.05f;
    
    /*Map*/
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;
    public static final int MAP_WIDTH = 21;
    public static final int MAP_HEIGHT = 21;
    
    /*Filtering bits*/
    public static final short WALL_FILTER = 1;
    public static final short LIGHT_FILTER = 2;
    public static final short CHARACTER_FILTER = 4;
    public static final short HALF_WALL_FILTER = 8;
    public static final short SENSOR_FILTER = 16;
    public static final short ENEMY_FILTER = 32;

    
    public static final float CHARACTER_FRAME_DURATION = 0.2f;
    public static final float BRAZIER_FRAME_DURATION = 0.4f;
    
    
    
}
