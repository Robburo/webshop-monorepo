import Hero from "@/components/Hero";
import CategoryGrid from "@/components/CategoryGrid";
import FeaturedProducts from "@/components/FeaturedProducts";

export default function HomePage() {
  return (
    <div className="space-y-12">
      <Hero />
      <CategoryGrid/>
      <FeaturedProducts/>
    </div>
  );
}
