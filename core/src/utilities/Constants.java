package utilities;

public final class Constants {
	
	private Constants() {} //Static class

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
    public static final int MAP_WIDTH = 25;
    public static final int MAP_HEIGHT = 25;
    
    /*Lights*/
    public static final float WHITE_CORE_CONE_DEGREE = 4f;
    public static final float WHITE_CORE_DISTANCE = 4f;
    
    public static final float YELLOW_CORE_CONE_DEGREE = 1f;
    public static final float YELLOW_CORE_DISTANCE = 20f;
    
    public static final float GREEN_CORE_CONE_DEGREE = 30f;
    public static final float GREEN_CORE_DISTANCE = 3f;
    
    /*Filtering bits*/
    public static final short LIGHT_FILTER = 1; 	//0b1
    public static final short PLAYER_FILTER = 2;	//0b10
    public static final short ENEMY_FILTER = 4;		//0b100
    public static final short SENSOR_FILTER = 8;	//0b1000
    public static final short WALL_FILTER = 16;		//0b1 0000
    public static final short ITEM_FILTER = 32;		//0b10 0000

    
    public static final float FRAME_DURATION = 0.2f;
    
    public static final int SPRITE_SIZE = 32;
    
}
