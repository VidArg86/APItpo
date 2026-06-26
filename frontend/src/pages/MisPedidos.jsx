import { Link } from 'react-router-dom';
import PerfilLayout from '../components/PerfilLayout';

const MisPedidos = () => {
    // TODO: cuando exista un endpoint de pedidos en el backend, traer acá el historial real
    // (por ejemplo GET /api/pedidos/usuario/{id}) y reemplazar el estado vacío por el listado.
    const pedidos = [];

    return (
        <PerfilLayout>
            <h2 className="perfil-title">Mis Pedidos</h2>
            <p className="perfil-subtitle">
                Acá vas a poder ver el estado y el historial de tus pedidos.
            </p>

            {pedidos.length === 0 ? (
                <div className="perfil-empty-state">
                    <span className="perfil-empty-icon">🧾</span>
                    <p className="perfil-empty-text">Todavía no realizaste ningún pedido.</p>
                    <Link to="/productos" className="perfil-empty-cta">
                        Explorar Catálogo
                    </Link>
                </div>
            ) : (
                <div className="perfil-pedidos-list">
                    {/* Render de pedidos cuando haya datos reales */}
                </div>
            )}
        </PerfilLayout>
    );
};

export default MisPedidos;