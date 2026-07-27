package com.lamp.amazonas.services;

import org.springframework.stereotype.Service;

import com.lamp.amazonas.modelo.DetalleVentaEntity;
import com.lamp.amazonas.modelo.ProductoEntity;
import com.lamp.amazonas.modelo.VentasEntity;
import com.lamp.amazonas.repository.ProductoRepository;
import com.lamp.amazonas.repository.VentasRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ProcesarVentaService {
    private final VentasRepository ventaRepo;
    private final ProductoRepository prodRepo;

    @Transactional
    public VentasEntity ProcesarVenta(VentasEntity ventaRequest) {
        ventaRequest.setFecha(java.time.LocalDateTime.now());
        ventaRequest.setEstadoPago("PENDIENTE");

        // CALCULAR TOTALES Y DESCONTAR EL STOCK
        double total = 0.0;
        for (DetalleVentaEntity detalle : ventaRequest.getDetalle()) {
            ProductoEntity p = prodRepo.findById(detalle.getProducto().getId()).orElseThrow();
            p.setStock(p.getStock() - detalle.getCantidad()); // actualiza Stock
            detalle.setPrecioUnitario(p.getPrecio());
            detalle.setSubtotal(p.getPrecio() * detalle.getCantidad());
            detalle.setVenta(ventaRequest);
            total += detalle.getSubtotal();
        }
        ventaRequest.setTotal(total);
        return ventaRepo.save(ventaRequest);
    }

}
