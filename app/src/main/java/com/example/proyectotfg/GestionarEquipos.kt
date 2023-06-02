package com.example.proyectotfg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectotfg.adapterEquipos.TeamAdapter
import com.example.proyectotfg.databinding.ActivityGestionarEquiposBinding
import com.google.firebase.firestore.FirebaseFirestore

class GestionarEquipos : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    private lateinit var binding: ActivityGestionarEquiposBinding
    private lateinit var adapterEquipos: TeamAdapter
    private lateinit var listaEquipos: ArrayList<Equipos>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGestionarEquiposBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnbuscarequipo.setOnClickListener {
            if (binding.editTextconsultarequipo.text.isNotEmpty()) {

                val nombreEquipo = binding.editTextconsultarequipo.text.toString()

                db.collection("Equipos")
                    .whereEqualTo("Nombre", nombreEquipo)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            val team = document.toObject(Equipos::class.java)
                            team.Nombre = document["Nombre"].toString()
                            team.Sede = document["Sede"].toString()
                            team.PaginaWeb = document["PaginaWeb"].toString()
                            team.Foto = document["Foto"].toString()
                            listaEquipos.add(team)
                        }
                    }
            } else {
                Toast.makeText(this, "Introduzca el nombre de algún equipo", Toast.LENGTH_SHORT).show()
            }
        }

            listaEquipos = ArrayList()
            adapterEquipos = TeamAdapter(listaEquipos)

            db.collection("Equipos").get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val team = document.toObject(Equipos::class.java)
                        team.Nombre = document["Nombre"].toString()
                        team.Sede = document["Sede"].toString()
                        team.PaginaWeb = document["PaginaWeb"].toString()
                        team.Foto = document["Foto"].toString()
                        listaEquipos.add(team)
                    }
                }

            binding.recyclerequipos.layoutManager = LinearLayoutManager(this)
            binding.recyclerequipos.adapter = adapterEquipos

    }
}