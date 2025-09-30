import ProductList from "./ProductList";
import { Product } from "@/types/Product";

interface FeaturedProductsProps {
  title: string;
  products: Product[];
  onAddToCart?: (id: number) => void;
}

export default function FeaturedProducts({ title, products, onAddToCart }: FeaturedProductsProps) {
  return (
    <section className="mb-8">
      <h2 className="text-2xl font-semibold mb-4">{title}</h2>
      <ProductList products={products} onAddToCart={onAddToCart} />
    </section>
  );
}
