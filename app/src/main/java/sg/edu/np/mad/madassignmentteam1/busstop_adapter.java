package sg.edu.np.mad.madassignmentteam1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class busstop_adapter extends RecyclerView.Adapter<busstop_adapter.busstop_adapterViewHolder> {

    private List<busstopdata> busstopdataList;
    private LayoutInflater inflater;

    public busstop_adapter(Bus_Moving context, List<busstopdata> busstopdataList) {
        this.busstopdataList = busstopdataList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public busstop_adapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = inflater.inflate(R.layout.busstop_item, parent, false);
        return new busstop_adapter.busstop_adapterViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull busstop_adapter.busstop_adapterViewHolder holder, int position) {
        busstopdata busstopdata = busstopdataList.get(position);
        holder.road.setText(busstopdata.RoadName);
        holder.busstop.setText(busstopdata.BusStopCode);
        String[] km = busstopdata.Direction.split("\\.");
        holder.direction.setText(km[0] + " km");
    }

    @Override
    public int getItemCount() {
        return busstopdataList.size();
    }

    class busstop_adapterViewHolder extends RecyclerView.ViewHolder {
        TextView road;
        TextView busstop;
        TextView direction;

        public busstop_adapterViewHolder(@NonNull View item) {
            super(item);
            road = item.findViewById(R.id.roadname);
            busstop = item.findViewById(R.id.BusStopCode);
            direction = item.findViewById(R.id.directionname);
        }
    }


}


