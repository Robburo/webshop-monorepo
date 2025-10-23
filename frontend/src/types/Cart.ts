export interface CartItem {
  id: number;
  productId: number;
  productName: string;
  quantity: number;
  userId: number;
}

export interface CartItemResponseDto {
  id: number;
  productId: number;
  productName: string;
  quantity: number;
  userId: number;
  price: number;
}

export interface CartItemCreateDto {
  productId: number;
  quantity: number;
}