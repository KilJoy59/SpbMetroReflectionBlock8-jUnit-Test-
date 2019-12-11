import core.Line;
import core.Station;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RouteCalculatorTest extends Assert {
    StationIndex stationIndex = new StationIndex();
    RouteCalculator routeCalculator = new RouteCalculator(stationIndex);
    List<Station> route;
    List<Station> conList1 = new ArrayList<>();
    List<Station> conList2 = new ArrayList<>();

    @Before
    public void setUpRouteCalculator() {
        route = new ArrayList<>();
        Line line1 = new Line(1, "Первая");
        Line line2 = new Line(2, "Вторая");
        Line line3 = new Line(3, "Третья");
        Station station11 = new Station("первая1", line1);
        Station station12 = new Station("первая2", line1);
        Station station13 = new Station("первая3", line1);
        Station station21 = new Station("вторая1", line2);
        Station station22 = new Station("вторая2", line2);
        Station station23 = new Station("вторая3", line2);
        Station station31 = new Station("третья1", line3);
        Station station32 = new Station("третья2", line3);
        Station station33 = new Station("третья3", line3);
        stationIndex.addStation(station11);
        stationIndex.addStation(station12);
        stationIndex.addStation(station13);
        stationIndex.addStation(station21);
        stationIndex.addStation(station22);
        stationIndex.addStation(station23);
        stationIndex.addStation(station31);
        stationIndex.addStation(station32);
        stationIndex.addStation(station33);
        line1.addStation(station11);
        line1.addStation(station12);
        line1.addStation(station13);
        line2.addStation(station21);
        line2.addStation(station22);
        line2.addStation(station23);
        line3.addStation(station31);
        line3.addStation(station32);
        line3.addStation(station33);
        stationIndex.addLine(line1);
        stationIndex.addLine(line2);
        stationIndex.addLine(line3);
        conList1.add(station11);
        conList1.add(station21);
        stationIndex.addConnection(conList1);
        conList2.add(station23);
        conList2.add(station32);
        stationIndex.addConnection(conList2);


        route.add(station11);
        route.add(station12);
        route.add(station21);
        route.add(station22);
        route.add(station23);
    }

    @Test
    public void testCalculateDuration() {
        Double actual = RouteCalculator.calculateDuration(route);
        Double expected = 11.0;
        assertEquals(expected, actual);
    }

    @Test
    public void testGetShortestRoute() {

        List<Station> actual = routeCalculator.getShortestRoute(stationIndex.getStation("первая1"), stationIndex.getStation("первая3"));
        List<Station> excpected = new ArrayList<>();
        excpected.add(stationIndex.getStation("первая1"));
        excpected.add(stationIndex.getStation("первая2"));
        excpected.add(stationIndex.getStation("первая3"));
        assertEquals(excpected, actual);
    }

    @Test
    public void testGetRouteOnTheLine() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class routCalcClass = routeCalculator.getClass();
        Class[] paramTypes = new Class[]{Station.class, Station.class};
        Method method = routCalcClass.getDeclaredMethod("getRouteOnTheLine", paramTypes);
        method.setAccessible(true);
        Station[] args = new Station[]{stationIndex.getStation("первая1"), stationIndex.getStation("первая3")};
        List<Station> actual = (List<Station>) method.invoke(routeCalculator, args);
        List<Station> excpected = new ArrayList<>();
        excpected.add(stationIndex.getStation("первая1"));
        excpected.add(stationIndex.getStation("первая2"));
        excpected.add(stationIndex.getStation("первая3"));
        assertEquals(excpected, actual);
    }

    @Test
    public void testGetRouteWithOneConnection() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class routCalcClass = routeCalculator.getClass();
        Class[] paramTypes = new Class[]{Station.class, Station.class};
        Method method = routCalcClass.getDeclaredMethod("getRouteWithOneConnection", paramTypes);
        method.setAccessible(true);
        Station[] args = new Station[]{stationIndex.getStation("первая1"), stationIndex.getStation("вторая3")};
        List<Station> actual = (List<Station>) method.invoke(routeCalculator, args);
        List<Station> excpected = new ArrayList<>();
        excpected.add(stationIndex.getStation("первая1"));
        excpected.add(stationIndex.getStation("вторая1"));
        excpected.add(stationIndex.getStation("вторая2"));
        excpected.add(stationIndex.getStation("вторая3"));
        assertEquals(excpected, actual);
    }

    @Test
    public void testGetRouteWithTwoConnection() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class routCalcClass = routeCalculator.getClass();
        Class[] paramTypes = new Class[]{Station.class, Station.class};
        Method method = routCalcClass.getDeclaredMethod("getRouteWithTwoConnections", paramTypes);
        method.setAccessible(true);
        Station[] args = new Station[]{stationIndex.getStation("первая1"), stationIndex.getStation("третья1")};
        List<Station> actual = (List<Station>) method.invoke(routeCalculator, args);
        List<Station> excpected = new ArrayList<>();
        excpected.add(stationIndex.getStation("первая1"));
        excpected.add(stationIndex.getStation("вторая1"));
        excpected.add(stationIndex.getStation("вторая2"));
        excpected.add(stationIndex.getStation("вторая3"));
        excpected.add(stationIndex.getStation("третья2"));
        excpected.add(stationIndex.getStation("третья1"));
        assertEquals(excpected, actual);
    }

}
