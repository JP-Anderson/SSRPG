package arch.interfaces;

import map.GridMap;
import map.GridPoint;
import map.gridsquares.GridSquare;
import ship.PlayerShip;
import java.util.ArrayList;

public interface MapSessionInterface {
	
	public void start(PlayerShip player);
	
	public ArrayList<ArrayList<GridSquare>> gridMap();
	
	public boolean tryToTravel(GridPoint destination);

}
