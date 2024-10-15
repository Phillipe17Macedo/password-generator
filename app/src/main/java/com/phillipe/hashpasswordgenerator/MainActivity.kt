package com.phillipe.hashpasswordgenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var tvGeneratedPassword: TextView
    private lateinit var btnRegenerate: Button
    private lateinit var btnCopy: Button
    private lateinit var seekBarLength: SeekBar
    private lateinit var cbUppercase: CheckBox
    private lateinit var cbLowercase: CheckBox
    private lateinit var cbNumbers: CheckBox
    private lateinit var cbSpecialChars: CheckBox
    private lateinit var cbExcludeSimilar: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializando as views
        tvGeneratedPassword = findViewById(R.id.tvGeneratedPassword)
        btnRegenerate = findViewById(R.id.btnRegenerate)
        btnCopy = findViewById(R.id.btnCopy)
        seekBarLength = findViewById(R.id.seekBarLength)
        cbUppercase = findViewById(R.id.cbUppercase)
        cbLowercase = findViewById(R.id.cbLowercase)
        cbNumbers = findViewById(R.id.cbNumbers)
        cbSpecialChars = findViewById(R.id.cbSpecialChars)
        cbExcludeSimilar = findViewById(R.id.cbExcludeSimilar)

        // Gerando a senha inicial
        generatePassword()

        // Configurando o botão de regenerar
        btnRegenerate.setOnClickListener {
            generatePassword()
        }

        // Configurando o botão de copiar
        btnCopy.setOnClickListener {
            val password = tvGeneratedPassword.text.toString()
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = android.content.ClipData.newPlainText("Senha", password)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Senha copiada para a área de transferência", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generatePassword() {
        val length = seekBarLength.progress
        val uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val lowercase = "abcdefghijklmnopqrstuvwxyz"
        val numbers = "0123456789"
        val specialChars = "!@#$%^&*()-_=+<>?"
        val similarChars = "Il1O0"

        var charPool = ""

        if (cbUppercase.isChecked) charPool += uppercase
        if (cbLowercase.isChecked) charPool += lowercase
        if (cbNumbers.isChecked) charPool += numbers
        if (cbSpecialChars.isChecked) charPool += specialChars
        if (cbExcludeSimilar.isChecked) charPool = charPool.filter { !similarChars.contains(it) }

        if (charPool.isEmpty()) {
            tvGeneratedPassword.text = "Selecione pelo menos uma opção"
            return
        }

        val password = (1..length)
            .map { Random.nextInt(0, charPool.length) }
            .map(charPool::get)
            .joinToString("")

        tvGeneratedPassword.text = password
    }
}
