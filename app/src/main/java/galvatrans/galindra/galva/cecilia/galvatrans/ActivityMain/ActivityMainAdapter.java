package galvatrans.galindra.galva.cecilia.galvatrans.ActivityMain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.Rute;
import galvatrans.galindra.galva.cecilia.galvatrans.R;

public class ActivityMainAdapter extends RecyclerView.Adapter<ActivityMainAdapter.ViewHolder> {

    private Context context;
    private List<Rute> rute;

    ActivityMainAdapter(Context context, List<Rute> rute) {
        this.context = context;
        this.rute = rute;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rute, viewGroup, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (rute.get(position).getMulai().equals("")){
            viewHolder.txtStatusOrder.setText("Belum Diantar");
            viewHolder.txtStatusOrder.setBackground(ContextCompat.getDrawable(context, R.drawable.background_idle));
        } else {
            viewHolder.txtStatusOrder.setText("Sedang Diantar");
            viewHolder.txtStatusOrder.setBackground(ContextCompat.getDrawable(context, R.drawable.background_onprogress));
        }
        viewHolder.txtNomorOrder.setText(": " + rute.get(position).getNoRute());
        viewHolder.txtNomorMobil.setText(": " + rute.get(position).getIdKendaraan());
        viewHolder.txtTanggalOrder.setText(": " + rute.get(position).getTglOrder());

        if (rute.get(position).getMulai().equals("")) {
            viewHolder.txtUser.setText(": -");
        } else {
            viewHolder.txtUser.setText(": " + rute.get(position).getMulai());
        }
    }

    @Override
    public int getItemCount() {
        return rute.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtStatusOrder, txtNomorOrder, txtNomorMobil, txtTanggalOrder, txtUser;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtStatusOrder = itemView.findViewById(R.id.txtStatusOrder);
            txtNomorOrder = itemView.findViewById(R.id.txtNomorOrder);
            txtNomorMobil = itemView.findViewById(R.id.txtNomorMobil);
            txtTanggalOrder = itemView.findViewById(R.id.txtTanggalOrder);
            txtUser = itemView.findViewById(R.id.txtTanggalMulai);
        }
    }
}
