package com.nexpoint.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.nexpoint.demo.model.Producto;
import com.nexpoint.demo.model.TcgApiResponse;
import com.nexpoint.demo.service.ProductoService;

@Controller
public class ProductosController {

    private final ProductoService productoService;
    private final RestTemplate restTemplate;

    @Autowired
    public ProductosController(ProductoService productoService) {
        this.productoService = productoService;
        this.restTemplate = new RestTemplate();
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("productosDestacados", productoService.listarTodos());
        return "Portada";
    }

    @GetMapping("/productos")
    public String listarProductos(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "buscar", required = false) String buscar,
            @RequestParam(name = "categoria", required = false) String categoria,
            Model model) {

        int pageSize = 8;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Producto> productosPage;

        List<String> categoriasAccesorios = List.of("booster", "micas", "carpetas", "deckboxes", "deck", "sleeves", "binders");

        if (buscar != null && !buscar.isBlank()) {
            productosPage = productoService.buscarPorNombrePaginado(buscar, pageable);
            model.addAttribute("buscar", buscar);
        } else if (categoria != null && !categoria.equals("todas")) {
            try {
                // Filtro directo y dinámico por ID numérico en la base de datos
                Integer idCat = Integer.parseInt(categoria);
                productosPage = productoService.buscarPorCategoriaIdPaginado(idCat, pageable);
                model.addAttribute("categoriaActual", categoria);
            } catch (NumberFormatException e) {
                // Compatibilidad si llega un parámetro textual por URL (ej. micas, booster, magic)
                List<Producto> todosLosProductos = productoService.listarTodos();
                List<Producto> filtrados = todosLosProductos.stream()
                    .filter(p -> p.getCategoria() != null && 
                                 p.getCategoria().getNombre() != null && 
                                 p.getCategoria().getNombre().toLowerCase().contains(categoria.toLowerCase()))
                    .toList();
                
                int start = (int) pageable.getOffset();
                int end = Math.min((start + pageable.getPageSize()), filtrados.size());
                List<Producto> paginaSublista = (start <= end) ? filtrados.subList(start, end) : List.of();

                productosPage = new org.springframework.data.domain.PageImpl<>(
                    paginaSublista,
                    pageable,
                    filtrados.size()
                );
                model.addAttribute("categoriaActual", categoria.toLowerCase());
            }
        } else {
            // "Todas las cartas": Excluimos accesorios para mostrar solo cartas sueltas/singles
            List<Producto> todosLosProductos = productoService.listarTodos();
            List<Producto> soloCartas = todosLosProductos.stream()
                .filter(p -> {
                    if (p.getCategoria() == null || p.getCategoria().getNombre() == null) {
                        return true;
                    }
                    String nombreCat = p.getCategoria().getNombre().toLowerCase();
                    return categoriasAccesorios.stream().noneMatch(nombreCat::contains);
                })
                .toList();

            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), soloCartas.size());
            List<Producto> paginaSublista = (start <= end) ? soloCartas.subList(start, end) : List.of();

            productosPage = new org.springframework.data.domain.PageImpl<>(
                paginaSublista,
                pageable,
                soloCartas.size()
            );
            model.addAttribute("categoriaActual", "todas");
        }

        model.addAttribute("productos", productosPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productosPage.getTotalPages());
        model.addAttribute("buscar", buscar);
        model.addAttribute("listaCategorias", productoService.listarCategorias());

        return "Productos";
    }
    
    @GetMapping("/producto/{id}")
    public String verDetalleProducto(@PathVariable("id") Integer id, Model model) {
        Producto producto = productoService.buscarPorId(id)
                .orElseGet(this::crearProductoPorDefecto);

        MetricasMercado metricas = obtenerMetricasMercado(producto);

        model.addAttribute("producto", producto);
        model.addAttribute("precioMercadoReal", metricas.precioMercado());
        model.addAttribute("precioBajo", metricas.precioBajo());
        model.addAttribute("precioAlto", metricas.precioAlto());
        model.addAttribute("historicoPrecios", metricas.historico());

        return "detalle-producto";
    }

    private MetricasMercado obtenerMetricasMercado(Producto producto) {
        Double mercado = 25.00;
        Double bajo = 20.00;
        Double alto = 35.00;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            String cardApiId = "sv3pt5-105";
            if (producto.getImagen() != null && producto.getImagen().contains("images.pokemontcg.io")) {
                String clean = producto.getImagen().replace("https://images.pokemontcg.io/", "");
                String[] parts = clean.split("/");
                if (parts.length >= 2) {
                    String set = parts[0];
                    String num = parts[1].replace("_hires.png", "").replace(".png", "");
                    cardApiId = set + "-" + num;
                }
            }

            String apiEndpoint = "https://api.pokemontcg.io/v2/cards/" + cardApiId;
            ResponseEntity<TcgApiResponse> response = restTemplate.exchange(
                    apiEndpoint, HttpMethod.GET, entity, TcgApiResponse.class);

            if (response.getBody() != null && response.getBody().getData() != null && response.getBody().getData().getTcgplayer() != null) {
                var prices = response.getBody().getData().getTcgplayer().getPrices();
                var detail = (prices.getHolofoil() != null) ? prices.getHolofoil() : prices.getNormal();

                if (detail != null && detail.getMarket() != null) {
                    mercado = detail.getMarket();
                    bajo = (detail.getLow() != null) ? detail.getLow() : mercado * 0.85;
                    alto = (detail.getHigh() != null) ? detail.getHigh() : mercado * 1.25;
                }
            }
        } catch (Exception e) {
            System.err.println("API Externa TCGPlayer no disponible (" + e.getMessage() + "). Usando datos de respaldo.");
        }

        List<Double> historico = List.of(
            Math.round(mercado * 1.12 * 100.0) / 100.0,
            Math.round(mercado * 1.08 * 100.0) / 100.0,
            Math.round(mercado * 1.05 * 100.0) / 100.0,
            Math.round(mercado * 1.02 * 100.0) / 100.0,
            Math.round(mercado * 0.97 * 100.0) / 100.0,
            Math.round(mercado * 0.99 * 100.0) / 100.0,
            Math.round(mercado * 0.95 * 100.0) / 100.0,
            Math.round(mercado * 100.0) / 100.0
        );

        return new MetricasMercado(
            Math.round(mercado * 100.0) / 100.0, 
            Math.round(bajo * 100.0) / 100.0, 
            Math.round(alto * 100.0) / 100.0, 
            historico
        );
    }

    private Producto crearProductoPorDefecto() {
        Producto p = new Producto();
        p.setIdProducto(2);
        p.setNombre("Charizard VMAX - SV107/SV122 (Shiny)");
        p.setDescripcion("Colección Shining Fates: Shiny Vault (Ultra Rara Secret)");
        p.setPrecioVenta(180.00);
        p.setStockActual(3);
        p.setImagen("https://images.pokemontcg.io/swsh45/SV107_hires.png");
        return p;
    }

    private record MetricasMercado(Double precioMercado, Double precioBajo, Double precioAlto, List<Double> historico) {}
}