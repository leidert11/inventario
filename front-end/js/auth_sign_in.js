const redirectLogin = document.getElementById("login-btn");

redirectLogin.addEventListener("click", function () {
  window.location.href = "login.html";
  console.log('hola');
})

const registerButton = document.getElementById("btnRegister");
    const registerUserNameInput = document.getElementById("adminUser");
    const registerPasswordInput = document.getElementById("passwordUser");

    registerButton.addEventListener("click", async function (event) {
      event.preventDefault();

      const userName = registerUserNameInput.value;
      const password = registerPasswordInput.value;

      const registerFormData = {
        username: userName,
        password: password,
      };

      await fetch("http://localhost:8080/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(registerFormData),
      })
        .then((response) => response.json())
        .then((data) => {
          console.log("Success:", data);
          alert("Successful Register");
          window.location.href = "login.html";
        })
        .catch((error) => {
          console.error("Registry error:", error);
        });
    });


