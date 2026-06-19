# TPG - Persistencia de autenticacion con redux-persist

## Consigna

Persistir el token para que no se borren los datos de `authSlice` cuando el usuario presiona F5.

Tambien se debe analizar la solucion elegida, fundamentarla, identificar el ataque asociado y explicar como mitigarlo.

## Problema detectado

Redux guarda el estado en memoria. Por eso, cuando se refresca la pagina con F5, el store se reinicia y se pierden los datos del slice de autenticacion.

Antes de esta implementacion, el usuario podia iniciar sesion y recibir un JWT, pero al refrescar la pagina la aplicacion podia dejar de reconocer visualmente la sesion activa porque `authSlice` volvia a su estado inicial.

## Opcion elegida

Se eligio la opcion intermedia:

```text
Usar redux-persist -> riesgo principal: XSS
```

La solucion implementada persiste solamente los datos necesarios del slice de autenticacion:

- `auth.token`
- `auth.isLoggedIn`

No se persisten estados transitorios como:

- `loading`
- `error`

## Fundamentacion

`redux-persist` permite guardar una parte del estado de Redux en el storage del navegador y recuperarla automaticamente cuando la aplicacion vuelve a cargar.

Esta solucion es adecuada para el TPO porque:

- Mantiene la sesion despues de presionar F5.
- Se integra directamente con Redux Toolkit.
- Evita repartir la logica de autenticacion en muchos componentes.
- No requiere modificar el contrato principal del backend.
- Permite persistir solo la parte necesaria del estado.

La implementacion se hizo sobre `authSlice`, usando una whitelist para guardar unicamente `token` e `isLoggedIn`.

## Desarrollo implementado

Se instalo la dependencia:

```bash
npm install redux-persist
```

En `frontend/src/store/store.js` se configuro:

- `persistReducer`
- `persistStore`
- `storage`
- whitelist de campos persistidos
- middleware para ignorar acciones internas de `redux-persist` en el serializable check

En `frontend/src/App.jsx` se agrego `PersistGate` para esperar la rehidratacion del store antes de renderizar la aplicacion.

En `frontend/src/store/authSlice.js` se centralizo la logica de:

- login
- logout
- carga de credenciales luego del registro

En las pantallas y componentes se reemplazo la lectura manual de sesion por Redux:

- `Login.jsx` despacha `loginUser`
- `Register.jsx` despacha `setCredentials`
- `Navbar.jsx` lee `auth.isLoggedIn`
- `PerfilLayout.jsx` protege rutas usando `auth.isLoggedIn`
- `Perfil.jsx` toma el token desde Redux

## Ajuste adicional para pruebas

Tambien se modifico `backend/src/main/java/com/uade/tpo/backend/config/SecurityConfig.java`.

El motivo fue permitir que el frontend de Vite pueda llamar al backend tanto desde:

```text
http://localhost:5173
```

como desde:

```text
http://127.0.0.1:5173
```

o desde otro puerto local que Vite pueda usar en desarrollo.

Este cambio no es la solucion de persistencia en si, pero ayuda a probar login y registro sin bloqueos de CORS durante el desarrollo.

## Ataque asociado: XSS

La desventaja principal de esta opcion es que el token queda persistido en el navegador y es accesible desde JavaScript.

Si la aplicacion tuviera una vulnerabilidad XSS, un atacante podria inyectar codigo JavaScript malicioso y leer el token guardado. Con ese token podria intentar realizar acciones autenticadas en nombre del usuario mientras el JWT siga siendo valido.

Ejemplo conceptual:

```js
const token = localStorage.getItem('persist:auth');
```

Ese riesgo existe porque `redux-persist`, usando storage web, termina guardando informacion del estado en el navegador.

## Mitigacion de XSS

Para reducir el riesgo de XSS se definieron estas medidas:

- No usar `dangerouslySetInnerHTML` para renderizar contenido ingresado por usuarios.
- Validar entradas del usuario en frontend y backend.
- Sanitizar datos que puedan venir de formularios o de fuentes externas.
- Escapar contenido dinamico antes de mostrarlo en pantalla.
- Mantener React como mecanismo principal de renderizado, ya que escapa texto por defecto.
- Usar una Content Security Policy estricta para limitar scripts externos.
- Mantener dependencias actualizadas.
- Revisar vulnerabilidades con `npm audit`.
- Persistir la menor cantidad posible de informacion.
- No persistir datos sensibles del perfil, contrasenas ni informacion personal innecesaria.
- Usar JWT con expiracion.
- Validar siempre el token del lado del backend.
- Eliminar el token persistido al cerrar sesion.

## Comparacion con otras soluciones

### Solo memoria

Guardar el token solo en memoria reduce la exposicion frente a robo desde storage, pero no cumple con el requisito de mantener la sesion luego de F5.

### localStorage manual

Guardar el token manualmente en `localStorage` es simple, pero reparte la logica de sesion por distintos componentes. Eso aumenta la posibilidad de inconsistencias.

### redux-persist

Permite centralizar la persistencia del estado de autenticacion dentro de Redux. Es la opcion elegida porque cumple la consigna con una integracion simple y controlada.

### Cookies HTTP-only

Seria una alternativa mas robusta contra robo por XSS, porque JavaScript no puede leer una cookie HTTP-only. Sin embargo, requiere cambiar el backend para emitir cookies seguras, configurar CORS con credenciales y agregar proteccion contra CSRF.

Para este TPO se eligio `redux-persist` porque era la opcion indicada por la consigna y porque el alcance era persistir `authSlice` en el frontend.

## Prueba realizada

Se verifico que el frontend compile correctamente con:

```bash
npm run build
```

Flujo funcional esperado:

1. Levantar backend en `localhost:8080`.
2. Levantar frontend con `npm run dev`.
3. Registrarse o iniciar sesion.
4. Verificar que aparece el usuario como autenticado.
5. Presionar F5.
6. Confirmar que la sesion se mantiene porque `authSlice` se rehidrata desde `redux-persist`.
7. Cerrar sesion.
8. Confirmar que el token se elimina y el usuario vuelve al estado no autenticado.

## Archivos modificados

- `frontend/package.json`: agrega `redux-persist`.
- `frontend/package-lock.json`: actualiza dependencias.
- `frontend/src/store/store.js`: configura `persistReducer`, `persistStore` y persistencia de `auth`.
- `frontend/src/store/authSlice.js`: centraliza login, logout y carga de credenciales.
- `frontend/src/App.jsx`: agrega `PersistGate`.
- `frontend/src/services/api.js`: permite respuestas de texto plano, como el JWT.
- `frontend/src/pages/Login.jsx`: usa `loginUser`.
- `frontend/src/pages/Register.jsx`: guarda el token con `setCredentials`.
- `frontend/src/components/Navbar.jsx`: lee sesion desde Redux y despacha `logout`.
- `frontend/src/components/PerfilSidebar.jsx`: despacha `logout`.
- `frontend/src/components/PerfilLayout.jsx`: protege rutas usando `auth.isLoggedIn`.
- `frontend/src/components/ProductCard.jsx`: usa `auth.isLoggedIn` para favoritos.
- `frontend/src/pages/Perfil.jsx`: decodifica el token persistido desde Redux.
- `backend/src/main/java/com/uade/tpo/backend/config/SecurityConfig.java`: ajusta CORS para pruebas locales.

## Conclusion

La solucion implementada cumple con la consigna porque el estado de autenticacion ya no se pierde al presionar F5. El token se persiste de forma controlada con `redux-persist`, se rehidrata al cargar la aplicacion y se elimina al cerrar sesion.

El riesgo principal de esta opcion es XSS, por lo que se documentaron medidas de mitigacion y se limito la persistencia a la menor cantidad de datos posible.
