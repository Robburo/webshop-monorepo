import Link from "next/link";

export default function Hero() {
  return (
    <section className="py-12 text-center text-white bg-gray-900 rounded-lg">
      <h1 className="mb-4 text-4xl font-bold">Velkommen til Webshoppen</h1>
      <p className="mb-6">Finn de beste produktene til gode priser.</p>
      <Link
        href="/products"
        className="px-6 py-2 text-gray-900 bg-white rounded shadow"
      >
        Se alle produkter
      </Link>
    </section>
  );
}
