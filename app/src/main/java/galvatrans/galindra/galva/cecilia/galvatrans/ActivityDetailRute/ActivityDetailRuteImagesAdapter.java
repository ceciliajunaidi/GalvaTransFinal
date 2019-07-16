package galvatrans.galindra.galva.cecilia.galvatrans.ActivityDetailRute;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.R;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.ImagesEntity;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.Repository;

public class ActivityDetailRuteImagesAdapter extends RecyclerView.Adapter<ActivityDetailRuteImagesAdapter.ViewHolder> {
    private List<ImagesEntity> imagesList;
    private Context context;

    ActivityDetailRuteImagesAdapter(Context context, List<ImagesEntity> imagesList) {
        this.context = context;
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_images_uploaded, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        byte[] decodedBytes = Base64.decode(imagesList.get(position).getImage(), Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

        viewHolder.imagesUploaded.setImageBitmap(decodedBitmap);

        if (imagesList.size() == 1) {
            viewHolder.btnDeleteImage.setVisibility(View.GONE);
        }
        viewHolder.btnDeleteImage.setOnClickListener(v -> {
            Repository repositoryDatabase = new Repository(context);
            repositoryDatabase.deleteImageById(imagesList.get(position).getIdImage());

            imagesList.remove(imagesList.get(position));
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imagesUploaded;
        Button btnDeleteImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagesUploaded = itemView.findViewById(R.id.imagesUploaded);
            btnDeleteImage = itemView.findViewById(R.id.btnDeleteImage);
        }
    }
}
