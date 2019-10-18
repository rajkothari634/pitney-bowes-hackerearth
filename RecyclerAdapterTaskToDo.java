package com.example.pitneybowes.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pitneybowes.Activities.MainActivity;
import com.example.pitneybowes.Classes.Delivery;
import com.example.pitneybowes.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.util.List;



public class RecyclerAdapterTaskToDo extends RecyclerView.Adapter<RecyclerAdapterTaskToDo.ViewHolderDelivery> {
    public List<Delivery> deliveryList;
    public Context context;


    public class ViewHolderDelivery extends RecyclerView.ViewHolder {
        public TextView text_view_company_name;
        public TextView text_view_delivery_price;
        public TextView text_view_mode_of_payment;
        public TextView text_view_payment_status;
        public TextView text_view_address;
        public TextView text_view_name_of_receiver_expanded;
        public TextView text_view_address_collapsed;
        public TextView text_view_order_number;
        public TextView text_view_phone_number;
        public LinearLayout  exp_Detail_Layout;
        public ImageView call_Viewer;
        public ImageView qrcode_sender;
        public ImageView button_collapse;
        public ImageView button_expand;
        public ImageView button_move_item;
        public ImageView google_image_button;
        public RelativeLayout relativeLayout;

        public ViewHolderDelivery(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.item_relative_layout);
            text_view_delivery_price = itemView.findViewById(R.id.text_view_delivery_price);
            text_view_company_name=itemView.findViewById(R.id.text_view_company_name);
            text_view_mode_of_payment = itemView.findViewById(R.id.text_view_mode_of_payment);
            text_view_payment_status=itemView.findViewById(R.id.text_view_payment_status);
            text_view_address=itemView.findViewById(R.id.text_view_address);
            text_view_name_of_receiver_expanded=itemView.findViewById(R.id.text_view_name_of_receiver_expanded);
            text_view_address_collapsed=itemView.findViewById(R.id.text_view_address_collapsed);
            text_view_order_number=itemView.findViewById(R.id.text_view_order_number);
            text_view_phone_number=itemView.findViewById(R.id.text_view_phone_number);
            exp_Detail_Layout = itemView.findViewById(R.id.expanded_details_linearlayout);
            call_Viewer = itemView.findViewById(R.id.image_view_call_viewer);
            qrcode_sender = itemView.findViewById(R.id.image_view_send_qrcode);
            button_collapse = itemView.findViewById(R.id.imageView_collapse_button);
            button_expand = itemView.findViewById(R.id.image_expand_button);
            button_move_item = itemView.findViewById(R.id.image_view_move_item);
            google_image_button = itemView.findViewById(R.id.image_view_google);
        }
    }
    public RecyclerAdapterTaskToDo(List<Delivery> deliveryList,Context context) {
        this.deliveryList = deliveryList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolderDelivery onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_to_do_recycler_item,viewGroup,false);
        return new ViewHolderDelivery(view);
    }


  //  public in.codepredators.delta.Classes.Delivery setData(in.codepredators.delta.Classes.Delivery delivery) {
      //  return delivery;
   // }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderDelivery viewHolder, int i) {
        final Delivery delivery = deliveryList.get(i);


        viewHolder.button_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delivery.setExpOrCol("expanded");
                notifyDataSetChanged();
            }
        });
        viewHolder.button_collapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delivery.setExpOrCol("collapsed");
                notifyDataSetChanged();
            }
        });

        viewHolder.qrcode_sender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = null;
                if (delivery.getQrCodeSendStatus().equals("Not_Done")) {
                    try {
                        Log.i("sendingqr","inside try of recucler adadpter");
                        result =  sendQRcode(viewHolder.text_view_order_number.getText().toString());

                    } catch (Exception e) {
                        result = "no";
                        Toast.makeText(context, "Not able to send QR code Try Again", Toast.LENGTH_SHORT).show();
                    }
                    if(result.equals("yes")){
                        delivery.setQrCodeSendStatus("Done");
                        viewHolder.qrcode_sender.setBackgroundColor(Color.parseColor("#F69f9f"));
                    }

                }else{
                    result = (new MainActivity()).startScanning(viewHolder.text_view_order_number.getText().toString());
                    if(result.equals("yes")){
                        viewHolder.relativeLayout.setAlpha(0.4f);
                        viewHolder.exp_Detail_Layout.setVisibility(View.GONE);
                        viewHolder.button_expand.setVisibility(View.GONE);
                        viewHolder.button_move_item.setVisibility(View.GONE);
                    }
            }
            }
        });


        if(delivery.getExpOrCol().equals("collapsed")){
            viewHolder.exp_Detail_Layout.setVisibility(View.GONE);
            viewHolder.call_Viewer.setVisibility(View.GONE);
            viewHolder.qrcode_sender.setVisibility(View.GONE);
            viewHolder.text_view_address_collapsed.setVisibility(View.VISIBLE);
            viewHolder.button_collapse.setVisibility(View.GONE);
            viewHolder.button_expand.setVisibility(View.VISIBLE);
            viewHolder.button_move_item.setVisibility(View.VISIBLE);
            viewHolder.google_image_button.setVisibility(View.GONE);
        }
        else{
            viewHolder.exp_Detail_Layout.setVisibility(View.VISIBLE);
            viewHolder.call_Viewer.setVisibility(View.VISIBLE);
            viewHolder.qrcode_sender.setVisibility(View.VISIBLE);
            viewHolder.text_view_address_collapsed.setVisibility(View.GONE);
            viewHolder.button_collapse.setVisibility(View.VISIBLE);
            viewHolder.button_expand.setVisibility(View.GONE);
            viewHolder.button_move_item.setVisibility(View.GONE);
            viewHolder.google_image_button.setVisibility(View.VISIBLE);
        }
        viewHolder.text_view_company_name.setText(delivery.getDeliveryCompanyName());
        viewHolder.text_view_address.setText(delivery.getAddress());
        viewHolder.text_view_address_collapsed.setText(delivery.getAddress());
        viewHolder.text_view_delivery_price.setText(delivery.getDeliveryPrice());
        viewHolder.text_view_mode_of_payment.setText(delivery.getModeOfPayment());
        viewHolder.text_view_order_number.setText(delivery.getId());
        viewHolder.text_view_name_of_receiver_expanded.setText(delivery.getReceiverName());
        viewHolder.text_view_phone_number.setText(delivery.getReceiverPhoneNumber());
        viewHolder.text_view_payment_status.setText(delivery.getPaymentStatus());

    }
    public Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            Log.i("sendingqr","inside main");
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );
            Log.i("sendingqr","inside main");

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];
        Log.i("sendingqr","inside main");
        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        context.getResources().getColor(R.color.black):context.getResources().getColor(R.color.white);
            }
        }
        Log.i("sendingqr","inside main");
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        Log.i("sendingqr","inside main");
        return bitmap;
    }
    public String sendQRcode(String order_number){
        try {
            Log.i("sendingqr","inside main");
            String strEmail = "rajkothari634@gmail.com";
            Log.i("sendingqr","inside main 2");
            Bitmap bitmap = TextToImageEncode(order_number);
            Log.i("sendingqr","inside main 3");
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            Log.i("sendingqr","inside main 4");
            emailIntent.setType("application/image");
            Log.i("sendingqr","inside main 5");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{strEmail});
            Log.i("sendingqr","inside main 6");
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"OR Code");
            Log.i("sendingqr","inside main 7");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Security code by Delivery boy");
            Log.i("sendingqr","inside main 8");
            emailIntent.putExtra(Intent.EXTRA_STREAM,getImageUri(context,bitmap));
            Log.i("sendingqr","inside main 9");
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            Log.i("sendingqr","inside main 10");
        }catch (Exception e){
            return "no";
        }
        Log.i("sendingqr","worksuccessfull");
        return "yes";
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public final static int QRcodeWidth = 500 ;
    @Override
    public int getItemCount() {

        return deliveryList.size();
    }

}

