import { StrictMode } from "react";
import { createRoot } from "react-dom/client";


import App from "./App.jsx";

// Actualizamos las rutas para que apunten a la nueva carpeta styles/
import "./styles/variables.css"; 
import "./styles/index.css"; 

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <App />
  </StrictMode>
);