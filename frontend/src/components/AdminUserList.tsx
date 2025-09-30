"use client";
import { useEffect, useState } from "react";
import { getAllUsers, UserResponseDto } from "@/services/adminApi";
import { getCurrentUser } from "@/services/userApi";

export default function AdminUserList() {
  const [users, setUsers] = useState<UserResponseDto[]>([]);
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    // sjekk innlogget bruker først
    getCurrentUser()
      .then((u) => {
        if (u.role === "ROLE_ADMIN") {
          setIsAdmin(true);
          // hent brukere hvis admin
          getAllUsers().then(setUsers).catch(console.error);
        }
      })
      .catch(() => setIsAdmin(false));
  }, []);

  if (!isAdmin) {
    return <p>Du har ikke tilgang til denne siden.</p>;
  }

  return (
    <div>
      <h2 className="text-xl font-bold mb-4">Alle brukere (Admin)</h2>
      <ul className="space-y-2">
        {users.map((u) => (
          <li key={u.id} className="p-2 border-b border-gray-700">
            <span className="font-semibold">{u.username}</span> ({u.email}) –{" "}
            {u.role}
          </li>
        ))}
      </ul>
    </div>
  );
}
