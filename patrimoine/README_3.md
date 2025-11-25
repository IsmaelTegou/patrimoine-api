# PHASE 3 COMPLÈTEMENT RÉSOLUE ✅
## Guide Complet Auth + Tous les Endpoints

---

## 🔐 AUTHENTICATION FLOW

### 1. Enregistrement (REGISTRATION)

**Endpoint**: `POST /api/v1/auth/register`
**Public**: OUI (pas besoin de token)

```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@heritage-cameroon.com",
    "password": "SecurePassword123",
    "firstName": "Jean",
    "lastName": "Dupont",
    "phoneNumber": "+237123456789"
  }'
```

**Response** (201 Created):
```json
{
  "status": 201,
  "message": "Registration successful. Please check your email to confirm your account.",
  "data": null,
  "timestamp": "2025-10-18T10:30:00"
}
```

⚠️ **Note**: L'utilisateur reçoit un email de confirmation. En développement, approuvez manuellement via la BD:
```sql
UPDATE users SET is_active = true WHERE email = 'user@heritage-cameroon.com';
```

---

### 2. Login (OBTENIR TOKEN JWT)

**Endpoint**: `POST /api/v1/auth/login`
**Public**: OUI (pas besoin de token)

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@heritage-cameroon.com",
    "password": "SecurePassword123"
  }'
```

**Response** (200 OK):
```json
{
  "status": 200,
  "message": "Request successful",
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "expiresIn": 86400000,
    "userId": "550e8400-e29b-41d4-a716-446655440000",
    "email": "user@heritage-cameroon.com",
    "role": "TOURIST",
    "tokenType": "Bearer"
  },
  "timestamp": "2025-10-18T10:35:00"
}
```

💾 **Sauvegardez le `accessToken`** - vous en aurez besoin pour les autres endpoints !

---

### 3. Utiliser le Token JWT

Pour tous les endpoints protégés, incluez le header:
```bash
Authorization: Bearer <accessToken>
```

**Exemple complet** avec tous les headers:
```bash
curl -X GET http://localhost:8080/api/v1/users/profile \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json"
```

---

## 👤 ENDPOINTS USER (Authentifié)

### Récupérer le profil utilisateur

**Endpoint**: `GET /api/v1/users/profile`
**Authentification**: ✅ Requise
**Rôles**: TOURIST, GUIDE, SITE_MANAGER, ADMINISTRATOR

```bash
curl -X GET http://localhost:8080/api/v1/users/profile \
  -H "Authorization: Bearer <TOKEN>"
```

**Response**:
```json
{
  "status": 200,
  "message": "Request successful",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "email": "user@heritage-cameroon.com",
    "firstName": "Jean",
    "lastName": "Dupont",
    "phoneNumber": "+237123456789",
    "role": "TOURIST",
    "language": "FRENCH",
    "isActive": true,
    "createdAt": "2025-10-18T10:30:00"
  },
  "timestamp": "2025-10-18T10:40:00"
}
```

### Mettre à jour le profil utilisateur

**Endpoint**: `PUT /api/v1/users/profile`
**Authentification**: ✅ Requise

```bash
curl -X PUT http://localhost:8080/api/v1/users/profile \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jean-Paul",
    "lastName": "Dupont-Martin",
    "phoneNumber": "+237987654321"
  }'
```

### Changer le mot de passe

**Endpoint**: `POST /api/v1/users/change-password`
**Authentification**: ✅ Requise

```bash
curl -X POST http://localhost:8080/api/v1/users/change-password \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "newPassword": "NewSecurePassword456"
  }'
```

---

## 🏛️ ENDPOINTS HERITAGE (Patrimoine Touristique)

### Créer un site patrimoinial (ADMIN UNIQUEMENT)

**Endpoint**: `POST /api/v1/heritage`
**Authentification**: ✅ Requise
**Rôles**: ADMINISTRATOR

```bash
curl -X POST http://localhost:8080/api/v1/heritage \
  -H "Authorization: Bearer <ADMIN_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "siteName": "Mont Cameroun",
    "description": "Volcan actif et site naturel spectaculaire",
    "heritageType": "NATURAL_SITE",
    "latitude": 4.2033,
    "longitude": 9.7679,
    "province": "Région du Sud-Ouest",
    "openingTime": "06:00:00",
    "closingTime": "18:00:00",
    "entryFee": 15.00,
    "maxCapacity": 500,
    "managerContactName": "Pierre Nkomo",
    "managerPhoneNumber": "+237123456789"
  }'
