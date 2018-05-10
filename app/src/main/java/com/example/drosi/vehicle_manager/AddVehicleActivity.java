package com.example.drosi.vehicle_manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.drosi.vehicle_manager.barcode.BarcodeCaptureActivity;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddVehicleActivity extends AppCompatActivity {

    private DatabaseHelper mDatabaseHelper;
    private final int BARCODE_READER_REQUEST_CODE = 1;
    private EditText txtVIN, txtMAKE, txtYEAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        // Create the database object so that we can add data to it
        mDatabaseHelper = new DatabaseHelper(this);

        // Add the add and cancel buttons
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnCancel = findViewById(R.id.btnCancel);
        ImageView btnScan = findViewById(R.id.imgScan);

        // Declare the textboxes to get the users data
        txtVIN = findViewById(R.id.txtVIN);
        txtYEAR = findViewById(R.id.txtYEAR);
        txtMAKE = findViewById(R.id.txtMAKE);
        final EditText txtMODEL = findViewById(R.id.txtMODEL);
        final EditText txtDESC = findViewById(R.id.txtDESC);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vin = txtVIN.getText().toString();
                String year = txtYEAR.getText().toString();
                String make = txtMAKE.getText().toString();
                String model = txtMODEL.getText().toString();
                String desc = txtDESC.getText().toString();

                // Call addData method to send the data to the database
                addData(vin, year, make, model, desc);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddVehicleActivity.this, MainActivity.class));
                finish();
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the barcode scanner activity
                Intent intent = new Intent(AddVehicleActivity.this, BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });

        // Key listener for the vin, whenever the vin gets over decode it
        txtVIN.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If there are more than 3 characters check for the make
                if (txtVIN.getText().toString().length() >= 3) {
                    String make = decodeVinMake(txtVIN.getText().toString());
                    txtMAKE.setText(make);
                }
                return false;
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    String vin = barcode.displayValue.substring(1);
                    if (barcode.displayValue.substring(0,1) == "I") {
                        vin = barcode.displayValue.substring(1);
                    }
                    txtVIN.setText(vin);
                    txtMAKE.setText(decodeVinMake(vin));
                } else {
                    // No barcode capture
                }
            } else {
                // Got a failure code from the resultcode

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void addData(String vin, String year, String make, String model, String desc) {
        // Verify all fields are filled out
        if (vin.isEmpty() || year.isEmpty() || make.isEmpty() || model.isEmpty()) {
            // The user didn't fill out all the required fields
            Toast.makeText(this, "Ensure all required fields are filled.", Toast.LENGTH_LONG).show();
        } else {
            boolean insertData = mDatabaseHelper.addVehicleData(vin, year, make, model, desc);

            if (insertData) {
                // Toast the user for a successful insert
                Toast.makeText(this, "Data successfully added.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(AddVehicleActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "An error occurred adding vehicle.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private String decodeVinMake(String vin) {
        // Take the vin, and decode the manufacturer code
        Map<String, String> map = new HashMap<>();
        map.put("AAV", "Volkswagen");
        map.put("AHT", "Toyota");
        map.put("AFA", "Ford");
        map.put("CL9", "Wallyscar");
        map.put("JA", "Isuzu");
        map.put("JC1", "Fiat Automobiles/Mazda");
        map.put("JF", "Subaru");
        map.put("JHL", "Honda");
        map.put("JHM", "Honda");
        map.put("JMB", "Mitsubishi");
        map.put("JM6", "Mazda");
        map.put("JN", "Nissan");
        map.put("JS", "Suzuki");
        map.put("JT", "Toyota");
        map.put("JY", "Yamaha");
        map.put("KL", "Daewoo");
        map.put("KMH", "Hyundai");
        map.put("KN", "Kia");
        map.put("KPT", "SsangYong");
        map.put("L6T", "Geely");
        map.put("LBE", "Beijing Hyundai");
        map.put("LBV", "BMW Brilliance");
        map.put("LDC", "Dongfeng Peugeot-Citroën");
        map.put("LE4", "Beijing Benz");
        map.put("LFM", "FAW Toyota (Sichuan)");
        map.put("LFP", "FAW Car");
        map.put("LFV", "FAW-Volkswagen");
        map.put("LGB", "Dongfeng Nissan");
        map.put("LGJ", "Dongfeng Fengshen");
        map.put("LGW", "Great Wall (Havel)");
        map.put("LGX", "BYD Auto");
        map.put("LH1", "FAW Haima");
        map.put("LHG", "Guangzhou Honda");
        map.put("LJ1", "JAC");
        map.put("LJD", "Dongfeng Yueda Kia");
        map.put("LLV", "Lifan");
        map.put("LMG", "GAC Trumpchi");
        map.put("LPA", "Changan PSA (DS Automobiles)");
        map.put("LS5", "Changan Suzuki");
        map.put("LSG", "SAIC General Motors");
        map.put("LSJ", "SAIC MG");
        map.put("LSV", "SAIC Volkswagen");
        map.put("LTV", "FAW Toyota (Tianjin)");
        map.put("LVG", "GAC Toyota");
        map.put("LVH", "Dongfeng Honda");
        map.put("LVR", "Changan Mazda");
        map.put("LVS", "Changan Ford");
        map.put("LVV", "Chery");
        map.put("LWV", "GAC Fiat");
        map.put("LZW", "SAIC GM Wuling");
        map.put("MM0", "Mazda");
        map.put("MS0", "KIA Myanmar");
        map.put("NMT", "Toyota");
        map.put("NM0", "Ford Otosan");
        map.put("PL1", "Proton");
        map.put("SAJ", "Jaguar");
        map.put("SAL", "Land Rover");
        map.put("SAR", "Rover");
        map.put("SAT", "Triumph");
        map.put("SB1", "Toyota");
        map.put("SCC", "Lotus");
        map.put("SCF", "Aston Martin Lagonda Limited");
        map.put("SCE", "DeLorean");
        map.put("SFD", "Alexander Dennis");
        map.put("SHH", "Honda");
        map.put("SHS", "Honda");
        map.put("SJN", "Nissan");
        map.put("TCC", "Micro Compact Car AG (SMART 1998-1999)");
        map.put("TMA", "Hyundai");
        map.put("TMB", "Škoda");
        map.put("TRU", "Audi");
        map.put("TSM", "Suzuki");
        map.put("U5Y", "Kia");
        map.put("UU", "Dacia");
        map.put("VA0", "ÖAF");
        map.put("VF1", "Renault");
        map.put("VF2", "Renault");
        map.put("VF3", "Peugeot");
        map.put("VF4", "Talbot");
        map.put("VF5", "Iveco Unic SA");
        map.put("VF6", "Renault Trucks/Volvo");
        map.put("VF7", "Citroën");
        map.put("VF8", "Matra/Talbot/Simca");
        map.put("VF9", "Bugatti");
        map.put("VFE", "IvecoBus");
        map.put("VNK", "Toyota");
        map.put("VSS", "SEAT");
        map.put("VV9", "Tauro Sport Auto");
        map.put("WAG", "Neoplan");
        map.put("WAU", "Audi");
        map.put("WAP", "Alpina");
        map.put("WBA", "BMW");
        map.put("WBS", "BMW M");
        map.put("WBX", "BMW");
        map.put("WDB", "Mercedes-Benz");
        map.put("WDC", "DaimlerChrysler AG/Daimler AG");
        map.put("WDD", "DaimlerChrysler AG/Daimler AG");
        map.put("WMX", "DaimlerChrysler AG/Daimler AG");
        map.put("WEB", "EvoBus");
        map.put("WF0", "Ford of Europe");
        map.put("WJM", "Iveco");
        map.put("WJR", "Irmscher");
        map.put("WKK", "Karl Kässbohrer Fahrzeugwerke");
        map.put("WMA", "MAN");
        map.put("WME", "Smart");
        map.put("WMW", "Mini");
        map.put("WP0", "Porsche");
        map.put("WP1", "Porsche SUV");
        map.put("WUA", "Quattro");
        map.put("WVG", "Volkswagen");
        map.put("WVW", "Volkswagen");
        map.put("WV1", "Volkswagen Commercial");
        map.put("WV2", "Volkswagen Commercial");
        map.put("W09", "Ruf Automobile");
        map.put("W0L", "Opel/Vauxhall");
        map.put("W0SV", "Opel Special Vehicle");
        map.put("XLR", "DAF Trucks");
        map.put("YK1", "Saab");
        map.put("YS2", "Scania, Södertälje");
        map.put("YS3", "Saab");
        map.put("YS4", "Scania, Katrineholm");
        map.put("YTN", "Saab NEVS");
        map.put("YV1", "Volvo Car");
        map.put("YV2", "Volvo Truck");
        map.put("YV3", "Volvo Bus");
        map.put("ZA9", "Bugatti");
        map.put("ZAM", "Maserati");
        map.put("ZAR", "Alfa Romeo");
        map.put("ZCF", "Iveco");
        map.put("ZFA", "Fiat");
        map.put("ZFF", "Ferrari");
        map.put("ZGA", "IvecoBus");
        map.put("ZHW", "Lamborghini");
        map.put("ZLA", "Lancia");
        map.put("1B", "Dodge");
        map.put("1C", "Chrysler");
        map.put("1F", "Ford");
        map.put("1G", "General Motors");
        map.put("1G1", "Chevrolet");
        map.put("1G3", "Oldsmobile");
        map.put("1G9", "Google");
        map.put("1GC", "Chevrolet");
        map.put("1GM", "Pontiac");
        map.put("1HG", "Honda");
        map.put("1J", "Jeep");
        map.put("1L", "Lincoln");
        map.put("1M", "Mercury");
        map.put("1N", "Nissan");
        map.put("1VW", "Volkswagen");
        map.put("1YV", "Mazda");
        map.put("2DG", "Ontario Drive & Gear");
        map.put("2F", "Ford");
        map.put("2G", "General Motors");
        map.put("2G1", "Chevrolet");
        map.put("2G2", "Pontiac");
        map.put("2HG", "Honda");
        map.put("2HH", "Acura");
        map.put("2HJ", "Honda");
        map.put("2HK", "Honda");
        map.put("2HM", "Hyundai");
        map.put("2M", "Mercury");
        map.put("2T", "Toyota");
        map.put("3F", "Ford");
        map.put("3G", "General Motors");
        map.put("3HG", "Honda");
        map.put("3HM", "Honda");
        map.put("3KP", "Kia");
        map.put("3N", "Nissan");
        map.put("3VW", "Volkswagen");
        map.put("4F", "Mazda");
        map.put("4J", "Mercedes-Benz");
        map.put("4M", "Mercury");
        map.put("4S3", "Subaru");
        map.put("4S4", "Subaru");
        map.put("4S6", "Honda");
        map.put("4T", "Toyota");
        map.put("4US", "BMW");
        map.put("5FN", "Honda");
        map.put("5J6", "Honda");
        map.put("5L", "Lincoln");
        map.put("5N1", "Nissan");
        map.put("5NM", "Hyundai");
        map.put("5NP", "Hyundai");
        map.put("5T", "Toyota");
        map.put("5U", "BMW");
        map.put("5X", "Hyundai/Kia");
        map.put("5YJ", "Tesla");
        map.put("55", "Mercedes-Benz");
        map.put("6F", "Ford");
        map.put("6G", "General Motors");
        map.put("6G1", "Chevrolet");
        map.put("6G2", "Pontiac");
        map.put("6H", "Holden");
        map.put("6MM", "Mitsubishi");
        map.put("6T1", "Toyota");
        map.put("7A3", "Honda");
        map.put("8AP", "Fiat");
        map.put("8AF", "Ford");
        map.put("8AG", "General Motors");
        map.put("8AW", "Volkswagen");
        map.put("8AJ", "Toyota");
        map.put("8A1", "Renault");
        map.put("8AC", "Mercedes Benz");
        map.put("8BC", "Citroën");
        map.put("8AD", "Peugeot");
        map.put("8C3", "Honda");
        map.put("8AT", "Iveco");
        map.put("9BD", "Fiat Automóveis");
        map.put("9BG", "General Motors");
        map.put("9BW", "Volkswagen");
        map.put("9BF", "Ford");
        map.put("93H", "Honda");
        map.put("9BR", "Toyota");
        map.put("936", "Peugeot");
        map.put("935", "Citroën");
        map.put("93Y", "Renault");
        map.put("93X", "Souza Ramos - Mitsubishi / Suzuki");
        map.put("9BH", "Hyundai Motor Company / Hyundai");
        map.put("95P", "CAOA / Hyundai");
        map.put("94D", "Nissan");
        map.put("98R", "Chery");
        map.put("988", "Jeep");
        map.put("98M", "BMW");
        map.put("9BM", "Mercedes Benz");
        map.put("99A", "Audi");
        map.put("99J", "JLR Jaguar Land Rover");
        map.put("9C2", "Honda Motorcycle");
        map.put("9C6", "Yamaha");
        map.put("9CD", "Suzuki Motorcycle");
        map.put("93W", "Fiat Professional");
        map.put("93Z", "Iveco");
        map.put("953", "VW Truck / MAN");
        map.put("9BS", "Scania");
        map.put("9BV", "Volvo Truck");
        map.put("9UJ", "Chery");
        map.put("9UK", "Lifan");
        map.put("9UW", "Kia");


        // Get the first 3 characters of the vin
        String wmi = vin.substring(0,3).toUpperCase();
        if (map.containsKey(wmi)) {
            return map.get(wmi);
        } else {
            // Check to see if it is a two digit wmi
            wmi = vin.substring(0,2).toUpperCase();
            return map.get(wmi);
        }
    }
}
