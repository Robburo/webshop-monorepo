import Hero from "@/components/Hero";
import FeaturedProducts from "@/components/FeaturedProducts";
import CategoryGrid from "@/components/CategoryGrid";
import { Product } from "@/types/Product";
import { Category } from "@/types/Category";

export default function HomePage() {
  // TODO: hent data fra backend (API-kall)
  const popularProducts: Product[] = [];
  const discountedProducts: Product[] = [];
  const categories: Category[] = [];

  return (
    <div className="space-y-12">
      <Hero />
      <FeaturedProducts title="Populære produkter" products={popularProducts} />
      <FeaturedProducts title="Tilbud" products={discountedProducts} />
      <CategoryGrid categories={categories} />
    </div>
  );
}
