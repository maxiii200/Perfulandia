package com.perfulandia.cl.perfulandia;

import com.perfulandia.cl.perfulandia.model.DetalleVenta;
import com.perfulandia.cl.perfulandia.model.Producto;
import com.perfulandia.cl.perfulandia.model.Usuario;
import com.perfulandia.cl.perfulandia.model.Venta;
import com.perfulandia.cl.perfulandia.repository.ProductoRepository;
import com.perfulandia.cl.perfulandia.repository.UsuarioRepository;
import com.perfulandia.cl.perfulandia.repository.VentaRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(new Locale("es"));
        Random random = new Random();

        // Crear productos
        for (int i = 0; i < 10; i++) {
            Producto producto = new Producto();
            producto.setNombre(faker.commerce().productName());
            producto.setPrecio(faker.number().randomDouble(2, 1000, 100000));
            producto.setStock(faker.number().numberBetween(1, 100));
            producto.setDescripcion(faker.commerce().material() + " - " + faker.commerce().productName());

            productoRepository.save(producto);
        }

        // Crear usuarios
        for (int i = 0; i < 50; i++) {
            Usuario usuario = new Usuario();
            usuario.setRun(faker.idNumber().valid());
            usuario.setNombres(faker.name().firstName());
            usuario.setApellidos(faker.name().lastName());
            usuario.setFechaNacimiento(LocalDate.now());  // mejor usar LocalDate
            usuario.setCorreo(faker.internet().emailAddress());

            usuarioRepository.save(usuario);
        }

        List<Usuario> usuarios = usuarioRepository.findAll();
        List<Producto> productos = productoRepository.findAll();

        // Crear ventas con detalles
        for (int i = 0; i < 20; i++) {
            Venta venta = new Venta();
            venta.setFecha(new Date());


            Usuario usuario = usuarios.get(random.nextInt(usuarios.size()));
            venta.setCliente(usuario);

            int cantidadDetalles = faker.number().numberBetween(1, 3);
            List<DetalleVenta> detalles = new ArrayList<>();
            double total = 0.0;

            for (int j = 0; j < cantidadDetalles; j++) {
                Producto producto = productos.get(random.nextInt(productos.size()));
                int cantidad = faker.number().numberBetween(1, 5);
                double subtotal = producto.getPrecio() * cantidad;
                total += subtotal;

                DetalleVenta detalle = new DetalleVenta();
                detalle.setProducto(producto);
                detalle.setCantidad(cantidad);
                detalle.setPrecioUnitario(producto.getPrecio());

                // Sincronizamos ambos lados para que JPA funcione bien
                detalle.setVenta(venta);
                detalles.add(detalle);
            }

            // Asignar lista de detalles sincronizando relación bidireccional
            venta.setDetalles(detalles);  // en el setter de venta debes asegurarte que detalle.setVenta(this)

            venta.setTotal(total);

            ventaRepository.save(venta);
        }
    }
}
