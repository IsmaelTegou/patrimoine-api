# Heritage Management Platform - Phase 2 Guide

## 📋 Vue d'ensemble

Vous avez reçu une structure Spring Boot 3 complète avec architecture hexagonale (3 couches). Ce guide vous aidera à démarrer et à comprendre la structure.

---

## 🚀 Démarrage Rapide

### 1. **Prérequis**

- Java 17+
- Maven 3.9+
- Docker & Docker Compose
- IDE (IntelliJ IDEA ou Eclipse)

### 2. **Clone et Configuration**

```bash
# Clone le projet
git clone <repository-url>
cd patrimoine

# Vérifier Java
java -version

# Vérifier Maven
mvn -version
```

### 3. **Lancer l'infrastructure (PostgreSQL + MinIO)**

```bash
# Development environment
docker-compose -f docker-compose.dev.yml up -d

# Vérifier les services
docker-compose -f docker-compose.dev.yml ps

# Accéder à MinIO Console
# URL: http://localhost:9001
# Username: minioadmin
# Password: minioadmin

# Accéder à PostgreSQL
# Host: localhost:5432
# Database: patrimoine_db
# User: patrimoine_user
# Password: patrimoine_secure_password
```

### 4. **Télécharger les dépendances**

```bash
mvn clean dependency:resolve
```

### 5. **Lancer l'application**

```bash
# Via Maven
mvn spring-boot:run

# Via IDE (Run PatrimoineApplication.java)

# L'application démarre sur: http://localhost:8080
```

### 6. **Accéder à la documentation API**

```
http://localhost:8080/api/swagger-ui.html
http://localhost:8080/api/v3/api-docs
```

---

## 📁 Structure du Projet

### Architecture Hexagonale (3 Couches)

```
src/main/java/com/ktiservice/patrimoine/
├── domain/                          # 🔴 COUCHE DOMAINE (Logique métier)
│   ├── entities/                    # Entités (User, HeritageNetwork, etc.)
│   ├── enums/                       # Énumérations (Role, Language, etc.)
│   ├── exceptions/                  # Exceptions métier
│   └── ports/                       # Interfaces (contrats)
│       ├── persistence/
│       └── services/
│
├── application/                     # 🟡 COUCHE APPLICATION (Orchestration)
│   ├── dtos/                        # Data Transfer Objects
│   ├── mappers/                     # MapStruct Mappers
│   ├── services/                    # Services applicatifs
│   └── usecases/                    # Use cases (optionnel)
│
├── infrastructure/                  # 🟢 COUCHE INFRASTRUCTURE (Adaptateurs)
│   ├── persistence/
│   │   ├── entities/                # Entités JPA
│   │   ├── repositories/            # Spring Data JPA Repositories
│   │   └── adapters/                # Implémentations des ports
│   ├── web/
│   │   ├── controllers/             # REST Controllers
│   │   ├── security/                # JWT, Authentication
│   │   └── advice/                  # Exception handlers
│   ├── config/                      # Configuration Spring
│   ├── utils/                       # Utilitaires
│   ├── minio/                       # Intégration MinIO
│   └── properties/                  # Properties binding
│
└── PatrimoineApplication.java       # 🟦 Entry Point
```

---

## 🔄 Flux d'une Requête

### Exemple: Créer un Utilisateur

```
1. HTTP POST /api/v1/users
   ↓
2. UserController.createUser()
   - Reçoit CreateUserRequest (DTO)
   - Valide les données (@Valid)
   ↓
3. UserApplicationService.registerUser()
   - Valide la logique métier
   - Hash le mot de passe (BCrypt)
   - Appelle UserRepository (port)
   ↓
4. UserRepositoryAdapter.save()
   - Convertit User → UserJpaEntity
   - Appelle UserJpaRepository
   ↓
5. PostgreSQL
   - INSERT INTO users
   ↓
6. UserMapper.toResponse()
   - Convertit User → UserResponse (DTO)
   ↓
7. HTTP 201 Created
   Response: UserResponse (JSON)
```

---

## 🛠️ Comprendre les Dépendances Clés

