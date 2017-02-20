package utilities;

public final class Constants {
	
	private Constants() {} //Static class

	/*Application*/
	public static final String GAME_TITLE = "Halo Delta";
    public static final int APP_WIDTH = 1200;
    public static final int APP_HEIGHT = 800;
    
    /*Conversion*/
    public static final float PPM = 128f;
    public static final double TO_DEGREE = 180d / Math.PI;

    /*Camera*/
    public static final float CAMERA_LERP = 0.05f;
    
    /*Map*/
    public static final int TILE_WIDTH = 128;
    public static final int TILE_HEIGHT = 128;
    public static final int MAP_WIDTH = 13;
    public static final int MAP_HEIGHT = 13;
    
    /*Lights*/
    public static final float CYAN_CORE_CONE_DEGREE = 5f;
    public static final float CYAN_CORE_DISTANCE = 2.5f;
    
    public static final float BLUE_CORE_CONE_DEGREE = 5f;
    public static final float BLUE_CORE_DISTANCE = 2.5f;
    
    public static final float PURPLE_CORE_CONE_DEGREE = 3f;
    public static final float PURPLE_CORE_DISTANCE = 30f;
    
    public static final float ORANGE_CORE_CONE_DEGREE = 45f;
    public static final float ORANGE_CORE_DISTANCE = 2f;
    
    public static final float RED_CORE_CONE_DEGREE = 180f;
    public static final float RED_CORE_DISTANCE = 1.5f;
    
    /*Filtering bits*/
    public static final short LIGHT_FILTER = 1; 	//0b1
    public static final short PLAYER_FILTER = 2;	//0b10
    public static final short ENEMY_FILTER = 4;		//0b100
    public static final short SENSOR_FILTER = 8;	//0b1000
    public static final short WALL_FILTER = 16;		//0b1 0000
    public static final short ITEM_FILTER = 32;		//0b10 0000

    public static final String T0 = "Bonjour dépositaire ! Besoin d'aide ?";
    
    public static final float FRAME_DURATION = 0.2f;
    
}
