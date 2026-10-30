package com.nexpoint.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nexpoint.demo.model.Categoria;
import com.nexpoint.demo.model.Producto;
import com.nexpoint.demo.service.ProductoService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AdminController {

    private final ProductoService productoService;

    @Autowired
    public AdminController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/gestion-productos")
    public String gestionProductos(@RequestParam(name = "id", required = false) Integer id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }

        Producto producto = Optional.ofNullable(id)
                .flatMap(productoService::buscarPorId)
                .orElseGet(Producto::new);

        model.addAttribute("producto", producto);
        model.addAttribute("listaProductos", productoService.listarTodos());
        model.addAttribute("categorias", productoService.listarCategorias());
        
        return "gestion-productos";
    }

    @PostMapping("/guardar-producto")
    public String guardarProducto(
            @Valid @ModelAttribute("producto") Producto producto,
            BindingResult bindingResult,
            @RequestParam(value = "idCategoria", required = false) Integer idCategoria,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || idCategoria == null) {
            if (idCategoria == null) {
                model.addAttribute("errorCategoria", "Debe seleccionar una categoría");
            }
            model.addAttribute("listaProductos", productoService.listarTodos());
            model.addAttribute("categorias", productoService.listarCategorias());
            return "gestion-productos";
        }

        Categoria cat = productoService.buscarCategoriaPorId(idCategoria);
        producto.setCategoria(cat);

        if (producto.getIdProducto() != null) {
            Producto productoExistente = productoService.buscarPorId(producto.getIdProducto()).orElse(null);
            if (productoExistente != null) {

                productoExistente.setNombre(producto.getNombre());
                productoExistente.setSubtitulo(producto.getSubtitulo());
                productoExistente.setPrecioVenta(producto.getPrecioVenta());
                productoExistente.setStockActual(producto.getStockActual());
                productoExistente.setImagen(producto.getImagen());
                productoExistente.setDescripcion(producto.getDescripcion());
                productoExistente.setCategoria(cat);
                productoExistente.setNumeroRareza(producto.getNumeroRareza());
                productoExistente.setTipoHpFase(producto.getTipoHpFase());
                productoExistente.setCardText(producto.getCardText());
                productoExistente.setAtaque1(producto.getAtaque1());
                productoExistente.setAtaque2(producto.getAtaque2());
                productoExistente.setDescAtaque1(producto.getDescAtaque1());
                productoExistente.setIlustrador(producto.getIlustrador());
                productoExistente.setCondicion(producto.getCondicion());
                
                productoService.guardarProducto(productoExistente);
            } else {
                productoService.guardarProducto(producto);
            }
        } else {
            productoService.guardarProducto(producto);
        }
        
        redirectAttributes.addFlashAttribute("mensajeExito", "Producto guardado");
        return "redirect:/gestion-productos";
    }

    @GetMapping("/eliminar-producto/{id}")
    public String eliminarProducto(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            productoService.eliminarProducto(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Producto eliminado");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "No se pudo eliminar el producto");
        }
        return "redirect:/gestion-productos";
    }

    @GetMapping("/login")
    public String verLogin(HttpSession session) {
        if (session.getAttribute("usuarioLogueado") != null) {
            return "redirect:/gestion-productos";
        }
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(
            @RequestParam("correo") String correo,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        if ("admin@nexpoint.com".equals(correo) && "123456".equals(password)) {
            session.setAttribute("usuarioLogueado", correo);
            return "redirect:/gestion-productos";
        }

        model.addAttribute("error", "Correo electrónico o contraseña incorrectos.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/productos";
    }
}
