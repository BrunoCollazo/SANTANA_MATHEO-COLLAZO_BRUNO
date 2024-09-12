document.addEventListener("DOMContentLoaded", function () {
  loadView("pacientes");

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
  content.innerHTML = "";

  if (view === "pacientes") {
    content.innerHTML = `
            <h2>Listado de Pacientes</h2>
            <button onclick="buscarPaciente()">Buscar Paciente</button>
            <div id="pacientes-list"></div>
        `;
    listarPacientes();
  } else if (view === "odontologos") {
    content.innerHTML = `
            <h2>Listado de Odontólogos</h2>
            <button onclick="buscarOdontologo()">Buscar Odontólogo</button>
            <div id="odontologos-list"></div>
        `;
    listarOdontologos();
  } else if (view === "turnos") {
    content.innerHTML = `
            <h2>Listado de Turnos</h2>
            <button onclick="buscarTurno()">Buscar Turno</button>
            <div id="turnos-list"></div>
        `;
    listarTurnos();
  }
}

function listarPacientes() {
  const pacientesList = document.getElementById("pacientes-list");
  pacientesList.innerHTML = "<p>Cargando pacientes...</p>";

  fetch("http://localhost:8080/pacientes/listar", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => response.json())
    .then((pacientes) => {
      pacientesList.innerHTML = "";

      let count = 0;
      pacientes.forEach((paciente) => {
        count++;
        pacientesList.innerHTML += `
                <div>
                    <h3>Paciente ${paciente.id}: ${paciente.nombre} ${paciente.apellido}</h3>
                    <p>DNI: ${paciente.dni}</p>
                </div>
            `;
      });
      if (count == 0) {
        pacientesList.innerHTML = "<p>No hay pacientes registrados</p>";
      }
    })
    .catch((error) => {
      console.error("Error al cargar los pacientes:", error);
      pacientesList.innerHTML = "<p>Error al cargar los pacientes.</p>";
    });
}

function listarOdontologos() {
  const odontologosList = document.getElementById("odontologos-list");
  odontologosList.innerHTML = "<p>Cargando odontólogos...</p>";

  fetch("http://localhost:8080/odontologos/listar", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => response.json())
    .then((odontologos) => {
      odontologosList.innerHTML = "";

      let count = 0;
      odontologos.forEach((odontologo) => {
        count++;
        odontologosList.innerHTML += `
                <div>
                    <h3>Odontólogo ${odontologo.id}: ${odontologo.nombre} ${odontologo.apellido}</h3>
                    <p>Matricula: ${odontologo.matricula}</p>
                </div>
            `;
      });
      if (count == 0) {
        odontologosList.innerHTML = "<p>No hay odontólogos registrados</p>";
      }
    })
    .catch((error) => {
      console.error("Error al cargar los odontólogos:", error);
      odontologosList.innerHTML = "<p>Error al cargar los odontólogos.</p>";
    });
}

function listarTurnos() {
  const turnosList = document.getElementById("turnos-list");
  turnosList.innerHTML = "<p>Cargando turnos...</p>";
  fetch("http://localhost:8080/turnos/listar", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => response.json())
    .then((turnos) => {
      turnosList.innerHTML = "";

      let count = 0;
      turnos.forEach((turno) => {
        count++;
        turnosList.innerHTML += `
                <div>
                    <h3>Turno ${turno.id}:</h3>
                    <h4>Paciente: ${turno.turnoPacienteSalidaDto.nombre} ${
          turno.turnoPacienteSalidaDto.apellido
        }</h4>
                    <h4>Odontólogo: ${turno.odontologoSalidaDto.nombre} ${
          turno.odontologoSalidaDto.apellido
        }</h4>
                    <p>Fecha y hora: ${formatDateTime(turno.fechaHora)}</p>
                </div>
            `;
      });
      if (count == 0) {
        turnosList.innerHTML = "<p>No hay turnos registrados</p>";
      }
    })
    .catch((error) => {
      console.error("Error al cargar los turnos:", error);
      turnosList.innerHTML = "<p>Error al cargar los turnos.</p>";
    });
}

function buscarPaciente() {
  const id = prompt("Ingrese id del paciente a buscar");
  if (!isNaN(parseInt(id))) {
    const pacientesList = document.getElementById("pacientes-list");
    pacientesList.innerHTML = "<p>Cargando pacientes...</p>";

    fetch("http://localhost:8080/pacientes/" + id, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((paciente) => {
        pacientesList.innerHTML = "";

        pacientesList.innerHTML += `
                <div>
                    <h3>Paciente ${paciente.id}: ${paciente.nombre} ${paciente.apellido}</h3>
                    <p>DNI: ${paciente.dni}</p>
                </div>
            `;
      })
      .catch((error) => {
        console.error("Error al cargar los pacientes:", error);
        pacientesList.innerHTML = `<p>No se ha podido encontrar el paciente con id ${id}</p>`;
      });
  } else {
    listarPacientes();
  }
}

function buscarOdontologo() {
  const id = prompt("Ingrese id del odontólogo a buscar");
  if (!isNaN(parseInt(id))) {
    const odontologosList = document.getElementById("odontologos-list");
    odontologosList.innerHTML = "<p>Cargando odontólogo...</p>";

    fetch("http://localhost:8080/odontologos/" + id, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((odontologo) => {
        odontologosList.innerHTML = "";

        odontologosList.innerHTML += `
                <div>
                    <h3>Odontólogo ${odontologo.id}: ${odontologo.nombre} ${odontologo.apellido}</h3>
                    <p>Matricula: ${odontologo.matricula}</p>
                </div>
            `;
      })
      .catch((error) => {
        console.error("Error al cargar los odontólogos:", error);
        odontologosList.innerHTML = `<p>No se ha podido encontrar el odontólogo con id ${id}</p>`;
      });
  } else {
    listarOdontologos();
  }
}

function buscarTurno() {
  const id = prompt("Ingrese id del turno a buscar");
  if (!isNaN(parseInt(id))) {
    const turnosList = document.getElementById("turnos-list");
    turnosList.innerHTML = "<p>Cargando turno...</p>";
    fetch("http://localhost:8080/turnos/" + id, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((turno) => {
        turnosList.innerHTML = "";

        turnosList.innerHTML += `
                <div>
                    <h3>Turno ${turno.id}:</h3>
                    <h4>Paciente: ${turno.turnoPacienteSalidaDto.nombre} ${
          turno.turnoPacienteSalidaDto.apellido
        }</h4>
                    <h4>Odontólogo: ${turno.odontologoSalidaDto.nombre} ${
          turno.odontologoSalidaDto.apellido
        }</h4>
                    <p>Fecha y hora: ${formatDateTime(turno.fechaHora)}</p>
                </div>
            `;
      })
      .catch((error) => {
        console.error("Error al cargar los turnos:", error);
        turnosList.innerHTML = `<p>No se ha podido encontrar el turno con id ${id}</p>`;
      });
  } else {
    listarTurnos();
  }
}

function formatDateTime(dateTimeString) {
  const date = new Date(dateTimeString);

  const day = String(date.getDate()).padStart(2, "0");
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const year = date.getFullYear();
  const hours = String(date.getHours()).padStart(2, "0");
  const minutes = String(date.getMinutes()).padStart(2, "0");

  return `${day}/${month}/${year} ${hours}:${minutes}`;
}
