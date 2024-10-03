package org.example.repository

import org.example.model.Empleado
import java.nio.file.Path
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.Source
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class XmlRepository {

    fun createXml(listaEmpleados:MutableList<Empleado>) {

        val dbf = DocumentBuilderFactory.newInstance()
        val db = dbf.newDocumentBuilder()
        val dip = db.domImplementation
        val document = dip.createDocument(null, "empleados", null)

        listaEmpleados.forEach {
            val empleado = document.createElement("empleado id=´${it.id}´")
            document.documentElement.appendChild(empleado)

            val apellido = document.createElement("apellido")
            val departamento = document.createElement("departamento")
            val salario = document.createElement("salario")
            empleado.appendChild(apellido)
            empleado.appendChild(departamento)
            empleado.appendChild(salario)

            val textApellido = document.createTextNode(it.apellido)
            val textDepartamento = document.createTextNode(it.departamento)
            val textSalario = document.createTextNode(it.salario.toString())

            apellido.appendChild(textApellido)
            departamento.appendChild(textDepartamento)
            salario.appendChild(textSalario)
        }

        val source = DOMSource(document)
        //val result = StreamResult(Path.of(""))

    }

}