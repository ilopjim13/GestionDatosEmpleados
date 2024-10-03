package org.example

import org.example.repository.EmpleadoRepository
import org.example.repository.XmlRepository
import java.nio.file.Path

fun main() {

    val fileCsv = Path.of("src/main/resources/empleados.csv")
    val fileXml = Path.of("src/main/resources/empleados.xml")

    val repository = EmpleadoRepository(fileCsv)
    val empleados = repository.generateEmpleados()

    val xmlRepository = XmlRepository(fileXml)
    xmlRepository.empleadosXml(empleados)

    xmlRepository.empleadosXml(empleados, 1, 1333.3)

    xmlRepository.readXml()

}