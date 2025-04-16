package com.example.exp10_100

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var telephonyManager: TelephonyManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize TelephonyManager
        telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager

        // Set up button click listeners
        findViewById<Button>(R.id.btnGetTelephonyServices).setOnClickListener {
            getTelephonyInfo()
        }

        findViewById<Button>(R.id.btnPermission).setOnClickListener {
            requestPhoneStatePermission()
        }
    }

    private fun getTelephonyInfo() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Get and display telephony information
            val operatorName = telephonyManager.networkOperatorName
            val phoneType = when (telephonyManager.phoneType) {
                TelephonyManager.PHONE_TYPE_GSM -> "GSM"
                TelephonyManager.PHONE_TYPE_CDMA -> "CDMA"
                TelephonyManager.PHONE_TYPE_SIP -> "SIP"
                TelephonyManager.PHONE_TYPE_NONE -> "NONE"
                else -> "UNKNOWN"
            }
            val networkCountryIso = telephonyManager.networkCountryIso.uppercase()
            val simCountryIso = telephonyManager.simCountryIso?.uppercase() ?: "N/A"
            val deviceSoftwareVersion = telephonyManager.deviceSoftwareVersion ?: "N/A"

            findViewById<TextView>(R.id.tvOperatorName).text = operatorName
            findViewById<TextView>(R.id.tvPhoneType).text = phoneType
            findViewById<TextView>(R.id.tvNetworkCountryIso).text = networkCountryIso
            findViewById<TextView>(R.id.tvSimCountryIso).text = simCountryIso
            findViewById<TextView>(R.id.tvDeviceSoftwareVersion).text = deviceSoftwareVersion
        } else {
            Toast.makeText(
                this,
                "Permission not granted to access telephony information",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun requestPhoneStatePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                PERMISSION_REQUEST_CODE
            )
        } else {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show()
                    getTelephonyInfo()
                } else {
                    Toast.makeText(
                        this,
                        "Permission denied. Some features may not work.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
    }
}