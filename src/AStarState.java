import java.util.HashMap;
import java.util.Map;

/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map.  This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints."  In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/
public class AStarState
{
    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;
    private Map<Location, Waypoint> openWaypoints;
    private Map<Location, Waypoint> closedWaypoints;

    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
        openWaypoints = new HashMap<Location, Waypoint>();
        closedWaypoints = new HashMap<Location, Waypoint>();
    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     * This method scans through all open waypoints, and returns the waypoint
     * with the minimum total cost.  If there are no open waypoints, this method
     * returns <code>null</code>.
     **/
    public Waypoint getMinOpenWaypoint()
    {
        Waypoint minWaypoint = null;
        Waypoint comparableWaypoint = null;
        float minCost = Float.MAX_VALUE;
        //Итерируем по всем открытым точкам и постепенно находим минимум.
        for (int i = 0; i < openWaypoints.size(); i++){
            comparableWaypoint = (Waypoint) openWaypoints.values().toArray()[i];
            if(comparableWaypoint.getTotalCost() < minCost){
                minWaypoint = comparableWaypoint;
                minCost = comparableWaypoint.getTotalCost();
            }
        }
        return minWaypoint;
    }

    /**
     * This method adds a waypoint to (or potentially updates a waypoint already
     * in) the "open waypoints" collection.  If there is not already an open
     * waypoint at the new waypoint's location then the new waypoint is simply
     * added to the collection.  However, if there is already a waypoint at the
     * new waypoint's location, the new waypoint replaces the old one <em>only
     * if</em> the new waypoint's "previous cost" value is less than the current
     * waypoint's "previous cost" value.
     **/
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        //Пробуем найти точку в открытых точках.
        Waypoint toCompare = openWaypoints.get(newWP.getLocation());
        //Если точка не найдена или она присутсвует, но её пред. значение меньше то добавляем/заменяем ее в коллекции.
        if(toCompare == null || toCompare.getPreviousCost() > newWP.getPreviousCost()){
            openWaypoints.put(newWP.getLocation(), newWP);
            return true;
        }
        return false;
    }


    /** Returns the current number of open waypoints. **/
    public int numOpenWaypoints()
    {
        return openWaypoints.size();
    }


    /**
     * This method moves the waypoint at the specified location from the
     * open list to the closed list.
     **/
    public void closeWaypoint(Location loc)
    {
        //Получаем точку по хэшу локации из открытых точек
        Waypoint point = openWaypoints.get(loc);
        //Если point - null, то этой точки нет в открытых, следовательно дальше нечего делать
        if(point == null) return;
        //Удаляем точку из списка открытых
        openWaypoints.remove(loc);
        //Добавляем точку в список закрытых
        closedWaypoints.put(loc, point);
    }

    /**
     * Returns true if the collection of closed waypoints contains a waypoint
     * for the specified location.
     **/
    public boolean isLocationClosed(Location loc)
    {
        return closedWaypoints.get(loc) != null;
    }
}