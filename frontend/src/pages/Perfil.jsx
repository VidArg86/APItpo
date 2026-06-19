import { useMemo } from 'react';
import PerfilLayout from '../components/PerfilLayout';

// El token solo guarda email (sub) y roles, no datos personales (nombre, teléfono, etc.),
// así que por ahora mostramos lo que tenemos disponible del lado del cliente.
const decodeToken = (token) => {
    try {
        const payload = token.split('.')[1];
        const json = atob(payload.replace(/-/g, '+').replace(/_/g, '/'));
        return JSON.parse(json);
    } catch {
        return null;
    }
};

const Perfil = () => {
    const datos = useMemo(() => {
        const token = localStorage.getItem('token');
        if (!token) return null;
        return decodeToken(token);
    }, []);

    return (
        <PerfilLayout>
            <h2 className="perfil-title">Mi Perfil</h2>
            <p className="perfil-subtitle">Información de tu cuenta.</p>

            <div className="perfil-card">
                <h3>Datos de la cuenta</h3>
                <div className="perfil-data-row">
                    <span className="perfil-data-label">Email</span>
                    <span className="perfil-data-value">{datos?.sub || '—'}</span>
                </div>
                <div className="perfil-data-row">
                    <span className="perfil-data-label">Rol</span>
                    <span className="perfil-data-value">{datos?.roles || '—'}</span>
                </div>
            </div>

            <div className="perfil-empty-state">
                <span className="perfil-empty-icon">🛠️</span>
                <p className="perfil-empty-text">
                    Próximamente vas a poder editar tu nombre, teléfono y dirección desde acá.
                </p>
            </div>
        </PerfilLayout>
    );
};

export default Perfil;