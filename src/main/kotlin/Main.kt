package org.example

import org.example.repository.EmpleadoRepository
import java.nio.file.Path

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    val fileCsv = Path.of("src/main/resources/empleados.csv")

    val repository = EmpleadoRepository(fileCsv)

    val empleados = repository.generateEmpleados()

}