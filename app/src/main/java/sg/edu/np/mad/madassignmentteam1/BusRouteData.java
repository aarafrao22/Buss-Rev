package sg.edu.np.mad.madassignmentteam1;

public class BusRouteData {

    String ServiceNo;
    String Operator;
    String Direction;
    String StopSequence;
    String BusStopCode;
    String Distance;
    String WD_FirstBus;
    String WD_LastBus;
    String SAT_FirstBus;
    String SAT_LastBus;
    String SUN_FirstBus;
    String SUN_LastBus;

    public BusRouteData(String ServiceNo, String Operator, String Direction, String StopSequence, String BusStopCode, String Distance, String WD_FirstBus, String WD_LastBus, String SAT_FirstBus, String SAT_LastBus, String SUN_FirstBus, String SUN_LastBus) {
        this.ServiceNo = ServiceNo;
        this.Operator = Operator;
        this.Direction = Direction;
        this.StopSequence = StopSequence;
        this.BusStopCode = BusStopCode;
        this.Distance = Distance;
        this.WD_FirstBus = WD_FirstBus;
        this.WD_LastBus = WD_LastBus;
        this.SAT_FirstBus = SAT_FirstBus;
        this.SAT_LastBus = SAT_LastBus;
        this.SUN_FirstBus = SUN_FirstBus;
        this.SUN_LastBus = SUN_LastBus;
    }

}