| Dépendance | Usage | Version |
|-----------|-------|---------|
| **Spring Boot** | Framework principal | 3.3.3 |
| **Spring Data JPA** | ORM + Repositories | Inclus |
| **Spring Security** | Authentification/Autorisation | Inclus |
| **PostgreSQL** | Base de données | 15 (Docker) |
| **Flyway** | Migration DB | 10.x |
| **JWT (JJWT)** | Tokens JWT | 0.12.3 |
| **MapStruct** | Mapping DTO ↔ Entity | 1.5.5 |
| **Lombok** | Réduction boilerplate | 1.18.30 |
| **MinIO** | Stockage objets | 8.5.7 |
| **Caffeine** | Cache en mémoire | 3.1.8 |
| **Swagger/OpenAPI** | Documentation API | 2.3.0 |

---

## 🔐 Configuration Sécurité

### JWT Token

- **Algorithme**: HS256 (HMAC-SHA256)
- **Durée**: 24h
- **Header**: `Authorization: Bearer <token>`

### Authentification

```java
// Login
POST /api/v1/auth/login
{
  "email": "user@example.com",
  "password": "password123"
}

// Response
{
  "token": "eyJhbGc...",
  "refreshToken": "eyJhbGc...",
  "expiresIn": 86400
}

// Utiliser le token
GET /api/v1/users/profile
Headers: Authorization: Bearer eyJhbGc...
```

### Autorisation (Rôles)

```java
@PreAuthorize("hasRole('ADMINISTRATOR')")
public void deleteUser(UUID userId) { ... }

@PreAuthorize("hasRole('SITE_MANAGER') or @securityService.isOwner(#siteId)")
public void updateSite(UUID siteId, ...) { ... }
```

---

## 💾 Migration Base de Données

### Flyway Migrations

```
src/main/resources/db/migration/
├── V1__init_schema.sql              # Schéma initial
├── V2__add_indexes.sql              # Indexes
└── V3__seed_data.sql                # Données initiales (optionnel)
```

### Ajouter une Migration

```bash
# Créer un nouveau fichier
touch src/main/resources/db/migration/V2__add_new_table.sql

# Flyway migrate automatiquement au démarrage
mvn spring-boot:run
```

---

## 🧪 Tests

### Structure des Tests

```
src/test/java/com/ktiservice/patrimoine/
├── domain/                          # Tests unitaires (Domain)
├── application/                     # Tests services (Application)
└── infrastructure/                  # Tests intégration
```

### Exemples de Tests

```java
// Test unitaire - Service
@ExtendWith(MockitoExtension.class)
class UserApplicationServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserApplicationService service;
    
    @Test
    void testRegisterUser_Success() {
        // Arrange
        CreateUserRequest request = CreateUserRequest.builder()
                .email("test@example.com")
                .password("password123")
                .firstName("John")
                .lastName("Doe")
                .build();
        
        // Act
        UserResponse response = service.registerUser(request, "admin@test.com");
        
        // Assert
        assertNotNull(response);
        assertEquals("test@example.com", response.getEmail());
    }
}
```

### Lancer les Tests

```bash
# Tous les tests
mvn test

# Un test spécifique
mvn test -Dtest=UserApplicationServiceTest

# Avec coverage
mvn test jacoco:report
# Report: target/site/jacoco/index.html
```

---

## 🔍 Logging

### Logback Configuration

```yaml
# Fichier: src/main/resources/logback-spring.xml

# Niveaux par package
logging:
  level:
    root: INFO
    com.ktiservice.patrimoine: DEBUG
    org.springframework.security: DEBUG
    org.hibernate: WARN

# Output
console: PatternLayout avec timestamp
file: logs/patrimoine.log (10MB max, 30 jours)
```

### Utiliser les Logs

```java
@Slf4j
public class UserService {
    
    public void registerUser(CreateUserRequest request) {
        log.info("Registering user with email: {}", request.getEmail());
        log.debug("User details: {}", request);
        log.warn("Email already exists: {}", request.getEmail());
        log.error("Failed to register user", exception);
    }
}
```

---

## 📊 Cache (Caffeine)

