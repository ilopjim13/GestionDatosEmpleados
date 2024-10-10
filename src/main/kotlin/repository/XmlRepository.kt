package org.example.repository

import org.example.console.Console
import org.example.model.Empleado
import org.w3c.dom.DOMImplementation
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.nio.file.Path
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class XmlRepository(private val fileXml:Path, private val console: Console) {

    fun empleadosXml(listaEmpleados: List<Empleado>) {
        val dip = implementationXml()
        val document = dip.createDocument(null, "empleados", null)

        writeXml(document,listaEmpleados)

    }

    private fun writeXml(document: Document, listaEmpleados: List<Empleado>, id:Int = -1, sal:Double = -1.0) {
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
        transformer(document)
    }

    private fun transformer(document: Document) {
        val source = DOMSource(document)
        val result = StreamResult(fileXml.toFile())
        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.transform(source, result)
    }

    fun modifyXml(id:Int, sal:Double) {
        val dct = parseXml(fileXml)
        val root = dct.documentElement
        root.normalize()

        val listaNodos = root.getElementsByTagName("empleado")
        val listaEmpleados: MutableList<Empleado> = mutableListOf()

        readElements(listaNodos, listaEmpleados)

        val dip = implementationXml()
        val document = dip.createDocument(null, "empleados", null)

        writeXml(document,listaEmpleados, id, sal)
    }

    private fun readElements(listaNodos: NodeList, listaEmpleados: MutableList<Empleado>? = null) {
        for (i in 0..<listaNodos.length) {
            val nodo = listaNodos.item(i)

            if (nodo.nodeType == Node.ELEMENT_NODE) {
                val nodoElemento = nodo as Element
                val ids = nodoElemento.getAttribute("id").toIntOrNull() ?: 0
                val apellido = nodoElemento.getElementsByTagName("apellido").item(0).textContent
                val departamento = nodoElemento.getElementsByTagName("departamento").item(0).textContent
                val salario = nodoElemento.getElementsByTagName("salario").item(0).textContent.toDouble()

                val empleado = Empleado(ids, apellido, departamento, salario)
                if (listaEmpleados != null) listaEmpleados.add(empleado)
                else console.showMessage(empleado.toString())
            }
        }
    }


    fun readXml() {
        val document = parseXml(fileXml)
        val root = document.documentElement
        root.normalize()

        val listaNodos = root.getElementsByTagName("empleado")
        readElements(listaNodos)
    }

    fun newEmpleado(newEmple:Empleado) {
        val document = parseXml(fileXml)
        val root = document.documentElement
        root.normalize()

        val elementoNuevoEmpleado = document.createElement("empleado")
        elementoNuevoEmpleado.setAttribute("id", newEmple.id.toString())

        root.appendChild(elementoNuevoEmpleado)

        val apellido = document.createElement("apellido")
        val departamento = document.createElement("departamento")
        val salario = document.createElement("salario")
        elementoNuevoEmpleado.appendChild(apellido)
        elementoNuevoEmpleado.appendChild(departamento)
        elementoNuevoEmpleado.appendChild(salario)

        val textApellido = document.createTextNode(newEmple.apellido)
        val textDepartamento = document.createTextNode(newEmple.departamento)
        val textSalario = document.createTextNode(newEmple.salario.toString())

        apellido.appendChild(textApellido)
        departamento.appendChild(textDepartamento)
        salario.appendChild(textSalario)

        transformer(document)
    }

    fun deleteEmple(id:Int) {
        val dct = parseXml(fileXml)
        val root = dct.documentElement
        root.normalize()

        val listaNodos = root.getElementsByTagName("empleado")
        val listaEmpleados: MutableList<Empleado> = mutableListOf()

        readElements(listaNodos, listaEmpleados)

        val dip = implementationXml()
        val document = dip.createDocument(null, "empleados", null)

        listaEmpleados.forEach {
            if(it.id != id) {
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
                val textSalario = document.createTextNode(it.salario.toString())

                apellido.appendChild(textApellido)
                departamento.appendChild(textDepartamento)
                salario.appendChild(textSalario)
            }
        }
        transformer(document)
    }

    private fun parseXml(fileXml: Path):Document {
        val dbf = DocumentBuilderFactory.newInstance()
        val db = dbf.newDocumentBuilder()
        return db.parse(fileXml.toFile())
    }

    private fun implementationXml() :DOMImplementation {
        val dbf = DocumentBuilderFactory.newInstance()
        val db = dbf.newDocumentBuilder()
        return db.domImplementation
    }

}