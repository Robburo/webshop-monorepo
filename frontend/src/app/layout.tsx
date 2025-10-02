import type { Metadata } from "next";
import "./globals.css";
import Navbar from "@/components/Navbar";
import { AuthProvider } from "@/hooks/useAuth";
import ToasterProvider from "@/components/Toast";

export const metadata: Metadata = {
  title: "Webshop",
  description: "En moderne webshop med Next.js + Tailwind",
};

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="no" className="dark">
      <body className="flex flex-col min-h-screen text-gray-100 bg-gray-900">
        <AuthProvider>
        <Navbar />
        <main className="flex-1 max-w-6xl p-6 mx-auto">{children}</main>
        <ToasterProvider/>
        <footer className="py-4 text-sm text-center text-gray-400 bg-gray-800">
          © {new Date().getFullYear()} Webshop. Alle rettigheter forbeholdt.
        </footer>
        </AuthProvider>
      </body>
    </html>
  );
}
