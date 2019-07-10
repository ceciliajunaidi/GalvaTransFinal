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

    ActivityMainItemAdapterClicked listener;
    private Context context;
    private List<Rute> rute;

    ActivityMainAdapter(Context context, List<Rute> rute, ActivityMainItemAdapterClicked listener) {
        this.context = context;
        this.rute = rute;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rute, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(rute.get(position), listener);
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
            txtTanggalOrder = itemView.findViewById(R.id.txtBerangkat);
            txtUser = itemView.findViewById(R.id.txtSampai);
        }

        @SuppressLint("SetTextI18n")
        void bind(Rute rute, ActivityMainItemAdapterClicked listener) {
            txtStatusOrder.setText(rute.getStatus());
            if (rute.getStatus().equals("Belum Diantar")) {
                txtStatusOrder.setBackground(ContextCompat.getDrawable(context, R.drawable.background_idle));
            } else if (rute.getStatus().equals("Sedang Diantar")){
                txtStatusOrder.setBackground(ContextCompat.getDrawable(context, R.drawable.background_onprogress));
            } else {
                txtStatusOrder.setBackground(ContextCompat.getDrawable(context, R.drawable.background_finish));
            }
            txtNomorOrder.setText(": " + rute.getNoRute());
            txtNomorMobil.setText(": " + rute.getIdKendaraan());
            txtTanggalOrder.setText(": " + rute.getTglOrder());

            if (rute.getMulai().equals("")) {
                txtUser.setText(": -");
            } else {
                txtUser.setText(": " + rute.getMulai());
            }

            itemView.setOnClickListener(v -> {
                listener.onItemClick(rute);
            });
        }
    }
}
