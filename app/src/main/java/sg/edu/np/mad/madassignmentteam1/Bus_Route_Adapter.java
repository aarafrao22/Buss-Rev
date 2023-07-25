package sg.edu.np.mad.madassignmentteam1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Bus_Route_Adapter extends RecyclerView.Adapter<Bus_Route_Adapter.busroute_adapterViewHolder> {


    private List<BusRouteData> busRouteDataList;
    private LayoutInflater inflater;

    public Bus_Route_Adapter(Bus_Moving context, List<BusRouteData> busRouteDataList) {
        this.busRouteDataList = busRouteDataList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public busroute_adapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = inflater.inflate(R.layout.busroute_item, parent, false);
        return new Bus_Route_Adapter.busroute_adapterViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull Bus_Route_Adapter.busroute_adapterViewHolder holder, int position) {
        BusRouteData busRouteData = busRouteDataList.get(position);
        holder.directionname.setText(busRouteData.Direction);
        holder.ServiceNo.setText(busRouteData.ServiceNo);
        holder.WD_FirstBus.setText(busRouteData.WD_FirstBus);
        holder.WD_LastBus.setText(busRouteData.WD_LastBus);
        holder.SAT_FirstBus.setText(busRouteData.SAT_FirstBus);
        holder.SAT_LastBus.setText(busRouteData.SAT_LastBus);
        holder.SUN_FirstBus.setText(busRouteData.SUN_FirstBus);
        holder.SUN_LastBus.setText(busRouteData.SUN_LastBus);
    }

    @Override
    public int getItemCount() {
        return busRouteDataList.size();
    }

    class busroute_adapterViewHolder extends RecyclerView.ViewHolder {
        TextView directionname, ServiceNo, WD_FirstBus, WD_LastBus, SAT_FirstBus, SUN_FirstBus;
        TextView SUN_LastBus, SAT_LastBus;
        TextView direction;

        public busroute_adapterViewHolder(@NonNull View item) {
            super(item);
            directionname = item.findViewById(R.id.directionname);
            ServiceNo = item.findViewById(R.id.ServiceNo);
            WD_FirstBus = item.findViewById(R.id.WD_FirstBus);
            WD_LastBus = item.findViewById(R.id.WD_LastBus);
            SAT_FirstBus = item.findViewById(R.id.SAT_FirstBus);
            SAT_LastBus = item.findViewById(R.id.SAT_LastBus);
            SUN_FirstBus = item.findViewById(R.id.SUN_FirstBus);
            SUN_LastBus = item.findViewById(R.id.SUN_LastBus);


        }
    }
}
