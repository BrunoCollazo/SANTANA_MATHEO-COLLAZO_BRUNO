document.addEventListener("DOMContentLoaded", function () {
  // Cargar la vista inicial
  loadView("pacientes");

  // Menú
  document
    .getElementById("menu-pacientes")
    .addEventListener("click", function () {
      loadView("pacientes");
    });

  document
    .getElementById("menu-odontologos")
    .addEventListener("click", function () {
      loadView("odontologos");
    });

  document.getElementById("menu-turnos").addEventListener("click", function () {
    loadView("turnos");
  });
});

function loadView(view) {
  const content = document.getElementById("content");
  content.innerHTML = ""; // Limpiar contenido

  if (view === "pacientes") {
    content.innerHTML = `
            <h2>Listado de Pacientes</h2>
            <button onclick="crearPaciente()">Nuevo Paciente</button>
            <div id="pacientes-list"></div>
        `;
    listarPacientes(); // Cargar pacientes
  } else if (view === "odontologos") {
    content.innerHTML = `
            <h2>Listado de Odontólogos</h2>
            <button onclick="crearOdontologo()">Nuevo Odontólogo</button>
            <div id="odontologos-list"></div>
        `;
    listarOdontologos(); // Cargar odontólogos
  } else if (view === "turnos") {
    content.innerHTML = `
            <h2>Listado de Turnos</h2>
            <button onclick="crearTurno()">Nuevo Turno</button>
            <div id="turnos-list"></div>
        `;
    listarTurnos(); // Cargar turnos
  }
}

// Funciones CRUD (se puede conectar con la API utilizando fetch)
function listarPacientes() {
  const pacientesList = document.getElementById("pacientes-list");
  pacientesList.innerHTML = "<p>Cargando pacientes...</p>";

  // Realiza la petición GET al backend
  fetch("http://localhost:8080/pacientes/listar", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => response.json())
    .then((pacientes) => {
      // Limpiar el contenido previo
      pacientesList.innerHTML = "";

      // Renderiza los pacientes en la página
      pacientes.forEach((paciente) => {
        pacientesList.innerHTML += `
                <div>
                    <h3>${paciente.nombre} ${paciente.apellido}</h3>
                    <p>DNI: ${paciente.dni}</p>
                    <button onclick="editarPaciente(${paciente.id})">Editar</button>
                    <button onclick="eliminarPaciente(${paciente.id})">Eliminar</button>
                </div>
            `;
      });
    })
    .catch((error) => {
      console.error("Error al cargar los pacientes:", error);
      pacientesList.innerHTML = "<p>Error al cargar los pacientes.</p>";
    });
}

function listarOdontologos() {
  const odontologosList = document.getElementById("odontologos-list");
  // Aquí iría el código para listar odontólogos con fetch a la API
  odontologosList.innerHTML = "<p>Cargando odontólogos...</p>";
}

function listarTurnos() {
  const turnosList = document.getElementById("turnos-list");
  // Aquí iría el código para listar turnos con fetch a la API
  turnosList.innerHTML = "<p>Cargando turnos...</p>";
}

function crearPaciente() {
  const nombre = prompt("Nombre del paciente:");
  const apellido = prompt("Apellido del paciente:");
  const dni = prompt("DNI del paciente:");
  const calle = prompt("Calle del paciente:");
  const numero = prompt("Número del paciente:");
  const localidad = prompt("Localidad del paciente:");
  const provincia = prompt("Provincia del paciente:");

  const fechaIngreso = new Date().toISOString().split("T")[0];

  if (nombre && apellido && dni) {
    const nuevoPaciente = {
      nombre: nombre,
      apellido: apellido,
      dni: dni,
      fechaIngreso: fechaIngreso,
      domicilioEntradaDto: {
        calle: calle,
        numero: numero,
        localidad: localidad,
        provincia: provincia,
      },
    };

    fetch("http://localhost:8080/pacientes/registrar", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(nuevoPaciente),
    })
      .then((response) => response.json())
      .then((pacienteCreado) => {
        alert(
          `Paciente creado con éxito: ${pacienteCreado.nombre} ${pacienteCreado.apellido}`
        );
        listarPacientes(); // Refresca la lista de pacientes
      })
      .catch((error) => console.error("Error al crear paciente:", error));
  } else {
    alert("Debes completar todos los campos");
  }
}

function crearOdontologo() {
  alert("Función para crear un nuevo odontólogo");
}

function crearTurno() {
  alert("Función para crear un nuevo turno");
}
