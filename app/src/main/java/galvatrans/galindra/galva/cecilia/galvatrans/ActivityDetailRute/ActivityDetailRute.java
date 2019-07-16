package galvatrans.galindra.galva.cecilia.galvatrans.ActivityDetailRute;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.ActivityBiaya.ActivityShowBiaya.ActivityShowBiaya;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.ResponseUpdate;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.Rute;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.RuteDetail;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.SessionManager;
import galvatrans.galindra.galva.cecilia.galvatrans.R;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.BiayaEntity;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.ImagesEntity;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.Repository;

public class ActivityDetailRute extends AppCompatActivity implements ActivityDetailRutePresenter.MainView {

    Dialog dialogLoading;
    Dialog dialogYesNo;
    Dialog dialogPicture;
    Dialog dialogOk;
    Dialog dialogMemo;
    String dbMaster = "GGMCCMGIN";
    String nomorOrder;
    String nomorRute;
    String kodeSopir;
    String kodeMobil;
    String kodeAreaSelected;
    String nameSopirSelected;
    String idHeaderBiaya;
    String memo;
    String stateActive;
    String submitAction;
    TextView txtNomorOrder, txtTujuan, txtWaktuSampai, txtWaktuBerangkat, txtNoteAdmin;
    TextView txtBadgeImage;
    TextView txtBadgeBiaya;
    MaterialButton btnAddBiayaTambahan, btnUploadImage, btnBatal, btnProses;
    Bitmap imgCaptured;

    RuteDetail ruteDetail;

    Rute actualRute;

    Uri imageUri;

    String idStatus;
    String stateUpdate;

    ActivityDetailRutePresenterImpl activityDetailRutePresenterImpl;

    SessionManager sessionManager;
    HashMap<String, String> sessionUser;
    Repository repositoryDatabase;