```

### Consulter un site patrimonial

**Endpoint**: `GET /api/v1/heritage/{siteId}`
**Authentification**: ❌ NON requise (public)

```bash
curl http://localhost:8080/api/v1/heritage/550e8400-e29b-41d4-a716-446655440000
```

### Rechercher des sites (filtres)

**Endpoint**: `GET /api/v1/heritage/search`
**Authentification**: ❌ NON requise (public)
**Paramètres**:
- `province`: Région (optionnel)
- `heritageType`: Type de patrimoine (optionnel)
- `search`: Mot-clé (optionnel)
- `pageNumber`: Numéro de page (défaut: 0)
- `pageSize`: Éléments par page (défaut: 20, max: 100)

```bash
# Rechercher tous les sites naturels dans le Sud-Ouest
curl "http://localhost:8080/api/v1/heritage/search?province=Sud-Ouest&heritageType=NATURAL_SITE&pageNumber=0&pageSize=20"

# Rechercher avec mot-clé
curl "http://localhost:8080/api/v1/heritage/search?search=mont&pageNumber=0&pageSize=20"
```

### Récupérer les sites les mieux notés

**Endpoint**: `GET /api/v1/heritage/top-rated`
**Authentification**: ❌ NON requise (public)

```bash
curl "http://localhost:8080/api/v1/heritage/top-rated?pageNumber=0&pageSize=10"
```

### Mettre à jour un site (SITE_MANAGER ou ADMIN)

**Endpoint**: `PUT /api/v1/heritage/{siteId}`
**Authentification**: ✅ Requise
**Rôles**: SITE_MANAGER, ADMINISTRATOR

```bash
curl -X PUT http://localhost:8080/api/v1/heritage/550e8400-e29b-41d4-a716-446655440000 \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "siteName": "Mont Cameroun (Mis à jour)",
    "openingTime": "07:00:00",
    "closingTime": "19:00:00",
    "entryFee": 20.00
  }'
```

### Supprimer un site (ADMIN UNIQUEMENT - Soft Delete)

**Endpoint**: `DELETE /api/v1/heritage/{siteId}`
**Authentification**: ✅ Requise
**Rôles**: ADMINISTRATOR

```bash
curl -X DELETE http://localhost:8080/api/v1/heritage/550e8400-e29b-41d4-a716-446655440000 \
  -H "Authorization: Bearer <ADMIN_TOKEN>"
```

---

## ⭐ ENDPOINTS AVIS (Reviews)

### Créer un avis (TOURIST AUTHENTIFIÉ)

**Endpoint**: `POST /api/v1/reviews`
**Authentification**: ✅ Requise
**Rôles**: TOURIST

```bash
curl -X POST http://localhost:8080/api/v1/reviews \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "siteId": "550e8400-e29b-41d4-a716-446655440000",
    "rating": 5,
    "comment": "Incroyable expérience! À visiter absolument!"
  }'
```

### Récupérer les avis approuvés d'un site

**Endpoint**: `GET /api/v1/reviews/site/{siteId}`
**Authentification**: ❌ NON requise (public)

```bash
curl "http://localhost:8080/api/v1/reviews/site/550e8400-e29b-41d4-a716-446655440000?pageNumber=0&pageSize=20"
```

### Récupérer les avis en attente de modération

**Endpoint**: `GET /api/v1/reviews/pending`
**Authentification**: ✅ Requise
**Rôles**: SITE_MANAGER, ADMINISTRATOR

```bash
curl http://localhost:8080/api/v1/reviews/pending \
  -H "Authorization: Bearer <MANAGER_TOKEN>"
