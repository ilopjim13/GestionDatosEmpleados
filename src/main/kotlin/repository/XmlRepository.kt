package org.example.repository

import org.example.model.Empleado
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.nio.file.Path
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class XmlRepository(private val fileXml:Path) {

    fun empleadosXml(listaEmpleados: List<Empleado>, id:Int = -1, sal:Double = -1.0) {
        val dbf = DocumentBuilderFactory.newInstance()
        val db = dbf.newDocumentBuilder()
        val dip = db.domImplementation
        val document = dip.createDocument(null, "empleados", null)

        listaEmpleados.forEach {
            val empleado = document.createElement("empleado")
            empleado.setAttribute("id", it.id.toString())
            document.documentElement.appendChild(empleado)

            val apellido = document.createElement("apellido")
            val departamento = document.createElement("departamento")
            val salario = document.createElement("salario")
            empleado.appendChild(apellido)
            empleado.appendChild(departamento)
            empleado.appendChild(salario)

            val textApellido = document.createTextNode(it.apellido)
            val textDepartamento = document.createTextNode(it.departamento)
            val textSalario = if(it.id == id && sal >= 0) document.createTextNode(sal.toString()) else document.createTextNode(it.salario.toString())

            apellido.appendChild(textApellido)
            departamento.appendChild(textDepartamento)
            salario.appendChild(textSalario)
        }

        val source = DOMSource(document)
        val result = StreamResult(fileXml.toFile())

        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")

        transformer.transform(source, result)
    }

    fun readXml() {
        val dbf = DocumentBuilderFactory.newInstance()
        val db = dbf.newDocumentBuilder()
        val document = db.parse(fileXml.toFile())
        val root = document.documentElement
        root.normalize()

        val listaNodos = root.getElementsByTagName("empleado")
        for (i in 0..<listaNodos.length ) {
            val nodo = listaNodos.item(i)

            if (nodo.nodeType == Node.ELEMENT_NODE) {
                val nodoElement = nodo as Element
                val id = nodoElement.getAttribute("id")
                val apellido = nodoElement.getElementsByTagName("apellido").item(0).textContent
                val departamento = nodoElement.getElementsByTagName("departamento").item(0).textContent
                val salario = nodoElement.getElementsByTagName("salario").item(0).textContent

                println("ID: $id, Apellido: $apellido, Departamento: $departamento, Salario: $salario")
            }
        }
    }

}