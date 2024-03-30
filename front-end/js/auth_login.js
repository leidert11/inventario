const redirectSignIn = document.getElementById("sign-up-btn");

redirectSignIn.addEventListener("click", function () {
  window.location.href = "sign-in.html";
  console.log('hola1');
})

const loginForm = document.getElementById("formUserToken");
const loginUserNameInput = document.getElementById("adminToken");
const loginPasswordInput = document.getElementById("passwordToken");

loginForm.addEventListener("submit", async function (event) {
  event.preventDefault();

  const userName = loginUserNameInput.value;
  const password = loginPasswordInput.value;

  const loginFormData = new FormData();
  loginFormData.append("username", userName);
  loginFormData.append("password", password);

  await fetch("http://localhost:8080/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    body: `username=${encodeURIComponent(
      userName
    )}&password=${encodeURIComponent(password)}`,
  })
    .then((response) => {
      if (!response.ok) {
        alert("Incorrect username or password");
        throw new Error(
          "Error obtaining login token. HTTP status code:" +
            response.status
        );
      }
      return response.text(); 
    })
    .then((token) => {
      console.log(token)
      sessionStorage.setItem("jwtToken", token.trim()); //Save the JWT token in sessionStorage
      alert("Successfully generated token");
      window.location.href = "index.html";
    })
    .catch((error) => {
      console.error("Error obtaining login token:", error);
      loginUserNameInput.value = ''; 
      loginPasswordInput.value = ''; 
    });
});

