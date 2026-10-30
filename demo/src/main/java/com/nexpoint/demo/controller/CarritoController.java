package com.nexpoint.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.nexpoint.demo.model.ItemCarrito;
import com.nexpoint.demo.model.Producto;
import com.nexpoint.demo.service.ProductoService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CarritoController {

    private final ProductoService productoService;

    @Autowired
    public CarritoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/carrito")
    public String verCarrito(HttpSession session, Model model) {
        List<ItemCarrito> carrito = obtenerCarritoDeSesion(session);

        double total = carrito.stream().mapToDouble(ItemCarrito::getSubtotal).sum();
        int totalItems = carrito.stream().mapToInt(ItemCarrito::getCantidad).sum();

        session.setAttribute("totalCarrito", totalItems);
        model.addAttribute("itemsCarrito", carrito);
        model.addAttribute("totalMonto", total);

        return "Carrito";
    }

    @GetMapping("/agregar-al-carrito")
    public String agregarAlCarrito(
            @RequestParam("idProducto") Integer idProducto,
            @RequestParam(name = "cantidad", defaultValue = "1") Integer cantidad,
            HttpSession session) {

        List<ItemCarrito> carrito = obtenerCarritoDeSesion(session);
        Optional<Producto> prodOpt = productoService.buscarPorId(idProducto);

        if (prodOpt.isPresent()) {
            Producto producto = prodOpt.get();
            Optional<ItemCarrito> existente = carrito.stream()
                    .filter(i -> i.getProducto().getIdProducto().equals(idProducto))
                    .findFirst();

            if (existente.isPresent()) {
                existente.get().setCantidad(existente.get().getCantidad() + cantidad);
            } else {
                carrito.add(new ItemCarrito(producto, cantidad));
            }
        }

        int totalItems = carrito.stream().mapToInt(ItemCarrito::getCantidad).sum();
        session.setAttribute("totalCarrito", totalItems);

        return "redirect:/carrito";
    }

    @GetMapping("/eliminar-del-carrito/{id}")
    public String eliminarDelCarrito(@PathVariable("id") Integer id, HttpSession session) {
        List<ItemCarrito> carrito = obtenerCarritoDeSesion(session);
        carrito.removeIf(item -> item.getProducto().getIdProducto().equals(id));

        int totalItems = carrito.stream().mapToInt(ItemCarrito::getCantidad).sum();
        session.setAttribute("totalCarrito", totalItems);

        return "redirect:/carrito";
    }

    @SuppressWarnings("unchecked")
    private List<ItemCarrito> obtenerCarritoDeSesion(HttpSession session) {
        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carritoCompras");
        if (carrito == null) {
            carrito = new ArrayList<>();
            session.setAttribute("carritoCompras", carrito);
        }
        return carrito;
    }
}
