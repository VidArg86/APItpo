import { NavLink, useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { logout } from '../store/authSlice';
import heartIcon from '../assets/heart-regular-black.svg';
import gearIcon from '../assets/gear.svg';

const navLinkClass = ({ isActive }) =>
    `perfil-nav-link${isActive ? ' active' : ''}`;

const PerfilSidebar = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleLogout = () => {
        dispatch(logout());
        navigate('/');
    };

    return (
        <aside className="perfil-sidebar">
            <nav>
                <ul className="perfil-nav-list">
                    <li>
                        <NavLink to="/perfil" end className={navLinkClass}>
                            Mi perfil
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/mis-pedidos" className={navLinkClass}>
                            Mis pedidos
                        </NavLink>
                    </li>
                    <li>
                        <span className="perfil-nav-link perfil-nav-disabled" title="Proximamente">
                            Direcciones
                        </span>
                    </li>
                    <li>
                        <span className="perfil-nav-link perfil-nav-disabled" title="Proximamente">
                            Metodos de pago
                        </span>
                    </li>
                    <li>
                        <NavLink to="/favoritos" className={navLinkClass}>
                            <img src={heartIcon} alt="" className="perfil-nav-icon" />
                            Mis Favoritos
                        </NavLink>
                    </li>
                    <li>
                        <span className="perfil-nav-link perfil-nav-disabled" title="Proximamente">
                            <img src={gearIcon} alt="" className="perfil-nav-icon" />
                            Configuracion
                        </span>
                    </li>
                    <li>
                        <button className="perfil-nav-link perfil-nav-logout" onClick={handleLogout}>
                            Cerrar Sesion
                        </button>
                    </li>
                </ul>
            </nav>
        </aside>
    );
};

export default PerfilSidebar;
