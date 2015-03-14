package Objects.Enemies;

import GUI.Map;

public interface AI {
	public double[] move(double x, double y, double dx, double dy, Map myMap, Enemy me);
}
