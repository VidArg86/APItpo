This was tested using REST client by Huachao Mao

Using the following Scripts


--------------------------------------------------------

Productos

### 1. GET ALL PRODUCTOS
GET http://localhost:8080/api/productos

###

### 2. CREATE A NEW PRODUCTO (POST)
POST http://localhost:8080/api/productos
Content-Type: application/json

{
    "nombre": "Monitor LG",
    "descripcion": "70 pulgadas, PLASMA, 144hz"
}

###

### 3. UPDATE AN EXISTING PRODUCTO (PUT)
# Replace the '1' at the end with the actual ID from your H2 database
PUT http://localhost:8080/api/productos/1
Content-Type: application/json

{
    "nombre": "Monitor LG UltraWide",
    "descripcion": "Actualizado: 34 pulgadas, curvo"
}

###

### 4. DELETE A PRODUCTO (DELETE)
# Replace the '1' with the ID you want to destroy
DELETE http://localhost:8080/api/productos/1




---------------------------------------

Usuarios

### GET ALL USUARIOS
GET http://localhost:8080/api/usuarios

### CREATE USUARIO
POST http://localhost:8080/api/usuarios
Content-Type: application/json

{
    "nombre": "Juan Perez",
    "DNI": 12345678,
    "numeroTelefonico": "1122334455"
}

### UPDATE USUARIO
PUT http://localhost:8080/api/usuarios/1
Content-Type: application/json

{
    "nombre": "Juan Perez Actualizado",
    "DNI": 12345678,
    "numeroTelefonico": "9988776655"
}

### DELETE USUARIO
DELETE http://localhost:8080/api/usuarios/1