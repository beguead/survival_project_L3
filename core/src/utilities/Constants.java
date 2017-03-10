package utilities;

public final class Constants {
	
	private Constants() {}
	
	public static enum PARASITE_STATES {normal, hunting, stunned};

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
    public static final int TILE_HEIGHT = TILE_WIDTH;
    public static final int MAP_WIDTH = 15;
    public static final int MAP_HEIGHT = MAP_WIDTH;
    
    public static final int NB_FRAGMENTS = 3;
    
    /*Lights*/
    public static final float CYAN_CORE_CONE_DEGREE = 10f;
    public static final float CYAN_CORE_DISTANCE = 2.5f;
    
    public static final float PURPLE_CORE_CONE_DEGREE = 3f;
    public static final float PURPLE_CORE_DISTANCE = 10f;
    
    public static final float ORANGE_CORE_CONE_DEGREE = 30f;
    public static final float ORANGE_CORE_DISTANCE = 2f;
    
    /*Filtering bits*/
    public static final short LIGHT_FILTER = 1;
    public static final short PLAYER_FILTER = 2;
    public static final short ENEMY_FILTER = 4;
    public static final short WALL_FILTER = 8;
    public static final short ITEM_FILTER = 16;
    
    /*End management*/
    public static final short PLAYER_ESCAPED = 1;
    public static final short PLAYER_CATCHED = -1;

    /*Text*/
    public static final String escape_screen_text = "Vous avez réussi à échapper au Parasite !";
    public static final String fail_screen_text = "Vous avez été fait prisonnier par le Parasite...";
    
    public static final String help_text = 	
    	"Bonjour dépositaire ! Besoin d'aide ?\nLaissez moi vous éclairer...\n\n\n\n" +
    	"Le but du jeu consiste à récupèrer les fragments de code dispérsés dans le labyrinthe afin de pouvoir rétablir votre lien avec la grille de téléportation de l'installation et ainsi fuir, mais faites bien attention à ne pas vous laisser capturer par le parasite qui cherche à s'approprier les secrets des Halos !\n\n" +
    	"Vous pouvez utiliser la souris afin d'orienter votre regard et avec le bouton gauche de cette dernière, vous pouvez cliquer là où vous le souhaitez afin de vous déplacer.\n\n" +
    	"Un clic avec le bouton droit sur des symboles semblables à celui présent en haut à gauche vous permet d'activer ou de désactiver des barrirères lumineuses afin de gêner la progression des parasites.\n\n" +
    	"Quand vous vous trouvez au dessus un noyeau d'énergie, qui émet une aura lumineuse à votre approche, vous pouvez utiliser le clic de la mollette pour récupèrer ce dernier afin de pouvoir modifier votre faisceau lumineux.";
    
    public static final float FRAME_DURATION = 0.3f;
    
}
