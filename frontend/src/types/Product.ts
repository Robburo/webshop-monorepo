export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  stock: number;
  categoryId: number;
  categoryName: string;
}

export interface ProductResponseDto {
  id: number;
  name: string;
  description: string;
  price: number;
  stock: number;
  categoryId: number;
  categoryName: string;
}

export interface ProductRequestDto {
  name: string;
  description: string;
  price: number;
  stock: number;
  categoryId: number;
}