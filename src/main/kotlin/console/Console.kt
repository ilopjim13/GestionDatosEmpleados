package org.example.console

class Console {

    fun showMessage(msg:String, br:Boolean = true) {
        if (br)println(msg)
        else print(msg)
    }

    fun showMenu() {
        showMessage("1. Guardar datos en xml")
        showMessage("2. Modificar los datos en el xml")
        showMessage("3. Mostrar los datos del xml")
        showMessage("4. Salir")
    }

    fun askOption(opciones: Int):Int {
        var option:Int
        do {
            showMessage(">> Seleccione una opcion: ", false)
            option = readln().toIntOrNull() ?: -1
            if (option !in 1..opciones) showMessage("Error introduce una opción válida")
        } while(option !in 1..opciones)

        return option
    }

    fun askId():Int {
        var id:Int
        do {
            showMessage(">> Introduce un id: ", false)
            id = readln().toIntOrNull() ?: -1
            if (id < 0) showMessage("Error introduce un id válido")
        } while(id < 0)

        return id
    }

    fun askSal():Double {
        var sal:Double
        do {
            showMessage(">> Introduce un salario: ", false)
            sal = readln().toDoubleOrNull() ?: -1.0
            if (sal < 0) showMessage("Error introduce un salario válido")
        } while(sal < 0)

        return sal
    }
}