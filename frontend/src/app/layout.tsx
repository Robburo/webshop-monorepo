import type { Metadata } from "next";
import "./globals.css";
import Navbar from "@/components/Navbar";
import { AuthProvider } from "@/hooks/useAuth";

export const metadata: Metadata = {
  title: "Webshop",
  description: "En moderne webshop med Next.js + Tailwind",
};

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="no" className="dark">
      <body className="bg-gray-900 text-gray-100 min-h-screen flex flex-col">
        <AuthProvider>
        <Navbar />
        <main className="flex-1 max-w-6xl mx-auto p-6">{children}</main>
        <footer className="bg-gray-800 text-center py-4 text-sm text-gray-400">
          © {new Date().getFullYear()} Webshop. Alle rettigheter forbeholdt.
        </footer>
        </AuthProvider>
      </body>
    </html>
  );
}
