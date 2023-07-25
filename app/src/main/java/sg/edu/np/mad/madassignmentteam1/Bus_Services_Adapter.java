package sg.edu.np.mad.madassignmentteam1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Bus_Services_Adapter extends RecyclerView.Adapter<Bus_Services_Adapter.busservice_adapterViewHolder> {


    private List<BusServiceData> busServiceDataList;
    private LayoutInflater inflater;


    public Bus_Services_Adapter(Bus_Moving context, List<BusServiceData> busServiceDataList) {
        this.busServiceDataList = busServiceDataList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public busservice_adapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = inflater.inflate(R.layout.busservice_item, parent, false);
        return new Bus_Services_Adapter.busservice_adapterViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull busservice_adapterViewHolder holder, int position) {
        BusServiceData busServiceData = busServiceDataList.get(position);
        holder.directionname.setText(busServiceData.Direction);
        holder.ServiceNo.setText(busServiceData.ServiceNo);
        holder.Category.setText(busServiceData.Category);
        holder.AM_Peak_Freqvalue.setText(busServiceData.AM_Peak_Freq);
        holder.AM_Offpeak_Freqvalue.setText(busServiceData.AM_Offpeak_Freq);
        holder.PM_Peak_Freqvalue.setText(busServiceData.PM_Peak_Freq);
        holder.PM_Offpeak_Freqvalue.setText(busServiceData.PM_Offpeak_Freq);

    }

    @Override
    public int getItemCount() {
        return busServiceDataList.size();
    }

    class busservice_adapterViewHolder extends RecyclerView.ViewHolder {
        TextView directionname, ServiceNo, Category, AM_Peak_Freqvalue, AM_Offpeak_Freqvalue, PM_Peak_Freqvalue;
        TextView PM_Offpeak_Freqvalue, OriginCode;
        TextView direction;

        public busservice_adapterViewHolder(@NonNull View item) {
            super(item);
            directionname = item.findViewById(R.id.directionname);
            Category = item.findViewById(R.id.ServiceNo);
            ServiceNo = item.findViewById(R.id.Category);
            AM_Peak_Freqvalue = item.findViewById(R.id.AM_Peak_Freqvalue);
            AM_Offpeak_Freqvalue = item.findViewById(R.id.AM_Offpeak_Freqvalue);
            PM_Peak_Freqvalue = item.findViewById(R.id.PM_Peak_Freqvalue);
            PM_Offpeak_Freqvalue = item.findViewById(R.id.PM_Offpeak_Freqvalue);


        }
    }
}
