package utilities;

public final class Constants {
	
	private Constants() {} //Static class

	/*Application*/
	public static final String GAME_TITLE = "Projet L3";
    public static final int APP_WIDTH = 1200;
    public static final int APP_HEIGHT = 800;
    
    /*Conversion*/
    public static final float PPM = 32f;
    public static final float TO_DEGREE = (float)(180f / Math.PI);

    /*Camera*/
    public static final float CAMERA_LERP = 0.05f;
    
    /*Map*/
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;
    public static final int MAP_WIDTH = 21;
    public static final int MAP_HEIGHT = 21;
    
    /*Filtering bits*/
    public static final short WALL_FILTER = 0b1;
    public static final short LIGHT_FILTER = 0b10;
    public static final short CHARACTER_FILTER = 0b100;
    public static final short HALF_WALL_FILTER = 0b1000;
    public static final short BRAZIER_FILTER = 0b10000;
    
    public static final float FRAME_DURATION = 0.2f;
    
    
    
}
