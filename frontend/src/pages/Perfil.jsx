import { useMemo } from 'react';
import { useSelector } from 'react-redux';
import PerfilLayout from '../components/PerfilLayout';

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
    const token = useSelector((state) => state.auth.token);
    const datos = useMemo(() => {
        if (!token) return null;
        return decodeToken(token);
    }, [token]);

    return (
        <PerfilLayout>
            <h2 className="perfil-title">Mi Perfil</h2>
            <p className="perfil-subtitle">Informacion de tu cuenta.</p>

            <div className="perfil-card">
                <h3>Datos de la cuenta</h3>
                <div className="perfil-data-row">
                    <span className="perfil-data-label">Email</span>
                    <span className="perfil-data-value">{datos?.sub || '-'}</span>
                </div>
                <div className="perfil-data-row">
                    <span className="perfil-data-label">Rol</span>
                    <span className="perfil-data-value">{datos?.roles || '-'}</span>
                </div>
            </div>

            <div className="perfil-empty-state">
                <span className="perfil-empty-icon">🛠️</span>
                <p className="perfil-empty-text">
                    Proximamente vas a poder editar tu nombre, telefono y direccion desde aca.
                </p>
            </div>
        </PerfilLayout>
    );
};

export default Perfil;
