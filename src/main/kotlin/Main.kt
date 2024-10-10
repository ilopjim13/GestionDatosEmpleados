package org.example

import org.example.console.Console
import org.example.model.Empleado
import org.example.repository.EmpleadoRepository
import org.example.repository.XmlRepository
import java.nio.file.Path

fun main() {

    val console = Console()

    val fileCsv = Path.of("src/main/resources/empleados.csv")
    val fileXml = Path.of("src/main/resources/empleados.xml")

    val repository = EmpleadoRepository(fileCsv)
    val empleados = repository.generateEmpleados()

    val xmlRepository = XmlRepository(fileXml, console)

    var option:Int
    do {
        console.showMenu()
        option = console.askOption(4)
        executeMenu(option, xmlRepository, empleados, console)

    } while (option != 4)
}

fun executeMenu(option:Int, repository: XmlRepository, empleados:List<Empleado>, console: Console) {
    when (option) {
        1 -> repository.empleadosXml(empleados)
        2 -> {
            val id = console.askId()
            val sal = console.askSal()
            repository.modifyXml(id, sal)
        }
        3-> repository.readXml()
    }
}