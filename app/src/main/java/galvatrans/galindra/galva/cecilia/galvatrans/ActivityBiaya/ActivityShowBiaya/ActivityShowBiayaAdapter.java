package galvatrans.galindra.galva.cecilia.galvatrans.ActivityBiaya.ActivityShowBiaya;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.ActivityBiaya.ActivityAddBiaya.ActivityAddBiaya;
import galvatrans.galindra.galva.cecilia.galvatrans.R;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.BiayaEntity;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.Repository;

public class ActivityShowBiayaAdapter extends RecyclerView.Adapter<ActivityShowBiayaAdapter.ViewHolder> {

    private List<BiayaEntity> listBiaya;
    private Context context;

    ActivityShowBiayaAdapter(List<BiayaEntity> listBiaya, Context context) {
        this.listBiaya = listBiaya;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_biaya, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.txtJenisBiaya.setText(listBiaya.get(position).getJenisBiaya());
        viewHolder.txtjumlah.setText(listBiaya.get(position).getJumlah());
        viewHolder.txtSatuan.setText(listBiaya.get(position).getSatuan());
        viewHolder.txtBiaya.setText(listBiaya.get(position).getHarga());

        viewHolder.btnDelete.setOnClickListener(v -> {
            Repository repositoryDatabase = new Repository(context);

            repositoryDatabase.deleteBiayaById(listBiaya.get(position).getIdBiaya());

            listBiaya.remove(listBiaya.get(position));
            notifyDataSetChanged();
        });

        viewHolder.btnEdit.setOnClickListener(v -> {
            Intent intentAddBiaya = new Intent(context, ActivityAddBiaya.class);
            intentAddBiaya.putExtra("actionBiaya", "edit");
            intentAddBiaya.putExtra("idBiaya", listBiaya.get(position).getIdBiaya());

            context.startActivity(intentAddBiaya);
        });
    }

    @Override
    public int getItemCount() {
        return listBiaya.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtJenisBiaya;
        TextView txtjumlah;
        TextView txtSatuan;
        TextView txtBiaya;
        MaterialButton btnEdit;
        MaterialButton btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtJenisBiaya = itemView.findViewById(R.id.txtJenisBiaya);
            txtjumlah = itemView.findViewById(R.id.txtJumlah);
            txtSatuan = itemView.findViewById(R.id.txtSatuan);
            txtBiaya = itemView.findViewById(R.id.txtBiaya);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
