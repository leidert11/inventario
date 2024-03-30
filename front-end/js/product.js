// //1 
// export function getOrnamentalProductsInStock() {
//     const token = sessionStorage.getItem('jwtToken');
//     fetch('http://localhost:8080/product/get-ornamental-products-in-stock', {
//         method: 'GET',
//         headers: {
//             'Authorization': `Bearer ${token}`
//         }
//     })
//     .then(response => response.json())
//     .then(data => {
//         const productCardsContainer = document.querySelector('.info-data');
//         const statement = document.querySelector('#statement').textContent = "Returns a list with all products belonging to the Ornamental range and having more than 100 units in stock. The list should be ordered by their sale price, showing the highest-priced products first.";

//         let html = '';
//         data.forEach(product => {
//             const productName = product[0];
//             const salePrice = product[1];
//             html += `
//                 <div class="card">
//                     <div class="head">
//                         <div>
//                             <h1>${product.name}</h1>
//                             <br>
//                             <h2>Sale Price: $${product.salePrice}</h2>
//                         </div>
//                     </div>
//                 </div>
//             `;
//         });
//         productCardsContainer.innerHTML = html;
//     })
//     .catch(error => console.error('Error:', error));
// }


// //2 
// export function getProductsAreNotInAnyOrder() {
//     const token = sessionStorage.getItem('jwtToken');
//     fetch('http://localhost:8080/product/products-are-not-in-any-order', {
//         method: 'GET',
//         headers: {
//             'Authorization': `Bearer ${token}`
//         }
//     })
//     .then(response => response.json())
//     .then(data => {
//         const productsContainer = document.querySelector('.info-data');
//         const statement = document.querySelector('#statement').textContent = "Returns a list of products that have never appeared in any order.";

//         let html = '';
//         data.forEach(product => {


//             html += `
//             <div class="card">
//                 <div class="head">
//                     <div>   
//                         <h2>${product.name}</h2>
//                         <br>
//                         <h3>Product Code:</h3> <strong>${product.productCode}</strong>
//                     </div>
//                 </div>
//             </div>
//             `;
//         });
//         productsContainer.innerHTML = html;
//     })
//     .catch(error => console.error('Error:', error));
// }


// //3 
// export function getProductsAreNotInAnyOrderNDI() {
//     const token = sessionStorage.getItem('jwtToken');
//     fetch('http://localhost:8080/product/products-are-not-in-any-order-n-d-i', {
//         method: 'GET',
//         headers: {
//             'Authorization': `Bearer ${token}`
//         }
//     })
//     .then(response => response.json())
//     .then(data => {
//         const productCardsContainer = document.querySelector('.info-data');
//         const statement = document.querySelector('#statement').textContent = "Returns a list of products that have never appeared in any order.";

//         let html = '';
//         data.forEach(product => {
//             const name = product[0];
//             const description = product[1];
//             const image = product[2];
//             html += `
//                 <div class="card">
//                     <div class="product-image">
//                         <img src="${image}" alt="${name}">
//                     </div>
//                     <div class="product-details">
//                         <h2>${name}</h2>
//                         <p><strong>Description:</strong> ${description}</p>
//                     </div>
//                 </div>
//             `;
//         });
//         productCardsContainer.innerHTML = html;
//     })
//     .catch(error => console.error('Error:', error));
// }



// // 4
// export function findMaxAndMinPrice() {
//     const token = sessionStorage.getItem('jwtToken');
//     fetch('http://localhost:8080/product/find-max-and-min-price', {
//         method: 'GET',
//         headers: {
//             'Authorization': `Bearer ${token}`
//         }
//     })
//     .then(response => response.json())
//     .then(data => {
//         const maxPrice = data[0][0];
//         const minPrice = data[0][1];

//         const clientCardsContainer = document.querySelector('.info-data');
//         const statement = document.querySelector('#statement').textContent = "Displays the sale price of the most expensive and cheapest product.";

//         const html = `
//             <div class="card">
//                 <div class="head">
//                     <div>
//                         <h1>Max Price</h1>
//                         <br>
//                         <h2>${maxPrice}</h2>
//                     </div>
//                 </div>
//             </div>
//             <div class="card">
//                 <div class="head">
//                     <div>
//                         <h1>Min Price</h1>
//                         <br>
//                         <h2>${minPrice}</h2>
//                     </div>
//                 </div>
//             </div>
//         `;
//         clientCardsContainer.innerHTML = html;
//     })
//     .catch(error => console.error('Error:', error));
// }
