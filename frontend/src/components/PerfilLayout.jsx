import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import PerfilSidebar from './PerfilSidebar';
import '../styles/perfil.css';

const PerfilLayout = ({ children }) => {
    const navigate = useNavigate();

    useEffect(() => {
        if (!localStorage.getItem('token')) {
            navigate('/login');
        }
    }, [navigate]);

    return (
        <div className="perfil-container">
            <PerfilSidebar />
            <main className="perfil-content">{children}</main>
        </div>
    );
};

export default PerfilLayout;