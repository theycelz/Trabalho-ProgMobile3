package com.example.aplicacaofinal.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aplicacaofinal.R;
import com.example.aplicacaofinal.databinding.ActivityCadastrarObjetoBinding;
import com.example.aplicacaofinal.model.Objeto;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class CadastrarObjetoActivity extends AppCompatActivity {
    private EditText campoTitulo, campoDescricao, campoValor;
    private Button btnCadastrar;
    private ActivityCadastrarObjetoBinding binding;
    private static final int PERMISSION_REQUEST_CODE = 200;
    static final int GALLERY = 2;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    byte foto[];
    private static final String IMAGE_DIRECTORY = "/camera2022";

    private Spinner campoSituacao, campoCategoria;
    private Objeto objeto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCadastrarObjetoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        inicializarComponentes();
        carregarDadosSpinner();

        binding.btnTirarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Camera camera = Camera.open(1);
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        });

        binding.btnCarregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, GALLERY);
            }
        });
    }


    private Objeto configurarObjeto() {
        String situacao = campoSituacao.getSelectedItem().toString();
        String categoria = campoCategoria.getSelectedItem().toString();
        String titulo = campoTitulo.getText().toString();
        String valor = campoValor.getText().toString();
        String descricao = campoDescricao.getText().toString();

        Objeto objeto = new Objeto();
        objeto.setSituacao(situacao);
        objeto.setCategoria(categoria);
        objeto.setTitulo(titulo);
        objeto.setValor(valor);
        objeto.setDescricao(descricao);
        return objeto;
    }

    public void validarDadosObjeto(View view) {

        objeto = configurarObjeto();

        if (!objeto.getSituacao().isEmpty()) {
            if (!objeto.getCategoria().isEmpty()) {
                if (!objeto.getTitulo().isEmpty()) {
                    if (!objeto.getValor().isEmpty()) {
                        if (!objeto.getDescricao().isEmpty()) {
                            objeto.getIdObjeto();
                            objeto.salvar();
                        } else { exibirMensagemErro("Preencha o campo Descrição"); }
                    } else { exibirMensagemErro("Preencha o campo Valor"); }
                } else { exibirMensagemErro("Preencha o campo Título"); }
            } else { exibirMensagemErro("Preencha o campo Categoria"); }
        } else { exibirMensagemErro("Preencha o campo Situação"); }
    }

    private void exibirMensagemErro(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    private void carregarDadosSpinner(){
        //Inflando spinner para situação dos objetos
        String [] situacao = getResources().getStringArray(R.array.situacao);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, situacao);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campoSituacao.setAdapter(adapter);

        //Inflando spinner para categoria dos objetos
        String [] categorias = getResources().getStringArray(R.array.categoria);
        ArrayAdapter<String> adapterCategoria = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, categorias);

        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campoCategoria.setAdapter(adapterCategoria);
    }

    private void inicializarComponentes(){
        campoTitulo = findViewById(R.id.editTitulo);
        campoDescricao = findViewById(R.id.editDescricao);
        campoValor = findViewById(R.id.editValor);
        campoSituacao = findViewById(R.id.spinnerSituacao);
        campoCategoria = findViewById(R.id.spinnerCategoria);
        btnCadastrar = findViewById(R.id.buttonCadastrar);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermission()) {
            // Success
        } else {
            requestPermission();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            binding.imageObjetoCadastrado.setImageBitmap(resizeImage(bitmap,
                    600, 600));
            saveImage(bitmap);
            Toast.makeText(CadastrarObjetoActivity.this, "Imagem Salva!",
                    Toast.LENGTH_SHORT).show();
        }else if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                            this.getContentResolver(),
                            contentURI);
                    // Salvando a imagem
                    String path = saveImage(bitmap);
                    Log.i("TAG","Path: " + path);
                    Toast.makeText(CadastrarObjetoActivity.this, "Imagem OK!",
                            Toast.LENGTH_SHORT).show();
                    // Print on screen
                    binding.imageObjetoCadastrado.setImageBitmap(resizeImage(bitmap, 600, 600));
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(CadastrarObjetoActivity.this, "Falha!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public String saveImage(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream(
                bitmap.getWidth() * bitmap.getHeight());
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        foto = bytes.toByteArray();

        File directory = new File(Environment.getExternalStorageDirectory()
                + IMAGE_DIRECTORY);
        // Criando o diretório caso ele não exista!
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            File fileImage = new File(directory,
                    Calendar.getInstance().getTimeInMillis() +".jpg");
            fileImage.createNewFile();
            FileOutputStream fo = new FileOutputStream(fileImage);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{fileImage.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved:->" + fileImage.getAbsolutePath());
            return fileImage.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public static Bitmap resizeImage(Bitmap bitmap, int newWidth, int newHeight) {
        Matrix m = new Matrix();
        m.setScale((float) newWidth / bitmap.getWidth(), (float) newHeight / bitmap.getHeight());
        //m.postRotate(90, 150, 300);
        Bitmap output = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawBitmap(bitmap, m, new Paint());
        return output;
    }
    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted",
                            Toast.LENGTH_SHORT).show();
                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied",
                            Toast.LENGTH_SHORT).show();
                    binding.btnTirarFoto.setVisibility(View.INVISIBLE);
                    binding.btnCarregarFoto.setVisibility(View.INVISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(CadastrarObjetoActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}