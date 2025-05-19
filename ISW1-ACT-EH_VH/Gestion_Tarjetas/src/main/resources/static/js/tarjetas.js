document.addEventListener("DOMContentLoaded", function () {
    const filtro = document.getElementById("filtroCliente");
    const filas = document.querySelectorAll("table tbody tr");

    filtro.addEventListener("change", function () {
        const clienteId = this.value;
        filas.forEach(fila => {
            const idCliente = fila.getAttribute("data-cliente");
            fila.style.display = (clienteId === "todos" || clienteId === idCliente) ? "" : "none";
        });
    });
});

function validarFormulario() {
    const total = parseFloat(document.getElementById("cupoTotal").value);
    const disponible = parseFloat(document.getElementById("cupoDisponible").value);

    if (isNaN(total) || isNaN(disponible)) {
        alert("Los valores de cupo deben ser válidos.");
        return false;
    }

    if (total <= 0 || disponible < 0) {
        alert("Cupo total debe ser mayor a cero y disponible no puede ser negativo.");
        return false;
    }

    if (disponible > total) {
        alert("Cupo disponible no puede ser mayor que el total.");
        return false;
    }

    return true;
}

function validarModificacion(form) {
    const input = form.querySelector('input[name="nuevoCupoTotal"]');
    const valor = parseFloat(input.value);
    if (isNaN(valor) || valor <= 0) {
        alert("El nuevo cupo debe ser mayor a cero.");
        return false;
    }
    return true;
}