### Cacher une Méthode

```java
@Cacheable(value = "users", key = "#userId")
public UserResponse getUserById(UUID userId) {
    // Résultat mis en cache
}

// Invalider le cache
@CacheEvict(value = "users", key = "#userId")
public UserResponse updateUser(UUID userId, ...) {
    // Cache invalidé après la méthode
}

// Vider tout le cache
@CacheEvict(value = "users", allEntries = true)
public void refreshAllUsers() { }
```

---

## 📦 Build et Déploiement

### Build avec Maven

```bash
# Build JAR
mvn clean package

# JAR: target/patrimoine-1.0.0.jar
```

### Docker

```bash
# Build image
docker build -t patrimoine:1.0.0 .

# Run container
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host:5432/patrimoine_db \
  -e MINIO_URL=http://minio:9000 \
  patrimoine:1.0.0
```

### Docker Compose Production

```bash
# Déployer
docker-compose -f docker-compose.yml up -d

# Logs
docker-compose logs -f patrimoine-app

# Arrêter
docker-compose down
```

---

## 🐛 Troubleshooting

### Erreur: "Cannot connect to PostgreSQL"

```bash
# Vérifier que les conteneurs tournent
docker ps

# Logs PostgreSQL
docker logs patrimoine-postgres-dev

# Redémarrer
docker-compose -f docker-compose.dev.yml restart postgresql
```

### Erreur: "Port 8080 already in use"

```bash
# Changer le port
java -Dserver.port=8081 -jar target/patrimoine.jar

# Ou dans application.yml
server:
  port: 8081
```

### Erreur: "JWT validation failed"

```
Assurez-vous que:
1. Token n'est pas expiré (24h)
2. Header format: "Bearer <token>"
3. Secret key correctement configurée
4. Pas d'espace en extra dans le token
```

---

## 📚 Prochaines Étapes (Après Phase 2)

1. ✅ **Phase 2 (Actuelle)**: Architecture + Configurations ✓
2. ⏳ **Phase 3**: Développer les features (Services + Controllers)
3. ⏳ **Phase 4**: Tester (Tests unitaires + intégration)
4. ⏳ **Phase 5**: Frontend React
5. ⏳ **Phase 6**: Déploiement + Documentation

---

## 💡 Bonnes Pratiques

### 1. Conventions de Codage

- Noms de classe: PascalCase (`UserService`, `HeritageMapper`)
- Noms de variable: camelCase (`userId`, `firstName`)
- Constantes: UPPER_SNAKE_CASE (`MAX_PAGE_SIZE`, `DEFAULT_LANGUAGE`)

### 2. Gestion des Erreurs

```java
// ❌ Mauvais
if (user == null) {
    return null;
}

// ✅ Bon
if (user == null) {
    throw new ResourceNotFoundException("User", userId.toString());
}
```

### 3. Logging

```java
// ❌ Mauvais
System.out.println("User created");

// ✅ Bon
log.info("User created with email: {}", user.getEmail());
```

### 4. Transactions

```java
// ✅ Bon - Transactions explicites
@Transactional
public UserResponse registerUser(CreateUserRequest request) {
    User user = userRepository.save(...);
    emailService.sendConfirmation(user.getEmail());
    return mapper.toResponse(user);
}
```

---

## 🆘 Support et Ressources

- **Spring Boot**: https://spring.io/projects/spring-boot
- **Spring Data JPA**: https://spring.io/projects/spring-data-jpa
- **JWT (JJWT)**: https://github.com/jwtk/jjwt
- **MapStruct**: https://mapstruct.org
- **Flyway**: https://flywaydb.org

---

## ✅ Checklist Démarrage

- [ ] Java 17 installé
- [ ] Maven 3.9 installé
- [ ] Docker & Docker Compose installés
- [ ] PostgreSQL et MinIO tournent (docker-compose up)
- [ ] Application démarre sans erreur
- [ ] API accessible sur http://localhost:8080/api/swagger-ui.html
- [ ] Tests passent (`mvn test`)

---

**Phase 2 complétée ! Prêt pour la Phase 3.** 🎉