    List<ImagesEntity> imagesEncodedList = new ArrayList<>();
    List<BiayaEntity> biayaList = new ArrayList<>();
    List<ImagesEntity> imageFailed = new ArrayList<>();
    List<BiayaEntity> biayaFailed = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_rute);

        activityDetailRutePresenterImpl = new ActivityDetailRutePresenterImpl(this, getApplicationContext());
        repositoryDatabase = new Repository(this);
        sessionManager = new SessionManager(this);
        sessionUser = sessionManager.getUserDetails();
        nameSopirSelected = sessionUser.get(SessionManager.KEY_NAME);
        kodeAreaSelected = sessionUser.get(SessionManager.KEY_AREA);

        Intent intent = getIntent();
        nomorOrder = intent.getStringExtra("nomorOrder");
        nomorRute = intent.getStringExtra("nomorRute");
        kodeMobil = intent.getStringExtra("kodeMobil");
        kodeSopir = intent.getStringExtra("kodeSopir");

        createLoadingDialog();
        initLayout();
        onGetDetailRute();
    }

    private void showActionPictureDialog() {
        dialogPicture = new Dialog(this);
        dialogPicture.setContentView(R.layout.dialog_konfirmasi_aksi);
        dialogPicture.setCancelable(false);
        dialogPicture.setCanceledOnTouchOutside(false);

        Button btnFirstAction = dialogPicture.findViewById(R.id.btnFirstAction);
        Button btnSecondAction = dialogPicture.findViewById(R.id.btnSecondAction);
        Button btnCancel = dialogPicture.findViewById(R.id.btnCancelAction);

        btnFirstAction.setOnClickListener(listener -> {
            dialogPicture.dismiss();

            onBtnAddImageClicked();
        });

        btnSecondAction.setOnClickListener(listener -> {
            dialogPicture.dismiss();

            onBtnShowImageClicked();
        });

        btnCancel.setOnClickListener(v -> dialogPicture.dismiss());

        dialogPicture.show();

        if (dialogPicture.getWindow() != null) {
            dialogPicture.getWindow().setLayout
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    private void onBtnAddImageClicked() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestMultiplePermissions();
        } else {
            openCamera();
        }
    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            openCamera();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showOkDialog("Izinkan semua permission", "Ok", "ON_PERMISSION_DENY");
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .withErrorListener(
                        error -> Toast.makeText(getApplicationContext(), "Error! ", Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
    }

    private void openCamera() {
        if (imagesEncodedList.size() == 3) {
            showOkDialog("Hanya dapat upload 3 gambar", "Ok", "GET_FULL_IMAGE");
        } else {
            ContentValues values = new ContentValues();
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, 0);
        }
    }

    private void onBtnShowImageClicked() {
        if (imagesEncodedList.size() == 0) {
            showOkDialog("Tidak ada gambar terupload", "Ok", "GET_EMPTY_IMAGES");
        } else {
            Dialog dialog = new Dialog(this);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            dialog.setContentView(R.layout.dialog_list);
            dialog.show();

            dialog.setOnDismissListener(v -> {
                onCheckImageList();
            });

            RecyclerView rvImagesUploaded = dialog.findViewById(R.id.rvDialog);
            SnapHelper helper = new LinearSnapHelper();
            helper.attachToRecyclerView(rvImagesUploaded);
            rvImagesUploaded.setHasFixedSize(true);
            rvImagesUploaded.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            ActivityDetailRuteImagesAdapter activityDetailRuteImagesAdapter = new ActivityDetailRuteImagesAdapter(this, imagesEncodedList);
            rvImagesUploaded.setAdapter(activityDetailRuteImagesAdapter);
        }
    }

    private void createLoadingDialog() {
        dialogLoading = new Dialog(this);
        dialogLoading.setContentView(R.layout.dialog_loading);
        dialogLoading.setCancelable(false);
        dialogLoading.setCanceledOnTouchOutside(false);

        TextView txtLoading = dialogLoading.findViewById(R.id.txtLoading);
        txtLoading.setText(R.string.loadingMessage);

        if (dialogLoading.getWindow() != null) {
            dialogLoading.getWindow().setLayout
                    (LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    private void initLayout() {
        txtNomorOrder = findViewById(R.id.txtNomorOrder);
        txtTujuan = findViewById(R.id.txtTujuan);
        txtWaktuBerangkat = findViewById(R.id.txtWaktuBerangkat);
        txtWaktuSampai = findViewById(R.id.txtWaktuSampai);
        txtNoteAdmin = findViewById(R.id.txtNoteAdmin);
        txtBadgeBiaya = findViewById(R.id.txtBadgeBiaya);
        txtBadgeImage = findViewById(R.id.txtBadgeImage);

        btnAddBiayaTambahan = findViewById(R.id.btnAddBiayaTambahan);
        btnAddBiayaTambahan.setOnClickListener(v -> {
            Intent intentShowBiaya = new Intent(this, ActivityShowBiaya.class);
            intentShowBiaya.putExtra("pt", "GGMC");
            intentShowBiaya.putExtra("nomorOrder", nomorOrder);
            intentShowBiaya.putExtra("kodeSopir", kodeSopir);
            intentShowBiaya.putExtra("kodeMobil", kodeMobil);

            startActivity(intentShowBiaya);

        });

        btnUploadImage = findViewById(R.id.btnUploadImage);
        btnUploadImage.setOnClickListener(v -> {
            showActionPictureDialog();

        });

        btnBatal = findViewById(R.id.btnBatal);
        btnBatal.setOnClickListener(v -> {
            idStatus = "7";
            stateUpdate = "UpdateCancel";
            submitAction = "CANCEL";

            onCheckUploadImg();
        });

        btnProses = findViewById(R.id.btnSubmit);
        btnProses.setOnClickListener(v -> {
            stateActive = ruteDetail.getStatus();

            switch (stateActive) {
                case "Belum Diantar":
                    idStatus = "3";
                    stateUpdate = "UpdateMulai";
                    onUpdateWaktuBerangkat();

                    break;
                case "Sedang Diantar":
                    idStatus = "4";
                    stateUpdate = "UpdateSelesai";
                    submitAction = "FINISH";

                    onCheckUploadImg();

                    break;
                case "Selesai":
                    super.onBackPressed();

                    break;
            }
        });
    }

    private void onCheckUploadImg() {
        if (imagesEncodedList.size() == 0) {
            showOkDialog("Upload minimal 1 gambar bukti", "Ok", "GET_EMPTY_IMAGES");
        } else {
            switch (submitAction) {
                case "CANCEL":
                    showCancelDeliveryDialog();

                    break;

                case "FINISH":
                    showFinishDeliveryDialog();

                    break;
            }
        }
    }

    private void showFinishDeliveryDialog() {
        showYesOrNoDialog("Sampai tujuan?", "Ya", "Batal", "FINISH_DELIVERY");
    }

    private void showCancelDeliveryDialog() {
        showYesOrNoDialog("Batalkan Tugas?", "Ya", "Tidak", "CANCEL_DELIVERY");
    }


    private void onUpdateWaktuBerangkat() {
        activityDetailRutePresenterImpl.updateWaktuBerangkat(ruteDetail.getIdRute(), ruteDetail.getRute());
    }

    private void onUpdateWaktuSampai() {
        activityDetailRutePresenterImpl.updateWaktuSampai(ruteDetail.getIdRute(), ruteDetail.getRute(), memo);
    }

    private void onGetDetailRute() {
        dialogLoading.show();

        activityDetailRutePresenterImpl.onGetDetailRute(nomorOrder, nomorRute);
    }

    @Override
    public void onGetDetailRuteSuccess(RuteDetail ruteDetail) {
        this.ruteDetail = ruteDetail;
        if (ruteDetail.getBerangkat().equals("") && ruteDetail.getSampai().equals("")) {
            this.ruteDetail.setStatus("Belum Diantar");
        } else if (!ruteDetail.getBerangkat().equals("") && ruteDetail.getSampai().equals("")) {
            this.ruteDetail.setStatus("Sedang Diantar");
        } else {
            this.ruteDetail.setStatus("Selesai");
        }

        setToLayout(this.ruteDetail);
    }

    private void setToLayout(RuteDetail ruteDetail) {
        txtNomorOrder.setText(ruteDetail.getIdRute());
        txtTujuan.setText(ruteDetail.getTujuan());
        txtWaktuBerangkat.setText(ruteDetail.getBerangkat());
        txtWaktuSampai.setText(ruteDetail.getSampai());
        txtNoteAdmin.setText(ruteDetail.getNoteAdmin());

        if (ruteDetail.getStatus().equals("Belum Diantar")) {
            btnAddBiayaTambahan.setVisibility(View.GONE);
            btnBatal.setVisibility(View.GONE);
            btnUploadImage.setVisibility(View.GONE);
            btnProses.setVisibility(View.VISIBLE);
            btnProses.setText("ANTAR");
        } else if (ruteDetail.getStatus().equals("Sedang Diantar")) {
            btnAddBiayaTambahan.setVisibility(View.VISIBLE);
            btnBatal.setVisibility(View.VISIBLE);
            btnUploadImage.setVisibility(View.VISIBLE);
            btnProses.setVisibility(View.VISIBLE);
            btnProses.setText("SELESAI");
        } else {
            btnAddBiayaTambahan.setVisibility(View.GONE);
            btnBatal.setVisibility(View.GONE);
            btnUploadImage.setVisibility(View.GONE);
            btnProses.setVisibility(View.VISIBLE);
            btnProses.setText("KEMBALI");
        }

        dialogLoading.dismiss();
    }

    @Override
    public void onGetDetailRuteFailed() {
        dialogLoading.dismiss();

        showYesOrNoDialog("Gagal mengambil data",
                "Ulangi", "Kembali", "ON_GET_LIST_RUTE_FAILED");
    }

    private void showYesOrNoDialog(String message, String buttonYes, String buttonNo, String stateActive) {
        dialogYesNo = new Dialog(this);
        dialogYesNo.setContentView(R.layout.dialog_konfirmasi_yes_or_no);
        dialogYesNo.setCancelable(false);
        dialogYesNo.setCanceledOnTouchOutside(false);

        TextView txtMessage = dialogYesNo.findViewById(R.id.txtMessage);
        Button btnYes = dialogYesNo.findViewById(R.id.btnYes);
        Button btnNo = dialogYesNo.findViewById(R.id.btnNo);

        txtMessage.setText(message);

        btnYes.setText(buttonYes);
        btnYes.setOnClickListener(listener -> {
            dialogYesNo.dismiss();
            switch (stateActive) {
                case "ON_GET_LIST_RUTE_FAILED":
                    onGetDetailRute();

                    break;
                case "FINISH_DELIVERY":
                    showMemoDialog();

                    break;
                case "CANCEL_DELIVERY":
//                    onUpdateWaktuSampai();
                    showMemoDialog();

                    break;
            }
        });

        btnNo.setText(buttonNo);
        btnNo.setOnClickListener(listener -> {
            dialogYesNo.dismiss();

            switch (stateActive) {
                case "ON_GET_LIST_RUTE_FAILED":
                    super.onBackPressed();

                    break;
                case "FINISH_DELIVERY":

                    break;
                case "CANCEL_DELIVERY":
                    showMemoDialog();

                    break;
            }
        });

        dialogYesNo.show();
        if (dialogYesNo.getWindow() != null) {
            dialogYesNo.getWindow().setLayout
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    private void showMemoDialog() {
        dialogMemo = new Dialog(this);
        dialogMemo.setContentView(R.layout.dialog_memo);
        dialogMemo.setCancelable(false);
        dialogMemo.setCanceledOnTouchOutside(false);
        TextInputEditText editMemo = dialogMemo.findViewById(R.id.editMemo);
        TextInputEditText editKeterangan = dialogMemo.findViewById(R.id.editKeterangan);
        MaterialButton btnCancel = dialogMemo.findViewById(R.id.btnCancelMemo);
        MaterialButton btnSubmit = dialogMemo.findViewById(R.id.btnSubmitMemo);

        switch (submitAction) {
            case "CANCEL":
                editMemo.setOnClickListener(v -> {
                    AlertDialog.Builder dialogSelectMemo = new AlertDialog.Builder(this);
                    dialogSelectMemo.setCancelable(true);
                    ArrayAdapter<String> memoText = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item);
                    memoText.add("Alamat Tidak Ketemu");
                    memoText.add("Mobil Rusak");
                    memoText.add("Pesanan Dibatalkan");
                    memoText.add("Lain-lain");

                    dialogSelectMemo.setAdapter(memoText, (dialog, position) -> {
                        editMemo.setText(memoText.getItem(position));
                    });

                    dialogSelectMemo.show();
                });

                btnCancel.setOnClickListener(listener -> dialogMemo.dismiss());

                btnSubmit.setOnClickListener(listener -> {
                    if (editMemo.getText().toString().equals("")) {
                        showOkDialog("Pilih memo", "Ok", "GET_MEMO_EMPTY");
                    } else {
                        dialogMemo.dismiss();
                        memo = ("[" + editMemo.getText().toString() + "] ").concat((editKeterangan.getText().toString() + ""));

                        onBtnCancelDeliveryClicked();
                    }
                });

                if (dialogMemo.getWindow() != null) {
                    dialogMemo.getWindow().setLayout
                            (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
                dialogMemo.show();

                break;

            case "FINISH":
                editMemo.setText("Selesai");
                editMemo.setEnabled(false);

                btnCancel.setOnClickListener(listener -> {
                    dialogMemo.dismiss();
                });

                btnSubmit.setOnClickListener(listener -> {
                    dialogMemo.dismiss();
                    memo = ("[" + editMemo.getText().toString() + "] ").concat(editKeterangan.getText().toString());

                    onBtnFinishDeliveryClicked();
                });

                if (dialogMemo.getWindow() != null) {
                    dialogMemo.getWindow().setLayout
                            (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
                dialogMemo.show();

                break;
        }
    }

    private void onBtnFinishDeliveryClicked() {
        dialogLoading.show();

        onGetIdBiaya();
    }

    private void onBtnCancelDeliveryClicked() {
        dialogLoading.show();

        onGetIdBiaya();
    }

    private void onGetIdBiaya() {
        dialogLoading.show();

        activityDetailRutePresenterImpl.onGetIdExist(dbMaster, kodeMobil);
    }

    @Override
    public void updateWaktuBerangkatSuccess() {
        setStatusRute();
    }

    private void setStatusRute() {
        activityDetailRutePresenterImpl.setStatusRute(ruteDetail, idStatus);
    }

    @Override
    public void updateWaktuSampaiSuccess() {
        onUploadImage(imagesEncodedList);
    }

    @Override
    protected void onResume() {
        super.onResume();

        onCheckBiayaList();
        onCheckImageList();
    }

    private void onCheckBiayaList() {
        repositoryDatabase.getAllBiaya(nomorOrder).observe(this, biayasEntities -> {
            if (biayasEntities != null) {
                biayaList = biayasEntities;
                if (biayasEntities.size() > 0) {
                    txtBadgeBiaya.setVisibility(View.VISIBLE);
                    txtBadgeBiaya.setText(String.valueOf(biayasEntities.size()));
                } else {
                    txtBadgeBiaya.setVisibility(View.GONE);
                }
            }
        });
    }

    private void onCheckImageList() {
        repositoryDatabase.getAllImage(nomorOrder).observe(this, imagesEntities -> {
            if (imagesEntities != null) {
                imagesEncodedList = imagesEntities;
                if (imagesEntities.size() > 0) {
                    txtBadgeImage.setVisibility(View.VISIBLE);
                    txtBadgeImage.setText(String.valueOf(imagesEntities.size()));
                } else {
                    txtBadgeImage.setVisibility(View.GONE);
                }
            }
        });
    }

    private void showOkDialog(String message, String buttonOk, String stateActive) {
        dialogOk = new Dialog(this);
        dialogOk.setContentView(R.layout.dialog_konfirmasi_ok);
        dialogOk.setCancelable(false);
        dialogOk.setCanceledOnTouchOutside(false);

        TextView txtMessage = dialogOk.findViewById(R.id.txtMessage);
        Button btnOk = dialogOk.findViewById(R.id.btnOk);
        txtMessage.setText(message);

        btnOk.setText(buttonOk);
        btnOk.setOnClickListener(listener -> {
            dialogOk.dismiss();

            switch (stateActive) {
                case "GET_FULL_IMAGE":

                    break;

                case "GET_EMPTY_IMAGES":

                    break;

                case "ON_PERMISSION_DENY":
                    openSetting();

                    break;

                case "POST_IMAGES_FAILED":
                    dialogLoading.show();
                    onUploadImage(imageFailed);

                    break;

                case "POST_BIAYA_FAILED":
                    dialogLoading.show();
                    onPostBiaya();

                    break;

                case "POST_SUB_BIAYAS_FAILED":
                    dialogLoading.show();
                    onPostSubBiaya(biayaFailed, idHeaderBiaya);

                    break;

                case "POST_ALL_DATA_SUCCESS":
                    repositoryDatabase.deleteImages();
                    repositoryDatabase.deleteBiayas();

                    super.onBackPressed();

                    break;

                case "POST_IMAGES_WITHOUT_BIAYA_SUCCESS":
                    repositoryDatabase.deleteImages();
                    onGetDetailRute();

                    super.onBackPressed();

                    break;

                case "ON_UPDATE_TANGGAL_BERANGKAT_SUCCESS":
                    onGetDetailRute();

                    break;

                case "ON_SET_STATUS_FAILED":
                    dialogLoading.show();
                    setStatusRute();

                    break;
            }
        });

        dialogOk.show();
        if (dialogOk.getWindow() != null) {
            dialogOk.getWindow().setLayout
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }

    private void openSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void onUploadImage(List<ImagesEntity> images) {
        activityDetailRutePresenterImpl.onUploadImage(images, ruteDetail);
    }

    private void onPostBiaya() {
        activityDetailRutePresenterImpl.onPostBiaya(biayaList, "GGMC", idHeaderBiaya);
    }

    private void onPostSubBiaya(List<BiayaEntity> biayaFailed, String idHeaderBiaya) {
        activityDetailRutePresenterImpl.onPostSubBiaya(biayaFailed, idHeaderBiaya, nameSopirSelected);
    }

    @Override
    public void onUploadImgSuccess(List<ResponseUpdate> responseUpdates) {
        if (biayaList.size() == 0) {
            dialogLoading.dismiss();

            showOkDialog("Proses berhasil", "Ok", "POST_IMAGES_WITHOUT_BIAYA_SUCCESS");
        } else {
            onPostBiaya();
        }
    }

    @Override
    public void onUploadImgFailed(List<ImagesEntity> imageFaileds) {
        dialogLoading.dismiss();

        imageFailed = imageFaileds;
        showOkDialog("Proses gagal", "Ulangi", "POST_IMAGES_FAILED");
    }

    @Override
    public void onPostBiayaSuccess(List<ResponseUpdate> responseUpdates) {
        onPostSubBiaya(biayaList, idHeaderBiaya);
    }

    @Override
    public void onPostBiayaFailed() {
        dialogLoading.dismiss();

        showOkDialog("Proses gagal", "Ulangi", "POST_BIAYA_FAILED");
    }

    @Override
    public void onPostSubBiayaSuccess(List<ResponseUpdate> responseUpdates) {
        dialogLoading.dismiss();

        showOkDialog("Proses berhasil", "Ok", "POST_ALL_DATA_SUCCESS");
    }

    @Override
    public void onPostSubBiayaFailed(List<BiayaEntity> biayaFaileds) {
        dialogLoading.dismiss();
        biayaFailed = biayaFaileds;

        showOkDialog("Proses gagal", "Ulangi", "POST_SUB_BIAYAS_FAILED");
    }

    @Override
    public void onEncodeImageSuccess(String imageEncoded) {
        ImagesEntity image = new ImagesEntity();
        image.setImage(imageEncoded);
        image.setSjImage(nomorOrder);

        repositoryDatabase.insertImage(image);
    }

    @Override
    public void onGetIdBiayaSuccess(List<ResponseUpdate> totalId) {
        DecimalFormat df = new DecimalFormat("0000");
        idHeaderBiaya = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)) + "/" + kodeMobil + "/"
                + df.format(Integer.parseInt(totalId.get(0).getResponse()));
        switch (submitAction) {
            case "CANCEL":
                onUpdateWaktuSampai();

                break;

            case "FINISH":
                onUpdateWaktuSampai();

                break;
        }
    }

    @Override
    public void onGetIdBiayaFailed() {
        dialogLoading.dismiss();

        showYesOrNoDialog("Gagal mendapatkan data", "Ulangi", "Batal", "GET_ID_BIAYA_FAILED");

    }

    @Override
    public void setStatusRuteSuccess() {
        switch (stateUpdate){
            case "UpdateMulai":
                dialogLoading.dismiss();

                onGetDetailRute();

                break;
            case "UpdateSelesai":
                dialogLoading.dismiss();

                onUploadImage(imagesEncodedList);

                break;
            default:
                onUploadImage(imagesEncodedList);
                break;
        }
    }

    @Override
    public void setStatusRuteFailed() {
        dialogLoading.dismiss();

        showOkDialog("Proses gagal", "Ulang", "ON_SET_STATUS_FAILED");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    imgCaptured = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                    onEncodeImage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onEncodeImage() {
        activityDetailRutePresenterImpl.onEncodeImage(imgCaptured);
    }
}