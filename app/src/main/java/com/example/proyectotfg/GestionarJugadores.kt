package com.example.proyectotfg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listadoparques.adapter.PlayerAdapter
import com.example.proyectotfg.adapterEquipos.TeamAdapter
import com.example.proyectotfg.databinding.ActivityGestionarJugadoresBinding
import com.google.firebase.firestore.FirebaseFirestore

class GestionarJugadores : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    private lateinit var binding: ActivityGestionarJugadoresBinding
    private lateinit var adapterJugadores: PlayerAdapter
    private lateinit var listaJugadores: ArrayList<Jugadores>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGestionarJugadoresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnbuscarjugadores.setOnClickListener {
            if (binding.editTextconsultarjugadores.text.isNotEmpty()) {
                val nombreJugador = binding.editTextconsultarjugadores.text.toString()

                db.collection("Jugadores")
                    .whereEqualTo("Nombre", nombreJugador)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            val player = document.toObject(Jugadores::class.java)
                            player.Nickname = document["Nickname"].toString()
                            player.Nacionalidad = document["Nacionalidad"].toString()
                            player.Equipo = document["Equipo"].toString()
                            player.Foto = document["Foto"].toString()
                            listaJugadores.add(player)
                        }
                    }
            }
            else
            {
                Toast.makeText(this, "Introduzca el nombre de algún jugador", Toast.LENGTH_SHORT).show()
            }
        }

        listaJugadores = ArrayList()
        adapterJugadores = PlayerAdapter(listaJugadores)

        db.collection("Jugadores").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val player = document.toObject(Jugadores::class.java)
                    player.Nickname = document["Nickname"].toString()
                    player.Nacionalidad = document["Nacionalidad"].toString()
                    player.Equipo = document["Equipo"].toString()
                    player.Foto = document["Foto"].toString()
                    listaJugadores.add(player)
                }
            }

        binding.recyclerjugadores.layoutManager = LinearLayoutManager(this)
        binding.recyclerjugadores.adapter = adapterJugadores
    }
}