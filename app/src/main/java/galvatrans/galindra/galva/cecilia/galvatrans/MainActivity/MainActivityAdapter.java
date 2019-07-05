package galvatrans.galindra.galva.cecilia.galvatrans.MainActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.Rute;
import galvatrans.galindra.galva.cecilia.galvatrans.R;
import galvatrans.galindra.galva.cecilia.galvatrans.RuteDetail.RuteDetail;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {
    private Context context;
    private List<Rute> rute;

    public MainActivityAdapter(Context context, List<Rute> rute) {
        this.context = context;
        this.rute = rute;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_rute, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (rute.get(position).getMulai().equals("")){
            viewHolder.txtStatus.setText("Belum diantar");
        } else {
            viewHolder.txtStatus.setText("Sedang diantar");
        }
        viewHolder.txtNoOrder.setText(rute.get(position).getNoRute());
        viewHolder.txtMobil.setText(rute.get(position).getIdKendaraan());
        viewHolder.txtTanggalOrder.setText(rute.get(position).getTglOrder());
    }

    @Override
    public int getItemCount() {
        return rute.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtStatus, txtNoOrder, txtMobil, txtTanggalOrder;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtNoOrder = itemView.findViewById(R.id.txtNoOrder);
            txtMobil = itemView.findViewById(R.id.txtMobil);
            txtTanggalOrder = itemView.findViewById(R.id.txtTanggalOrder);


        }
    }
}
