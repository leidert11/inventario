import * as producto from "./js/producto.js";
import * as proveedor from "./js/proveedor.js";
import * as pedido from "./js/pedido.js";

//pedido
document.getElementById("p1").addEventListener("click", function() {
    pedido.totalPedidosEntregadosByProveedor();
});

document.getElementById("p2").addEventListener("click", function() {
    pedido.productosMasCarosPedidosByProveedor();
});

//producto
document.getElementById("product1").addEventListener("click", function() {
    producto.precioPromedioUltimos30Dias();
});
document.getElementById("product2").addEventListener("click", function() {
    producto.productosNoPedidosUltimoTrimestre();
});
document.getElementById("product3").addEventListener("click", function() {
    producto.productosPrecioSuperiorPromedio();
});
