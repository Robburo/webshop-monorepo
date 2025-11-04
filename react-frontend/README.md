# Webshop React Frontend

Modern **React 19 + Next.js 15 + TailwindCSS 4** frontend for the Webshop API.

---

## ⚙️ Tech Stack

* React 19
* Next.js 15
* TypeScript 5.9
* TailwindCSS 4.1
* Fetch API for backend communication
* React Hot Toast (notifications)
* ESLint 9 (with Next config)

---

## 🚀 Run Locally

```bash
cd react-frontend
npm install
npm run dev
```

Frontend runs at **[http://localhost:3000](http://localhost:3000)**

---

## 🧠 Folder Structure

```
src/
├── components/
├── pages/
├── hooks/
├── services/
└── styles/
```

---

## ⚡ Environment Variables

Create `.env.local`:

```bash
NEXT_PUBLIC_API_URL=http://localhost:8080/api
```

---

## 📦 Dependency Summary

```
@eslint/eslintrc@3.3.1
@tailwindcss/postcss@4.1.16
autoprefixer@10.4.21
eslint-config-next@15.5.4
eslint@9.39.1
next@15.5.4
react@19.1.0
react-dom@19.1.0
react-hot-toast@2.6.0
tailwindcss@4.1.16
typescript@5.9.3
@types/node@20.19.24
@types/react@19.2.2
@types/react-dom@19.2.2
```