```

### Approuver un avis

**Endpoint**: `PUT /api/v1/reviews/{reviewId}/approve`
**Authentification**: ✅ Requise
**Rôles**: SITE_MANAGER, ADMINISTRATOR

```bash
curl -X PUT http://localhost:8080/api/v1/reviews/550e8400-e29b-41d4-a716-446655440001/approve \
  -H "Authorization: Bearer <MANAGER_TOKEN>"
```

### Rejeter un avis

**Endpoint**: `PUT /api/v1/reviews/{reviewId}/reject`
**Authentification**: ✅ Requise
**Rôles**: SITE_MANAGER, ADMINISTRATOR

```bash
curl -X PUT http://localhost:8080/api/v1/reviews/550e8400-e29b-41d4-a716-446655440001/reject \
  -H "Authorization: Bearer <MANAGER_TOKEN>"
```

---

## 📚 RÉSUMÉ - TOUS LES ENDPOINTS

### Public (Pas d'authentification requise)
- ✅ `POST /api/v1/auth/register` - Créer un compte
- ✅ `POST /api/v1/auth/login` - Se connecter
- ✅ `GET /api/v1/heritage/{id}` - Consulter site
- ✅ `GET /api/v1/heritage/search` - Rechercher sites
- ✅ `GET /api/v1/heritage/top-rated` - Sites les mieux notés
- ✅ `GET /api/v1/reviews/site/{id}` - Avis approuvés

### Authentifiés (Avec JWT Token)
- ✅ `GET /api/v1/users/profile` - Récupérer profil
- ✅ `PUT /api/v1/users/profile` - Mettre à jour profil
- ✅ `POST /api/v1/users/change-password` - Changer mot de passe
- ✅ `POST /api/v1/auth/refresh-token` - Renouveler token
- ✅ `POST /api/v1/auth/logout` - Se déconnecter

### TOURIST Authentifiés
- ✅ `POST /api/v1/reviews` - Créer un avis

### SITE_MANAGER Authentifiés
- ✅ `PUT /api/v1/heritage/{id}` - Mettre à jour site
- ✅ `GET /api/v1/reviews/pending` - Avis en attente
- ✅ `PUT /api/v1/reviews/{id}/approve` - Approuver avis
- ✅ `PUT /api/v1/reviews/{id}/reject` - Rejeter avis

### ADMINISTRATOR Authentifiés
- ✅ `POST /api/v1/heritage` - Créer site
- ✅ `PUT /api/v1/heritage/{id}` - Mettre à jour site
- ✅ `DELETE /api/v1/heritage/{id}` - Supprimer site
- ✅ `GET /api/v1/reviews/pending` - Avis en attente
- ✅ `PUT /api/v1/reviews/{id}/approve` - Approuver avis
- ✅ `PUT /api/v1/reviews/{id}/reject` - Rejeter avis
- ✅ `DELETE /api/v1/reviews/{id}` - Supprimer avis

---

## ✅ CHECKLIST AUTHENTIFICATION

- [ ] Application démarre sans erreur
- [ ] `POST /auth/register` fonctionne
- [ ] `POST /auth/login` retourne un JWT token
- [ ] Token JWT est sauvegardé
- [ ] `GET /users/profile` avec token retourne le profil
- [ ] `GET /heritage/search` public fonctionne SANS token
- [ ] `POST /heritage` avec ADMIN token crée un site
- [ ] `POST /reviews` avec TOURIST token crée un avis
- [ ] Endpoints sans authentification sont accessibles
- [ ] Endpoints protégés rejettent sans token
- [ ] RBAC appliquée correctement

---

## 🚀 VOUS ÊTES MAINTENANT PRÊT POUR PHASE 3.5 !

Tous les endpoints Auth sont fonctionnels. Vous pouvez :
1. ✅ Vous enregistrer
2. ✅ Vous connecter et obtenir un token JWT
3. ✅ Utiliser le token pour accéder aux endpoints protégés
4. ✅ Créer/consulter sites et avis

**PHASE 3.5**: Développer les services manquants (Media, Itinerary, Event, Report, etc.)