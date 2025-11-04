# Webshop Vue Frontend

Modern **Vue 3 + Nuxt 4 + TailwindCSS 3** frontend for the Webshop API.

---

## ⚙️ Tech Stack

* Vue 3.5
* Nuxt 4.2
* TailwindCSS 3.4
* Pinia 3 (state management)
* Axios for backend communication
* Vue Toastification for notifications
* Vue Router 4.6

---

## 🚀 Run Locally

```bash
cd vue-frontend
npm install
npm run dev
```

Frontend runs at **[http://localhost:3000](http://localhost:3000)**

---

## 🧠 Folder Structure

```
components/
layouts/
pages/
services/
composables/
stores/
```

---

## ⚡ Environment Variables

Create `.env`:

```bash
API_BASE_URL=http://localhost:8080/api
```

---

## 🧰 Build for Production

```bash
npm run build
npm run preview
```

---

## 📦 Dependency Summary

```
@nuxtjs/tailwindcss@6.14.0
@pinia/nuxt@0.11.2
autoprefixer@10.4.21
axios@1.13.1
nuxt@4.2.0
pinia@3.0.3
postcss@8.5.6
tailwindcss@3.4.18
vue@3.5.22
vue-router@4.6.3
vue-toastification@2.0.0-rc.5
```