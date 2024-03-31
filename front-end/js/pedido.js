//1
export function totalPedidosEntregadosByProveedor() {
    const token = sessionStorage.getItem('jwtToken'); 
    fetch('http://localhost:8080/pedido/totalPedidosEntregadosByProveedor', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
    .then(response => response.json())
    .then(data => {
        const clientCardsContainer = document.querySelector('.info-data');
        const point = document.querySelector('#point').textContent = "Total Pedidos Entregados por Proveedor";
        const statement = document.querySelector('#statement').textContent = "Returns a list with the total number of delivered orders by each supplier.";

        let html = '';
        data.forEach(item => {
            html += `
                <div class="card">
                    <div class="head">
                        <div>
                            <h1>Proveedor</h1>
                            <br>
                            <h2>${item[0]}</h2>
                            <p>Total Pedidos Entregados: ${item[1]}</p>
                        </div>
                    </div>
                </div>
            `;
        });
        clientCardsContainer.innerHTML = html;
    })
    .catch(error => console.error('Error:', error));
}
//2
export function productosMasCarosPedidosByProveedor() {
    const token = sessionStorage.getItem('jwtToken'); 
    fetch('http://localhost:8080/pedido/productosMasCarosPedidosByProveedor', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
    .then(response => response.json())
    .then(data => {
        const clientCardsContainer = document.querySelector('.info-data');
        const point = document.querySelector('#point').textContent = "Productos Más Caros Pedidos por Proveedor";
        const statement = document.querySelector('#statement').textContent = "Returns a list with the most expensive products ordered by each supplier.";

        let html = '';
        data.forEach(item => {
            html += `
                <div class="card">
                    <div class="head">
                        <div>
                            <h1>Proveedor</h1>
                            <br>
                            <h2>${item[0]}</h2>
                            <p>Producto Más Caro: ${item[1]}</p>
                            <p>Precio Máximo: ${item[2]}</p>
                        </div>
                    </div>
                </div>
            `;
        });
        clientCardsContainer.innerHTML = html;
    })
    .catch(error => console.error('Error:', error));
}
