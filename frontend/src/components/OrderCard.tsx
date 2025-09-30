import { OrderDto } from "@/services/orderApi";

interface Props {
  order: OrderDto;
}

export default function OrderCard({ order }: Props) {
  return (
    <div className="p-4 bg-gray-800 rounded shadow">
      <p className="font-semibold">
        Ordre #{order.id} – {new Date(order.createdAt).toLocaleDateString()} –{" "}
        Status: {order.status}
      </p>
      <ul className="ml-4 mt-2 list-disc">
        {order.items.map((item) => (
          <li key={item.id}>
            {item.productName} – {item.quantity} stk – {item.price} kr
          </li>
        ))}
      </ul>
    </div>
  );
}
