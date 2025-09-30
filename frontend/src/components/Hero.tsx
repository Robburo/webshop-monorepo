import Link from "next/link";

export default function Hero() {
  return (
    <section className="text-center py-12 bg-gray-900 text-white rounded-lg">
      <h1 className="text-4xl font-bold mb-4">Velkommen til Webshoppen</h1>
      <p className="mb-6">Finn de beste produktene til gode priser.</p>
      <Link
        href="/products"
        className="bg-white text-gray-900 px-6 py-2 rounded shadow"
      >
        Se alle produkter
      </Link>
    </section>
  );
}
