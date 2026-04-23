package com.asg.auditorioestelar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


// pdf
import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PerfilFragment extends Fragment {

    private TextView tvUsuario, tvEmail;
    private Button btnCerrarSesion;
    private SessionManager sessionManager;

    /* historial entradas no se implementa
    private RecyclerView rvHistorial;
    private HistorialAdapter adapter;
    private List<Entrada> listaEntradas = new ArrayList<>();
    */
    private RecyclerView rvReservasPendientes;
    private AdaptadorReservaPendiente adapterPendientes;
    private List<ReservaPendiente> listaPendientes = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        sessionManager = new SessionManager(getContext());

        if (!sessionManager.estaLogueado()) {

            //No Login - ir a login
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())
                    .commit();

            return null;
        }

        tvUsuario = view.findViewById(R.id.txtNombre);
        tvEmail = view.findViewById(R.id.txtEmail);

        btnCerrarSesion = view.findViewById(R.id.btnCerrarSesion);
        //btnDescargarPDF = view.findViewById(R.id.btnDescargarPDF);

        /* CONFIGURACIÓN RECYCLERVIEW venta no se implementa
        rvHistorial = view.findViewById(R.id.rvHistorial);
        rvHistorial.setLayoutManager(new LinearLayoutManager(getContext()));
         */

        rvReservasPendientes = view.findViewById(R.id.rvReservasPendientes);
        rvReservasPendientes.setLayoutManager(new LinearLayoutManager(getContext()));

        //datos usuario reales

        String nombre = sessionManager.getNombre();
        String email = sessionManager.getEmail();

        tvUsuario.setText(nombre);
        tvEmail.setText(email);


        btnCerrarSesion.setOnClickListener(v -> {
            sessionManager.cerrarSesion();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())
                    .commit();
        });

        //btnDescargarPDF.setOnClickListener(v -> generarEntradaPDF());
        //cargarHistorial(); No se implementa
        cargarReservasPendientes();

        return view;
    }
    //reservas pendientes
    private void cargarReservasPendientes() {

        int idUsuario = sessionManager.getIdUsuario();

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<List<ReservaPendiente>> call = apiService.getReservasPendientes(idUsuario);

        call.enqueue(new Callback<List<ReservaPendiente>>() {
            @Override
            public void onResponse(Call<List<ReservaPendiente>> call, Response<List<ReservaPendiente>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    listaPendientes = response.body();

                    adapterPendientes = new AdaptadorReservaPendiente(
                            listaPendientes,
                            reserva -> generarEntradaPDF(reserva)
                    );

                    rvReservasPendientes.setAdapter(adapterPendientes);
                }
            }

            @Override
            public void onFailure(Call<List<ReservaPendiente>> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Error reservas: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    //pagar reserva No se implementa
    /*
    private void pagarReserva(int idReserva){

        android.util.Log.d(
                "PAGAR",
                "Entró id="+idReserva
        );

        ApiService apiService =
                RetrofitClient.getClient().create(ApiService.class);

        Map<String,Integer> body = new HashMap<>();
        body.put("id_reserva", idReserva);

        Call<ResponseBody> call=
                apiService.pagarReserva(body);

        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(
                    Call<ResponseBody> call,
                    Response<ResponseBody> response) {

                 if(response.isSuccessful()){

                    Toast.makeText(
                            getContext(),
                            "Reserva pagada",
                            Toast.LENGTH_SHORT
                    ).show();

                try {
                    String respuesta = response.body().string();
                    android.util.Log.d("PAGO", respuesta );
                    Toast.makeText(
                            getContext(),
                            respuesta,
                            Toast.LENGTH_LONG
                    ).show();

                } catch (Exception e){ e.printStackTrace();
                }


                    //recargar automáticamente
                    cargarReservasPendientes();
                    cargarHistorial();
                }

            @Override
            public void onFailure(
                    Call<ResponseBody> call,
                    Throwable t) {
                android.util.Log.e("PAGO", t.getMessage() );
                Toast.makeText(
                        getContext(),
                        "Error: "+t.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
    // llamada a la api
    private void cargarHistorial() {
        String idUsuario = sessionManager.getIdUsuarioParaHistorial();

        // log para fallos
        android.util.Log.d("HISTORIAL", "idUsuario = " + idUsuario);

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<List<Entrada>> call = apiService.getHistorial(idUsuario);

        call.enqueue(new Callback<List<Entrada>>() {
            @Override
            public void onResponse(Call<List<Entrada>> call, Response<List<Entrada>> response) {
                //log historial
                android.util.Log.d("HISTORIAL",
                        "HTTP code = " + response.code());

                android.util.Log.d("HISTORIAL",
                        "isSuccessful = " + response.isSuccessful());

                if (response.body() != null) {
                    android.util.Log.d("HISTORIAL",
                            "Entradas recibidas = " + response.body().size());
                } else {
                    android.util.Log.d("HISTORIAL",
                            "Body es NULL");
                }

                if (response.isSuccessful() && response.body() != null) {
                    listaEntradas = response.body();
                    adapter = new HistorialAdapter(listaEntradas);
                    rvHistorial.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Entrada>> call, Throwable t) {
                android.util.Log.e("HISTORIAL","ERROR RETROFIT: " + t.getMessage(), t);
                Toast.makeText(getContext(), "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private void generarEntradaPDF(ReservaPendiente reserva) {

        String titulo = reserva.getTitulo();
        String fecha = reserva.getFecha();
        String total = String.valueOf(reserva.getTotal());
        String butacas = reserva.getButacas();
        String id = String.valueOf(reserva.getIdReserva());

        File file = new File(requireContext().getCacheDir(),
                "entrada_" + id + ".pdf");

        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(file));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // logo en pdf
            Drawable d = getContext().getDrawable(R.drawable.logo_ae_sl_sf);
            if (d != null) {
                Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 10, stream);
                ImageData imageData = ImageDataFactory.create(stream.toByteArray());
                Image logo = new Image(imageData).setWidth(100);
                logo.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER);
                document.add(logo);
            }


            // Cabecera

            Paragraph cabecera = new Paragraph("AUDITORIO ESTELAR")
                    .setBold()
                    .setFontSize(24);
            cabecera.setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
            document.add(cabecera);

            Paragraph subtitulo = new Paragraph("ENTRADA OFICIAL")
                    .setFontSize(14);
            subtitulo.setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
            document.add(subtitulo);

            document.add(new Paragraph(" "));




            //Cuerpo
            document.add(new Paragraph("EVENTO: ")
                    .setBold()
                    .setFontSize(12));

            document.add(new Paragraph(titulo)
                    .setFontSize(16));

            document.add(new Paragraph(" "));

            document.add(new Paragraph("FECHA:")
                    .setBold()
                    .setFontSize(12));

            document.add(new Paragraph(fecha)
                    .setFontSize(14));

            document.add(new Paragraph(" "));

            document.add(new Paragraph("BUTACAS:")
                    .setBold()
                    .setFontSize(12));

            document.add(new Paragraph(butacas)
                    .setFontSize(14));

            // Total
            Paragraph totalP = new Paragraph("TOTAL: " + total + " €")
                    .setBold()
                    .setFontSize(18);
            totalP.setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);

            document.add(totalP);

            // ID_RESERVA
            Paragraph idP = new Paragraph("ID RESERVA: #" + id)
                    .setFontSize(10);
            idP.setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
            document.add(idP);

            // QR
            BarcodeQRCode qrCode = new BarcodeQRCode(id);
            Image qrImage = new Image(qrCode.createFormXObject(pdf));

            qrImage.setWidth(200);
            qrImage.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER);

            document.add(new Paragraph(" "));
            document.add(qrImage);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Entrada válida - Auditorio Estelar")
                    .setFontSize(10)
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER));

            document.close();

            abrirPDF(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void generarEntradaPDFAntiguo(ReservaPendiente reserva) {
        // datos para el pdf
        /* Metodo antigo con ultima reserva
        SharedPreferences prefs = getActivity().getSharedPreferences("AuditorioPrefs", Context.MODE_PRIVATE);

        String titulo = prefs.getString("pdf_titulo", "Sin título");
        String fecha = prefs.getString("pdf_fecha", "Sin fecha");
        String total = prefs.getString("pdf_total", "0.0");
        String numButaca = prefs.getString("butaca", "Sin asignar");

        if (titulo.equals("Sin título")) {
            Toast.makeText(getContext(), "No hay ninguna entrada reciente", Toast.LENGTH_SHORT).show();
            return;
        }

         */
        String titulo = reserva.getTitulo();
        String fecha = reserva.getFecha();
        String total = String.valueOf(reserva.getTotal());
        String numButaca = reserva.getButacas();

        // preparar el pdf
        File file = new File(getContext().getCacheDir(), "entrada.pdf");
        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(file));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // logo en pdf
            Drawable d = getContext().getDrawable(R.drawable.logo_ae_sl_sf);
            if (d != null) {
                Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 10, stream);
                ImageData imageData = ImageDataFactory.create(stream.toByteArray());
                Image logo = new Image(imageData).setWidth(100);
                logo.setMarginLeft(200f);
                document.add(logo);
            }

            // contenido del pdf

            //CABECERA

            Paragraph cabecera = new Paragraph("AUDITORIO ESTELAR")
                    .setBold()
                    .setFontSize(24);
            cabecera.setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
            document.add(cabecera);

            //Cuerpo

            document.add(new Paragraph("Evento: " + titulo));
            document.add(new Paragraph("Fecha: " + fecha));

            // Línea de la butaca con formato rojo y negrita
            document.add(new Paragraph("Butaca: " + numButaca)
                    .setFontSize(14));
                    //.setBold());

            document.add(new Paragraph("Total: " + total + "€").setBold());

            document.close();
            abrirPDF(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirPDF(File file) {
        Uri uri = FileProvider.getUriForFile(getContext(),
                "com.asg.auditorioestelar.fileprovider", file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");


        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Instala un visor de PDF", Toast.LENGTH_LONG).show();
        }
    }
}