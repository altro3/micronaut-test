package example.micronaut;

import example.micronaut.entities.Product;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.test.annotation.Sql;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.support.TestPropertyProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.testcontainers.junit.jupiter.Testcontainers;

@Sql(scripts = "classpath:threeproducts.sql")
@Sql(scripts = "classpath:rollbackthreeproducts.sql", phase = Sql.Phase.AFTER_ALL)
@MicronautTest(startApplication = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers(disabledWithoutDocker = true)
class ThreeProductsTest implements TestPropertyProvider {

    @Override
    public @NonNull Map<String, String> getProperties() {
        return PostgreSQL.getProperties();
    }

    @Test
    void thereAreTwoProducts(ProductRepository productRepository) {
        assertEquals(3L, productRepository.count());
        productRepository.save(new Product(999L, "foo", "bar"));
    }
}
