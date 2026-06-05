import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { Provider } from 'react-redux' // <-- Importamos Provider
import { store } from './store/store.js' // <-- Importamos el store
import './index.css'
import App from './App.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <Provider store={store}> {/* <-- Envolvemos App */}
      <App />
    </Provider>
  </StrictMode>,
)