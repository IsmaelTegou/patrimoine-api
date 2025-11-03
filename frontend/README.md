# Patrimoine API Frontend

Interface d'administration pour la gestion du patrimoine touristique.

## Installation

```bash
npm install
```

## Développement

Pour lancer le serveur de développement :

```bash
npm run dev
```

## Scripts disponibles

- `npm run dev` : Lance le serveur de développement
- `npm run build` : Construit l'application pour la production
- `npm run preview` : Prévisualise la version de production
- `npm run lint` : Vérifie le code avec ESLint

## Structure du projet

```
src/
  ├── assets/        # Images, vidéos et autres ressources statiques
  ├── components/    # Composants React réutilisables
  │   ├── pages/    # Pages de l'application
  │   ├── Dashboard.jsx
  │   ├── Login.jsx
  │   ├── Sidebar.jsx
  │   └── Signup.jsx
  ├── App.jsx       # Composant racine
  └── main.jsx      # Point d'entrée de l'application
```

## Technologies utilisées

- React
- Material-UI (MUI)
- Vite
- ESLint
