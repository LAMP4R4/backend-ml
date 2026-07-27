package com.lamp.amazonas.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lamp.amazonas.modelo.ClienteEntity;
import com.lamp.amazonas.modelo.DetalleVentaEntity;
import com.lamp.amazonas.modelo.ProductoEntity;
import com.lamp.amazonas.modelo.VentasEntity;
import com.lamp.amazonas.repository.ClienteRepository;
import com.lamp.amazonas.repository.ProductoRepository;
import com.lamp.amazonas.repository.VentasRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VentasService {

    private final ProductoRepository productoRepository;
    private final VentasRepository ventasRepository;
    private final ClienteRepository clienteRepository;

    // crea una venta nueva: valida cliente y stock, descuenta inventario y calcula
    // el total
    @Transactional
    public VentasEntity procesarVenta(VentasEntity ventaRequest, String email) {
        ClienteEntity cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cliente no registrado: " + email));
        ventaRequest.setCliente(cliente);
        ventaRequest.setFecha(LocalDateTime.now());
        ventaRequest.setEstadoPago("PENDIENTE");
        double total = 0.0;
        for (DetalleVentaEntity detalle : ventaRequest.getDetalle()) {
            ProductoEntity producto = productoRepository.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no existe"));
            if (producto.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente de producto");
            }

            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoRepository.save(producto);

            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(producto.getPrecio() * detalle.getCantidad());
            detalle.setVenta(ventaRequest);

            total += detalle.getSubtotal();
        }

        ventaRequest.setTotal(total);
        return ventasRepository.save(ventaRequest);
    }

    // marca una venta existente como pagada
    @Transactional
    public VentasEntity confirmarPago(Long idVenta) {
        VentasEntity venta = ventasRepository.findById(idVenta)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID" + idVenta));
        venta.setEstadoPago("PAGADO");
        return ventasRepository.save(venta);
    }

    // lista todas las ventas registradas
    @Transactional(readOnly = true)
    public List<VentasEntity> obtenerTodo() {
        return ventasRepository.findAll();
    }

    // busca una venta por id, lanza excepcion si no existe
    @Transactional(readOnly = true)
    public VentasEntity obtenerPorId(Long id) {
        return ventasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + id));
    }

    // guarda una venta ya armada, vinculando cada detalle con la venta
    @Transactional
    public VentasEntity guardar(VentasEntity venta) {
        if (venta.getDetalle() != null) {
            venta.getDetalle().forEach(d -> d.setVenta(venta));
        }
        return ventasRepository.save(venta);
    }

    // actualiza los datos de una venta existente, sin tocar id ni detalle
    @Transactional
    public VentasEntity actualizar(Long id, VentasEntity detalle) {
        VentasEntity existente = obtenerPorId(id);
        BeanUtils.copyProperties(detalle, existente, "id", "detalle");
        return ventasRepository.save(existente);
    }

    // elimina una venta por id, lanza excepcion si no existe
    @Transactional
    public void eliminar(Long id) {
        if (!ventasRepository.existsById(id)) {
            throw new RuntimeException("Venta no encontrada con id: " + id);
        }
        ventasRepository.deleteById(id);
    }

    // lista las ventas de un cliente segun su email
    @Transactional
    public List<VentasEntity> obtenerVentasPorCliente(String email) {
        return ventasRepository.findByClienteEmail(email);
    }

}
