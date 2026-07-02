import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import AdminSidebar from './AdminSidebar';
import '../../styles/admin.css';

const AdminLayout = ({ title, children }) => {
  const navigate = useNavigate();
  const isLoggedIn = useSelector((state) => state.auth.isLoggedIn);

  useEffect(() => {
    if (!isLoggedIn) {
      navigate('/login');
    }
  }, [isLoggedIn, navigate]);

  if (!isLoggedIn) return null;

  return (
    <div className="admin-shell">
      <AdminSidebar />
      <div className="admin-main">
        <header className="admin-topbar">
          <div className="admin-topbar-left">
            <span className="admin-topbar-toggle" aria-hidden="true">☰</span>
            <h1>{title}</h1>
          </div>
          <div className="admin-topbar-right">
            <div className="admin-search">
              <input type="text" placeholder="Buscar pedidos, productos, usuarios..." />
              <span aria-hidden="true">🔍</span>
            </div>
            <button className="admin-bell" title="Notificaciones" aria-label="Notificaciones">
              🔔
            </button>
            <div className="admin-user">
              <div className="admin-avatar" aria-hidden="true">A</div>
              <div>
                <p className="admin-user-name">Admin</p>
                <p className="admin-user-role">Administrador</p>
              </div>
            </div>
          </div>
        </header>
        <main className="admin-content">{children}</main>
      </div>
    </div>
  );
};

export default AdminLayout;
