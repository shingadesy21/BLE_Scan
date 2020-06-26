package com.example.blescan;

import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.polidea.rxandroidble2.RxBleClient;
import com.polidea.rxandroidble2.RxBleDevice;
import com.polidea.rxandroidble2.scan.ScanResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Result_Adapter extends RecyclerView.Adapter<Result_Adapter.ViewHolder> {
    private final List<ScanResult> data=new ArrayList<>();
  private onItemAdapterClickListener onItemAdapterClickListener;
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.two_line,parent,false);
        view.setOnClickListener(onClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ScanResult scan_result=data.get(position);
        final RxBleDevice rxBleDevice= (RxBleDevice) scan_result.getBleDevice();
        holder.line1.setText(String.format(Locale.getDefault(),"%s (%s)",rxBleDevice.getMacAddress(),rxBleDevice.getName()));
        holder.line2.setText(String.format(Locale.getDefault(),"RSSI: %d",scan_result.getRssi()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    private final View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(onItemAdapterClickListener!=null){
                onItemAdapterClickListener.onAdapterViewClick(v);
            }
        }
    };

    public void addScanResult(ScanResult scanResult) {
        for (int i=0;i< data.size();i++){
            if(data.get(i).getBleDevice().equals(scanResult.getBleDevice()))
            {
                data.set(i,scanResult);
                notifyItemChanged(i);
                return;
            }
        }
    }



    interface onItemAdapterClickListener{
        void onAdapterViewClick(View view);
    }

  /*  void addScanResult(Scan_Result result){
        for (int i=0;i< data.size();i++){
            if(data.get(i).getBleDevice().equals(result.getBleDevice()))
            {
                data.set(i,result);
                notifyItemChanged(i);
                return;
            }
        }
    }*/
    void clearScanResult(){
        data.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView line1,line2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            line1=itemView.findViewById(R.id.device_name);
            line1=itemView.findViewById(R.id.device_res);
        }
    }
}
