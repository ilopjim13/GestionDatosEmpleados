package org.example.repository

import org.example.model.Empleado
import java.nio.file.Files
import java.nio.file.Path

class EmpleadoRepository(private val fileCsv: Path) {

    fun generateEmpleados() :List<Empleado> {
        val listaEmpleados :MutableList<Empleado> = mutableListOf()

        val br = Files.newBufferedReader(fileCsv)
        br.use { flujo ->
            flujo.forEachLine {
                val line = it.split(",")
                if (line[0].toIntOrNull() != null) {
                    val empleado = Empleado(line[0].toInt(), line[1], line[2], line[3].toDouble())
                    listaEmpleados.add(empleado)
                }

            }
        }

        return listaEmpleados
    }

}