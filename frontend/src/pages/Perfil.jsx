import React from 'react';
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import ProductCard from '../components/ProductCard';
import faheartbl from '../assets/heart-regular-black.svg'
import gear from '../assets/gear.svg';

const Perfil = () => {
    // Obtenemos la lista de favoritos desde el store de Redux
    const favoritos = useSelector((state) => state.favoritos.items);

    return (
        <div className="perfil-container" style={{ display: 'flex', gap: '2.5rem', padding: '2rem', maxWidth: '1800px', margin: '0 ' }}>

            {/* BARRA LATERAL (Sidebar) */}
            <aside className="perfil-sidebar" style={{ width: '280px', flexShrink: 0, borderRight: '1px solid #e0e0e0', paddingRight: '1.5rem' }}>
                <div style={{ marginBottom: '2rem', textAlign: 'center' }}>


                </div>

                <nav>
                    <ul style={{ listStyleType: 'none', padding: 0, margin: 0 }}>
                        <li style={{ marginBottom: '0.75rem', textAlign:'left' }}>
                            <span style={{ display: 'block', padding: '0.5rem 1rem', color: '#000', cursor: 'pointer' }}>
                            Mi perfil
                            </span>
                            <span style={{ display: 'block', padding: '0.5rem 1rem', color: '#000', cursor: 'pointer' }}>
                                Mis pedidos
                            </span>
                            <span style={{ display: 'block', padding: '0.5rem 1rem', color: '#000', cursor: 'pointer' }}>
                                Direcciones
                            </span>
                            <span style={{ display: 'block', padding: '0.5rem 1rem', color: '#000', cursor: 'pointer' }}>
                                Metodos de pago
                            </span>
                            <Link to="/perfil" style={{ display: 'block', padding: '0.5rem 1rem', backgroundColor: '#f5f5f5', borderRadius: '6px', fontWeight: 'bold', textDecoration: 'none', color: '#007bff' }}>
                                <img src={faheartbl} alt="Favorito" style={{ width: '15px', height: '15px'}}/> Mis Favoritos
                            </Link>
                            <span style={{ display: 'block', padding: '0.5rem 1rem', color: '#000', cursor: 'pointer' }}>
                                <img src={gear} style={{width:'25px', height:'25px'}}/> Configuración
                            </span>
                            <span style={{ display: 'block', padding: '0.5rem 1rem', color: '#000', cursor: 'pointer' }}>
                                Cerrar Sesión
                            </span>
                        </li>
                        {/* Espacio reservado para futuras secciones del perfil */}

                        <li style={{ marginBottom: '0.75rem' }}>


                        </li>
                    </ul>
                </nav>

            </aside>

            {/* CONTENIDO PRINCIPAL (Catálogo de Favoritos) */}
            <main className="perfil-content" style={{ flexGrow: 1 }}>
                <h2 style={{ marginBottom: '0.5rem', color: '#222' , textAlign:"left"}}>Mis Favoritos</h2>
                <p style={{ color: '#666', marginBottom: '2rem' , textAlign:'left'}}> Guardá tus productos preferidos para volver a pedirlos cuando quieras.</p>

                {favoritos.length === 0 ? (
                    <div style={{ textAlign: 'center', padding: '4rem 2rem', border: '2px dashed #e0e0e0', borderRadius: '12px', marginTop: '1rem' }}>
                        <span style={{ fontSize: '3rem', display: 'block', marginBottom: '1rem' }}>🤍</span>
                        <p style={{ color: '#888', fontSize: '16px', margin: 0 }}>Tu lista de favoritos está vacía en este momento.</p>
                        <Link to="/productos" style={{
                            display: 'inline-block',
                            backgroundColor: '#007bff',
                            color: 'white',
                            padding: '0.75rem 1.5rem',
                            textDecoration: 'none',
                            borderRadius: '6px',
                            marginTop: '1.5rem',
                            fontWeight: '500',
                            boxShadow: '0 2px 4px rgba(0,123,255,0.2)'
                        }}>
                            Explorar Catálogo
                        </Link>
                    </div>
                ) : (
                    /* Reutilizamos los estilos de cuadrícula que uses en tu catálogo general */
                    <div className="products-grid" style={{
                        display: 'grid',
                        gridTemplateColumns: 'repeat(auto-fill, minmax(240px, 1fr))',
                        gap: '1.5rem'
                    }}>
                        {favoritos.map((producto) => (
                            <ProductCard key={producto.id} product={producto} />
                        ))}
                    </div>
                )}
            </main>
        </div>
    );
};

export default Perfil;