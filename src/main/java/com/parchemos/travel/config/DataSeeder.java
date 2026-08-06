package com.parchemos.travel.config;

import com.parchemos.travel.model.Destino;
import com.parchemos.travel.model.Paquete;
import com.parchemos.travel.repository.DestinoRepository;
import com.parchemos.travel.repository.PaqueteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataSeeder {

    private record SeedPaquete(
            String nombre,
            String region,
            String descripcion,
            int precio,
            String duracionTexto,
            int duracionDias,
            String categoria,
            String tipoViaje,
            int cupos,
            String imagenUrl
    ) {
    }

    private static final SeedPaquete[] PAQUETES_SEED = {
            new SeedPaquete("Caño Cristales", "La Macarena, Meta",
                    "Conocido como 'el río de los cinco colores', sus aguas cristalinas se tiñen de rojo, amarillo, verde, azul y negro gracias a una planta acuática endémica. Un fenómeno natural único en el mundo.",
                    850000, "3 días / 2 noches", 3, "Naturaleza", "Parche individual", 15,
                    "https://media.istockphoto.com/id/1201458044/photo/colombia-cano-cristales-national-park-serrania-de-la-macarena.jpg?s=1024x1024&w=is&k=20&c=b6Ww7Dw7hUYNIwxE6aDn67kWv2Sy2_9jLCbyRXInVyk="),
            new SeedPaquete("Ciudad Amurallada", "Cartagena, Bolívar",
                    "Centro histórico declarado Patrimonio de la Humanidad por la UNESCO. Calles coloniales, balcones floridos, murallas frente al mar Caribe y una vibrante vida cultural hacen de este destino una joya imperdible.",
                    420000, "2 días / 1 noche", 2, "Cultural", "Parchemos con amigos", 30,
                    "https://mlqfmr3rpryd.i.optimole.com/cb:0cAX.b2f4/w:auto/h:auto/q:mauto/g:sm/f:best/https://cartagena-tours.co/wp-content/uploads/2023/12/49806996192_ec0e5e29b1_b.jpg"),
            new SeedPaquete("Valle de Cocora", "Salento, Quindío",
                    "Hogar de la palma de cera, árbol nacional de Colombia y la palmera más alta del mundo. Rodeado de montañas verdes y niebla, es ideal para senderismo en pleno corazón del Eje Cafetero.",
                    280000, "1 día (full day)", 1, "Montaña", "A viajar en familia", 25,
                    "https://images.pexels.com/photos/13829249/pexels-photo-13829249.jpeg"),
            new SeedPaquete("Parque Tayrona", "Santa Marta, Magdalena",
                    "Playas de arena blanca enmarcadas por la selva tropical y la Sierra Nevada. Caminatas ecológicas, aguas turquesas y biodiversidad excepcional en uno de los parques naturales más visitados del país.",
                    390000, "3 días / 2 noches", 3, "Playa", "Parchemos con amigos", 20,
                    "https://phantom-elmundo.unidadeditorial.es/38a56cccc9ad25c7bf05c58e97acf27c/crop/0x0/2873x1915/resize/646/f/webp/assets/multimedia/imagenes/2024/04/08/17125820913517.jpg"),
            new SeedPaquete("Guatapé", "Guatapé, Antioquia",
                    "Pueblo de casas coloridas con zócalos pintados a mano, junto al embalse de Guatapé. Subida a la Piedra del Peñol con vistas panorámicas y paseos en lancha por el lago.",
                    310000, "2 días / 1 noche", 2, "Rural", "A viajar en familia", 22,
                    "https://images.pexels.com/photos/4016193/pexels-photo-4016193.jpeg"),
            new SeedPaquete("Desierto de la Tatacoa", "Villavieja, Huila",
                    "La segunda zona árida más extensa de Colombia. Un paisaje fascinante de formaciones de tierra rojiza y gris, ideal para el senderismo y la observación astronómica gracias a su cielo despejado.",
                    340000, "2 días / 1 noche", 2, "Naturaleza", "Parche individual", 18,
                    "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/09/34/1c/b2/desierto-de-la-tatacoa.jpg?w=700&h=400&s=1"),
            new SeedPaquete("Santuario de Las Lajas", "Ipiales, Nariño",
                    "Impresionante iglesia de estilo neogótico construida sobre el cañón del río Guáitara. Considerada una de las maravillas arquitectónicas del mundo por su imponente estructura y entorno natural.",
                    450000, "2 días / 1 noche", 2, "Cultural", "A viajar en familia", 25,
                    "https://images.pexels.com/photos/13783816/pexels-photo-13783816.jpeg"),
            new SeedPaquete("Catedral de Sal", "Zipaquirá, Cundinamarca",
                    "Una joya arquitectónica subterránea tallada completamente en roca de sal a 180 metros bajo tierra. Un lugar único que combina ingeniería, fe y arte en una antigua mina minera.",
                    150000, "1 día (full day)", 1, "Cultural", "Parche individual", 40,
                    "https://images.pexels.com/photos/36209536/pexels-photo-36209536.jpeg"),
            new SeedPaquete("San Andrés Islas", "San Andrés, Archipiélago",
                    "Famosa por su 'mar de los siete colores' que degrada desde el azul marino hasta el verde turquesa. Destino caribeño perfecto para el buceo, snorkel, playas de arena coralina y descanso total.",
                    950000, "4 días / 3 noches", 4, "Playa", "Parchemos con amigos", 12,
                    "https://images.pexels.com/photos/3417784/pexels-photo-3417784.jpeg"),
            new SeedPaquete("Nevado del Ruiz", "Manizales, Caldas",
                    "Aventura de alta montaña en el Parque Nacional Natural Los Nevados. Un recorrido entre paisajes de páramo, vegetación de frailejones y vistas imponentes a las cumbres blancas de la cordillera central.",
                    320000, "1 día (full day)", 1, "Montaña", "Parchemos con amigos", 15,
                    "https://images.pexels.com/photos/17370933/pexels-photo-17370933.jpeg"),
            new SeedPaquete("Monserrate y La Candelaria", "Bogotá, Cundinamarca",
                    "Sube en teleférico o funicular hasta el cerro de Monserrate para ver la capital a tus pies, y recorre después las calles empedradas y coloridas de La Candelaria, el corazón histórico de Bogotá.",
                    200000, "1 día (full day)", 1, "Cultural", "Parche individual", 35,
                    "https://images.pexels.com/photos/19676274/pexels-photo-19676274.jpeg"),
            new SeedPaquete("Comuna 13", "Medellín, Antioquia",
                    "De barrio marcado por el conflicto a símbolo de transformación social a través del arte. Sus escaleras eléctricas, grafitis gigantes y hip hop callejero cuentan la historia de resiliencia de Medellín.",
                    180000, "1 día (full day)", 1, "Cultural", "Parchemos con amigos", 28,
                    "https://images.pexels.com/photos/37278658/pexels-photo-37278658.jpeg"),
            new SeedPaquete("Villa de Leyva", "Villa de Leyva, Boyacá",
                    "Uno de los pueblos coloniales mejor conservados de Colombia, con la plaza principal empedrada más grande del país. Fósiles marinos, viñedos y paisajes de montaña a pocas horas de Bogotá.",
                    260000, "2 días / 1 noche", 2, "Rural", "A viajar en familia", 24,
                    "https://images.pexels.com/photos/17413264/pexels-photo-17413264.jpeg"),
            new SeedPaquete("Barichara", "Barichara, Santander",
                    "Considerado uno de los pueblos más bonitos de Colombia. Calles de piedra, fachadas blancas y techos de teja, rodeado del cañón del río Suárez. Ideal para caminar el Camino Real hasta Guane.",
                    300000, "2 días / 1 noche", 2, "Rural", "Parche individual", 20,
                    "https://images.pexels.com/photos/31649387/pexels-photo-31649387.jpeg"),
            new SeedPaquete("Santa Cruz de Mompox", "Mompox, Bolívar",
                    "Ciudad colonial a orillas del río Magdalena, Patrimonio de la Humanidad, famosa por su filigrana de oro y plata, sus balcones republicanos y un ambiente detenido en el tiempo.",
                    380000, "3 días / 2 noches", 3, "Cultural", "A viajar en familia", 18,
                    "https://www.tomplanmytrip.com/wp-content/uploads/2024/06/Main-Square-Santa-Cruz-de-Mompox-East-Caribbean-Coast-1.jpg"),
            new SeedPaquete("Providencia", "Providencia, Archipiélago de San Andrés",
                    "Hermana pequeña de San Andrés, más tranquila y menos turística. Snorkel en la Barrera de Coral, Old Providence, y el famoso Hoyo Soplador. Naturaleza caribeña casi intacta.",
                    1100000, "4 días / 3 noches", 4, "Playa", "Parchemos con amigos", 14,
                    "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/0b/1d/a3/c0/el-lugar-es-hermoso-100.jpg?w=1400&h=-1&s=1"),
            new SeedPaquete("Cabo de la Vela", "La Guajira",
                    "Territorio ancestral wayúu entre el desierto y el mar Caribe. Kitesurf, atardeceres en el Pilón de Azúcar y una experiencia cultural profunda en una de las regiones más auténticas de Colombia.",
                    420000, "3 días / 2 noches", 3, "Aventura", "Parche individual", 16,
                    "https://images.pexels.com/photos/13209625/pexels-photo-13209625.jpeg"),
            new SeedPaquete("Ciudad Perdida", "Sierra Nevada de Santa Marta, Magdalena",
                    "Trekking de varios días por la selva hasta las ruinas de Teyuna, una ciudad indígena precolombina anterior a Machu Picchu. Ríos, cascadas y comunidades ancestrales en el camino.",
                    980000, "4 días / 3 noches", 4, "Aventura", "Parchemos con amigos", 12,
                    "https://turcol.co/wp-content/uploads/2020/12/Lost-city-trek-2.png"),
            new SeedPaquete("Popayán", "Popayán, Cauca",
                    "La 'Ciudad Blanca', con uno de los centros históricos coloniales mejor conservados de Latinoamérica. Cuna de la gastronomía payanesa y escenario de una de las procesiones de Semana Santa más importantes del mundo.",
                    240000, "2 días / 1 noche", 2, "Cultural", "A viajar en familia", 26,
                    "https://images.pexels.com/photos/13200191/pexels-photo-13200191.jpeg"),
            new SeedPaquete("Leticia y el Amazonas", "Leticia, Amazonas",
                    "Puerta de entrada a la selva amazónica colombiana. Navega por el río Amazonas, visita comunidades indígenas y explora una de las reservas de biodiversidad más grandes del planeta.",
                    1250000, "4 días / 3 noches", 4, "Naturaleza", "Parche individual", 10,
                    "https://images.pexels.com/photos/17025853/pexels-photo-17025853.jpeg"),
    };

    @Bean
    CommandLineRunner seedPaquetes(DestinoRepository destinoRepository, PaqueteRepository paqueteRepository) {
        return args -> {
            if (paqueteRepository.count() > 0) {
                return;
            }

            System.out.println("Cargando paquetes turísticos iniciales...");
            for (SeedPaquete seed : PAQUETES_SEED) {
                String[] partesRegion = seed.region().split(",", 2);
                String ciudad = partesRegion[0].trim();
                String departamento = partesRegion.length > 1 ? partesRegion[1].trim() : "Colombia";

                Destino destino = new Destino();
                destino.setNombre(seed.nombre());
                destino.setCiudad(ciudad);
                destino.setPais(departamento);
                destino.setDescripcion(seed.descripcion());
                destino = destinoRepository.save(destino);

                Paquete paquete = new Paquete();
                paquete.setDestino(destino);
                paquete.setNombre(seed.nombre());
                paquete.setDescripcion(seed.descripcion());
                paquete.setPrecio(BigDecimal.valueOf(seed.precio()));
                paquete.setDuracionDias(seed.duracionDias());
                paquete.setDuracionTexto(seed.duracionTexto());
                paquete.setCupoMaximo(seed.cupos());
                paquete.setImagenUrl(seed.imagenUrl());
                paquete.setCategoria(seed.categoria());
                paquete.setTipoViaje(seed.tipoViaje());
                paquete.setServicios("Hotel,Transporte,Alimentación");
                paqueteRepository.save(paquete);
            }
            System.out.println("Seed completado: " + PAQUETES_SEED.length + " paquetes cargados.");
        };
    }
}
