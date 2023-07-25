package sg.edu.np.mad.madassignmentteam1;

public class BusServiceData {
    String ServiceNo;
    String Operator;
    String Direction;
    String Category;
    String OriginCode;
    String DestinationCode;
    String AM_Peak_Freq;
    String AM_Offpeak_Freq;
    String PM_Peak_Freq;
    String PM_Offpeak_Freq;
    String LoopDesc;

    public BusServiceData(String ServiceNo, String Operator, String Direction, String Category, String OriginCode, String DestinationCode, String AM_Peak_Freq, String AM_Offpeak_Freq, String PM_Peak_Freq, String PM_Offpeak_Freq, String LoopDesc) {
        this.ServiceNo = ServiceNo;
        this.Operator = Operator;
        this.Direction = Direction;
        this.Category = Category;
        this.OriginCode = OriginCode;
        this.DestinationCode = DestinationCode;
        this.AM_Peak_Freq = AM_Peak_Freq;
        this.AM_Offpeak_Freq = AM_Offpeak_Freq;
        this.PM_Peak_Freq = PM_Peak_Freq;
        this.PM_Offpeak_Freq = PM_Offpeak_Freq;
        this.LoopDesc = LoopDesc;
    }
}
