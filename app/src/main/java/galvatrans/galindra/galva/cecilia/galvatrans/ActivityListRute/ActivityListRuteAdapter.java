package galvatrans.galindra.galva.cecilia.galvatrans.ActivityListRute;

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

import galvatrans.galindra.galva.cecilia.galvatrans.Model.RuteDetail;
import galvatrans.galindra.galva.cecilia.galvatrans.R;

public class ActivityListRuteAdapter extends RecyclerView.Adapter<ActivityListRuteAdapter.ViewHolder> {

    ActivityListRuteItemAdapterClicked listener;
    private Context context;
    private List<RuteDetail> listRute;

    ActivityListRuteAdapter(Context context, List<RuteDetail> listRute, ActivityListRuteItemAdapterClicked listener) {
        this.context = context;
        this.listRute = listRute;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rute_detail, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(listRute.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return listRute.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtStatusOrder, txtNomorOrder, txtTujuan, txtWaktuBerangkat, txtWaktuSampai;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtStatusOrder = itemView.findViewById(R.id.txtStatusOrder);
            txtNomorOrder = itemView.findViewById(R.id.txtNomorOrder);
            txtTujuan = itemView.findViewById(R.id.txtTujuan);
            txtWaktuBerangkat = itemView.findViewById(R.id.txtBerangkat);
            txtWaktuSampai = itemView.findViewById(R.id.txtSampai);
        }

        @SuppressLint("SetTextI18n")
        void bind(RuteDetail listRute, ActivityListRuteItemAdapterClicked listener) {
            if (listRute.getBerangkat().equals("") && listRute.getSampai().equals("")) {
                txtStatusOrder.setText("Menunggu Konfirmasi");
                txtStatusOrder.setBackground(ContextCompat.getDrawable(context, R.drawable.background_idle));
            } else if (!listRute.getBerangkat().equals("") && listRute.getSampai().equals("")){
                txtStatusOrder.setText("Dalam Proses");
                txtStatusOrder.setBackground(ContextCompat.getDrawable(context, R.drawable.background_onprogress));
            } else {
                txtStatusOrder.setText("Proses Selesai");
                txtStatusOrder.setBackground(ContextCompat.getDrawable(context, R.drawable.background_onprogress));
            }
            txtNomorOrder.setText(": " + listRute.getIdRute());
            txtTujuan.setText(": " + listRute.getTujuan());
            txtWaktuBerangkat.setText(": " + listRute.getBerangkat());

            if (listRute.getBerangkat().equals("")) {
                txtWaktuBerangkat.setText(": -");
            } else {
                txtWaktuBerangkat.setText(": " + listRute.getBerangkat());
            }

            txtWaktuSampai.setText(": " + listRute.getSampai());

            itemView.setOnClickListener(v -> {
                listener.onItemClick(listRute);
            });
        }
    }
}
