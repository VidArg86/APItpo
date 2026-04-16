package com.uade.tpo.e_commerce3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.e_commerce3.model.Carrito;
import com.uade.tpo.e_commerce3.model.ItemCarrito;
import com.uade.tpo.e_commerce3.model.Producto;
import com.uade.tpo.e_commerce3.repository.CarritoRepository;
import com.uade.tpo.e_commerce3.repository.ProductoRepository;
import com.uade.tpo.e_commerce3.dto.CarritoSolicitudDTO;
import com.uade.tpo.e_commerce3.dto.ProductoDetalleDTO;
import com.uade.tpo.e_commerce3.exception.*;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoService productoService; // ¡Faltaba inyectar esto!

    // --- BASIC CRUD ---

    public List<Carrito> findAll() {
        return carritoRepository.findAll();
    }

    public Optional<Carrito> findById(Long id) {
        return carritoRepository.findById(id);
    }

    public Carrito create(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    public void delete(Long id) {
        carritoRepository.deleteById(id);
    }

    // --- SHOPPING CART SPECIFIC LOGIC ---

    /**
     * Agrega un producto al carrito usando el flujo de DTOs solicitado.
     */
    public Carrito addProductoToCarrito(Long carritoId, CarritoSolicitudDTO solicitud) {
        
        // 1. Buscamos el carrito
        Carrito carrito = carritoRepository.findById(carritoId)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró el carrito con ID: " + carritoId));

        // 2. Pedimos la info al ProductoService usando el DTO (Flecha del diagrama)
        ProductoDetalleDTO productoDTO = productoService.obtenerInformacionParaCarrito(solicitud);

        // 3. Validar stock usando la info que vino en el DTO
        if (productoDTO.getStockActual() < solicitud.getCantidad()) {
            throw new InsufficientStockException(
                "Solo quedan " + productoDTO.getStockActual() + " unidades de " + productoDTO.getNombre());
        }

        // 4. Buscar si el producto ya está en el carrito para no duplicar filas
        Long pId = Long.parseLong(productoDTO.getId());
        Optional<ItemCarrito> itemExistente = carrito.getProductos().stream()
            .filter(item -> item.getProducto().getId().equals(pId))
            .findFirst();

        if (itemExistente.isPresent()) {
            // Si ya existe, aumentamos la cantidad
            ItemCarrito item = itemExistente.get();
            item.setCantidad(item.getCantidad() + solicitud.getCantidad());
        } else {
            // Si es nuevo, buscamos la entidad para la relación JPA y creamos el vínculo
            Producto producto = productoRepository.findById(pId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            
            ItemCarrito nuevoItem = new ItemCarrito();
            nuevoItem.setCarrito(carrito);
            nuevoItem.setProducto(producto);
            nuevoItem.setCantidad(solicitud.getCantidad());
            carrito.getProductos().add(nuevoItem);
        }

        recalculateTotal(carrito);
        return carritoRepository.save(carrito);
    }

    /**
     * Removes an entire line item from the cart.
     */
    public Carrito removeProductoFromCarrito(Long carritoId, Long productoId) {
        Carrito carrito = carritoRepository.findById(carritoId)
            .orElseThrow(() -> new RuntimeException("Carrito not found"));

        carrito.getProductos().removeIf(item -> item.getProducto().getId().equals(productoId));

        recalculateTotal(carrito);
        return carritoRepository.save(carrito);
    }

    /**
     * Clears all items from the cart.
     */
    public Carrito clearCarrito(Long id) {
        Carrito carrito = carritoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Carrito not found"));

        carrito.getProductos().clear();
        carrito.setPrecioTotal(0.0);

        return carritoRepository.save(carrito);
    }

    // Helper method: Price * Quantity for every item
    private void recalculateTotal(Carrito carrito) {
        double total = carrito.getProductos().stream()
            .mapToDouble(item -> item.getProducto().getPrecio() * item.getCantidad())
            .sum();
        carrito.setPrecioTotal(total);
    }

    public String checkout(Long carritoId) {
        Carrito carrito = carritoRepository.findById(carritoId)
            .orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado"));

        if (carrito.getProductos().isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        // 1. Validar stock
        for (ItemCarrito item : carrito.getProductos()) {
            Producto producto = item.getProducto();
            if (producto.getStock() < item.getCantidad()) {
                throw new InsufficientStockException("Solo quedan " + producto.getStock() +
                    " unidades de " + producto.getNombre());
            }
        }

        // 2. Descontar stock
        for (ItemCarrito item : carrito.getProductos()) {
            Producto producto = item.getProducto();
            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);
        }

        // 3. Finalizar
        double totalFinal = carrito.getPrecioTotal();
        carrito.getProductos().clear();
        carrito.setPrecioTotal(0.0);
        carritoRepository.save(carrito);

        return "Checkout exitoso. Total abonado: $" + totalFinal;
    }
}