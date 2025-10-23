export interface OrderItem {
  id: number;
  productId: number;
  productName: string;
  quantity: number;
  price: number;
}

export interface Order {
  id: number;
  userId: number;
  createdAt: string;
  status: string;
  items: OrderItem[];
}

export interface OrderItemDto {
  id: number;
  productId: number;
  productName: string;
  quantity: number;
  price: number;
}

export interface OrderDto {
  id: number;
  userId: number;
  createdAt: string;
  status: string;

  recipientName: string;
  street: string;
  postalCode: string;
  city: string;
  country: string;

  items: OrderItemDto[];
}