**"Tests unitaires et tests d'intégration des applications Java et Spring Boot. Développement piloté par les tests (TDD)."**
![image](https://github.com/user-attachments/assets/2eb859e9-755e-490c-8fc8-9a43ed51ee4a)
![image](https://github.com/user-attachments/assets/5b6e8c0c-f8a9-430c-abf2-6f792e115ec4)

![Junit](https://github.com/user-attachments/assets/5eeb1123-7bdf-48b3-8b3a-b635e6f52346)
![image](https://github.com/user-attachments/assets/6a15433d-02c8-4ec7-9ab2-bee4d73bdf05)

Exemple : How to use WireMock , le serveur WireMock est géré automatiquement par JUnit grâce à l’annotation @RegisterExtension :
@RegisterExtension
static WireMockExtension wireMockRule = WireMockExtension.newInstance()
        .options(options().port(8089))
        .build();

Cela signifie que :
---------------------
  >>WireMock démarre automatiquement avant le test.
  >>Il s'arrête après le test.
  >>Tu n’as pas besoin de le lancer en ligne de commande, ni de le faire tourner comme un service indépendant.

        
// Fichier : User.java
public class User {
    private int id;
    private String name;

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

// Fichier : UserService.java
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final WebClient webClient;

    public UserService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<User> getUserById(int id) {
        return webClient.get()
                .uri("/api/users/" + id)
                .retrieve()
                .bodyToMono(User.class);
    }
}

// Fichier : UserServiceTest.java
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class UserServiceTest {

    @RegisterExtension
    static WireMockExtension wireMockRule = WireMockExtension.newInstance()
            .options(options().port(8089))
            .build();

    @Test
    public void testGetUserById() {
        // Stub de la réponse simulée
        wireMockRule.stubFor(get(urlEqualTo("/api/users/1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":1, \"name\":\"Jean Dupont\"}")));

        // Création du WebClient pointant vers le serveur mocké
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8089")
                .build();

        UserService userService = new UserService(webClient);

        // Vérification avec StepVerifier
        StepVerifier.create(userService.getUserById(1))
                .expectNextMatches(user -> user.getName().equals("Jean Dupont"))
                .verifyComplete();
    }
}



