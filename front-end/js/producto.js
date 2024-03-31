// Función para obtener el precio promedio de los productos que no han sido pedidos en los últimos 30 días
export function precioPromedioUltimos30Dias() {
    const token = sessionStorage.getItem('jwtToken'); 
    fetch('http://localhost:8080/producto/precioPromedioUltimos30Dias', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
    .then(response => response.json())
    .then(data => {
        const clientCardsContainer = document.querySelector('.info-data');
        const point = document.querySelector('#point').textContent = "Precio Promedio de Productos No Pedidos en los Últimos 30 Días";
        const statement = document.querySelector('#statement').textContent = "Devuelve el precio promedio de los productos que no han sido pedidos en los últimos 30 días.";

        let html = '';
        data.forEach(item => {
            html += `
                <div class="card">
                    <div class="head">
                        <div>
                            <h1>Producto</h1>
                            <br>
                            <h2>${item[0]}</h2>
                            <p>Precio Promedio: ${item[1]}</p>
                        </div>
                    </div>
                </div>
            `;
        });
        clientCardsContainer.innerHTML = html;
    })
    .catch(error => console.error('Error:', error));
}

// Función para obtener los productos que no han sido pedidos en el último trimestre
export function productosNoPedidosUltimoTrimestre() {
    const token = sessionStorage.getItem('jwtToken'); 
    fetch('http://localhost:8080/producto/productosNoPedidosUltimoTrimestre', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
    .then(response => response.json())
    .then(data => {
        const clientCardsContainer = document.querySelector('.info-data');
        const point = document.querySelector('#point').textContent = "Productos No Pedidos en el Último Trimestre";
        const statement = document.querySelector('#statement').textContent = "Devuelve una lista con los productos que no han sido pedidos en el último trimestre.";

        let html = '';
        data.forEach(producto => {
            html += `
                <div class="card">
                    <div class="head">
                        <div>
                            <h1>Producto</h1>
                            <br>
                            <h2>${producto}</h2>
                        </div>
                    </div>
                </div>
            `;
        });
        clientCardsContainer.innerHTML = html;
    })
    .catch(error => console.error('Error:', error));
}

// Función para obtener los productos cuyo precio es superior al promedio de todos los productos
export function productosPrecioSuperiorPromedio() {
    const token = sessionStorage.getItem('jwtToken'); 
    fetch('http://localhost:8080/producto/productosPrecioSuperiorPromedio', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
    .then(response => response.json())
    .then(data => {
        const clientCardsContainer = document.querySelector('.info-data');
        const point = document.querySelector('#point').textContent = "Productos con Precio Superior al Promedio";
        const statement = document.querySelector('#statement').textContent = "Devuelve una lista con los productos cuyo precio es mayor que el promedio de todos los productos.";

        let html = '';
        data.forEach(item => {
            html += `
                <div class="card">
                    <div class="head">
                        <div>
                            <h1>Producto</h1>
                            <br>
                            <h2>${item[0]}</h2>
                            <p>Cantidad: ${item[1]}</p>
                        </div>
                    </div>
                </div>
            `;
        });
        clientCardsContainer.innerHTML = html;
    })
    .catch(error => console.error('Error:', error));
}
